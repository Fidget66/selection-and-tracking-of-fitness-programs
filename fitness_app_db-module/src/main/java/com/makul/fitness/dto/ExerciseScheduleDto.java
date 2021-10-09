package com.makul.fitness.dto;

import com.makul.fitness.model.FitnessProgram;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseScheduleDto {
    private long id;
    @NotEmpty
    private Date exerciseDate;
    private boolean isComplited;
    // надо проверить необходимость связи
    @NotEmpty
    private FitnessProgram fitnessProgram;
}
