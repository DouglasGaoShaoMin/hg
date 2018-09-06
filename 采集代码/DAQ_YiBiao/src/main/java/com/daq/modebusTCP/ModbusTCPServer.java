package com.daq.modebusTCP;

import com.daq.model.CodeProperties;
import com.daq.until.TypeUntil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: gaosm
 * Date: 2018/5/12
 * Time: 17:03
 */
public class ModbusTCPServer extends TimerTask {

    private List<CodeProperties> codePropertiesList;
    private Socket socket;

    public ModbusTCPServer() {
    }

    public ModbusTCPServer(List<CodeProperties> codePropertiesList) {
        this.codePropertiesList = codePropertiesList;
    }

    @Override
    public void run() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        InputStream in = null;
        OutputStream out = null;
        try {
            while (true) {
                try {
                    socket = ModBusTcpUntil.NowSocket.accept();
                    socket.setSoTimeout(3000);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    in = socket.getInputStream();
                    out = socket.getOutputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                byte[] bs = new byte[32];
                System.out.println("请求数据开始:" + formatter.format(new Date()));
                while (12 == in.read(bs)) {
                    System.out.println("请求到数据:" + formatter.format(new Date()));
                    //TODO 此处应该增加更多判断,根据收到的modbus指令不同执行不同的解析的发送
                    String MBAPFirst = String.format("%02x", bs[0]) + String.format("%02x", bs[1]);                //获取MBAP的数据行识别位
                    String MBAPTwo = String.format("%02x", bs[2]) + String.format("%02x", bs[3]);                  //获取modbus类型识别位(一般为00 00)
                    String MBAPThree = String.format("%02x", bs[4]) + String.format("%02x", bs[5]);                //读取modbus长度位(后面数据的长度 00为一个数据)
                    String MBAPAddress = String.format("%02x", bs[6]);                                              //读取设备的地址位
                    String funCode = String.format("%02x", bs[7]);                                                  //读取modbus的功能码
                    if (funCode.equals("03") || funCode.equals("04")) {
                        int StartIndex = Integer.parseInt(String.format("%02x", bs[8]) + String.format("%02x", bs[9]), 16);               //要求读取的内存长度
                        int ReadLength = Integer.parseInt(String.format("%02x", bs[11]), 16);               //要求读取的内存长度
                        int ReturnMBLenght = ReadLength * 2 + 3;                                                    //返回码中MBAP中的长度
                        int ReturnDateLength = ReadLength * 2;                                                      //返回码中数据码中的长度
                        int DATECode[] = new int[ReturnDateLength];
                        String MBAP = MBAPFirst + MBAPTwo + String.format("%04x", ReturnMBLenght) + MBAPAddress;    //生成MBAP的头文件
                        String returnTCPCode = MBAP + funCode + String.format("%02x", ReturnDateLength);            //生成带命令号和数据长度的头文件
                        System.out.println("MBAPFirst:" + MBAPFirst + "    MBAPTwo:" + MBAPTwo + "    MBAPThree:" + MBAPThree + "    ReadLength:" + ReadLength + "    funCode:" + funCode);
                        System.out.println("请求码:" + TypeUntil.bytesToHexString(bs));
                        for (CodeProperties codeProperties : codePropertiesList) {
                            float floatDate = 0;
                            if (!codeProperties.getReturncode().isEmpty()) {
/*                                if(codeProperties.getType().equals("ZheDaZhongKongF1000")){
                                    DATECode = ParsingReturnCode_ZDZK_Rbus(DATECode, codeProperties);                       //使用哪种解析方式解析
                                }else{
                                    DATECode = ParsingReturnCode_ZDZK_Modbus(DATECode, codeProperties);                     //使用哪种解析方式解析
                                }*/
                                try {
                                    Class<?> parseClass = Class.forName("com.daq.Parse." + codeProperties.getType());                     //反射获取解析类
                                    Method parseMethod = parseClass.getMethod(codeProperties.getParsemethod(), CodeProperties.class);       //反射获取解析方法
                                    floatDate = (float) parseMethod.invoke(null, codeProperties);                                     //反射获取解析值(静态方法用null)
                                } catch (ClassNotFoundException e) {
                                    System.out.println("无法找到反射类");
                                    e.printStackTrace();
                                } catch (NoSuchMethodException e) {
                                    System.out.println("无法找到反射方法");
                                    e.printStackTrace();
                                } catch (IllegalAccessException e) {
                                    System.out.println("反射访问权限不够");
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    System.out.println("反射异常");
                                    e.printStackTrace();
                                }
                                //DATECode = changeFloatDate(DATECode, floatDate, codeProperties.getRamaddress(),StartIndex);
                            }
                            DATECode = changeFloatDate(DATECode, floatDate, codeProperties.getRamaddress(), StartIndex);
                        }

                        for (int str : DATECode) {
                            returnTCPCode = returnTCPCode + String.format("%02x", str);
                        }
                        System.out.println("returnTCPCode:" + returnTCPCode);
                        out.write(TypeUntil.hexStringToBytes(returnTCPCode));
                        out.flush();
                        System.out.println("返回完成:" + formatter.format(new Date()) + "--------------华丽丽的分割线--------------");
                        //break;
                    }
                }
            }
        } catch (BindException b) {
            b.printStackTrace();
            System.out.println("地址监听异常,请排查机器IP是否设置正确");
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            /* out.write(TypeUntil.hexStringToBytes(returnTCPCode));
            out.flush();*/
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
            Timer timer1 = new Timer(false);
            timer1.schedule(new ModbusTCPServer(codePropertiesList), 3000);
            throw new RuntimeException();
        }
    }

    private int[] changeFloatDate(int[] dateCode, float floatDate, int ramaddress, int startIndex) {
        /*      System.out.println("查看1长度:"+(ramaddress + 1+1-startIndex) * 4);
                System.out.println("查看2startIndex:"+startIndex);
                System.out.println("查看3ramaddress:"+dateCode.length);*/
        if ((ramaddress - startIndex / 2) * 4 < dateCode.length && ramaddress >= startIndex / 2) {
        /*      System.out.println("查看4长度:"+(ramaddress + 1+1-startIndex) * 4);
                System.out.println("查看5startIndex:"+startIndex);
                System.out.println("查看6ramaddress:"+dateCode.length);*/
            String IEE754Str = String.format("%08x", Float.floatToIntBits(floatDate));
            Long long1 = Long.parseLong(IEE754Str.substring(0, 2), 16);
            Long long2 = Long.parseLong(IEE754Str.substring(2, 4), 16);
            Long long3 = Long.parseLong(IEE754Str.substring(4, 6), 16);
            Long long4 = Long.parseLong(IEE754Str.substring(6, 8), 16);
            int code1 = long1.intValue();
            int code2 = long2.intValue();
            int code3 = long3.intValue();
            int code4 = long4.intValue();
            /**
             * 倒序的byte,一个byte等于两个code(16进制数),所以需要两两互换.
             */
            System.out.println("code3:" + code3 + "code4:" + code4 + "code1:" + code1 + "code2:" + code2);
            dateCode[(ramaddress - startIndex / 2) * 4 + 0] = code3;
            dateCode[(ramaddress - startIndex / 2) * 4 + 1] = code4;
            dateCode[(ramaddress - startIndex / 2) * 4 + 2] = code1;
            dateCode[(ramaddress - startIndex / 2) * 4 + 3] = code2;
            System.out.println("返回16进制数据:" + Arrays.toString(dateCode));
        }
        return dateCode;
    }
}
