package com.daq.until;

/**
 * User: gaosm
 * Date: 2018/8/5
 * Time: 11:30
 */
public class ChangHui4Float {
    public static void main(String[] args) {
        String returnCodeBuffer = "@ 01 RD 01 12 06 89 33 35 01 B2 F0 00 06 8B BF 03 01 C0 44 39 12 81 B1 00 03 F2 AA EB 00 00 19 CR";
        returnCodeBuffer = returnCodeBuffer.replace(" ", "");
        String hex = returnCodeBuffer.substring(33, 41);

        //将16进制字符串处理
        //String hex = "01 C0 44 39";
        hex = hex.replace(" ", "");
        //提取解码第一字符
        String zhengshuweiF = hex.substring(0, 2);
        String xiaoshuweiF = hex.substring(2);
        System.out.println(zhengshuweiF);
        System.out.println(xiaoshuweiF);
        Integer int1 = Integer.parseInt(zhengshuweiF, 16);
        String btyestring = Integer.toBinaryString(int1);
        System.out.println(btyestring);
        System.out.println(hex);
        int shufuwei = 0;
        int jiefuwei = 0;
        int jiema = 0;
        System.out.println("btyestring:" + btyestring);
        if (btyestring.length() == 8) {
            shufuwei = Integer.parseInt(btyestring.substring(0, 1));
            jiefuwei = Integer.parseInt(btyestring.substring(0, 1));
            jiema = Integer.parseInt(btyestring.substring(1, 8), 2);
        }
        if (btyestring.length() == 7) {
            shufuwei = 0;
            jiefuwei = Integer.parseInt(btyestring.substring(0, 1));
            jiema = Integer.parseInt(btyestring.substring(1, 7), 2);
            System.out.println();
        } else {
            shufuwei = 0;
            jiefuwei = 0;
            jiema = Integer.parseInt(btyestring, 2);
        }
        System.out.println("shufuwei:" + shufuwei);
        System.out.println("jiefuwei:" + jiefuwei);
        System.out.println("jiema:" + jiema);
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
        System.out.println("zhengshuwei:" + zhengshuwei);
        float f1=0;
        for(int i=xiaoshuweiF.length();i>0;i--){
            int int2=Integer.parseInt(xiaoshuweiF.substring(i-1,i),16);
            f1 = parsefloat(int2, f1);
        }
        System.out.println("正常浮点:"+f1 * zhengshuwei);
        double shunshi = f1 * zhengshuwei * 3600;
        System.out.println("shunshi:"+shunshi);

    }

    private static float parsefloat(int int2, float f1) {
        float f2=(int2+f1)/16;
        return f2;
    }
}
