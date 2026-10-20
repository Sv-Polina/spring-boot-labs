package ru.urfu.MySpringBoot2Dbase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.urfu.MySpringBoot2Dbase.dao.StudentDAO;
import ru.urfu.MySpringBoot2Dbase.entity.Student;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentDAO studentDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    @Override
    @Transactional
    public Student saveStudent(Student student) {
        student.setId(0);
        return studentDAO.saveStudent(student);
    }

    @Override
    @Transactional
    public Student updateStudent(Student student) {
        if (studentDAO.getStudent(student.getId()) == null) {
            return null;
        }
        return studentDAO.saveStudent(student);
    }

    @Override
    @Transactional(readOnly = true)
    public Student getStudent(int id) {
        return studentDAO.getStudent(id);
    }

    @Override
    @Transactional
    public boolean deleteStudent(int id) {
        return studentDAO.deleteStudent(id);
    }
}
