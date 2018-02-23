package com.cczcrv.jack.cczcrv.Model;

import java.io.Serializable;

/**
 * Created by jack on 2018/1/23.
 */

public class PackageModel implements Serializable {

    private Long id;
    private String name;
    private String price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
