package com.makul.fitness.service.api;

import com.makul.fitness.dto.*;
import java.util.List;
import java.util.Set;

public interface ClientService {
    List<CategoryOfFitnessProgramDto> getCategoryOfFitnessProgram();
    List<FitnessProgramDto> getListFitnessProgram(long categoryId);
    FitnessProgramDto getFitnessProgram(long programId);
    List<FitnessProgramDto> getFitnessProgramWithRestrictions(FiltredDto filtredDto);
    void addToBookMarks(long fitnessProgramId);
    List<BookmarkDto> getBookmarks();
    void  deleteBookmark(long bookmarkId);
    List <ActiveProgramDto> getComplitedActivePrograms();
    void addReview(long fitnessProgramId, ReviewDto reviewDto);
    ActiveProgramDto readUserActiveProgram();
    void createSchedule(long activeProgramId, Set<String> days);
    List<ExerciseScheduleDto> getSchedule(long activeProgramId);
    void updateSchedule(long exerciseId);
    void addActiveProgram(long fitnessProgramId);
    List <ReviewDto> readReviews(long fitnessProgramId);
    List <String> getDaysOfWeek();
}
