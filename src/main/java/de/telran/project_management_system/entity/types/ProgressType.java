package de.telran.project_management_system.entity.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ProgressType {

    TODO(1, "todo"),
    IN_PROGRESS(2, "in progress"),
    DONE(3, "done");

    private final Integer progressTypeId;
    private final String externalTypeId;

    public static ProgressType findByTypeId(Integer id) {

        if (id == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Id cannot be null! "
            );
        }

        return Arrays.stream(ProgressType.values())
                .filter(type -> type.getProgressTypeId().equals(id))
                .findFirst()
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("No type with id [%s] found.", id)
                        )
                );
    }

    @JsonCreator
    public static ProgressType findByExternalTypeId(String externalId) {
        if (externalId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "External id cannot be null"
            );
        }

        return Arrays.stream(ProgressType.values())
                .filter(type -> type.getExternalTypeId().equals(externalId))
                .findFirst()
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("No type with external id [%s] found.", externalId)
                        )
                );
    }
}
