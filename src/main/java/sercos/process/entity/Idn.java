package sercos.process.entity;

/**
 * Created by 宗祥 on 2017/3/9.
 */
public class Idn {

    private String idnNumber;

    private int teleLen;

    @Override
    public String toString() {
        return "Idn{" +
                "idnNumber='" + idnNumber + '\'' +
                ", teleLen=" + teleLen +
                '}';
    }

    public String getIdnNumber() {
        return idnNumber;
    }

    public void setIdnNumber(String idnNumber) {
        this.idnNumber = idnNumber;
    }

    public int getTeleLen() {
        return teleLen;
    }

    public void setTeleLen(int teleLen) {
        this.teleLen = teleLen;
    }
}
