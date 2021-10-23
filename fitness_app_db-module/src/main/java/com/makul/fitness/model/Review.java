package com.makul.fitness.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long authorId;
    private String authorName;
    private String text;
    @ManyToOne
    @JsonIgnore
    private FitnessProgram fitnessProgram;
}
