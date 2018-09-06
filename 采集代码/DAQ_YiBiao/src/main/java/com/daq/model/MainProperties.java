package com.daq.model;

/**
 * User: gaosm
 * Date: 2018/5/19
 * Time: 15:39
 */
public class MainProperties {
    private int portnum;
    private String portcomcode;
    private int iBaudRate;
    private int iDataBit;
    private int iStopBit;
    private int sVerifyBit;
    private int iTimeOut;
    private int everySendTime;

    @Override
    public String toString() {
        return "MainProperties{" +
                "portnum=" + portnum +
                ", portcomcode='" + portcomcode + '\'' +
                ", iBaudRate=" + iBaudRate +
                ", iDataBit=" + iDataBit +
                ", iStopBit=" + iStopBit +
                ", sVerifyBit=" + sVerifyBit +
                ", iTimeOut=" + iTimeOut +
                ", everySendTime=" + everySendTime +
                '}';
    }

    public int getEverySendTime() {
        return everySendTime;
    }

    public void setEverySendTime(int everySendTime) {
        this.everySendTime = everySendTime;
    }

    public int getPortnum() {
        return portnum;
    }

    public void setPortnum(int portnum) {
        this.portnum = portnum;
    }

    public String getPortcomcode() {
        return portcomcode;
    }

    public void setPortcomcode(String portcomcode) {
        this.portcomcode = portcomcode;
    }

    public int getiBaudRate() {
        return iBaudRate;
    }

    public void setiBaudRate(int iBaudRate) {
        this.iBaudRate = iBaudRate;
    }

    public int getiDataBit() {
        return iDataBit;
    }

    public void setiDataBit(int iDataBit) {
        this.iDataBit = iDataBit;
    }

    public int getiStopBit() {
        return iStopBit;
    }

    public void setiStopBit(int iStopBit) {
        this.iStopBit = iStopBit;
    }

    public int getsVerifyBit() {
        return sVerifyBit;
    }

    public void setsVerifyBit(int sVerifyBit) {
        this.sVerifyBit = sVerifyBit;
    }

    public int getiTimeOut() {
        return iTimeOut;
    }

    public void setiTimeOut(int iTimeOut) {
        this.iTimeOut = iTimeOut;
    }
}
