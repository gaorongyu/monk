package com.fngry.monk.common.exception;

import com.fngry.monk.common.bizcode.IBizCode;
import lombok.Data;

/**
 *
 * 业务异常
 *
 * Created by gaorongyu on 16/11/30.
 */
@Data
public class BizException extends RuntimeException {

    /**
     * 异常号
     */
    private String code;

    public BizException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(IBizCode bizCode) {
        super(bizCode.getMessage());
        this.code = bizCode.getMessage();
    }

}
