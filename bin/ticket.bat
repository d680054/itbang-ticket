@echo off

setlocal

rem Minimal version to run JMeter
set MINIMAL_VERSION=1.8.0

for /f "tokens=3" %%g in ('java -version 2^>^&1 ^| findstr /i "version"') do (
   @REM @echo Debug Output: %%g
   set JAVAVER=%%g
)
if not defined JAVAVER (
   @echo Not able to find Java executable or version. Please check your Java installation.
   set ERRORLEVEL=2
   goto pause
)
set JAVAVER=%JAVAVER:"=%
for /f "delims=. tokens=1-3" %%v in ("%JAVAVER%") do (
   set current_minor=%%w
)

for /f "delims=. tokens=1-3" %%v in ("%MINIMAL_VERSION%") do (
   set minimal_minor=%%w
)

if not defined current_minor (
   @echo Not able to find Java executable or version. Please check your Java installation.
   set ERRORLEVEL=2
   goto pause
)
rem @echo Debug: CURRENT=%current_minor% - MINIMAL=%minimal_minor%
if %current_minor% LSS %minimal_minor% (
   @echo Error: Java version -- %JAVAVER% -- is too low to run ITBang-Ticket. Needs a Java version greater than or equal to %MINIMAL_VERSION%
   set ERRORLEVEL=3
   goto pause
)

if .%J_LAUNCH% == . set J_LAUNCH=java.exe


if .%TICKET_BIN% == . set TICKET_BIN=%~dp0


%J_LAUNCH% -Dapp=itbang -jar %TICKET_BIN%/bin/ticket-1.0.jar