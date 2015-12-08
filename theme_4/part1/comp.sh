#!/bin/bash

javac -d build src/org/myorg/*.java

jar -cvf ./theme4.jar -C build/ .

rm -rf output
/usr/bin/hadoop-0.20 jar theme4.jar org.myorg.JoinMain sbml/model/listOfReactions/reaction/listOfReactants/listOfReactants.attr sbml/model/listOfReactions/reaction/listOfProducts/speciesReference/speciesReference.attr output/
