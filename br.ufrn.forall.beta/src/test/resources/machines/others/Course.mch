MACHINE Course

SETS
 STUDENT; result = {pass, final_exam, fail}

PROPERTIES
 card(STUDENT) = 15

VARIABLES
 students,
 grades,
 has_taken_lab_classes

INVARIANT
 students <: STUDENT &
 grades : students +-> 0..5 &
 has_taken_lab_classes : students +-> BOOL
  
INITIALISATION
 grades := {} ||
 students := {} ||
 has_taken_lab_classes := {}

OPERATIONS
  add_student(student) =
    PRE
      student : STUDENT
    THEN
      students := students \/ {student}
    END;

  add_grade(student, grade) = 
    PRE
      student : students &
      grade : 0..5
    THEN
      grades(student) := grade
    END;

  present_on_lab_classes(student, present) =
    PRE
      student : students &
      present : BOOL
    THEN
      has_taken_lab_classes(student) := present
    END;    

  rr <-- student_pass_or_fail(student) =
    PRE
      student : students &
      student : dom(grades) &
      student : dom(has_taken_lab_classes)
    THEN
      IF grades(student) > 3 & has_taken_lab_classes(student) = TRUE THEN rr := pass
      ELSIF grades(student) > 2 & has_taken_lab_classes(student) = TRUE THEN rr := final_exam
      ELSE rr := fail
      END
    END
END