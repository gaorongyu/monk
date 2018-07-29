package com.fngry.monk.biz.service.accounting.common.filter;

public enum FilterEnum {

    SCRIPT_FILTER(ScriptFilter.class, "groovy脚本过滤器");

    FilterEnum(Class<? extends Filter> filterClass, String description) {
        this.filterClass = filterClass;
        this.description = description;
    }

    Class<? extends Filter> filterClass;

    String description;

    public Class<? extends Filter> getFilterClass() {
        return filterClass;
    }

    public void setFilterClass(Class<? extends Filter> filterClass) {
        this.filterClass = filterClass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
