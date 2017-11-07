package com.fngry.monk.common.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.util.CollectionUtils;

/**
 * Created by gaorongyu on 16/12/13.
 */
public class CollectionUtil {

    /**
     *
     * list分组
     *
     * @param originList 原始参数列表
     * @param capacity   分组后每组的大小
     *
     * @return List<List<T>> 分组后的参数列表
     *
     */
    public static <T> List<List<T>>divideList(List<T> originList, int capacity) {
        List<List<T>> list = new LinkedList<>();
        int originListSize = originList.size();

        int length = originListSize / capacity;
        if(length == 0) {
            list.add(originList);
            return list;
        }
        if(originListSize % capacity > 0) {
            length = length + 1;
        }
        for(int i = 0; i < length; i++) {
            int fromIndex = i * capacity;
            int toIndex = (i + 1) * capacity > originListSize ? originListSize : (i + 1) * capacity;

            list.add(originList.subList(fromIndex, toIndex));
        }
        return list;
    }

    public static boolean isEmpty(Collection<?> collection) {
        return CollectionUtils.isEmpty(collection);
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !CollectionUtils.isEmpty(collection);
    }

}
