package netty.serializable.java;

import java.io.Serializable;

/**
 * <code>SubscribeReq</code>
 * POJO类的定义,实现序列化得接口
 *
 * @author sunqiyuan
 * @date 2020-01-11
 */
public class SubscribeResp implements Serializable {
    private static final long servialVersionUID = 1L;
    private int subReqId;
    private int respCode;
    private String desc;

    @Override
    public String toString() {
        return "SubscribeResp{" +
                "subReqId=" + subReqId +
                ", respCode='" + respCode + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

    public static long getServialVersionUID() {
        return servialVersionUID;
    }

    public int getSubReqId() {
        return subReqId;
    }

    public void setSubReqId(int subReqId) {
        this.subReqId = subReqId;
    }

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

