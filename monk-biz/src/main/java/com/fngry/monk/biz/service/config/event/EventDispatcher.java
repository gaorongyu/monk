package com.fngry.monk.biz.service.config.event;

import java.util.ArrayList;
import java.util.List;

import com.fngry.monk.biz.service.config.listener.IEventListener;

/**
 * 时间分发器
 * Created by gaorongyu on 2017/11/11.
 */
public class EventDispatcher {

    /**
     * 监听器列表
     */
    private static List<IEventListener> listenerList = new ArrayList<>();

    /**
     * 添加监听器
     * @param listener
     */
    public static synchronized void addListener(IEventListener listener) {
        EventDispatcher.listenerList.add(listener);
    }

    /**
     * 移除监听器
     * @param listener
     */
    public static synchronized void removeListener(IEventListener listener) {
        if (EventDispatcher.listenerList.contains(listener)) {
            EventDispatcher.listenerList.remove(listener);
        }
    }

    /**
     * 分发事件
     * @param event
     */
    public static void fireEvent(Event event) {
        for (IEventListener listener : listenerList) {
            fireEvent(event, listener);
        }
    }

    /**
     * 分发事件
     * @param event
     * @param listener
     */
    private static void fireEvent(Event event, IEventListener listener) {
        List<Class<? extends Event>> interestList = listener.interest();

        for (Class clazz : interestList) {
            if (clazz.equals(event.getClass())) {
                listener.onEvent(event);
            }
        }
    }

}
