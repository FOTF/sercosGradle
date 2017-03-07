package sercos.process.common;

/**
 * Created by 宗祥 on 2017/3/6.
 */
public class SercosTypeData {

    private String psTelegram;

    private String mdtOrAt;

    private String cycleCndEnable = "enabled";

    private String sercosTeleNum;

    public String getPsTelegram() {
        return psTelegram;
    }

    public void setPsTelegram(String psTelegram) {
        this.psTelegram = psTelegram;
    }

    public String getMdtOrAt() {
        return mdtOrAt;
    }

    public void setMdtOrAt(String mdtOrAt) {
        this.mdtOrAt = mdtOrAt;
    }

    public String getCycleCndEnable() {
        return cycleCndEnable;
    }

    public void setCycleCndEnable(String cycleCndEnable) {
        this.cycleCndEnable = cycleCndEnable;
    }

    public String getSercosTeleNum() {
        return sercosTeleNum;
    }

    public void setSercosTeleNum(String sercosTeleNum) {
        this.sercosTeleNum = sercosTeleNum;
    }

    @Override
    public String toString() {
        return "SercosTypeData{" +
                "psTelegram='" + psTelegram + '\'' +
                ", mdtOrAt='" + mdtOrAt + '\'' +
                ", cycleCndEnable='" + cycleCndEnable + '\'' +
                ", sercosTeleNum='" + sercosTeleNum + '\'' +
                '}';
    }
}
