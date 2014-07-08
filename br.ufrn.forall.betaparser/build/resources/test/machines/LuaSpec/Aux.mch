/* Aux
 * Author: Anamaria
 * Creation date: Wed Jul 18 2012
 */
MACHINE
    Aux
    
SEES
    /* all type information */
    BasicTypes,
    BasicTypeProjections,
    NumberADT,
    BooleanADT,
    NilADT,
    StringADT,
    FunctionADT,
    UserdataADT,
    LightuserdataADT,
    TableADT,
    ThreadADT

CONSTANTS
    convert_index,
    positive_keys,
    table_len,
    len
    
PROPERTIES
    /* function to convert acceptable indexes in their absolute values, returning zero or the value itself in the case of non-acceptable indexes. To be a constant, it cannot depend on the global values of stack, so they have to be passed as arguments to the function. 
    Order of arguments: stack_top, index
*/    
    convert_index : (NAT * INT) --> NAT &
    ! (actual_size, index). 
    (actual_size: NAT & index: INT =>
        (index > 0 => convert_index(actual_size, index) = index) &
        /* stack index (index : -stack_top..-1) */
        (index < 0 & -index <= actual_size  => 
            convert_index(actual_size, index) = actual_size - (-index) + 1) &
        /* non-acceptable index (0 or below -stack_top)*/
        ((index = 0 or -index > actual_size) =>
            				convert_index(actual_size, index) =  0))  &
        
        
    /* auxiliary functions to compute the sequence length of a table */  
    /* positive_keys_of a table is the set of positive numeric keys of the table */
    positive_keys : TABLE_CONTENTS --> POW(LUA_NUMBER) &
    !tt. (tt : TABLE_CONTENTS => 
        positive_keys(tt) = 
        	{nn| nn: LUA_NUMBER & nn > 0 &
        	    #vv. (vv: dom(NUMBER_LUA_VALUES <| tt) & 
        	          nn = lua_number_from_lua_value(vv)) }) &
    
    /* gives the size of a set of numbers on the form 1..nn, for some nn:NAT    
    (includes empty set for nn = 0), or any natural number if s is not 1..nn */
    table_len: POW(LUA_NUMBER) --> NAT &    
    !ss. (ss: POW(LUA_NUMBER) => 
        (#nn. (nn:NAT & ss = 1..nn & table_len(ss) = nn)) or
        not(#nn. (nn:NAT & ss = 1..nn))) &
    
    /*
    !ss. (ss: POW(LUA_NUMBER) => 
        (!nn. (nn: ss => nn: NAT1 & 1..nn <: ss) => 
           ((ss = {} =>  table_len(ss) = 0) &
            (ss /= {} => table_len(ss) = max({nn|nn:NAT1 & nn:ss & 1..nn <: ss})))) &
        (not(!nn. (nn: ss => nn: NAT1 & 1..nn <: ss)) => 
        table_len(ss) : NAT )) &
        */
        
        
    /* function to return the lenght of a lua string or of a sequence represented 
        as a lua table - it is a function of the table heap */
    /* not defined for Lua values other than tables and strings */
    len : LUA_VALUES * (LUA_TABLE +-> TABLE_CONTENTS) +-> NAT &
    
    !(vv,tt). (vv: STRING_LUA_VALUES & tt : (LUA_TABLE +-> TABLE_CONTENTS) 
        => len(vv,tt) = 1) &
    
    !(vv,tt). (vv: TABLE_LUA_VALUES & tt : (LUA_TABLE +-> TABLE_CONTENTS) 
        => len(vv,tt) = table_len(positive_keys(tt(lua_table_from_lua_value(vv)))))
        
     
ASSERTIONS
    
     ! (actual_size, index). 
     (actual_size: NAT & index: INT  => 
         (convert_index(actual_size, index) > actual_size => index > 0 ) )  &
     
     
     ! (actual_size, index). 
     (actual_size: NAT & index: NAT1 => 
         (convert_index(actual_size, index) = index ))  &
     
      ! (actual_size, index). 
     (actual_size: NAT & index: NAT => 
         (convert_index(actual_size, index) = index ))  &
     
      ! (actual_size, index). 
     (actual_size: NAT1 & index: -actual_size..-1 => 
         (convert_index(actual_size, index) : 1..actual_size ))  &
     
     
      ! (actual_size, index). 
     (actual_size: NAT1 & index: -actual_size..-1 => 
         (convert_index(actual_size, index) = actual_size - (-index) + 1)) &
     
     ! (actual_size, index). 
     (actual_size: NAT & index: -actual_size..-1 \/ NAT1 => 
         (convert_index(actual_size, index) : NAT1)) 
     
     
    
END

