package com.makul.fitness.service;

import com.makul.fitness.exceptions.ActiveProgramIsPresentException;
import com.makul.fitness.exceptions.BookmarkIsPresentException;
import com.makul.fitness.exceptions.ReviewIsPresentException;
import com.makul.fitness.exceptions.ScheduleIsPresentException;
import com.makul.fitness.model.*;
import com.makul.fitness.service.api.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BusinessServiceImpl implements BusinessService {

    private final UsersService usersService;
    private final BookmarkService bookmarkService;
    private final FitnessProgramService fitnessProgramService;
    private final ActiveProgramService activeProgramService;
    private final ExerciseScheduleService exerciseScheduleService;
    private final CategoryOfFitnessProgramService categoryService;
    private final ReviewService reviewService;

    public BusinessServiceImpl(UsersService usersService, BookmarkService bookmarkService,
                               FitnessProgramService fitnessProgramService, ActiveProgramService activeProgramService,
                               ExerciseScheduleService exerciseScheduleService,
                               CategoryOfFitnessProgramService categoryService, ReviewService reviewService) {
        this.usersService = usersService;
        this.bookmarkService = bookmarkService;
        this.fitnessProgramService = fitnessProgramService;
        this.activeProgramService = activeProgramService;
        this.exerciseScheduleService = exerciseScheduleService;
        this.categoryService = categoryService;
        this.reviewService = reviewService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Bookmark addBookmark(long userId, long fitnessProgramId) {
        Users user=usersService.read(userId);
        if (Objects.isNull(user.getBookmarks())) {
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
    public FitnessProgram addFitnessProgram(long categoryId, FitnessProgram fitnessProgram) {
        CategoryOfFitnessProgram category = categoryService.read(categoryId);
        fitnessProgram.setCategory(category);
        return fitnessProgramService.create(fitnessProgram);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActiveProgram createSchedule(ActiveProgram inputActiveProgram){
        ActiveProgram activeProgram=activeProgramService.read(inputActiveProgram.getId());
        activeProgram.setDays(inputActiveProgram.getDays());
        String[] days =inputActiveProgram.getDays().split(";");
        createNewScheduleLIst(activeProgram,days);
        exerciseScheduleService.createAll(activeProgram.getScheduleList());
        return activeProgramService.update(activeProgram);
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
            for (int i=0; i<days.length; i++){
                if (currentDate.getDayOfWeek().toString().equalsIgnoreCase(days[i])) {
                 scheduleList.add(createNewExerciseSchedule(currentDate,activeProgram));
                 exercisesCounter++;
                }
            }
            currentDate=currentDate.plusDays(1);
        }
        return scheduleList;
    }

    private ExerciseSchedule createNewExerciseSchedule(LocalDate dateOfExercise, ActiveProgram activeProgram){
        ExerciseSchedule exerciseSchedule = ExerciseSchedule
                .builder()
                .programShortName(activeProgram.getFitnessProgram().getShortName())
                .exerciseDate(dateOfExercise)
                .activeProgram(activeProgram)
                .build();
        return exerciseSchedule;
    }

    private void isAllExerciseComplited(long activeProgramId){
        int counter =0;
        ActiveProgram activeProgram = activeProgramService.read(activeProgramId);
        for (ExerciseSchedule exercise:activeProgram.getScheduleList()) {
            if (exercise.isComplited()) counter++;
        }
        if (counter==activeProgram.getScheduleList().size()) activeProgram.setComplited(true);
        activeProgramService.update(activeProgram);
    }
}
