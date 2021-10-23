package com.makul.fitness.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private long id;
    private String authorName;
    @NotEmpty
    @Min(1)
    private long authorId;
    @NotEmpty
    @Size(min = 3, max = 1000)
    private String text;
}
