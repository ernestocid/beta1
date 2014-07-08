/* LogFile
 * Author: Anamaria e Noemi
 * Creation date: 5/2/13
 */
MACHINE
    LogFile
    
SEES
    Logs
    
VARIABLES
    log_file
    
INVARIANT
    log_file <: LOG
        
INITIALISATION
    log_file :: POW(LOG)
    
OPERATIONS
    ret <-- group_status =
    BEGIN
        LET
            st_set
        BE 
            st_set = {used | used: RET_STATUS & #log . (log : log_file & log'st = used)}
        IN
            ret := % xx. (xx : st_set | card({log| log : log_file & log'st = xx}))
        END        
    END;
    
    ret <-- group_method =
    BEGIN
        LET
            method_set
        BE 
            method_set = {used | used: METHOD & #log . (log : log_file & log'comm'http = used)}
        IN
            ret := % xx. (xx : method_set | card({log| log : log_file & log'comm'http = xx}))
        END        
    END;
     
    ret <-- group_user =
    BEGIN
        LET
            user_set
        BE 
            user_set = {uses | uses: USER & #log . (log : log_file & log'comm'access'user = uses)}
        IN
            ret := % xx. (xx : user_set | card({log| log : log_file & log'comm'access'user = xx}))
        END        
    END;
    
    country_count <-- group_geo_ip =
    BEGIN
        LET
            country_set
        BE 
            country_set = {cc | cc: COUNTRY & #log . (log : log_file & (geo_ip_city(log'ip))'country = cc)}
        IN
            country_count := % xx. (xx : country_set | card({log| log : log_file & (geo_ip_city(log'ip))'country = xx}))            
        END        
    END
    
        
END
