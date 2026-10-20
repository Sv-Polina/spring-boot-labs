package ru.urfu.MySpringBoot2Dbase.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "STUDENTS")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Имя не должно быть пустым")
    @Size(max = 15, message = "Имя — не более 15 символов")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Фамилия не должна быть пустой")
    @Size(max = 25, message = "Фамилия — не более 25 символов")
    @Column(name = "surname")
    private String surname;

    @Size(max = 20, message = "Факультет — не более 20 символов")
    @Column(name = "faculty")
    private String faculty;

    @Min(value = 14, message = "Возраст должен быть не меньше 14")
    @Max(value = 100, message = "Возраст должен быть не больше 100")
    @Column(name = "age")
    private Integer age;

}
