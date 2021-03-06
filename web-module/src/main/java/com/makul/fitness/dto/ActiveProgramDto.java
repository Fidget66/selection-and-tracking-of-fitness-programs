package com.makul.fitness.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveProgramDto {
    @Min(value = 0)
    private long id;
    private String days;
    private List<ExerciseScheduleDto> scheduleList;
    @NotEmpty
    private FitnessProgramDto fitnessProgram;
}
