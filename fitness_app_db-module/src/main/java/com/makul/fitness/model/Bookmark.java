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
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private boolean isComplited;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "bookmarks_id")
    private List<ExerciseSchedule> scheduleList;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bookmark_id")
    private FitnessProgram fitnessPrograms;
}
