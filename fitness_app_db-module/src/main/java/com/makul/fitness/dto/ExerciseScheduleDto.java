package com.makul.fitness.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseScheduleDto {
    private long id;
    @ApiModelProperty(value = "The short name of the program for the schedule",
            name = "programShortName",
            dataType = "String",
            example = "Cycle training")
    @NotEmpty
    private String programShortName;
    @ApiModelProperty(value = "Training date",
            name = "exerciseDate",
            dataType = "LocalDate",
            example = "2021-10-30")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate exerciseDate;
    @ApiModelProperty(value = "Select true if this exercise is executed",
            name = "isComplited",
            dataType = "boolean",
            example = "true")
    private boolean isComplited;
}
