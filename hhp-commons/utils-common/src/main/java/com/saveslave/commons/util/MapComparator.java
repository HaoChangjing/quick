package com.saveslave.commons.util;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.Set;

public final class MapComparator {

    /**
     * 比较两个Map
     */
    public static boolean compareMaps(Map<String, Object> map1, Map<String, Object> map2, List<String> properties) {
        // 判断map1和map2的键集合是否相同
        Set<String> keys1 = map1.keySet();
        Set<String> keys2 = map2.keySet();

        // 如果两个Map的键数不同，直接返回字段数量不同
        if (keys1.size() != keys2.size()) {
            // return "Maps have different number of fields";
            return false;
        }

        // 如果字段相同数量，逐个检查每个字段的值是否相等
        for (String key : keys1) {
            if (!keys2.contains(key)) {
                // return "Field " + key + " is missing in map2";
                return false;
            }

            Object value1 = map1.get(key);
            Object value2 = map2.get(key);

            // 递归比较不同类型的值
            boolean result = compareValues(value1, value2, key, properties);
            if (!result) {
                return false;
            }
            // String result = compareValues(value1, value2, key);
            // if (!result.equals("Values are identical")) {
            //     return result;
            // }
        }

        // return "Maps are identical";
        return true;
    }

    // 比较值，考虑Map、List和其他类型
    @SuppressWarnings("unchecked")
    private static boolean compareValues(Object value1, Object value2, String key, List<String> properties) {
        if (value1 == null && value2 == null) {
            // return "Values are identical"; // 如果两个值都是null，认为相等
            return true;
        }

        if (value1 == null || value2 == null) {
            // return "Field " + key + " has different values: " + value1 + " vs " + value2;
            return false;
        }

        if (value1 instanceof Map && value2 instanceof Map) {
            // 递归比较两个Map
            return compareMaps((Map<String, Object>) value1, (Map<String, Object>) value2, properties);
            // String result = compareMaps((Map<String, Object>) value1, (Map<String, Object>) value2);
            // if (!result.equals("Maps are identical")) {
            //     return "In field " + key + ": " + result;
            // }
        } else if (value1 instanceof List && value2 instanceof List) {
            // 逐个比较List的元素
            return compareLists((List<JSONObject>) value1, (List<JSONObject>) value2, key, properties);
            // String result = compareLists((List<Object>) value1, (List<Object>) value2, key);
            // if (!result.equals("Lists are identical")) {
            //     return result;
            // }
        } else {
            // return "Field " + key + " has different values: " + value1 + " vs " + value2;
            return value1.equals(value2);
        }
    }

    // 比较List中的值
    public static boolean compareLists(List<JSONObject> list1, List<JSONObject> list2, String key, List<String> properties) {
        if (list1.size() != list2.size()) {
            // return "Field " + key + " has different list sizes: " + list1.size() + " vs " + list2.size();
            return false;
        }

        sortListByProperty(list1, properties);
        sortListByProperty(list2, properties);
        for (int i = 0; i < list1.size(); i++) {
            Object value1 = list1.get(i);
            Object value2 = list2.get(i);

            boolean result = compareValues(value1, value2, key + "[" + i + "]", properties);
            if (!result) {
                return false;
            }
            // String result = compareValues(value1, value2, key + "[" + i + "]");
            // if (!result.equals("Values are identical")) {
            //     return result;
            // }
        }

        // return "Lists are identical";
        return true;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void sortListByProperty(List<JSONObject> list, List<String> properties) {
        if (list == null || properties == null || properties.isEmpty()) {
            return;
        }

        list.sort((json1, json2) -> {
            for (String property : properties) {
                // 尝试获取两个对象中该属性的值
                Object val1 = json1.get(property);
                Object val2 = json2.get(property);

                // 如果两个值都为null，则继续检查下一个属性
                if (val1 == null && val2 == null) {
                    continue;
                }

                // 如果其中一个值为null，非null值排在前面
                if (val1 == null) return 1;
                if (val2 == null) return -1;

                // 确保属性值可以比较
                if (!(val1 instanceof Comparable) || !(val2 instanceof Comparable)) {
                    throw new ClassCastException("Property value must be Comparable.");
                }

                // 比较属性值并返回结果
                return ((Comparable) val1).compareTo((Comparable) val2);
            }
            // 如果所有属性都相等或为null，认为这两个对象相等
            return 0;
        });
    }

    public static void main(String[] args) {
        // 示例 1: Map包含Map作为值
        Map<String, Object> map1 = ImmutableMap.of(
                "name", "John",
                "age", 30,
                "address", ImmutableMap.of("city", "New York", "zip", 10001)
        );

        Map<String, Object> map2 = ImmutableMap.of(
                "name", "John",
                "age", 30,
                "address", ImmutableMap.of("city", "New York", "zip", 10001)
        );

        Map<String, Object> map3 = ImmutableMap.of(
                "name", "John",
                "age", 30,
                "address", ImmutableMap.of("city", "Boston", "zip", 10002)
        );

        // 示例 2: Map包含List作为值
        Map<String, Object> map4 = ImmutableMap.of(
                "name", "John",
                "age", 30,
                "hobbies", Lists.newArrayList("Reading", "Traveling", "Cooking")
        );

        Map<String, Object> map5 = ImmutableMap.of(
                "name", "John",
                "age", 30,
                "hobbies", Lists.newArrayList("Reading", "Traveling", "Cooking")
        );

        Map<String, Object> map6 = ImmutableMap.of(
                "name", "John",
                "age", 30,
                "hobbies", Lists.newArrayList("Reading", "Traveling")
        );

        System.out.println(compareMaps(map1, map2, Lists.newArrayList("address")));  // Maps are identical
        System.out.println(compareMaps(map1, map3, Lists.newArrayList("address")));  // In field address: Maps are identical (but values are different)
        System.out.println(compareMaps(map4, map5, Lists.newArrayList("hobbies")));  // Lists are identical
        System.out.println(compareMaps(map4, map6, Lists.newArrayList("hobbies")));  // Field hobbies has different list sizes: 3 vs 2
    }

}

