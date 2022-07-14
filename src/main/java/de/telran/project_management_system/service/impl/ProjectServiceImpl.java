package de.telran.project_management_system.service.impl;

import de.telran.project_management_system.converters.ProjectConverter;
import de.telran.project_management_system.dto.request.ProjectRequestDTO;
import de.telran.project_management_system.dto.response.ProjectResponseDTO;
import de.telran.project_management_system.entity.Project;
import de.telran.project_management_system.entity.Task;
import de.telran.project_management_system.entity.types.ProgressType;
import de.telran.project_management_system.repository.ProjectRepository;
import de.telran.project_management_system.repository.TaskRepository;
import de.telran.project_management_system.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository repository;
    private final TaskRepository taskRepository;


    @Override
    public void create(ProjectRequestDTO request) {
        if (repository.existsByProjectName(request.getProjectName())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    String.format("Project with name [%s] already exists", request.getProjectName())
            );
        }

        Project project = Project
                .builder()
                .projectName(request.getProjectName())
                .build();
        repository.save(project);

        List<Task> tasks = request.getTasks()
                .stream()
                .map(task -> Task
                        .builder()
                        .taskName(task.getTaskName())
                        .daysToComplete(task.getDaysToComplete())
                        .progressType(ProgressType.TODO)
                        .project(project)
                        .build())
                .collect(Collectors.toList());

        taskRepository.saveAll(tasks);
    }


    @Override
    public ProjectResponseDTO getById(Long id) {
        if (id == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Id cannot be null!"
            );
        }

        Project project = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("No projects with id [%s] found", id)
                )
        );

        List<Task> tasks = taskRepository.findAllByProject(project);

        return ProjectConverter.convertProjectIntoResponseDTO(project, tasks);
    }


    @Override
    public List<ProjectResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(project -> {
                    List<Task> tasks = taskRepository.findAllByProject(project);
                    return ProjectConverter.convertProjectIntoResponseDTO(project, tasks);
                })
                .collect(Collectors.toList());
    }
}
