package de.telran.project_management_system.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProjectResponseDTO {

    private Long projectId;
    private String projectName;
    private List<TaskResponseDTO> tasks;
}
