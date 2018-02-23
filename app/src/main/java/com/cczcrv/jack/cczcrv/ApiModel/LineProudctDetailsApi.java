package com.cczcrv.jack.cczcrv.ApiModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 2018/1/26.
 */

public class LineProudctDetailsApi {
    public LineProudctDetailsApi()
    {
        albums=new ArrayList<>();
    }
    public String price;
    public String originPrice;
    public String startCity;
    public String placeOfDeparture;
    public String startTime;
    public String lineDetail;
    public String travelProfile;
    public String carIntroduce;
    public String lineReserveNotice;
    public String name;
    public Integer browseNo;
    public Integer praiseNo;
    public String title;
    public String cover;
    public String shareDescription;
    public Integer giftType;
    public Integer commentNo;
    public Integer commodityScore;
    public List<String> albums;
    public Long id;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(String originPrice) {
        this.originPrice = originPrice;
    }

    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public String getPlaceOfDeparture() {
        return placeOfDeparture;
    }

    public void setPlaceOfDeparture(String placeOfDeparture) {
        this.placeOfDeparture = placeOfDeparture;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getLineDetail() {
        return lineDetail;
    }

    public void setLineDetail(String lineDetail) {
        this.lineDetail = lineDetail;
    }

    public String getTravelProfile() {
        return travelProfile;
    }

    public void setTravelProfile(String travelProfile) {
        this.travelProfile = travelProfile;
    }

    public String getCarIntroduce() {
        return carIntroduce;
    }

    public void setCarIntroduce(String carIntroduce) {
        this.carIntroduce = carIntroduce;
    }

    public String getLineReserveNotice() {
        return lineReserveNotice;
    }

    public void setLineReserveNotice(String lineReserveNotice) {
        this.lineReserveNotice = lineReserveNotice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBrowseNo() {
        return browseNo;
    }

    public void setBrowseNo(Integer browseNo) {
        this.browseNo = browseNo;
    }

    public Integer getPraiseNo() {
        return praiseNo;
    }

    public void setPraiseNo(Integer praiseNo) {
        this.praiseNo = praiseNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getShareDescription() {
        return shareDescription;
    }

    public void setShareDescription(String shareDescription) {
        this.shareDescription = shareDescription;
    }

    public Integer getGiftType() {
        return giftType;
    }

    public void setGiftType(Integer giftType) {
        this.giftType = giftType;
    }

    public Integer getCommentNo() {
        return commentNo;
    }

    public void setCommentNo(Integer commentNo) {
        this.commentNo = commentNo;
    }

    public Integer getCommodityScore() {
        return commodityScore;
    }

    public void setCommodityScore(Integer commodityScore) {
        this.commodityScore = commodityScore;
    }

    public List<String> getAlbums() {
        return albums;
    }

    public void setAlbums(List<String> albums) {
        this.albums = albums;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
