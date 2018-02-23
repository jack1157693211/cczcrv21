package com.cczcrv.jack.cczcrv.ApiModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 2018/1/29.
 */

public class LinePriceApi {
    public LinePriceApi() {
        extras = new ArrayList<>();
        packages = new ArrayList<>();
    }

    public String departureCity;
    public String departureTime;
    public String costDescription;
    public List<Extra> extras;
    public String deductibleAmount;
    public String contactName;
    public String contactNo;
    public String productName;
    public String cover;
    public String browserNo;
    public String praiseNo;
    public String title;
    public String giftType;
    public String shareDescription;
    public List<Package> packages;

    public class Extra {
        public String extraName;
        public String stock;
        public Integer count;
        public Boolean billingByDay;
        public String price;
        public Long id;

        public String getExtraName() {
            return extraName;
        }

        public void setExtraName(String extraName) {
            this.extraName = extraName;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Boolean getBillingByDay() {
            return billingByDay;
        }

        public void setBillingByDay(Boolean billingByDay) {
            this.billingByDay = billingByDay;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    public class Package implements Serializable {
        public Package() {
            datePrices = new ArrayList<>();
        }

        public String packageName;
        public String introduce;
        public Boolean billingByDay;
        public String todayPrice;
        public Integer count;
        public String startDate;
        public String endDate;
        public Long id;
        public List<DatePrice> datePrices;

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public Boolean getBillingByDay() {
            return billingByDay;
        }

        public void setBillingByDay(Boolean billingByDay) {
            this.billingByDay = billingByDay;
        }

        public String getTodayPrice() {
            return todayPrice;
        }

        public void setTodayPrice(String todayPrice) {
            this.todayPrice = todayPrice;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public List<DatePrice> getDatePrices() {
            return datePrices;
        }

        public void setDatePrices(List<DatePrice> datePrices) {
            this.datePrices = datePrices;
        }
    }

    public class DatePrice {
        public Long packageId;
        public String date;
        public String price;
        public Integer stock;
        public Long id;

        public Long getPackageId() {
            return packageId;
        }

        public void setPackageId(Long packageId) {
            this.packageId = packageId;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public Integer getStock() {
            return stock;
        }

        public void setStock(Integer stock) {
            this.stock = stock;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getCostDescription() {
        return costDescription;
    }

    public void setCostDescription(String costDescription) {
        this.costDescription = costDescription;
    }

    public List<Extra> getExtras() {
        return extras;
    }

    public void setExtras(List<Extra> extras) {
        this.extras = extras;
    }

    public String getDeductibleAmount() {
        return deductibleAmount;
    }

    public void setDeductibleAmount(String deductibleAmount) {
        this.deductibleAmount = deductibleAmount;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getBrowserNo() {
        return browserNo;
    }

    public void setBrowserNo(String browserNo) {
        this.browserNo = browserNo;
    }

    public String getPraiseNo() {
        return praiseNo;
    }

    public void setPraiseNo(String praiseNo) {
        this.praiseNo = praiseNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGiftType() {
        return giftType;
    }

    public void setGiftType(String giftType) {
        this.giftType = giftType;
    }

    public String getShareDescription() {
        return shareDescription;
    }

    public void setShareDescription(String shareDescription) {
        this.shareDescription = shareDescription;
    }

    public List<Package> getPackages() {
        return packages;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }
}
