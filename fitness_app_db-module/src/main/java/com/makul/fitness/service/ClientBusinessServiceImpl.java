package com.makul.fitness.service;

import com.makul.fitness.exceptions.*;
import com.makul.fitness.model.*;
import com.makul.fitness.service.api.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientBusinessServiceImpl implements ClientBusinessService {

    private final UsersService usersService;
    private final BookmarkService bookmarkService;
    private final FitnessProgramService fitnessProgramService;
    private final ActiveProgramService activeProgramService;
    private final ExerciseScheduleService exerciseScheduleService;
    private final ReviewService reviewService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Bookmark addBookmark(UUID userId, UUID fitnessProgramId) {
        Users user = usersService.read(userId);
        if (Objects.nonNull(user.getBookmarks()) && user.getBookmarks()
                .stream()
                .anyMatch(bookmark -> bookmark.getFitnessProgram().getId().equals(fitnessProgramId))) {
            throw new BusinessException("Такая закладка уже есть у Вас.");
        } else {
            Bookmark bookmark = new Bookmark();
            bookmark.setUser(user);
            bookmark.setFitnessProgram(fitnessProgramService.read(fitnessProgramId));
            return bookmarkService.create(bookmark);
        }
    }

    @Override
    public Page <Bookmark> viewBookmarks(UUID userId, int pageNumber, int size) {
        Users user = usersService.read(userId);
        Pageable pageable = PageRequest.of(pageNumber,size);
        return bookmarkService.readByUserId(userId, pageable);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActiveProgram addActiveProgram(UUID userId, UUID fitnessProgramId) {
        Users user = usersService.read(userId);
        FitnessProgram fitnessProgram = fitnessProgramService.read(fitnessProgramId);
        ActiveProgram activeProgram = ActiveProgram.builder().fitnessProgram(fitnessProgram).build();
        if (Objects.isNull(user.getActivePrograms()) || user.getActivePrograms().isEmpty() || user.getActivePrograms()
                .stream()
                .noneMatch(actProg -> !actProg.isComplited())) {
            activeProgram.setUser(user);
            return activeProgramService.create(activeProgram);
        }
        throw new BusinessException("У Вас есть незавершенные активные программы");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActiveProgram createSchedule(ActiveProgram inputActiveProgram) {
        ActiveProgram activeProgram = activeProgramService.read(inputActiveProgram.getId());
        activeProgram.setDays(inputActiveProgram.getDays());
        String[] days = inputActiveProgram.getDays().split(";");
        createNewScheduleLIst(activeProgram, days);
        exerciseScheduleService.createAll(activeProgram.getScheduleList());
        return activeProgramService.update(activeProgram);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Review addReview(UUID fitnessProgramId, Review review) {
        if (Objects.nonNull(reviewService.readReviewByUserIdFitnessId(review.getAuthorId(), fitnessProgramId))) throw
                new BusinessException("Вы уже оставили отзыв по данной программе");
        review.setAuthorName(usersService.read(review.getAuthorId()).getFirstName());
        review.setFitnessProgram(fitnessProgramService.read(fitnessProgramId));
        return reviewService.create(review);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ExerciseSchedule updateExercise(UUID exerciseId) {
        ExerciseSchedule exercise = exerciseScheduleService.update(exerciseId);
        updActiveProgramIfAllExerciseComplited(exerciseScheduleService.read(exerciseId).getActiveProgram().getId());
        return exercise;
    }

    private ActiveProgram createNewScheduleLIst(ActiveProgram activeProgram, String[] days) {
        if (Objects.nonNull(activeProgram.getScheduleList()) && activeProgram.getScheduleList().size() > 0)
            throw new BusinessException("Расписание для данной программы уже составлено!");
        activeProgram.setScheduleList(fillNewSchedule(activeProgram, days));
        return activeProgram;
    }

    private List<ExerciseSchedule> fillNewSchedule(ActiveProgram activeProgram, String[] days) {
        List<ExerciseSchedule> scheduleList = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        int exercisesCounter = 0;
        while (exercisesCounter < activeProgram.getFitnessProgram().getDuration()) {
            for (String day : days) {
                if (currentDate.getDayOfWeek().toString().equalsIgnoreCase(day)) {
                    scheduleList.add(createNewExerciseSchedule(currentDate, activeProgram));
                    exercisesCounter++;
                }
            }
            currentDate = currentDate.plusDays(1);
        }
        return scheduleList;
    }

    private ExerciseSchedule createNewExerciseSchedule(LocalDate dateOfExercise, ActiveProgram activeProgram) {
        return ExerciseSchedule
                .builder()
                .programShortName(activeProgram.getFitnessProgram().getShortName())
                .exerciseDate(dateOfExercise)
                .activeProgram(activeProgram)
                .build();
    }

    private void updActiveProgramIfAllExerciseComplited(UUID activeProgramId) {
        ActiveProgram activeProgram = activeProgramService.read(activeProgramId);
        if (activeProgram.getScheduleList()
                .stream()
                .filter(ExerciseSchedule::isComplited)
                .count() == activeProgram.getScheduleList().size()) {
            activeProgram.setComplited(true);
        }
        activeProgramService.update(activeProgram);
    }
}
