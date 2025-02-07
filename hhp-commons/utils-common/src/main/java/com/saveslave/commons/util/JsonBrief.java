package com.saveslave.commons.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

@Slf4j
public class JsonBrief {

    public static final int RESULT_LENGTH = 600;

    public static final int ARRAY_SIZE = 3;

    /**
     * json摘要信息
     * --数组仅显示前3个
     */
    public static String brief(Object obj) {
        return brief(JSONObject.toJSONString(obj));
    }

    /**
     * json摘要信息
     * --数组仅显示前3个
     */
    public static String brief(String jsonString) {
        try {
            if (StringUtils.isBlank(jsonString)) {
                return jsonString;
            }
            if (jsonString.length() <= RESULT_LENGTH) {
                return jsonString;
            }
            StringBuilder result = new StringBuilder();
            if (isArray(jsonString)) {
                JSONArray jsonArray = JSONArray.parseArray(jsonString);
                if (jsonArray.size() == 0) {
                    return jsonArray.toString();
                }
                for (int i = 0; i < jsonArray.size(); i++) {
                    if (i == 0) {
                        result.append("[");
                    }
                    result.append(brief(jsonArray.get(i).toString())).append(",");
                    if (i == ARRAY_SIZE - 1 || i == jsonArray.size() - 1) {
                        result.append("...");
                        result.append("]");
                        break;
                    }
                }
                return result.toString();
            }
            result.append("{");
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            if (jsonObject.isEmpty()) {
                return jsonObject.toString();
            }
            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                if (StringUtils.isNotBlank(entry.getKey())) {
                    result.append("\"").append(entry.getKey()).append("\"").append(":");
                }
                String value = entry.getValue().toString();
                if (isArray(value)) {
                    result.append(brief(entry.getValue().toString())).append(",");
                } else {
                    result.append("\"").append(brief(entry.getValue().toString())).append("\",");
                }
            }
            result.deleteCharAt(result.length() - 1);
            result.append("}");
            return result.toString();
        } catch (Exception e) {
            log.info("Exception:{}/{}", jsonString, e.getMessage());
            return StringUtils.left(jsonString, RESULT_LENGTH);
        }
    }

    public static boolean isArray(String string) {
        if (StringUtils.isEmpty(string)) {
            return false;
        }
        for (int i = 0; i < string.length(); ++i) {
            if (!Character.isWhitespace(string.charAt(i))) {
                return '[' == string.charAt(i);
            }
        }
        return false;
    }

}
