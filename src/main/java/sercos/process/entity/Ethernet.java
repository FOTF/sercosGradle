package sercos.process.entity;

/**
 * Created by kai on 2017/3/2.
 */
public class Ethernet {

    /**
     * 目标地址
     */
    private String destAddress;

    /**
     * 源地址
     */
    private String sourceAddress;

    private String ethernetType;

    private String serviceDataUnit;

    public String getDestAddress() {
        return destAddress;
    }

    public void setDestAddress(String destAddress) {
        this.destAddress = destAddress;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getEthernetType() {
        return ethernetType;
    }

    public void setEthernetType(String ethernetType) {
        this.ethernetType = ethernetType;
    }

    public String getServiceDataUnit() {
        return serviceDataUnit;
    }

    public void setServiceDataUnit(String serviceDataUnit) {
        this.serviceDataUnit = serviceDataUnit;
    }

    @Override
    public String toString() {
        return "Ethernet{" +
                "destAddress='" + destAddress + '\'' +
                ", sourceAddress='" + sourceAddress + '\'' +
                ", ethernetType='" + ethernetType + '\'' +
                ", serviceDataUnit='" + serviceDataUnit + '\'' +
                '}';
    }
}
