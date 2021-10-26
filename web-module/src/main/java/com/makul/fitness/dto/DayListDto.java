package com.makul.fitness.dto;

import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Data
public class DayListDto {
    private Set<String> daysOfweek = new HashSet<>();
}
