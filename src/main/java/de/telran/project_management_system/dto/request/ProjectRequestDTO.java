package de.telran.project_management_system.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProjectRequestDTO {

    @NotBlank(message = "Project name cannot be blank")
    @Size(min = 5, max = 30, message = "Project name length must contain 5-30 chars")
    private String projectName;

    @NotNull(message = "List of tasks cannot be null")
    @NotEmpty(message = "List of tasks cannot be empty")
    @Valid
    private List<TaskRequestDTO> tasks;
}
