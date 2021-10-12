package com.makul.fitness.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryOfFitnessProgramDto {
    private long id;
    @NotEmpty
    @Size(min = 3, max = 80)
    private String shortName;
    @NotEmpty
    @Size(min = 20)
    private String description;
    private Set <FitnessProgramDto> fitnessPrograms;
}
