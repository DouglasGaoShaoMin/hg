package com.daq.task;

import com.daq.serialException.ReadDataFromSerialPortFailure;
import com.daq.serialException.SerialPortInputStreamCloseFailure;
import com.daq.serialPort.SerialTool;
import com.daq.until.DAQMessage;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: gaosm
 * Date: 2018/5/22
 * Time: 14:47
 */
public class SerialListener implements SerialPortEventListener {
    public SerialPort serialPort;
    private long timeok = 0;
    //private DAQMessage daqMessage;
    StringBuffer completeCode = new StringBuffer();

    public SerialListener(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    public SerialListener() {
    }

/*    public SerialListener(SerialPort serialPort, DAQMessage daqMessage) {
        this.serialPort = serialPort;
        this.daqMessage = daqMessage;
    }*/

    /**
     * 处理监控到的串口事件
     */
    public void serialEvent(SerialPortEvent serialPortEvent) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        switch (serialPortEvent.getEventType()) {
            case SerialPortEvent.BI: // 10 通讯中断
                System.out.println("串口通信中断!");
                break;
            case SerialPortEvent.OE: // 7 溢位（溢出）错误
                System.out.println("溢位（溢出）错误");
            case SerialPortEvent.FE: // 9 帧错误
                System.out.println("帧错误");
            case SerialPortEvent.PE: // 8 奇偶校验错误
                System.out.println("奇偶校验错误!");
            case SerialPortEvent.CD: // 6 载波检测
                System.out.println("载波检测!");
            case SerialPortEvent.CTS: // 3 清除待发送数据
                System.out.println("清除待发送数据!");
            case SerialPortEvent.DSR: // 4 待发送数据准备好了
                System.out.println("待发送数据准备好了!");
            case SerialPortEvent.RI: // 5 振铃指示
                System.out.println("振铃指示!");
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2 输出缓冲区已清空
                System.out.println("输出缓冲区已清空!");
                break;
            case SerialPortEvent.DATA_AVAILABLE: // 1 串口存在可用数据
                byte[] data = null;
                try {
                    if (serialPort == null) {
                        System.out.println("端口断开连接");
                    } else {
                        data = SerialTool.readFromPort(serialPort);    //读取数据，存入字节数组
                        //自定义解析过程
                        if (data == null || data.length < 1) { //检查数据是否读取正确
                        } else {
                            if (timeok == 0) {
                                timeok = System.currentTimeMillis();
                            }
                            //将获取到的数据进行转码并输出
                            long timejiange = System.currentTimeMillis() - timeok;
                            if (timejiange < 45) {
                                for (int j = 0; j < data.length; j++) {
                                    //byte数组表示的字符串转换为16进制，
                                    completeCode.append(String.format("%02x", data[j]) + " ");
                                }
                                sendCanSave();
                            } else {
                                completeCode = new StringBuffer();
                                for (int j = 0; j < data.length; j++) {
                                    //byte数组表示的字符串转换为16进制，
                                    completeCode.append(String.format("%02x", data[j]) + " ");
                                }
                                sendCanSave();
                            }
                            timeok = System.currentTimeMillis();
                        }
                    }
                } catch (ReadDataFromSerialPortFailure | SerialPortInputStreamCloseFailure e) {
                    System.out.print("读数据报错!");
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.print("未知异常!");
                }
                break;
        }
    }

    private void sendCanSave() {
        if (completeCode.toString().replace(" ", "").length() >= DAQMessage.getDAQMessage().getReturnLength()) {
            String canSave = completeCode.toString();
            DAQMessage.getDAQMessage().setFlag(true);
            DAQMessage.getDAQMessage().setMsg(canSave);
            completeCode = new StringBuffer();
        }
    }
}