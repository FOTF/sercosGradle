package sercos.gui;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.event.MouseEvent;

/**
 * Created by 宗祥 on 2017/2/28.
 */
public class MyJTable extends JTable {

    protected String[] columnToolTips = {null,
            null,
            "The person's favorite sport to participate in is : ",
            "The number of years the person has played the sportis : ",
            "If checked, the person eats no meat"};

    //Implement table cell tool tips.
    public String getToolTipText(MouseEvent e) {
        String tip = null;
        java.awt.Point p = e.getPoint();
        int rowIndex = rowAtPoint(p);
        int colIndex = columnAtPoint(p);
        int realColumnIndex = convertColumnIndexToModel(colIndex);
        if(rowIndex < 0)
        {
            //System.out.printf("abnormal rowIndex: %n", rowIndex);
            return null;
        }

        if (realColumnIndex == 2) { //Sport column
            tip = columnToolTips[2]
                    + getValueAt(rowIndex, colIndex);
        }
        else if (realColumnIndex == 3) { //Years column
            tip = columnToolTips[3] + getValueAt(rowIndex, colIndex);

        }else if (realColumnIndex == 4) { //Veggie column
            TableModel model = getModel();
            String firstName = (String)model.getValueAt(rowIndex,0);
            String lastName = (String)model.getValueAt(rowIndex,1);
            Boolean veggie = (Boolean)model.getValueAt(rowIndex,4);
            if (Boolean.TRUE.equals(veggie)) {
                tip = firstName + " " + lastName
                        + " is a vegetarian";
            } else {
                tip = firstName + " " + lastName
                        + " is not a vegetarian";
            }
        } else {
            //You can omit this part if you know you don't
            //have any renderers that supply their own tool
            //tips.
            tip = super.getToolTipText(e);
        }
        return tip;
    }

    //Implement table header tool tips.
    protected JTableHeader createDefaultTableHeader() {
        return new JTableHeader(columnModel) {
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int index = columnModel.getColumnIndexAtX(p.x);
                int realIndex =
                        columnModel.getColumn(index).getModelIndex();
                return columnToolTips[realIndex];
            }
        };
    }
}
