package com.makul.fitness.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
    @OneToMany(mappedBy = "fitnessProgram",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List <Review> reviews;
    @ManyToOne
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private CategoryOfFitnessProgram category;
}
