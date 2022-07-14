package de.telran.project_management_system.dto.response;

import de.telran.project_management_system.entity.types.ProgressType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TaskResponseDTO {

    private Long taskId;
    private String taskName;
    private ProgressType progressType;
    private Long projectId;

}
