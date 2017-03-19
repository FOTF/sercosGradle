package sercos.process.util;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import sercos.process.entity.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 宗祥 on 2017/3/9.
 */
public class XMLReadUtil {

    public static void main(String[] args) {
        XMLReadUtil.readXml("F:\\wangkai\\smc3#1.xml");

    }

    public static SercosObject readXml(String xmlPath){
        SAXReader reader = new SAXReader();
        reader.setEncoding("gbk");
        try {
            Document document = reader.read(new File(xmlPath));
            Element node = document.getRootElement();
            //遍历所有的元素节点
            return conventNodes(node);
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 遍历当前节点元素下面的所有(元素的)子节点
     * @param node
     */
    public static SercosObject conventNodes(Element node) {
        System.out.println("当前节点的名称：：" + node.getName());
        // 获取当前节点的所有属性节点
        SercosObject sercosObject = new SercosObject();
        sercosObject.setProfileName(node.element("ProfileName").getText());
        ManagerObject managerObject = new ManagerObject();

        Element initialisationFileElement = node.element("InitialisationFile");

        Element managerObjectElement = initialisationFileElement.element("ManagerObject");
        managerObject.setFileNo(managerObjectElement.elementText("FileNo"));
        managerObject.setFileLocation(managerObjectElement.elementText("FileLocation"));

        InitializationList initializationList = new InitializationList();
        Element initializationListElement = managerObjectElement.element("InitializationList");
        Project project = new Project();
        Element projectElement = initializationListElement.element("Project");

        project.setServiceJobs(Integer.parseInt(projectElement.elementText("ServiceJobs")));
        project.setFifoSize(Integer.parseInt(projectElement.elementText("FifoSize")));
        project.setAtEnd2InterruptDistance(Integer.parseInt(projectElement.elementText("AtEnd2InterruptDistance")));
        project.setTelegramMethod(Integer.parseInt(projectElement.elementText("TelegramMethod")));
        project.setMinStartTelBlock2(Integer.parseInt(projectElement.elementText("MinStartTelBlock2")));
        List<Ring> ringList = new ArrayList<>();

        Iterator<Element> ringIterators = projectElement.element("RingList").elementIterator();
        Ring ring = null;
        while (ringIterators.hasNext()){
            ring = new Ring();
            Element ringElement = ringIterators.next();
            ring.setSercosCardNo(Integer.parseInt(ringElement.elementText("SERCOSCardNo")));
            ring.setSercosChannel(Integer.parseInt(ringElement.elementText("SERCONChannel")));
            ring.setMasterRing(Integer.parseInt(ringElement.elementText("MasterRing")));
            ring.setServiceChannelTimeOut(Integer.parseInt(ringElement.elementText("ServiceChannelTimeOut")));
            ring.setCycleTimeP0P2(Integer.parseInt(ringElement.elementText("CycleTimeP0_P2")));
            ring.setNcCycleTime(Integer.parseInt(ringElement.elementText("NCCycleTime")));
            ring.setCycleTimeP0P2(Integer.parseInt(ringElement.elementText("SERCycleTime")));
            ring.setCycClkActive(Integer.parseInt(ringElement.elementText("CycClkActive")));
            ring.setConClkPolarity(Integer.parseInt(ringElement.elementText("CycClkPolarity")));
            ring.setConClkEnable(Integer.parseInt(ringElement.elementText("CycClkEnable")));
            ring.setCycClkStartDelay(Integer.parseInt(ringElement.elementText("CycClkStartDelay")));
            ring.setConClkPolarity(Integer.parseInt(ringElement.elementText("CONClkPolarity")));
            ring.setConClkEnable(Integer.parseInt(ringElement.elementText("CONClkEnable")));
            ring.setConClkEnableDriver(Integer.parseInt(ringElement.elementText("CONClkEnableDriver")));
            ring.setConClkStart(Integer.parseInt(ringElement.elementText("CONClkStart")));
            ring.setConClkEnd(Integer.parseInt(ringElement.elementText("CONClkEnd")));
            ring.setIpChannelTime(Integer.parseInt(ringElement.elementText("IPChannelTime")));
            ring.setIpChannelMaxLengthP34(Integer.parseInt(ringElement.elementText("IPChannelMaxLengthP34")));
            ring.setIpCBandwidth(Integer.parseInt(ringElement.elementText("IPCBandwidth")));
            ring.setHotPlug(Integer.parseInt(ringElement.elementText("HotPlug")));
            ring.setDmaPCImode(Integer.parseInt(ringElement.elementText("DMA_PCImode")));
            ring.setCommunicationVersion(Integer.parseInt(ringElement.elementText("CommunicationVersion")));
            ring.setMacAddress(ringElement.elementText("MAC_Address"));
            ring.setMaxTelLoss(Integer.parseInt(ringElement.elementText("MaxTelLoss")));
            ring.setJitterMaster(Integer.parseInt(ringElement.elementText("JitterMaster")));
            ring.setJitterStartAT(Integer.parseInt(ringElement.elementText("JitterStartAT")));
            ring.setSyncJitterS01023(Integer.parseInt(ringElement.elementText("SyncJitter_S01023")));
            ring.setDelayMaster(Integer.parseInt(ringElement.elementText("DelayMaster")));
            ring.setDelaySlave(Integer.parseInt(ringElement.elementText("DelaySlave")));
            ring.setDelayComponent(Integer.parseInt(ringElement.elementText("DelayComponent")));
            List<Slave> slaveList = new ArrayList<>();
            Iterator<Element> slaveInterator = ringElement.element("SlaveList").elementIterator("Slave");
            Slave slave = null;
            while(slaveInterator.hasNext()){
                slave = new Slave();
                Element slaveElement = slaveInterator.next();
                slave.setLogAxisNr(Integer.parseInt(slaveElement.elementText("LogAxisNr")));
                slave.setDrvAdr(Integer.parseInt(slaveElement.elementText("DrvAdr")));
                slave.setMaxNbrTelErrS1003(Integer.parseInt(slaveElement.elementText("MaxNbrTelErr_S1003")));
                slave.setMacAddress(slaveElement.elementText("MAC_Address"));
                List<Connection> connectionList = new ArrayList<>();
                Iterator<Element> connectionIterator = slaveElement.element("ConnectionList").elementIterator();
                Connection connection = null;
                while (connectionIterator.hasNext()){
                    connection = new Connection();
                    Element connectionElement = connectionIterator.next();
                    connection.setApplicationID(Integer.parseInt(connectionElement.elementText("ApplicationID")));
                    connection.setConnectionName(connectionElement.elementText("ConnectionName"));
                    connection.setConnectionClass(Integer.parseInt(connectionElement.elementText("ConnectionClass")));
                    connection.setConnectionConfType(connectionElement.elementText("ConnectionConfType"));
                    //这个代表是无用的
                    if("0xC000".equals(connection.getConnectionConfType())){
                        continue;
                    }
                    connection.setConnectionID(Integer.parseInt(connectionElement.elementText("ConnectionID")));
                    connection.setProducerCycleTime(Integer.parseInt(connectionElement.elementText("ProducerCycleTime")));
                    connection.setMaxDataLos(Integer.parseInt(connectionElement.elementText("MaxDataLos")));
                    connection.setTeleType(Integer.parseInt(connectionElement.elementText("TeleType")));
                    connection.setStatus(connectionElement.attributeValue("status"));
                    connection.setTelegram(connectionElement.attributeValue("Telegram"));
                    //如果是AT类型，则获取属性DeviceStatus
                    if("AT".equals(connection.getTelegram())){
                        if(!"SLAVE_TX".equals(connection.getStatus())){
                            connection.setDeviceStatus(Integer.parseInt(connectionElement.attributeValue("DeviceStatus")));
                        }
                    }else if("MDT".equals(connection.getTelegram())){
                        if(!"SLAVE_RX".equals(connection.getStatus())){
                            connection.setDeviceControl(Integer.parseInt(connectionElement.attributeValue("DeviceControl")));
                        }
                    }
                    List<Idn> idns = new ArrayList<>();
                    Iterator<Element> idnIterator = connectionElement.element("IdnList").elementIterator();
                    Idn idn = null;
                    while(idnIterator.hasNext()){
                        idn = new Idn();
                        idn.setIdnNumber(idnIterator.next().getData().toString());
                        idn.setTeleLen(Integer.parseInt(idnIterator.next().getData().toString()));
                        idns.add(idn);
                        System.out.println(idn.toString());
                    }
                    connection.setIdnList(idns);
                    connectionList.add(connection);
                }
                slave.setConnectionList(connectionList);
                slaveList.add(slave);
            }
            ring.setSlaveList(slaveList);
            ringList.add(ring);
        }


        project.setRingList(ringList);
        initializationList.setProject(project);
        managerObject.setInitializationList(initializationList);
        sercosObject.setManagerObject(managerObject);
        System.out.println(sercosObject);
        return sercosObject;
    }

    /*public static SercosObject SetOffset(SercosObject sercosobject)
    {
        sercosobject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getSlaveList();
        //计算每个从站MDT中数据的长度和AT中数据的长度Length = Device字节数(2byte)+Connection字节数之和
        int num1 = 0;//当前从站之前的从站的（设备控制和Telegram==MDT的所有连接的Connection字节数之和）
        int num2 = 0;//当前从站之前的从站的（设备状态和Telegram==AT的所有连接的Connection字节数之和）
        for (int i = 0; i < sercosobject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getSlaveList().size(); i++)
        {
            sercosobject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getSlaveList().get(i).setSvcOffsetInATofCP1CP2(6 + 6 * i);
            sercosobject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getSlaveList().get(i).setSvcOffsetInMDTofCP1CP2(6 + 6 * i);
            sercosobject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getSlaveList().get(i).setSvcOffsetInATofCP3CP4(8 + 6 * i);
            sercosobject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getSlaveList().get(i).setSvcOffsetInMDTofCP3CP4(8 + 6 * i);
            sercosobject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getSlaveList().get(i).setDeviceControlOffsetofCP1CP2(6 * 128 + 4 * i);
            sercosobject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getSlaveList().get(i).setDeviceStatusOffsetofCP1CP2(6 * 128 + 4 * i);
            sercosobject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getSlaveList().get(i).setDeviceControlOffsetofCP3CP4(8 + 6 * sercosobject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getSlaveList().size() + num1);
            sercosobject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getSlaveList().get(i).setDeviceStatusOffsetofCP3CP4(8 + 6 * sercosobject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getSlaveList().size() + num2);

            num1 = num1 + 2;//加当前从站的设备控制字节数
            num2 = num2 + 2;//加当前从站的设备状态字节数
            int num4 = 2;
            for (int j = 0; j < sercosobject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getSlaveList().get(i).getConnectionList().size(); j++)
            {
                int num5 = 2;

                if (sercosobject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getSlaveList().get(i).getConnectionList().get(j).getTelegram().equals("MDT") && sercosobject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getSlaveList().get(i).getConnectionList().get(j).getConnectionConfType().equals("0x8000"))
                {
                    int connectionlength = 2;//加当前连接的控制字字节数
                    sercosobject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getSlaveList().get(i).getConnectionList().get(j).setConnectionOffset(8 + 6 * sercosobject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getSlaveList().size() + num1);
                    num1 = num1 + 2;//加当前连接的控制字字节数
                    for (int k = 0; k < sercosobject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getSlaveList().get(i).getConnectionList().get(j).getIdnList().Count; k++)
                    {
                        sercosobject.getManagerObject().getInitializationList().getProject().getRingList()[0].getSlaveList()[i].getConnectionList()[j].getIdnList()[k].setIdnOffset(8 + 6 * sercosobject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getSlaveList().size() + num1);
                        connectionlength = connectionlength + sercosobject.getManagerObject().getInitializationList().getProject().getRingList()[0].getSlaveList()[i].getConnectionList()[j].getIdnList()[k].getTeleLen();//加当前IDN的字节数
                        num1 = num1 + sercosobject.getManagerObject().getInitializationList().getProject().getRingList()[0].getSlaveList()[i].getConnectionList()[j].getIdnList()[k].getTeleLen();//加当前IDN的字节数
                    }
                    sercosobject.getManagerObject().getInitializationList().getProject().getRingList()[0].getSlaveList()[i].getConnectionList()[j].setLength(connectionlength);
                }
                if (sercosobject.getManagerObject().getInitializationList().getProject().getRingList()[0].getSlaveList()[i].getConnectionList()[j].getTelegram() == "AT" && sercosobject.getManagerObject().getInitializationList().getProject().getRingList()[0].getSlaveList()[i].getConnectionList()[j].getConnectionConfType() == "0x8000")
                {
                    //int num4 = 0;
                    sercosobject.getManagerObject().getInitializationList().getProject().getRingList()[0].getSlaveList()[i].getConnectionList()[j].setConnectionOffset(8 + 6 * sercosobject.getManagerObject().getInitializationList().getProject().getRingList()[0].getSlaveList().Count + num2);
                    num2 = num2 + 2;//加当前连接的控制字字节数
                    int connectionlength = 2;//加当前连接的控制字字节数
                    for (int k = 0; k < sercosobject.getManagerObject().getInitializationList().getProject().getRingList()[0].getSlaveList()[i].getConnectionList()[j].getIdnList().Count; k++)
                    {
                        sercosobject.getManagerObject().getInitializationList().getProject().getRingList()[0].getSlaveList()[i].getConnectionList()[j].getIdnList()[k].setIdnOffset(8 + 6 * sercosobject.getManagerObject().getInitializationList().getProject().getRingList()[0].getSlaveList().Count + num2);
                        connectionlength = connectionlength + sercosobject.getManagerObject().getInitializationList().getProject().getRingList()[0].getSlaveList()[i].getConnectionList()[j].getIdnList()[k].getTeleLen();//加当前IDN的字节数

                        num2 = num2 + sercosobject.getManagerObject().getInitializationList().getProject().getRingList()[0].getSlaveList()[i].getConnectionList()[j].getIdnList()[k].getTeleLen();
                        // num5 = num5 + sercosobject.getManagerObject().getInitializationList().getProject().getRingList()[0].getSlaveList()[i].getConnectionList()[j].getIdnList()[k].getTeleLen();
                        // num4 = num4 + sercosobject.getManagerObject().getInitializationList().getProject().getRingList()[0].getSlaveList()[i].getConnectionList()[j].getIdnList()[k].getTeleLen();
                    }
                    sercosobject.getManagerObject().getInitializationList().getProject().getRingList()[0].getSlaveList()[i].getConnectionList()[j].setLength(connectionlength);

                }
            }
        }
        return sercosobject;
    }*/
}
