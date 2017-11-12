package com.fngry.monk.web.controller.config;

import javax.annotation.Resource;

import com.fngry.monk.biz.service.config.ConfigService;
import com.fngry.monk.common.result.Result;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by gaorongyu on 2017/11/12.
 */
@RequestMapping("config")
@Component("configController")
public class ConfigController {

    @Resource
    private ConfigService configService;

    /**
     * 修改配置
     * @param dataId  dataId
     * @param group   group
     * @param content 内容
     * @param type  数据类型 xml, json, properties
     */
    @RequestMapping("modify")
    @ResponseBody
    public Result<Boolean> modify(String dataId, String group, String content, String type) {
        return configService.modify(dataId, group, content, type);
    }

}
