package com.makul.fitness.controller;

import com.makul.fitness.service.api.BookmarkService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookmarkController {

    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @DeleteMapping("/bookmark/{id}")
    public void deleteBookmark(@PathVariable("id") long id){
        bookmarkService.delete(id);
    }
}
