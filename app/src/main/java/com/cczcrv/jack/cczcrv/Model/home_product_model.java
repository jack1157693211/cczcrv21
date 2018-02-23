package com.cczcrv.jack.cczcrv.Model;

/**
 * Created by jack on 2018/1/10.
 */

public class home_product_model {
    public Long id;
    public Integer productPic;
    public Integer productPicTag;
    public String productName;
    public String productDescribe;
    public String productMoney;
    public String productStartCity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProductPic() {
        return productPic;
    }

    public void setProductPic(Integer productPic) {
        this.productPic = productPic;
    }

    public Integer getProductPicTag() {
        return productPicTag;
    }

    public void setProductPicTag(Integer productPicTag) {
        this.productPicTag = productPicTag;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescribe() {
        return productDescribe;
    }

    public void setProductDescribe(String productDescribe) {
        this.productDescribe = productDescribe;
    }

    public String getProductMoney() {
        return productMoney;
    }

    public void setProductMoney(String productMoney) {
        this.productMoney = productMoney;
    }

    public String getProductStartCity() {
        return productStartCity;
    }

    public void setProductStartCity(String productStartCity) {
        this.productStartCity = productStartCity;
    }
}
