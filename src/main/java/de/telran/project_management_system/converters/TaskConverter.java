package de.telran.project_management_system.converters;

import de.telran.project_management_system.dto.response.TaskResponseDTO;
import de.telran.project_management_system.entity.Task;

public class TaskConverter {

    public static TaskResponseDTO convertTaskIntoResponseDTO(Task task){
        return TaskResponseDTO
                .builder()
                .taskId(task.getId())
                .taskName(task.getTaskName())
                .progressType(task.getProgressType())
                .projectId(task.getProject().getId())
                .build();
    }
}
