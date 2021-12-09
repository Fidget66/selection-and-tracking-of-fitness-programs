package com.makul.fitness.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;


@UtilityClass
public class CustomMapperUtil {

    public <E, D> List<D> convertToDto(List<E> entities, Class<D> dtoClass) {
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(new JavaTimeModule());
        return entities
                .stream()
                .map(entity -> objectMapper.convertValue(entity, dtoClass))
                .collect(Collectors.toList());
    }

    public <E, D> D convertToDto(E entity, Class<D> dtoClass) {
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(new JavaTimeModule());
        return objectMapper.convertValue(entity, dtoClass);
    }

    public <E, D> Page<D> convertToDto(Page<E> entities, Class<D> dtoClass) {
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(new JavaTimeModule());
        return new PageImpl<D>(
                entities.getContent()
                        .stream()
                        .map(entity -> objectMapper.convertValue(entity, dtoClass))
                        .collect(Collectors.toList()),
                entities.getPageable(),
                entities.getTotalElements());
    }
}
