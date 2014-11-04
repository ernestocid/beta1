
MACHINE
    PseudoStackReadingOperations
SEES
    BasicTypes ,
    NumberADT ,
    BooleanADT ,
    NilADT ,
    Aux ,
    Pseudo_Stack







OPERATIONS

/* lua_absindex [-0, +0, ?]
int lua_absindex (lua_State *L, int idx); Converts the acceptable index idx into an absolute index (that is, one that does not depend on the stack top). 
*/

ret <-- lua_absindex ( idx ) =
PRE
    idx : INT &
    idx : ( - stack_top .. - 1 \/ 1 .. stack_top \/ pseudo_bottom .. MAXINT ) \/ ( stack_top + 1 ) .. max_stack_top
THEN
    ret := convert_index ( stack_top , idx )
END ;



/*
lua_compare [-0, +0, e] 
 int lua_compare (lua_State *L, int index1, int index2, int op); 
Compares two Lua values. Returns 1 if the value at acceptable index index1 satisfies op when compared with the value at acceptable index index2, following the semantics of the corresponding Lua operator (that is, it may call metamethods). Otherwise returns 0. Also returns 0 if any of the indices is non valid. 
The value of op must be one of the following constants: 
LUA_OPEQ: compares for equality (==) 
LUA_OPLT: compares for less than (<) 
LUA_OPLE: compares for less or equal (<=) 
*/
/* !!! a semantica dos operadores de comparacao ainda nao esta definida - e nem como lidar com metamethods*/
ret <-- lua_compare ( index1 , index2 , oper ) =
PRE
    index1 : ( - stack_top .. - 1 \/ 1 .. stack_top \/ pseudo_bottom .. MAXINT ) \/ ( stack_top + 1 ) .. max_stack_top &
    index2 : ( - stack_top .. - 1 \/ 1 .. stack_top \/ pseudo_bottom .. MAXINT ) \/ ( stack_top + 1 ) .. max_stack_top &
    oper : { LUA_OPEQ , LUA_OPLT , LUA_OPLE }
THEN
    IF ( index1 > stack_top or index2 > stack_top )
    THEN
        ret := 0
    ELSE
        LET
            value1 , value2
        BE
            value1 = stack ( convert_index ( stack_top , index1 ) ) &
            value2 = stack ( convert_index ( stack_top , index2 ) )
        IN
            CASE oper OF
            EITHER LUA_OPEQ THEN ret :: { 0 , 1 }
                OR LUA_OPLT THEN ret :: { 0 , 1 }
                OR LUA_OPLE THEN ret :: { 0 , 1 }
                END
            END
        END
    END
END ;

/* lua_gettop [-0, +0, ?]
int lua_gettop (lua_State *L); Returns the index of the top element in the stack. Because indices start at 1, this result is equal to the number of elements in the stack (and so 0 means an empty stack). 
*/
ret <-- lua_gettop =
    ret := stack_top ;

/*   
lua_rawequal  [-0, +0, ?] 
int lua_rawequal (lua_State *L, int index1, int index2); 
Returns 1 if the two values in acceptable indices index1 and index2 are primitively equal (that is, 
without calling metamethods). Otherwise returns 0. Also returns 0 if any of the indices are non valid. 
*/
/* !!! atencao - por causa da "casca" com varios lixos em cada lua_value, o igual basico de B nao tem o significado desejado. Descascar obriga a comparar valores de cada tipo separadamente - acho que a a unica solucao, mas ainda nao esta feito assim aqui. Ou criara uma funcao generica para descascar que teste o tipo do dado antes de chamar a funcao correspondente. */
ret <-- lua_rawequal ( index1 , index2 ) =
PRE
    index1 : ( - stack_top .. - 1 \/ 1 .. stack_top \/ pseudo_bottom .. MAXINT ) \/ ( stack_top + 1 ) .. max_stack_top &
    index2 : ( - stack_top .. - 1 \/ 1 .. stack_top \/ pseudo_bottom .. MAXINT ) \/ ( stack_top + 1 ) .. max_stack_top
THEN
      IF ( index1 /: ( - stack_top .. - 1 \/ 1 .. stack_top \/ pseudo_bottom .. MAXINT ) or index2 /: ( - stack_top .. - 1 \/ 1 .. stack_top \/ pseudo_bottom .. MAXINT ) )
    THEN
        ret := 0
    ELSE
        LET
            value1 , value2
        BE
            value1 = stack ( convert_index ( stack_top , index1 ) ) &
            value2 = stack ( convert_index ( stack_top , index2 ) )
        IN
            IF
                value1 = value2
            THEN
                ret := 1
            ELSE
                ret := 0
            END
        END
    END
END

END
