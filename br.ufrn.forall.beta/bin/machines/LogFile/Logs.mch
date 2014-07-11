/* Logs
 * Author: Anamaria e Noemi
 * Creation date: 5/2/13
 */
MACHINE
    Logs
    
SETS
    IP;
    DATE;
    RET_STATUS;
    USER;
    PATH;
    HTTP_VERSION;
    METHOD = {GET, HEAD, OPTIONS, POST, PROPFIND, INPUT, DELETE, TRACE, CONNECT, PATCH, PUT};
    CITY;
    COUNTRY
    
CONSTANTS
    LOG,
    COMMAND,
    ACCESS,
    geo_ip_city
    
PROPERTIES
    ACCESS = struct (user: USER, path: PATH) &
    COMMAND = struct (http: METHOD, access: ACCESS, version: HTTP_VERSION) &
    LOG = struct (ip: IP, date: DATE, comm: COMMAND, st: RET_STATUS, num: NAT) &
    geo_ip_city : IP +-> struct (city: CITY, country: COUNTRY)
        
END
