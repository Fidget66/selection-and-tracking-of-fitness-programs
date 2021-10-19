package com.makul.fitness.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String sex;
    private int weight;
    @Column(unique = true)
    private String email;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private List<ActiveProgram> activePrograms;
    @OneToMany (mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Bookmark> bookmarks;
}
