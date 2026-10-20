package ru.urfu.MySpringBoot2Dbase.dao;

import ru.urfu.MySpringBoot2Dbase.entity.Discipline;

import java.util.List;

public interface DisciplineDAO {
    List<Discipline> getAllDisciplines();

    Discipline saveDiscipline(Discipline discipline);

    Discipline getDiscipline(int id);

    boolean deleteDiscipline(int id);

}
