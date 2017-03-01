package sercos.gui.event;


import sercos.gui.AnalysisResultModel;
import sercos.gui.AnalysisResultTable;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by 宗祥 on 2017/3/1.
 */
public class EventUtils {

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
                //analysisResultModel.printSelectRowValue(analysisResultTable.getSelectedRow());
                createTree(resultDetailTree);
            }
        });
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
