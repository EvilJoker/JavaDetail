package netty.serializable.java;

import java.io.Serializable;

/**
 * <code>SubscribeReq</code>
 * POJO类的定义,实现序列化得接口
 *
 * @author sunqiyuan
 * @date 2020-01-11
 */
public class SubscribeReq implements Serializable {
    private static final long servialVersionUID = 1L;
    private int subReqId;
    private String userName;
    private String productName;
    private String address;

    @Override
    public String toString() {
        return "SubscribeReq{" +
                "subReqId=" + subReqId +
                ", userName='" + userName + '\'' +
                ", productName='" + productName + '\'' +
                ", address='" + address + '\'' +

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

