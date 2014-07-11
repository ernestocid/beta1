/* PseudoStackReadingOperations
 * Author: Anamaria
 * Creation date: Tue Jul 31 2012
 */
MACHINE
    PseudoStackReadingOperations
SEES  
    BasicTypes,
    NumberADT,
    BooleanADT,
    NilADT,
    StringADT,
    FunctionADT,
    UserdataADT,
    TableADT,
    Aux,
    Pseudo_Stack,
    TableHeap,
    UserdataHeap
    
DEFINITIONS
    stack_indexes == -stack_top..-1 \/ 1..stack_top;
    pseudo_indexes == pseudo_bottom..MAXINT;
    valid_indexes == (stack_indexes \/ pseudo_indexes);
    acceptable_indexes == valid_indexes \/ (stack_top+1)..max_stack_top
 
OPERATIONS
    
    /* lua_absindex [-0, +0, ?]
int lua_absindex (lua_State *L, int idx); Converts the acceptable index idx into an absolute index (that is, one that does not depend on the stack top). 
*/

ret <-- lua_absindex (idx) =
PRE
    idx : INT &
    idx : acceptable_indexes
THEN
    ret := convert_index(stack_top, idx)  
END;



/*
lua_compare [-0, +0, e] 
 int lua_compare (lua_State *L, int index1, int index2, int op); 
Compares two Lua values. Returns 1 if the value at acceptable index index1 satisfies op when compared with the value at acceptable index index2, following the semantics of the corresponding Lua operator (that is, it may call metamethods). Otherwise returns 0. Also returns 0 if any of the indices is non valid. 
The value of op must be one of the following constants: 
LUA_OPEQ: compares for equality (==) 
LUA_OPLT: compares for less than (<) 
LUA_OPLE: compares for less or equal (<=) 
*/
/* !!! a semântica dos operadores de comparação ainda não está definida - e nem como lidar com metamethods*/
ret <-- lua_compare (index1, index2, oper) =
PRE
    index1 : acceptable_indexes &
    index2 : acceptable_indexes &
    oper : {LUA_OPEQ, LUA_OPLT, LUA_OPLE}
THEN
    IF (index1 > stack_top or index2 > stack_top)
    THEN
        ret := 0
    ELSE
        LET 
            value1,value2 
        BE 
            value1 = stack(convert_index(stack_top,index1)) &
            value2 = stack(convert_index(stack_top,index2))
        IN
            CASE oper OF
            EITHER LUA_OPEQ THEN ret :: {0,1}
                OR LUA_OPLT THEN ret :: {0,1}
                OR LUA_OPLE THEN ret :: {0,1}
                END
            END
        END
    END
END;

/* lua_gettop [-0, +0, ?]
int lua_gettop (lua_State *L); Returns the index of the top element in the stack. Because indices start at 1, this result is equal to the number of elements in the stack (and so 0 means an empty stack). 
*/
ret <-- lua_gettop =
    ret := stack_top;
    
/*   
lua_rawequal  [-0, +0, ?] 
int lua_rawequal (lua_State *L, int index1, int index2); 
Returns 1 if the two values in acceptable indices index1 and index2 are primitively equal (that is, 
without calling metamethods). Otherwise returns 0. Also returns 0 if any of the indices are non valid. 
*/
/* !!! atenção - por causa da "casca" com vários lixos em cada lua_value, o igual básico de B não tem o significado desejado. Descascar obriga a comparar valores de cada tipo separadamente - acho que é a única solução, mas ainda não está feito assim aqui. Ou criara uma função genérica para descascar que teste o tipo do dado antes de chamar a função correspondente. */
ret <-- lua_rawequal (index1, index2) =
PRE
    index1 : acceptable_indexes &
    index2 : acceptable_indexes
THEN
      IF (index1 /: valid_indexes or index2 /: valid_indexes)
    THEN
        ret := 0
    ELSE
        LET 
            value1,value2 
        BE 
            value1 = stack(convert_index(stack_top,index1)) &
            value2 = stack(convert_index(stack_top,index2))
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
END;

/* lua_rawlen [-0, +0, ?]
size_t lua_rawlen (lua_State *L, int index);
Returns the raw "length" of the value at the given acceptable index: for
strings, this is the string length; for tables, this is the result of the
length operator ('#') with no metamethods; for userdata, this is the size of
the block of memory allocated for the userdata; for other values, it is 0. 
*/
ret <-- lua_rawlen (index) = 
PRE
    index : acceptable_indexes 
THEN
    LET
        vv
    BE 
        vv = stack(convert_index(stack_top,index))
    IN
        IF
            vv : STRING_LUA_VALUES
        THEN 
            ret := 1
        ELSIF
            vv : TABLE_LUA_VALUES
        THEN
            ret := len(vv,table_heap)
        ELSIF
            vv : USERDATA_LUA_VALUES
        THEN
            ret := udsize_of(userdata_heap(lua_userdata_from_lua_value(vv)))
        ELSE
            ret := 0
        END
    END
END



END
