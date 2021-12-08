package com.makul.fitness.service.api;

import com.makul.fitness.model.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BookmarkService {
    Bookmark create(Bookmark bookmark);
    Page <Bookmark> readByUserId(UUID id, Pageable pageable);
    void delete(UUID id);
}
