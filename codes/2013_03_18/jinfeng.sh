#!/bin/bash
echo "begin"

for i in `find QAPlatform-UITest_Prof/ -name *.java|grep -v pageObjects|grep -v CommonOpt`
do
	grep -H '"//' $i
done

echo "end"
