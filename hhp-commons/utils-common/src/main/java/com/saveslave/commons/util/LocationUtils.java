package com.saveslave.commons.util;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.saveslave.commons.biz.model.LocationInfo;
import org.apache.commons.lang3.StringUtils;

public class LocationUtils {
    private static String mapLocationurl = "https://ditu.amap.com/service/regeo?longitude=LONG&latitude=LATI";

    /**
     * 百度地图key
     */

    private static String key = "";

    private static String ipUrl = "http://api.map.baidu.com/location/ip?";

    /**
     * 根据经纬度获取地区信息
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @return
     */
    public static LocationInfo getAddress(String longitude, String latitude) {
        LocationInfo info = new LocationInfo();
        info.setLatitude(latitude).setLongitude(longitude);
        if (StringUtils.isEmpty(longitude) || StringUtils.isEmpty(latitude)) {
            return info;
        }
        try {
            String url = mapLocationurl.replace("LONG", longitude).replace("LATI", latitude);
            String result = HttpUtil.get(url, 2000);
            JSONObject jsonObject = JSON.parseObject(result);
            System.out.println(jsonObject);
            jsonObject.getJSONObject("data").getString("province_city");
            info.setCity(jsonObject.getJSONObject("data").getString("city"))
                    .setProvince(jsonObject.getJSONObject("data").getString("province"))
                    .setProvinceadcode(jsonObject.getJSONObject("data").getString("provinceadcode"))
                    .setDesc(jsonObject.getJSONObject("data").getString("desc"))
                    .setCityCode(jsonObject.getJSONObject("data").getString("cityadcode"))
                    .setAdCode(jsonObject.getJSONObject("data").getString("adcode"))
                    .setAdName(jsonObject.getJSONObject("data").getString("district"));
        } catch (Exception e) {
        }
        return info;
    }

    /**
     * 根据IP获取地理位置
     *
     * @param ip
     * @return
     */
    public static LocationInfo getAddressByIp(String ip) {
        LocationInfo info = new LocationInfo();
        if (StringUtils.isEmpty(ip)) {
            return null;
        }

        String url = ipUrl + "ip=" + ip + "&ak=" + key;
        String result = HttpUtil.get(url, 2000);
        JSONObject jsonObject = JSON.parseObject(result);
        System.out.println(jsonObject);
        if (StringUtils.equals("0", jsonObject.getString("status"))) {
            JSONObject addressObj = jsonObject.getJSONObject("content").getJSONObject("address_detail");
            info.setProvince(addressObj.getString("province"))
                    .setCity(addressObj.getString("city"))
                    .setCityCode(addressObj.getString("adcode"));
        }

        return info;
    }

    public static void main(String[] args) {
        System.out.println("getAddress(\"as\",\"as\") = " + getAddress("sdsd", "sdsd"));
    }
}
