package com.fngry.monk.biz.demo.pdf.pdfbox.plugin;

import com.fngry.monk.biz.demo.pdf.pdfbox.PrintImageLocations;

import java.awt.*;
import java.util.List;

/**
 * @author gaorongyu
 * @Date 2018/11/29
 */
public abstract class AbstractAutoPartsPdfParsePlugin implements AutoPartsPdfParsePlugin {

    @Override
    public int getPdfPageWidth() {
        return 595;
    }

    @Override
    public int getPdfPageHeight() {
        return 842;
    }

    @Override
    public int getPdfPageHeadBlockHeight() {
        return 51;
    }

    @Override
    public int getPdfPageContentBlockHeight() {
        return 72;
    }

    @Override
    public int getPdfPageContentBlockWidth() {
        return 291;
    }

    @Override
    public int getPdfPageContentRowCount() {
        return 10;
    }

    @Override
    public int getPdfPageContentColumnCount() {
        return 2;
    }

    @Override
    public int getPdfPageContentWidthDiff() {
        return 4;
    }

    @Override
    public int getPdfPageContentHeightDiff() {
        return 10;
    }

    @Override
    abstract public String extractInfo(String originMsg);

    @Override
    abstract public String preDisposeStrContent(String originContent);

    @Override
    abstract public String getOeNo();

    @Override
    abstract public List<PrintImageLocations.ImageInfo> getImageInfo(PrintImageLocations imageLocations, Rectangle rectangle);

    @Override
    abstract public boolean isContentPageNum(int pageNum);

    /**
     * 给定图片imageInfo 是否在rectangle区域
     * @return
     */
    protected boolean isInRectabgle(PrintImageLocations.ImageInfo imageInfo, Rectangle rectangle) {
        // Rectange 左上原点 与 PDImage 左下原点 需进行坐标 y 值转换
        return rectangle.contains(imageInfo.getCenterX(), this.getPdfPageHeight() - imageInfo.getCenterY());
    }

}
