package com.makul.fitness.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseScheduleDto {
    private long id;
    @NotEmpty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    //@DateTimeFormat( pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", iso = DateTimeFormat.ISO.DATE_TIME )
    private LocalDate exerciseDate;
    private boolean isComplited;
}
