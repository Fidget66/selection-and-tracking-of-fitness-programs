package com.makul.fitness.service.api;

import com.makul.fitness.model.Bookmark;
import com.makul.fitness.model.ExerciseSchedule;
import java.util.Set;

public interface BookmarkService {
    Bookmark createNewSchedule (long bookmarkId, String [] days);
    Bookmark completionMark (long bookmarkId, Set<ExerciseSchedule> scheduleList);
    Bookmark readById(long id);
}
