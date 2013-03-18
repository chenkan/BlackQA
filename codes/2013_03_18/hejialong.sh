#! /bin/bash
echo "bigin to find"
INIT_PATH="/home/hzyuqf/QA_BLACK/QAPlatform-UITest_Prof/src"
function ergodic(){
for file in ` ls $1 `
do
   if [ -d $1"/"$file ]&&[ $1"/"$file != $INIT_PATH'/com/netease/qaplatform/pageObjects' ]
then
ergodic $1"/"$file
else
local path=$1"/"$file  
local name=$file 
echo "$name" | grep -q ".java" 
if [ $? -eq 0 ]&&[ $path != $INIT_PATH"/com/netease/qaplatform/CommonOpt.java" ]; then 
  echo "$path is a java file"
  grep '"//' $path
fi    
fi
        done
}
ergodic $INIT_PATH 
