package de.telran.project_management_system.service;

import de.telran.project_management_system.dto.request.AddTaskRequestDTO;
import de.telran.project_management_system.dto.response.TaskResponseDTO;
import de.telran.project_management_system.entity.types.ProgressType;

import java.util.List;

public interface TaskService {

    void addTaskToProject(AddTaskRequestDTO request);

    void pushTaskForward(Long id);

    void pushTaskBackward(Long id);

    List<TaskResponseDTO> getAll();

    List<TaskResponseDTO> getByName(String name);

    List<TaskResponseDTO> getByStatus(ProgressType type);

    List<TaskResponseDTO> findAbandoned();
}
