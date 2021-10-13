package com.makul.fitness.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkDto {
    private long id;
    @NotEmpty
    FitnessProgramDto fitnessProgram;
}
