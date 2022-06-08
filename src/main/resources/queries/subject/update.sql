UPDATE subject
SET name=:name, lesson_length=:lesson_length, class_range=:class_range, is_itn=:is_itn, is_mandatory=:is_mandatory,
    is_instrument_related=:is_instrument_related
WHERE id=:id;