package sercos.process.common;

/**
 * Created by 宗祥 on 2017/3/6.
 */
public enum MDTATEnum {

    MDT("0", "MDT"),

    AT("1", "AT")

    ;

    private String code;

    private String desc;

    private MDTATEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
