package com.makul.fitness.service;

import com.makul.fitness.exceptions.ActiveProgramIsPresentException;
import com.makul.fitness.exceptions.BookmarkIsPresentException;
import com.makul.fitness.exceptions.ReviewIsPresentException;
import com.makul.fitness.exceptions.ScheduleIsPresentException;
import com.makul.fitness.model.*;
import com.makul.fitness.service.api.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ClientBusinessServiceImpl implements ClientBusinessService {

    private final UsersService usersService;
    private final BookmarkService bookmarkService;
    private final FitnessProgramService fitnessProgramService;
    private final ActiveProgramService activeProgramService;
    private final ExerciseScheduleService exerciseScheduleService;
    private final ReviewService reviewService;

    public ClientBusinessServiceImpl(UsersService usersService, BookmarkService bookmarkService,
                                     FitnessProgramService fitnessProgramService, ActiveProgramService activeProgramService,
                                     ExerciseScheduleService exerciseScheduleService, ReviewService reviewService) {
        this.usersService = usersService;
        this.bookmarkService = bookmarkService;
        this.fitnessProgramService = fitnessProgramService;
        this.activeProgramService = activeProgramService;
        this.exerciseScheduleService = exerciseScheduleService;
        this.reviewService = reviewService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Bookmark addBookmark(long userId, long fitnessProgramId) {
        Users user=usersService.read(userId);
        if (Objects.isNull(user.getBookmarks()) || user.getBookmarks().size()==0) {
            Bookmark bookmark = new Bookmark();
            bookmark.setUser(user);
            bookmark.setFitnessProgram(fitnessProgramService.read(fitnessProgramId));
            return bookmarkService.create(bookmark);
        } else{
            int counter = 0;
            for (Bookmark bookmark : user.getBookmarks()) {
                if (bookmark.getFitnessProgram().getId() == fitnessProgramId) counter++;
            }
            if (counter==0)   {
                Bookmark bookmark = new Bookmark();
                bookmark.setUser(user);
                bookmark.setFitnessProgram(fitnessProgramService.read(fitnessProgramId));
                return bookmarkService.create(bookmark);
            } else throw new BookmarkIsPresentException();
        }
    }

    @Override
    public List<Bookmark> viewBookmarks(long userId) {
        Users user = usersService.read(userId);
        return user.getBookmarks();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActiveProgram addActiveProgram(long userId,long fitnessProgramId){
        Users user= usersService.read(userId);
        FitnessProgram fitnessProgram= fitnessProgramService.read(fitnessProgramId);
        ActiveProgram activeProgram=ActiveProgram.builder().fitnessProgram(fitnessProgram).build();
        long restriction=0;
        if (Objects.nonNull(user.getActivePrograms()) && user.getActivePrograms().size()>0){
            restriction = user.getActivePrograms().stream()
                    .filter(activePrograms ->activePrograms.isComplited()==false)
                    .count();
            if (restriction==0){
                activeProgram.setUser(user);
                return activeProgramService.create(activeProgram);
            } else throw new ActiveProgramIsPresentException();
        }else{
            activeProgram.setUser(user);
            return activeProgramService.create(activeProgram);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActiveProgram createSchedule(ActiveProgram inputActiveProgram){
        ActiveProgram activeProgram=activeProgramService.read(inputActiveProgram.getId());
        activeProgram.setDays(inputActiveProgram.getDays());
        String[] days =inputActiveProgram.getDays().split(";");
        createNewScheduleLIst(activeProgram,days);
        exerciseScheduleService.createAll(activeProgram.getScheduleList());
        return update(activeProgram);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Review addReview(long fitnessProgramId, Review review) {
        if (Objects.nonNull(reviewService.readReviewByUserIdFitnessId(review.getAuthorId(),fitnessProgramId))) throw
            new ReviewIsPresentException();
        review.setAuthorName(usersService.read(review.getAuthorId()).getFirstName());
        review.setFitnessProgram(fitnessProgramService.read(fitnessProgramId));
        return reviewService.create(review);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ExerciseSchedule updateExercise(long exerciseId) {
        ExerciseSchedule exercise = exerciseScheduleService.update(exerciseId);
        isAllExerciseComplited(exerciseScheduleService.read(exerciseId).getActiveProgram().getId());
        return exercise;
    }

    @Override
    @Transactional(rollbackFor = Exception.class,isolation = Isolation.REPEATABLE_READ)
    public ActiveProgram update(ActiveProgram inputActiveProgram) {
        ActiveProgram activeProgram = activeProgramService.read(inputActiveProgram.getId());
        activeProgram.setComplited(inputActiveProgram.isComplited());
        if (inputActiveProgram.getDays().length()>6) activeProgram.setDays(inputActiveProgram.getDays());
        if (Objects.nonNull(inputActiveProgram.getScheduleList()) && inputActiveProgram.getScheduleList().size()>0)
            activeProgram.setScheduleList(inputActiveProgram.getScheduleList());
        return activeProgramService.create(activeProgram);
    }

    private ActiveProgram createNewScheduleLIst(ActiveProgram activeProgram, String[] days) {
        if (Objects.nonNull(activeProgram.getScheduleList()) && activeProgram.getScheduleList().size()>0)
            throw new ScheduleIsPresentException();
        activeProgram.setScheduleList(fillNewSchedule(activeProgram, days));
        return activeProgram;
    }

    private List<ExerciseSchedule> fillNewSchedule(ActiveProgram activeProgram, String[] days){
        List<ExerciseSchedule> scheduleList = new ArrayList<>();
        LocalDate currentDate= LocalDate.now();
        int exercisesCounter=0;
        while (exercisesCounter < activeProgram.getFitnessProgram().getDuration()){
            for (String day : days) {
                if (currentDate.getDayOfWeek().toString().equalsIgnoreCase(day)) {
                    scheduleList.add(createNewExerciseSchedule(currentDate, activeProgram));
                    exercisesCounter++;
                }
            }
            currentDate=currentDate.plusDays(1);
        }
        return scheduleList;
    }

    private ExerciseSchedule createNewExerciseSchedule(LocalDate dateOfExercise, ActiveProgram activeProgram){
        return ExerciseSchedule
                .builder()
                .programShortName(activeProgram.getFitnessProgram().getShortName())
                .exerciseDate(dateOfExercise)
                .activeProgram(activeProgram)
                .build();
    }

    private void isAllExerciseComplited(long activeProgramId){
        int counter =0;
        ActiveProgram activeProgram = activeProgramService.read(activeProgramId);
        for (ExerciseSchedule exercise:activeProgram.getScheduleList()) {
            if (exercise.isComplited()) counter++;
        }
        if (counter==activeProgram.getScheduleList().size()) activeProgram.setComplited(true);
        update(activeProgram);
    }
}
