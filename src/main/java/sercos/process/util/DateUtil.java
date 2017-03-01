package sercos.process.util;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 宗祥 on 2017/3/2.
 */
public class DateUtil {

    /**
     * 纳秒转秒
     * @param nSec
     */
    public static String nSecTiSec(long nSec){
        BigDecimal bigDecimal = new BigDecimal(nSec);
        BigDecimal result = bigDecimal.divide(new BigDecimal(1000 * 1000)).setScale(9, BigDecimal.ROUND_HALF_UP);
        return result.toString();
    }

    /**
     * 计算两个时间的纳秒差
     * @param timeStart
     * @param timeEnd
     * @return
     */
    public static long calcNSecCha(String timeStart, String timeEnd){
        long longTimeStart = conventTimeToNS(timeStart);
        long longTimeEnd = conventTimeToNS(timeEnd);
        return longTimeEnd - longTimeStart;
    }

    /**
     * 转换为纳秒
     * @param time
     * @return
     */
    public static long conventTimeToNS(String time){
        int hour = Integer.parseInt(time.substring(0, 2));
        int min = Integer.parseInt(time.substring(3, 5));
        int sec = Integer.parseInt(time.substring(6, 8));
        Date date;
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970, 1, 1, hour, min, sec);
        date = calendar.getTime();
        //计算秒
        long dataSec = date.getTime() / 1000;

        //计算纳秒值
        int wsec = Integer.parseInt(time.substring(9, 12));
        int nsec = Integer.parseInt(time.substring(13, 16));
        long dateNSec = dataSec * 1000 * 1000 + wsec * 1000 + nsec;
        //纳秒值
        return dateNSec;
    }
}
