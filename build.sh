#!/bin/bash
webapp="$(cat webapp.txt)"

rm -rf build && mkdir -p build
rm -rf target && mkdir -p target
cp -r app/* build
export CLASSPATH=.:$(ls complibs/*jar|tr '\n' ':')

shopt -s globstar
javac -d build/WEB-INF/classes src/**/*java && 
  jar -cf target/$webapp.war -C build . && 
  echo "build success" &&
  exit 0
exit 1

