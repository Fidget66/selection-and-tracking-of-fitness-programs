package com.makul.fitness.service;

import com.makul.fitness.dao.BookmarkDao;
import com.makul.fitness.dao.UsersDao;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.exceptions.ScheduleIsPresentException;
import com.makul.fitness.model.Bookmark;
import com.makul.fitness.model.ExerciseSchedule;
import com.makul.fitness.model.Users;
import com.makul.fitness.service.api.BookmarkService;
import com.makul.fitness.service.api.BusinessService;
import com.makul.fitness.service.api.FitnessProgramService;
import com.makul.fitness.service.api.UsersService;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class BusinessServiceImpl implements BusinessService {

    private final UsersService usersService;
    private final BookmarkService bookmarkService;
    private final FitnessProgramService fitnessProgramService;

    public BusinessServiceImpl(UsersService usersService, BookmarkService bookmarkService,
                               FitnessProgramService fitnessProgramService) {
        this.usersService = usersService;
        this.bookmarkService = bookmarkService;
        this.fitnessProgramService = fitnessProgramService;
    }

    @Override
    @Transactional
    public Users addBookmark(long userId, long fitnessProgramId) {
        Users user=usersService.read(userId);
        Set<Bookmark> bookmarks = user.getBookmarks();
        bookmarks.add(bookmarkService.create(createNemBookmark(fitnessProgramId)));
        user.setBookmarks(bookmarks);
        return usersService.update(user);
    }

    @Override
    @Transactional
    public void deleteBookmark(long userId,long bookmarkId) {
        Users user = usersService.read(userId);
        user.getBookmarks().remove(bookmarkService.read(bookmarkId));
        usersService.create(user);
    }

    @Override
    public Set<Bookmark> viewAllComplitedBookmarks(long userId) {
        Users user = usersDao.findById(userId).orElseThrow(()->new NoEntityException("Users"));
        Set <Bookmark> bookmarks = user.getBookmarks()
                .stream()
                .filter(bookmark -> bookmark.isComplited()==true)
                .collect(Collectors.toSet());
        return bookmarks;
    }

    @Override
    public Set<Bookmark> viewAllUncomplitedBookmarks(long userId) {
        Users user = usersDao.findById(userId).orElseThrow(()->new NoEntityException("Users"));
        Set <Bookmark> bookmarks = user.getBookmarks()
                .stream()
                .filter(bookmark -> bookmark.isComplited()==false)
                .collect(Collectors.toSet());
        return bookmarks;
    }

    @Override
    public Bookmark createNewSchedule(long bookmarkId, String[] days) {
        Bookmark bookmark = bookmarkDao.findById(bookmarkId).orElseThrow(()->new NoEntityException("Bookmark"));
        if (Objects.nonNull(bookmark.getScheduleList())) throw new ScheduleIsPresentException();
        fillNewSchedule(String[] days);
        return null;
    }

    @Override
    public Bookmark completionMark(long bookmarkId, Set<ExerciseSchedule> scheduleList) {
        return null;
    }

    private Bookmark createNemBookmark(long fitnessProgramId){
        Bookmark bookmark = new Bookmark();
        bookmark.setFitnessProgram(fitnessProgramService.read(fitnessProgramId));
        return bookmark;
    }

    private Set<ExerciseSchedule> fillNewSchedule(String[] days){
        Set<ExerciseSchedule> scheduleList;
        Date currentDate= Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        return scheduleList;
    }
}
