SELECT id, name, lesson_length, class_range, is_itn, is_mandatory, is_instrument_related
FROM known_subject JOIN subject on subject.id = known_subject.subject_id where teacher_id = :teacher_id;
