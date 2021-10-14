package com.makul.fitness.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {
    private long id;
    @NotEmpty
    @Size(min = 3, max = 80)
    private String firstName;
    @NotEmpty
    @Size(min = 3, max = 80)
    private String lastName;
    @NotEmpty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    //@DateTimeFormat( pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", iso = DateTimeFormat.ISO.DATE_TIME )
    private LocalDate dateOfBirth;
    @NotEmpty
    @Size(min = 1, max = 1)
    @Pattern(regexp = "[mf]")
    private String sex;
    @NotEmpty
    @Min(20)@Max(200)
    private short weight;
    @NotEmpty
    @Email
    private String email;
    private List<ActiveProgramDto> activePrograms;
    private List<BookmarkDto> bookmarksDto;
}
