#!/bin/bash


javaregexer="ManuellaRegexR"

allManFile="allMan"
outputFile="manTitles"

manDirectory="man"
manPrefix="man"

apropos -r .+ > $allManFile


#TODO - check if .java file exists?
if [ ! -f "$javaregexer.class" ]; 
    then javac "$javaregexer.java"
fi


java $javaregexer $allManFile $outputFile


if [ ! -d $manDirectory ];
    then mkdir $manDirectory
fi



while read line
do    
    man "$line" > "$manDirectory/$manPrefix.$line" 2> /dev/null
done < $outputFile
