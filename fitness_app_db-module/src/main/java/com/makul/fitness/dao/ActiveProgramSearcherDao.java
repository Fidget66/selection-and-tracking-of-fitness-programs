package com.makul.fitness.dao;

import com.makul.fitness.model.ActiveProgram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActiveProgramSearcherDao extends JpaRepository <ActiveProgram, UUID> {

    Page<ActiveProgram> findActiveProgramsByUserIdAndIsComplitedTrue(UUID userId, Pageable pageable);

    Optional<ActiveProgram> findActiveProgramsByUserIdAndIsComplitedFalse(UUID userId);
}
