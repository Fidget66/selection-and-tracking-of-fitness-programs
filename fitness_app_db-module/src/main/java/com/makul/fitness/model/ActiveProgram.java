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
public class ActiveProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private boolean isComplited;
    @OneToMany(mappedBy = "activeProgram",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ExerciseSchedule> scheduleList;
    @OneToOne
    @JoinColumn(name = "active_program_id")
    private FitnessProgram fitnessProgram;
    @ManyToOne
    private Users user;
}
