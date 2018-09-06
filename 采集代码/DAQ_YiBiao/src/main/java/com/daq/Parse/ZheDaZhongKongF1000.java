package com.daq.Parse;

import com.daq.model.CodeProperties;

/**
 * User: gaosm
 * Date: 2018/6/8
 * Time: 17:36
 */
public class ZheDaZhongKongF1000 {
    public static float ParsingCode_ZDZK_Rbus_L(CodeProperties codeProperties) {
        System.out.println("解析对象ParsingCode_ZDZK_Rbus_L:" + codeProperties);
        String returncode = codeProperties.getReturncode();                                      //获取该对象收到的返回码
        returncode = returncode.replaceAll(" ", "");
        String str0 = returncode.substring(12, 14);                                              //从第十二个字符开始读取
        String str1 = returncode.substring(14, 16);
        String str2 = returncode.substring(16, 18);
        String str3 = returncode.substring(18, 20);
        String str3210 = str3 + str2 + str1 + str0;
        Long long3210 = Long.parseLong(str3210, 16);
        int int3210 =long3210.intValue();                                                       //将字符串转成16进制整形
        float fdate = int3210;                                                                  //将整形转成浮点型
        System.out.println("解析值:" + fdate);
        return fdate;
    }

    public static float ParsingCode_ZDZK_Rbus_F(CodeProperties codeProperties) {
        System.out.println("解析对象ParsingCode_ZDZK_Rbus_F:" + codeProperties);
        String returncode = codeProperties.getReturncode();                                      //获取该对象收到的返回码
        returncode = returncode.replaceAll(" ", "");
        String str0 = returncode.substring(10, 12);
        String str1 = returncode.substring(12, 14);
        String str10 = str1 + str0;
        Long long10 = Long.parseLong(str10, 16);
        int int10 = long10.intValue();
        //公式解析
        float fdate = int10 / 65535f * codeProperties.getRange();
        System.out.println("fdate:" + fdate);
        return fdate;
    }
    public static float ParsingCode_ZDZK_Rbus_F1(CodeProperties codeProperties) {
        System.out.println("解析对象ParsingCode_ZDZK_Rbus_F:" + codeProperties);
        String returncode = codeProperties.getReturncode();                                      //获取该对象收到的返回码
        returncode = returncode.replaceAll(" ", "");
        String str0 = returncode.substring(16, 18);
        String str1 = returncode.substring(18, 20);
        String str10 = str1 + str0;
        Long long10 = Long.parseLong(str10, 16);
        int int10 = long10.intValue();
        //公式解析
        float fdate = int10 / 65535f * codeProperties.getRange();
        System.out.println("fdate:" + fdate);
        return fdate;
    }
    public static float ParsingCode_ZDZK_Rbus_F2(CodeProperties codeProperties) {
        System.out.println("解析对象ParsingCode_ZDZK_Rbus_F:" + codeProperties);
        String returncode = codeProperties.getReturncode();                                      //获取该对象收到的返回码
        returncode = returncode.replaceAll(" ", "");
        String str0 = returncode.substring(22, 24);
        String str1 = returncode.substring(24, 26);
        String str10 = str1 + str0;
        Long long10 = Long.parseLong(str10, 16);
        int int10 = long10.intValue();
        //公式解析
        float fdate = int10 / 65535f * codeProperties.getRange();
        System.out.println("fdate:" + fdate);
        return fdate;
    }
    public static float ParsingCode_ZDZK_Modbus_F(CodeProperties codeProperties) {
        System.out.println("解析对象ParsingCode_ZDZK_Modbus_F:" + codeProperties);
        String returncode = codeProperties.getReturncode();                                      //获取该对象收到的返回码
        returncode = returncode.replaceAll(" ", "");
        String str0 = returncode.substring(6, 8);
        String str1 =returncode.substring(8, 10);
        String str2 = returncode.substring(10, 12);
        String str3 = returncode.substring(12, 14);
        String str10 = str2+str3+str0+str1;
        //公式解析
        Long long10 = Long.parseLong(str10, 16);
        //Integer.valueOf(str10, 16);
        float fdate = Float.intBitsToFloat(long10.intValue());
        System.out.println("解析值:" + fdate);
        return fdate;
    }
}
