package sercos.process.entity;

import java.util.List;

/**
 * Created by 宗祥 on 2017/3/9.
 */
public class Slave {

    /**
     * Log axis number is unique in whole project
     */
    private int logAxisNr;

    /**
     * the device address is unique in a SERCOS ring
     */
    private int drvAdr;

    private int maxNbrTelErrS1003;

    /**
     * Mac address in hex format
     */
    private String macAddress;

    private List<Connection> connectionList;

    @Override
    public String toString() {
        return "Slave{" +
                "logAxisNr=" + logAxisNr +
                ", drvAdr=" + drvAdr +
                ", maxNbrTelErrS1003=" + maxNbrTelErrS1003 +
                ", macAddress='" + macAddress + '\'' +
                ", connectionList=" + connectionList +
                '}';
    }

    public int getLogAxisNr() {
        return logAxisNr;
    }

    public void setLogAxisNr(int logAxisNr) {
        this.logAxisNr = logAxisNr;
    }

    public int getDrvAdr() {
        return drvAdr;
    }

    public void setDrvAdr(int drvAdr) {
        this.drvAdr = drvAdr;
    }

    public int getMaxNbrTelErrS1003() {
        return maxNbrTelErrS1003;
    }

    public void setMaxNbrTelErrS1003(int maxNbrTelErrS1003) {
        this.maxNbrTelErrS1003 = maxNbrTelErrS1003;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public List<Connection> getConnectionList() {
        return connectionList;
    }

    public void setConnectionList(List<Connection> connectionList) {
        this.connectionList = connectionList;
    }
}
