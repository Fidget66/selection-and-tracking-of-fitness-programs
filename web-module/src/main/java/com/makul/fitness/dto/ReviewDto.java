package com.makul.fitness.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    @Min(value = 0)
    private long id;
    private String authorName;
    @Min(value = 1)
    private long authorId;
    private String text;
}
