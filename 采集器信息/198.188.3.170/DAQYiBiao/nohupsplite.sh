this_path=$(cd `dirname $0`;pwd)   
cd $this_path  
echo $this_path  
current_date=`date -d "-1 day" "+%Y%m%d"`  
echo $current_date  
split -b 35535000 -d -a 4 myout.file   log_${current_date}_  
cat /dev/null > myout.file  