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
    private String password; // может выделить поля
    private String login;// в отдельный класс
    private String name;
    private String lastName;
    private Date dateOfBirth;
    private String sex;
    private short weight;
    @Column(unique = true)
    private String email;
    private boolean isAccountNonLocked;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<CategoryOfFitnessProgram> category;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = { @JoinColumn(name = "users_id") },
            inverseJoinColumns = { @JoinColumn(name = "roles_id") }
    )
    private Set<Roles> role;
    @OneToMany (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Set<Bookmark> bookmarks;
}
