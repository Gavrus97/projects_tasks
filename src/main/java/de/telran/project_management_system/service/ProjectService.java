package de.telran.project_management_system.service;

import de.telran.project_management_system.dto.request.ProjectRequestDTO;
import de.telran.project_management_system.dto.response.ProjectResponseDTO;

import java.util.List;

public interface ProjectService {

    void create (ProjectRequestDTO request);

    ProjectResponseDTO getById(Long id);

    List<ProjectResponseDTO> getAll();
}
