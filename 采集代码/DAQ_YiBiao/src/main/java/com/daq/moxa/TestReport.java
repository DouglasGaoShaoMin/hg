package com.daq.moxa;

import com.daq.modebusTCP.ModBusTcpUntil;
import com.daq.modebusTCP.ModbusTCPServer;
import com.daq.model.CodeProperties;
import com.daq.model.MainProperties;
import com.daq.serialException.TooManyListeners;
import com.daq.task.SendCodeTimer;
import com.daq.until.ReadProperties;
import gnu.io.SerialPort;

import java.net.ServerSocket;
import java.util.List;
import java.util.Timer;

/**
 * User: gaosm
 * Date: 2018/6/7
 * Time: 17:40
 */
public class TestReport {
    public static void main(String[] args) {
        //--------------------------------扫描可用串口-------------------------------------1
        List<String> portlist = SerialTool1.findPort();
        System.out.println("扫描到:" + portlist.size() + "个串口:" + portlist.toString());
        //--------------------------------读取串口配置文件---------------------------------2
        System.out.println("开始读取串口配置文件!");
        ReadProperties readProperties = new ReadProperties();
        List<MainProperties> mainConfList = readProperties.LoadMainConf();
        System.out.println("读取串口配置文件完成,开始打开串口!");
        //--------------------------------打开串口-----------------------------------------3
        System.out.println("开始打开串口!");
        List<SerialPort> serialPortList = SerialTool1.openPortLocalhost(mainConfList);
        System.out.println("串口启动成功,开始发送数据!");
        //TODO  该处可以增加启动守护线程循环探测端口是否掉线(判空),如果掉线进行重连.
        //--------------------------------读取发送数据配置文件-----------------------------4
        System.out.println("读取命令配置文件!");
        List<CodeProperties> codePropertiesList = readProperties.ReadCodeProperties();
        System.out.println("读取配置文件引入全部完成!下面启动发数定时器!");
        //--------------------------------发送数据-----------------------------------------5
        //--------------------------------发送数据_打开串口监听并启动发数定时器----------------6
        Timer timer = new Timer(false);
        for (int i = 0; i < serialPortList.size(); i++) {
            try {
                System.out.println("启动串口监听!");
                SerialTool1.addListener(serialPortList.get(i), new SerialListenerTest(serialPortList.get(i)));
                System.out.println("串口监听成功!启动定时发送器!");
            } catch (TooManyListeners tooManyListeners) {
                tooManyListeners.printStackTrace();
                System.out.println("串口监听器添加失败!");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            System.out.println("启动定时发送器!");
            timer.schedule(new SendCodeTimer(serialPortList.get(i), codePropertiesList), 3000);
            System.out.println("启动定时发送器完成!下面创建Socket连接");
        }
/*        //--------------------------------ModbusTCP上传数据----------------------------------7
        System.out.println("开始创建socket连接!");
        ModBusTcpUntil modBusTcpUntil = new ModBusTcpUntil();
        ServerSocket serverSocket = modBusTcpUntil.OpenSocket();
        System.out.println("socket连接已创建!下面开始监听并根据请求返回数据!");
        Timer timer1 = new Timer(false);
        timer1.schedule(new ModbusTCPServer(serverSocket, codePropertiesList), 3000);*/
    }
}
