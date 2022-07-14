package de.telran.project_management_system.converters;

import de.telran.project_management_system.dto.response.ProjectResponseDTO;
import de.telran.project_management_system.dto.response.TaskResponseDTO;
import de.telran.project_management_system.entity.Project;
import de.telran.project_management_system.entity.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectConverter {

    public static ProjectResponseDTO convertProjectIntoResponseDTO(Project project, List<Task> tasks) {
        List<TaskResponseDTO> taskDTOs = tasks.isEmpty() ? new ArrayList<>() : tasks
                .stream()
                .map(TaskConverter::convertTaskIntoResponseDTO)
                .collect(Collectors.toList());

        return ProjectResponseDTO
                .builder()
                .projectId(project.getId())
                .projectName(project.getProjectName())
                .tasks(taskDTOs)
                .build();
    }
}
