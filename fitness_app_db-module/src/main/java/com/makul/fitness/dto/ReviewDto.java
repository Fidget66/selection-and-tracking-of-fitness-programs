package com.makul.fitness.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Review entity")
public class ReviewDto {
    private long id;
    @ApiModelProperty(value = "Author's name",
            name = "Name",
            dataType = "String",
            example = "Olya")
    private String authorName;
    @ApiModelProperty(value = "Author's id",
            name = "id",
            dataType = "long",
            example = "2")
    @Min(value = 1)
    private long authorId;
    @ApiModelProperty(value = "Message text",
            name = "text",
            dataType = "String",
            example = "This is a swagger API message")
    @Size(min = 3, max = 1000, message = "Длина не менее 5 и не более 1000 символов")
    private String text;
}
