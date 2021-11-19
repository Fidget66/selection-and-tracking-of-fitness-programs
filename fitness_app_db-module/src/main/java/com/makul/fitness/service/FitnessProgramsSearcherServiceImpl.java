package com.makul.fitness.service;

import com.makul.fitness.dao.FitnessProgramsSearcherDao;
import com.makul.fitness.model.FitnessProgram;
import com.makul.fitness.service.api.FitnessProgramsSearcherService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@AllArgsConstructor
public class FitnessProgramsSearcherServiceImpl implements FitnessProgramsSearcherService {

    private final FitnessProgramsSearcherDao fitnessSearcherDao;
    private final UsersServiceImpl usersService;

    @Override
    public List<FitnessProgram> readFitnessProgram(long categoryId) {
        return fitnessSearcherDao.findFitnessProgram(categoryId);
    }

    @Override
    public List<FitnessProgram> readFitnessProgramWithRestrictions(long userId, int duration , long categoryId) {
        LocalDate birthday = usersService.read(userId).getDateOfBirth();
        LocalDate currentDate = LocalDate.now();
        int userAge = Period.between(birthday, currentDate).getYears();
        return fitnessSearcherDao.findFitnessProgramWithRestrictions(userId, duration, categoryId, userAge);
    }
}
