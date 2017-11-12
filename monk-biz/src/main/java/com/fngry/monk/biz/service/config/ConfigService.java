package com.fngry.monk.biz.service.config;

import java.util.HashMap;
import java.util.Map;

import com.fngry.monk.biz.service.config.event.LocalDataChangeEvent;
import com.fngry.monk.biz.service.config.event.EventDispatcher;
import com.fngry.monk.common.result.Result;
import com.fngry.monk.common.util.EncryptUtil;
import com.fngry.monk.common.util.StringUtil;
import org.springframework.stereotype.Component;

/**
 * Created by gaorongyu on 2017/11/11.
 */
@Component("configService")
public class ConfigService {

    private Map<String, String> serverMd5Cache = new HashMap<>();

    public boolean isUptodate(String groupKey, String clientMd5) {
        String serverMd5 = serverMd5Cache.get(groupKey);

        if (StringUtil.isBlank(serverMd5)) {
            return false;
        }
        return serverMd5.equals(clientMd5);
    }

    public Result<Boolean> modify(String dataId, String group, String content, String type) {

        // todo update db record

        String groupKey = ConfigControl.generateGroupKey(dataId, group);
        serverMd5Cache.put(groupKey, EncryptUtil.md5Hex(content));

        // 触发数据变更事件
        LocalDataChangeEvent event = new LocalDataChangeEvent(this, groupKey, System.currentTimeMillis());
        EventDispatcher.fireEvent(event);

        // todo config data change, notify cluster

        return Result.wrapSuccessResult(true);
    }

}
