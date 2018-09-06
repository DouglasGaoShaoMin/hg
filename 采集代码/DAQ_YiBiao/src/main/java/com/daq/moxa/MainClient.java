package com.daq.moxa;

import com.daq.modebusTCP.ModbusTCPServer;
import com.daq.model.CodeProperties;
import com.daq.model.MainProperties;
import com.daq.serialException.TooManyListeners;
import com.daq.serialPort.SerialTool;
import com.daq.task.SendCodeTimer;
import com.daq.task.SerialListener;
import com.daq.task.ShouHuTimer;
import com.daq.until.ReadProperties;
import gnu.io.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Timer;

/**
 * User: gaosm
 * Date: 2018/5/19
 * Time: 15:24
 */
public class MainClient {
    private final static Logger logger = LoggerFactory.getLogger(MainClient.class);
    public static void main(String[] args) {
        //--------------------------------扫描可用串口-------------------------------------1
        List<String> portlist = SerialTool.findPort();
        logger.info("扫描到:" + portlist.size() + "个串口:" + portlist.toString());
        //--------------------------------读取串口配置文件---------------------------------2
        logger.info("开始读取串口配置文件!");
        ReadProperties readProperties = new ReadProperties();
        List<MainProperties> mainConfList = readProperties.LoadMainConf();
        logger.info("读取串口配置文件完成,开始打开串口!");
        //--------------------------------打开串口-----------------------------------------3
        logger.info("开始打开串口!");
        List<SerialPort> serialPortList = SerialTool.openPortLocalhost(mainConfList);
        logger.info("串口启动成功,开始发送数据!");
        //TODO  该处可以增加启动守护线程循环探测端口是否掉线(判空),如果掉线进行重连.
        //--------------------------------读取发送数据配置文件-----------------------------4
        logger.info("读取命令发送配置文件!");
        List<CodeProperties> codePropertiesList = readProperties.ReadCodeProperties();
        logger.info("读取配置文件引入全部完成!下面启动发数定时器!");
        //--------------------------------发送数据-----------------------------------------5
        //--------------------------------发送数据_打开串口监听并启动发数定时器------------6
        Timer timer = new Timer(false);
        for (int i = 0; i < serialPortList.size(); i++) {
            try {
                logger.info("启动接收数据串口监听!");
                SerialTool.addListener(serialPortList.get(i), new SerialListener(serialPortList.get(i)));
                logger.info("串口监听成功!启动定时发送数据事务!");
            } catch (TooManyListeners tooManyListeners) {
                tooManyListeners.printStackTrace();
                logger.info("串口监听器添加失败!");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            logger.info("启动定时发送数据事务!");
            timer.schedule(new SendCodeTimer(serialPortList.get(i),codePropertiesList,mainConfList.get(0).getEverySendTime()), 3000);
            logger.info("启动定时发送数据事务完成!下面创建Socket连接");
        }
        //--------------------------------ModbusTCP上传数据----------------------------------7
        logger.info("开始创建socket连接!");
        //ModBusTcpUntil modBusTcpUntil = new ModBusTcpUntil();
        //serverSocket = modBusTcpUntil.OpenSocket();
        logger.info("socket连接已创建!下面开始监听并根据请求返回数据!");
        Timer timer1 = new Timer(false);
        try {
            timer1.schedule(new ModbusTCPServer(codePropertiesList), 3000, 3000);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        /*Timer timer2 = new Timer(false);
        timer2.schedule(new ShouHuTimer(), 3000,3000);*/
    }
}
