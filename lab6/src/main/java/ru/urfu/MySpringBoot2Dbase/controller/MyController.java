package ru.urfu.MySpringBoot2Dbase.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.urfu.MySpringBoot2Dbase.dto.OperationResult;
import ru.urfu.MySpringBoot2Dbase.entity.Student;
import ru.urfu.MySpringBoot2Dbase.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MyController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public ResponseEntity<OperationResult<List<Student>>> allStudents() {
        List<Student> allStudents = studentService.getAllStudents();
        return ResponseEntity.ok(OperationResult.ok(
                "Найдено студентов: " + allStudents.size(), allStudents));
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<OperationResult<Student>> getStudent(@PathVariable("id") int id) {
        Student student = studentService.getStudent(id);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(OperationResult.fail("Студент с id=" + id + " не найден"));
        }
        return ResponseEntity.ok(OperationResult.ok("Студент найден", student));
    }

    @PostMapping("/students")
    public ResponseEntity<OperationResult<Student>> saveStudent(@Valid @RequestBody Student student) {
        Student saved = studentService.saveStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(OperationResult.ok("Студент успешно добавлен, id=" + saved.getId(), saved));
    }

    @PutMapping("/students")
    public ResponseEntity<OperationResult<Student>> updateStudent(@Valid @RequestBody Student student) {
        Student updated = studentService.updateStudent(student);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(OperationResult.fail("Не удалось изменить: студент с id="
                            + student.getId() + " не найден"));
        }
        return ResponseEntity.ok(OperationResult.ok("Данные студента успешно изменены", updated));
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<OperationResult<Void>> deleteStudent(@PathVariable("id") int id) {
        if (!studentService.deleteStudent(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(OperationResult.fail("Не удалось удалить: студент с id=" + id + " не найден"));
        }
        return ResponseEntity.ok(OperationResult.ok("Студент с id=" + id + " успешно удалён", null));
    }
}
