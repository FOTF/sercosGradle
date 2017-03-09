package sercos.process.entity;

import java.util.List;

/**
 * Created by 宗祥 on 2017/3/9.
 */
public class Connection {

    private String status;

    /**
     * AT/MDT
     */
    private String telegram;

    private int deviceStatus;

    private int deviceControl;

    private int applicationID;

    private String connectionName;

    private int connectionClass;

    private String connectionConfType;

    private int connectionID;

    private int connectionType;

    private int producerCycleTime;

    private int maxDataLos;

    private int teleType;

    private List<Idn> idnList;

    @Override
    public String toString() {
        return "Connection{" +
                "status='" + status + '\'' +
                ", telegram='" + telegram + '\'' +
                ", deviceStatus=" + deviceStatus +
                ", deviceControl=" + deviceControl +
                ", applicationID=" + applicationID +
                ", connectionName='" + connectionName + '\'' +
                ", connectionClass=" + connectionClass +
                ", connectionConfType='" + connectionConfType + '\'' +
                ", connectionID=" + connectionID +
                ", connectionType=" + connectionType +
                ", producerCycleTime=" + producerCycleTime +
                ", maxDataLos=" + maxDataLos +
                ", teleType=" + teleType +
                ", idnList=" + idnList +
                '}';
    }

    public int getDeviceControl() {
        return deviceControl;
    }

    public void setDeviceControl(int deviceControl) {
        this.deviceControl = deviceControl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public int getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(int deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public int getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(int applicationID) {
        this.applicationID = applicationID;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public int getConnectionClass() {
        return connectionClass;
    }

    public void setConnectionClass(int connectionClass) {
        this.connectionClass = connectionClass;
    }

    public String getConnectionConfType() {
        return connectionConfType;
    }

    public void setConnectionConfType(String connectionConfType) {
        this.connectionConfType = connectionConfType;
    }

    public int getConnectionID() {
        return connectionID;
    }

    public void setConnectionID(int connectionID) {
        this.connectionID = connectionID;
    }

    public int getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(int connectionType) {
        this.connectionType = connectionType;
    }

    public int getProducerCycleTime() {
        return producerCycleTime;
    }

    public void setProducerCycleTime(int producerCycleTime) {
        this.producerCycleTime = producerCycleTime;
    }

    public int getMaxDataLos() {
        return maxDataLos;
    }

    public void setMaxDataLos(int maxDataLos) {
        this.maxDataLos = maxDataLos;
    }

    public int getTeleType() {
        return teleType;
    }

    public void setTeleType(int teleType) {
        this.teleType = teleType;
    }

    public List<Idn> getIdnList() {
        return idnList;
    }

    public void setIdnList(List<Idn> idnList) {
        this.idnList = idnList;
    }
}
