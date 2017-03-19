package sercos.gui;

import sercos.process.entity.ResultEntity;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 宗祥 on 2017/2/28.
 */
public class AnalysisResultModel extends DefaultTableModel {

    /**
     * 定义模型的内部值，方便之后构造树
     */
    private List<ResultEntity> resultEntityList = new ArrayList<>();

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

    public void setValue(List<ResultEntity> resultEntityList){
        this.resultEntityList = resultEntityList;
        this.setNumRows(resultEntityList.size());
        for(int index = 0; index < resultEntityList.size(); index++){
            this.setValue(resultEntityList.get(index), index);
        }
    }

    public void addValue(ResultEntity resultEntity){
        resultEntityList.add(resultEntity);
        this.setNumRows(resultEntityList.size());
        this.setValue(resultEntity, resultEntityList.size() - 1);
    }

    public void setValue(ResultEntity resultEntity, int index){
        this.setValueAt(index, index, 0);
        this.setValueAt(resultEntity.getTelegramFrame().getCaptureTime(), index, 1);
        this.setValueAt("SERCOS", index, 2);
        this.setValueAt(resultEntity.getSercos().getSercosType().getSercosTypeData().getPsTelegram(), index, 3);
        this.setValueAt(resultEntity.getSercos().getSercosType().getSercosTypeData().getMdtOrAt() + " " + resultEntity.getSercos().getSercosType().getSercosTypeData().getSercosTeleNum(), index, 4);
        this.setValueAt(resultEntity.getSercos().getSercosPhase().getSercosPhaseData().getCommunPhase(), index, 5);
        this.setValueAt(resultEntity.getTelegramFrame().getTelegramLength(), index, 6);
    }

    public List<ResultEntity> getResultEntityList() {
        return resultEntityList;
    }

    public void setResultEntityList(List<ResultEntity> resultEntityList) {
        this.resultEntityList = resultEntityList;
    }
}
