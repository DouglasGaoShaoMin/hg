sudo setinterface /dev/ttyM0 1
sudo setinterface /dev/ttyM1 1
sudo nohup java com.daq.moxa.MainClient >/dev/null 2>&1