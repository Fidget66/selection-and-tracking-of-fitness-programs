package com.makul.fitness.dao.api;

import com.makul.fitness.dao.FitnessProgramsSearcherDao;
import com.makul.fitness.model.FitnessProgram;
import com.makul.fitness.service.api.UsersService;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class FitnessProgramsSearcherDaoImpl implements FitnessProgramsSearcherDao {

    @PersistenceContext
    EntityManager entityManager;

    private final UsersService usersService;

    public FitnessProgramsSearcherDaoImpl(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public List<FitnessProgram> findFitnessProgramWithRestrictions(long userId, int duration,
                                                                   long categoryId, int userAge) {
        Query query = entityManager.createNamedQuery("findFitnessProgramWithRestrictions",
                FitnessProgram.class);
        query.setParameter("userId", userId)
                .setParameter("userAge",userAge)
                .setParameter("durationLimit",duration)
                .setParameter("categoryId",categoryId);
        List<FitnessProgram> result = query.getResultList();
        return result;
    }

    @Override
    public List<FitnessProgram> findFitnessProgram(long categoryId) {
        Query query = entityManager.createNamedQuery("findFitnessProgramFromCategory",
                FitnessProgram.class);
        query.setParameter("categoryId", categoryId);
        List<FitnessProgram> result = query.getResultList();
        return result;
    }
}
