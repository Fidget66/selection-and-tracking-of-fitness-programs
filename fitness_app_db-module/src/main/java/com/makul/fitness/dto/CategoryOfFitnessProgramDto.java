package com.makul.fitness.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Category of fitness program entity")
public class CategoryOfFitnessProgramDto {
    private long id;
    @ApiModelProperty(value = "Category short name",
            name = "shortName",
            dataType = "String",
            example = "New category of fitness ")
    private String shortName;
    @ApiModelProperty(value = "Fitness program category description",
            name = "description",
            dataType = "String",
            example = "New category of fitness program description")
    private String description;
    private List <FitnessProgramDto> fitnessPrograms;
}
