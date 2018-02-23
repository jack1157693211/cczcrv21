package com.cczcrv.jack.cczcrv.ApiModel;

import java.io.Serializable;

/**
 * Created by jack on 2018/2/8.
 */

public class BaseListProductItemApi implements Serializable {
    public String name;
    public String price;
    public String cover;
    public Double originPrice;
    public Integer praiseNo;
    public Double discount;
    public Integer browseNo;
    public String cityName;
    public Long id;

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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Double getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(Double originPrice) {
        this.originPrice = originPrice;
    }

    public Integer getPraiseNo() {
        return praiseNo;
    }

    public void setPraiseNo(Integer praiseNo) {
        this.praiseNo = praiseNo;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getBrowseNo() {
        return browseNo;
    }

    public void setBrowseNo(Integer browseNo) {
        this.browseNo = browseNo;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
