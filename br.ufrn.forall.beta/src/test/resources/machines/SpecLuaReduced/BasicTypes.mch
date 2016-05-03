/* BasicTypes
 * Author: 
 * Creation date: 28/04/14
 */
MACHINE
    BasicTypes

SETS
    LUA_NIL = {nil};
    LUA_BOOLEAN = {false, true};
    
    /* labels corresponding to the types */    
    LUA_TYPES = {LUA_TNIL, LUA_TNUMBER, LUA_TBOOLEAN, LUA_TNONE};
    
    LUA_OP_KEYS = {LUA_OPADD, LUA_OPSUB, LUA_OPMUL, LUA_OPDIV, LUA_OPMOD, LUA_OPPOW, LUA_OPUNM};
    
    LUA_CMP_KEYS = {LUA_OPEQ, LUA_OPLT, LUA_OPLE}


CONSTANTS
    LUA_NUMBER,
    LUA_VALUES,
    
    NIL_LUA_VALUES,
    BOOLEAN_LUA_VALUES,
    NUMBER_LUA_VALUES,
    
    NOT_LUA_VALUES,
    
    VALUE_TUPLES,
    value_type,
    not_a_lua_value
    
PROPERTIES
    
    /* partial solution for Number - only integers */	
    LUA_NUMBER = INT &
    
    VALUE_TUPLES =
    (
        LUA_NIL * 
        LUA_BOOLEAN * 
        LUA_NUMBER
    ) &
    
    /* a Lua value is modeled by a record with the first field being the label which indicates where to find the value according to its type */
    LUA_VALUES =  LUA_TYPES * VALUE_TUPLES &
     
    value_type : LUA_VALUES --> LUA_TYPES &      
    value_type = prj1(LUA_TYPES,VALUE_TUPLES) &

    NIL_LUA_VALUES <: LUA_VALUES &
    NIL_LUA_VALUES = {vv|vv : LUA_VALUES & value_type(vv) = LUA_TNIL} &    
    
    BOOLEAN_LUA_VALUES <: LUA_VALUES &
    BOOLEAN_LUA_VALUES = {vv|vv : LUA_VALUES & value_type(vv)= LUA_TBOOLEAN} &
    
    NUMBER_LUA_VALUES <: LUA_VALUES &
    NUMBER_LUA_VALUES = {vv|vv : LUA_VALUES & value_type(vv) = LUA_TNUMBER} &
    
    NOT_LUA_VALUES <: LUA_VALUES &
    NOT_LUA_VALUES = {vv|vv : LUA_VALUES & value_type(vv) = LUA_TNONE} &
    
    not_a_lua_value : NOT_LUA_VALUES

ASSERTIONS 
    not(not_a_lua_value : NIL_LUA_VALUES)&
    not(not_a_lua_value : NUMBER_LUA_VALUES)&
    not(not_a_lua_value : BOOLEAN_LUA_VALUES)&
    value_type(not_a_lua_value) = LUA_TNONE

END
