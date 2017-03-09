package sercos.process.entity;

/**
 * Created by 宗祥 on 2017/3/9.
 */
public class SercosObject {

    private String profileName;

    private IdentityObject identityObject;

    private ManagerObject managerObject;

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public IdentityObject getIdentityObject() {
        return identityObject;
    }

    public void setIdentityObject(IdentityObject identityObject) {
        this.identityObject = identityObject;
    }

    public ManagerObject getManagerObject() {
        return managerObject;
    }

    public void setManagerObject(ManagerObject managerObject) {
        this.managerObject = managerObject;
    }

    @Override
    public String toString() {
        return "SercosObject{" +
                "profileName='" + profileName + '\'' +
                ", identityObject=" + identityObject +
                ", managerObject=" + managerObject +
                '}';
    }
}
