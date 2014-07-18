/* Functions
 * Author: Anamaria
 * Creation date: Thu Nov 22 2012
 */
MACHINE
    Functions
    
SEES 
    BasicTypes,
    NumberADT,
    BooleanADT,
    NilADT,
    StringADT,
    FunctionADT,
    UserdataADT,
    LightuserdataADT,
    TableADT,
    ThreadADT,
    Aux,
    ADTConvertions
    
INCLUDES 
    Memory
    
    
OPERATIONS
/*
[-(nargs+1), +nresults, e]
void lua_call (lua_State *L, int nargs, int nresults);

Calls a function.

To call a function you must use the following protocol: first, the function to be called is pushed onto the stack; then, the arguments to the function are pushed in direct order; that is, the first argument is pushed first. Finally you call lua_call; nargs is the number of arguments that you pushed onto the stack. All arguments and the function value are popped from the stack when the function is called. The function results are pushed onto the stack when the function returns. The number of results is adjusted to nresults, unless nresults is LUA_MULTRET. In this case, all results from the function are pushed. Lua takes care that the returned values fit into the stack space. The function results are pushed onto the stack in direct order (the first result is pushed first), so that after the call the last result is on the top of the stack.

Any error inside the called function is propagated upwards (with a longjmp).

*/
lua_call (nargs, nresults) =
PRE
    nargs : NAT &
    nresults : NAT &
    nargs < stack_top & 
    stack_top - (nargs + 1) + nresults <= max_stack_top &
    stack(stack_top - nargs) : FUNCTION_LUA_VALUES 
THEN
/*    LET res
    BE
        res = function_application(table_heap,userdata_heap,
            %ii.(ii:1..nargs+1|stack(stack_top - nargs + ii - 1)),
            nresults)
    IN */
        LET 
            ss, newth, newuh
        BE 
            ss = select_results(function_application(table_heap,userdata_heap,
            %ii.(ii:1..nargs+1|stack(stack_top - nargs + ii - 1)),
            nresults)) &
            newth = select_table_heap(function_application(table_heap,userdata_heap,
            %ii.(ii:1..nargs+1|stack(stack_top - nargs + ii - 1)),
            nresults)) &
            newuh = select_userdata_heap(function_application(table_heap,userdata_heap,
            %ii.(ii:1..nargs+1|stack(stack_top - nargs + ii - 1)),
            nresults))        
        IN
            mem_call (nargs + 1, ss, newth, newuh)
        END
 /*   END */
END
END
