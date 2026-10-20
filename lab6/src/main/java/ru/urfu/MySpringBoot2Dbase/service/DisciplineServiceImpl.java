package ru.urfu.MySpringBoot2Dbase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.urfu.MySpringBoot2Dbase.dao.DisciplineDAO;
import ru.urfu.MySpringBoot2Dbase.entity.Discipline;

import java.util.List;

@Service
public class DisciplineServiceImpl implements DisciplineService {
    @Autowired
    private DisciplineDAO disciplineDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Discipline> getAllDisciplines() {
        return disciplineDAO.getAllDisciplines();
    }

    @Override
    @Transactional
    public Discipline saveDiscipline(Discipline discipline) {
        discipline.setId(0);
        return disciplineDAO.saveDiscipline(discipline);
    }

    @Override
    @Transactional
    public Discipline updateDiscipline(Discipline discipline) {
        if (disciplineDAO.getDiscipline(discipline.getId()) == null) {
            return null;
        }
        return disciplineDAO.saveDiscipline(discipline);
    }

    @Override
    @Transactional(readOnly = true)
    public Discipline getDiscipline(int id) {
        return disciplineDAO.getDiscipline(id);
    }

    @Override
    @Transactional
    public boolean deleteDiscipline(int id) {
        return disciplineDAO.deleteDiscipline(id);
    }
}
