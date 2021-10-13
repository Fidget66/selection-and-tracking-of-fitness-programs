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
    private String shortName;
    private short duration;
    private byte ageRestriction;
    private short weightRestriction;
    private String sexRestriction;
    private byte exercisePerWeek;
    private String description;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "fitness_programm_id")
    private List <Review> reviews;
    @ManyToOne
    private CategoryOfFitnessProgram category;
}
