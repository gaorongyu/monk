package com.fngry.monk.biz.service.config.listener;

import java.util.List;

import com.fngry.monk.biz.service.config.event.Event;

/**
 * 时间监听接口
 * Created by gaorongyu on 2017/11/11.
 */
public interface IEventListener {

    /**
     * 感兴趣的事件列表
     * @return
     */
    List<Class<? extends Event>> interest();

    /**
     * 响应事件
     * @param event
     */
    void onEvent(Event event);

    /**
     * 注册监听器
     */
    void register();

    /**
     * 移除监听器
     */
    void unRegister();

}
