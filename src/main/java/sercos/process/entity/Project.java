package sercos.process.entity;

import java.util.List;

/**
 * Created by 宗祥 on 2017/3/9.
 */
public class Project {

    private int serviceJobs;

    private int fifoSize;

    private int atEnd2InterruptDistance;

    private int telegramMethod;

    private int minStartTelBlock2;

    private List<Ring> ringList;

    public int getServiceJobs() {
        return serviceJobs;
    }

    public void setServiceJobs(int serviceJobs) {
        this.serviceJobs = serviceJobs;
    }

    public int getFifoSize() {
        return fifoSize;
    }

    public void setFifoSize(int fifoSize) {
        this.fifoSize = fifoSize;
    }

    public int getAtEnd2InterruptDistance() {
        return atEnd2InterruptDistance;
    }

    public void setAtEnd2InterruptDistance(int atEnd2InterruptDistance) {
        this.atEnd2InterruptDistance = atEnd2InterruptDistance;
    }

    public int getTelegramMethod() {
        return telegramMethod;
    }

    public void setTelegramMethod(int telegramMethod) {
        this.telegramMethod = telegramMethod;
    }

    public int getMinStartTelBlock2() {
        return minStartTelBlock2;
    }

    public void setMinStartTelBlock2(int minStartTelBlock2) {
        this.minStartTelBlock2 = minStartTelBlock2;
    }

    public List<Ring> getRingList() {
        return ringList;
    }

    public void setRingList(List<Ring> ringList) {
        this.ringList = ringList;
    }

    @Override
    public String toString() {
        return "Project{" +
                "serviceJobs=" + serviceJobs +
                ", fifoSize=" + fifoSize +
                ", atEnd2InterruptDistance=" + atEnd2InterruptDistance +
                ", telegramMethod=" + telegramMethod +
                ", minStartTelBlock2=" + minStartTelBlock2 +
                ", ringList=" + ringList +
                '}';
    }
}
