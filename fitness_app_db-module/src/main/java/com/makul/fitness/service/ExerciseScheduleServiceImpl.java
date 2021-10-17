package com.makul.fitness.service;

import com.makul.fitness.dao.ExerciseScheduleDao;
import com.makul.fitness.exceptions.IncorrectDataException;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.ExerciseSchedule;
import com.makul.fitness.service.api.ExerciseScheduleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ExerciseScheduleServiceImpl implements ExerciseScheduleService {

    private final ExerciseScheduleDao exerciseScheduleDao;
    public ExerciseScheduleServiceImpl(ExerciseScheduleDao exerciseScheduleDao) {
        this.exerciseScheduleDao = exerciseScheduleDao;
    }

    @Override
    @Transactional
    public List<ExerciseSchedule> createAll(List<ExerciseSchedule> scheduleList) {
        return StreamSupport.stream(exerciseScheduleDao.saveAll(scheduleList).spliterator(),false)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ExerciseSchedule update(ExerciseSchedule exerciseSchedule) {
        ExerciseSchedule outputSchedule = read(exerciseSchedule.getId());
        outputSchedule.setComplited(exerciseSchedule.isComplited());
        return exerciseScheduleDao.save(outputSchedule);
    }

    @Override
    public ExerciseSchedule read(long id) {
        if (id<1) throw new IncorrectDataException("Exercise Schedule id");
        return exerciseScheduleDao.findById(id).orElseThrow(()->new NoEntityException("Exercise Schedule"));
    }
}