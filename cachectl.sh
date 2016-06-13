#!/bin/sh
ARGV="$@"
JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_65.jdk/Contents/Home

PROG_HOME=/WS/MasterCache/projects/MasterCache
JAR_FILE=build/libs/master-cache-0.1.0.jar
export SPRING_PROFILES_ACTIVE=default  # default | test | prod

# ===
JAVAEXE=$JAVA_HOME/bin/java
JARPATH=$PROG_HOME/$JAR_FILE
PIDFILE=$PROG_HOME/pid
export SPRING_CONFIG_LOCATION=$PROG_HOME/config/
export LOGGING_CONFIG=$SPRING_CONFIG_LOCATION/logback.xml


case $ARGV in
start)
        echo exec: nohup $JAVAEXE -jar $JARPATH
        nohup $JAVAEXE  -jar $JARPATH > /dev/null &
        echo $! > $PIDFILE
        ;;
run)
        $JAVAEXE  -jar $JARPATH
        ;;

stop)
        kill -9 `cat $PIDFILE`
        ps ax | grep master-cache
    ;;
esac

exit $ERROR