package sercos.process.entity;

/**
 * Created by 宗祥 on 2017/3/9.
 */
public class InitializationList {

    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "InitializationList{" +
                "project=" + project +
                '}';
    }
}
