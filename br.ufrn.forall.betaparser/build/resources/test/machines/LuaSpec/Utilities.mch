/* Utilities
 * Author: Anamaria
 * Creation date: Fri Jul 20 2012
 */
MACHINE
    Utilities
    
SETS
    GC_COMMANDS = {LUA_GCSTOP, LUA_GCRESTART, LUA_GCCOLLECT, LUA_GCCOUNT,  LUA_GCCOUNTB, LUA_GCSTEP, LUA_GCSETPAUSE, LUA_GCSETSTEPMUL, LUA_GCISRUNNING,
LUA_GCGEN, LUA_GCINC}

OPERATIONS
    
/*    
    
lua_gc [-0, +0, e] 
int lua_gc (lua_State *L, int what, int data); 
Controls the garbage collector. 
This function performs several tasks, according to the value of the parameter what: 
LUA_GCSTOP: stops the garbage collector. 
LUA_GCRESTART: restarts the garbage collector. 
LUA_GCCOLLECT: performs a full garbage-collection cycle. 
LUA_GCCOUNT: returns the current amount of memory (in Kbytes) in use by Lua. 
LUA_GCCOUNTB: returns the remainder of dividing the current amount of bytes of memory in use 
by Lua by 1024. 
LUA_GCSTEP: performs an incremental step of garbage collection. The step "size" is controlled 
by data (larger values mean more steps) in a non-speci?ed way. If you want to control the step 
size you must experimentally tune the value of data. The function returns 1 if the step ?nished a 
garbage-collection cycle. 
LUA_GCSETPAUSE: sets data as the new value for the pause of the collector (see §2.5). The 
function returns the previous value of the pause. 
LUA_GCSETSTEPMUL: sets data as the new value for the step multiplier of the collector (see 
§2.5). The function returns the previous value of the step multiplier. 
LUA_GCISRUNNING: returns a boolean that tells whether the collector is running (i.e., not 
stopped). 
LUA_GCGEN: changes the collector to generational mode (see §2.5). 
LUA_GCINC: changes the collector to incremental mode. This is the default mode. 
For more details about these options, see collectgarbage. 
*/
lua_gc (what, data) =
PRE
    what : GC_COMMANDS &
    data : INT
THEN
    skip
END

    


END
