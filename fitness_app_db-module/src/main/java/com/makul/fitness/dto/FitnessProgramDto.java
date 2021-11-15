package com.makul.fitness.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Fitness program entity")
public class FitnessProgramDto {
    private long id;
    @ApiModelProperty(value = "Fitness program short name",
            name = "shortName",
            dataType = "String",
            example = "Cycle training")
    private String shortName;
    @ApiModelProperty(value = "Duration of the fitness program",
            name = "duration",
            dataType = "int",
            example = "25")
    private int duration;
    @ApiModelProperty(value = "Age restriction of the fitness program",
            name = "ageRestriction",
            dataType = "int",
            example = "75")
    private int ageRestriction;
    @ApiModelProperty(value = "Weight restriction of the fitness program",
            name = "weightRestriction",
            dataType = "int",
            example = "95")
    private int weightRestriction;
    @ApiModelProperty(value = "Sex restriction of the fitness program",
            name = "sexRestriction",
            dataType = "String",
            example = "m")
    private String sexRestriction;
    @ApiModelProperty(value = "Number of exercises per week",
            name = "exercisePerWeek",
            dataType = "int",
            example = "2")
    private int exercisePerWeek;
    @ApiModelProperty(value = "Description of the fitness program",
            name = "duration",
            dataType = "String",
            example = "Description of the fitness program, Description of the fitness program")
    private String description;
    private List <ReviewDto> reviews;
}
