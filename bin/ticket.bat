@echo off
if "%JAVA_HOME%"=="" echo Error: JAVA_HOME is not defined.

"%JAVA_HOME%/bin/java" -Dapp=itbang -jar ./bin/ticket*.jar