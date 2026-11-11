@echo off
call mvn compile dependency:copy-dependencies
java -cp "target/classes;target/dependency/*" com.tsp4.Launcher