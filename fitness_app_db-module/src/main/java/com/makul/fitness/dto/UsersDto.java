package com.makul.fitness.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "user's entity")
public class UsersDto {
    private long id;
    @ApiModelProperty(value = "User first name",
            name = "firstName",
            dataType = "String",
            example = "UnknownUser")
    @Size(min = 3, max = 80, message = "Длина имени не менее 3 и не более 80 символов")
    private String firstName;
    @ApiModelProperty(value = "User last name",
            name = "lastName",
            dataType = "String",
            example = "UnknownUserLastName")
    @Size(min = 2, message = "Длина фамилии не менее 2 символов")
    private String lastName;
    @ApiModelProperty(value = "User's date of birth",
            name = "dateOfBirth",
            dataType = "LocalDate",
            example = "1984-03-03")
    @NotNull(message = "Введите дату рождения")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat( pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE_TIME )
    private LocalDate dateOfBirth;
    @ApiModelProperty(value = "User's sex",
            name = "sex",
            dataType = "char",
            example = "m")
    @Size(max = 1, message = "Длина не более 1го символа")
    @Pattern(regexp = "m|f",message = "Значение данного поля m или f")
    private String sex;
    @ApiModelProperty(value = "User's weight",
            name = "weight",
            dataType = "int",
            example = "77")
    @Min(value = 40, message = "Введенный вес должен быть не менее 40 кг")
    @Max(value = 200, message = "Введенный вес должен быть не более 200 кг")
    private int weight;
    @ApiModelProperty(value = "Email address",
            name = "email",
            dataType = "String",
            example = "someOne@google.com")
    @NotEmpty (message = "Адрес электронной почты не должен быть пустым")
    @Email(message = "Введите корректный адрес электронной почты")
    private String email;
    private List<ActiveProgramDto> activePrograms;
    private List<BookmarkDto> bookmarksDto;
}
