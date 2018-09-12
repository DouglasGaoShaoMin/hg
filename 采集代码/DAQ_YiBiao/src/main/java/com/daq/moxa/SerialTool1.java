package com.daq.moxa;

import com.daq.model.MainProperties;
import com.daq.serialException.*;
import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TooManyListenersException;

/**
 * 串口服务类，提供打开、关闭串口，读取、发送串口数据等服务（采用单例设计模式）
 *
 * @author gao
 */
public class SerialTool1 {

    private static SerialTool1 serialTool = null;

    static {
        //在该类被ClassLoader加载时就初始化一个SerialTool对象
        if (serialTool == null) {
            serialTool = new SerialTool1();
        }
    }

    //私有化SerialTool类的构造方法，不允许其他类生成SerialTool对象
    private SerialTool1() {
    }

    /**
     * 获取提供服务的SerialTool对象
     *
     * @return serialTool
     */
    public static SerialTool1 getSerialTool() {
        if (serialTool == null) {
            serialTool = new SerialTool1();
        }
        return serialTool;
    }


    /**
     * 查找所有可用端口
     *
     * @return 可用端口名称列表
     */
    public static final ArrayList<String> findPort() {

        //获得当前所有可用串口
        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();

        ArrayList<String> portNameList = new ArrayList<>();

        //将可用串口名添加到List并返回该List
        while (portList.hasMoreElements()) {
            String portName = portList.nextElement().getName();
            portNameList.add(portName);
        }

        return portNameList;

    }

    /**
     * 打开串口
     *
     * @param portName   端口名称
     * @param baudrate   波特率
     * @param iDataBit   数据位
     * @param iStopBit   停止位
     * @param sVerifyBit 校验方式:
     * @return 串口对象
     * @throws SerialPortParameterFailure 设置串口参数失败
     * @throws NotASerialPort             端口指向设备不是串口类型
     * @throws NoSuchPort                 没有该端口对应的串口设备
     * @throws PortInUse                  端口已被占用
     */
    public static final SerialPort openPort(String portName, int baudrate, int iDataBit, int iStopBit, int sVerifyBit) throws SerialPortParameterFailure, NotASerialPort, NoSuchPort, PortInUse {

        try {
            //通过端口名识别端口
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);

            //打开端口，并给端口名字和一个timeout（打开操作的超时时间）
            CommPort commPort = portIdentifier.open(portName, 2000);

            //判断是不是串口
            if (commPort instanceof SerialPort) {

                SerialPort serialPort = (SerialPort) commPort;

                try {
                    //设置一下串口的波特率等参数
                    serialPort.setSerialPortParams(baudrate, iDataBit, iStopBit, sVerifyBit);
                } catch (UnsupportedCommOperationException e) {
                    throw new SerialPortParameterFailure();
                }
                System.out.println("Open " + portName + " sucessfully !");
                return serialPort;
            } else {
                //不是串口
                throw new NotASerialPort();
            }
        } catch (NoSuchPortException e1) {
            throw new NoSuchPort();
        } catch (PortInUseException e2) {
            throw new PortInUse();
        }
    }

    /**
     * 关闭串口
     *
     * @param serialport 待关闭的串口对象
     */
    public static void closePort(SerialPort serialPort) {
        if (serialPort != null) {
            serialPort.close();
            serialPort = null;
        }
    }

    /**
     * 往串口发送数据
     *
     * @param serialPort 串口对象
     * @param order      待发送数据
     * @throws SendDataToSerialPortFailure        向串口发送数据失败
     * @throws SerialPortOutputStreamCloseFailure 关闭串口对象的输出流出错
     */
    public static void sendToPort(SerialPort serialPort, byte[] order) throws SendDataToSerialPortFailure, SerialPortOutputStreamCloseFailure {

        OutputStream out = null;

        try {
            out = serialPort.getOutputStream();
            out.write(order);
            out.flush();
        } catch (IOException e) {
            throw new SendDataToSerialPortFailure();
        } finally {
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
            } catch (IOException e) {
                throw new SerialPortOutputStreamCloseFailure();
            }
        }

    }

    /**
     * 从串口读取数据
     *
     * @param serialPort 当前已建立连接的SerialPort对象
     * @return 读取到的数据
     * @throws ReadDataFromSerialPortFailure     从串口读取数据时出错
     * @throws SerialPortInputStreamCloseFailure 关闭串口对象输入流出错
     */
    public static byte[] readFromPort(SerialPort serialPort) throws ReadDataFromSerialPortFailure, SerialPortInputStreamCloseFailure {

        InputStream in = null;
        byte[] bytes = null;

        try {

            in = serialPort.getInputStream();
            int bufflenth = in.available();        //获取buffer里的数据长度

            while (bufflenth != 0) {
                bytes = new byte[bufflenth];    //初始化byte数组为buffer中数据的长度
                in.read(bytes);
                bufflenth = in.available();
            }
        } catch (IOException e) {
            throw new ReadDataFromSerialPortFailure();
        } finally {
            try {
                if (in != null) {
                    in.close();
                    in = null;
                }
            } catch (IOException e) {
                throw new SerialPortInputStreamCloseFailure();
            }

        }

        return bytes;

    }

    /**
     * 添加监听器
     *
     * @param port     串口对象
     * @param listener 串口监听器
     * @throws TooManyListeners 监听类对象过多
     */
    public static void addListener(SerialPort port, SerialPortEventListener listener) throws TooManyListeners {
        try {
            //给串口添加监听器
            port.addEventListener(listener);
            //设置当有数据到达时唤醒监听接收线程
            port.notifyOnDataAvailable(true);
            //设置当通信中断时唤醒中断线程
            port.notifyOnBreakInterrupt(true);
            //设置载波监听事件有效
            port.notifyOnCarrierDetect(true);
            //设置清除发送事件
            port.notifyOnCTS(true);
            //设置数据备妥事件
            port.notifyOnDSR(true);
            //设置发生错误事件
            port.notifyOnFramingError(true);
            //设置发送缓冲区为空事件
            port.notifyOnOutputEmpty(true);
            //设置发生奇偶校验错误事件
            port.notifyOnParityError(true);
            //设置响铃侦测事件
            port.notifyOnRingIndicator(true);
/*            port.notifyOnRingIndicator(true);
            port.notifyOnDSR(true);*/
            /*port.notifyOnOutputEmpty(true);*/
        } catch (TooManyListenersException e) {
            System.out.println("太多监听了");
            throw new TooManyListeners();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("监听器添加未知异常");
        }
    }

    //打开串口静态方法
    public static List<SerialPort> openPortLocalhost(List<MainProperties> mainConfList) {
        List<SerialPort> serialPortList = new ArrayList<SerialPort>();
        try {
            for (int i = 0; i < mainConfList.size(); i++) {
                MainProperties mainConf = mainConfList.get(i);
                System.out.println("mainConf"+mainConf);
                SerialPort port = SerialTool1.openPort(mainConf.getPortcomcode(), mainConf.getiBaudRate(), mainConf.getiDataBit(), mainConf.getiStopBit(),mainConf.getsVerifyBit());
                serialPortList.add(port);
            }
        } catch (SerialPortParameterFailure serialPortParameterFailure) {
            serialPortParameterFailure.printStackTrace();
            System.out.println("设置串口参数失败");
        } catch (NotASerialPort notASerialPort) {
            notASerialPort.printStackTrace();
            System.out.println("端口指向设备不是串口类型");
        } catch (NoSuchPort noSuchPort) {
            noSuchPort.printStackTrace();
            System.out.println("没有该端口对应的串口设备");
        } catch (PortInUse portInUse) {
            portInUse.printStackTrace();
            System.out.println("端口已被占用");
            serialPortList = openPortLocalhost(mainConfList);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("未知打开串口异常");
        } finally {
            return serialPortList;
        }
    }
}
