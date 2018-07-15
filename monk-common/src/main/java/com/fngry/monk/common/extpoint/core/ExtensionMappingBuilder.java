package com.fngry.monk.common.extpoint.core;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ExtensionMappingBuilder {

    private static ExtensionMappingBuilder builder = new ExtensionMappingBuilder();

    public static ExtensionMappingBuilder getInstance() {
        return builder;
    }

    private ExtensionMappingBuilder() {
    }

    private Map<Class, Multimap<String, ExtensionPoints>> extMap = new HashMap<>();


    public void build() {
        Reflections platformReflections = new Reflections("com.fngry.monk.common.extpoint.platform");

        //find all the extension point classes
        Set<Class<? extends ExtensionPoints>> extPointClasses =
                platformReflections.getSubTypesOf(ExtensionPoints.class);


        Reflections pluginsReflections = new Reflections("com.fngry.monk.common.extpoint.plugins");

        //find all the business plugins which implements the extension point
        Set<Class<? extends ExtensionFacade>> pluginFacades =
                pluginsReflections.getSubTypesOf(ExtensionFacade.class);


        for (Class<?> pointClass : extPointClasses) {

            Multimap<String, ExtensionPoints> bizCodeExtMap = ArrayListMultimap.create();
            for (Class<?> facade : pluginFacades) {
                BizCode[] annotationByType = facade.getAnnotationsByType(BizCode.class);

                if (annotationByType != null && annotationByType.length > 0) {
                    BizCode bizCode = annotationByType[0];

                    ExtensionPoints plugin = createExtInstance(facade, pointClass);
                    bizCodeExtMap.put(bizCode.value(), plugin);
                }

            }
            extMap.put(pointClass, bizCodeExtMap);
        }
    }

    private ExtensionPoints createExtInstance(Class<?> facade, Class<?> pointClass) {

        Object points;

        try {
            points = facade.newInstance();
            Method[] methods = facade.getDeclaredMethods();

            for (Method method : methods) {
                if (pointClass.isAssignableFrom(method.getReturnType())) {
                    try {
                        return (ExtensionPoints) method.invoke(points);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;

    }


    public <Ext> Ext getExtPoint(Class<Ext> extClass, String bizCode) {

        return (Ext) extMap.get(extClass).get(bizCode).iterator().next();

    }

}
