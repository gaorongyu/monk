package com.fngry.monk.biz.service.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fngry.monk.common.util.StringUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by gaorongyu on 2017/11/11.
 */
@Component("configControl")
public class ConfigControl implements ApplicationContextAware {

    public static final String SEPARATOR_CONFIG = ";";

    public static final String SEPARATOR_GROUP_VALUE = ":";

    public static final String SEPARATOR_GROUP_KEY = ",";

    /**
     * groupKeyL连接符 dataId_group
     */
    public static final String CONCAT_GROUP_KEY = "_";

    private static ConfigService configService;

    /**
     *
     * key1=value1;key2=value2... convert to Map
     *
     * @param probeModify
     * @return
     */
    public static Map<String, String> getClientMd5Map(String probeModify) {
        Map<String, String> clientMd5Map = new HashMap<>();
        String[] clientConfigs = StringUtil.split(probeModify, SEPARATOR_CONFIG);
        for (String config : clientConfigs) {
            String[] groups = StringUtil.split(config, SEPARATOR_GROUP_VALUE);
            String groupKey = groups[0];
            String clientMd5 = groups[1];

            clientMd5Map.put(groupKey, clientMd5);
        }
        return clientMd5Map;
    }

    public static List<String> compareMd5(HttpServletRequest req, HttpServletResponse resp, Map<String, String> clientMd5Map) {
        List<String> changedGroupKeys = new ArrayList<>();

        for (Map.Entry<String, String> entry : clientMd5Map.entrySet()) {
            String groupKey = entry.getKey();
            String clientMd5 = entry.getValue();

            boolean isUptodate = configService.isUptodate(groupKey, clientMd5);
            if (!isUptodate) {
                changedGroupKeys.add(groupKey);
            }
        }
        return changedGroupKeys;
    }

    public static String generateGroupKey(String dataId, String group) {
        return dataId + CONCAT_GROUP_KEY + group;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        configService = (ConfigService) applicationContext.getBean("configService");
    }

}
