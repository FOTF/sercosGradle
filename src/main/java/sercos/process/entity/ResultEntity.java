package sercos.process.entity;

import sercos.process.common.CommonPhase;
import sercos.process.common.SercosPhase;
import sercos.process.common.SercosType;

/**
 * Created by 宗祥 on 2017/3/2.
 */
public class ResultEntity {

    private int num;

    /**
     * TelegramFrame
     */
    private TelegramFrame telegramFrame;


    private Ethernet ethernet;

    private Sercos sercos;

    /**
     * 状态
     */
    private CommonPhase commonPhase;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public TelegramFrame getTelegramFrame() {
        return telegramFrame;
    }

    public void setTelegramFrame(TelegramFrame telegramFrame) {
        this.telegramFrame = telegramFrame;
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

    public Sercos getSercos() {
        return sercos;
    }

    public void setSercos(Sercos sercos) {
        this.sercos = sercos;
    }

    @Override
    public String toString() {
        return "ResultEntity{" +
                "telegramFrame=" + telegramFrame +
                ", ethernet=" + ethernet +
                ", sercos=" + sercos +
                ", commonPhase=" + commonPhase +
                '}';
    }
}
