package de.telran.project_management_system.service;

import de.telran.project_management_system.dto.request.AddTaskRequestDTO;
import de.telran.project_management_system.dto.request.PushTaskRequestDTO;
import de.telran.project_management_system.dto.response.TaskResponseDTO;
import de.telran.project_management_system.entity.types.ProgressType;

import java.util.List;

public interface TaskService {

    void addTaskToProject(AddTaskRequestDTO request);

    void pushTask(PushTaskRequestDTO request);

    List<TaskResponseDTO> getAll();

    List<TaskResponseDTO> getByName(String name);

    TaskResponseDTO getByStatus(ProgressType type);

    List<TaskResponseDTO> findAbandoned();
}
