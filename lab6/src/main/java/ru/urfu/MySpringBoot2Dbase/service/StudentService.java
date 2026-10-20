package ru.urfu.MySpringBoot2Dbase.service;

import ru.urfu.MySpringBoot2Dbase.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> getAllStudents();

    Student saveStudent(Student student);

    Student updateStudent(Student student);

    Student getStudent(int id);

    boolean deleteStudent(int id);

}
