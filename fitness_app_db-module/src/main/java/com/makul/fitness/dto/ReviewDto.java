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
    @Min(value = 1)
    private long authorId;
    @Size(min = 3, max = 1000, message = "Длина не менее 5 и не более 1000 символов")
    private String text;
}
