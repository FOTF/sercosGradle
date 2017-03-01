package sercos.gui;

import javax.swing.table.DefaultTableModel;

/**
 * Created by 宗祥 on 2017/2/28.
 */
public class AnalysisResultModel extends DefaultTableModel {

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return super.getColumnClass(columnIndex);
    }

    /**
     * 返回选中行的值
     * @param row
     */
    public void printSelectRowValue(int row){
        System.out.println("---" + this.getDataVector().get(row));
    }
}
