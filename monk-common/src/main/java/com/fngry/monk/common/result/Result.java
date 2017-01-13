package com.fngry.monk.common.result;

import com.fngry.monk.common.bizcode.IBizCode;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by gaorongyu on 16/11/28.
 */
@Data
public class Result<D> implements Serializable {

    private static final long serialVersionUID = -4027286376656560612L;

    private boolean success;

    private D data;

    private String code;

    private String message;


    public static <D> Result<D> wrapSuccessResult(D data) {
        Result<D> result = new Result<>();
        result.data = data;
        result.success = true;
        return result;
    }

    public static <D> Result<D> wrapErrorResult(String code, String message) {
        Result<D> result = new Result<>();
        result.success = false;
        result.code = code;
        result.message = message;
        return result;
    }

    public static <D> Result<D> wrapErrorResult(IBizCode bizCode) {
        Result<D> result = new Result<>();
        result.success = false;
        result.code = bizCode.getCode();
        result.message = bizCode.getMessage();
        return result;
    }

}
