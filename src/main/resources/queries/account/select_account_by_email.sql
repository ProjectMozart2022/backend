SELECT id, email, password, role, firebase_uid
FROM account
WHERE email=:email;