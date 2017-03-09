package sercos.process.entity;

import java.util.List;

/**
 * Created by 宗祥 on 2017/3/9.
 */
public class ManagerObject {

    private String fileNo;

    private String fileLocation;

    private InitializationList initializationList;

    public String getFileNo() {
        return fileNo;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public InitializationList getInitializationList() {
        return initializationList;
    }

    public void setInitializationList(InitializationList initializationList) {
        this.initializationList = initializationList;
    }

    @Override
    public String toString() {
        return "ManagerObject{" +
                "fileNo='" + fileNo + '\'' +
                ", fileLocation='" + fileLocation + '\'' +
                ", initializationList=" + initializationList +
                '}';
    }
}
