package com.makul.fitness.service.api;

import com.makul.fitness.model.Bookmark;
import com.makul.fitness.model.Users;

import java.util.Set;

public interface UsersService {
    Users create(Users user);
    Users readByLogin(String login);
    Users addBookmark(long userId, long fitnessProgramId);
    void deleteBookmark(long userId,long bookmarkId);
    Set<Bookmark> viewAllComplitedBookmarks(long userId);
    Set<Bookmark> viewAllUncomplitedBookmarks(long userId);
}
