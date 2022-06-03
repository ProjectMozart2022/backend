INSERT INTO subject (name, lesson_length, class_range, is_itn, is_mandatory)
VALUES (:name, :lesson_length, :class_range, :is_itn, :is_mandatory) RETURNING id;