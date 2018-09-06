package com.daq.task;

import com.daq.modebusTCP.ModBusTcpUntil;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.TimerTask;

/**
 * User: gaosm
 * Date: 2018/7/12
 * Time: 17:41
 */
public class ShouHuTimer extends TimerTask {
    @Override
    public void run() {
/*        System.out.println("是否还在连接" + ModBusTcpUntil.NowSocket.isBound());
        System.out.println("是否还在连接1" + ModBusTcpUntil.NowSocket.isClosed());
        System.out.println("socket.isClosed()" + ModBusTcpUntil.NowSocket.isClosed());
        System.out.println("socket.isBound()" + ModBusTcpUntil.NowSocket.isBound());
        System.out.println("socket.tostring" + ModBusTcpUntil.NowSocket.toString());*/

    }
}
