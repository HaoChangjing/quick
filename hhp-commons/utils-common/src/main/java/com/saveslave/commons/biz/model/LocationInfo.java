package com.saveslave.commons.biz.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LocationInfo {

    private  String  province;
    private String provinceadcode;
    private  String  city;
    private String desc;
    private String longitude;
    private String   latitude;
    //城市编码
    private String   cityCode;
    //区县编码
    private String   adCode;
    //区县名称
    private  String  adName;



}
