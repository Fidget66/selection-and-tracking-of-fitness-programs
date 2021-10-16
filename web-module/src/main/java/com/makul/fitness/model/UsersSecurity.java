package com.makul.fitness.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersSecurity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String password;
    private String login;
    private boolean isAccountNonLocked;
    private long userId;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_security_roles",
            joinColumns = { @JoinColumn(name = "users_security_id") },
            inverseJoinColumns = { @JoinColumn(name = "roles_id") }
    )
    private Set<Roles> role;
}
