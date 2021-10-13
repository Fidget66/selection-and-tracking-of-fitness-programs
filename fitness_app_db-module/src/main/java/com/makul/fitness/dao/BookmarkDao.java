package com.makul.fitness.dao;

import com.makul.fitness.model.Bookmark;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkDao extends CrudRepository <Bookmark, Long> {
}
