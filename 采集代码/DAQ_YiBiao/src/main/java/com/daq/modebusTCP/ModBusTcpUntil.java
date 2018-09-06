package com.daq.modebusTCP;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ResourceBundle;

/**
 * User: gaosm
 * Date: 2018/5/24
 * Time: 18:30
 */
public class ModBusTcpUntil extends Thread{
    public static ResourceBundle mainPropertiesConf = ResourceBundle.getBundle("MainProperties");
    public static ServerSocket NowSocket=OpenSocket();
    public static ServerSocket OpenSocket() {
        try {
            ServerSocket serverSocket = new ServerSocket(502, 100,InetAddress.getByName(mainPropertiesConf.getString("loclhost")));                 //InetAddress.getByName("192.168.0.111" )
            return serverSocket;
        } catch (IOException e1) {
            e1.printStackTrace();
            System.out.println("SocketTCP协议建立出错!");
            return null;
        }
    }
    
}
