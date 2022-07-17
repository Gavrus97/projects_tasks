package de.telran.project_management_system.dto.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProgressStatusConvertErrorResponseDTO {

    private HttpStatus status;
    private String message;
}
