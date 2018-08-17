#!/bin/sh
# Check if Java is present and the minimal version requierement
MINIMAL_VERSION=1.8.0

_java=`type java | awk '{ print $ NF }'`
CURRENT_VERSION=`"$_java" -version 2>&1 | awk -F'"' '/version/ {print $2}'`
minimal_version=`echo $MINIMAL_VERSION | awk -F'.' '{ print $2 }'`
current_version=`echo $CURRENT_VERSION | awk -F'.' '{ print $2 }'`
if [ $current_version ]; then
        if [ $current_version -lt $minimal_version ]; then
                 echo "Error: Java version is too low to run ITBang-Ticket. Needs at least Java >= ${MINIMAL_VERSION}." 
                 exit 1
        fi
    else
         echo "Not able to find Java executable or version. Please check your Java installation."
         exit 1
fi

pid=$(jps -v | grep "itbang" | awk '{print $1}')
kill -9 $pid
java -Dapp=itbang -jar ./bin/ticket*.jar & 2>&1
