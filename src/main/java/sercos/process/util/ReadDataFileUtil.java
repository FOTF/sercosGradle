package sercos.process.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 读取结果文件工具类
 * Created by 宗祥 on 2017/3/1.
 */
public class ReadDataFileUtil {

    private static final String TIME_END_CHAR = "   ETHER";

    public static void readFile(String filePath){
        List<String> lines;
        try {
            lines = FileUtils.readLines(new File(filePath));
        } catch (IOException e) {
            return;
        }
        String startTime = lines.get(2).substring(0, lines.get(2).indexOf(TIME_END_CHAR));
        for(int i = 1, j = lines.size() / 4; i < j; i ++){
            String time = lines.get(4 * i + 2);
            time = time.substring(0, time.indexOf(TIME_END_CHAR));
            String data = lines.get(4 * i + 3);
        }
    }



    public static void main(String[] args) {
//        ReadDataFileUtil.calcNSecCha("09:07:42,240,172", "09:07:42,240,273");
        //09:07:42,240,172
        /*Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970, 1, 1, 0, 0, 0);
        date = calendar.getTime();
        System.out.println(date);
        System.out.println(date.getTime() / 1000);*/
    }

}
