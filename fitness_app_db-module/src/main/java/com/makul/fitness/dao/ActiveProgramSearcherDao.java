package com.makul.fitness.dao;

import com.makul.fitness.model.ActiveProgram;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ActiveProgramSearcherDao extends CrudRepository<ActiveProgram, Long> {

    List<ActiveProgram> findActiveProgramsByUserIdAndIsComplitedTrue(long userId);

    ActiveProgram findActiveProgramsByUserIdAndIsComplitedFalse(long userId);
}
