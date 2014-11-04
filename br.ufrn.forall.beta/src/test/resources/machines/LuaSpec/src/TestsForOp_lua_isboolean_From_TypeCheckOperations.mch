MACHINE TestsForOp_lua_isboolean_From_TypeCheckOperations

SEES
    BasicTypes,
    Aux,
    Pseudo_Stack

OPERATIONS
/* Equivalence Class test data for lua_isboolean */
lua_isboolean_test1(
index
) =
PRE
index : INT & index : -stack_top..-1 \/ 1..stack_top \/ pseudo_bottom..MAXINT \/ ((stack_top + 1))..max_stack_top /* POSITIVE */
THEN
skip
END
;
/* Equivalence Class test data for lua_isboolean */
lua_isboolean_test2(
index
) =
PRE
index : INT & not(index : -stack_top..-1 \/ 1..stack_top \/ pseudo_bottom..MAXINT \/ ((stack_top + 1))..max_stack_top) /* NEGATIVE */
THEN
skip
END
END