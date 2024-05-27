ALTER TABLE students
    ADD CONSTRAINT students_groups_fk FOREIGN KEY (group_id) REFERENCES "groups" (id);

ALTER TABLE groups
    ADD CONSTRAINT groups_student_fk FOREIGN KEY (monitor_id) REFERENCES students (id);
ALTER TABLE groups
    ADD CONSTRAINT groups_teachers_fk FOREIGN KEY (curator_id) REFERENCES teachers (id);

ALTER TABLE lessons
    ADD CONSTRAINT lessons_teacher_fk FOREIGN KEY (teacher_id) REFERENCES teachers (id);
ALTER TABLE lessons
    ADD CONSTRAINT lessons_subject_fk FOREIGN KEY (subject_id) REFERENCES subjects (id);
ALTER TABLE lessons
    ADD CONSTRAINT lessons_group_fk FOREIGN KEY (group_id) REFERENCES "groups" (id);
ALTER TABLE teacher_subjects
    ADD CONSTRAINT teacher_subjects_teachers_fk FOREIGN KEY (teacher_id) REFERENCES teachers (id);
ALTER TABLE teacher_subjects
    ADD CONSTRAINT teachers_subject_subject_fk FOREIGN KEY (subject_id) REFERENCES subjects (id);