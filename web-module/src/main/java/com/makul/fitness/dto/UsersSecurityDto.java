package com.makul.fitness.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersSecurityDto {
    private long id;
    @NotEmpty
    @Size(min = 6)
    private String password;
    @NotEmpty
    @Size(min = 3)
    private String login;
    @NotEmpty
    @Size(min = 3)
    private Set<RolesDto> role;
}
