package com.makul.fitness.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FitnessProgram{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private short duration;
    private byte ageRestriction;
    private short weightRestriction;
    private String sexRestriction;
    private byte exercisePerWeek;
    private String description;
    private boolean isComplited;
    @OneToMany
    private List <Review> reviews;
    @ManyToOne
    private CategoryOfFitnessProgram category;
    @OneToMany // надо проверить необходимость связи
    private List<ExerciseSchedule> scheduleList;
}
