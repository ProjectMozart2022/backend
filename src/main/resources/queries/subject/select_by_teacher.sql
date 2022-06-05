SELECT id, name, lesson_length AS lessonLength, class_range AS classRange, is_itn AS itn, is_mandatory as mandatory,
       is_instrument_related as instrumentRelated
FROM known_subject JOIN subject on subject.id = known_subject.subject_id where teacher_id = :teacher_id;
