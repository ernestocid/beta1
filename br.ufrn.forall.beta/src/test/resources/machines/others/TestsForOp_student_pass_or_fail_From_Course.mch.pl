spec_trans(root,'$initialise_machine',1).
spec_trans(0,'$initialise_machine',1).
spec_trans(1,'student_pass_or_fail_test1(STUDENT1,{(STUDENT1|->0)},{},{STUDENT1})',1).
spec_trans(1,'student_pass_or_fail_test2(STUDENT1,{(STUDENT1|->0)},{(STUDENT1|->TRUE)},{STUDENT1})',1).
spec_trans(1,'student_pass_or_fail_test3(STUDENT1,{(STUDENT1|->0)},{(STUDENT1|->FALSE)},{STUDENT1})',1).
spec_trans(1,'student_pass_or_fail_test4(STUDENT1,{(STUDENT1|->3)},{(STUDENT1|->TRUE)},{STUDENT1})',1).
spec_trans(1,'student_pass_or_fail_test5(STUDENT1,{(STUDENT1|->4)},{(STUDENT1|->TRUE)},{STUDENT1})',1).
spec_trans(1,'student_pass_or_fail_test6(STUDENT1,{(STUDENT1|->0)},{(STUDENT1|->TRUE)},{STUDENT1})',1).
spec_trans(1,'student_pass_or_fail_test7(STUDENT1,{(STUDENT1|->0)},{(STUDENT1|->TRUE)},{STUDENT1})',1).
spec_trans(1,'student_pass_or_fail_test8(STUDENT1,{},{(STUDENT1|->TRUE)},{STUDENT1})',1).
spec_trans(1,'student_pass_or_fail_test9(STUDENT1,{(STUDENT1|->0)},{(STUDENT1|->TRUE)},{STUDENT1})',1).
spec_max_reached_for_node(root).
spec_max_reached_for_node(0).
spec_max_reached_for_node(1).
spec_max_reached_for_node(1).
spec_not_all_transitions_added(_) :-
        fail.
spec_completely_explored :-
        fail.
