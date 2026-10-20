package ru.urfu.MySpringBoot2Dbase.dao;

import ru.urfu.MySpringBoot2Dbase.entity.Student;

import java.util.List;

public interface StudentDAO {
    List<Student> getAllStudents();

    Student saveStudent(Student student);

    Student getStudent(int id);

    boolean deleteStudent(int id);

}
