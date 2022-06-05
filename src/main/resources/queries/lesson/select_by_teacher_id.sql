select t.firebase_id as teacher_id,
       t.first_name as teacher_first_name,
       t.last_name as teacher_last_name,
       t.email as teacher_email,
       t.password as teacher_password,
       t.minimal_num_of_hours as teacher_minimal_num_of_hours,
       s.id as student_id,
       s.first_name as student_first_name,
       s.last_name as student_last_name,
       s.class_number as student_class_number,
       s.main_instrument as student_main_instrument,
       p.id as subject_id,
       p.name as subject_name,
       p.lesson_length as subject_lesson_length,
       p.class_range as subject_class_range,
       p.is_itn as subject_is_itn,
       p.is_mandatory as subject_is_mandatory
from lesson l
         join teacher t on t.firebase_id = l.teacher_id
         join student s on s.id = l.student_id
         join subject p on p.id = l.subject_id
where l.teacher_id = :teacher_id
