package com.daq.Parse;

import com.daq.model.CodeProperties;

/**
 * User: gaosm
 * Date: 2018/8/6
 * Time: 10:31
 */
public class ChangHuiLK80 {
    public static float ParsingCode_CH_Modbus_F(CodeProperties codeProperties) {
        float defult = ParsingCode_CH_modbus_defult(codeProperties, 33, 41);
        float fdate = defult * 3600;
        return fdate;
    }

    public static float ParsingCode_CH_Modbus_C(CodeProperties codeProperties) {
        float defult = ParsingCode_CH_modbus_defult(codeProperties, 9, 17);
        return defult;
    }

    public static float ParsingCode_CH_Modbus_P(CodeProperties codeProperties) {
        float defult = ParsingCode_CH_modbus_defult(codeProperties, 17, 25);
        return defult;
    }

    public static float ParsingCode_CH_Modbus_L(CodeProperties codeProperties) {
        float defult1 = ParsingCode_CH_modbus_defult(codeProperties, 41, 49);
        float defult2 = ParsingCode_CH_modbus_defult(codeProperties, 49, 57);
        float defult3 = defult1 * 100 + defult2;
        return defult3;
    }

    private static float ParsingCode_CH_modbus_defult(CodeProperties codeProperties, int start, int end) {
        System.out.println("解析对象ParsingCode_CH_Modbus_F:" + codeProperties);
        String returncode = codeProperties.getReturncode();                                      //获取该对象收到的返回码
        returncode = returncode.replaceAll(" ", "");
        StringBuffer returnCodeBuffer = new StringBuffer();
        if (returncode.length() % 2 == 0) {
            for (int i = 0; i < returncode.length(); i = i + 2) {
                int in1 = Integer.parseInt(returncode.substring(i, i + 2), 16);
                char char1 = (char) in1;
                if (char1 == '\r') {
                    returnCodeBuffer.append("CR");
                    break;
                }
                returnCodeBuffer.append("" + char1);
            }
            //System.out.println("返回码:" + returnCodeBuffer);
        }
        String parsehex = returnCodeBuffer.substring(start, end);                                    //获取瞬时值的四字节浮点,八个字符
        //System.out.println(parsehex); //输出二进制八位字符串
        //提取整数位(第一字符)(八位二进制  第一位为数符,第二位为阶符(0为正,-1为负),剩下的六位重新组成十进制指数,底数为2  如2^9)
        String zhengshuweiF = parsehex.substring(0, 2);
        //提取小数位(二三四字节)
        String xiaoshuweiF = parsehex.substring(2);

        Integer zhengshuweiInt = Integer.parseInt(zhengshuweiF, 16);
        String btyestring = Integer.toBinaryString(zhengshuweiInt);                                        //解析成二进制字符串
        //System.out.println("btyestring:" + btyestring);      //二进制字符串输出
        int shufuwei = 0;
        int jiefuwei = 0;
        int jiema = 0;
        /**
         * 根据生成的不同位数的二进制字符串进行解析.
         */
        if (btyestring.length() == 8) {
            shufuwei = Integer.parseInt(btyestring.substring(0, 1));
            jiefuwei = Integer.parseInt(btyestring.substring(0, 1));
            jiema = Integer.parseInt(btyestring.substring(1, 8), 2);
        } else if (btyestring.length() == 7) {
            shufuwei = 0;
            jiefuwei = Integer.parseInt(btyestring.substring(0, 1));
            jiema = Integer.parseInt(btyestring.substring(1, 7), 2);
        } else {
            shufuwei = 0;
            jiefuwei = 0;
            jiema = Integer.parseInt(btyestring, 2);
        }
        //System.out.println("shufuwei:" + shufuwei+"  jiefuwei:" + jiefuwei+" jiema:" + jiema);
        //对数位符和阶位符进行正负号调整.
        if (shufuwei == 0) {
            shufuwei = 1;
        } else {
            shufuwei = -1;
        }
        if (jiefuwei == 0) {
            jiefuwei = 1;
        } else {
            jiefuwei = -1;
        }
        jiema = jiefuwei * jiema;
        double zhengshuwei = Math.pow(2, jiema);
        //System.out.println("zhengshuwei:" + zhengshuwei);
        float f1 = 0;
        for (int i = xiaoshuweiF.length(); i > 0; i--) {
            int int2 = Integer.parseInt(xiaoshuweiF.substring(i - 1, i), 16);
            f1 = parsefloat(int2, f1);
        }
        //System.out.println("正常浮点:" + f1 * zhengshuwei);
        double fdate = f1 * zhengshuwei;
        System.out.println("fdate:" + fdate);

        System.out.println("解析值:" + fdate);
        return (float) fdate;
    }

    private static float parsefloat(int int2, float f1) {
        float f2 = (int2 + f1) / 16;
        return f2;
    }
}
