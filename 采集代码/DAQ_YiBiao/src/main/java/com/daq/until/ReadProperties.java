package com.daq.until;

import com.daq.model.CodeProperties;
import com.daq.model.MainProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * User: gaosm
 * Date: 2018/5/19
 * Time: 17:26
 */
public class ReadProperties {
    public static ResourceBundle mainPropertiesConf = ResourceBundle.getBundle("MainProperties");
    public static ResourceBundle codePropertiesConf = ResourceBundle.getBundle("CodeProperties");
    private final static Logger logger = LoggerFactory.getLogger(ReadProperties.class); ;

    /**
     * 读取配置文件加载串口信息,返回串口List
     *
     * @return
     */
    public List<MainProperties> LoadMainConf() {
        List<MainProperties> portList = new ArrayList<MainProperties>();
        //获取串口配置文件信息
        int portnum = Integer.parseInt(mainPropertiesConf.getString("port.num"));
        for (int i = 0; i < portnum; i++) {
            MainProperties mainProperties = new MainProperties();
            String comCode = mainPropertiesConf.getString("port" + i + ".comCode"); //端口名
            int iBaudRate = Integer.parseInt(mainPropertiesConf.getString("port" + i + ".iBaudRate"));   //波特率
            int iDataBit = Integer.parseInt(mainPropertiesConf.getString("port" + i + ".iDataBit"));     //数据位
            int iStopBit = Integer.parseInt(mainPropertiesConf.getString("port" + i + ".iStopBit"));     //停止位
            int sVerifyBit = Integer.parseInt(mainPropertiesConf.getString("port" + i + ".sVerifyBit")); //校验位
            int iTimeOut = Integer.parseInt(mainPropertiesConf.getString("port" + i + ".iTimeOut")); //校验位
            int everySendTime = Integer.parseInt(mainPropertiesConf.getString("port" + i + ".everySendTime")); //校验位

            mainProperties.setPortcomcode(comCode);
            mainProperties.setiBaudRate(iBaudRate);
            mainProperties.setiDataBit(iDataBit);
            mainProperties.setiStopBit(iStopBit);
            mainProperties.setsVerifyBit(sVerifyBit);
            mainProperties.setiTimeOut(iTimeOut);
            mainProperties.setEverySendTime(everySendTime);
            portList.add(mainProperties);
        }
        return portList;
    }

    public List<CodeProperties> ReadCodeProperties() {
        List<CodeProperties> codePropertieslist = new ArrayList<>();
        int meternum = Integer.parseInt(codePropertiesConf.getString("meternum"));          //获取命令码数量
        /**
         * 循环读取命令行,将命令信息存入List实体类
         */
        for (int i = 0; i < meternum; i++) {
            CodeProperties codeProperty = new CodeProperties();
            String keyfirst = "meter" + i;
            String code = codePropertiesConf.getString(keyfirst + ".code");                  //需要发送的命令行
            String type = codePropertiesConf.getString(keyfirst + ".type");                  //类型
            String parsemethod = codePropertiesConf.getString(keyfirst + ".parsemethod");   //标签名
            String address = codePropertiesConf.getString(keyfirst + ".address");           //仪表使用地址
            String function = codePropertiesConf.getString(keyfirst + ".function");         //命令号
            String crc = codePropertiesConf.getString(keyfirst + ".crc");                    //CRC校验
            String namecode = codePropertiesConf.getString(keyfirst + ".namecode");         //标签名
            int ramaddress = Integer.parseInt(codePropertiesConf.getString(keyfirst + ".ramaddress"));//ram寄存器地址
            float range = Float.parseFloat(codePropertiesConf.getString(keyfirst + ".range"));//流量量程
            float rangetype = Float.parseFloat(codePropertiesConf.getString(keyfirst + ".rangetype"));//量程格式
            int returnlength = Integer.parseInt(codePropertiesConf.getString(keyfirst + ".returnlength"));//返回字符串长度限制
            codeProperty.setCode(code);
            codeProperty.setType(type);
            codeProperty.setParsemethod(parsemethod);
            codeProperty.setAddress(address);
            codeProperty.setFunction(function);
            codeProperty.setCrc(crc);
            codeProperty.setNamecode(namecode);
            codeProperty.setRamaddress(ramaddress);
            codeProperty.setRange(range);
            codeProperty.setRangetype(rangetype);
            codeProperty.setReturnlength(returnlength);
            //codeProperty.setReturncode("070304C8674B502540");
            codePropertieslist.add(codeProperty);
            logger.info("读取配置文件" + i + "配置成功" + codeProperty.toString());
        }
        return codePropertieslist;
    }
}
