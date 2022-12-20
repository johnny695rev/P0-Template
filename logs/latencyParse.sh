latency=$(grep Response logs.log | cut -f 2 -d k | cut -f 1 -d m | cut -c 2-)
echo "$latency"
count=0
less=0
for code in $latency
do
        ((count++))
        if [ $(awk "BEGIN {print !($code < 200.00)}") -eq 0 ]
        then
                ((less++))
        fi
done
result=$(awk "BEGIN {print $less / $count * 100; exit}")
echo "$result% requests within 200 ms"