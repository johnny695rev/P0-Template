httpCodes=$(grep Response logs.log | cut -f 2 -d " " | cut -c 2-)
echo "$httpCodes"
httpR=0
httpF=0

for code in $httpCodes
do
        ((httpR++))
        if [ $code -eq 500 ]
        then
                ((httpF++))
        fi
done

httpS=$(($httpR - $httpF))
result=$(awk "BEGIN {print $httpS / $httpR * 100; exit}")
echo "HTTP success rate: $result%"