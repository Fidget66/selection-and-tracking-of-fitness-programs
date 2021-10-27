package com.makul.fitness.service;

import com.makul.fitness.dao.ExerciseScheduleSearcherDao;
import com.makul.fitness.model.ExerciseSchedule;
import com.makul.fitness.service.api.ExerciseScheduleSearcherService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExerciseScheduleSearcherServiceImpl implements ExerciseScheduleSearcherService {

    private final ExerciseScheduleSearcherDao searcherDao;

    public ExerciseScheduleSearcherServiceImpl(ExerciseScheduleSearcherDao searcherDao) {
        this.searcherDao = searcherDao;
    }

    @Override
    public List<ExerciseSchedule> readExerciseByDate(LocalDate currentDate) {
        return searcherDao.findExerciseByDate(currentDate);
    }

    @Override
    public List<ExerciseSchedule> readExerciseByActiveProgramId(long activeProgramId) {
        return searcherDao.findExerciseByProgramId(activeProgramId);
    }
}
