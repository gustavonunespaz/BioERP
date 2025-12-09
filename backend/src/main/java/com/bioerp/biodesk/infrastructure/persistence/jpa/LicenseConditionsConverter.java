package com.bioerp.biodesk.infrastructure.persistence.jpa;

import com.bioerp.biodesk.core.domain.model.LicenseCondition;
import com.bioerp.biodesk.core.domain.model.LicenseConditionStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.Period;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.UUID;

@Converter
public class LicenseConditionsConverter implements AttributeConverter<Set<LicenseCondition>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Override
    public String convertToDatabaseColumn(Set<LicenseCondition> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "[]";
        }
        try {
            Set<ConditionData> payload = attribute.stream()
                    .map(condition -> new ConditionData(
                            condition.getId(),
                            condition.getName(),
                            condition.getDocumentType(),
                            condition.getPeriodicity().toString(),
                            condition.getStatus()))
                    .collect(Collectors.toSet());
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to serialize license conditions", e);
        }
    }

    @Override
    public Set<LicenseCondition> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return Collections.emptySet();
        }
        try {
            Set<ConditionData> payload = objectMapper.readValue(dbData, new TypeReference<Set<ConditionData>>() {
            });
            return payload.stream()
                    .map(condition -> LicenseCondition.builder()
                            .id(condition.id())
                            .name(condition.name())
                            .documentType(condition.documentType())
                            .periodicity(Period.parse(condition.periodicity()))
                            .status(condition.status())
                            .build())
                    .collect(Collectors.toSet());
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to deserialize license conditions", e);
        }
    }

    private record ConditionData(
            UUID id,
            String name,
            String documentType,
            String periodicity,
            LicenseConditionStatus status
    ) {
    }
}
