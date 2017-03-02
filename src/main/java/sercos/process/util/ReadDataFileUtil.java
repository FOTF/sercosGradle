package sercos.process.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import sercos.process.entity.ResultEntity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 读取结果文件工具类
 * Created by 宗祥 on 2017/3/1.
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
        ResultEntity resultEntity = new ResultEntity();
        String startTime = lines.get(2).substring(0, lines.get(2).indexOf(TIME_END_CHAR));
        String data = lines.get(3);
        for(int i = 1, j = lines.size() / 4; i < j; i ++){
            String time = lines.get(4 * i + 2);
            time = time.substring(0, time.indexOf(TIME_END_CHAR));

            data = lines.get(4 * i + 3);
        }

        return resultEntities;
    }

    public static void conventLineToEntity(String line2, String line3){
        ResultEntity resultEntity = new ResultEntity();
        String startTime = line2.substring(0, line2.indexOf(TIME_END_CHAR));
        String[] dataStrs = line3.substring(6).split("\\|");

        String destAddress = dataStrs[0] + ":" + dataStrs[1] + ":" + dataStrs[2] + ":" + dataStrs[3] + ":" + dataStrs[4] + ":" + dataStrs[5];
        String sourceAddress = dataStrs[6] + ":" + dataStrs[7] + ":" + dataStrs[8] + ":" + dataStrs[9] + ":" + dataStrs[10] + ":" + dataStrs[11];;
        String enternetType = "0x" + dataStrs[12] + dataStrs[13];

        String[] binaryChars = hexToBinary(dataStrs[14], 8);
        //计算是CP几

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
        //MDT 00100000
        //AT  01100000
        //CP0 01000000
        //CP1 00100001   21
        //CP2 01100010   62
        //CP3 00110011   33
        //CP4 00010100
        for (String str : hexToBinary("21", 8)){
            System.out.println("---" + str + "---");

        }

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
        int a = 0Xff;
        System.out.println(a);
    }

}
