package com.makul.fitness.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String lastName;
    private Date dateOfBirth;
    private String sex;
    private short weight;
    @Column(unique = true)
    private String email;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<ActiveProgram> activePrograms;
    @OneToMany (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Set<Bookmark> bookmarks;
    @OneToOne (mappedBy = "user")
    private UsersSecurity usersSecurity;
}
