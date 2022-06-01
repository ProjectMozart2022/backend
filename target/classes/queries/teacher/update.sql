UPDATE teacher
SET first_name=:first_name, last_name=:last_name, email=:email, password=:password
WHERE firebase_id=:firebase_id;