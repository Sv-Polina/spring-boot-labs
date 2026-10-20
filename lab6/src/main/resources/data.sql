INSERT INTO STUDENTS (name, surname, faculty, age)
SELECT * FROM (VALUES
    ('Тест', 'Тестов', 'IT', 20),
    ('Петр', 'Петров', 'Math', 20),
    ('Мария', 'Сидорова', 'РТФ', 19),
    ('Алексей', 'Смирнов', 'ИРИТ-РТФ', 21)
) AS t(name, surname, faculty, age)
WHERE NOT EXISTS (SELECT 1 FROM STUDENTS);

INSERT INTO DISCIPLINES (name, teacher, hours, semester)
SELECT * FROM (VALUES
    ('Технологии программирования', 'Николаев А. С.', 144, 5),
    ('Базы данных', 'Иванова Е. В.', 108, 4),
    ('Компьютерные сети', 'Сидоров П. П.', 72, 5),
    ('Математический анализ', 'Кузнецова О. И.', 180, 1)
) AS t(name, teacher, hours, semester)
WHERE NOT EXISTS (SELECT 1 FROM DISCIPLINES);
