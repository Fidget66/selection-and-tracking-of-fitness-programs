package com.makul.fitness.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkDto {
    private long id;
    private boolean isComplited;
    private List<ExerciseScheduleDto> scheduleList;
}
