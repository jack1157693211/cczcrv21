package com.cczcrv.jack.cczcrv.Model;

import java.io.Serializable;

/**
 * Created by jack on 2018/1/30.
 */

public class PackageReturnModel implements Serializable {
    public Long id;
    public String name;
    public String price;
    public Integer count;
    public Integer maxCount;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
