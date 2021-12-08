package com.makul.fitness.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Active program entity")
public class ActiveProgramDto {
    private UUID id;
    @ApiModelProperty(value = "Selected workout days",
            name = "days",
            example = "Monday;Friday")
    private String days;
    private List<ExerciseScheduleDto> scheduleList;
    private FitnessProgramDto fitnessProgram;
}
