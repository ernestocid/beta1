MACHINE TestsForOp_student_pass_or_fail_From_Classroom

SETS
result = {pass, final_exam, fail};
STUDENT

PROPERTIES
card(STUDENT) = 15

OPERATIONS
/* Equivalence Class test data for student_pass_or_fail */
student_pass_or_fail_test1(
i__finished,
i__student,
i__grades,
i__has_taken_lab_classes,
i__students
) =
PRE
i__students <: STUDENT & i__grades : (i__students +-> 0..5) & not(i__student : dom(i__has_taken_lab_classes)) & not(i__student : dom(i__grades)) & i__finished : BOOL & not(i__grades(i__student) > 3 & i__has_taken_lab_classes(i__student) = TRUE) & i__has_taken_lab_classes : (i__students +-> BOOL) & i__grades(i__student) > 2 & i__has_taken_lab_classes(i__student) = TRUE & i__student : i__students /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for student_pass_or_fail */
student_pass_or_fail_test2(
i__finished,
i__student,
i__grades,
i__has_taken_lab_classes,
i__students
) =
PRE
i__students <: STUDENT & i__grades : (i__students +-> 0..5) & not(i__student : dom(i__has_taken_lab_classes)) & i__student : dom(i__grades) & i__finished : BOOL & i__grades(i__student) > 3 & i__has_taken_lab_classes(i__student) = TRUE & i__has_taken_lab_classes : (i__students +-> BOOL) & i__grades(i__student) > 2 & i__has_taken_lab_classes(i__student) = TRUE & not(i__student : i__students) /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for student_pass_or_fail */
student_pass_or_fail_test3(
i__finished,
i__student,
i__grades,
i__has_taken_lab_classes,
i__students
) =
PRE
i__students <: STUDENT & i__grades : (i__students +-> 0..5) & not(i__student : dom(i__has_taken_lab_classes)) & i__student : dom(i__grades) & i__finished : BOOL & i__grades(i__student) > 3 & i__has_taken_lab_classes(i__student) = TRUE & i__has_taken_lab_classes : (i__students +-> BOOL) & not(i__grades(i__student) > 2 & i__has_taken_lab_classes(i__student) = TRUE) & i__student : i__students /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for student_pass_or_fail */
student_pass_or_fail_test4(
i__finished,
i__student,
i__grades,
i__has_taken_lab_classes,
i__students
) =
PRE
i__students <: STUDENT & i__grades : (i__students +-> 0..5) & i__student : dom(i__has_taken_lab_classes) & not(i__student : dom(i__grades)) & i__finished : BOOL & i__grades(i__student) > 3 & i__has_taken_lab_classes(i__student) = TRUE & i__has_taken_lab_classes : (i__students +-> BOOL) & not(i__grades(i__student) > 2 & i__has_taken_lab_classes(i__student) = TRUE) & not(i__student : i__students) /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for student_pass_or_fail */
student_pass_or_fail_test5(
i__finished,
i__student,
i__grades,
i__has_taken_lab_classes,
i__students
) =
PRE
i__students <: STUDENT & i__grades : (i__students +-> 0..5) & i__student : dom(i__has_taken_lab_classes) & i__student : dom(i__grades) & i__finished : BOOL & i__grades(i__student) > 3 & i__has_taken_lab_classes(i__student) = TRUE & i__has_taken_lab_classes : (i__students +-> BOOL) & i__grades(i__student) > 2 & i__has_taken_lab_classes(i__student) = TRUE & i__student : i__students /* POSITIVE */
THEN
skip
END
;
/* Equivalence Class test data for student_pass_or_fail */
student_pass_or_fail_test6(
i__finished,
i__student,
i__grades,
i__has_taken_lab_classes,
i__students
) =
PRE
i__students <: STUDENT & i__grades : (i__students +-> 0..5) & i__student : dom(i__has_taken_lab_classes) & i__student : dom(i__grades) & i__finished : BOOL & not(i__grades(i__student) > 3 & i__has_taken_lab_classes(i__student) = TRUE) & i__has_taken_lab_classes : (i__students +-> BOOL) & not(i__grades(i__student) > 2 & i__has_taken_lab_classes(i__student) = TRUE) & not(i__student : i__students) /* NEGATIVE */
THEN
skip
END
END