@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  br.ufrn.forall.betagui startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

@rem Add default JVM options here. You can also use JAVA_OPTS and BR_UFRN_FORALL_BETAGUI_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windowz variants

if not "%OS%" == "Windows_NT" goto win9xME_args
if "%@eval[2+2]" == "4" goto 4NT_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*
goto execute

:4NT_args
@rem Get arguments from the 4NT Shell from JP Software
set CMD_LINE_ARGS=%$

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\br.ufrn.forall.betagui-1.2.jar;%APP_HOME%\lib\probcliparser-2.4.18-SNAPSHOT.jar;%APP_HOME%\lib\unittestgenerator.jar;%APP_HOME%\lib\br.ufrn.forall.beta-1.2.jar;%APP_HOME%\lib\br.ufrn.forall.betaanimator-1.2.jar;%APP_HOME%\lib\br.ufrn.forall.betacommon-1.2.jar;%APP_HOME%\lib\br.ufrn.forall.betaparser-1.2.jar;%APP_HOME%\lib\br.ufrn.forall.datacomb-1.2.jar;%APP_HOME%\lib\de.prob.core.kernel-2.0.0-milestone-14-SNAPSHOT.jar;%APP_HOME%\lib\commons-io-2.4.jar;%APP_HOME%\lib\mockito-all-1.9.5.jar;%APP_HOME%\lib\xom-1.2.10.jar;%APP_HOME%\lib\xml-resolver-1.2.jar;%APP_HOME%\lib\commons-vfs2-2.0.jar;%APP_HOME%\lib\commons-logging-1.1.1.jar;%APP_HOME%\lib\commons-jxpath-1.3.jar;%APP_HOME%\lib\commons-jexl-2.1.1.jar;%APP_HOME%\lib\commons-digester-1.8.1.jar;%APP_HOME%\lib\commons-configuration-1.9.jar;%APP_HOME%\lib\commons-collections-3.2.1.jar;%APP_HOME%\lib\commons-codec-1.6.jar;%APP_HOME%\lib\commons-beanutils-1.8.3.jar;%APP_HOME%\lib\xstream-1.4.3.jar;%APP_HOME%\lib\jetty-io-8.0.0.M3.jar;%APP_HOME%\lib\groovy-all-2.3.0.jar;%APP_HOME%\lib\gson-1.7.1.jar;%APP_HOME%\lib\jung-jai-2.0.1.jar;%APP_HOME%\lib\prologlib-2.4.22-SNAPSHOT.jar;%APP_HOME%\lib\guava-14.0.1.jar;%APP_HOME%\lib\bparser-2.4.22-SNAPSHOT.jar;%APP_HOME%\lib\org.eventb.core.ast-2.6.0.r15029.jar;%APP_HOME%\lib\slf4j-api-1.6.1.jar;%APP_HOME%\lib\answerparser-2.4.22-SNAPSHOT.jar;%APP_HOME%\lib\cliparser-2.4.22-SNAPSHOT.jar;%APP_HOME%\lib\unicode-2.4.22-SNAPSHOT.jar;%APP_HOME%\lib\jetty-servlet-8.0.0.M3.jar;%APP_HOME%\lib\jna-3.4.0.jar;%APP_HOME%\lib\jetty-util-8.0.0.M3.jar;%APP_HOME%\lib\tla2bAST-1.0.5-SNAPSHOT.jar;%APP_HOME%\lib\junit-4.8.2.jar;%APP_HOME%\lib\jetty-server-8.0.0.M3.jar;%APP_HOME%\lib\ltl-dsl-1.0.0.jar;%APP_HOME%\lib\commons-cli-1.2.jar;%APP_HOME%\lib\theorymapping-2.4.22-SNAPSHOT.jar;%APP_HOME%\lib\jung-api-2.0.1.jar;%APP_HOME%\lib\pegdown-1.1.0.jar;%APP_HOME%\lib\ltlparser-2.4.22-SNAPSHOT.jar;%APP_HOME%\lib\guice-3.0.jar;%APP_HOME%\lib\parserbase-2.4.22-SNAPSHOT.jar;%APP_HOME%\lib\spock-core-0.7-groovy-2.0.jar;%APP_HOME%\lib\logback-core-0.9.29.jar;%APP_HOME%\lib\jfmi-1.0.2-SNAPSHOT.jar;%APP_HOME%\lib\antlr4-runtime-4.0.jar;%APP_HOME%\lib\jung-algorithms-2.0.1.jar;%APP_HOME%\lib\jung-graph-impl-2.0.1.jar;%APP_HOME%\lib\compiler-0.8.12.jar;%APP_HOME%\lib\logback-classic-0.9.29.jar;%APP_HOME%\lib\jsoup-1.7.2.jar;%APP_HOME%\lib\jetty-webapp-8.0.0.M3.jar;%APP_HOME%\lib\guice-servlet-3.0.jar;%APP_HOME%\lib\xercesImpl-2.8.0.jar;%APP_HOME%\lib\xalan-2.7.0.jar;%APP_HOME%\lib\maven-scm-api-1.4.jar;%APP_HOME%\lib\maven-scm-provider-svnexe-1.4.jar;%APP_HOME%\lib\xmlpull-1.1.3.1.jar;%APP_HOME%\lib\xpp3_min-1.1.4c.jar;%APP_HOME%\lib\jung-visualization-2.0.1.jar;%APP_HOME%\lib\jsr305-1.3.9.jar;%APP_HOME%\lib\jetty-security-8.0.0.M3.jar;%APP_HOME%\lib\tlatools-1.0.0-SNAPSHOT.jar;%APP_HOME%\lib\servlet-api-3.0.20100224.jar;%APP_HOME%\lib\jetty-continuation-8.0.0.M3.jar;%APP_HOME%\lib\jetty-http-8.0.0.M3.jar;%APP_HOME%\lib\collections-generic-4.01.jar;%APP_HOME%\lib\parboiled-core-1.0.2.jar;%APP_HOME%\lib\parboiled-java-1.0.2.jar;%APP_HOME%\lib\javax.inject-1.jar;%APP_HOME%\lib\aopalliance-1.0.jar;%APP_HOME%\lib\cglib-2.2.1-v20090111.jar;%APP_HOME%\lib\junit-dep-4.10.jar;%APP_HOME%\lib\hamcrest-core-1.3.jar;%APP_HOME%\lib\org.abego.treelayout.core-1.0.1.jar;%APP_HOME%\lib\colt-1.2.0.jar;%APP_HOME%\lib\jetty-xml-8.0.0.M3.jar;%APP_HOME%\lib\plexus-utils-1.5.6.jar;%APP_HOME%\lib\maven-scm-provider-svn-commons-1.4.jar;%APP_HOME%\lib\regexp-1.3.jar;%APP_HOME%\lib\asm-3.3.1.jar;%APP_HOME%\lib\asm-util-3.3.1.jar;%APP_HOME%\lib\asm-tree-3.3.1.jar;%APP_HOME%\lib\asm-analysis-3.3.1.jar;%APP_HOME%\lib\concurrent-1.3.4.jar;%APP_HOME%\lib\commons-lang-2.6.jar;%APP_HOME%\lib\xml-apis-2.0.2.jar

@rem Execute br.ufrn.forall.betagui
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %BR_UFRN_FORALL_BETAGUI_OPTS%  -classpath "%CLASSPATH%" views.Application %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable BR_UFRN_FORALL_BETAGUI_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%BR_UFRN_FORALL_BETAGUI_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
