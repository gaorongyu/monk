package com.fngry.monk.common.log.tempalte;

import java.util.Map;

public class DefaultTemplateRender implements TemplateRender {

    @Override
    public String render(String valueExp, Map<String, Object> context) {
        return TemplateUtil.render(valueExp, context);
    }

}
