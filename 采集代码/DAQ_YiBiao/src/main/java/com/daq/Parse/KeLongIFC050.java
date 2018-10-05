package com.daq.Parse;

import com.daq.model.CodeProperties;

/**
 * User: gaosm
 * Date: 2018/10/5
 * Time: 9:43
 */
public class KeLongIFC050 {
    /**
     * 科隆IFC050   波特率19200
     * 读取瞬时流量时的报文命令:
     * （01 04 75 32 00 02 ca 08）为主站发出的命令
     * 01代表仪表地址
     * 04 代表获取参数命令
     * 75 32代表获取流量
     * 00代表固定值
     * 02代表返回单精度数据（4个字节）
     * ca 08代表（crc）
     * <p>
     * （01 04 04 3f 80 00 00 f6 78）为从站响应的报文
     * 01代表仪表地址
     * 04代表获取参数命令
     * 04代表字节数，04即表示4个字节
     * 3f 80 00 00代表数据，即流量值
     * f6 78（crc）
     *
     * @param codeProperties
     * @return
     */
    public static float ParsingCode_KL_Modbus_F(CodeProperties codeProperties) {
        String returncode = codeProperties.getReturncode();
        returncode = returncode.replaceAll(" ", "");
        String str0 = returncode.substring(4, 12);                               //从第四个字符开始读取
        str0 = str0 + "00000000";
        Double d1 = Double.longBitsToDouble(Long.valueOf(str0, 16));
        float f1 = (float) (d1 * 3600);
        System.out.println("解析值:"+f1);
        return f1;
    }

    /**
     * 读取累计流量时的报文命令
     * （01 04 75 38 00 04 6a 08）为主站发出的命令
     * 01代表仪表地址
     * 04 代表获取参数命令
     * 75 38代表获取累积流量
     * 00代表固定值
     * 04代表返回双精度数据（8个字节）
     * 6a 08代表（crc）
     * （01 04 08 40 b6 ed 74 9f 12 8d 58 ba 3b）为从站响应的报文
     * 01代表仪表地址
     * 04代表获取参数命令
     * 08代表字节数，08即表示8个字节
     * 40 b6 ed 74 9f 12 8d 58代表数据，即counter 1值
     * ba 3b代表（crc）
     * @param codeProperties
     * @return
     */
    public static float ParsingCode_KL_Modbus_L(CodeProperties codeProperties) {
        String returncode = codeProperties.getReturncode();
        returncode = returncode.replaceAll(" ", "");
        String str0 = returncode.substring(4, 20);                               //从第四个字符开始读取
        Double d1 = Double.longBitsToDouble(Long.valueOf(str0, 16));
        float f1 = (float) (d1 * 1);
        System.out.println("解析值:" + f1);
        return f1;
    }
}
