package com.makul.fitness.dao;

import com.makul.fitness.model.ActiveProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ActiveProgramDao extends JpaRepository <ActiveProgram, UUID> {
}
