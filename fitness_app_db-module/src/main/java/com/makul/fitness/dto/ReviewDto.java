package com.makul.fitness.dto;

import com.makul.fitness.model.FitnessProgram;
import com.makul.fitness.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private long id;
    @NotEmpty
    @Size(min = 3, max = 1000)
    private String text;
    @NotEmpty
    private User user;
    @NotEmpty
    private FitnessProgram fitnessProgram;
}
