package sercos.process.common;

/**
 * Created by 宗祥 on 2017/3/6.
 */
public class SercosPhase {

    private String data;

    private SercosPhaseData sercosPhaseData;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public SercosPhaseData getSercosPhaseData() {
        return sercosPhaseData;
    }

    public void setSercosPhaseData(SercosPhaseData sercosPhaseData) {
        this.sercosPhaseData = sercosPhaseData;
    }

    @Override
    public String toString() {
        return "SercosPhase{" +
                "data='" + data + '\'' +
                ", sercosPhaseData=" + sercosPhaseData +
                '}';
    }
}
