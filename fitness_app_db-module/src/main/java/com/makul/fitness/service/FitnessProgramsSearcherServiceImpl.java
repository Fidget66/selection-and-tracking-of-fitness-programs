package com.makul.fitness.service;

import com.makul.fitness.dao.FitnessProgramsSearcherDao;
import com.makul.fitness.model.FitnessProgram;
import com.makul.fitness.service.api.FitnessProgramsSearcherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FitnessProgramsSearcherServiceImpl implements FitnessProgramsSearcherService {

    private final FitnessProgramsSearcherDao fitnessSearcherDao;
    private final UsersServiceImpl usersService;

    @Override
    public Page <FitnessProgram> readFitnessProgram(UUID categoryId, int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber,size);
        log.info("Reading fitn progr with categoryId={}",categoryId);
        return fitnessSearcherDao.findFitnessProgram(categoryId,pageable);
    }

    @Override
    public Page <FitnessProgram> readFitnessProgramWithRestrictions(UUID userId, int duration , UUID categoryId,
                                                                    int pageNumber, int size) {
        LocalDate birthday = usersService.read(userId).getDateOfBirth();
        LocalDate currentDate = LocalDate.now();
        int userAge = Period.between(birthday, currentDate).getYears();
        Pageable pageable = PageRequest.of(pageNumber,size);
        log.info("Reading fitn progr with categoryId={} and userID={}",categoryId,userId);
        return fitnessSearcherDao.findFitnessProgramWithRestrictions(userId, duration, categoryId, userAge, pageable);
    }
}
