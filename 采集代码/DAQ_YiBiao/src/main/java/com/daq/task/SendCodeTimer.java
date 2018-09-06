package com.daq.task;

import com.daq.model.CodeProperties;
import com.daq.serialException.SendDataToSerialPortFailure;
import com.daq.serialException.SerialPortOutputStreamCloseFailure;
import com.daq.serialPort.SerialTool;
import com.daq.until.DAQMessage;
import com.daq.until.TypeUntil;
import gnu.io.SerialPort;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

/**
 * User: gaosm
 * Date: 2018/5/20
 * Time: 22:45
 */
//定时发送数据
public class SendCodeTimer extends TimerTask {
    private SerialPort serialPort;
    private int everySendTime;
    private List<CodeProperties> codePropertieslist = new ArrayList<CodeProperties>();
    //private DAQMessage daqMessage;
    private long time = 0;
    public SendCodeTimer(SerialPort serialPort, List<CodeProperties> codePropertieslist) {
        this.codePropertieslist = codePropertieslist;
        this.serialPort = serialPort;
    }

    public SendCodeTimer() {
    }

    public SendCodeTimer(SerialPort serialPort, List<CodeProperties> codePropertieslist, int everySendTime) {
        this.codePropertieslist = codePropertieslist;
        this.serialPort = serialPort;
        this.everySendTime = everySendTime;
    }

/*  public SendCodeTimer(SerialPort serialPort, List<CodeProperties> codePropertiesList, DAQMessage daqMessage) {
        //System.out.println("codePropertieslistcodePropertieslist"+codePropertiesList);
        this.codePropertieslist.addAll(codePropertiesList);
        this.serialPort = serialPort;
        this.daqMessage = daqMessage;
    }*/

    @Override
    public void run() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("codePropertieslist:"+codePropertieslist);
        try {
            while (true) {
                for (CodeProperties codeProperties : codePropertieslist) {
                    byte[] sendcode = TypeUntil.hexStringToBytes(codeProperties.getCode());
                    SerialTool.sendToPort(serialPort, sendcode);
                    DAQMessage.getDAQMessage().setReturnLength(codeProperties.getReturnlength());
                    System.out.println(formatter.format(new Date()) + "发送指令:" + codeProperties.getCode());
                    //System.out.println("everySendTime:"+everySendTime);
                    Thread.sleep(everySendTime);
                    long sendTime = System.currentTimeMillis();
                    for (int i = 0; System.currentTimeMillis() - sendTime <everySendTime; i++) {
                        if (DAQMessage.getDAQMessage().isFlag()) {
                            System.out.println(formatter.format(new Date()) + "接收指令" + DAQMessage.getDAQMessage().getMsg());
                            codeProperties.setReturncode(DAQMessage.getDAQMessage().getMsg());
                            DAQMessage.getDAQMessage().setFlag(false);
                            //System.out.println("codeProperties:"+codeProperties);
                            break;
                        }
                    }
                    //DAQMessage.getDAQMessage().setFlag(false);
                }
            }
        } catch (SendDataToSerialPortFailure sendDataToSerialPortFailure) {
            sendDataToSerialPortFailure.printStackTrace();
        } catch (SerialPortOutputStreamCloseFailure serialPortOutputStreamCloseFailure) {
            serialPortOutputStreamCloseFailure.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
