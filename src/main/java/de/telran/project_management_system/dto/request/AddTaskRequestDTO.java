package de.telran.project_management_system.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddTaskRequestDTO {

    @NotBlank(message = "Name of the task cannot be blank")
    @Size(min = 5, max = 30, message = "Task name length must contain 5-30 chars")
    private String taskName;

    @NotNull(message = "days to complete cannot be null")
    @Max(value = 365, message = "Days to complete cannot be bigger then 365")
    @Min(value = 1, message = "Days to complete cannot be smaller then 1")
    private Integer daysToComplete;

    @NotNull(message = "Project id cannot be null")
    @Positive(message = "Project id may be greater then zero")
    private Long projectId;
}
