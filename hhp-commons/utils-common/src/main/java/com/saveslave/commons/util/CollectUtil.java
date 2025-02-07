package com.saveslave.commons.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;

import java.util.*;

@Slf4j
public class CollectUtil {


    public interface Tool<R, S> {
        R get(S s);
    }

    public interface ToolVoid<S> {
        void get(S s);
    }

    /**
     * 集合分批处理
     * ----默认1000一批
     *
     * @param dataSet 待处理数据
     * @param tool    处理方式
     * @param <S>     待处理数据类型
     * @param <R>     处理结果类型
     * @return
     */
    public static <S, R> List<R> partitionExecute(Collection<S> dataSet, Tool<List<R>, List<S>> tool) {
        return partitionExecute(dataSet, tool, 1000, null);
    }

    /**
     * 集合分批处理
     * ----默认1000一批
     *
     * @param dataSet 待处理数据
     * @param tool    处理方式
     * @param <S>     待处理数据类型
     * @param <R>     处理结果类型
     * @param logMark 日志输出标记
     */
    public static <S, R> List<R> partitionExecute(Collection<S> dataSet, Tool<List<R>, List<S>> tool, String logMark) {
        return partitionExecute(dataSet, tool, 1000, logMark);
    }

    /**
     * 集合分批处理
     * ----默认1000一批
     *
     * @param dataSet       待处理数据
     * @param tool          处理方式
     * @param <S>           待处理数据类型
     */
    public static <S> void partitionExecuteVoid(Collection<S> dataSet, ToolVoid<List<S>> tool) {
        partitionExecuteVoid(dataSet, tool, 1000, null);
    }

    /**
     * 集合分批处理
     *
     * @param dataSet 待处理数据
     * @param tool    处理方式
     * @param size    每批次大小
     * @param <S>     待处理数据类型
     * @param <R>     处理结果类型
     * @return
     */
    public static <S, R> List<R> partitionExecute(Collection<S> dataSet, Tool<List<R>, List<S>> tool, int size, String logMark) {
        if (CollectionUtils.isEmpty(dataSet)) {
            return new ArrayList<R>();
        }

        List<R> resultList = new ArrayList<>();
        List<S> arrayList = null;
        if (logMark == null || logMark.length() == 0) {
            logMark = "CollectUtil.partitionExecute(...)";
        }

        if (dataSet instanceof ArrayList) {
            arrayList = (ArrayList) dataSet;
        } else {
            arrayList = new ArrayList<S>(dataSet);
        }

        if (arrayList.size() <= size) {
            long begin = System.currentTimeMillis();
            resultList = tool.get(arrayList);
            long end = System.currentTimeMillis();
            log.info(logMark + " 数量[{}]/row, 耗时[{}]/s, 速度[{}]row/s"
                    , String.format("% 6d", arrayList.size())
                    , String.format("% 6.3f", (end - begin) / 1000d)
                    , String.format("% 6.3f", arrayList.size() * 1000d / (end - begin + 1)));
            return resultList;
        }

        List<List<S>> listList = ListUtils.partition(arrayList, size);
        for (List<S> subList : listList) {
            long begin = System.currentTimeMillis();
            List<R> tempResultList = tool.get(subList);
            long end = System.currentTimeMillis();
            log.info(logMark + " 数量[{}]/row, 耗时[{}]/s, 速度[{}]row/s"
                    , String.format("% 6d", subList.size())
                    , String.format("% 6.3f", (end - begin) / 1000d)
                    , String.format("% 6.3f", subList.size() * 1000d / (end - begin + 1)));
            if (tempResultList == null || tempResultList.size() == 0) {
                continue;
            }
            resultList.addAll(tempResultList);
        }
        return resultList;
    }

    /**
     * 集合分批处理<br>
     * 新增的方法: 处理没有返回值的情况
     *
     * @param dataSet       待处理数据
     * @param toolVoid      处理方式
     * @param size          每批次大小
     */
    public static <S> void partitionExecuteVoid(Collection<S> dataSet, ToolVoid<List<S>> toolVoid, int size, String logMark) {
        if (CollectionUtils.isEmpty(dataSet)) {
            return;
        }

        List<S> arrayList;

        if (logMark == null || logMark.isEmpty()) {
            logMark = "CollectUtil.partitionExecuteVoid(...)";
        }

        if (dataSet instanceof ArrayList) {
            arrayList = (ArrayList<S>) dataSet;
        } else {
            arrayList = new ArrayList<>(dataSet);
        }

        if (arrayList.size() <= size) {
            long begin = System.currentTimeMillis();
            toolVoid.get(arrayList);
            long end = System.currentTimeMillis();
            log.info(logMark + " 数量[{}]/row, 耗时[{}]/s, 速度[{}]row/s",
                    String.format("%6d", arrayList.size()),
                    String.format("%6.3f", (end - begin) / 1000d),
                    String.format("%6.3f", arrayList.size() * 1000d / (end - begin + 1)));
            return;
        }

        List<List<S>> listList = ListUtils.partition(arrayList, size);
        for (List<S> subList : listList) {
            long begin = System.currentTimeMillis();
            toolVoid.get(subList);
            long end = System.currentTimeMillis();
            log.info(logMark + " 数量[{}]/row, 耗时[{}]/s, 速度[{}]row/s",
                    String.format("%6d", subList.size()),
                    String.format("%6.3f", (end - begin) / 1000d),
                    String.format("%6.3f", subList.size() * 1000d / (end - begin + 1)));
        }
    }

    /**
     * List 转 Map
     * --可重复
     *
     * @param dataSet
     * @param tool
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, List<V>> convert2MapList(Collection<V> dataSet, Tool<K, V> tool) {
        Map<K, List<V>> map = new HashMap<>();
        if (org.springframework.util.CollectionUtils.isEmpty(dataSet)) {
            return map;
        }
        for (V obj : dataSet) {
            K key = tool.get(obj);
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(obj);
        }
        return map;
    }

    /**
     * List 转map
     * --无重复
     *
     * @param dataSet
     * @param tool
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> convert2Map(Collection<V> dataSet, Tool<K, V> tool) {
        Map<K, V> map = new HashMap<>();
        if (org.springframework.util.CollectionUtils.isEmpty(dataSet)) {
            return map;
        }
        for (V obj : dataSet) {
            K key = tool.get(obj);
            map.put(key, obj);
        }
        return map;
    }

    /**
     * List 转类型 List
     *
     * @param dataSet
     * @param tool
     * @param <T>
     * @param <S>
     * @return
     */
    public static <T, S> List<T> convert2List(Collection<S> dataSet, Tool<T, S> tool) {
        List<T> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(dataSet)) {
            return list;
        }
        for (S obj : dataSet) {
            T t = tool.get(obj);
            list.add(t);
        }
        return list;
    }

    public static <T, S> Set<T> convert2Set(Collection<S> dataSet, Tool<T, S> tool) {
        Set<T> set = new HashSet<>();
        if (CollectionUtils.isEmpty(dataSet)) {
            return set;
        }
        for (S obj : dataSet) {
            T t = tool.get(obj);
            set.add(t);
        }
        return set;
    }


    /**
     * 集合toString
     *
     * @param dateSet
     * @param <E>
     * @return
     */
    public static <E> String toString(Collection<E> dateSet) {
        return toString(dateSet, 30);
    }

    /**
     * 集合toString
     *
     * @param dateSet
     * @param <E>
     * @return
     */
    public static <E> String toString(Collection<E> dateSet, int max) {
        if (CollectionUtils.isEmpty(dateSet)) {
            return "{empty Collection}";
        }
        StringBuilder stringBuilder = new StringBuilder();
        int count = 0;
        for (E obj : dateSet) {
            stringBuilder.append(obj.toString()).append("\n");
            if (++count >= max) {
                break;
            }
        }
        return stringBuilder.toString();
    }

    public static <T> void addAllIfNotEmpty(Collection<T> target, Collection<T> source) {
        if (CollectionUtils.isNotEmpty(source)) {
            target.addAll(source);
        }
    }

}

