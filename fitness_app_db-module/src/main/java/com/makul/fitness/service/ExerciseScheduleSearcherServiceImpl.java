package com.makul.fitness.service;

import com.makul.fitness.dao.ExerciseScheduleSearcherDao;
import com.makul.fitness.model.ExerciseSchedule;
import com.makul.fitness.service.api.ExerciseScheduleSearcherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExerciseScheduleSearcherServiceImpl implements ExerciseScheduleSearcherService {

    private final ExerciseScheduleSearcherDao searcherDao;

    @Override
    public List <ExerciseSchedule> readExerciseByDate(LocalDate currentDate) {
        return searcherDao.findByExerciseDate(currentDate);
    }

    @Override
    public Page <ExerciseSchedule> readExerciseByActiveProgramId(UUID activeProgramId, int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber, size);
        return searcherDao.findByActiveProgram_IdOrderByExerciseDateAsc(activeProgramId, pageable);
    }
}
