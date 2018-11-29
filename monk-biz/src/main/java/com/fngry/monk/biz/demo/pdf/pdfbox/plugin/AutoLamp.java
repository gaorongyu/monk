package com.fngry.monk.biz.demo.pdf.pdfbox.plugin;

import com.fngry.monk.biz.demo.pdf.pdfbox.PrintImageLocations;

import java.awt.*;
import java.util.List;

/**
 * @author gaorongyu
 * @Date 2018/11/26
 */
public class AutoLamp extends AbstractAutoPartsPdfParsePlugin {

    @Override
    public String extractInfo(String originMsg) {
        return null;
    }

    @Override
    public String preDisposeStrContent(String originContent) {
        return null;
    }

    @Override
    public String getOeNo() {
        return null;
    }

    @Override
    public List<PrintImageLocations.ImageInfo> getImageInfo(PrintImageLocations imageLocations, Rectangle rectangle) {
        return null;
    }

    @Override
    public boolean isContentPageNum(int pageNum) {
        return false;
    }

}
