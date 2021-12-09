package com.makul.fitness.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryOfFitnessProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String shortName;
    private String description;
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List <FitnessProgram> fitnessPrograms;
}
