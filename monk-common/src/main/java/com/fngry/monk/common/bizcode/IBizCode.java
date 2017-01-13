package com.fngry.monk.common.bizcode;

import java.io.Serializable;

/**
 *
 * 业务码 及含义
 *
 * Created by gaorongyu on 16/11/30.
 */
public interface IBizCode extends Serializable {

    public String getCode();

    public String getMessage();

    public String getMessage(Object... args);

}
