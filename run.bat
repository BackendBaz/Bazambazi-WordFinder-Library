@echo off
chcp 65001 > nul
set JAVA_OPTS=-Dfile.encoding=UTF-8
java %JAVA_OPTS% -jar target\bazambazi-wordfinder-0.0.1-SNAPSHOT.jar