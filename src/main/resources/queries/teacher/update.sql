UPDATE teacher
SET first_name=:first_name, last_name=:last_name, email=:email, password=:password,
    minimal_num_of_hours=:minimal_num_of_hours, taught_instruments=:taught_instruments
WHERE firebase_id=:firebase_id;