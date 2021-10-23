package com.makul.fitness.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String programShortName;
    private LocalDate exerciseDate;
    private boolean isComplited;
    @ManyToOne
    @JoinColumn (name = "active_program_id")
    @JsonIgnore
    @ToString.Exclude
    private ActiveProgram activeProgram;
}
