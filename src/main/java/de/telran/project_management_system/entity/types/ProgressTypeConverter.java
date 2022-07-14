package de.telran.project_management_system.entity.types;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ProgressTypeConverter implements AttributeConverter<ProgressType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ProgressType progressType) {
        return progressType == null ? null : progressType.getProgressTypeId();
    }

    @Override
    public ProgressType convertToEntityAttribute(Integer id) {
        return id == null ? null : ProgressType.findByTypeId(id);
    }
}
