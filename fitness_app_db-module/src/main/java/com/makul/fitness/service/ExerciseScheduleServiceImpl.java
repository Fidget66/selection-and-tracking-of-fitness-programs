package com.makul.fitness.service;

import com.makul.fitness.dao.ExerciseScheduleDao;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.ExerciseSchedule;
import com.makul.fitness.service.api.ExerciseScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExerciseScheduleServiceImpl implements ExerciseScheduleService {

    private final ExerciseScheduleDao exerciseScheduleDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ExerciseSchedule> createAll(List<ExerciseSchedule> scheduleList) {
        return exerciseScheduleDao.saveAll(scheduleList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ExerciseSchedule update(UUID exerciseId) {
        ExerciseSchedule exercise = read(exerciseId);
        exercise.setComplited(true);
        return exerciseScheduleDao.save(exercise);
    }

    @Override
    public ExerciseSchedule read(UUID id) {
        return exerciseScheduleDao.findById(id).orElseThrow(() -> new NoEntityException("Exercise Schedule Id="+id));
    }
}
