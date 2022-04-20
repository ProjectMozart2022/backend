select t.firebase_id,
       t.first_name,
       t.last_name,
       t.email,
       t.password,
       s.id,
       s.first_name,
       s.last_name,
       s.class_number,
       p.id,
       p.name,
       p.lesson_length,
       p.class_range,
       p.is_itn
from lesson l
         join teacher t on t.firebase_id = l.teacher_id
         join student s on s.id = l.student_id
         join subject p on p.id = l.subject_id
where l.teacher_id = :teacher_id
