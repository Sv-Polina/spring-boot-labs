package ru.urfu.MySpringBoot2Dbase.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.urfu.MySpringBoot2Dbase.entity.Discipline;

import java.util.List;

@Slf4j
@Repository
public class DisciplineDAOImpl implements DisciplineDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Discipline> getAllDisciplines() {
        Query query = entityManager.createQuery("from Discipline order by id");
        List<Discipline> allDisciplines = query.getResultList();
        log.info("getAllDisciplines " + allDisciplines);
        return allDisciplines;
    }

    @Override
    public Discipline saveDiscipline(Discipline discipline) {
        return entityManager.merge(discipline);
    }

    @Override
    public Discipline getDiscipline(int id) {
        return entityManager.find(Discipline.class, id);
    }

    @Override
    public boolean deleteDiscipline(int id) {
        Query query = entityManager.createQuery("delete from Discipline "
                + " where id =:disciplineId");
        query.setParameter("disciplineId", id);
        return query.executeUpdate() > 0;
    }
}
