package sercos.process.entity;

import sercos.process.common.SercosPhase;
import sercos.process.common.SercosType;

/**
 * Created by 宗祥 on 2017/3/2.
 */
public class Sercos {

    private SercosType sercosType;

    private SercosPhase sercosPhase;

    private String data;

    public SercosType getSercosType() {
        return sercosType;
    }

    public void setSercosType(SercosType sercosType) {
        this.sercosType = sercosType;
    }

    public SercosPhase getSercosPhase() {
        return sercosPhase;
    }

    public void setSercosPhase(SercosPhase sercosPhase) {
        this.sercosPhase = sercosPhase;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Sercos{" +
                "sercosType=" + sercosType +
                ", sercosPhase=" + sercosPhase +
                ", data='" + data + '\'' +
                '}';
    }
}
