package sercos.process.deal;

import sercos.process.entity.ResultEntity;
import sercos.process.entity.Slave;

import java.util.HashMap;
import java.util.Map;

/**
 * 从站处理
 * Created by 宗祥 on 2017/3/15.
 */
public class SlaveProcess {

    //从站地址，读取DrvAdr
    private String address;

    private String macAdress = null;

    private Slave slave = null;

    private Map<String, String> slaveMacInfoMap = new HashMap<>();

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public SlaveProcess(Slave slave) {
        this.slave = slave;
        macAdress = slave.getMacAddress() + " " + slave.getDrvAdr();
        address = slave.getDrvAdr() + "";
    }


    /**
     * 从站接收mdt报文
     */
    public void receiveMdtAndDeal(ResultEntity resultMdtEntity){
        String cpNum = resultMdtEntity.getSercos().getSercosPhase().getSercosPhaseData().getCommunPhase();

        if("CP0".equals(cpNum)){
            //do nothing
        }else if("CP1".equals(cpNum)){

        }else if("CP2".equals(cpNum)){

        }
    }

    /**
     * 从站接收at报文
     */
    public void receiveAtAndDeal(ResultEntity resultAtEntity){
        String cpNum = resultAtEntity.getSercos().getSercosPhase().getSercosPhaseData().getCommunPhase();
        if("CP0".equals(cpNum)){
            //写入自己的地址
            Map<String, Object> topoIndicList = (Map<String, Object>)resultAtEntity.getSercos().getSercosAT().get("topoIndics");
            topoIndicList.put(macAdress, address);
        }else if("CP1".equals(cpNum)){

        }else if("CP2".equals(cpNum)){
            Map<String, Object> slaveListMap = resultAtEntity.getSercos().getSlaves();
            //获取自己的对象
            Map<String, Object> slaveMap = (Map<String, Object>) slaveListMap.get("Slave " + this.macAdress);
            Map<String, Object> slaveMapDetail = (Map<String, Object>) slaveMap.get("Slave " + this.address + ":[Addr. " + this.macAdress + "]");
            Map<String, Object> slaveMapDetailInfo2 = (Map<String, Object>) slaveMapDetail.get("SVC - Slave " + this.address + ":0x09 0x00 0x00 0x00 0x00 0x00");
            Map<String, Object> slaveMapDetailInfo21 = (Map<String, Object>) slaveMapDetailInfo2.get("SVC Status - Slave " + this.address + ": 0x0009");
            slaveMapDetailInfo21.put("Busy Bit", "finished");
        }
    }
}
