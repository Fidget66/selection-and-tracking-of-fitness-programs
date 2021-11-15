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
    private String firstName;
    @ApiModelProperty(value = "User last name",
            name = "lastName",
            dataType = "String",
            example = "UnknownUserLastName")
    private String lastName;
    @ApiModelProperty(value = "User's date of birth",
            name = "dateOfBirth",
            dataType = "LocalDate",
            example = "1984-03-03")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    @ApiModelProperty(value = "User's sex",
            name = "sex",
            dataType = "char",
            example = "m")
    private String sex;
    @ApiModelProperty(value = "User's weight",
            name = "weight",
            dataType = "int",
            example = "77")
    private int weight;
    @ApiModelProperty(value = "Email address",
            name = "email",
            dataType = "String",
            example = "someOne@google.com")
    private String email;
    private List<ActiveProgramDto> activePrograms;
    private List<BookmarkDto> bookmarksDto;
}
