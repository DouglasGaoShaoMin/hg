package com.daq.until;

/**
 * User: gaosm
 * Date: 2018/5/22
 * Time: 15:10
 */
public class DAQMessage {
    private boolean flag=false;
    private String msg="";
    private static volatile DAQMessage daqMessage = null;
    private int returnLength;

    static {
        //在该类被ClassLoader加载时就初始化一个SerialTool对象
        if (daqMessage == null) {
            daqMessage = new DAQMessage();
        }
    }
    public static DAQMessage getDAQMessage() {
        if (daqMessage == null) {
            daqMessage = new DAQMessage();
        }
        return daqMessage;
    }

    @Override
    public String toString() {
        return "DAQMessage{" +
                "flag=" + flag +
                ", msg='" + msg + '\'' +
                ", returnLength=" + returnLength +
                '}';
    }

    public int getReturnLength() {
        return returnLength;
    }

    public void setReturnLength(int returnLength) {
        this.returnLength = returnLength;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
