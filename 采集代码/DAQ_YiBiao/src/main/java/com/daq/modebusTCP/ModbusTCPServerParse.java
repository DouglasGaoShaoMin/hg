package com.daq.modebusTCP;

import com.daq.model.CodeProperties;

/**
 * User: gaosm
 * Date: 2018/6/9
 * Time: 18:29
 */
public class ModbusTCPServerParse {
    private int[] ParsingReturnCode_ZDZK_Modbus(int DATECode[], CodeProperties codeProperties) {
        System.out.println("解析对象:" + codeProperties);
        int ramaddress = codeProperties.getRamaddress();                                         //获取对象的内存地址号(0开始)
        String returncode = codeProperties.getReturncode();                                      //获取该对象收到的返回码
        if ((ramaddress + 1) * 4 <= DATECode.length) {
            if (returncode.isEmpty()) {
                System.out.println("ramaddress长度" + ramaddress);
                System.out.println("DATECode长度" + DATECode.length);
                DATECode[ramaddress * 4 + 0] = 0;
                DATECode[ramaddress * 4 + 1] = 0;
                DATECode[ramaddress * 4 + 2] = 0;
                DATECode[ramaddress * 4 + 3] = 0;
            } else {
                Long long1 = Long.parseLong(returncode.replaceAll(" ", "").substring(6, 8), 16);
                Long long2 = Long.parseLong(returncode.replaceAll(" ", "").substring(8, 10), 16);
                Long long3 = Long.parseLong(returncode.replaceAll(" ", "").substring(10, 12), 16);
                Long long4 = Long.parseLong(returncode.replaceAll(" ", "").substring(12, 14), 16);
                int code1 =long1.intValue() ;
                int code2 =long2.intValue();
                int code3 =long3.intValue();
                int code4 =long4.intValue();
                DATECode[ramaddress * 4 + 0] = code1;
                DATECode[ramaddress * 4 + 1] = code2;
                DATECode[ramaddress * 4 + 2] = code3;
                DATECode[ramaddress * 4 + 3] = code4;
                System.out.println("code:" + code1 + code2 + code3 + code4);
            }
        }
        return DATECode;
    }
    private int[] ParsingReturnCode_ZDZK_Rbus(int DATECode[], CodeProperties codeProperties) {
        System.out.println("解析对象:" + codeProperties);
        int ramaddress = codeProperties.getRamaddress();                                         //获取对象的内存地址号(0开始)
        String returncode = codeProperties.getReturncode();                                      //获取该对象收到的返回码
        if ((ramaddress + 1) * 4 <= DATECode.length) {
            if (returncode.isEmpty()) {
                System.out.println("ramaddress长度" + ramaddress);
                System.out.println("DATECode长度" + DATECode.length);
                DATECode[ramaddress * 4 + 0] = 0;
                DATECode[ramaddress * 4 + 1] = 0;
                DATECode[ramaddress * 4 + 2] = 0;
                DATECode[ramaddress * 4 + 3] = 0;
            } else if (codeProperties.getFunction().equals("10")) {
                returncode = codeProperties.getReturncode();
                returncode = returncode.replaceAll(" ", "");
                String str0 = returncode.substring(10, 12);
                String str1 = returncode.substring(12, 14);
                String str10 = str1 + str0;
                Long long10 = Long.parseLong(str10, 16);
                int int10 = long10.intValue();
                System.out.println("int10:"+int10);
                //公式解析
                float intshunshi = int10 / 65535f * codeProperties.getRange();
                System.out.println("int11:"+intshunshi);
                float f1 = intshunshi;
                String IEE754Str = String.format("%08x", Float.floatToIntBits(f1));
                System.out.println("int12:"+IEE754Str);
                int code1 = Integer.parseInt(IEE754Str.substring(0, 2), 16);
                int code2 = Integer.parseInt(IEE754Str.substring(2, 4), 16);
                int code3 = Integer.parseInt(IEE754Str.substring(4, 6), 16);
                int code4 = Integer.parseInt(IEE754Str.substring(6, 8), 16);
                /**
                 * 倒序的byte,一个byte等于两个code(16进制数),所以需要两两互换.
                 */
                DATECode[ramaddress * 4 + 0] = code3;
                DATECode[ramaddress * 4 + 1] = code4;
                DATECode[ramaddress * 4 + 2] = code1;
                DATECode[ramaddress * 4 + 3] = code2;
                //System.out.println("code:" + code3 + code4 + code1 + code2);
            } else if (codeProperties.getFunction().equals("14")) {
                returncode = codeProperties.getReturncode();
                returncode = returncode.replaceAll(" ", "");
                String str0 = returncode.substring(12, 14);
                String str1 = returncode.substring(14, 16);
                String str2 = returncode.substring(16, 18);
                String str3 = returncode.substring(18, 20);
                String str3210 = str3 + str2 + str1 + str0;
                Long long3210 = Long.parseLong(str3210, 16);
                int int3210 =long3210.intValue();
                float f1 = int3210;
                String IEE754Str = String.format("%08x", Float.floatToIntBits(f1));
                int code1 = Integer.parseInt(IEE754Str.substring(0, 2), 16);
                int code2 = Integer.parseInt(IEE754Str.substring(2, 4), 16);
                int code3 = Integer.parseInt(IEE754Str.substring(4, 6), 16);
                int code4 = Integer.parseInt(IEE754Str.substring(6, 8), 16);
                /**
                 * 倒序的byte,一个byte等于两个code(16进制数),所以需要两两互换.
                 */
                DATECode[ramaddress * 4 + 0] = code3;
                DATECode[ramaddress * 4 + 1] = code4;
                DATECode[ramaddress * 4 + 2] = code1;
                DATECode[ramaddress * 4 + 3] = code2;
                //System.out.println("code:" + code3 + code4 + code1 + code2);
            }
        }
        return DATECode;
    }
}
