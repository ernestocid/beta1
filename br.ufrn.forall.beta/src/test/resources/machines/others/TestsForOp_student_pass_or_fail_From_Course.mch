MACHINE TestsForOp_student_pass_or_fail_From_Course

SETS
result = {pass, final_exam, fail};
STUDENT

PROPERTIES
card(STUDENT) = 15

OPERATIONS
/* Equivalence Class test data for student_pass_or_fail */
student_pass_or_fail_test1(
i__student,
i__grades,
i__has_taken_lab_classes,
i__students
) =
PRE
i__students <: STUDENT & i__grades : (i__students +-> 0..5) & i__student : dom(i__grades) & not(i__student : dom(i__has_taken_lab_classes)) & i__has_taken_lab_classes : (i__students +-> BOOL) & i__student : i__students /* POSITIVE */
THEN
skip
END
;
/* Equivalence Class test data for student_pass_or_fail */
student_pass_or_fail_test2(
i__student,
i__grades,
i__has_taken_lab_classes,
i__students
) =
PRE
i__students <: STUDENT & i__grades : (i__students +-> 0..5) & i__student : dom(i__has_taken_lab_classes) & i__has_taken_lab_classes(i__student) = TRUE & i__student : dom(i__grades) & i__has_taken_lab_classes : (i__students +-> BOOL) & i__student : i__students /* POSITIVE */
THEN
skip
END
;
/* Equivalence Class test data for student_pass_or_fail */
student_pass_or_fail_test3(
i__student,
i__grades,
i__has_taken_lab_classes,
i__students
) =
PRE
i__students <: STUDENT & i__grades : (i__students +-> 0..5) & i__student : dom(i__has_taken_lab_classes) & not(i__has_taken_lab_classes(i__student) = TRUE) & i__student : dom(i__grades) & i__has_taken_lab_classes : (i__students +-> BOOL) & i__student : i__students /* POSITIVE */
THEN
skip
END
;
/* Equivalence Class test data for student_pass_or_fail */
student_pass_or_fail_test4(
i__student,
i__grades,
i__has_taken_lab_classes,
i__students
) =
PRE
i__students <: STUDENT & i__grades : (i__students +-> 0..5) & i__student : dom(i__has_taken_lab_classes) & i__student : dom(i__grades) & i__grades(i__student) > 2 & i__has_taken_lab_classes : (i__students +-> BOOL) & i__student : i__students /* POSITIVE */
THEN
skip
END
;
/* Equivalence Class test data for student_pass_or_fail */
student_pass_or_fail_test5(
i__student,
i__grades,
i__has_taken_lab_classes,
i__students
) =
PRE
i__students <: STUDENT & i__grades : (i__students +-> 0..5) & i__student : dom(i__has_taken_lab_classes) & i__student : dom(i__grades) & i__grades(i__student) > 3 & i__has_taken_lab_classes : (i__students +-> BOOL) & i__student : i__students /* POSITIVE */
THEN
skip
END
;
/* Equivalence Class test data for student_pass_or_fail */
student_pass_or_fail_test6(
i__student,
i__grades,
i__has_taken_lab_classes,
i__students
) =
PRE
i__students <: STUDENT & i__grades : (i__students +-> 0..5) & i__student : dom(i__has_taken_lab_classes) & i__student : dom(i__grades) & i__has_taken_lab_classes : (i__students +-> BOOL) & i__student : i__students & not(i__grades(i__student) > 3) /* POSITIVE */
THEN
skip
END
;
/* Equivalence Class test data for student_pass_or_fail */
student_pass_or_fail_test7(
i__student,
i__grades,
i__has_taken_lab_classes,
i__students
) =
PRE
i__students <: STUDENT & i__grades : (i__students +-> 0..5) & i__student : dom(i__has_taken_lab_classes) & i__student : dom(i__grades) & not(i__grades(i__student) > 2) & i__has_taken_lab_classes : (i__students +-> BOOL) & i__student : i__students /* POSITIVE */
THEN
skip
END
;
/* Equivalence Class test data for student_pass_or_fail */
student_pass_or_fail_test8(
i__student,
i__grades,
i__has_taken_lab_classes,
i__students
) =
PRE
i__students <: STUDENT & i__student : dom(i__has_taken_lab_classes) & i__grades : (i__students +-> 0..5) & not(i__student : dom(i__grades)) & i__has_taken_lab_classes : (i__students +-> BOOL) & i__student : i__students /* POSITIVE */
THEN
skip
END
;
/* Equivalence Class test data for student_pass_or_fail */
student_pass_or_fail_test9(
i__student,
i__grades,
i__has_taken_lab_classes,
i__students
) =
PRE
i__students <: STUDENT & i__student : dom(i__has_taken_lab_classes) & i__grades : (i__students +-> 0..5) & i__student : dom(i__grades) & i__has_taken_lab_classes : (i__students +-> BOOL) & i__student : i__students /* POSITIVE */
THEN
skip
END
;
/* Equivalence Class test data for student_pass_or_fail */
student_pass_or_fail_test10(
i__student,
i__grades,
i__has_taken_lab_classes,
i__students
) =
PRE
i__students <: STUDENT & i__student : dom(i__has_taken_lab_classes) & i__grades : (i__students +-> 0..5) & i__student : dom(i__grades) & not(i__student : i__students) & i__has_taken_lab_classes : (i__students +-> BOOL) /* POSITIVE */
THEN
skip
END
END