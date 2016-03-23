#!/usr/bin/env bash

PDIR=$(pwd)
mkdir -p lib
mkdir -p other
cd /tmp
git clone https://github.com/Z3Prover/z3 -b master
cd z3
git checkout z3-java
python scripts/mk_make.py --java
cd build
make all
cp com.microsoft.z3.jar $PDIR/lib
cp libz3java.* $PDIR/lib
mkdir -p $PDIR/other/com/microsoft
cd ..
cp -r src/api/java $PDIR/other/com/microsoft/z3
