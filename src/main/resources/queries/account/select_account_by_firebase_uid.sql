SELECT id, email, password, role, firebase_uid
FROM account
WHERE firebase_uid=:firebase_uid;