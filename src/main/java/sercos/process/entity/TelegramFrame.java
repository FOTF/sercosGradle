package sercos.process.entity;

/**
 * Created by 宗祥 on 2017/3/2.
 */
public class TelegramFrame {

    private String telegramNumber;

    private String telegramLength;

    private int captureLength;

    /**
     * 开始时间点
     */
    private String captureTime;

    /**
     * 花费时间
     */
    private String preciseCaptureTime;

    private String telegramDefect;

    public String getTelegramNumber() {
        return telegramNumber;
    }

    public void setTelegramNumber(String telegramNumber) {
        this.telegramNumber = telegramNumber;
    }

    public String getTelegramLength() {
        return telegramLength;
    }

    public void setTelegramLength(String telegramLength) {
        this.telegramLength = telegramLength;
    }

    public int getCaptureLength() {
        return captureLength;
    }

    public void setCaptureLength(int captureLength) {
        this.captureLength = captureLength;
    }

    public String getCaptureTime() {
        return captureTime;
    }

    public void setCaptureTime(String captureTime) {
        this.captureTime = captureTime;
    }

    public String getPreciseCaptureTime() {
        return preciseCaptureTime;
    }

    public void setPreciseCaptureTime(String preciseCaptureTime) {
        this.preciseCaptureTime = preciseCaptureTime;
    }

    public String getTelegramDefect() {
        return telegramDefect;
    }

    public void setTelegramDefect(String telegramDefect) {
        this.telegramDefect = telegramDefect;
    }
}
