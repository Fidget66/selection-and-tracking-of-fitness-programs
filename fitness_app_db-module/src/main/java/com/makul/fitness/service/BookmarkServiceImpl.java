package com.makul.fitness.service;

import com.makul.fitness.dao.BookmarkDao;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.Bookmark;
import com.makul.fitness.model.ExerciseSchedule;
import com.makul.fitness.service.api.BookmarkService;

import java.util.Set;

public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkDao bookmarkDao;

    public BookmarkServiceImpl(BookmarkDao bookmarkDao) {
        this.bookmarkDao = bookmarkDao;
    }

    @Override
    public Bookmark createNewSchedule(long bookmarkId, String[] days) {
        return null;
    }

    @Override
    public Bookmark completionMark(long bookmarkId, Set<ExerciseSchedule> scheduleList) {
        return null;
    }

    @Override
    public Bookmark readById(long id) {
        return bookmarkDao.findById(id).orElseThrow(()->new NoEntityException("Bookmark"));
    }
}
