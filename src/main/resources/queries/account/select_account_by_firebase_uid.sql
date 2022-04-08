SELECT id, email, password, role, firebase_uid AS firebaseUid
FROM account
WHERE firebase_uid=:firebase_uid;