package sercos.gui.event;


import sercos.gui.AnalysisResultModel;
import sercos.gui.AnalysisResultTable;
import sercos.gui.DisplayPanel;
import sercos.process.deal.DealProcess;
import sercos.process.entity.ResultEntity;
import sercos.process.entity.SercosObject;
import sercos.process.util.ReadDataFileUtil;
import sercos.process.util.XMLReadUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 宗祥 on 2017/3/1.
 */
public class EventUtils {

    /**
     * 仿真按钮事件监听，选择文件
     */
    public static void listenerSimulationBtn(JButton simulationBtn, DisplayPanel displayPanel, JTable resultTable){
        simulationBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser("F:\\wangkai");
                int i = jFileChooser.showOpenDialog(null);
                if(i == JFileChooser.APPROVE_OPTION){
                    String path = jFileChooser.getSelectedFile().getAbsolutePath();
                    SercosObject sercosObject = XMLReadUtil.readXml(path);
                    displayPanel.setSlaveList(sercosObject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getSlaveList());
                    displayPanel.draw();
                    //开始发送报文
                    DealProcess dealProcess = new DealProcess(sercosObject, resultTable);
                    dealProcess.init();
                    new Thread(dealProcess).start();
//                    dealProcess.deal(resultTable);
                }
            }
        });
    }

    /**
     * 分析按钮事件监听，选择文件
     */
    public static void listenerAnalysisBtn(JButton analysisBtn, JTable resultTable){
        analysisBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser jFileChooser = new JFileChooser();
                int i = jFileChooser.showOpenDialog(null);
                if(i == JFileChooser.APPROVE_OPTION){
                    String path = jFileChooser.getSelectedFile().getAbsolutePath();
                    List<ResultEntity> resultEntityList = ReadDataFileUtil.readFile(path);
                    ((AnalysisResultModel)resultTable.getModel()).setValue(resultEntityList);
                }
            }
        });
    }


    /**
     * 监听事件，点击table列表转换到tree上
     */
    public static void listenerTableToDetailTree(JTable resultTable, JTree resultDetailTree){
        resultTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                AnalysisResultTable analysisResultTable = (AnalysisResultTable)e.getSource();
                AnalysisResultModel analysisResultModel = analysisResultTable.getResultModel();
                createTree(resultDetailTree, analysisResultModel.getResultEntityList().get(analysisResultTable.getSelectedRow()));
            }
        });
    }


    /**
     * mdt cp4阶段的树
     * @param resultDetailTree
     * @param resultEntity
     */
    private static void createTree(JTree resultDetailTree, ResultEntity resultEntity){

        String mdtOrAt = resultEntity.getSercos().getSercosType().getSercosTypeData().getMdtOrAt();
        String cpNum = resultEntity.getSercos().getSercosPhase().getSercosPhaseData().getCommunPhase();
        if("MDT".equals(mdtOrAt)){
            if("CP0".equals(cpNum)){
                createMdtCp0Tree(resultDetailTree, resultEntity);
            }else if("CP1".equals(cpNum)){
                createMdtCp1Tree(resultDetailTree, resultEntity);
            }else if("CP2".equals(cpNum) || "CP3".equals(cpNum)){
                createMdtCp2Tree(resultDetailTree, resultEntity);
            }
        }else if("AT".equals(mdtOrAt)){
            if("CP0".equals(cpNum)){
                createAtCp0Tree(resultDetailTree, resultEntity);
            }else if("CP1".equals(cpNum)){
                createAtCp1Tree(resultDetailTree, resultEntity);
            }else if("CP2".equals(cpNum) || "CP3".equals(cpNum)){
                createAtCp2Tree(resultDetailTree, resultEntity);
            }
        }
    }


    /**
     * mdt cp0阶段的树
     * @param resultDetailTree
     * @param resultEntity
     */
    private static void createMdtCp0Tree(JTree resultDetailTree, ResultEntity resultEntity){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
        DefaultMutableTreeNode telegramNode = new DefaultMutableTreeNode("telegram Frame");
        root.add(telegramNode);
        telegramNode.add(new DefaultMutableTreeNode("Telegram Number(captured telegrams):" + resultEntity.getNum()));
        telegramNode.add(new DefaultMutableTreeNode("Telegram Number(received telegrams):" + resultEntity.getNum()));
        telegramNode.add(new DefaultMutableTreeNode("Telegram Length(no FCS):" + resultEntity.getTelegramFrame().getTelegramLength() + "Byte"));
        telegramNode.add(new DefaultMutableTreeNode("Capture Time:" + resultEntity.getTelegramFrame().getCaptureTime() + " s"));
        telegramNode.add(new DefaultMutableTreeNode("Precise Capture Time:" + resultEntity.getTelegramFrame().getCaptureTime() + " s"));
        telegramNode.add(new DefaultMutableTreeNode("Telegram Defect:" + resultEntity.getTelegramFrame().getTelegramDefect()));
        DefaultMutableTreeNode ethernetNode = new DefaultMutableTreeNode("Ethernet");
        ethernetNode.add(new DefaultMutableTreeNode("Destination Adress:" + resultEntity.getEthernet().getDestAddress()));
        ethernetNode.add(new DefaultMutableTreeNode("Source Adress:" + resultEntity.getEthernet().getSourceAddress()));
        ethernetNode.add(new DefaultMutableTreeNode("Ethernet Type:" + resultEntity.getEthernet().getEthernetType()));
        ethernetNode.add(new DefaultMutableTreeNode("Service Data Unit()SDU"));
        root.add(ethernetNode);
        DefaultMutableTreeNode sercosNode = new DefaultMutableTreeNode("Sercos");

        DefaultMutableTreeNode sercosTypeNode = new DefaultMutableTreeNode("Sercos Type:                   " + resultEntity.getSercos().getSercosType().getData());
        sercosTypeNode.add(new DefaultMutableTreeNode("P/S Telegram:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getPsTelegram()));
        sercosTypeNode.add(new DefaultMutableTreeNode("MDT ot AT:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getMdtOrAt()));
        sercosTypeNode.add(new DefaultMutableTreeNode("Cycle CNT enable:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getCycleCndEnable()));
        sercosTypeNode.add(new DefaultMutableTreeNode("Sercos Telegram Number:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getSercosTeleNum()));
        sercosTypeNode.add(new DefaultMutableTreeNode("Telegram Type and number:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getMdtOrAt() + " " + resultEntity.getSercos().getSercosType().getSercosTypeData().getSercosTeleNum()));
        sercosNode.add(sercosTypeNode);
        DefaultMutableTreeNode sercosPhaseNode = new DefaultMutableTreeNode("Sercos Phase:                 " + resultEntity.getSercos().getSercosPhase().getData());
        sercosPhaseNode.add(new DefaultMutableTreeNode("Communication Phase:   " + resultEntity.getSercos().getSercosPhase().getSercosPhaseData().getCommunPhase()));
        sercosPhaseNode.add(new DefaultMutableTreeNode("Communication Phase Switching:   " + resultEntity.getSercos().getSercosPhase().getSercosPhaseData().getCommunPhaseSwitch()));
        sercosPhaseNode.add(new DefaultMutableTreeNode("Cycle CNT:   " + resultEntity.getSercos().getSercosPhase().getSercosPhaseData().getCycleCnt()));
        sercosPhaseNode.add(new DefaultMutableTreeNode("Communication Phase and Switching:   " + resultEntity.getSercos().getSercosPhase().getSercosPhaseData().getCommunPhase()));
        sercosNode.add(sercosPhaseNode);


        DefaultMutableTreeNode sercosCrcNode = new DefaultMutableTreeNode("Sercos CRC");
        sercosCrcNode.add(new DefaultMutableTreeNode("Vaild Telegram:       yes"));
        sercosNode.add(sercosCrcNode);

        DefaultMutableTreeNode sercosMdtOrAtNode = new DefaultMutableTreeNode(resultEntity.getSercos().getSercosType().getSercosTypeData().getMdtOrAt());
        sercosMdtOrAtNode.add(new DefaultMutableTreeNode(resultEntity.getSercos().getSercosType().getSercosTypeData().getMdtOrAt()));
        sercosNode.add(sercosMdtOrAtNode);

        DefaultMutableTreeNode sercosSlavesNode = new DefaultMutableTreeNode("Slaves");
        /*List<Map<String, Object>> slavesList = resultEntity.getSercos().getSlaves();
        Map<String, Object> slaveMap = null;
        for(int i = 0; i < slavesList.size(); i++){
            slaveMap = slavesList.get(i);
            sercosSlavesNode.add(new DefaultMutableTreeNode("Slave" + (i + 1)));
        }*/
        sercosNode.add(sercosSlavesNode);

        root.add(sercosNode);

        TreeModel treeModel = new DefaultTreeModel(root);
        resultDetailTree.setModel(treeModel);
        resultDetailTree.setRootVisible(false);
    }

    private static void createMdtCp1Tree(JTree resultDetailTree, ResultEntity resultEntity){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
        DefaultMutableTreeNode telegramNode = new DefaultMutableTreeNode("telegram Frame");
        root.add(telegramNode);
        telegramNode.add(new DefaultMutableTreeNode("Telegram Number(captured telegrams):" + resultEntity.getNum()));
        telegramNode.add(new DefaultMutableTreeNode("Telegram Number(received telegrams):" + resultEntity.getNum()));
        telegramNode.add(new DefaultMutableTreeNode("Telegram Length(no FCS):" + resultEntity.getTelegramFrame().getTelegramLength() + "Byte"));
        telegramNode.add(new DefaultMutableTreeNode("Capture Time:" + resultEntity.getTelegramFrame().getCaptureTime() + " s"));
        telegramNode.add(new DefaultMutableTreeNode("Precise Capture Time:" + resultEntity.getTelegramFrame().getCaptureTime() + " s"));
        telegramNode.add(new DefaultMutableTreeNode("Telegram Defect:" + resultEntity.getTelegramFrame().getTelegramDefect()));
        DefaultMutableTreeNode ethernetNode = new DefaultMutableTreeNode("Ethernet");
        ethernetNode.add(new DefaultMutableTreeNode("Destination Adress:" + resultEntity.getEthernet().getDestAddress()));
        ethernetNode.add(new DefaultMutableTreeNode("Source Adress:" + resultEntity.getEthernet().getSourceAddress()));
        ethernetNode.add(new DefaultMutableTreeNode("Ethernet Type:" + resultEntity.getEthernet().getEthernetType()));
        ethernetNode.add(new DefaultMutableTreeNode("Service Data Unit()SDU"));
        root.add(ethernetNode);
        DefaultMutableTreeNode sercosNode = new DefaultMutableTreeNode("Sercos");

        DefaultMutableTreeNode sercosTypeNode = new DefaultMutableTreeNode("Sercos Type:                   " + resultEntity.getSercos().getSercosType().getData());
        sercosTypeNode.add(new DefaultMutableTreeNode("P/S Telegram:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getPsTelegram()));
        sercosTypeNode.add(new DefaultMutableTreeNode("MDT ot AT:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getMdtOrAt()));
        sercosTypeNode.add(new DefaultMutableTreeNode("Cycle CNT enable:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getCycleCndEnable()));
        sercosTypeNode.add(new DefaultMutableTreeNode("Sercos Telegram Number:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getSercosTeleNum()));
        sercosTypeNode.add(new DefaultMutableTreeNode("Telegram Type and number:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getMdtOrAt() + " " + resultEntity.getSercos().getSercosType().getSercosTypeData().getSercosTeleNum()));
        sercosNode.add(sercosTypeNode);
        DefaultMutableTreeNode sercosPhaseNode = new DefaultMutableTreeNode("Sercos Phase:                 " + resultEntity.getSercos().getSercosPhase().getData());
        sercosPhaseNode.add(new DefaultMutableTreeNode("Communication Phase:   " + resultEntity.getSercos().getSercosPhase().getSercosPhaseData().getCommunPhase()));
        sercosPhaseNode.add(new DefaultMutableTreeNode("Communication Phase Switching:   " + resultEntity.getSercos().getSercosPhase().getSercosPhaseData().getCommunPhaseSwitch()));
        sercosPhaseNode.add(new DefaultMutableTreeNode("Cycle CNT:   " + resultEntity.getSercos().getSercosPhase().getSercosPhaseData().getCycleCnt()));
        sercosPhaseNode.add(new DefaultMutableTreeNode("Communication Phase and Switching:   " + resultEntity.getSercos().getSercosPhase().getSercosPhaseData().getCommunPhase()));
        sercosNode.add(sercosPhaseNode);


        DefaultMutableTreeNode sercosCrcNode = new DefaultMutableTreeNode("Sercos CRC");
        sercosCrcNode.add(new DefaultMutableTreeNode("Vaild Telegram:       yes"));
        sercosNode.add(sercosCrcNode);

        DefaultMutableTreeNode sercosMdtOrAtNode = new DefaultMutableTreeNode("MDT");
        Map<String, Object> sercosMDT = resultEntity.getSercos().getSercosMDT();

        Map<String, Object> mdtDeviceControWord = (Map<String, Object>) sercosMDT.get("mdtDeviceControWord");
        DefaultMutableTreeNode mdtDeviceControWordNode = new DefaultMutableTreeNode("MDT Device Control Words");
        for(String key : mdtDeviceControWord.keySet()){
            DefaultMutableTreeNode mdtDeviceControWordNode1 = new DefaultMutableTreeNode(key);
            Map<String, Object> mdtDeviceControWordInfo = (Map<String, Object>) mdtDeviceControWord.get(key);
            for(String key1 : mdtDeviceControWordInfo.keySet()){
                mdtDeviceControWordNode1.add(new DefaultMutableTreeNode(key1 + ":" + mdtDeviceControWordInfo.get(key1)));
            }
            mdtDeviceControWordNode.add(mdtDeviceControWordNode1);
        }
        sercosMdtOrAtNode.add(mdtDeviceControWordNode);

        Map<String, Object> mdtServiceChannels = (Map<String, Object>) sercosMDT.get("mdtServiceChannels");
        DefaultMutableTreeNode mdtServiceChannelsNode = new DefaultMutableTreeNode("MDT Service Channels");

        for(String key : mdtServiceChannels.keySet()){
            mdtServiceChannelsNode.add(diguiTreeNode(key, (Map) mdtServiceChannels.get(key)));
        }
        sercosMdtOrAtNode.add(mdtServiceChannelsNode);

        sercosNode.add(sercosMdtOrAtNode);

        DefaultMutableTreeNode sercosSlavesNode = new DefaultMutableTreeNode("Slaves");
        Map<String, Object> slavesList = resultEntity.getSercos().getSlaves();
        Map<String, Object> slaveMap = null;

        /*for(int i = 0; i < slavesList.size(); i++){
            slaveMap = slavesList.get(i);
            sercosSlavesNode.add(new DefaultMutableTreeNode("Slave" + (i + 1)));
        }*/
        sercosNode.add(sercosSlavesNode);

        root.add(sercosNode);

        TreeModel treeModel = new DefaultTreeModel(root);
        resultDetailTree.setModel(treeModel);
        resultDetailTree.setRootVisible(false);
    }

    private static void createAtCp1Tree(JTree resultDetailTree, ResultEntity resultEntity){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
        DefaultMutableTreeNode telegramNode = new DefaultMutableTreeNode("telegram Frame");
        root.add(telegramNode);
        telegramNode.add(new DefaultMutableTreeNode("Telegram Number(captured telegrams):" + resultEntity.getNum()));
        telegramNode.add(new DefaultMutableTreeNode("Telegram Number(received telegrams):" + resultEntity.getNum()));
        telegramNode.add(new DefaultMutableTreeNode("Telegram Length(no FCS):" + resultEntity.getTelegramFrame().getTelegramLength() + "Byte"));
        telegramNode.add(new DefaultMutableTreeNode("Capture Time:" + resultEntity.getTelegramFrame().getCaptureTime() + " s"));
        telegramNode.add(new DefaultMutableTreeNode("Precise Capture Time:" + resultEntity.getTelegramFrame().getCaptureTime() + " s"));
        telegramNode.add(new DefaultMutableTreeNode("Telegram Defect:" + resultEntity.getTelegramFrame().getTelegramDefect()));
        DefaultMutableTreeNode ethernetNode = new DefaultMutableTreeNode("Ethernet");
        ethernetNode.add(new DefaultMutableTreeNode("Destination Adress:" + resultEntity.getEthernet().getDestAddress()));
        ethernetNode.add(new DefaultMutableTreeNode("Source Adress:" + resultEntity.getEthernet().getSourceAddress()));
        ethernetNode.add(new DefaultMutableTreeNode("Ethernet Type:" + resultEntity.getEthernet().getEthernetType()));
        ethernetNode.add(new DefaultMutableTreeNode("Service Data Unit()SDU"));
        root.add(ethernetNode);
        DefaultMutableTreeNode sercosNode = new DefaultMutableTreeNode("Sercos");

        DefaultMutableTreeNode sercosTypeNode = new DefaultMutableTreeNode("Sercos Type:                   " + resultEntity.getSercos().getSercosType().getData());
        sercosTypeNode.add(new DefaultMutableTreeNode("P/S Telegram:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getPsTelegram()));
        sercosTypeNode.add(new DefaultMutableTreeNode("MDT ot AT:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getMdtOrAt()));
        sercosTypeNode.add(new DefaultMutableTreeNode("Cycle CNT enable:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getCycleCndEnable()));
        sercosTypeNode.add(new DefaultMutableTreeNode("Sercos Telegram Number:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getSercosTeleNum()));
        sercosTypeNode.add(new DefaultMutableTreeNode("Telegram Type and number:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getMdtOrAt() + " " + resultEntity.getSercos().getSercosType().getSercosTypeData().getSercosTeleNum()));
        sercosNode.add(sercosTypeNode);
        DefaultMutableTreeNode sercosPhaseNode = new DefaultMutableTreeNode("Sercos Phase:                 " + resultEntity.getSercos().getSercosPhase().getData());
        sercosPhaseNode.add(new DefaultMutableTreeNode("Communication Phase:   " + resultEntity.getSercos().getSercosPhase().getSercosPhaseData().getCommunPhase()));
        sercosPhaseNode.add(new DefaultMutableTreeNode("Communication Phase Switching:   " + resultEntity.getSercos().getSercosPhase().getSercosPhaseData().getCommunPhaseSwitch()));
        sercosPhaseNode.add(new DefaultMutableTreeNode("Cycle CNT:   " + resultEntity.getSercos().getSercosPhase().getSercosPhaseData().getCycleCnt()));
        sercosPhaseNode.add(new DefaultMutableTreeNode("Communication Phase and Switching:   " + resultEntity.getSercos().getSercosPhase().getSercosPhaseData().getCommunPhase()));
        sercosNode.add(sercosPhaseNode);


        DefaultMutableTreeNode sercosCrcNode = new DefaultMutableTreeNode("Sercos CRC");
        sercosCrcNode.add(new DefaultMutableTreeNode("Vaild Telegram:       yes"));
        sercosNode.add(sercosCrcNode);
        root.add(sercosNode);

        Map<String, Object> slavesList = resultEntity.getSercos().getSlaves();
        Map<String, Object> slaveMap = null;
        DefaultMutableTreeNode slaveNode = null;
        for(String key : slavesList.keySet()){
            root.add(diguiTreeNode("Slaves", (Map) slavesList.get(key)));
        }

        /*List<Map<String, Object>> slavesList = resultEntity.getSercos().getSlaves();
        Map<String, Object> slaveMap = null;
        DefaultMutableTreeNode slaveNode = null;
        for(int i = 0; i < slavesList.size(); i++){
            slaveMap = slavesList.get(i);
            root.add(diguiTreeNode("Slaves", slaveMap));
        }*/

        TreeModel treeModel = new DefaultTreeModel(root);
        resultDetailTree.setModel(treeModel);
        resultDetailTree.setRootVisible(false);
    }

    private static void createAtCp2Tree(JTree resultDetailTree, ResultEntity resultEntity){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
        DefaultMutableTreeNode telegramNode = new DefaultMutableTreeNode("telegram Frame");
        root.add(telegramNode);
        telegramNode.add(new DefaultMutableTreeNode("Telegram Number(captured telegrams):" + resultEntity.getNum()));
        telegramNode.add(new DefaultMutableTreeNode("Telegram Number(received telegrams):" + resultEntity.getNum()));
        telegramNode.add(new DefaultMutableTreeNode("Telegram Length(no FCS):" + resultEntity.getTelegramFrame().getTelegramLength() + "Byte"));
        telegramNode.add(new DefaultMutableTreeNode("Capture Time:" + resultEntity.getTelegramFrame().getCaptureTime() + " s"));
        telegramNode.add(new DefaultMutableTreeNode("Precise Capture Time:" + resultEntity.getTelegramFrame().getCaptureTime() + " s"));
        telegramNode.add(new DefaultMutableTreeNode("Telegram Defect:" + resultEntity.getTelegramFrame().getTelegramDefect()));
        DefaultMutableTreeNode ethernetNode = new DefaultMutableTreeNode("Ethernet");
        ethernetNode.add(new DefaultMutableTreeNode("Destination Adress:" + resultEntity.getEthernet().getDestAddress()));
        ethernetNode.add(new DefaultMutableTreeNode("Source Adress:" + resultEntity.getEthernet().getSourceAddress()));
        ethernetNode.add(new DefaultMutableTreeNode("Ethernet Type:" + resultEntity.getEthernet().getEthernetType()));
        ethernetNode.add(new DefaultMutableTreeNode("Service Data Unit()SDU"));
        root.add(ethernetNode);
        DefaultMutableTreeNode sercosNode = new DefaultMutableTreeNode("Sercos");

        DefaultMutableTreeNode sercosTypeNode = new DefaultMutableTreeNode("Sercos Type:                   " + resultEntity.getSercos().getSercosType().getData());
        sercosTypeNode.add(new DefaultMutableTreeNode("P/S Telegram:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getPsTelegram()));
        sercosTypeNode.add(new DefaultMutableTreeNode("MDT ot AT:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getMdtOrAt()));
        sercosTypeNode.add(new DefaultMutableTreeNode("Cycle CNT enable:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getCycleCndEnable()));
        sercosTypeNode.add(new DefaultMutableTreeNode("Sercos Telegram Number:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getSercosTeleNum()));
        sercosTypeNode.add(new DefaultMutableTreeNode("Telegram Type and number:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getMdtOrAt() + " " + resultEntity.getSercos().getSercosType().getSercosTypeData().getSercosTeleNum()));
        sercosNode.add(sercosTypeNode);
        DefaultMutableTreeNode sercosPhaseNode = new DefaultMutableTreeNode("Sercos Phase:                 " + resultEntity.getSercos().getSercosPhase().getData());
        sercosPhaseNode.add(new DefaultMutableTreeNode("Communication Phase:   " + resultEntity.getSercos().getSercosPhase().getSercosPhaseData().getCommunPhase()));
        sercosPhaseNode.add(new DefaultMutableTreeNode("Communication Phase Switching:   " + resultEntity.getSercos().getSercosPhase().getSercosPhaseData().getCommunPhaseSwitch()));
        sercosPhaseNode.add(new DefaultMutableTreeNode("Cycle CNT:   " + resultEntity.getSercos().getSercosPhase().getSercosPhaseData().getCycleCnt()));
        sercosPhaseNode.add(new DefaultMutableTreeNode("Communication Phase and Switching:   " + resultEntity.getSercos().getSercosPhase().getSercosPhaseData().getCommunPhase()));
        sercosNode.add(sercosPhaseNode);


        DefaultMutableTreeNode sercosCrcNode = new DefaultMutableTreeNode("Sercos CRC");
        sercosCrcNode.add(new DefaultMutableTreeNode("Vaild Telegram:       yes"));
        sercosNode.add(sercosCrcNode);
        root.add(sercosNode);

        Map<String, Object> slavesList = resultEntity.getSercos().getSlaves();
        DefaultMutableTreeNode slaveNode = new DefaultMutableTreeNode("Slaves");

        for(String key : slavesList.keySet()){
            slaveNode.add((MutableTreeNode) diguiTreeNode(slaveNode, (Map) slavesList.get(key)).getFirstChild());
        }
        root.add(slaveNode);

        TreeModel treeModel = new DefaultTreeModel(root);
        resultDetailTree.setModel(treeModel);
        resultDetailTree.setRootVisible(false);
    }

    private static void createMdtCp2Tree(JTree resultDetailTree, ResultEntity resultEntity){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
        DefaultMutableTreeNode telegramNode = new DefaultMutableTreeNode("telegram Frame");
        root.add(telegramNode);
        telegramNode.add(new DefaultMutableTreeNode("Telegram Number(captured telegrams):" + resultEntity.getNum()));
        telegramNode.add(new DefaultMutableTreeNode("Telegram Number(received telegrams):" + resultEntity.getNum()));
        telegramNode.add(new DefaultMutableTreeNode("Telegram Length(no FCS):" + resultEntity.getTelegramFrame().getTelegramLength() + "Byte"));
        telegramNode.add(new DefaultMutableTreeNode("Capture Time:" + resultEntity.getTelegramFrame().getCaptureTime() + " s"));
        telegramNode.add(new DefaultMutableTreeNode("Precise Capture Time:" + resultEntity.getTelegramFrame().getCaptureTime() + " s"));
        telegramNode.add(new DefaultMutableTreeNode("Telegram Defect:" + resultEntity.getTelegramFrame().getTelegramDefect()));
        DefaultMutableTreeNode ethernetNode = new DefaultMutableTreeNode("Ethernet");
        ethernetNode.add(new DefaultMutableTreeNode("Destination Adress:" + resultEntity.getEthernet().getDestAddress()));
        ethernetNode.add(new DefaultMutableTreeNode("Source Adress:" + resultEntity.getEthernet().getSourceAddress()));
        ethernetNode.add(new DefaultMutableTreeNode("Ethernet Type:" + resultEntity.getEthernet().getEthernetType()));
        ethernetNode.add(new DefaultMutableTreeNode("Service Data Unit()SDU"));
        root.add(ethernetNode);
        DefaultMutableTreeNode sercosNode = new DefaultMutableTreeNode("Sercos");

        DefaultMutableTreeNode sercosTypeNode = new DefaultMutableTreeNode("Sercos Type:                   " + resultEntity.getSercos().getSercosType().getData());
        sercosTypeNode.add(new DefaultMutableTreeNode("P/S Telegram:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getPsTelegram()));
        sercosTypeNode.add(new DefaultMutableTreeNode("MDT ot AT:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getMdtOrAt()));
        sercosTypeNode.add(new DefaultMutableTreeNode("Cycle CNT enable:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getCycleCndEnable()));
        sercosTypeNode.add(new DefaultMutableTreeNode("Sercos Telegram Number:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getSercosTeleNum()));
        sercosTypeNode.add(new DefaultMutableTreeNode("Telegram Type and number:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getMdtOrAt() + " " + resultEntity.getSercos().getSercosType().getSercosTypeData().getSercosTeleNum()));
        sercosNode.add(sercosTypeNode);
        DefaultMutableTreeNode sercosPhaseNode = new DefaultMutableTreeNode("Sercos Phase:                 " + resultEntity.getSercos().getSercosPhase().getData());
        sercosPhaseNode.add(new DefaultMutableTreeNode("Communication Phase:   " + resultEntity.getSercos().getSercosPhase().getSercosPhaseData().getCommunPhase()));
        sercosPhaseNode.add(new DefaultMutableTreeNode("Communication Phase Switching:   " + resultEntity.getSercos().getSercosPhase().getSercosPhaseData().getCommunPhaseSwitch()));
        sercosPhaseNode.add(new DefaultMutableTreeNode("Cycle CNT:   " + resultEntity.getSercos().getSercosPhase().getSercosPhaseData().getCycleCnt()));
        sercosPhaseNode.add(new DefaultMutableTreeNode("Communication Phase and Switching:   " + resultEntity.getSercos().getSercosPhase().getSercosPhaseData().getCommunPhase()));
        sercosNode.add(sercosPhaseNode);


        DefaultMutableTreeNode sercosCrcNode = new DefaultMutableTreeNode("Sercos CRC");
        sercosCrcNode.add(new DefaultMutableTreeNode("Vaild Telegram:       yes"));
        sercosNode.add(sercosCrcNode);
        root.add(sercosNode);

        Map<String, Object> slavesList = resultEntity.getSercos().getSlaves();
        DefaultMutableTreeNode slaveNode = new DefaultMutableTreeNode("Slaves");

        for(String key : slavesList.keySet()){
            slaveNode.add((MutableTreeNode) diguiTreeNode(slaveNode, (Map) slavesList.get(key)).getFirstChild());
        }
        root.add(slaveNode);
        TreeModel treeModel = new DefaultTreeModel(root);
        resultDetailTree.setModel(treeModel);
        resultDetailTree.setRootVisible(false);
    }

    private static DefaultMutableTreeNode diguiTreeNode(Object key, Map val){
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(key);
        for(Object key1 : val.keySet()){
            if(val.get(key1) instanceof Map){
                DefaultMutableTreeNode node1 = diguiTreeNode(key1, (Map) val.get(key1));
                treeNode.add(node1);
            }else {
                treeNode.add(new DefaultMutableTreeNode(key1 + ":" + val.get(key1)));
            }
        }
        return treeNode;
    }

    /**
     * AT cp0阶段的树
     * @param resultDetailTree
     * @param resultEntity
     */
    private static void createAtCp0Tree(JTree resultDetailTree, ResultEntity resultEntity){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
        DefaultMutableTreeNode telegramNode = new DefaultMutableTreeNode("telegram Frame");
        root.add(telegramNode);
        telegramNode.add(new DefaultMutableTreeNode("Telegram Number(captured telegrams):" + resultEntity.getNum()));
        telegramNode.add(new DefaultMutableTreeNode("Telegram Number(received telegrams):" + resultEntity.getNum()));
        telegramNode.add(new DefaultMutableTreeNode("Telegram Length(no FCS):" + resultEntity.getTelegramFrame().getTelegramLength() + "Byte"));
        telegramNode.add(new DefaultMutableTreeNode("Capture Time:" + resultEntity.getTelegramFrame().getCaptureTime() + " s"));
        telegramNode.add(new DefaultMutableTreeNode("Precise Capture Time:" + resultEntity.getTelegramFrame().getCaptureTime() + " s"));
        telegramNode.add(new DefaultMutableTreeNode("Telegram Defect:" + resultEntity.getTelegramFrame().getTelegramDefect()));
        DefaultMutableTreeNode ethernetNode = new DefaultMutableTreeNode("Ethernet");
        ethernetNode.add(new DefaultMutableTreeNode("Destination Adress:" + resultEntity.getEthernet().getDestAddress()));
        ethernetNode.add(new DefaultMutableTreeNode("Source Adress:" + resultEntity.getEthernet().getSourceAddress()));
        ethernetNode.add(new DefaultMutableTreeNode("Ethernet Type:" + resultEntity.getEthernet().getEthernetType()));
        ethernetNode.add(new DefaultMutableTreeNode("Service Data Unit()SDU"));
        root.add(ethernetNode);
        DefaultMutableTreeNode sercosNode = new DefaultMutableTreeNode("Sercos");

        DefaultMutableTreeNode sercosTypeNode = new DefaultMutableTreeNode("Sercos Type:                   " + resultEntity.getSercos().getSercosType().getData());
        sercosTypeNode.add(new DefaultMutableTreeNode("P/S Telegram:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getPsTelegram()));
        sercosTypeNode.add(new DefaultMutableTreeNode("MDT ot AT:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getMdtOrAt()));
        sercosTypeNode.add(new DefaultMutableTreeNode("Cycle CNT enable:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getCycleCndEnable()));
        sercosTypeNode.add(new DefaultMutableTreeNode("Sercos Telegram Number:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getSercosTeleNum()));
        sercosTypeNode.add(new DefaultMutableTreeNode("Telegram Type and number:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getMdtOrAt() + " " + resultEntity.getSercos().getSercosType().getSercosTypeData().getSercosTeleNum()));
        sercosNode.add(sercosTypeNode);
        DefaultMutableTreeNode sercosPhaseNode = new DefaultMutableTreeNode("Sercos Phase:                 " + resultEntity.getSercos().getSercosPhase().getData());
        sercosPhaseNode.add(new DefaultMutableTreeNode("Communication Phase:   " + resultEntity.getSercos().getSercosPhase().getSercosPhaseData().getCommunPhase()));
        sercosPhaseNode.add(new DefaultMutableTreeNode("Communication Phase Switching:   " + resultEntity.getSercos().getSercosPhase().getSercosPhaseData().getCommunPhaseSwitch()));
        sercosPhaseNode.add(new DefaultMutableTreeNode("Cycle CNT:   " + resultEntity.getSercos().getSercosPhase().getSercosPhaseData().getCycleCnt()));
        sercosPhaseNode.add(new DefaultMutableTreeNode("Communication Phase and Switching:   " + resultEntity.getSercos().getSercosPhase().getSercosPhaseData().getCommunPhase()));
        sercosNode.add(sercosPhaseNode);

        DefaultMutableTreeNode sercosCrcNode = new DefaultMutableTreeNode("Sercos CRC");
        sercosCrcNode.add(new DefaultMutableTreeNode("Vaild Telegram:       yes"));
        sercosNode.add(sercosCrcNode);

        DefaultMutableTreeNode sercosMdtOrAtNode = new DefaultMutableTreeNode("AT");
        sercosMdtOrAtNode.add(new DefaultMutableTreeNode("Sequence Counter:                 " + resultEntity.getSercos().getSercosAT().get("sqquenceCounter")));

        DefaultMutableTreeNode topologyIndiciesNode = new DefaultMutableTreeNode("Topology Indicies");
        Map<String, Object> topoIndicList = (Map<String, Object>) resultEntity.getSercos().getSercosAT().get("topoIndics");

        int index = 1;
        for(String key : topoIndicList.keySet()){
            topologyIndiciesNode.add(new DefaultMutableTreeNode("Topology Index " + index + ":" + topoIndicList.get(key)));
            index++;
        }
        sercosMdtOrAtNode.add(topologyIndiciesNode);
        sercosNode.add(sercosMdtOrAtNode);
        DefaultMutableTreeNode sercosSlavesNode = new DefaultMutableTreeNode("Slaves");
        /*List<Map<String, Object>> slavesList = resultEntity.getSercos().getSlaves();
        Map<String, Object> slaveMap = null;
        for(int i = 0; i < slavesList.size(); i++){
            slaveMap = slavesList.get(i);
            sercosSlavesNode.add(new DefaultMutableTreeNode("Slave" + (i + 1)));
        }*/
        sercosNode.add(sercosSlavesNode);

        Map<String, Object> slavesList = resultEntity.getSercos().getSlaves();
        DefaultMutableTreeNode slaveNode = null;
        for(String key : slavesList.keySet()){
            root.add(diguiTreeNode("Slaves", (Map) slavesList.get(key)));
        }

        root.add(sercosNode);

        TreeModel treeModel = new DefaultTreeModel(root);
        resultDetailTree.setModel(treeModel);
        resultDetailTree.setRootVisible(false);
    }

    /**
     * mdt cp4阶段的树
     * @param resultDetailTree
     * @param resultEntity
     */
    private static void createMdtCp4Tree(JTree resultDetailTree, ResultEntity resultEntity){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
        DefaultMutableTreeNode telegramNode = new DefaultMutableTreeNode("telegram Frame");
        root.add(telegramNode);
        telegramNode.add(new DefaultMutableTreeNode("Telegram Number(captured telegrams):" + resultEntity.getNum()));
        telegramNode.add(new DefaultMutableTreeNode("Telegram Number(received telegrams):" + resultEntity.getNum()));
        telegramNode.add(new DefaultMutableTreeNode("Telegram Length(no FCS):" + resultEntity.getTelegramFrame().getTelegramLength() + "Byte"));
        telegramNode.add(new DefaultMutableTreeNode("Capture Time:" + resultEntity.getTelegramFrame().getCaptureTime() + " s"));
        telegramNode.add(new DefaultMutableTreeNode("Precise Capture Time:" + resultEntity.getTelegramFrame().getCaptureTime() + " s"));
        telegramNode.add(new DefaultMutableTreeNode("Telegram Defect:" + resultEntity.getTelegramFrame().getTelegramDefect()));
        DefaultMutableTreeNode ethernetNode = new DefaultMutableTreeNode("Ethernet");
        ethernetNode.add(new DefaultMutableTreeNode("Destination Adress:" + resultEntity.getEthernet().getDestAddress()));
        ethernetNode.add(new DefaultMutableTreeNode("Source Adress:" + resultEntity.getEthernet().getSourceAddress()));
        ethernetNode.add(new DefaultMutableTreeNode("Ethernet Type:" + resultEntity.getEthernet().getEthernetType()));
        ethernetNode.add(new DefaultMutableTreeNode("Service Data Unit()SDU"));
        root.add(ethernetNode);
        DefaultMutableTreeNode sercosNode = new DefaultMutableTreeNode("Sercos");

        DefaultMutableTreeNode sercosTypeNode = new DefaultMutableTreeNode("Sercos Type:                   " + resultEntity.getSercos().getSercosType().getData());
        sercosTypeNode.add(new DefaultMutableTreeNode("P/S Telegram:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getPsTelegram()));
        sercosTypeNode.add(new DefaultMutableTreeNode("MDT ot AT:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getMdtOrAt()));
        sercosTypeNode.add(new DefaultMutableTreeNode("Cycle CNT enable:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getCycleCndEnable()));
        sercosTypeNode.add(new DefaultMutableTreeNode("Sercos Telegram Number:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getSercosTeleNum()));
        sercosTypeNode.add(new DefaultMutableTreeNode("Telegram Type and number:         " + resultEntity.getSercos().getSercosType().getSercosTypeData().getMdtOrAt() + " " + resultEntity.getSercos().getSercosType().getSercosTypeData().getSercosTeleNum()));
        sercosNode.add(sercosTypeNode);
        DefaultMutableTreeNode sercosPhaseNode = new DefaultMutableTreeNode("Sercos Phase:                 " + resultEntity.getSercos().getSercosPhase().getData());
        sercosPhaseNode.add(new DefaultMutableTreeNode("Communication Phase:   " + resultEntity.getSercos().getSercosPhase().getSercosPhaseData().getCommunPhase()));
        sercosPhaseNode.add(new DefaultMutableTreeNode("Communication Phase Switching:   " + resultEntity.getSercos().getSercosPhase().getSercosPhaseData().getCommunPhaseSwitch()));
        sercosPhaseNode.add(new DefaultMutableTreeNode("Cycle CNT:   " + resultEntity.getSercos().getSercosPhase().getSercosPhaseData().getCycleCnt()));
        sercosPhaseNode.add(new DefaultMutableTreeNode("Communication Phase and Switching:   " + resultEntity.getSercos().getSercosPhase().getSercosPhaseData().getCommunPhase()));
        sercosNode.add(sercosPhaseNode);


        DefaultMutableTreeNode sercosCrcNode = new DefaultMutableTreeNode("Sercos CRC");
        sercosCrcNode.add(new DefaultMutableTreeNode("Vaild Telegram:       yes"));
        sercosNode.add(sercosCrcNode);

        DefaultMutableTreeNode sercosMdtOrAtNode = new DefaultMutableTreeNode(resultEntity.getSercos().getSercosType().getSercosTypeData().getMdtOrAt());
        sercosMdtOrAtNode.add(new DefaultMutableTreeNode(resultEntity.getSercos().getSercosType().getSercosTypeData().getMdtOrAt()));
        sercosNode.add(sercosMdtOrAtNode);

        DefaultMutableTreeNode sercosSlavesNode = new DefaultMutableTreeNode("Slaves");
        /*List<Map<String, Object>> slavesList = resultEntity.getSercos().getSlaves();
        Map<String, Object> slaveMap = null;
        for(int i = 0; i < slavesList.size(); i++){
            slaveMap = slavesList.get(i);
            sercosSlavesNode.add(new DefaultMutableTreeNode("Slave" + (i + 1)));
        }*/
        sercosNode.add(sercosSlavesNode);

        root.add(sercosNode);

        TreeModel treeModel = new DefaultTreeModel(root);
        resultDetailTree.setModel(treeModel);
        resultDetailTree.setRootVisible(false);
    }

    private static void createTree(JTree resultDetailTree){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
        DefaultMutableTreeNode newChild1 = new DefaultMutableTreeNode("nzx");
        root.add(newChild1);
        newChild1.add(new DefaultMutableTreeNode("nzx111"));
        DefaultMutableTreeNode newChild2 = new DefaultMutableTreeNode("ffff");
        newChild2.add(new DefaultMutableTreeNode("ffff222222"));
        root.add(newChild2);
        DefaultMutableTreeNode newChild3 = new DefaultMutableTreeNode("zzzz");
        newChild3.add(new DefaultMutableTreeNode("zzzz33333"));
        root.add(newChild3);
        TreeModel treeModel = new DefaultTreeModel(root);
        resultDetailTree.setModel(treeModel);
        resultDetailTree.setRootVisible(false);
    }
}
