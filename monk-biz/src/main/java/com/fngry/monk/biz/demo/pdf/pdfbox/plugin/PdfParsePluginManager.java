package com.fngry.monk.biz.demo.pdf.pdfbox.plugin;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gaorongyu
 * @Date 2018/11/26
 */
public class PdfParsePluginManager {

    private static final Map<String, AutoPartsPdfParsePlugin> plugins = new HashMap<>();

    static {
        plugins.put("OEM_unlocked", new OEMUnlocked());
        plugins.put("Auto-Lamp-S1.5-unlocked", new AutoLamp());
    }

    public static AutoPartsPdfParsePlugin getPlugin(String bizCode) {
        return plugins.get(bizCode);
    }

}
