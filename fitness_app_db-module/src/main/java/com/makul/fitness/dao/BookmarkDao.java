package com.makul.fitness.dao;

import com.makul.fitness.model.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookmarkDao extends JpaRepository <Bookmark, UUID> {
    Page <Bookmark> findByUserId(UUID userId, Pageable pageable);
}
