package com.makul.fitness.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkDto {
    @Min(value = 0)
    private long id;
    @NotEmpty
    private FitnessProgramDto fitnessProgram;
}
