package sercos.gui;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by 宗祥 on 2017/3/1.
 */
public class AnalysisResultTable extends JTable {

    private AnalysisResultModel resultModel;

    public AnalysisResultTable(){
        resultModel = new AnalysisResultModel();
        resultModel.setDataVector(
                new Object [][] {

                },
                new String [] {
                        "No.(rec.)", "Prec.Time/s", "EthType", "P/S", "Type&Nr.", "CP&S", "Length/Byte"
                }
        );

        this.setModel(resultModel);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
    }



    public AnalysisResultModel getResultModel() {
        return resultModel;
    }

    public void setResultModel(AnalysisResultModel resultModel) {
        this.resultModel = resultModel;
    }
}
