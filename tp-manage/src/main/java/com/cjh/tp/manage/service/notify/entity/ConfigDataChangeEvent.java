package com.cjh.tp.manage.service.notify.entity;


/**
 * @program: tp
 * @description: 配置文件改变事件
 * @author: chenjiehan
 * @create: 2020-11-27 16:00
 **/
public class ConfigDataChangeEvent extends Event {
    /**
     * 配置文件的名称
     */
    public final String configName;

    public ConfigDataChangeEvent(String configName) {
        this.configName = configName;
    }
}
