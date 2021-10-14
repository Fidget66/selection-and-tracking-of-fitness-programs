package com.makul.fitness.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActiveProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private boolean isComplited;
    private String days;
    @OneToMany(mappedBy = "activeProgram",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ExerciseSchedule> scheduleList;
    @OneToOne
    @JoinColumn(name = "fitness_program_id")
    private FitnessProgram fitnessProgram;
    @ManyToOne
    @JsonIgnore
    private Users user;
}
