find . -name '*.java' | awk '{ if(/CommonOpt/ || /pageObject/) print > "ignore.txt"; else print }' | xargs grep-E '"//.+"'
