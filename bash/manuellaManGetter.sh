#!/bin/bash

#TODO(s): 
#	-Check if any of the file-related operations could error-out.
#	-Optimization wanted: reward 1 000 Indian Rupees
#	-The 'progress bar' is.. well... somewhat barbaric and too aproximate.


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

lineCount=$(wc -l $outputFile | grep -Eo ^[0-9]+)

counter=0
increment=$((lineCount/100))
progress=0

echo $lineCount

echo -n 0:%
for ((i=0;i<100;i++));
do
    echo -n '.'
done
echo -n ..:100%

echo -ne "\n  :"

while read line
do    
    ((counter++))
    man "$line" > "$manDirectory/$manPrefix.$line" 2> /dev/null

    if (( counter>=progress )); then
        echo -ne \# 
        progress=$((progress + increment))
    fi
done < $outputFile
echo -e "\n DONE"
