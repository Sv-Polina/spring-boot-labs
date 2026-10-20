package ru.urfu.MySpringBoot2Dbase.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.urfu.MySpringBoot2Dbase.dto.OperationResult;
import ru.urfu.MySpringBoot2Dbase.entity.Discipline;
import ru.urfu.MySpringBoot2Dbase.service.DisciplineService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DisciplineController {
    @Autowired
    private DisciplineService disciplineService;

    @GetMapping("/disciplines")
    public ResponseEntity<OperationResult<List<Discipline>>> allDisciplines() {
        List<Discipline> allDisciplines = disciplineService.getAllDisciplines();
        return ResponseEntity.ok(OperationResult.ok(
                "Найдено дисциплин: " + allDisciplines.size(), allDisciplines));
    }

    @GetMapping("/disciplines/{id}")
    public ResponseEntity<OperationResult<Discipline>> getDiscipline(@PathVariable("id") int id) {
        Discipline discipline = disciplineService.getDiscipline(id);
        if (discipline == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(OperationResult.fail("Дисциплина с id=" + id + " не найдена"));
        }
        return ResponseEntity.ok(OperationResult.ok("Дисциплина найдена", discipline));
    }

    @PostMapping("/disciplines")
    public ResponseEntity<OperationResult<Discipline>> saveDiscipline(@Valid @RequestBody Discipline discipline) {
        Discipline saved = disciplineService.saveDiscipline(discipline);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(OperationResult.ok("Дисциплина успешно добавлена, id=" + saved.getId(), saved));
    }

    @PutMapping("/disciplines")
    public ResponseEntity<OperationResult<Discipline>> updateDiscipline(@Valid @RequestBody Discipline discipline) {
        Discipline updated = disciplineService.updateDiscipline(discipline);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(OperationResult.fail("Не удалось изменить: дисциплина с id="
                            + discipline.getId() + " не найдена"));
        }
        return ResponseEntity.ok(OperationResult.ok("Дисциплина успешно изменена", updated));
    }

    @DeleteMapping("/disciplines/{id}")
    public ResponseEntity<OperationResult<Void>> deleteDiscipline(@PathVariable("id") int id) {
        if (!disciplineService.deleteDiscipline(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(OperationResult.fail("Не удалось удалить: дисциплина с id=" + id + " не найдена"));
        }
        return ResponseEntity.ok(OperationResult.ok("Дисциплина с id=" + id + " успешно удалена", null));
    }
}
