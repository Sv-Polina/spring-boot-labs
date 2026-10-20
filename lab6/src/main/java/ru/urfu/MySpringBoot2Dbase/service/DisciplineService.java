package ru.urfu.MySpringBoot2Dbase.service;

import ru.urfu.MySpringBoot2Dbase.entity.Discipline;

import java.util.List;

public interface DisciplineService {
    List<Discipline> getAllDisciplines();

    Discipline saveDiscipline(Discipline discipline);

    Discipline updateDiscipline(Discipline discipline);

    Discipline getDiscipline(int id);

    boolean deleteDiscipline(int id);

}
