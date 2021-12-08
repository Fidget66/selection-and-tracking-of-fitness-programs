package com.makul.fitness.controller;

import com.makul.fitness.dto.BookmarkDto;
import com.makul.fitness.service.api.BookmarkService;
import com.makul.fitness.service.api.ClientBusinessService;
import com.makul.fitness.util.CustomMapperUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Api(tags = "Controller for bookmark entity")
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final ClientBusinessService clientBusinessService;

    @GetMapping("/user/{userId}/bookmark/{fitnessId}")
    @ApiOperation(value = "Bookmark the active program for the current user")
    public BookmarkDto addBookmark(@ApiParam(defaultValue = "1") @PathVariable("userId") UUID userId,
                                   @ApiParam(defaultValue = "2") @PathVariable("fitnessId") UUID fitnessProgramId) {
        return CustomMapperUtil.convertToDto(clientBusinessService.addBookmark(userId, fitnessProgramId),
                BookmarkDto.class);
    }

    @GetMapping("/user/{userId}/bookmarks")
    @ApiOperation(value = "Viewing the bookmarks of the current user")
    public Page<BookmarkDto> showBookmarks(@ApiParam(defaultValue = "1") @PathVariable("userId") UUID userId,
                                           @ApiParam(defaultValue = "0") @RequestParam int page,
                                           @ApiParam(defaultValue = "10") @RequestParam int size) {
        return CustomMapperUtil.convertToDto(clientBusinessService.viewBookmarks(userId, page, size), BookmarkDto.class);
    }

    @DeleteMapping("/bookmark/{id}")
    @ApiOperation(value = "Delete bookmark by id")
    public void deleteBookmark(@ApiParam(defaultValue = "4") @PathVariable("id") UUID bookmarkId) {
        bookmarkService.delete(bookmarkId);
    }
}
