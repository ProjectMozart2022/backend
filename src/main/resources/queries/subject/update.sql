UPDATE subject
SET name=:name, lesson_length=:lesson_length, class_range=:class_range, is_itn=:is_itn, is_mandatory=:is_mandatory
WHERE id=:id;