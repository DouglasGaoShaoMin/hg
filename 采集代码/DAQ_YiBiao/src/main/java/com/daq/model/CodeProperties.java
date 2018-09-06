package com.daq.model;

/**
 * User: gaosm
 * Date: 2018/5/9
 * Time: 20:47
 */
public class CodeProperties {
    private String code;
    private String type;
    private String parsemethod;
    private String address;
    private String function;
    private String crc;
    private String namecode;
    private int ramaddress;
    private float range;
    private float rangetype;
    private String returncode = "";
    private int returnlength;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeProperties that = (CodeProperties) o;
        return code.equals(that.code);
    }

    @Override
    public String toString() {
        return "CodeProperties{" +
                "code='" + code + '\'' +
                ", type='" + type + '\'' +
                ", parsemethod='" + parsemethod + '\'' +
                ", address='" + address + '\'' +
                ", function='" + function + '\'' +
                ", crc='" + crc + '\'' +
                ", namecode='" + namecode + '\'' +
                ", ramaddress=" + ramaddress +
                ", range=" + range +
                ", rangetype=" + rangetype +
                ", returncode='" + returncode + '\'' +
                ", returnlength=" + returnlength +
                '}';
    }

    public String getParsemethod() {
        return parsemethod;
    }

    public void setParsemethod(String parsemethod) {
        this.parsemethod = parsemethod;
    }

    public float getRangetype() {
        return rangetype;
    }

    public void setRangetype(float rangetype) {
        this.rangetype = rangetype;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public int getReturnlength() {
        return returnlength;
    }

    public void setReturnlength(int returnlength) {
        this.returnlength = returnlength;
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    public String getReturncode() {
        return returncode;
    }

    public void setReturncode(String returncode) {
        this.returncode = returncode;
    }

    public int getRamaddress() {
        return ramaddress;
    }

    public void setRamaddress(int ramaddress) {
        this.ramaddress = ramaddress;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getCrc() {
        return crc;
    }

    public void setCrc(String crc) {
        this.crc = crc;
    }

    public String getNamecode() {
        return namecode;
    }

    public void setNamecode(String namecode) {
        this.namecode = namecode;
    }
}
