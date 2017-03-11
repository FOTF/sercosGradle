package sercos.gui;

import sercos.process.entity.Slave;
import sercos.process.entity.SlaveRect;

import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by 宗祥 on 2017/3/11.
 */
public class DisplayPanel extends JPanel {

    private List<Slave> slaveList;

    private Graphics graphics;

    private static final int rectX = 100;

    private static final int rectY = 300;

    private static final int rectWidth = 600;

    private static final int rectHeight = 400;


    /**
     * 每个子片的宽度
     */
    private static final int slaveWidth = 20;

    /**
     * 每个字片的高度
     */
    private static final int slaveHight = 50;

    /**
     * 两个分片间的距离
     */
    private static final int slaveDistance = 30;

    /**
     * 状态圆与矩形的距离
     */
    private static final int slaveOvalDistance = slaveWidth;

    /**
     * 状态圆半径，和矩形宽度保持一致
     */
    private static final int ovalRadius = 20;

    private List<SlaveRect> rectList = new ArrayList<>();

    private boolean isDraw = false;

    @Override
    public void paint(Graphics g){
        super.paint(g);
        graphics = g;
        graphics.drawRect(rectX, rectY, rectWidth, rectHeight);
        this.calcSlaveLocation();
        this.drawSlaveRects();
        this.drawSlaveStatusOval();
        if(isDraw){

        }
    }

    private void drawSlaveRects(){
        for(SlaveRect slaveRect : rectList){
            graphics.drawRect(slaveRect.getX(), slaveRect.getY(), slaveRect.getWidth(), slaveRect.getHeight());
        }
    }

    private void drawSlaveStatusOval(){
        for(SlaveRect slaveRect : rectList){
            Graphics2D g2d = (Graphics2D)graphics;
            g2d.setColor(Color.GREEN);
            g2d.fillOval(slaveRect.getOvalX(), slaveRect.getOvalY(), slaveRect.getOvalWidth(), slaveRect.getOvalHeight());
        }
    }

    /**
     * 计算每个子路由展示的位置
     */
    private void calcSlaveLocation(){
        SlaveRect slaveRect = null;
        for(int i = 0; i < 3; i++){
            slaveRect = new SlaveRect();
            slaveRect.setX(rectX + slaveDistance + (slaveWidth + slaveDistance) * i);
            slaveRect.setY((rectY - slaveHight / 2));
            slaveRect.setWidth(slaveWidth);
            slaveRect.setHeight(slaveHight);

            slaveRect.setOvalX(slaveRect.getX());
            slaveRect.setOvalY(slaveRect.getY() - slaveOvalDistance - ovalRadius);
            slaveRect.setOvalWidth(ovalRadius);
            slaveRect.setOvalHeight(ovalRadius);
            rectList.add(slaveRect);
        }
    }

    public void draw(){
        isDraw = true;
        this.repaint();
    }
}
