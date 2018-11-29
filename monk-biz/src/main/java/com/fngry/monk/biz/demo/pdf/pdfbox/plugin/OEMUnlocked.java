package com.fngry.monk.biz.demo.pdf.pdfbox.plugin;

import com.fngry.monk.biz.demo.pdf.pdfbox.Constants;
import com.fngry.monk.biz.demo.pdf.pdfbox.PrintImageLocations;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gaorongyu
 * @Date 2018/11/26
 */
public class OEMUnlocked extends AbstractAutoPartsPdfParsePlugin {

    private static final String YEAR_PATTERN = "[0-9]{2}|[0-9]{2}-[0-9]{2}|;[0-9]{2}|[0-9]{4}";

    private static final String BRAND_PATTERN = "[A-Z]+";

    private static final int MIN_CONTENT_LINES = 3;

    private static final int MIN_OE_LENGTH = 6;

    private String oeNo;


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
        return 54;
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
    public String extractInfo(String originMsg) {
        if (originMsg == null || "".equals(originMsg.trim())) {
            return null;
        }

        String[] items = originMsg.split("\n");
        List<String> itemList = Arrays.asList(items);

        if (items.length < MIN_CONTENT_LINES) {
            return originMsg;
        }

        // 产品名称 和 oe 码在同一行
        String productAndOe = itemList.get(0);

        // 长度 大于 6 才认为是完成oe  避免 WL51-14-240 A 的情况误认为A
        int oeSeparatorIndex = productAndOe.length();
        String temp = productAndOe;
        while (productAndOe.length() - 1 - oeSeparatorIndex < MIN_OE_LENGTH) {
            oeSeparatorIndex = temp.lastIndexOf(" ");
            temp = temp.substring(0, oeSeparatorIndex);
        }

        // 产品名称 去掉前面的序号
        String productNameStr = productAndOe.substring(0, oeSeparatorIndex);
        int productNameSeparatorIndex = productNameStr.indexOf(" ");
        String productName = productNameStr.substring(productNameSeparatorIndex + 1);

        String oe = productAndOe.substring(oeSeparatorIndex + 1);
        this.oeNo = oe;

        // 品牌
        int brandSeparatorIndex = itemList.get(1).lastIndexOf(" ");
        String carBrand = itemList.get(1).substring(brandSeparatorIndex + 1);
        if (!carBrand.matches(BRAND_PATTERN)) {
            carBrand = "";
        }

        // 车型
        String carModel = itemList.get(2);
        if (carModel.indexOf(oe) != -1) {
            carModel = "";
        }

        // 年款
        String year = getYear(itemList);

        String result = Arrays.asList(productName, carBrand, carModel, year, oe).stream()
                .collect(Collectors.joining(Constants.RESULT_CONTENT_SEPARATOR));

        return result;
    }


    @Override
    public String preDisposeStrContent(String originContent) {
        if (originContent == null || "".equals(originContent.trim())) {
            return null;
        }

        String[] lines = originContent.split("\n");
        String content = Arrays.asList(lines).stream()
                .map(e -> e.replace("'", ""))
                .collect(Collectors.joining("\n"));

        return content;
    }

    @Override
    public String getOeNo() {
        return this.oeNo;
    }

    @Override
    public List<PrintImageLocations.ImageInfo> getImageInfo(PrintImageLocations imageLocations, Rectangle rectangle) {
        return imageLocations.getImageList().stream()
                .filter(e -> isInRectabgle(e, rectangle))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isContentPageNum(int pageNum) {
        return true;
    }

    private String getYear(List<String> itemList) {
        String year = itemList.stream().filter(e -> e.matches(YEAR_PATTERN)).findFirst().orElse(null);
        return year != null ? year.replace(";", "") : "";
    }

}
