package com.makul.fitness.service.api;

import com.makul.fitness.model.ActiveProgram;
import com.makul.fitness.model.Bookmark;
import com.makul.fitness.model.ExerciseSchedule;
import com.makul.fitness.model.Review;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ClientBusinessService {
    Bookmark addBookmark(UUID userId, UUID fitnessProgramId);
    Page <Bookmark> viewBookmarks(UUID userId, int pageNumber, int size);
    ActiveProgram addActiveProgram(UUID userId, UUID fitnessProgramId);
    ActiveProgram createSchedule(ActiveProgram activeProgram);
    Review addReview(UUID fitnessProgramId, Review review);
    ExerciseSchedule updateExercise(UUID exerciseId);
}
