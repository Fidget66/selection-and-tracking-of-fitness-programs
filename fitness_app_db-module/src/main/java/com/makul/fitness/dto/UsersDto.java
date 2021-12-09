package com.makul.fitness.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "user's entity")
public class UsersDto {
    private UUID id;
    @ApiModelProperty(value = "User first name",
            name = "firstName",
            example = "UnknownUser")
    private String firstName;
    @ApiModelProperty(value = "User last name",
            name = "lastName",
            example = "UnknownUserLastName")
    private String lastName;
    @ApiModelProperty(value = "User's date of birth",
            name = "dateOfBirth",
            example = "1984-03-03")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    @ApiModelProperty(value = "User's sex",
            name = "sex",
            example = "m")
    private String sex;
    @ApiModelProperty(value = "User's weight",
            name = "weight",
            example = "77")
    private int weight;
    @ApiModelProperty(value = "Email address",
            name = "email",
            example = "someOne@google.com")
    private String email;
    private List<ActiveProgramDto> activePrograms;
    private List<BookmarkDto> bookmarksDto;
}
