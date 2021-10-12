package com.makul.fitness.service;

import com.makul.fitness.dao.UsersDao;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.Bookmark;
import com.makul.fitness.model.Users;
import com.makul.fitness.service.api.BookmarkService;
import com.makul.fitness.service.api.FitnessProgramService;
import com.makul.fitness.service.api.RolesService;
import com.makul.fitness.service.api.UsersService;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

public class UsersServiceImpl implements UsersService {

    private final RolesService rolesService;
    private final UsersDao usersDao;
    private final BookmarkService bookmarkService;
    private final FitnessProgramService fitnessProgramService;

    public UsersServiceImpl(RolesService rolesService, UsersDao usersDao, BookmarkService bookmarkService,
                            FitnessProgramService fitnessProgramService) {
        this.rolesService = rolesService;
        this.usersDao = usersDao;
        this.bookmarkService = bookmarkService;
        this.fitnessProgramService = fitnessProgramService;
    }

    @Override
    @Transactional
    public Users create(Users user) {
        user.setRole(Set.of(rolesService.readRoleByName("CLIENT")));
        user.setAccountNonLocked(true);
        return usersDao.save(user);
    }

    @Override
    public Users readByLogin(String login) {
        return usersDao.findByLogin(login);
    }

    @Override
    @Transactional
    public Users addBookmark(long userId, long fitnessProgramId) {
        Users user=usersDao.findById(userId).orElseThrow(()->new NoEntityException("Users"));
        Set <Bookmark> bookmarks = user.getBookmarks();
        bookmarks.add(createNemBookmark(fitnessProgramId));
        user.setBookmarks(bookmarks);
        return usersDao.save(user);
    }

    @Override
    @Transactional
    public void deleteBookmark(long userId,long bookmarkId) {
        Users users = usersDao.findById(userId).orElseThrow(()->new NoEntityException("Users"));
        users.getBookmarks().remove(bookmarkService.readById(bookmarkId));
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

    private Bookmark createNemBookmark(long fitnessProgramId){
        Bookmark bookmark = new Bookmark();
        bookmark.setFitnessPrograms(fitnessProgramService.readById(fitnessProgramId));
        return bookmark;
    }
}
