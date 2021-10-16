package com.makul.fitness.dao;

import com.makul.fitness.model.ActiveProgram;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ActiveProgramSearcherDao extends CrudRepository<ActiveProgram, Long> {

    @Query("SELECT activeProgram FROM Users user JOIN user.activePrograms activeProgram WHERE " +
            "(user.id = ?1) and (activeProgram.isComplited=true)")
    List<ActiveProgram> findComplitedActiveProgram(long userId);

    @Query("SELECT activeProgram FROM Users user JOIN user.activePrograms activeProgram WHERE " +
            "(user.id = ?1) and (activeProgram.isComplited=false)")
    ActiveProgram findUncomplitedActiveProgram(long userId);
}
