package sercos.process.entity;

import sercos.process.common.CommonPhase;
import sercos.process.common.SercosType;

/**
 * Created by 宗祥 on 2017/3/2.
 */
public class ResultEntity {

    /**
     * TelegramFrame
     */
    private TelegramFrame telegramFrame;


    private Ethernet ethernet;

    /**
     * MDT还是AT
     */
    private SercosType sercosType;

    /**
     * 状态
     */
    private CommonPhase commonPhase;

    private Sercos data;

    public TelegramFrame getTelegramFrame() {
        return telegramFrame;
    }

    public void setTelegramFrame(TelegramFrame telegramFrame) {
        this.telegramFrame = telegramFrame;
    }

    public SercosType getSercosType() {
        return sercosType;
    }

    public void setSercosType(SercosType sercosType) {
        this.sercosType = sercosType;
    }

    public CommonPhase getCommonPhase() {
        return commonPhase;
    }

    public void setCommonPhase(CommonPhase commonPhase) {
        this.commonPhase = commonPhase;
    }

    public Ethernet getEthernet() {
        return ethernet;
    }

    public void setEthernet(Ethernet ethernet) {
        this.ethernet = ethernet;
    }

    public Sercos getData() {
        return data;
    }

    public void setData(Sercos data) {
        this.data = data;
    }
}
