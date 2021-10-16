package com.makul.fitness.dao;

import com.makul.fitness.model.ActiveProgram;
import com.makul.fitness.model.FitnessProgram;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FitnessProgramsWithRestrictionsSearcherDao extends CrudRepository<FitnessProgram, Long> {
    @Query("SELECT fitProg FROM Users user JOIN FitnessProgram fitProg WHERE (user.id = ?1) and (fitProg.category.id=?2) " +
            " and (user.weight <= fitProg.weightRestriction) and (user.sex = fitProg.sexRestriction) " +
            "and (fitProg.duration <= ?3)")
    List<ActiveProgram> findFitnessProgramWithRestrictions(long userId, long categoryId, int duration);

    @Query("SELECT fitProg FROM FitnessProgram fitProg WHERE fitProg.category.id=?2")
    List<ActiveProgram> findFitnessProgram(long categoryId);
}
