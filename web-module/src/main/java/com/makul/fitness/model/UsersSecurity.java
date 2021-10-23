package com.makul.fitness.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersSecurity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    @NotNull
    private String login;
    @NotNull
    private String password;
    private long userId;
    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = { @JoinColumn(name = "users_security_id") },
            inverseJoinColumns = { @JoinColumn(name = "roles_id") }
    )
    private List<Roles> role;
}
