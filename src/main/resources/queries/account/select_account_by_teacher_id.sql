SELECT id, email, password, role, firebase_uid
FROM account
WHERE id = (SELECT account_id
            FROM teacher
            WHERE id=:id);