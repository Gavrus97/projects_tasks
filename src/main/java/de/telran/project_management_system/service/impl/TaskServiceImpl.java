package de.telran.project_management_system.service.impl;

import de.telran.project_management_system.converters.TaskConverter;
import de.telran.project_management_system.dto.request.AddTaskRequestDTO;
import de.telran.project_management_system.dto.response.TaskResponseDTO;
import de.telran.project_management_system.entity.Project;
import de.telran.project_management_system.entity.Task;
import de.telran.project_management_system.entity.types.ProgressType;
import de.telran.project_management_system.repository.ProjectRepository;
import de.telran.project_management_system.repository.TaskRepository;
import de.telran.project_management_system.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;
    private final ProjectRepository projectRepository;

    @Override
    public void addTaskToProject(AddTaskRequestDTO request) {

        Project project = projectRepository
                .findById(request.getProjectId())
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("No project with id [%s] found", request.getProjectId())
                        )
                );

        Task task = Task
                .builder()
                .taskName(request.getTaskName())
                .progressType(ProgressType.TODO)
                .project(project)
                .daysToComplete(request.getDaysToComplete())
                .build();

        repository.save(task);
    }

    @Override
    public void pushTaskForward(Long id) {
        Task task = repository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("No task with id [%s] found", id)
                        )
                );
        var status = task.getProgressType();
        var newStatus = Arrays
                .stream(ProgressType.values())
                .filter(type -> type.getProgressTypeId() > status.getProgressTypeId())
                .findFirst()
                .orElse(status);

        task.setProgressType(newStatus);
        repository.save(task);
    }

    @Override
    public void pushTaskBackward(Long id) {
        Task task = repository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("No task with id [%s] found", id)
                        )
                );

        var status = task.getProgressType();
        var newStatus = Arrays
                .stream(ProgressType.values())
                .filter(type -> type.getProgressTypeId() < status.getProgressTypeId())
                .sorted(Comparator.reverseOrder())
                .findFirst()
                .orElse(status);

        task.setProgressType(newStatus);
        repository.save(task);
    }

    @Override
    public List<TaskResponseDTO> getAll() {
        return repository
                .findAll()
                .stream()
                .map(TaskConverter::convertTaskIntoResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskResponseDTO> getByName(String name) {
        return repository.findAllByTaskName(name)
                .stream()
                .map(TaskConverter::convertTaskIntoResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskResponseDTO> getByStatus(ProgressType type) {
        return repository.findAllByProgressType(type)
                .stream()
                .map(TaskConverter::convertTaskIntoResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskResponseDTO> findAbandoned() {
        return repository
                .findAllByProgressTypeAndUpdatedOnIsBefore(
                        ProgressType.IN_PROGRESS,
                        LocalDateTime.now().minusDays(7))
                .stream()
                .map(TaskConverter::convertTaskIntoResponseDTO)
                .collect(Collectors.toList());
    }
}
