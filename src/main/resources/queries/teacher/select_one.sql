SELECT firebase_id AS firebaseId, first_name AS firstName, last_name AS lastName, email, password
FROM teacher
WHERE firebase_id=:firebase_id;