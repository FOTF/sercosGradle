package sercos.process.common;

/**
 * Created by 宗祥 on 2017/3/6.
 */
public class SercosPhaseData {

    private String communPhase;

    private String communPhaseSwitch;

    private String cycleCnt;

    public String getCommunPhase() {
        return communPhase;
    }

    public void setCommunPhase(String communPhase) {
        this.communPhase = communPhase;
    }

    public String getCommunPhaseSwitch() {
        return communPhaseSwitch;
    }

    public void setCommunPhaseSwitch(String communPhaseSwitch) {
        this.communPhaseSwitch = communPhaseSwitch;
    }

    public String getCycleCnt() {
        return cycleCnt;
    }

    public void setCycleCnt(String cycleCnt) {
        this.cycleCnt = cycleCnt;
    }

    @Override
    public String toString() {
        return "SercosPhaseData{" +
                "communPhase='" + communPhase + '\'' +
                ", communPhaseSwitch='" + communPhaseSwitch + '\'' +
                ", cycleCnt='" + cycleCnt + '\'' +
                '}';
    }
}
