MACHINE ClassroomWithoutDeferredSetsCBCTest

INCLUDES ClassroomWithoutDeferredSets

PROMOTES add_grade, add_student, present_on_lab_classes

OPERATIONS
student_pass_or_fail_test =
PRE
students = {st1} & has_taken_lab_classes = {(st1|->TRUE)} & grades = {(st1|->4)}
THEN skip
END

END