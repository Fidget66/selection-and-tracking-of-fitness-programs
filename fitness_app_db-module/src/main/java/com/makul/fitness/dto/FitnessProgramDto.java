package com.makul.fitness.dto;

import com.makul.fitness.model.ExerciseSchedule;
import com.makul.fitness.model.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FitnessProgramDto {
    private long id;
    @NotEmpty
    @Min(10)
    private short duration;
    @NotEmpty
    @Min(30)@Max(60)
    private byte ageRestriction;
    @NotEmpty
    @Min(50)
    private short weightRestriction;
    @Size(max = 1)
    private String sexRestriction;
    @NotEmpty
    @Min(1)@Max(3)
    private byte exercisePerWeek;
    @NotEmpty
    @Size(min = 40)
    private String description;
    private boolean isComplited;
    private List <Review> reviews;
    private ExerciseSchedule schedule;
}
