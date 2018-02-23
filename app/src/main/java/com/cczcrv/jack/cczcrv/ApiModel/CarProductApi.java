package com.cczcrv.jack.cczcrv.ApiModel;

/**
 * Created by jack on 2018/2/8.
 */

public class CarProductApi extends BaseListProductItemApi {

    public String drivingLicense;
    public String numberLoad;
    public String beds;
    public String gearType;

    public String getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public String getNumberLoad() {
        return numberLoad;
    }

    public void setNumberLoad(String numberLoad) {
        this.numberLoad = numberLoad;
    }

    public String getBeds() {
        return beds;
    }

    public void setBeds(String beds) {
        this.beds = beds;
    }

    public String getGearType() {
        return gearType;
    }

    public void setGearType(String gearType) {
        this.gearType = gearType;
    }
}
