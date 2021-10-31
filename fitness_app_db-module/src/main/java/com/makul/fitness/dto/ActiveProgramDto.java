package com.makul.fitness.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Active program entity")
public class ActiveProgramDto {
    @Min(value = 0)
    private long id;
    @ApiModelProperty(value = "Selected workout days",
            name = "days",
            dataType = "String",
            example = "Monday;Friday")
    private String days;
    private List<ExerciseScheduleDto> scheduleList;
    @NotEmpty
    private FitnessProgramDto fitnessProgram;
}
