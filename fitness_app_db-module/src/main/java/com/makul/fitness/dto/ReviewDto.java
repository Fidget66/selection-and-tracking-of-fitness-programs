package com.makul.fitness.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Review entity")
public class ReviewDto {
    private UUID id;
    @ApiModelProperty(value = "Author's name",
            name = "Name",
            example = "Olya")
    private String authorName;
    @ApiModelProperty(value = "Author's id",
            name = "id",
            example = "2")
    private UUID authorId;
    @ApiModelProperty(value = "Message text",
            name = "text",
            example = "This is a swagger API message")
    private String text;
}
