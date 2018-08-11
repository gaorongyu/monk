package com.fngry.monk.common.log.tempalte;

import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.concurrent.CopyOnWriteArrayList;

public class TemplateRenderManager {

    private static TemplateRender defaultTemplateRender = new DefaultTemplateRender();

    private static CopyOnWriteArrayList<TemplateRender> registeredRenders = new CopyOnWriteArrayList<>();

    static {
        initTemplateRender();
    }

    public static void register(TemplateRender templateRender) {
        registeredRenders.add(templateRender);
    }

    public static void initTemplateRender() {
        ServiceLoader<TemplateRender> loadedRenders = ServiceLoader.load(TemplateRender.class);
        Iterator<TemplateRender> renderIterator = loadedRenders.iterator();

        while (renderIterator.hasNext()) {
            register(renderIterator.next());
        }
    }

    public static TemplateRender getRender() {
        return registeredRenders.size() > 0 ? registeredRenders.get(0) : defaultTemplateRender;
    }

}
