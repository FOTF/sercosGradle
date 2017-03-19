package sercos.process.entity;

import sercos.process.common.SercosPhase;
import sercos.process.common.SercosType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 宗祥 on 2017/3/2.
 */
public class Sercos {

    private SercosType sercosType;

    private SercosPhase sercosPhase;

    private Map<String, Object> sercosMDT = new HashMap<>();

    private Map<String, Object> sercosAT = new HashMap<>();

    private Map<String, Object> slaves = new HashMap<>();

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

    public Map<String, Object> getSercosMDT() {
        return sercosMDT;
    }

    public void setSercosMDT(Map<String, Object> sercosMDT) {
        this.sercosMDT = sercosMDT;
    }

    public Map<String, Object> getSercosAT() {
        return sercosAT;
    }

    public void setSercosAT(Map<String, Object> sercosAT) {
        this.sercosAT = sercosAT;
    }

    public Map<String, Object> getSlaves() {
        return slaves;
    }

    public void setSlaves(Map<String, Object> slaves) {
        this.slaves = slaves;
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
