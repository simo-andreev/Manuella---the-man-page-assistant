#!/bin/bash


javaregexer="ManuellaRegexR"
allManFile="allMan"

apropos -r .+ > $allManFile

#TODO - check if .java file exists?
if [ ! -f "$javaregexer.class" ]; 
    then javac "$javaregexer.java"
fi

java $javaregexer $allManFile

