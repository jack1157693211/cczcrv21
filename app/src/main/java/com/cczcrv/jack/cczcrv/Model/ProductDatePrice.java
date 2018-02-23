package com.cczcrv.jack.cczcrv.Model;

import java.io.Serializable;

/**
 * Created by jack on 2018/1/23.
 */

public class ProductDatePrice implements Serializable {
    private int id;
    private int productId;
    private String priceDate;
    private String price;
    private int limitStock;
    private int stock;
    private int online;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(String priceDate) {
        this.priceDate = priceDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getLimitStock() {
        return limitStock;
    }

    public void setLimitStock(int limitStock) {
        this.limitStock = limitStock;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    @Override
    public String toString() {
        return String.format("%s %s",priceDate,price);
    }
}
