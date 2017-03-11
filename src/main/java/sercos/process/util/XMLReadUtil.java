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
                    while(idnIterator.hasNext()){
                        //idnIterator.next().getName()
                        System.out.println("--" + idnIterator.next().getData());
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
}
