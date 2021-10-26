package com.makul.fitness.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FitnessProgramDto {
    private long id;
    @Size(min = 5, message = "Минимальная длина 5 символов")
    private String shortName;
    @Min(value = 10, message = "Минимальная продолжительность 10 занятий")
    private int duration;
    @Min(value = 20, message = "Минимальный возраст 20 лет")
    @Max(value = 65, message = "Максимальный возраст 65 лет")
    private int ageRestriction;
    @Min(value = 50, message = "Минимальный вес 50 кг")
    @Max(value = 120, message = "Максимальный вес 120 кг")
    private int weightRestriction;
    @Size(max = 1, message = "Длина не более 1го символа")
    @Pattern(regexp = "m|f",message = "Значение данного поля m или f")
    private String sexRestriction;
    @Min(value = 1, message = "Минимальное количество занятий в неделю - 1")
    @Max(value = 4, message = "Максимальное количество занятий в неделю - 4")
    private int exercisePerWeek;
    @Size(min = 40, message = "Минимальная длина описания 40 символов")
    private String description;
    private List <ReviewDto> reviews;
}
