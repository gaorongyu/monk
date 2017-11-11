package com.fngry.monk.biz.service.config;

import org.springframework.stereotype.Component;

/**
 * Created by gaorongyu on 2017/11/11.
 */
@Component("configService")
public class ConfigService {

    public boolean isUptodate(String groupKey, String clientMd5) {
        // todo
        if ("biztype_fngry".equals(groupKey)) {
            // 不是最新 需更新
            return false;
        }
        return true;
    }

}
