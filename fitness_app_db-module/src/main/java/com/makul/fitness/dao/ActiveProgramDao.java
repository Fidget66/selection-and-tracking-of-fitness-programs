package com.makul.fitness.dao;

import com.makul.fitness.model.ActiveProgram;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActiveProgramDao extends CrudRepository <ActiveProgram, Long> {
}
