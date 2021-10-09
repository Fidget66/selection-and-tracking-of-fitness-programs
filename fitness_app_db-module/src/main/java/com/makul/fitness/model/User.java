package com.makul.fitness.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String lastName;
    private Date dateOfBirth;
    private String sex;
    private short weight;
    private String email;
    private boolean isActive;
    @ManyToMany
    private Set<CategoryOfFitnessProgram> category;
    @OneToMany
    private List <ExerciseSchedule> exerciseSchedule;
    @ManyToOne
    private Set <Roles> role;
    @OneToMany
    private List <Review> reviews;
}
