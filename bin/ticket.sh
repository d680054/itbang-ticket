#!/bin/sh
if type -p java; then
    _java=java
elif [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]];  then
    _java="$JAVA_HOME/bin/java"
else
    echo "*****************Error: no java installed***************"
    exit
fi

if [[ "$_java" ]]; then
    version=$("$_java" -version 2>&1 | awk -F '"' '/version/ {print $2}')
    echo version "********************found Java Version: $version******************"
    if [[ "$version" < "1.8" ]]; then
        echo need java version 1.8
    fi
fi

pid=$(jps -v | grep "itbang" | awk '{print $1}')
kill -9 $pid
java -Dapp=itbang -jar ./bin/ticket*.jar & 2>&1
