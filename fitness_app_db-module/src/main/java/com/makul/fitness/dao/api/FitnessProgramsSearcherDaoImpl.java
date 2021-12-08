package com.makul.fitness.dao.api;

import com.makul.fitness.dao.FitnessProgramsSearcherDao;
import com.makul.fitness.model.FitnessProgram;
import com.makul.fitness.service.api.UsersService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.UUID;

@Repository
public class FitnessProgramsSearcherDaoImpl implements FitnessProgramsSearcherDao {

    @PersistenceContext
    EntityManager entityManager;

    private final UsersService usersService;

    public FitnessProgramsSearcherDaoImpl(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public Page <FitnessProgram> findFitnessProgramWithRestrictions(UUID userId, int duration,
                                                                    UUID categoryId, int userAge, Pageable pageable) {
        Query query = entityManager.createQuery("SELECT fitProg FROM FitnessProgram fitProg, Users user WHERE (user.id = :userId) " +
                "and (user.weight <= fitProg.weightRestriction) and (user.sex = fitProg.sexRestriction) and " +
                "(:userAge <= fitProg.ageRestriction) and (fitProg.duration <= :durationLimit) " +
                "and (fitProg.category.id) = :categoryId", FitnessProgram.class);
        query.setParameter("userId", userId)
                .setParameter("userAge",userAge)
                .setParameter("durationLimit",duration)
                .setParameter("categoryId",categoryId);
        Long total = Long.valueOf(query.getMaxResults());
        query.setMaxResults(pageable.getPageSize());
        query.setFirstResult(pageable.getPageNumber()* pageable.getPageSize());
        Page <FitnessProgram> result = new PageImpl<>(query.getResultList(),pageable,total);
        return result;
    }

    @Override
    public Page <FitnessProgram> findFitnessProgram(UUID categoryId, Pageable pageable) {
        Query query = entityManager.createQuery("SELECT fitProg FROM FitnessProgram fitProg " +
                "WHERE fitProg.category.id = :categoryId", FitnessProgram.class);
        query.setParameter("categoryId", categoryId);
        Long total = Long.valueOf(query.getMaxResults());
        query.setMaxResults(pageable.getPageSize());
        query.setFirstResult(pageable.getPageNumber()* pageable.getPageSize());
        Page <FitnessProgram> result = new PageImpl<>(query.getResultList(),pageable,total);
        return result;
    }
}
