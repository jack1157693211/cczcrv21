package com.cczcrv.jack.cczcrv.Model;

import java.io.Serializable;

/**
 * Created by jack on 2018/2/1.
 */

public class SubCalculateDetailModel implements Serializable {
    public String name;
    public Integer count;
    public String price;
    public String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
