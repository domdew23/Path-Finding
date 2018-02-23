@echo off

javac -d bin src/*.java

java -Xmx4096m -cp bin main
