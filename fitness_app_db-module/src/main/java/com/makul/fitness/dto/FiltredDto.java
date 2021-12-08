package com.makul.fitness.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiltredDto {
    @ApiModelProperty(value = "User id",
            name = "userId",
            example = "1")
    private UUID userId;
    @ApiModelProperty(value = "Category of fitness program id",
            name = "categoryId",
            example = "2")
    private UUID categoryId;
    @ApiModelProperty(value = "Fitness program duration",
            name = "duration",
            example = "100")
    private int duration;
}
