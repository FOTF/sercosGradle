package sercos.process.deal;

import sercos.gui.AnalysisResultModel;
import sercos.process.entity.Connection;
import sercos.process.entity.ResultEntity;
import sercos.process.entity.SercosObject;
import sercos.process.entity.Slave;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 宗祥 on 2017/3/15.
 */
public class DealProcess implements Runnable {

    /**
     * 配置文件
     */
    private SercosObject sercosObjectXml = null;

    /**
     * 报文集合
     */
    public static List<ResultEntity> baowenList = new ArrayList<>();

    /**
     * 处理中的报文
     */
    public static ResultEntity dealingMdtResultEntity = null;

    /**
     * 处理中的at报文
     */
    public static ResultEntity dealingAtResultEntity = null;

    /**
     * 从站信息集合
     */
    public List<Slave> slaveList = null;

    /**
     * 从站处理类集合
     */
    public List<SlaveProcess> slaveProcessList = null;

    private JTable resultTable = null;

    public DealProcess(SercosObject sercosObjectXml) {
        this.sercosObjectXml = sercosObjectXml;
    }

    public DealProcess(SercosObject sercosObjectXml, JTable resultTable) {
        this.sercosObjectXml = sercosObjectXml;
        this.resultTable = resultTable;
    }

    /**
     *
     */
    public MasterProcess masterProcess = null;

    public void init(){
        //配置主站
        masterProcess = new MasterProcess(sercosObjectXml);
        slaveProcessList = new ArrayList<>();
        //初始化从站
        List<Slave> slaves = sercosObjectXml.getManagerObject().getInitializationList().getProject().getRingList().get(0).getSlaveList();
        SlaveProcess slaveProcess = null;
        for(Slave slave : slaves){
            slaveProcess = new SlaveProcess(slave);
            slaveProcessList.add(slaveProcess);
        }
    }
    private static final int sleepTime = 100;

    @Override
    public void run() {
        int i = 0;
        while(i < 10){
            //初始化mdt cp0报文
            ResultEntity startMdtResultEntity1 = masterProcess.initMdtcp0Baowen();
            ResultEntity startMdtResultEntity2 = masterProcess.initMdtcp0Baowen();
            //初始报文记录一次
            System.out.println(startMdtResultEntity1.toString());
            baowenList.add(startMdtResultEntity1);
            ((AnalysisResultModel)resultTable.getModel()).addValue(startMdtResultEntity1);
            //主站发送报文
            masterProcess.sendMdt(startMdtResultEntity2, slaveProcessList);

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //初始化at cp0报文
            ResultEntity startAtResultEntity1 = masterProcess.initAtcp0Baowen();
            ResultEntity startAtResultEntity2 = masterProcess.initAtcp0Baowen();
            //初始报文记录一次
            System.out.println(startAtResultEntity1.toString());
            baowenList.add(startAtResultEntity1);
            ((AnalysisResultModel)resultTable.getModel()).addValue(startAtResultEntity1);
            masterProcess.sendAt(startAtResultEntity2, slaveProcessList);

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            for(SlaveProcess slaveProcess : slaveProcessList){
                slaveProcess.receiveMdtAndDeal(startMdtResultEntity2);
            }
            //结尾报文记录一次
            baowenList.add(startMdtResultEntity2);
            ((AnalysisResultModel)resultTable.getModel()).addValue(startMdtResultEntity2);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            for(SlaveProcess slaveProcess : slaveProcessList){
                slaveProcess.receiveAtAndDeal(startAtResultEntity2);
            }
            //结尾报文记录一次
            baowenList.add(startAtResultEntity2);
            ((AnalysisResultModel)resultTable.getModel()).addValue(startAtResultEntity2);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //这个时候主站应该知道了从站的地址信息，更新信息到主站的内存数据中
            masterProcess.setSlaveMacInfoMap((Map<String, String>)startAtResultEntity2.getSercos().getSercosAT().get("topoIndics"));


            //发送mdt cp1
            ResultEntity startMdtcp1ResultEntity1 = masterProcess.initMdtcp1Baowen();
            ResultEntity startMdtcp1ResultEntity2 = masterProcess.initMdtcp1Baowen();
            baowenList.add(startMdtcp1ResultEntity1);
            ((AnalysisResultModel)resultTable.getModel()).addValue(startMdtcp1ResultEntity1);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for(SlaveProcess slaveProcess : slaveProcessList){
                slaveProcess.receiveAtAndDeal(startMdtcp1ResultEntity2);
            }
            baowenList.add(startMdtcp1ResultEntity2);
            ((AnalysisResultModel)resultTable.getModel()).addValue(startMdtcp1ResultEntity2);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //发送at cp1
            ResultEntity startAtcp1ResultEntity1 = masterProcess.initAtcp1Baowen();
            ResultEntity startAtcp1ResultEntity2 = masterProcess.initAtcp1Baowen();
            baowenList.add(startAtcp1ResultEntity1);
            ((AnalysisResultModel)resultTable.getModel()).addValue(startAtcp1ResultEntity1);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for(SlaveProcess slaveProcess : slaveProcessList){
                slaveProcess.receiveAtAndDeal(startAtcp1ResultEntity2);
            }
            baowenList.add(startAtcp1ResultEntity2);
            ((AnalysisResultModel)resultTable.getModel()).addValue(startAtcp1ResultEntity2);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //先计算出最大的connection长度和idn长度
            int maxConnectionNum = 0;
            int maxIdnNum = 0;

            List<Slave> slaves = sercosObjectXml.getManagerObject().getInitializationList().getProject().getRingList().get(0).getSlaveList();
            for(Slave slave : slaves){
                if(maxConnectionNum < slave.getConnectionList().size()){
                    maxConnectionNum = slave.getConnectionList().size();
                }
                for(Connection connection : slave.getConnectionList()){
                    if(maxIdnNum  < connection.getIdnList().size()){
                        maxIdnNum = connection.getIdnList().size();
                    }
                }
            }

            //构建mdt cp2空白阶段的报文，发送的个数是con长度

            /*ResultEntity startMdtcp2BlankResultEntity1 = null;
            ResultEntity startMdtcp2BlankResultEntity2 = null;
            for(int blankIndex = 0; blankIndex < maxConnectionNum; blankIndex++){
                startMdtcp2BlankResultEntity1 = masterProcess.initMdtcp2Baowen("", 0, 0);
                startMdtcp2BlankResultEntity2 = masterProcess.initMdtcp2Baowen("", 0, 0);
                baowenList.add(startMdtcp2BlankResultEntity1);
                ((AnalysisResultModel)resultTable.getModel()).addValue(startMdtcp2BlankResultEntity1);
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/


            //构建mdt cp2 idn阶段的报文
            ResultEntity startMdtcp2IdnResultEntity1 = null;
            ResultEntity startMdtcp2IdnResultEntity2 = null;

            ResultEntity startAtcp2IdnResultEntity1 = null;
            ResultEntity startAtcp2IdnResultEntity2 = null;

            ResultEntity startMdtcp2OpdataResultEntity1 = null;
            ResultEntity startMdtcp2OpdataResultEntity2 = null;

            ResultEntity startAtcp2OpdataResultEntity1 = null;
            ResultEntity startAtcp2OpdataResultEntity2 = null;


            for(int connIndex = 0; connIndex < maxConnectionNum; connIndex++){
                for(int idnIndex = 0; idnIndex < maxIdnNum; idnIndex++){
                    startMdtcp2IdnResultEntity1 = masterProcess.initMdtcp2Baowen("idn", connIndex, idnIndex);
                    startMdtcp2IdnResultEntity2 = masterProcess.initMdtcp2Baowen("idn", connIndex, idnIndex);
                    baowenList.add(startMdtcp2IdnResultEntity1);
                    ((AnalysisResultModel)resultTable.getModel()).addValue(startMdtcp2IdnResultEntity1);
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for(SlaveProcess slaveProcess : slaveProcessList){
                        slaveProcess.receiveMdtAndDeal(startMdtcp2IdnResultEntity2);
                    }
                    baowenList.add(startMdtcp2IdnResultEntity2);
                    ((AnalysisResultModel)resultTable.getModel()).addValue(startMdtcp2IdnResultEntity2);
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //构建at cp2阶段的报文
                    startAtcp2IdnResultEntity1 = masterProcess.initAtcp2Baowen(null, connIndex, idnIndex);
                    startAtcp2IdnResultEntity2 = masterProcess.initAtcp2Baowen(null, connIndex, idnIndex);
                    baowenList.add(startAtcp2IdnResultEntity1);
                    ((AnalysisResultModel)resultTable.getModel()).addValue(startAtcp2IdnResultEntity1);
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for(SlaveProcess slaveProcess : slaveProcessList){
                        slaveProcess.receiveAtAndDeal(startAtcp2IdnResultEntity2);
                    }
                    baowenList.add(startAtcp2IdnResultEntity2);
                    ((AnalysisResultModel)resultTable.getModel()).addValue(startAtcp2IdnResultEntity2);
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //构建mdt cp2 opdata阶段报文
                    startMdtcp2OpdataResultEntity1 = masterProcess.initMdtcp2Baowen("opdata", connIndex, idnIndex);
                    startMdtcp2OpdataResultEntity2 = masterProcess.initMdtcp2Baowen("opdata", connIndex, idnIndex);
                    baowenList.add(startMdtcp2OpdataResultEntity1);
                    ((AnalysisResultModel)resultTable.getModel()).addValue(startMdtcp2OpdataResultEntity1);
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for(SlaveProcess slaveProcess : slaveProcessList){
                        slaveProcess.receiveMdtAndDeal(startMdtcp2OpdataResultEntity2);
                    }
                    baowenList.add(startMdtcp2OpdataResultEntity2);
                    ((AnalysisResultModel)resultTable.getModel()).addValue(startMdtcp2OpdataResultEntity2);
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //构建at cp2 opdata阶段的报文
                    startAtcp2OpdataResultEntity1 = masterProcess.initAtcp2Baowen(null, connIndex, idnIndex);
                    startAtcp2OpdataResultEntity2 = masterProcess.initAtcp2Baowen(null, connIndex, idnIndex);
                    baowenList.add(startAtcp2OpdataResultEntity1);
                    ((AnalysisResultModel)resultTable.getModel()).addValue(startAtcp2OpdataResultEntity1);
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for(SlaveProcess slaveProcess : slaveProcessList){
                        slaveProcess.receiveAtAndDeal(startAtcp2OpdataResultEntity2);
                    }
                    baowenList.add(startAtcp2OpdataResultEntity2);
                    ((AnalysisResultModel)resultTable.getModel()).addValue(startAtcp2OpdataResultEntity2);
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            }


            i++;
        }

    }
}
