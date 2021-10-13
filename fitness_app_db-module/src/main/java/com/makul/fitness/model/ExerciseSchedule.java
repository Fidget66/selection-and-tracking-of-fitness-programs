package com.makul.fitness.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private LocalDate exerciseDate;
    private boolean isComplited;
    @ManyToOne
    @JoinColumn (name = "schedule_list_id")
    private ActiveProgram activeProgram;
}
