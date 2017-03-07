package sercos.process.common;

/**
 * Created by 宗祥 on 2017/3/2.
 */
public class SercosType {

    private String data;


    private SercosTypeData sercosTypeData;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public SercosTypeData getSercosTypeData() {
        return sercosTypeData;
    }

    public void setSercosTypeData(SercosTypeData sercosTypeData) {
        this.sercosTypeData = sercosTypeData;
    }

    @Override
    public String toString() {
        return "SercosType{" +
                "data='" + data + '\'' +
                ", sercosTypeData=" + sercosTypeData +
                '}';
    }
}
