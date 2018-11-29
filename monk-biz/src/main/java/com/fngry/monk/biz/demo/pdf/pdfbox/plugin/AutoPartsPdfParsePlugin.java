package com.fngry.monk.biz.demo.pdf.pdfbox.plugin;

import com.fngry.monk.biz.demo.pdf.pdfbox.PrintImageLocations;

import java.awt.*;
import java.util.List;

/**
 * @author gaorongyu
 * @Date 2018/11/26
 */
public interface AutoPartsPdfParsePlugin {

    /**
     * 页面宽度
     * @return
     */
    int getPdfPageWidth();

    /**
     * 页面高度
     * @return
     */
    int getPdfPageHeight();

    /**
     * head行高
     * @return
     */
    int getPdfPageHeadBlockHeight();

    /**
     * 内容块高度
     * @return
     */
    int getPdfPageContentBlockHeight();

    /**
     * 内容块宽度
     * @return
     */
    int getPdfPageContentBlockWidth();

    /**
     * 内容块行数
     * @return
     */
    int getPdfPageContentRowCount();

    /**
     * 内容块每行列数
     * @return
     */
    int getPdfPageContentColumnCount();

    /**
     * 内容行宽度调整误差
     * @return
     */
    int getPdfPageContentWidthDiff();

    /**
     * 内容行高度调整误差
     * @return
     */
    int getPdfPageContentHeightDiff();

    String extractInfo(String originMsg);

    /**
     * 提出出的字符进行预处理
     */
    String preDisposeStrContent(String originContent);

    String getOeNo();

    /**
     * 获取rectangle 区块内的图片
     * @param rectangle
     * @return
     */
    List<PrintImageLocations.ImageInfo> getImageInfo(PrintImageLocations imageLocations, Rectangle rectangle);

    boolean isContentPageNum(int pageNum);

}
