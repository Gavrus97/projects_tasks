package de.telran.project_management_system.dto.request;

import de.telran.project_management_system.entity.types.ProgressType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PushTaskRequestDTO {

    @NotNull(message = "Task id cannot be null")
    @Positive(message = "Task id must be greater then zero")
    private Long taskId;

    //@NotNull(message = "PushTo param cannot be null")
    //@NotNull(message = "Push to param must be "todo" or "in progress" or "done"")
    //@NotBlank(message = "PushTo param cannot be blank")
   // @Size(min = 4, max = 11, message = "PushTo param must be between 4 and 11 chars")
    private ProgressType pushTo;
}
