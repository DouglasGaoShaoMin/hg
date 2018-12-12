package com.daq.Parse;

import com.daq.model.CodeProperties;

/**
 * User: gaosm
 * Date: 2018/12/12
 * Time: 14:50
 */
public class ChangHuiD80 {
    /**
     * 瞬时量
     * @param codeProperties
     * @return
     */
    public static float ParsingCode_KL_Modbus_F(CodeProperties codeProperties) {
        String returncode = codeProperties.getReturncode();
        returncode = returncode.replaceAll(" ", "");
        String IEE754Str = returncode.substring(30, 38);                               //从第四个字符开始读取
        Long l1 = Long.valueOf(IEE754Str, 16);
        float f1 = Float.intBitsToFloat(l1.intValue());
        System.out.println("解析值:"+f1);
        return f1;
    }

    /**
     * 累计量
     * @param codeProperties
     * @return
     */
    public static float ParsingCode_KL_Modbus_L(CodeProperties codeProperties) {
        String returncode = codeProperties.getReturncode();
        returncode = returncode.replaceAll(" ", "");
        String IEE754Str = returncode.substring(46, 54);                               //从第四个字符开始读取
        Long l1 = Long.valueOf(IEE754Str, 16);
        float f1 = Float.intBitsToFloat(l1.intValue());
        System.out.println("解析值:"+f1);
        return f1;
    }
}
