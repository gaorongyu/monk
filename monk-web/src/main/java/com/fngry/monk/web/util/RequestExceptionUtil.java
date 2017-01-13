package com.fngry.monk.web.util;

import com.fngry.monk.common.bizcode.SystemCode;
import com.fngry.monk.common.exception.BizException;
import com.fngry.monk.common.result.Result;
import com.fngry.monk.web.constant.WebConstants;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by gaorongyu on 16/11/30.
 */
@Slf4j
public class RequestExceptionUtil {

    /**
     *
     * 异常处理 业务异常, 返回业务提示result. 运行时异常, 提示系统异常
     *
     * @param ex
     * @param request
     * @param response
     * @throws IOException
     *
     */
    public static void handleException(Exception ex, HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        if (ex != null) {
            Result result;   // 返回给调用方请求结果result信息, 代替异常堆栈信息

            if (ex instanceof BizException) {
                // 业务异常 返回提示信息
                BizException bizEx = (BizException) ex ;
                result = Result.wrapErrorResult(bizEx.getCode(), bizEx.getMessage());
            } else {
                // 运行时异常
                log.error(" system runtime exception ", ex);
                result = Result.wrapErrorResult(SystemCode.SYSTEM_ERROR);
            }

            JSONObject jsonObject = new JSONObject(result);

            response.setContentType(WebConstants.CONTENT_TYPE_APPLICATION_JSON);
            response.getWriter().write(jsonObject.toString());
            response.getWriter().flush();
        }
    }

}
