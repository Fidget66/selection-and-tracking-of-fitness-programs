package com.makul.fitness.dao;

import com.makul.fitness.model.Bookmark;
import org.springframework.data.repository.CrudRepository;

public interface BookmarkDao extends CrudRepository <Bookmark, Long> {
}
