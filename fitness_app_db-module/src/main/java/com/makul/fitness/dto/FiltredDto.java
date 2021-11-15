package com.makul.fitness.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiltredDto {
    @ApiModelProperty(value = "User id",
            name = "userId",
            dataType = "long",
            example = "1")
    private long userId;
    @ApiModelProperty(value = "Category of fitness program id",
            name = "categoryId",
            dataType = "long",
            example = "2")
    private long categoryId;
    @ApiModelProperty(value = "Fitness program duration",
            name = "duration",
            dataType = "int",
            example = "100")
    private int duration;
}
