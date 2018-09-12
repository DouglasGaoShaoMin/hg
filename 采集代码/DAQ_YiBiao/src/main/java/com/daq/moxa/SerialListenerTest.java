package com.daq.moxa;

import com.daq.serialException.ReadDataFromSerialPortFailure;
import com.daq.serialException.SerialPortInputStreamCloseFailure;
import com.daq.serialPort.SerialTool;
import com.daq.until.DAQMessage;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.text.SimpleDateFormat;

/**
 * User: gaosm
 * Date: 2018/5/22
 * Time: 14:47
 */
public class SerialListenerTest implements SerialPortEventListener {
    public SerialPort serialPort;
    private long timeok = 0;
    //private DAQMessage daqMessage;
    StringBuffer completeCode = new StringBuffer();
    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5',
                '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public SerialListenerTest(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    public SerialListenerTest() {
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
                //System.out.println("输出缓冲区已清空!");
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
                            System.out.println("1");
                        } else {
                            System.out.println("2");

                            if (timeok == 0) {
                                timeok = System.currentTimeMillis();
                            }
                            System.out.println("3");

                            //将获取到的数据进行转码并输出
                            long timejiange = System.currentTimeMillis() - timeok;
                            System.out.println("timejiange"+timejiange);
                           // if (timejiange <45) {
                                //String bs = bytesToHexFun2(data);
                               //completeCode.append(bs);
                                for (int j = 0; j < data.length; j++) {
                                    //byte数组表示的字符串转换为16进制，
                                    completeCode.append(String.format("%02x", data[j]) + " ");
                                    //completeCode.append(data[j]+ " ");

                                }
                                sendCanSave();
                            //}
                            /*else {
                               // completeCode = new StringBuffer();
                               // String bs = bytesToHexFun2(data);
                               // completeCode.append(bs);
                                for (int j = 0; j < data.length; j++) {
                                    //byte数组表示的字符串转换为16进制，
                                    //completeCode.append(String.format("%02x", data[j]) + " ");
                                     completeCode.append(data[j]+ " ");
                                }

                                sendCanSave();
                            }*/
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
        System.out.println("completeCode"+completeCode.toString());
        
/*        if (completeCode.toString().replace(" ", "").length() >= DAQMessage.getDAQMessage().getReturnLength()) {
            String canSave = completeCode.toString();
            DAQMessage.getDAQMessage().setFlag(true);
            DAQMessage.getDAQMessage().setMsg(canSave);
            completeCode = new StringBuffer();
        }*/
    }
    /**
     * 方法一：
     * byte[] to hex string
     *
     * @param bytes
     * @return
     */
    public static String bytesToHexFun1(byte[] bytes) {
        // 一个byte为8位，可用两个十六进制位标识
        char[] buf = new char[bytes.length * 2];
        int a = 0;
        int index = 0;
        for(byte b : bytes) { // 使用除与取余进行转换
            if(b < 0) {
                a = 256 + b;
            } else {
                a = b;
            }

            buf[index++] = HEX_CHAR[a / 16];
            buf[index++] = HEX_CHAR[a % 16];
        }

        return new String(buf);
    }

    /**
     * 方法二：
     * byte[] to hex string
     *
     * @param bytes
     * @return
     */
    public static String bytesToHexFun2(byte[] bytes) {
        char[] buf = new char[bytes.length * 3];
        int index = 0;
        for(byte b : bytes) { // 利用位运算进行转换，可以看作方法一的变种
            buf[index++] = HEX_CHAR[b >>> 4 & 0xf];
            buf[index++] = HEX_CHAR[b & 0xf];
            buf[index++]=' ';
        }

        return new String(buf);
    }

    /**
     * 方法三：
     * byte[] to hex string
     *
     * @param bytes
     * @return
     */
    public static String bytesToHexFun3(byte[] bytes) {
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        for(byte b : bytes) { // 使用String的format方法进行转换
            buf.append(String.format("%02x", new Integer(b & 0xff)));
        }

        return buf.toString();
    }
}