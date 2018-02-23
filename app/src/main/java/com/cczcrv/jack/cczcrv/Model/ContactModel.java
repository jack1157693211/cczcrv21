package com.cczcrv.jack.cczcrv.Model;

import java.io.Serializable;

/**
 * Created by jack on 2018/1/30.
 */

public class ContactModel implements Serializable {
    public String name;
    public String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
