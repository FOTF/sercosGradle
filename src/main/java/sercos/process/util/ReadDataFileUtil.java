package sercos.process.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import sercos.process.common.SercosPhase;
import sercos.process.common.SercosPhaseData;
import sercos.process.common.SercosType;
import sercos.process.common.SercosTypeData;
import sercos.process.entity.Ethernet;
import sercos.process.entity.ResultEntity;
import sercos.process.entity.Sercos;
import sercos.process.entity.TelegramFrame;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取结果文件工具类
 * Created by kai on 2017/3/1.
 */
public class ReadDataFileUtil {

    private static final String TIME_END_CHAR = "   ETHER";

    private static final String DATA_START_CHAR = "|0   |";

    /**
     * 读取文件
     * @param filePath
     */
    public static List<ResultEntity> readFile(String filePath){
        List<ResultEntity> resultEntities = new ArrayList<>();
        List<String> lines;
        try {
            lines = FileUtils.readLines(new File(filePath));
        } catch (IOException e) {
            return resultEntities;
        }
        Integer index = 1;
        ResultEntity resultEntity = null;
        String startTime = lines.get(1).substring(0, lines.get(1).indexOf(TIME_END_CHAR));
        String data = lines.get(2);
        resultEntity = conventLineToEntity(lines.get(1), data, null, index);
        resultEntities.add(resultEntity);

        int end = 0;
        for(int i = 1, j = lines.size() / 4; i < j; i++){
            String time = lines.get(4 * i + 1);
            data = lines.get(4 * i + 2);
            resultEntity = conventLineToEntity(time, data, startTime, index += i);
            resultEntities.add(resultEntity);
            end++;
            if(end == 100){
                break;
            }
        }
        return resultEntities;
    }

    /**
     * 解析文件行
     * @param line2
     * @param line3
     * @param timeStart
     * @param index
     * @return
     */
    public static ResultEntity conventLineToEntity(String line2, String line3, String timeStart, Integer index){
        ResultEntity resultEntity = new ResultEntity();
        TelegramFrame telegramFrame = new TelegramFrame();
        telegramFrame.setTelegramNumber(index.toString());
        Ethernet ethernet = new Ethernet();
        String endTime = line2.substring(0, line2.indexOf(TIME_END_CHAR));

        if(StringUtils.isBlank(timeStart)){
            telegramFrame.setCaptureTime("0.000000000");
            telegramFrame.setPreciseCaptureTime("0.000000000");
        }else{
            telegramFrame.setCaptureTime(DateUtil.nSecToSec(DateUtil.calcNSecCha(timeStart, endTime)));
            telegramFrame.setPreciseCaptureTime(telegramFrame.getCaptureTime());
        }

        String[] dataStrs = line3.substring(6).split("\\|");
        telegramFrame.setCaptureLength(dataStrs.length);
        telegramFrame.setTelegramLength(dataStrs.length + "");

        String destAddress = dataStrs[0] + ":" + dataStrs[1] + ":" + dataStrs[2] + ":" + dataStrs[3] + ":" + dataStrs[4] + ":" + dataStrs[5];
        String sourceAddress = dataStrs[6] + ":" + dataStrs[7] + ":" + dataStrs[8] + ":" + dataStrs[9] + ":" + dataStrs[10] + ":" + dataStrs[11];
        String enternetType = "0x" + dataStrs[12] + dataStrs[13];

        resultEntity.setTelegramFrame(telegramFrame);

        ethernet.setDestAddress(destAddress);
        ethernet.setEthernetType(enternetType);
        ethernet.setSourceAddress(sourceAddress);
        resultEntity.setEthernet(ethernet);

        Sercos sercos = new Sercos();
        SercosType sercosType = new SercosType();
        SercosTypeData sercosTypeData = new SercosTypeData();
        String[] mdtAtBinaryChars = hexToBinary(dataStrs[14], 8);
        //高位逆转，比如10进制20的二进制是00100000  那么高位代表从左到右
        if(StringUtils.equals(mdtAtBinaryChars[0], "0")){
            sercosTypeData.setPsTelegram("P");
        }else{
            sercosTypeData.setPsTelegram("S");
        }

        //计算是mdt还是at
        if(StringUtils.equals(mdtAtBinaryChars[1], "0")){
            sercosTypeData.setMdtOrAt("MDT");
        }else{
            sercosTypeData.setMdtOrAt("AT");
        }
        //计算是第几个mdt或者at
        sercosTypeData.setSercosTeleNum(binaryToNum(mdtAtBinaryChars, 6, 7).toString());
        sercosType.setSercosTypeData(sercosTypeData);
        sercosType.setData("0x" + dataStrs[14]);
        sercos.setSercosType(sercosType);

        //计算是CP几
        String[] cpBinaryChars = hexToBinary(dataStrs[15], 8);
        SercosPhase sercosPhase = new SercosPhase();
        sercosPhase.setData("0x" + dataStrs[15]);
        SercosPhaseData sercosPhaseData = new SercosPhaseData();
        sercosPhaseData.setCommunPhase("CP" + binaryToNum(cpBinaryChars, 4, 8).toString());
        //计算是否是当前cp
        if(StringUtils.equals(cpBinaryChars[0], "0")){
            sercosPhaseData.setCommunPhaseSwitch("Current CP");
        }else{
            sercosPhaseData.setCommunPhaseSwitch("New CP");
        }
        sercosPhaseData.setCycleCnt("0x01");
        sercosPhase.setSercosPhaseData(sercosPhaseData);
        sercos.setSercosPhase(sercosPhase);
        resultEntity.setSercos(sercos);
        resultEntity.setNum(index);
        System.out.println(resultEntity);
        return resultEntity;
    }

    public static Integer binaryToNum(String[] binarys, int from, int to){
        String str = "";
        for(int i = from; i < to; i++){
            str += binarys[i];
        }
        return Integer.parseInt(str, 2);
    }

    /**
     * 十六进制字符串转为二进制字符串
     * 并且扩充到多少位
     * @param str
     * @return
     */
    public static String[] hexToBinary(String str, int fixNum){
        String[] binarys = Integer.toBinaryString(Integer.valueOf(str, 16)).split("");
        if(binarys.length == fixNum){
            return binarys;
        }else if(binarys.length > fixNum){
            return null;
        }
        String[] resultBinarys = new String[fixNum];
        for(int i = 0, j = binarys.length; i < fixNum; i++){
            if(i >= fixNum - j){
                resultBinarys[i] = binarys[i - fixNum + j];
            }else{
                resultBinarys[i] = "0";
            }
        }
        return resultBinarys;
    }



    public static void main(String[] args) {
        ReadDataFileUtil.readFile("D:\\SMC3.txt");
        //MDT 00100000   20
        //AT  01100000   60
        //CP0 01000000
        //CP1 00100001   21
        //CP2 01100010   62
        //CP3 00110011   33
        //CP4 00010100   14
        /*for (String str : hexToBinary("21", 8)){
            System.out.println("---" + str + "---");
        }*/
        /*String[] str = {
                "1", "2"
        };
        System.out.println(Integer.parseInt("0110", 2));*/

        //String[] dataStrs = "ff|ff|ff|ff|ff|ff|".split("\\|");

        /*for(String str : dataStrs){
            System.out.println("---" + str);
        }*/
//        ReadDataFileUtil.calcNSecCha("09:07:42,240,172", "09:07:42,240,273");
        //09:07:42,240,172
        /*Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970, 1, 1, 0, 0, 0);
        date = calendar.getTime();
        System.out.println(date);
        System.out.println(date.getTime() / 1000);*/
        /*int a = 0Xff;
        System.out.println(a);*/
    }

}
