UPDATE student
SET first_name=:first_name, last_name=:last_name, class_number=:class_number, main_instrument=:main_instrument
WHERE id=:id;