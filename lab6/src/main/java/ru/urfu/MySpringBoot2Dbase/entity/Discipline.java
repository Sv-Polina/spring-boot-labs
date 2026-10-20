package ru.urfu.MySpringBoot2Dbase.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DISCIPLINES")
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Название дисциплины не должно быть пустым")
    @Size(max = 100, message = "Название — не более 100 символов")
    @Column(name = "name")
    private String name;

    @Size(max = 60, message = "ФИО преподавателя — не более 60 символов")
    @Column(name = "teacher")
    private String teacher;

    @Min(value = 1, message = "Количество часов должно быть больше 0")
    @Column(name = "hours")
    private Integer hours;

    @Min(value = 1, message = "Семестр — от 1 до 12")
    @Max(value = 12, message = "Семестр — от 1 до 12")
    @Column(name = "semester")
    private Integer semester;

}
