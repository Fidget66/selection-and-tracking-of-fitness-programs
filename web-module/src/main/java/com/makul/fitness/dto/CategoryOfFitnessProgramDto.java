package com.makul.fitness.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryOfFitnessProgramDto {
    @Min(value = 0)
    private long id;
    @Size(min = 3, message = "Минимальный размер 3 символа")
    private String shortName;
    @Size(min = 20, message = "Минимальный размер 20 символов")
    private String description;
    private List <FitnessProgramDto> fitnessPrograms;
}
