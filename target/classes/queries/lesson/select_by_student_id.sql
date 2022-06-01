select t.firebase_id as teacher_id,
       t.first_name as teacher_first_name,
       t.last_name as teacher_last_name,
       t.email as teacher_email,
       t.password as teacher_password,
       s.id as student_id,
       s.first_name as student_first_name,
       s.last_name as student_last_name,
       s.class_number as student_class_number,
       p.id as subject_id,
       p.name as subject_name,
       p.lesson_length as subject_lesson_length,
       p.class_range as subject_class_range,
       p.is_itn as subject_is_itn
from lesson l
         join teacher t on t.firebase_id = l.teacher_id
         join student s on s.id = l.student_id
         join subject p on p.id = l.subject_id
where l.student_id = :student_id

