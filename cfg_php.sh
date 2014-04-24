#!/bin/bash
#make a directory for bytecode files
echo 'making output directory for bytecode';
mkdir bytecode;
#compile all php files in test_files
for f in `find $1 -name '*.php'`;
do
  echo 'Compiling '$f;
  fileName= basename $f;
  #echo $fileName;
  #TODO: Redirect bytecode to the folder bytecode/
  hhvm -v Eval.DumpBytecode=1 -l $f 2> $f.bc
done

#run the jar file:
java -jar cfg_php.jar $1 
