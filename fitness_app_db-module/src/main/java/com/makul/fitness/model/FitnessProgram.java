package com.makul.fitness.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FitnessProgram{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String shortName;
    private int duration;
    private int ageRestriction;
    private int weightRestriction;
    private String sexRestriction;
    private int exercisePerWeek;
    private String description;
    @OneToMany(mappedBy = "fitnessProgram",cascade = CascadeType.ALL)
    private List <Review> reviews;
    @ManyToOne
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private CategoryOfFitnessProgram category;
}
