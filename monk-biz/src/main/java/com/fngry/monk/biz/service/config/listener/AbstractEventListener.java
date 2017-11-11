package com.fngry.monk.biz.service.config.listener;

import com.fngry.monk.biz.service.config.event.EventDispatcher;

/**
 * 事件监听器
 * Created by gaorongyu on 2017/11/11.
 */
public abstract class AbstractEventListener implements IEventListener {

    public AbstractEventListener() {
        register();
    }

    @Override
    public void register() {
        EventDispatcher.addListener(this);
    }

    @Override
    public void unRegister() {
        EventDispatcher.removeListener(this);
    }

}
