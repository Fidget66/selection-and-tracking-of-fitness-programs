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
    @Size(min = 5, message = "Минимальная длина 5 символов")
    private String shortName;
    @ApiModelProperty(value = "Duration of the fitness program",
            name = "duration",
            dataType = "int",
            example = "25")
    @Min(value = 10, message = "Минимальная продолжительность 10 занятий")
    private int duration;
    @ApiModelProperty(value = "Age restriction of the fitness program",
            name = "ageRestriction",
            dataType = "int",
            example = "75")
    @Min(value = 20, message = "Минимальный возраст 20 лет")
    @Max(value = 65, message = "Максимальный возраст 65 лет")
    private int ageRestriction;
    @ApiModelProperty(value = "Weight restriction of the fitness program",
            name = "weightRestriction",
            dataType = "int",
            example = "95")
    @Min(value = 50, message = "Минимальный вес 50 кг")
    @Max(value = 120, message = "Максимальный вес 120 кг")
    private int weightRestriction;
    @ApiModelProperty(value = "Sex restriction of the fitness program",
            name = "sexRestriction",
            dataType = "String",
            example = "m")
    @Size(max = 1, message = "Длина не более 1го символа")
    @Pattern(regexp = "m|f",message = "Значение данного поля m или f")
    private String sexRestriction;
    @ApiModelProperty(value = "Number of exercises per week",
            name = "exercisePerWeek",
            dataType = "int",
            example = "2")
    @Min(value = 1, message = "Минимальное количество занятий в неделю - 1")
    @Max(value = 4, message = "Максимальное количество занятий в неделю - 4")
    private int exercisePerWeek;
    @ApiModelProperty(value = "Description of the fitness program",
            name = "duration",
            dataType = "String",
            example = "Description of the fitness program, Description of the fitness program")
    @Size(min = 40, message = "Минимальная длина описания 40 символов")
    private String description;
    private List <ReviewDto> reviews;
}
