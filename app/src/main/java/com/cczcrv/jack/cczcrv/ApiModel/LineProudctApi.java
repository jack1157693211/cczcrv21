package com.cczcrv.jack.cczcrv.ApiModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 2018/1/24.
 */

public class LineProudctApi extends BaseListProductItemApi {
    public LineProudctApi()
    {
        albums=new ArrayList<>();
    }
    public String listExplain;
    public Double giftType;
    public List<String> albums;


    public String getListExplain() {
        return listExplain;
    }

    public void setListExplain(String listExplain) {
        this.listExplain = listExplain;
    }

    public Double getGiftType() {
        return giftType;
    }

    public void setGiftType(Double giftType) {
        this.giftType = giftType;
    }

    public List<String> getAlbums() {
        return albums;
    }

    public void setAlbums(List<String> albums) {
        this.albums = albums;
    }
}
