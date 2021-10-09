package com.makul.fitness.dto;

import com.makul.fitness.model.CategoryOfFitnessProgram;
import com.makul.fitness.model.ExerciseSchedule;
import com.makul.fitness.model.Review;
import com.makul.fitness.model.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private long id;
    @NotEmpty
    @Size(min = 3, max = 80)
    private String name;
    @NotEmpty
    @Size(min = 3, max = 80)
    private String lastName;
    @NotEmpty
    private Date dateOfBirth;
    @NotEmpty
    @Size(min = 1, max = 1)
    private String sex;
    @NotEmpty
    @Min(20)@Max(200)
    private short weight;
    @NotEmpty
    @Email
    private String email;
    @Size(max = 2)
    private Set<CategoryOfFitnessProgram> category;
    private List<ExerciseSchedule> exerciseSchedule;
    private Set <Roles> role;
    private List <Review> reviews;
}
