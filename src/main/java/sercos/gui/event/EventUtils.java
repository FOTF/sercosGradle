package sercos.gui.event;


import sercos.gui.AnalysisResultModel;
import sercos.gui.AnalysisResultTable;
import sercos.gui.DisplayPanel;
import sercos.process.entity.ResultEntity;
import sercos.process.entity.SercosObject;
import sercos.process.util.ReadDataFileUtil;
import sercos.process.util.XMLReadUtil;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Created by 宗祥 on 2017/3/1.
 */
public class EventUtils {

    /**
     * 仿真按钮事件监听，选择文件
     */
    public static void listenerSimulationBtn(JButton simulationBtn, DisplayPanel displayPanel){
        simulationBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                int i = jFileChooser.showOpenDialog(null);
                if(i == JFileChooser.APPROVE_OPTION){
                    String path = jFileChooser.getSelectedFile().getAbsolutePath();
                    SercosObject sercosObject = XMLReadUtil.readXml(path);
                    displayPanel.setSlaveList(sercosObject.getManagerObject().getInitializationList().getProject().getRingList().get(0).getSlaveList());
                    displayPanel.draw();
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
                System.out.println(analysisResultModel.getResultEntityList());
                System.out.println(analysisResultModel.getResultEntityList().get(analysisResultTable.getSelectedRow()));
                createMdtCp4Tree(resultDetailTree, analysisResultModel.getResultEntityList().get(analysisResultTable.getSelectedRow()));
            }
        });
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
        root.add(sercosNode);

        DefaultMutableTreeNode sercosCrcNode = new DefaultMutableTreeNode("Sercos CRC");
        sercosCrcNode.add(new DefaultMutableTreeNode("Vaild Telegram:       yes"));
        root.add(sercosCrcNode);

        DefaultMutableTreeNode sercosMdtOrAtNode = new DefaultMutableTreeNode(resultEntity.getSercos().getSercosType().getSercosTypeData().getMdtOrAt());
        sercosMdtOrAtNode.add(new DefaultMutableTreeNode(resultEntity.getSercos().getSercosType().getSercosTypeData().getMdtOrAt()));
        root.add(sercosMdtOrAtNode);

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
