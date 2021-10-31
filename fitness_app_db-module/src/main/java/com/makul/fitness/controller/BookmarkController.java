package com.makul.fitness.controller;

import com.makul.fitness.service.api.BookmarkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Controller for delete bookmark by id")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @DeleteMapping("/bookmark/{id}")
    @ApiOperation(value = "Delete bookmark by id")
    public void deleteBookmark (@ApiParam(defaultValue = "4") @PathVariable("id") long bookmarkId){
        bookmarkService.delete(bookmarkId);
    }
}
