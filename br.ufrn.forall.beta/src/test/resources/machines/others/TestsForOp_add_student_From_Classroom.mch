MACHINE TestsForOp_add_student_From_Classroom

SETS
result = {pass, final_exam, fail};
STUDENT

PROPERTIES
card(STUDENT) = 15

OPERATIONS
/* Equivalence Class test data for add_student */
add_student_test1(
i__finished,
i__student,
i__grades,
i__has_taken_lab_classes,
i__students
) =
PRE
i__students <: STUDENT & i__grades : (i__students +-> 0..5) & i__student : STUDENT & i__finished : BOOL & i__has_taken_lab_classes : (i__students +-> BOOL) /* POSITIVE */
THEN
skip
END
END