package com.makul.fitness.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveProgramDto {
    private long id;
    @NotEmpty
    @Size(min = 6,max = 9)
    private String days;
    private List<ExerciseScheduleDto> scheduleList;
    @NotEmpty
    private FitnessProgramDto fitnessProgram;
}
