package com.makul.fitness.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.makul.fitness.model.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {
    @Min(value = 0)
    private long id;
    @Size(min = 6, message = "Минимальная длина логина 6 символов")
    private String login;
    @Size(min = 6, message = "Минимальная длина пароля 6 символов")
    private String password;
    @Size(min = 3, max = 80, message = "Длина имени не менее 3 и не более 80 символов")
    private String firstName;
    @Size(min = 2, message = "Длина фамилии не менее 2 символов")
    private String lastName;
    @NotNull(message = "Введите дату рождения")
    @DateTimeFormat( pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE_TIME )
    private LocalDate dateOfBirth;
    @Size(max = 1, message = "Длина не более 1го символа")
    @Pattern(regexp = "m|f",message = "Значение данного поля m или f")
    private String sex;
    @Min(value = 40, message = "Введенный вес должен быть не менее 40 кг")
    @Max(value = 200, message = "Введенный вес должен быть не более 200 кг")
    private int weight;
    @NotEmpty (message = "Адрес электронной почты не должен быть пустым")
    @Email(message = "Введите корректный адрес электронной почты")
    private String email;
    private List<ActiveProgramDto> activePrograms;
    private List<BookmarkDto> bookmarksDto;
    private List<Roles> roles;
}
