package com.cjh.tp.manage.controller;

import com.cjh.tp.manage.constant.Constants;
import com.cjh.tp.manage.service.CacheConfig;
import com.cjh.tp.manage.service.notify.entity.ConfigDataChangeEvent;
import com.cjh.tp.manage.service.notify.entity.Event;
import com.cjh.tp.manage.service.notify.NotifyCenter;
import com.cjh.tp.manage.service.notify.listener.Subscriber;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @program: tp
 * @description:
 * @author: chenjiehan
 * @create: 2020-11-27 13:04
 **/
@RestController
@RequestMapping(Constants.CONFIG_CONTROLLER_PATH)
public class TpConfigController {

    CacheConfig cacheConfig = CacheConfig.getInstance();

    /**
     * 配置中心 进行配置的修改、新增，并推给服务（未区分谁更新）
     *
     * @param configName
     * @param configValueJson
     * @return
     */
    @PostMapping("/publishConfig")
    public Boolean publishConfig(String configName, String configValueJson) {
        cacheConfig.saveConfig(configName, configValueJson);
        NotifyCenter.publishEvent(new ConfigDataChangeEvent(configName));
        return true;
    }

    /**
     * 长轮询 查看配置中心数据
     *
     * @return key, value
     */
    @PostMapping("/longPolling")
    public Map<String, String> longPollingConfig() throws InterruptedException {
        final String[] configValueJson = new String[1];
        final String[] configName = new String[1];
        //注册主题（就是监听），当事件发生的时候，会回调这里
        NotifyCenter.registerSubscriber(new Subscriber() {
            @Override
            public void onEvent(Event event) {
                if (event instanceof ConfigDataChangeEvent) {
                    ConfigDataChangeEvent configDataChangeEvent = (ConfigDataChangeEvent) event;
                    configName[0] = configDataChangeEvent.configName;
                    //获取新的数据
                    configValueJson[0] = cacheConfig.getConfig(configName[0]);
                }
            }
        });
        int i = 0;
        while (configValueJson[0] == null) {
            System.out.println("自旋中，等待数据");
            TimeUnit.SECONDS.sleep(1);
            if (i >= 30) {
                break;
            }
            i++;
        }
        Map<String, String> map = new HashMap<>();
        if (configName[0] == null) {
            map.put("好家伙", "没更新");
            return map;
        }
        map.put(configName[0], configValueJson[0]);
        System.out.println("获取数据,key:" + configName[0] + "value:" + configValueJson[0]);
        return map;
    }
}
