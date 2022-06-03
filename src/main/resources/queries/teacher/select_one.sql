SELECT firebase_id AS firebaseId, first_name AS firstName, last_name AS lastName, email, password,
       minimal_num_of_hours as minimalNumOfHours
FROM teacher
WHERE firebase_id=:firebase_id;