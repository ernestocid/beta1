MACHINE CourseCBCTest

INCLUDES Course

PROMOTES add_grade, add_student, present_on_lab_classes

OPERATIONS
student_pass_or_fail_test1(student) =
PRE
students <: STUDENT & student : dom(has_taken_lab_classes) & grades : (students +-> 0..5) & student : dom(grades) & has_taken_lab_classes : (students +-> BOOL) & student : students
THEN skip
END

END