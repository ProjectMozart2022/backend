UPDATE profile
SET name=:name, lesson_length=:lessonLength, class_range=:classRange, is_itn=:itn
WHERE id=:id;