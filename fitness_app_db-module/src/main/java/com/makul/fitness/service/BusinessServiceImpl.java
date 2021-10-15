package com.makul.fitness.service;

import com.makul.fitness.exceptions.ActiveProgramIsPresentException;
import com.makul.fitness.exceptions.BookmarkIsPresentException;
import com.makul.fitness.exceptions.ScheduleIsPresentException;
import com.makul.fitness.model.*;
import com.makul.fitness.service.api.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    @Transactional
    public Bookmark addBookmark(long userId, long fitnessProgramId) {
        Users user=usersService.read(userId);
        for (Bookmark bookmark:user.getBookmarks()) {
            if (bookmark.getFitnessProgram().getId()==fitnessProgramId)
                throw new BookmarkIsPresentException();
        }
        Bookmark bookmark = new Bookmark();
        bookmark.setUser(user);
        bookmark.setFitnessProgram(fitnessProgramService.read(fitnessProgramId));
        return bookmarkService.create(bookmark);
    }

    @Override
    @Transactional
    public ActiveProgram addActiveProgram(long userId,long fitnessProgramId){
        Users user= usersService.read(userId);
        FitnessProgram fitnessProgram= fitnessProgramService.read(fitnessProgramId);
        ActiveProgram activeProgram=ActiveProgram.builder().fitnessProgram(fitnessProgram).build();
        byte restriction=0;
        if (user.getActivePrograms().size()>0){
            restriction=(byte) user.getActivePrograms().stream()
                    .filter(activePrograms ->activePrograms.isComplited()==false)
                    .count();
            if (restriction==0){
                activeProgram.setUser(user);
                return activeProgramService.create(activeProgram);
            } else if (restriction > 0) throw new ActiveProgramIsPresentException();
        }else{
            activeProgram.setUser(user);
            return activeProgramService.create(activeProgram);
        }
        return null;
    }

    @Override
    @Transactional
    public ActiveProgram createSchedule(ActiveProgram inputActiveProgram){
        ActiveProgram activeProgram=activeProgramService.read(inputActiveProgram.getId());
        String[] days =inputActiveProgram.getDays().split(";");// {"MONDAY","TUESDAY","SATURDAY"};
        createNewScheduleLIst(activeProgram,days);
        exerciseScheduleService.createAll(activeProgram.getScheduleList());
        return activeProgramService.update(activeProgram);
    }

    @Override
    public Review addReview(long userId, long fitnessProgramId, Review review) {
        review.setAuthorId(userId);
        review.setFitnessProgram(fitnessProgramService.read(fitnessProgramId));
        return reviewService.create(review);
    }

    @Override
    public FitnessProgram addFitnessProgram(long categoryId, FitnessProgram fitnessProgram) {
        CategoryOfFitnessProgram category = categoryService.read(categoryId);
        fitnessProgram.setCategory(category);
        return fitnessProgramService.create(fitnessProgram);
    }

    private Bookmark createNemBookmark(FitnessProgram fitnessProgram){
        Bookmark bookmark = new Bookmark();
        bookmark.setFitnessProgram(fitnessProgram);
        return bookmark;
    }

    private ActiveProgram createNewScheduleLIst(ActiveProgram activeProgram, String[] days) {
        if (activeProgram.getScheduleList().size()>0) throw new ScheduleIsPresentException();
        activeProgram.setScheduleList(fillNewSchedule(activeProgram, days));
        return activeProgram;
    }

    private List<ExerciseSchedule> fillNewSchedule(ActiveProgram activeProgram, String[] days){
        List<ExerciseSchedule> scheduleList = new ArrayList<>();
        LocalDate currentDate= LocalDate.now();
        int exercisesCounter=0;
        while (exercisesCounter<activeProgram.getFitnessProgram().getDuration()){
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
                .exerciseDate(dateOfExercise)
                .activeProgram(activeProgram)
                .build();
        return exerciseSchedule;
    }
}
