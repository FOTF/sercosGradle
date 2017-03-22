package sercos.process.deal;

import org.apache.commons.lang3.StringUtils;
import sercos.process.common.SercosPhase;
import sercos.process.common.SercosPhaseData;
import sercos.process.common.SercosType;
import sercos.process.common.SercosTypeData;
import sercos.process.entity.*;

import java.util.*;

/**
 * 主站处理
 * Created by 宗祥 on 2017/3/15.
 */
public class MasterProcess {

    private SercosObject sercosObject = null;

//    private ResultEntity resultMdtEntity = null;

//    private ResultEntity resultAtEntity = null;

    //主站从从站获取的信息的对应关系,从站的地址
    private Map<String, String> slaveMacInfoMap = new HashMap<>();

    public void setSlaveMacInfoMap(Map<String, String> slaveMacInfoMap) {
        this.slaveMacInfoMap = slaveMacInfoMap;
    }

    public MasterProcess(SercosObject sercosObject) {
        this.sercosObject = sercosObject;
    }

    public SercosObject getSercosObject() {
        return sercosObject;
    }

    public void setSercosObject(SercosObject sercosObject) {
        this.sercosObject = sercosObject;
    }

    /**
     * 初始化mdt cp0报文
     */
    public ResultEntity initMdtcp0Baowen(){
        ResultEntity intiResultEntity = new ResultEntity();
        TelegramFrame telegramFrame = new TelegramFrame();
        telegramFrame.setTelegramLength("60");
        telegramFrame.setNowTime(System.nanoTime());
        intiResultEntity.setTelegramFrame(telegramFrame);

        Ethernet ethernet = new Ethernet();
        ethernet.setDestAddress("FF:FF:FF:FF:FF:FF");
        ethernet.setSourceAddress(sercosObject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getMacAddress());
        ethernet.setEthernetType("0x88CD");
        intiResultEntity.setEthernet(ethernet);

        Sercos sercos = new Sercos();

        SercosPhase sercosPhase = new SercosPhase();
        SercosPhaseData sercosPhaseData = new SercosPhaseData();
        sercosPhaseData.setCycleCnt("0x00");
        sercosPhaseData.setCommunPhaseSwitch("current CP");
        sercosPhaseData.setCommunPhase("CP0");
        sercosPhase.setSercosPhaseData(sercosPhaseData);
        sercos.setSercosPhase(sercosPhase);
        SercosType sercosType = new SercosType();
        SercosTypeData sercosTypeData = new SercosTypeData();
        sercosTypeData.setMdtOrAt("MDT");
        sercosTypeData.setSercosTeleNum("0");
        sercosTypeData.setPsTelegram("P");
        sercosTypeData.setCycleCndEnable("enabled");
        sercosType.setSercosTypeData(sercosTypeData);
        sercos.setSercosType(sercosType);

        if("MDT".equals(sercosTypeData.getMdtOrAt())){
            Map<String, Object> sercosMDT = new HashMap<>();

            Map<String, Object> communVersionMap = new HashMap<>();
            communVersionMap.put("tranmistationCP", "no trans");
            communVersionMap.put("structureNumTele", 2);
            communVersionMap.put("adressAlloc", "alloc");
            sercosMDT.put("communVersion", communVersionMap);
            sercos.setSercosMDT(sercosMDT);
        }else{
            Map<String, Object> sercosAT = new HashMap<>();
            sercosAT.put("sqquence", "0x0001");
            sercos.setSercosAT(sercosAT);
        }

        /*List<Map<String, Object>> slaveMapList = new ArrayList<>();
        Map<String, Object> slaveMap = null;
        List<Slave> slaveList = sercosObject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getSlaveList();
        for(int i = 0; i < slaveList.size(); i++){
            slaveMap = new HashMap<>();
            slaveMap.put("name", "Slave" + (i + 1));
            slaveMap.put("addr", "");
            slaveMap.put("macAddress", slaveList.get(i).getMacAddress());
            slaveMapList.add(slaveMap);
        }
        sercos.setSlaves(slaveMapList);*/
        intiResultEntity.setSercos(sercos);
        return intiResultEntity;
    }

    /**
     * 初始化at cp0报文
     */
    public ResultEntity initAtcp0Baowen(){
        ResultEntity intiResultEntity = new ResultEntity();
        TelegramFrame telegramFrame = new TelegramFrame();
        telegramFrame.setTelegramLength("1044");
        telegramFrame.setNowTime(System.nanoTime());
        intiResultEntity.setTelegramFrame(telegramFrame);

        Ethernet ethernet = new Ethernet();
        ethernet.setDestAddress("FF:FF:FF:FF:FF:FF");
        ethernet.setSourceAddress(sercosObject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getMacAddress());
        ethernet.setEthernetType("0x88CD");
        intiResultEntity.setEthernet(ethernet);

        Sercos sercos = new Sercos();

        SercosPhase sercosPhase = new SercosPhase();
        SercosPhaseData sercosPhaseData = new SercosPhaseData();
        sercosPhaseData.setCycleCnt("0x00");
        sercosPhaseData.setCommunPhaseSwitch("current CP");
        sercosPhaseData.setCommunPhase("CP0");
        sercosPhase.setSercosPhaseData(sercosPhaseData);
        sercos.setSercosPhase(sercosPhase);
        SercosType sercosType = new SercosType();
        SercosTypeData sercosTypeData = new SercosTypeData();
        sercosTypeData.setMdtOrAt("AT");
        sercosTypeData.setSercosTeleNum("0");
        sercosTypeData.setPsTelegram("P");
        sercosTypeData.setCycleCndEnable("enabled");
        sercosType.setSercosTypeData(sercosTypeData);
        sercos.setSercosType(sercosType);

        List<Slave> slaveList = sercosObject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getSlaveList();

        Map<String, Object> sercosAT = new HashMap<>();
        sercosAT.put("sqquenceCounter", "0x0004");
        Map<String, Object> topoIndicList = new HashMap<>();
        for(int i = 0; i < slaveList.size(); i++){
            topoIndicList.put(slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr(), "");
        }
        sercosAT.put("topoIndics", topoIndicList);
        sercos.setSercosAT(sercosAT);

        /*List<Map<String, Object>> slaveMapList = new ArrayList<>();
        Map<String, Object> slaveMap = null;
        for(int i = 0; i < slaveList.size(); i++){
            slaveMap = new HashMap<>();
            slaveMap.put("name", "Slave" + (i + 1));
            slaveMap.put("addr", "");
            slaveMap.put("macAddress", slaveList.get(i).getMacAddress());
            slaveMapList.add(slaveMap);
        }
        sercos.setSlaves(slaveMapList);*/
        intiResultEntity.setSercos(sercos);
        return intiResultEntity;
    }

    /**
     * 初始化mdt cp1报文
     */
    public ResultEntity initMdtcp1Baowen(){
        ResultEntity intiResultEntity = new ResultEntity();
        TelegramFrame telegramFrame = new TelegramFrame();
        telegramFrame.setTelegramLength("1300");
        telegramFrame.setNowTime(System.nanoTime());
        intiResultEntity.setTelegramFrame(telegramFrame);

        Ethernet ethernet = new Ethernet();
        ethernet.setDestAddress("FF:FF:FF:FF:FF:FF");
        ethernet.setSourceAddress(sercosObject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getMacAddress());
        ethernet.setEthernetType("0x88CD");
        intiResultEntity.setEthernet(ethernet);

        Sercos sercos = new Sercos();

        SercosPhase sercosPhase = new SercosPhase();
        SercosPhaseData sercosPhaseData = new SercosPhaseData();
        sercosPhaseData.setCycleCnt("0x00");
        sercosPhaseData.setCommunPhaseSwitch("current CP");
        sercosPhaseData.setCommunPhase("CP1");
        sercosPhase.setSercosPhaseData(sercosPhaseData);
        sercos.setSercosPhase(sercosPhase);
        SercosType sercosType = new SercosType();
        SercosTypeData sercosTypeData = new SercosTypeData();
        sercosTypeData.setMdtOrAt("MDT");
        sercosTypeData.setSercosTeleNum("0");
        sercosTypeData.setPsTelegram("P");
        sercosTypeData.setCycleCndEnable("enabled");
        sercosType.setSercosTypeData(sercosTypeData);
        sercos.setSercosType(sercosType);


        List<Slave> slaveList = sercosObject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getSlaveList();
        Map<String, Object> sercosMDT = new HashMap<>();

        Map<String, Object> mdtDeviceControWord = new HashMap<>();
        for(int i = 0; i < slaveList.size(); i++){
            Map<String, Object> mdtDeviceControWordInfo = new HashMap<>();
            mdtDeviceControWordInfo.put("Idenfication", "no request");
            mdtDeviceControWordInfo.put("Topology Handshake", "0");
            mdtDeviceControWordInfo.put("Topology Control", "FF");
            mdtDeviceControWordInfo.put("Control physical topology", "broken");
            mdtDeviceControWordInfo.put("Master Vaild Node", "not valid");
            mdtDeviceControWord.put("MDT Device Control - Slave " + (i + 1) + " [Addr. " + slaveMacInfoMap.get(slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr()) + "]:0x0000", mdtDeviceControWordInfo);
        }
        sercosMDT.put("mdtDeviceControWord", mdtDeviceControWord);

        Map<String, Object> mdtServiceChannels = new HashMap<>();
        for(int i = 0; i < slaveList.size(); i++){
            Map<String, Object> mdtSvcContorl = new HashMap<>();
            Map<String, Object> mdtSvcContorInfo = new HashMap<>();
            mdtSvcContorInfo.put("Element", "not active");
            mdtSvcContorInfo.put("Last transmission", "in progr.");
            mdtSvcContorInfo.put("Read or Write", "R");
            mdtSvcContorInfo.put("Master Handshake Bit", "1");
            mdtSvcContorl.put("MDT SVC Control - Slave " + (i + 1) + ":0x0001", mdtSvcContorInfo);
            mdtSvcContorl.put("MDT SVC Info - Slave " + (i + 1) + "", "0x00000000");
            mdtServiceChannels.put("MDT SVC - Slave " + (i + 1) + " [Addr. " + slaveMacInfoMap.get(slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr()) + "]:0x01 0x00 0x00 0x00 0x00 0x00", mdtSvcContorl);
        }
        sercosMDT.put("mdtServiceChannels", mdtServiceChannels);


        sercos.setSercosMDT(sercosMDT);

        List<Map<String, Object>> slaveMapList = new ArrayList<>();
        Map<String, Object> slaveMap = null;

        /*for(int i = 0; i < slaveList.size(); i++){
            slaveMap = new HashMap<>();
            slaveMap.put("name", "Slave" + (i + 1));
            slaveMap.put("addr", "");
            slaveMap.put("macAddress", slaveList.get(i).getMacAddress());
            slaveMapList.add(slaveMap);
        }
        sercos.setSlaves(slaveMapList);*/
        intiResultEntity.setSercos(sercos);
        return intiResultEntity;
    }

    /**
     * 初始化at cp1报文
     */
    public ResultEntity initAtcp1Baowen(){
        ResultEntity intiResultEntity = new ResultEntity();
        TelegramFrame telegramFrame = new TelegramFrame();
        telegramFrame.setTelegramLength("1300");
        telegramFrame.setNowTime(System.nanoTime());
        intiResultEntity.setTelegramFrame(telegramFrame);

        Ethernet ethernet = new Ethernet();
        ethernet.setDestAddress("FF:FF:FF:FF:FF:FF");
        ethernet.setSourceAddress(sercosObject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getMacAddress());
        ethernet.setEthernetType("0x88CD");
        intiResultEntity.setEthernet(ethernet);

        Sercos sercos = new Sercos();

        SercosPhase sercosPhase = new SercosPhase();
        SercosPhaseData sercosPhaseData = new SercosPhaseData();
        sercosPhaseData.setCycleCnt("0x00");
        sercosPhaseData.setCommunPhaseSwitch("current CP");
        sercosPhaseData.setCommunPhase("CP1");
        sercosPhase.setSercosPhaseData(sercosPhaseData);
        sercos.setSercosPhase(sercosPhase);
        SercosType sercosType = new SercosType();
        SercosTypeData sercosTypeData = new SercosTypeData();
        sercosTypeData.setMdtOrAt("AT");
        sercosTypeData.setSercosTeleNum("0");
        sercosTypeData.setPsTelegram("P");
        sercosTypeData.setCycleCndEnable("enabled");
        sercosType.setSercosTypeData(sercosTypeData);
        sercos.setSercosType(sercosType);

        List<Slave> slaveList = sercosObject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getSlaveList();
        /*List<Map<String, Object>> slaveMapList = new ArrayList<>();
        Map<String, Object> slaveMap = new HashMap<>();

        for(int i = 0; i < slaveList.size(); i++){
            Map<String, Object> slaveMapDetail = new HashMap<>();
            Map<String, Object> slaveMapDetailInfo1 = new HashMap<>();
            slaveMapDetailInfo1.put("Communcation Warning Interface", "warning");
            slaveMapDetailInfo1.put("Topology handshake", "0");
            slaveMapDetailInfo1.put("Topology Status", "FF diag.");
            slaveMapDetailInfo1.put("Error Connection", "error");
            slaveMapDetailInfo1.put("Slave Vaild", "vaild");
            slaveMapDetailInfo1.put("Error of Device(C1D)", "no error");
            slaveMapDetailInfo1.put("Warning of Device(C2D)", "no waring");
            slaveMapDetailInfo1.put("Procedure Command Change Bit", "no change");
            slaveMapDetailInfo1.put("Sub-Device Level", "PL");
            slaveMapDetail.put("Device Status - Slave " + (i + 1) + ":0x8701", slaveMapDetailInfo1);

            Map<String, Object> slaveMapDetailInfo2 = new HashMap<>();
            Map<String, Object> slaveMapDetailInfo21 = new HashMap<>();
            slaveMapDetailInfo21.put("SVC processing", "vaild");
            slaveMapDetailInfo21.put("SVC error", "no error");
            slaveMapDetailInfo21.put("Busy Bit", "finished");
            slaveMapDetailInfo21.put("Acknowledge Hansshake Bit", "1");
            slaveMapDetailInfo2.put("SVC Status - Slave " + (i + 1) + ": 0x0009", slaveMapDetailInfo21);
            slaveMapDetail.put("SVC - Slave " + (i + 1) + ":0x09 0x00 0x00 0x00 0x00 0x00", slaveMapDetailInfo2);
            slaveMap.put("Slave " + (i + 1) + ":[Addr. " + slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr() + "]", slaveMapDetail);
        }
        slaveMapList.add(slaveMap);
        sercos.setSlaves(slaveMapList);*/

        Map<String, Object> slaveListMap = new HashMap<>();
        Map<String, Object> slaveMap = new HashMap<>();

        for(int i = 0; i < slaveList.size(); i++){
            Map<String, Object> slaveMapDetail = new HashMap<>();
            Map<String, Object> slaveMapDetailInfo1 = new HashMap<>();
            slaveMapDetailInfo1.put("Communcation Warning Interface", "warning");
            slaveMapDetailInfo1.put("Topology handshake", "0");
            slaveMapDetailInfo1.put("Topology Status", "FF diag.");
            slaveMapDetailInfo1.put("Error Connection", "error");
            slaveMapDetailInfo1.put("Slave Vaild", "vaild");
            slaveMapDetailInfo1.put("Error of Device(C1D)", "no error");
            slaveMapDetailInfo1.put("Warning of Device(C2D)", "no waring");
            slaveMapDetailInfo1.put("Procedure Command Change Bit", "no change");
            slaveMapDetailInfo1.put("Sub-Device Level", "PL");
            slaveMapDetail.put("Device Status - Slave " + (i + 1) + ":0x8701", slaveMapDetailInfo1);

            Map<String, Object> slaveMapDetailInfo2 = new HashMap<>();
            Map<String, Object> slaveMapDetailInfo21 = new HashMap<>();
            slaveMapDetailInfo21.put("SVC processing", "vaild");
            slaveMapDetailInfo21.put("SVC error", "no error");
            slaveMapDetailInfo21.put("Busy Bit", "finished");
            slaveMapDetailInfo21.put("Acknowledge Hansshake Bit", "1");
            slaveMapDetailInfo2.put("SVC Status - Slave " + (i + 1) + ": 0x0009", slaveMapDetailInfo21);
            slaveMapDetail.put("SVC - Slave " + (i + 1) + ":0x09 0x00 0x00 0x00 0x00 0x00", slaveMapDetailInfo2);
            slaveMap.put("Slave " + (i + 1) + ":[Addr. " + slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr() + "]", slaveMapDetail);
            slaveListMap.put("Slave " + slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr(), slaveMap);
        }
        sercos.setSlaves(slaveListMap);
        intiResultEntity.setSercos(sercos);
        return intiResultEntity;
    }


    /**
     * type 代表是什么报文 先是null, 然后idn，
     * @param type
     * @return
     */
    public ResultEntity initMdtcp2Baowen(String type, int connIndex, int idnIndex){

        String blankIdnInfo = "0x00000000";
        String idnInfo = "0x000003F6";

        String opdata = "123456";

        ResultEntity intiResultEntity = new ResultEntity();
        TelegramFrame telegramFrame = new TelegramFrame();
        telegramFrame.setTelegramLength("1300");
        telegramFrame.setNowTime(System.nanoTime());
        intiResultEntity.setTelegramFrame(telegramFrame);

        Ethernet ethernet = new Ethernet();
        ethernet.setDestAddress("FF:FF:FF:FF:FF:FF");
        ethernet.setSourceAddress(sercosObject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getMacAddress());
        ethernet.setEthernetType("0x88CD");
        intiResultEntity.setEthernet(ethernet);

        Sercos sercos = new Sercos();

        SercosPhase sercosPhase = new SercosPhase();
        SercosPhaseData sercosPhaseData = new SercosPhaseData();
        sercosPhaseData.setCycleCnt("0x00");
        sercosPhaseData.setCommunPhaseSwitch("current CP");
        sercosPhaseData.setCommunPhase("CP2");
        sercosPhase.setSercosPhaseData(sercosPhaseData);
        sercos.setSercosPhase(sercosPhase);
        SercosType sercosType = new SercosType();
        SercosTypeData sercosTypeData = new SercosTypeData();
        sercosTypeData.setMdtOrAt("MDT");
        sercosTypeData.setSercosTeleNum("0");
        sercosTypeData.setPsTelegram("P");
        sercosTypeData.setCycleCndEnable("enabled");
        sercosType.setSercosTypeData(sercosTypeData);
        sercos.setSercosType(sercosType);

        List<Slave> slaveList = sercosObject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getSlaveList();
        Map<String, Object> slaveListMap = new HashMap<>();


        if(StringUtils.isBlank(type)){
            for(int i = 0; i < slaveList.size(); i++){
                Map<String, Object> slaveMap = new HashMap<>();
                Map<String, Object> slaveMapDetail = new HashMap<>();
                Map<String, Object> slaveMapDetailInfo1 = new HashMap<>();
                slaveMapDetailInfo1.put("Idenfication", "no request");
                slaveMapDetailInfo1.put("Topology handshake", "0");
                slaveMapDetailInfo1.put("Topology Control", "FF.");
                slaveMapDetailInfo1.put("Control physical topology", "broken");
                slaveMapDetailInfo1.put("Master Vaild Node", "no vaild");
                slaveMapDetail.put("Device Control - Slave " + (i + 1) + ":0x0000", slaveMapDetailInfo1);

                Map<String, Object> slaveMapDetailInfo2 = new HashMap<>();
                Map<String, Object> slaveMapDetailInfo21 = new HashMap<>();
                slaveMapDetailInfo21.put("Element", "no active");
                slaveMapDetailInfo21.put("Last transimssion", "in progr.");
                slaveMapDetailInfo21.put("Read or Write", "R");
                slaveMapDetailInfo21.put("Mast Handshake Bit", "1");
                slaveMapDetailInfo2.put("SVC Control - Slave " + (i + 1) + ": 0x0001", slaveMapDetailInfo21);
                slaveMapDetailInfo2.put("MDT SVC Info - Slave " + (i + 1), blankIdnInfo);
                slaveMapDetail.put("SVC - Slave " + (i + 1) + ":0x01 0x00 0x00 0x00 0x00 0x00", slaveMapDetailInfo2);
                slaveMap.put("Slave " + (i + 1) + ":[Addr. " + slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr() + "]", slaveMapDetail);
                slaveListMap.put("Slave " + slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr(), slaveMap);
            }
        }else if("idn".equals(type)){
            for(int i = 0; i < slaveList.size(); i++){
                Map<String, Object> slaveMap = new HashMap<>();
                Map<String, Object> slaveMapDetail = new HashMap<>();
                Map<String, Object> slaveMapDetailInfo1 = new HashMap<>();
                slaveMapDetailInfo1.put("Idenfication", "no request");
                slaveMapDetailInfo1.put("Topology handshake", "0");
                slaveMapDetailInfo1.put("Topology Control", "FF.");
                slaveMapDetailInfo1.put("Control physical topology", "broken");
                slaveMapDetailInfo1.put("Master Vaild Node", "no vaild");
                slaveMapDetail.put("Device Control - Slave " + (i + 1) + ":0x0000", slaveMapDetailInfo1);

                Map<String, Object> slaveMapDetailInfo2 = new HashMap<>();
                Map<String, Object> slaveMapDetailInfo21 = new HashMap<>();
                slaveMapDetailInfo21.put("Element", "IDN");

                Connection connection = null;
                if(connIndex >= slaveList.get(i).getConnectionList().size()){
                    connection = slaveList.get(i).getConnectionList().get(slaveList.get(i).getConnectionList().size() - 1);
                    slaveMapDetailInfo21.put("Last transimssion", "last trans");
                }else{
                    connection = slaveList.get(i).getConnectionList().get(connIndex);
                    slaveMapDetailInfo21.put("Last transimssion", "in progr.");
                }

                //判断idn长度是否超过
                Idn idn = null;
                if(idnIndex >= connection.getIdnList().size()){
                    idn = connection.getIdnList().get(connection.getIdnList().size() - 1);
                    slaveMapDetailInfo21.put("Last transimssion", "last trans");
                }else{
                    idn = connection.getIdnList().get(idnIndex);
                    slaveMapDetailInfo21.put("Last transimssion", "in progr.");
                }

                slaveMapDetailInfo21.put("Read or Write", "W");
                slaveMapDetailInfo21.put("Mast Handshake Bit", "0");
                slaveMapDetailInfo2.put("SVC Control - Slave " + (i + 1) + ": 0x0001", slaveMapDetailInfo21);
                slaveMapDetailInfo2.put("MDT SVC Info - Slave " + (i + 1), idnInfo);
                slaveMapDetail.put("SVC - Slave " + (i + 1) + ":0x01 0x00 0x00 0x00 0x00 0x00", slaveMapDetailInfo2);
                slaveMap.put("Slave " + (i + 1) + ":[Addr. " + slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr() + "]", slaveMapDetail);
                slaveListMap.put("Slave " + slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr(), slaveMap);
            }
        }else if("opdata".equals(type)){

            for(int i = 0; i < slaveList.size(); i++){
                Map<String, Object> slaveMap = new HashMap<>();
                Map<String, Object> slaveMapDetail = new HashMap<>();
                Map<String, Object> slaveMapDetailInfo1 = new HashMap<>();
                slaveMapDetailInfo1.put("Idenfication", "no request");
                slaveMapDetailInfo1.put("Topology handshake", "0");
                slaveMapDetailInfo1.put("Topology Control", "FF.");
                slaveMapDetailInfo1.put("Control physical topology", "broken");
                slaveMapDetailInfo1.put("Master Vaild Node", "no vaild");
                slaveMapDetail.put("Device Control - Slave " + (i + 1) + ":0x0000", slaveMapDetailInfo1);

                Map<String, Object> slaveMapDetailInfo2 = new HashMap<>();
                Map<String, Object> slaveMapDetailInfo21 = new HashMap<>();
                slaveMapDetailInfo21.put("Element", "op.data");
                slaveMapDetailInfo21.put("Last transimssion", "last trans");
                slaveMapDetailInfo21.put("Read or Write", "W");
                slaveMapDetailInfo21.put("Mast Handshake Bit", "1");
                slaveMapDetailInfo2.put("SVC Control - Slave " + (i + 1) + ": 0x0001", slaveMapDetailInfo21);
                slaveMapDetailInfo2.put("MDT SVC Info - Slave " + (i + 1), opdata);
                slaveMapDetail.put("SVC - Slave " + (i + 1) + ":0x01 0x00 0x00 0x00 0x00 0x00", slaveMapDetailInfo2);
                slaveMap.put("Slave " + (i + 1) + ":[Addr. " + slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr() + "]", slaveMapDetail);
                slaveListMap.put("Slave " + slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr(), slaveMap);
            }
        }

        sercos.setSlaves(slaveListMap);
        intiResultEntity.setSercos(sercos);
        return intiResultEntity;
    }


    public ResultEntity initAtcp2Baowen(String type, int connIndex, int idnIndex){
        ResultEntity intiResultEntity = new ResultEntity();
        TelegramFrame telegramFrame = new TelegramFrame();
        telegramFrame.setTelegramLength("1300");
        telegramFrame.setNowTime(System.nanoTime());
        intiResultEntity.setTelegramFrame(telegramFrame);

        Ethernet ethernet = new Ethernet();
        ethernet.setDestAddress("FF:FF:FF:FF:FF:FF");
        ethernet.setSourceAddress(sercosObject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getMacAddress());
        ethernet.setEthernetType("0x88CD");
        intiResultEntity.setEthernet(ethernet);

        Sercos sercos = new Sercos();

        SercosPhase sercosPhase = new SercosPhase();
        SercosPhaseData sercosPhaseData = new SercosPhaseData();
        sercosPhaseData.setCycleCnt("0x00");
        sercosPhaseData.setCommunPhaseSwitch("current CP");
        sercosPhaseData.setCommunPhase("CP2");
        sercosPhase.setSercosPhaseData(sercosPhaseData);
        sercos.setSercosPhase(sercosPhase);
        SercosType sercosType = new SercosType();
        SercosTypeData sercosTypeData = new SercosTypeData();
        sercosTypeData.setMdtOrAt("AT");
        sercosTypeData.setSercosTeleNum("0");
        sercosTypeData.setPsTelegram("P");
        sercosTypeData.setCycleCndEnable("enabled");
        sercosType.setSercosTypeData(sercosTypeData);
        sercos.setSercosType(sercosType);

        List<Slave> slaveList = sercosObject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getSlaveList();
        Map<String, Object> slaveListMap = new HashMap<>();
        Map<String, Object> slaveMap = new HashMap<>();

        for(int i = 0; i < slaveList.size(); i++){
            Map<String, Object> slaveMapDetail = new HashMap<>();
            Map<String, Object> slaveMapDetailInfo1 = new HashMap<>();
            slaveMapDetailInfo1.put("Communcation Warning Interface", "warning");
            slaveMapDetailInfo1.put("Topology handshake", "0");
            slaveMapDetailInfo1.put("Topology Status", "FF diag.");
            slaveMapDetailInfo1.put("Error Connection", "error");
            slaveMapDetailInfo1.put("Slave Vaild", "vaild");
            slaveMapDetailInfo1.put("Error of Device(C1D)", "no error");
            slaveMapDetailInfo1.put("Warning of Device(C2D)", "no waring");
            slaveMapDetailInfo1.put("Procedure Command Change Bit", "no change");
            slaveMapDetailInfo1.put("Sub-Device Level", "PL");
            slaveMapDetail.put("Device Status - Slave " + (i + 1) + ":", slaveMapDetailInfo1);

            Map<String, Object> slaveMapDetailInfo2 = new HashMap<>();
            Map<String, Object> slaveMapDetailInfo21 = new HashMap<>();
            slaveMapDetailInfo21.put("SVC processing", "vaild");
            slaveMapDetailInfo21.put("SVC error", "no error");
//            slaveMapDetailInfo21.put("Busy Bit", "finished");
            slaveMapDetailInfo21.put("Acknowledge Hansshake Bit", "1");
            slaveMapDetailInfo2.put("SVC Status - Slave " + (i + 1) + ": 0x0009", slaveMapDetailInfo21);
            slaveMapDetail.put("SVC - Slave " + (i + 1) + ":0x09 0x00 0x00 0x00 0x00 0x00", slaveMapDetailInfo2);
            slaveMap.put("Slave " + (i + 1) + ":[Addr. " + slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr() + "]", slaveMapDetail);
            slaveListMap.put("Slave " + slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr(), slaveMap);
        }

//        slaveListMap.add(slaveMap);
        sercos.setSlaves(slaveListMap);
        intiResultEntity.setSercos(sercos);
        return intiResultEntity;
    }

    public ResultEntity initMdtcp3Baowen(String type, int connIndex){

        String blankIdnInfo = "0x00000000";
        String idnInfo = "0x000003F6";

        String opdata = "123456";

        ResultEntity intiResultEntity = new ResultEntity();
        TelegramFrame telegramFrame = new TelegramFrame();
        telegramFrame.setTelegramLength("84");
        telegramFrame.setNowTime(System.nanoTime());
        intiResultEntity.setTelegramFrame(telegramFrame);

        Ethernet ethernet = new Ethernet();
        ethernet.setDestAddress("FF:FF:FF:FF:FF:FF");
        ethernet.setSourceAddress(sercosObject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getMacAddress());
        ethernet.setEthernetType("0x88CD");
        intiResultEntity.setEthernet(ethernet);

        Sercos sercos = new Sercos();
        SercosPhase sercosPhase = new SercosPhase();
        SercosPhaseData sercosPhaseData = new SercosPhaseData();
        sercosPhaseData.setCycleCnt("0x00");
        sercosPhaseData.setCommunPhaseSwitch("current CP");
        sercosPhaseData.setCommunPhase("CP3");
        sercosPhase.setSercosPhaseData(sercosPhaseData);
        sercos.setSercosPhase(sercosPhase);
        SercosType sercosType = new SercosType();
        SercosTypeData sercosTypeData = new SercosTypeData();
        sercosTypeData.setMdtOrAt("MDT");
        sercosTypeData.setSercosTeleNum("0");
        sercosTypeData.setPsTelegram("P");
        sercosTypeData.setCycleCndEnable("enabled");
        sercosType.setSercosTypeData(sercosTypeData);
        sercos.setSercosType(sercosType);

        List<Slave> slaveList = sercosObject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getSlaveList();
        Map<String, Object> slaveListMap = new HashMap<>();


        if(StringUtils.isBlank(type)){
            for(int i = 0; i < slaveList.size(); i++){
                Map<String, Object> slaveMap = new HashMap<>();
                Map<String, Object> slaveMapDetail = new HashMap<>();
                Map<String, Object> slaveMapDetailInfo1 = new HashMap<>();
                slaveMapDetailInfo1.put("Idenfication", "no request");
                slaveMapDetailInfo1.put("Topology handshake", "0");
                slaveMapDetailInfo1.put("Topology Control", "FF.");
                slaveMapDetailInfo1.put("Control physical topology", "broken");
                slaveMapDetailInfo1.put("Master Vaild Node", "no vaild");
                slaveMapDetail.put("Device Control - Slave " + (i + 1) + ":0x0000", slaveMapDetailInfo1);

                Map<String, Object> slaveMapConnectionInfo = new HashMap<>();
                Map<String, Object> connectionControlMap = new HashMap<>();
                connectionControlMap.put("Producer Ready", "not vaild");
                connectionControlMap.put("New Data", "0");
                connectionControlMap.put("Data Field Delay", "no delay");
                connectionControlMap.put("Flow Control", "run");
                connectionControlMap.put("Real-time Bit 1", "0");
                connectionControlMap.put("Real-time Bit 2", "0");
                connectionControlMap.put("Counter", "0");
                slaveMapConnectionInfo.put("Connection Control", connectionControlMap);

                Map<String, Object> slaveMapDetailInfo2 = new HashMap<>();
                Map<String, Object> slaveMapDetailInfo21 = new HashMap<>();
                slaveMapDetailInfo21.put("Element", "not active");

                List<Connection> mdtConnectionList = new ArrayList<>();
                List<Connection> connectionList = slaveList.get(i).getConnectionList();
                for(Connection connection : connectionList){
                    if(connection.getTelegram().equals("MDT") && connection.getConnectionConfType().equals("0x8000")){
                        mdtConnectionList.add(connection);
                    }
                }
                //只获取MDT的connection
                Connection connection = null;
                if(connIndex >= mdtConnectionList.size()){
                    connection = mdtConnectionList.get(mdtConnectionList.size() - 1);
                }else{
                    connection = mdtConnectionList.get(connIndex);
                }
                slaveMapDetailInfo21.put("Last transimssion", "in progr.");

                Map<String, Object> connectionDataMap = new HashMap<>();
                List<Idn> idnList = connection.getIdnList();
                for(Idn idn : idnList){
                    connectionDataMap.put(idn.getIdnNumber(), this.getVal(idn.getTeleLen()));
                }
                slaveMapConnectionInfo.put("Connection Data", connectionDataMap);
                slaveMapDetail.put("Connections - Slave " + (i + 1) + ":0x0000", slaveMapConnectionInfo);
                slaveMapDetailInfo21.put("Read or Write", "R");
                slaveMapDetailInfo21.put("Mast Handshake Bit", "0");
                slaveMapDetailInfo2.put("SVC Control - Slave " + (i + 1) + ": 0x0001", slaveMapDetailInfo21);
                slaveMapDetailInfo2.put("MDT SVC Info - Slave " + (i + 1), idnInfo);
                slaveMapDetail.put("SVC - Slave " + (i + 1) + ":0x01 0x00 0x00 0x00 0x00 0x00", slaveMapDetailInfo2);
                slaveMap.put("Slave " + (i + 1) + ":[Addr. " + slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr() + "]", slaveMapDetail);
                slaveListMap.put("Slave " + slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr(), slaveMap);
            }
        }else if("idn".equals(type)){
            for(int i = 0; i < slaveList.size(); i++){
                Map<String, Object> slaveMap = new HashMap<>();
                Map<String, Object> slaveMapDetail = new HashMap<>();
                Map<String, Object> slaveMapDetailInfo1 = new HashMap<>();
                slaveMapDetailInfo1.put("Idenfication", "no request");
                slaveMapDetailInfo1.put("Topology handshake", "0");
                slaveMapDetailInfo1.put("Topology Control", "FF.");
                slaveMapDetailInfo1.put("Control physical topology", "broken");
                slaveMapDetailInfo1.put("Master Vaild Node", "no vaild");
                slaveMapDetail.put("Device Control - Slave " + (i + 1) + ":0x0000", slaveMapDetailInfo1);

                Map<String, Object> slaveMapConnectionInfo = new HashMap<>();
                Map<String, Object> connectionControlMap = new HashMap<>();
                connectionControlMap.put("Producer Ready", "not vaild");
                connectionControlMap.put("New Data", "0");
                connectionControlMap.put("Data Field Delay", "no delay");
                connectionControlMap.put("Flow Control", "run");
                connectionControlMap.put("Real-time Bit 1", "0");
                connectionControlMap.put("Real-time Bit 2", "0");
                connectionControlMap.put("Counter", "0");
                slaveMapConnectionInfo.put("Connection Control", connectionControlMap);

                Map<String, Object> slaveMapDetailInfo2 = new HashMap<>();
                Map<String, Object> slaveMapDetailInfo21 = new HashMap<>();
                slaveMapDetailInfo21.put("Element", "IDN");

                List<Connection> mdtConnectionList = new ArrayList<>();
                List<Connection> connectionList = slaveList.get(i).getConnectionList();
                for(Connection connection : connectionList){
                    if(connection.getTelegram().equals("MDT") && connection.getConnectionConfType().equals("0x8000")){
                        mdtConnectionList.add(connection);
                    }
                }
                //只获取MDT的connection
                Connection connection = null;
                if(connIndex >= mdtConnectionList.size()){
                    connection = mdtConnectionList.get(mdtConnectionList.size() - 1);
                    slaveMapDetailInfo21.put("Last transimssion", "last trans");
                }else{
                    connection = mdtConnectionList.get(connIndex);
                    slaveMapDetailInfo21.put("Last transimssion", "in progr.");
                }

                Map<String, Object> connectionDataMap = new HashMap<>();
                List<Idn> idnList = connection.getIdnList();
                for(Idn idn : idnList){
                    connectionDataMap.put(idn.getIdnNumber(), this.getVal(idn.getTeleLen()));
                }
                slaveMapConnectionInfo.put("Connection Data", connectionDataMap);
                slaveMapDetail.put("Connections - Slave " + (i + 1) + ":0x0000", slaveMapConnectionInfo);

                slaveMapDetailInfo21.put("Read or Write", "W");
                slaveMapDetailInfo21.put("Mast Handshake Bit", "0");
                slaveMapDetailInfo2.put("SVC Control - Slave " + (i + 1) + ": 0x0001", slaveMapDetailInfo21);
                slaveMapDetailInfo2.put("MDT SVC Info - Slave " + (i + 1), idnInfo);
                slaveMapDetail.put("SVC - Slave " + (i + 1) + ":0x01 0x00 0x00 0x00 0x00 0x00", slaveMapDetailInfo2);
                slaveMap.put("Slave " + (i + 1) + ":[Addr. " + slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr() + "]", slaveMapDetail);
                slaveListMap.put("Slave " + slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr(), slaveMap);
            }
        }else if("opdata".equals(type)){

            for(int i = 0; i < slaveList.size(); i++){
                Map<String, Object> slaveMap = new HashMap<>();
                Map<String, Object> slaveMapDetail = new HashMap<>();
                Map<String, Object> slaveMapDetailInfo1 = new HashMap<>();
                slaveMapDetailInfo1.put("Idenfication", "no request");
                slaveMapDetailInfo1.put("Topology handshake", "0");
                slaveMapDetailInfo1.put("Topology Control", "FF.");
                slaveMapDetailInfo1.put("Control physical topology", "broken");
                slaveMapDetailInfo1.put("Master Vaild Node", "no vaild");
                slaveMapDetail.put("Device Control - Slave " + (i + 1) + ":0x0000", slaveMapDetailInfo1);

                Map<String, Object> slaveMapConnectionInfo = new HashMap<>();
                Map<String, Object> connectionControlMap = new HashMap<>();
                connectionControlMap.put("Producer Ready", "not vaild");
                connectionControlMap.put("New Data", "0");
                connectionControlMap.put("Data Field Delay", "no delay");
                connectionControlMap.put("Flow Control", "run");
                connectionControlMap.put("Real-time Bit 1", "0");
                connectionControlMap.put("Real-time Bit 2", "0");
                connectionControlMap.put("Counter", "0");
                slaveMapConnectionInfo.put("Connection Control", connectionControlMap);

                Map<String, Object> slaveMapDetailInfo2 = new HashMap<>();
                Map<String, Object> slaveMapDetailInfo21 = new HashMap<>();
                slaveMapDetailInfo21.put("Element", "op.data");

                List<Connection> mdtConnectionList = new ArrayList<>();
                List<Connection> connectionList = slaveList.get(i).getConnectionList();
                for(Connection connection : connectionList){
                    if(connection.getTelegram().equals("MDT") && connection.getConnectionConfType().equals("0x8000")){
                        mdtConnectionList.add(connection);
                    }
                }
                //只获取MDT的connection
                Connection connection = null;
                if(connIndex >= mdtConnectionList.size()){
                    connection = mdtConnectionList.get(mdtConnectionList.size() - 1);
                    slaveMapDetailInfo21.put("Last transimssion", "last trans");
                }else{
                    connection = mdtConnectionList.get(connIndex);
                    slaveMapDetailInfo21.put("Last transimssion", "in progr.");
                }

                Map<String, Object> connectionDataMap = new HashMap<>();
                List<Idn> idnList = connection.getIdnList();
                for(Idn idn : idnList){
                    connectionDataMap.put(idn.getIdnNumber(), this.getVal(idn.getTeleLen()));
                }
                slaveMapConnectionInfo.put("Connection Data", connectionDataMap);
                slaveMapDetail.put("Connections - Slave " + (i + 1) + ":0x0000", slaveMapConnectionInfo);

                slaveMapDetailInfo21.put("Read or Write", "W");
                slaveMapDetailInfo21.put("Mast Handshake Bit", "0");
                slaveMapDetailInfo2.put("SVC Control - Slave " + (i + 1) + ": 0x0001", slaveMapDetailInfo21);
                slaveMapDetailInfo2.put("MDT SVC Info - Slave " + (i + 1), idnInfo);
                slaveMapDetail.put("SVC - Slave " + (i + 1) + ":0x01 0x00 0x00 0x00 0x00 0x00", slaveMapDetailInfo2);
                slaveMap.put("Slave " + (i + 1) + ":[Addr. " + slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr() + "]", slaveMapDetail);
                slaveListMap.put("Slave " + slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr(), slaveMap);
            }
            /*for(int i = 0; i < slaveList.size(); i++){
                Map<String, Object> slaveMap = new HashMap<>();
                Map<String, Object> slaveMapDetail = new HashMap<>();
                Map<String, Object> slaveMapDetailInfo1 = new HashMap<>();
                slaveMapDetailInfo1.put("Idenfication", "no request");
                slaveMapDetailInfo1.put("Topology handshake", "0");
                slaveMapDetailInfo1.put("Topology Control", "FF.");
                slaveMapDetailInfo1.put("Control physical topology", "broken");
                slaveMapDetailInfo1.put("Master Vaild Node", "no vaild");
                slaveMapDetail.put("Device Control - Slave " + (i + 1) + ":0x0000", slaveMapDetailInfo1);

                Map<String, Object> slaveMapDetailInfo2 = new HashMap<>();
                Map<String, Object> slaveMapDetailInfo21 = new HashMap<>();
                slaveMapDetailInfo21.put("Element", "op.data");
                slaveMapDetailInfo21.put("Last transimssion", "last trans");
                slaveMapDetailInfo21.put("Read or Write", "W");
                slaveMapDetailInfo21.put("Mast Handshake Bit", "1");
                slaveMapDetailInfo2.put("SVC Control - Slave " + (i + 1) + ": 0x0001", slaveMapDetailInfo21);
                slaveMapDetailInfo2.put("MDT SVC Info - Slave " + (i + 1), opdata);
                slaveMapDetail.put("SVC - Slave " + (i + 1) + ":0x01 0x00 0x00 0x00 0x00 0x00", slaveMapDetailInfo2);
                slaveMap.put("Slave " + (i + 1) + ":[Addr. " + slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr() + "]", slaveMapDetail);
                slaveListMap.put("Slave " + slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr(), slaveMap);
            }*/
        }

        sercos.setSlaves(slaveListMap);
        intiResultEntity.setSercos(sercos);
        return intiResultEntity;
    }

    public ResultEntity initAtcp3Baowen(String type, int connIndex){

        String blankIdnInfo = "0x00000000";
        String idnInfo = "0x000003F6";

        String opdata = "123456";

        ResultEntity intiResultEntity = new ResultEntity();
        TelegramFrame telegramFrame = new TelegramFrame();
        telegramFrame.setTelegramLength("92");
        telegramFrame.setNowTime(System.nanoTime());
        intiResultEntity.setTelegramFrame(telegramFrame);

        Ethernet ethernet = new Ethernet();
        ethernet.setDestAddress("FF:FF:FF:FF:FF:FF");
        ethernet.setSourceAddress(sercosObject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getMacAddress());
        ethernet.setEthernetType("0x88CD");
        intiResultEntity.setEthernet(ethernet);

        Sercos sercos = new Sercos();
        SercosPhase sercosPhase = new SercosPhase();
        SercosPhaseData sercosPhaseData = new SercosPhaseData();
        sercosPhaseData.setCycleCnt("0x00");
        sercosPhaseData.setCommunPhaseSwitch("current CP");
        sercosPhaseData.setCommunPhase("CP3");
        sercosPhase.setSercosPhaseData(sercosPhaseData);
        sercos.setSercosPhase(sercosPhase);
        SercosType sercosType = new SercosType();
        SercosTypeData sercosTypeData = new SercosTypeData();
        sercosTypeData.setMdtOrAt("AT");
        sercosTypeData.setSercosTeleNum("0");
        sercosTypeData.setPsTelegram("P");
        sercosTypeData.setCycleCndEnable("enabled");
        sercosType.setSercosTypeData(sercosTypeData);
        sercos.setSercosType(sercosType);

        List<Slave> slaveList = sercosObject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getSlaveList();
        Map<String, Object> slaveListMap = new HashMap<>();


        if(StringUtils.isBlank(type)){
            for(int i = 0; i < slaveList.size(); i++){
                Map<String, Object> slaveMap = new HashMap<>();
                Map<String, Object> slaveMapDetail = new HashMap<>();
                Map<String, Object> slaveMapDetailInfo1 = new HashMap<>();
                slaveMapDetailInfo1.put("Idenfication", "no request");
                slaveMapDetailInfo1.put("Topology handshake", "0");
                slaveMapDetailInfo1.put("Topology Control", "FF.");
                slaveMapDetailInfo1.put("Control physical topology", "broken");
                slaveMapDetailInfo1.put("Master Vaild Node", "no vaild");
                slaveMapDetail.put("Device Control - Slave " + (i + 1) + ":0x0000", slaveMapDetailInfo1);

                Map<String, Object> slaveMapConnectionInfo = new HashMap<>();
                Map<String, Object> connectionControlMap = new HashMap<>();
                connectionControlMap.put("Producer Ready", "not vaild");
                connectionControlMap.put("New Data", "0");
                connectionControlMap.put("Data Field Delay", "no delay");
                connectionControlMap.put("Flow Control", "run");
                connectionControlMap.put("Real-time Bit 1", "0");
                connectionControlMap.put("Real-time Bit 2", "0");
                connectionControlMap.put("Counter", "0");
                slaveMapConnectionInfo.put("Connection Control", connectionControlMap);

                Map<String, Object> slaveMapDetailInfo2 = new HashMap<>();
                Map<String, Object> slaveMapDetailInfo21 = new HashMap<>();
                slaveMapDetailInfo21.put("Element", "not active");

                //只获取AT的connection
                List<Connection> atConnectionList = new ArrayList<>();
                List<Connection> connectionList = slaveList.get(i).getConnectionList();
                for(Connection connection : connectionList){
                    if(connection.getTelegram().equals("AT") && connection.getConnectionConfType().equals("0x8000")){
                        atConnectionList.add(connection);
                    }
                }
                //只获取MDT的connection
                Connection connection = null;
                if(connIndex >= atConnectionList.size()){
                    connection = atConnectionList.get(atConnectionList.size() - 1);
                    slaveMapDetailInfo21.put("Last transimssion", "last trans");
                }else{
                    connection = atConnectionList.get(connIndex);
                    slaveMapDetailInfo21.put("Last transimssion", "in progr.");
                }

                Map<String, Object> connectionDataMap = new HashMap<>();
                List<Idn> idnList = connection.getIdnList();
                for(Idn idn : idnList){
                    connectionDataMap.put(idn.getIdnNumber(), this.getVal(idn.getTeleLen()));
                }
                slaveMapConnectionInfo.put("Connection Data", connectionDataMap);
                slaveMapDetail.put("Connections - Slave " + (i + 1) + ":0x0000", slaveMapConnectionInfo);

                slaveMapDetailInfo21.put("Read or Write", "R");
                slaveMapDetailInfo21.put("Mast Handshake Bit", "0");
                slaveMapDetailInfo2.put("SVC Control - Slave " + (i + 1) + ": 0x0001", slaveMapDetailInfo21);
                slaveMapDetailInfo2.put("MDT SVC Info - Slave " + (i + 1), idnInfo);
                slaveMapDetail.put("SVC - Slave " + (i + 1) + ":0x01 0x00 0x00 0x00 0x00 0x00", slaveMapDetailInfo2);
                slaveMap.put("Slave " + (i + 1) + ":[Addr. " + slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr() + "]", slaveMapDetail);
                slaveListMap.put("Slave " + slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr(), slaveMap);
            }
            /*for(int i = 0; i < slaveList.size(); i++){
                Map<String, Object> slaveMap = new HashMap<>();
                Map<String, Object> slaveMapDetail = new HashMap<>();
                Map<String, Object> slaveMapDetailInfo1 = new HashMap<>();
                slaveMapDetailInfo1.put("Idenfication", "no request");
                slaveMapDetailInfo1.put("Topology handshake", "0");
                slaveMapDetailInfo1.put("Topology Control", "FF.");
                slaveMapDetailInfo1.put("Control physical topology", "broken");
                slaveMapDetailInfo1.put("Master Vaild Node", "no vaild");
                slaveMapDetail.put("Device Control - Slave " + (i + 1) + ":0x0000", slaveMapDetailInfo1);

                Map<String, Object> slaveMapDetailInfo2 = new HashMap<>();
                Map<String, Object> slaveMapDetailInfo21 = new HashMap<>();
                slaveMapDetailInfo21.put("Element", "no active");
                slaveMapDetailInfo21.put("Last transimssion", "in progr.");
                slaveMapDetailInfo21.put("Read or Write", "R");
                slaveMapDetailInfo21.put("Mast Handshake Bit", "1");
                slaveMapDetailInfo2.put("SVC Control - Slave " + (i + 1) + ": 0x0001", slaveMapDetailInfo21);
                slaveMapDetailInfo2.put("MDT SVC Info - Slave " + (i + 1), blankIdnInfo);
                slaveMapDetail.put("SVC - Slave " + (i + 1) + ":0x01 0x00 0x00 0x00 0x00 0x00", slaveMapDetailInfo2);
                slaveMap.put("Slave " + (i + 1) + ":[Addr. " + slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr() + "]", slaveMapDetail);
                slaveListMap.put("Slave " + slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr(), slaveMap);
            }*/
        }else if("idn".equals(type)){
            for(int i = 0; i < slaveList.size(); i++){
                Map<String, Object> slaveMap = new HashMap<>();
                Map<String, Object> slaveMapDetail = new HashMap<>();
                Map<String, Object> slaveMapDetailInfo1 = new HashMap<>();
                slaveMapDetailInfo1.put("Idenfication", "no request");
                slaveMapDetailInfo1.put("Topology handshake", "0");
                slaveMapDetailInfo1.put("Topology Control", "FF.");
                slaveMapDetailInfo1.put("Control physical topology", "broken");
                slaveMapDetailInfo1.put("Master Vaild Node", "no vaild");
                slaveMapDetail.put("Device Control - Slave " + (i + 1) + ":0x0000", slaveMapDetailInfo1);

                Map<String, Object> slaveMapConnectionInfo = new HashMap<>();
                Map<String, Object> connectionControlMap = new HashMap<>();
                connectionControlMap.put("Producer Ready", "not vaild");
                connectionControlMap.put("New Data", "0");
                connectionControlMap.put("Data Field Delay", "no delay");
                connectionControlMap.put("Flow Control", "run");
                connectionControlMap.put("Real-time Bit 1", "0");
                connectionControlMap.put("Real-time Bit 2", "0");
                connectionControlMap.put("Counter", "0");
                slaveMapConnectionInfo.put("Connection Control", connectionControlMap);

                Map<String, Object> slaveMapDetailInfo2 = new HashMap<>();
                Map<String, Object> slaveMapDetailInfo21 = new HashMap<>();
                slaveMapDetailInfo21.put("Element", "IDN");

                //只获取AT的connection
                List<Connection> atConnectionList = new ArrayList<>();
                List<Connection> connectionList = slaveList.get(i).getConnectionList();
                for(Connection connection : connectionList){
                    if(connection.getTelegram().equals("AT") && connection.getConnectionConfType().equals("0x8000")){
                        atConnectionList.add(connection);
                    }
                }
                //只获取MDT的connection
                Connection connection = null;
                if(connIndex >= atConnectionList.size()){
                    connection = atConnectionList.get(atConnectionList.size() - 1);
                    slaveMapDetailInfo21.put("Last transimssion", "last trans");
                }else{
                    connection = atConnectionList.get(connIndex);
                    slaveMapDetailInfo21.put("Last transimssion", "in progr.");
                }

                Map<String, Object> connectionDataMap = new HashMap<>();
                List<Idn> idnList = connection.getIdnList();
                for(Idn idn : idnList){
                    connectionDataMap.put(idn.getIdnNumber(), this.getVal(idn.getTeleLen()));
                }
                slaveMapConnectionInfo.put("Connection Data", connectionDataMap);
                slaveMapDetail.put("Connections - Slave " + (i + 1) + ":0x0000", slaveMapConnectionInfo);

                slaveMapDetailInfo21.put("Read or Write", "W");
                slaveMapDetailInfo21.put("Mast Handshake Bit", "0");
                slaveMapDetailInfo2.put("SVC Control - Slave " + (i + 1) + ": 0x0001", slaveMapDetailInfo21);
                slaveMapDetailInfo2.put("MDT SVC Info - Slave " + (i + 1), idnInfo);
                slaveMapDetail.put("SVC - Slave " + (i + 1) + ":0x01 0x00 0x00 0x00 0x00 0x00", slaveMapDetailInfo2);
                slaveMap.put("Slave " + (i + 1) + ":[Addr. " + slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr() + "]", slaveMapDetail);
                slaveListMap.put("Slave " + slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr(), slaveMap);
            }
        }else if("opdata".equals(type)){

            for(int i = 0; i < slaveList.size(); i++){
                Map<String, Object> slaveMap = new HashMap<>();
                Map<String, Object> slaveMapDetail = new HashMap<>();
                Map<String, Object> slaveMapDetailInfo1 = new HashMap<>();
                slaveMapDetailInfo1.put("Idenfication", "no request");
                slaveMapDetailInfo1.put("Topology handshake", "0");
                slaveMapDetailInfo1.put("Topology Control", "FF.");
                slaveMapDetailInfo1.put("Control physical topology", "broken");
                slaveMapDetailInfo1.put("Master Vaild Node", "no vaild");
                slaveMapDetail.put("Device Control - Slave " + (i + 1) + ":0x0000", slaveMapDetailInfo1);

                Map<String, Object> slaveMapConnectionInfo = new HashMap<>();
                Map<String, Object> connectionControlMap = new HashMap<>();
                connectionControlMap.put("Producer Ready", "not vaild");
                connectionControlMap.put("New Data", "0");
                connectionControlMap.put("Data Field Delay", "no delay");
                connectionControlMap.put("Flow Control", "run");
                connectionControlMap.put("Real-time Bit 1", "0");
                connectionControlMap.put("Real-time Bit 2", "0");
                connectionControlMap.put("Counter", "0");
                slaveMapConnectionInfo.put("Connection Control", connectionControlMap);

                Map<String, Object> slaveMapDetailInfo2 = new HashMap<>();
                Map<String, Object> slaveMapDetailInfo21 = new HashMap<>();
                slaveMapDetailInfo21.put("Element", "op.data");

                //只获取AT的connection
                List<Connection> atConnectionList = new ArrayList<>();
                List<Connection> connectionList = slaveList.get(i).getConnectionList();
                for(Connection connection : connectionList){
                    if(connection.getTelegram().equals("AT") && connection.getConnectionConfType().equals("0x8000")){
                        atConnectionList.add(connection);
                    }
                }
                //只获取MDT的connection
                Connection connection = null;
                if(connIndex >= atConnectionList.size()){
                    connection = atConnectionList.get(atConnectionList.size() - 1);
                    slaveMapDetailInfo21.put("Last transimssion", "last trans");
                }else{
                    connection = atConnectionList.get(connIndex);
                    slaveMapDetailInfo21.put("Last transimssion", "in progr.");
                }

                Map<String, Object> connectionDataMap = new HashMap<>();
                List<Idn> idnList = connection.getIdnList();
                for(Idn idn : idnList){
                    connectionDataMap.put(idn.getIdnNumber(), this.getVal(idn.getTeleLen()));
                }
                slaveMapConnectionInfo.put("Connection Data", connectionDataMap);
                slaveMapDetail.put("Connections - Slave " + (i + 1) + ":0x0000", slaveMapConnectionInfo);

                slaveMapDetailInfo21.put("Read or Write", "W");
                slaveMapDetailInfo21.put("Mast Handshake Bit", "0");
                slaveMapDetailInfo2.put("SVC Control - Slave " + (i + 1) + ": 0x0001", slaveMapDetailInfo21);
                slaveMapDetailInfo2.put("MDT SVC Info - Slave " + (i + 1), idnInfo);
                slaveMapDetail.put("SVC - Slave " + (i + 1) + ":0x01 0x00 0x00 0x00 0x00 0x00", slaveMapDetailInfo2);
                slaveMap.put("Slave " + (i + 1) + ":[Addr. " + slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr() + "]", slaveMapDetail);
                slaveListMap.put("Slave " + slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr(), slaveMap);
            }


            /*for(int i = 0; i < slaveList.size(); i++){
                Map<String, Object> slaveMap = new HashMap<>();
                Map<String, Object> slaveMapDetail = new HashMap<>();
                Map<String, Object> slaveMapDetailInfo1 = new HashMap<>();
                slaveMapDetailInfo1.put("Idenfication", "no request");
                slaveMapDetailInfo1.put("Topology handshake", "0");
                slaveMapDetailInfo1.put("Topology Control", "FF.");
                slaveMapDetailInfo1.put("Control physical topology", "broken");
                slaveMapDetailInfo1.put("Master Vaild Node", "no vaild");
                slaveMapDetail.put("Device Control - Slave " + (i + 1) + ":0x0000", slaveMapDetailInfo1);

                Map<String, Object> slaveMapDetailInfo2 = new HashMap<>();
                Map<String, Object> slaveMapDetailInfo21 = new HashMap<>();
                slaveMapDetailInfo21.put("Element", "op.data");
                slaveMapDetailInfo21.put("Last transimssion", "last trans");
                slaveMapDetailInfo21.put("Read or Write", "W");
                slaveMapDetailInfo21.put("Mast Handshake Bit", "1");
                slaveMapDetailInfo2.put("SVC Control - Slave " + (i + 1) + ": 0x0001", slaveMapDetailInfo21);
                slaveMapDetailInfo2.put("MDT SVC Info - Slave " + (i + 1), opdata);
                slaveMapDetail.put("SVC - Slave " + (i + 1) + ":0x01 0x00 0x00 0x00 0x00 0x00", slaveMapDetailInfo2);
                slaveMap.put("Slave " + (i + 1) + ":[Addr. " + slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr() + "]", slaveMapDetail);
                slaveListMap.put("Slave " + slaveList.get(i).getMacAddress() + " " + slaveList.get(i).getDrvAdr(), slaveMap);
            }*/
        }

        sercos.setSlaves(slaveListMap);
        intiResultEntity.setSercos(sercos);
        return intiResultEntity;
    }


    private String getVal(int length){
        String str = "0x";
        for (int i = 0; i < length; i++){
            str += "0";
        }
        return str;
    }



    /**
     * 主站发送mdt报文给从站
     */
    public void sendMdt(ResultEntity mdtResultEntity, List<SlaveProcess> slaveProcessList){
        /*for(SlaveProcess slaveProcess : slaveProcessList){
            slaveProcess.setResultMdtEntity(mdtResultEntity);
        }*/
    }

    /**
     * 主站发送at报文给从站
     */
    public void sendAt(ResultEntity atResultEntity, List<SlaveProcess> slaveProcessList){
        /*for(SlaveProcess slaveProcess : slaveProcessList){
            slaveProcess.setResultAtEntity(atResultEntity);
        }*/
    }
}
