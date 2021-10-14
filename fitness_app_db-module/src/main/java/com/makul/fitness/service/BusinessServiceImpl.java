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
import java.util.Objects;
import java.util.Set;

@Service
public class BusinessServiceImpl implements BusinessService {

    private final UsersService usersService;
    private final BookmarkService bookmarkService;
    private final FitnessProgramService fitnessProgramService;
    private final ActiveProgramService activeProgramService;
    private final ExerciseScheduleService exerciseScheduleService;
    public final CategoryOfFitnessProgramService categoryService;

    public BusinessServiceImpl(UsersService usersService, BookmarkService bookmarkService,
                               FitnessProgramService fitnessProgramService, ActiveProgramService activeProgramService,
                               ExerciseScheduleService exerciseScheduleService,
                               CategoryOfFitnessProgramService categoryService) {
        this.usersService = usersService;
        this.bookmarkService = bookmarkService;
        this.fitnessProgramService = fitnessProgramService;
        this.activeProgramService = activeProgramService;
        this.exerciseScheduleService = exerciseScheduleService;
        this.categoryService = categoryService;
    }

    @Override
    @Transactional
    public Users addBookmark(long userId, FitnessProgram fitnessProgram) {
        Users user=usersService.read(userId);
        for (Bookmark bookmark:user.getBookmarks()) {
            if (bookmark.getFitnessProgram().getId()==fitnessProgram.getId())
                throw new BookmarkIsPresentException();
        }
        Bookmark bookmark = new Bookmark();
        bookmark.setUser(user);
        bookmark.setFitnessProgram(fitnessProgramService.read(fitnessProgram.getId()));
        bookmarkService.create(bookmark);
        return user;
    }

    @Override
    @Transactional
    public void addActiveProgram(long userId,FitnessProgram fitnessProgram){

        String[] days = {"MONDAY","TUESDAY","SATURDAY"};
        Users user= usersService.read(userId);
        ActiveProgram activeProgram=ActiveProgram.builder().fitnessProgram(fitnessProgram).build();
        byte restriction=0;
        byte k=1;
        if (user.getActivePrograms().size()>0){
            restriction=(byte) user.getActivePrograms().stream()
                    .filter(activePrograms ->activePrograms.isComplited()==false)
                    .count();
            System.out.println(restriction + "attempt" + k++);
            if (restriction==0){
                createNewActiveProgram(activeProgram,days);
                activeProgram.setUser(user);
                activeProgramService.create(activeProgram);
            } else if (restriction > 0) throw new ActiveProgramIsPresentException();
        }else{
            createNewActiveProgram(activeProgram,days);
            activeProgram.setUser(user);
            activeProgramService.create(activeProgram);
        }
    }

    @Override
    public void addFitnessProgram(long categoryId, FitnessProgram fitnessProgram) {
        CategoryOfFitnessProgram category = categoryService.read(categoryId);
        fitnessProgram.setCategory(category);
        fitnessProgramService.create(fitnessProgram);
    }

    @Override
    public Bookmark completionMark(long bookmarkId, Set<ExerciseSchedule> scheduleList) {
        return null;
    }

    private Bookmark createNemBookmark(FitnessProgram fitnessProgram){
        Bookmark bookmark = new Bookmark();
        bookmark.setFitnessProgram(fitnessProgram);
        return bookmark;
    }

    private ActiveProgram createNewActiveProgram(ActiveProgram activeProgram, String[] days) {
        if (Objects.nonNull(activeProgram.getScheduleList())) throw new ScheduleIsPresentException();
        activeProgram.setScheduleList(fillNewSchedule(activeProgram, days));
        return activeProgramService.create(activeProgram);
    }

    private List<ExerciseSchedule> fillNewSchedule(ActiveProgram activeProgram, String[] days){
        List<ExerciseSchedule> scheduleList = new ArrayList<>();
        LocalDate currentDate= LocalDate.now();
        int exercisesCounter=0;
        while (exercisesCounter<activeProgram.getFitnessProgram().getDuration()){
            for (int i=0; i<days.length; i++){
                if (currentDate.getDayOfWeek().toString().equals(days[i])) {
                 scheduleList.add(createNewExerciseSchedule(currentDate,activeProgram));
                 exercisesCounter++;
                }
            }
            currentDate=currentDate.plusDays(1);
        }
        return exerciseScheduleService.createAll(scheduleList);
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
