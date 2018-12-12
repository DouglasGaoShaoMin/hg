package com.daq.moxa;

import java.util.Arrays;

/**
 * User: gaosm
 * Date: 2018/5/27
 * Time: 15:21
 */
public class Test {
    public static void main(String args[]) {
        //公式解析
        //String zdzk = "0d 10 0d 00 03 00 00 00 ff ff 05 00 00 00 00 00 00 c2 5e";
/*      String zdzk   = "07 10 0f 00 03 89 69 05 ff ff 05 14 4e 00 01 a3 69 00 00 45 3c";
        zdzk = zdzk.replaceAll(" ", "");
        String str0 = zdzk.substring(10, 12);
        String str1 = zdzk.substring(12, 14);
        String str10 = str1 + str0;
        System.out.println(str10);
        int int10 = Integer.parseInt(str10, 16);
        System.out.println(int10);
        float intshunshi = int10 / 65535f*30000f;
        System.out.println(intshunshi);
        float f1 = intshunshi;
        System.out.println(String.format("%08x",Float.floatToIntBits(f1)));
        String IEE754Str = Integer.toHexString(Float.floatToIntBits(f1));

        System.out.println(IEE754Str);
        System.out.println(Integer.toHexString(Float.floatToIntBits(f1)));*/
        //int a = Integer.valueOf("ba41499a", 16);
        //float fdate = Float.intBitsToFloat(a);
        //Long l = Long.parseLong("ba41499a", 16);
        //int a = Integer.valueOf("ba41499a", 16);
        //System.out.println("a:"+a);
        //System.out.println("l:"+l);
        // Float f = Float.intBitsToFloat(l.intValue());
        // int i = Float.floatToIntBits(a);             //1267528.1f
        // System.out.println("fdate:"+f);
        /**
         * 昌辉的LDR校验
         */
/*      char a = 0x30;
        char b = 0x33;
        char c = 0x52;
        char d = 0x44;
        int x = a ^ b ^ c ^ d;
       // System.out.println("a:" + a);
        System.out.println("x:"+String.format("%02x",x) );*/
        String IEE754Str = "00F101A5";
        Long a = Long.valueOf(IEE754Str, 16);
        System.out.println("a:"+a);
        Float f = Float.intBitsToFloat(a.intValue());
        //double d = Double.longBitsToDouble(a.intValue());
        System.out.println(f);
        String hex="3f80000000000000";

        Double  value=Double.longBitsToDouble(Long.valueOf(hex, 16));
        float f1= (float)(value*1);

        System.out.println("value:"+value*1000);
        System.out.println("value:"+f1*1000);

/*      String s1 = "40 30 33 52 44 30 31 31 32 30 36 41 43 30 30 30 30 34 37 44 31 45 33 35 37 43 35 38 41 43 30 30 30 30 30 30 30 30 30 30 30 31 30 44 36 36 45 30 30 30 37 39 34 41 41 37 38 30 30 30 30 36 38 0d ";
        s1 = s1.replaceAll(" ", "");
        System.out.println("s1:" + s1);
        StringBuffer sBuffer = new StringBuffer();
        System.out.println(sBuffer);
        if (s1.length() % 2 == 0) {
            for (int i = 0; i < s1.length(); i = i + 2) {
                int in1 = Integer.parseInt(s1.substring(i, i + 2), 16);
                char char1 = (char) in1;
                if (char1 == '\r') {
                    sBuffer.append("CR");
                    break;
                }
                sBuffer.append("" + char1);
            }
            //sBuffer.append("/0") ;
            System.out.println("返回码:" + sBuffer);
        }*/



/*        int code1 = Integer.parseInt(IEE754Str.substring(0, 2), 16);
        int code2 = Integer.parseInt(IEE754Str.substring(2, 4), 16);
        int code3 = Integer.parseInt(IEE754Str.substring(4, 6), 16);
        int code4 = Integer.parseInt(IEE754Str.substring(6, 8), 16);
        System.out.println(code2);*/
/*        int i=1;
        for(int j=1;j<10;j++) {
            i=test(i);
        }
        System.out.println("i:"+i);
    }
    private static int test(int i) {
        i++;
        return  i;
    }*/
/**
 *  0000AA81
 *  33194
 */
/*
        浙大中控累积量解析测试
        String zdzk = "07 14 06 00 01 00 46 CD F4 09 59 4F";
        zdzk=zdzk.replaceAll(" ","");
        String str0=zdzk.substring(12,14);
        String str1=zdzk.substring(14,16);
        String str2=zdzk.substring(16,18);
        String str3=zdzk.substring(18,20);
        String str3210=str3+str2+str1+str0;
        int int3210=Integer.parseInt(str3210,16);

        System.out.println(str0+str1+str2+str3);
        System.out.println(int3210);
        float f=33194f;
        float f1=int3210;
        System.out.println("f1:"+f1);
        System.out.println(Integer.toHexString(Float.floatToIntBits(f1)));
        String IEE754Str = Integer.toHexString(Float.floatToIntBits(f1));
        int code1 = Integer.parseInt(IEE754Str.substring(0, 2), 16);
        int code2 = Integer.parseInt(IEE754Str.substring(2, 4), 16);
        int code3 = Integer.parseInt(IEE754Str.substring(4, 6), 16);
        int code4 = Integer.parseInt(IEE754Str.substring(6, 8), 16);
        System.out.println(code2);*/
    }
}
