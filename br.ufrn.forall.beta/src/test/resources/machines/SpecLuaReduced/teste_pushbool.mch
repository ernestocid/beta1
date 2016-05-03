MACHINE TestsForOp_lua_pushboolean_From_PseudoStackOperations

/* Inserido Manualmente */
SEES 
    BasicTypes,
    NumberADT,
    BooleanADT,
    NilADT,
    Aux
    
INCLUDES 
    Pseudo_Stack
/* FIM */

/*
Removido porque ja tem nas maquinas incluidas ou visualizadas.

CONSTANTS LUA_MINSTACK, pseudo_bottom

PROPERTIES
pseudo_bottom > LUA_MINSTACK &
LUA_MINSTACK = 20 &
pseudo_bottom : NAT1 &
LUA_MINSTACK : NAT1
*/

DEFINITIONS
stack_indexes == -stack_top..-1 \/ 1..stack_top;
pseudo_indexes == pseudo_bottom..MAXINT;
valid_indexes == stack_indexes \/ pseudo_indexes;
acceptable_indexes == valid_indexes \/ ((stack_top + 1))..max_stack_top;
SET_PREF_MININT == -10;
SET_PREF_MAXINT == 30

OPERATIONS
/* O prefixo aux_ foi inserido nos parametros que representam a variaveis de estado */

/* Equivalence Class test data for lua_pushboolean */
lua_pushboolean_test1(
aux_max_stack_top,
aux_stack_top,
bb
) =
PRE
bb : LUA_BOOLEAN & not(aux_stack_top < aux_max_stack_top) & aux_max_stack_top < pseudo_bottom & aux_stack_top : NAT & aux_stack_top <= aux_max_stack_top & aux_max_stack_top : NAT1 /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for lua_pushboolean */
lua_pushboolean_test2(
aux_max_stack_top,
aux_stack_top,
bb
) =
PRE
bb : LUA_BOOLEAN & aux_stack_top < aux_max_stack_top & aux_max_stack_top < pseudo_bottom & aux_stack_top : NAT & aux_stack_top <= aux_max_stack_top & aux_max_stack_top : NAT1 /* POSITIVE */
THEN
skip
END
;
/* Equivalence Class test data for lua_pushboolean */
lua_pushboolean_test3(
aux_max_stack_top,
aux_stack_top,
bb
) =
PRE
not(bb : LUA_BOOLEAN) & not(aux_stack_top < aux_max_stack_top) & aux_max_stack_top < pseudo_bottom & aux_stack_top : NAT & aux_stack_top <= aux_max_stack_top & aux_max_stack_top : NAT1 /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for lua_pushboolean */
lua_pushboolean_test4(
aux_max_stack_top,
aux_stack_top,
bb
) =
PRE
not(bb : LUA_BOOLEAN) & aux_stack_top < aux_max_stack_top & aux_max_stack_top < pseudo_bottom & aux_stack_top : NAT & aux_stack_top <= aux_max_stack_top & aux_max_stack_top : NAT1 /* NEGATIVE */
THEN
skip
END
END