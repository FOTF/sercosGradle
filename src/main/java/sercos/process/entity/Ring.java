package sercos.process.entity;

import java.util.List;

/**
 * Created by 宗祥 on 2017/3/9.
 */
public class Ring {

    private int sercosCardNo;

    private int sercosChannel;

    private int masterRing;

    private int serviceChannelTimeOut;

    private int cycleTimeP0P2;

    private int ncCycleTime;

    private int serCycleTime;

    private int cycClkActive;

    private int cycClkPolarity;

    private int cycClkEnable;

    private int cycClkStartDelay;

    private int conClkPolarity;

    private int conClkEnable;

    private int conClkEnableDriver;

    private int conClkStart;

    private int conClkEnd;

    private int ipChannelTime;

    private int ipChannelMaxLengthP34;

    private int ipCBandwidth;

    private int hotPlug;

    private int dmaPCImode;

    private int communicationVersion;

    private String macAddress;

    private int maxTelLoss;

    private int jitterMaster;

    private int jitterStartAT;

    private int syncJitterS01023;

    private int delayMaster;

    private int delaySlave;

    private int delayComponent;

    private List<Slave> slaveList;

    public int getSercosCardNo() {
        return sercosCardNo;
    }

    public void setSercosCardNo(int sercosCardNo) {
        this.sercosCardNo = sercosCardNo;
    }

    public int getSercosChannel() {
        return sercosChannel;
    }

    public void setSercosChannel(int sercosChannel) {
        this.sercosChannel = sercosChannel;
    }

    public int getMasterRing() {
        return masterRing;
    }

    public void setMasterRing(int masterRing) {
        this.masterRing = masterRing;
    }

    public int getServiceChannelTimeOut() {
        return serviceChannelTimeOut;
    }

    public void setServiceChannelTimeOut(int serviceChannelTimeOut) {
        this.serviceChannelTimeOut = serviceChannelTimeOut;
    }

    public int getCycleTimeP0P2() {
        return cycleTimeP0P2;
    }

    public void setCycleTimeP0P2(int cycleTimeP0P2) {
        this.cycleTimeP0P2 = cycleTimeP0P2;
    }

    public int getNcCycleTime() {
        return ncCycleTime;
    }

    public void setNcCycleTime(int ncCycleTime) {
        this.ncCycleTime = ncCycleTime;
    }

    public int getSerCycleTime() {
        return serCycleTime;
    }

    public void setSerCycleTime(int serCycleTime) {
        this.serCycleTime = serCycleTime;
    }

    public int getCycClkActive() {
        return cycClkActive;
    }

    public void setCycClkActive(int cycClkActive) {
        this.cycClkActive = cycClkActive;
    }

    public int getCycClkPolarity() {
        return cycClkPolarity;
    }

    public void setCycClkPolarity(int cycClkPolarity) {
        this.cycClkPolarity = cycClkPolarity;
    }

    public int getCycClkEnable() {
        return cycClkEnable;
    }

    public void setCycClkEnable(int cycClkEnable) {
        this.cycClkEnable = cycClkEnable;
    }

    public int getCycClkStartDelay() {
        return cycClkStartDelay;
    }

    public void setCycClkStartDelay(int cycClkStartDelay) {
        this.cycClkStartDelay = cycClkStartDelay;
    }

    public int getConClkPolarity() {
        return conClkPolarity;
    }

    public void setConClkPolarity(int conClkPolarity) {
        this.conClkPolarity = conClkPolarity;
    }

    public int getConClkEnable() {
        return conClkEnable;
    }

    public void setConClkEnable(int conClkEnable) {
        this.conClkEnable = conClkEnable;
    }

    public int getConClkEnableDriver() {
        return conClkEnableDriver;
    }

    public void setConClkEnableDriver(int conClkEnableDriver) {
        this.conClkEnableDriver = conClkEnableDriver;
    }

    public int getConClkStart() {
        return conClkStart;
    }

    public void setConClkStart(int conClkStart) {
        this.conClkStart = conClkStart;
    }

    public int getConClkEnd() {
        return conClkEnd;
    }

    public void setConClkEnd(int conClkEnd) {
        this.conClkEnd = conClkEnd;
    }

    public int getIpChannelTime() {
        return ipChannelTime;
    }

    public void setIpChannelTime(int ipChannelTime) {
        this.ipChannelTime = ipChannelTime;
    }

    public int getIpChannelMaxLengthP34() {
        return ipChannelMaxLengthP34;
    }

    public void setIpChannelMaxLengthP34(int ipChannelMaxLengthP34) {
        this.ipChannelMaxLengthP34 = ipChannelMaxLengthP34;
    }

    public int getIpCBandwidth() {
        return ipCBandwidth;
    }

    public void setIpCBandwidth(int ipCBandwidth) {
        this.ipCBandwidth = ipCBandwidth;
    }

    public int getHotPlug() {
        return hotPlug;
    }

    public void setHotPlug(int hotPlug) {
        this.hotPlug = hotPlug;
    }

    public int getDmaPCImode() {
        return dmaPCImode;
    }

    public void setDmaPCImode(int dmaPCImode) {
        this.dmaPCImode = dmaPCImode;
    }

    public int getCommunicationVersion() {
        return communicationVersion;
    }

    public void setCommunicationVersion(int communicationVersion) {
        this.communicationVersion = communicationVersion;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public int getMaxTelLoss() {
        return maxTelLoss;
    }

    public void setMaxTelLoss(int maxTelLoss) {
        this.maxTelLoss = maxTelLoss;
    }

    public int getJitterMaster() {
        return jitterMaster;
    }

    public void setJitterMaster(int jitterMaster) {
        this.jitterMaster = jitterMaster;
    }

    public int getJitterStartAT() {
        return jitterStartAT;
    }

    public void setJitterStartAT(int jitterStartAT) {
        this.jitterStartAT = jitterStartAT;
    }

    public int getSyncJitterS01023() {
        return syncJitterS01023;
    }

    public void setSyncJitterS01023(int syncJitterS01023) {
        this.syncJitterS01023 = syncJitterS01023;
    }

    public int getDelayMaster() {
        return delayMaster;
    }

    public void setDelayMaster(int delayMaster) {
        this.delayMaster = delayMaster;
    }

    public int getDelaySlave() {
        return delaySlave;
    }

    public void setDelaySlave(int delaySlave) {
        this.delaySlave = delaySlave;
    }

    public int getDelayComponent() {
        return delayComponent;
    }

    public void setDelayComponent(int delayComponent) {
        this.delayComponent = delayComponent;
    }

    public List<Slave> getSlaveList() {
        return slaveList;
    }

    public void setSlaveList(List<Slave> slaveList) {
        this.slaveList = slaveList;
    }

    @Override
    public String toString() {
        return "Ring{" +
                "sercosCardNo=" + sercosCardNo +
                ", sercosChannel=" + sercosChannel +
                ", masterRing=" + masterRing +
                ", serviceChannelTimeOut=" + serviceChannelTimeOut +
                ", cycleTimeP0P2=" + cycleTimeP0P2 +
                ", ncCycleTime=" + ncCycleTime +
                ", serCycleTime=" + serCycleTime +
                ", cycClkActive=" + cycClkActive +
                ", cycClkPolarity=" + cycClkPolarity +
                ", cycClkEnable=" + cycClkEnable +
                ", cycClkStartDelay=" + cycClkStartDelay +
                ", conClkPolarity=" + conClkPolarity +
                ", conClkEnable=" + conClkEnable +
                ", conClkEnableDriver=" + conClkEnableDriver +
                ", conClkStart=" + conClkStart +
                ", conClkEnd=" + conClkEnd +
                ", ipChannelTime=" + ipChannelTime +
                ", ipChannelMaxLengthP34=" + ipChannelMaxLengthP34 +
                ", ipCBandwidth=" + ipCBandwidth +
                ", hotPlug=" + hotPlug +
                ", dmaPCImode=" + dmaPCImode +
                ", communicationVersion=" + communicationVersion +
                ", macAddress='" + macAddress + '\'' +
                ", maxTelLoss=" + maxTelLoss +
                ", jitterMaster=" + jitterMaster +
                ", jitterStartAT=" + jitterStartAT +
                ", syncJitterS01023=" + syncJitterS01023 +
                ", delayMaster=" + delayMaster +
                ", delaySlave=" + delaySlave +
                ", delayComponent=" + delayComponent +
                ", slaveList=" + slaveList +
                '}';
    }
}
