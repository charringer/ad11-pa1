#!/bin/sh
#
# Test the code quickly without using the clumsy ant script.

javac src/Sorter.java -classpath build -d build && java -cp build ads1ss11.pa1.Main
