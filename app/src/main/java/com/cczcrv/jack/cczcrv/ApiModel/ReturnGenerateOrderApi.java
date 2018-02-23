package com.cczcrv.jack.cczcrv.ApiModel;

import java.io.Serializable;

/**
 * Created by jack on 2018/2/2.
 */

public class ReturnGenerateOrderApi implements Serializable {
    public String orderNo;
    public Boolean needPay;
    public Boolean isForce;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Boolean getNeedPay() {
        return needPay;
    }

    public void setNeedPay(Boolean needPay) {
        this.needPay = needPay;
    }

    public Boolean getForce() {
        return isForce;
    }

    public void setForce(Boolean force) {
        isForce = force;
    }
}
