package com.daq.until;

import java.util.*;

/**
 * User: gaosm
 * Date: 2018/4/26
 * Time: 21:12
 */
/*16进制浮点数的表示方法，根据IEEE的标准，分为
32位和64位两种，参数分别如下：

             符号位      指数位             尾数位            指数偏移量
32位     1[31]     8[23-30]        23[0-22]           127

64位     1[63]     11[52-62]      52[0-51]          1023

说明：

1位符号位(SIGN)
8位指数位(EXPONENT)
23位尾数位(MANTISSA)

    其中，32位二进制数的排位为：[31][30][29]...[2][1][0]

         64位二进制数的排位为：[63][62][61]...[2][1][0]

16进制转换为10进制的公式如下：

SGL = (-1)^SIGN * 1.MANTISSA * 2^(EXPONENT-127) */
public class zhedazhongkongjisuan {
        public static void main(String[] args) {
/*            Float f = 100.2f;
            zhedazhongkongjisuan ieee = new zhedazhongkongjisuan();
             06 03 04 49 9a ba 41 09 d0
            String ieee754 = ieee.floatToIEEE754(f);
            System.out.println(ieee754);*/
            String hex1="06 8B BF 03";
            String hex="07 94 AA 78";
            //String hexnoempty=hex.replace(" ","");

            List<String> hexlist=new ArrayList<String>();
            //for(int i=0;i<4;i++){
            String[] hexarr = hex.split(" ");

            hexlist=Arrays.asList(hexarr);
            //System.out.print(hexlist+"    ");
            String a3210=hexlist.get(0)+hexlist.get(1)+hexlist.get(2)+hexlist.get(3);
            String a0123=hexlist.get(3)+hexlist.get(2)+hexlist.get(1)+hexlist.get(0);
            String a1032=hexlist.get(2)+hexlist.get(3)+hexlist.get(0)+hexlist.get(1);
            String a2301=hexlist.get(1)+hexlist.get(0)+hexlist.get(3)+hexlist.get(2);
            String guise[]={a3210,a0123,a1032,a2301};
            for(int i=0;i<4;i++){
                Float value=null;
                try {
                    //System.out.print(guise[i]+"    ");
                    Long long1 = Long.parseLong(guise[i], 16);
                    value=Float.intBitsToFloat(long1.intValue());
                }catch (Exception e){
                    System.out.println("问题数据");
                        continue;
                }
                System.out.println(value);
            }
            //}
/*            hexlist.add("72");
            hexlist.add("3A");
            hexlist.add("45");
            hexlist.add("03");*/


  /*          System.out.println(hexnoempty);
            Float  value=Float.intBitsToFloat(Integer.valueOf(hexnoempty, 16));*/
            //System.out.println(value);
        }

        /**
         * 获取float的IEEE存储格式
         * @param value
         * @return
         */
        public String floatToIEEE(float value) {
            //符号位
            String sflag = value > 0 ? "0" : "1";

            //整数部分
            int fz = (int) Math.floor(value);
            //整数部分二进制
            String fzb = Integer.toBinaryString(fz);
            //小数部分，格式： 0.02
            String valueStr = String.valueOf(value);
            String fxStr = "0" + valueStr.substring(valueStr.indexOf("."));
            float fx = Float.parseFloat(fxStr);
            //小数部分二进制
            String fxb = toBin(fx);

            //指数位
            String e = Integer.toBinaryString(127 + fzb.length() - 1);
            //尾数位
            String m = fzb.substring(1) + fxb;

            String result = sflag + e + m;

            while (result.length() < 32) {
                result += "0";
            }
            if (result.length() > 32) {
                result = result.substring(0, 32);
            }
            return result;
        }

        private String toBin(float f) {
            List<Integer> list = new ArrayList<Integer>();
            Set<Float> set = new HashSet<Float>();
            int MAX = 24; // 最多8位

            int bits = 0;
            while (true) {
                f = calc(f, set, list);
                bits++;
                if (f == -1 || bits >= MAX)
                    break;
            }
            String result = "";
            for (Integer i : list) {
                result += i;
            }
            return result;
        }

        private float calc(float f, Set<Float> set, List<Integer> list) {
            if (f == 0 || set.contains(f))
                return -1;
            float t = f * 2;
            if (t >= 1) {
                list.add(1);
                return t - 1;
            } else {
                list.add(0);
                return t;
            }
        }
}
