package de.telran.project_management_system.dto.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ValidationErrorResponseDTO {

    private HttpStatus status;
    private Map<String, List<String>> errors;
}
