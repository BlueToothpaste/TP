package com.cjh.tp.manage.service.notify.listener;

import com.cjh.tp.manage.service.notify.entity.Event;

/**
 * @program: tp
 * @description: 订阅者（监听器),定义成抽象类
 * @author: chenjiehan
 * @create: 2020-11-27 16:08
 **/
public abstract class Subscriber<T extends Event> {

    /**
     * 监听到事件，做什么事，写在这个方法内
     *
     * @param event
     */
    public abstract void onEvent(T event);

}
