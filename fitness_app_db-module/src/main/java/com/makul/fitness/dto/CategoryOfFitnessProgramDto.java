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
@ApiModel(description = "Category of fitness program entity")
public class CategoryOfFitnessProgramDto {
    private UUID id;
    @ApiModelProperty(value = "Category short name",
            name = "shortName",
            example = "New category of fitness ")
    private String shortName;
    @ApiModelProperty(value = "Fitness program category description",
            name = "description",
            example = "New category of fitness program description")
    private String description;
    private List <FitnessProgramDto> fitnessPrograms;
}
