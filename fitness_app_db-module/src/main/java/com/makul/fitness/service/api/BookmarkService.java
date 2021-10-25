package com.makul.fitness.service.api;

import com.makul.fitness.model.Bookmark;
import java.util.List;

public interface BookmarkService {
    Bookmark create(Bookmark bookmark);
    void delete(long id);
}
