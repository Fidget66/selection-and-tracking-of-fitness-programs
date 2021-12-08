package com.makul.fitness.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseScheduleDto {
    private UUID id;
    @ApiModelProperty(value = "The short name of the program for the schedule",
            name = "programShortName",
            example = "Cycle training")
    private String programShortName;
    @ApiModelProperty(value = "Training date",
            name = "exerciseDate",
            example = "2021-10-30")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate exerciseDate;
    @ApiModelProperty(value = "Select true if this exercise is executed",
            name = "isComplited",
            example = "true")
    private boolean isComplited;
}
