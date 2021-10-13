package com.makul.fitness.service.api;

import com.makul.fitness.model.Bookmark;
import com.makul.fitness.model.ExerciseSchedule;
import com.makul.fitness.model.Users;

import java.util.Set;

public interface BusinessService {
    Users addBookmark(long userId, long fitnessProgramId);
    void deleteBookmark(long userId,long bookmarkId);
    Set<Bookmark> viewAllComplitedBookmarks(long userId);
    Set<Bookmark> viewAllUncomplitedBookmarks(long userId);
    Bookmark createNewSchedule (long bookmarkId, String [] days);
    Bookmark completionMark (long bookmarkId, Set<ExerciseSchedule> scheduleList);
}
