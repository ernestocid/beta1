MACHINE ClassroomCBCTest

INCLUDES Classroom

PROMOTES add_grade, add_student, present_on_lab_classes

OPERATIONS
student_pass_or_fail_test1(student) =
PRE
grades(student) > 2 & grades(student) > 3 & has_taken_lab_classes(student) = TRUE & student : dom(grades) & student : dom(has_taken_lab_classes) & student : students
THEN skip
END

END