package com.fngry.monk.biz.demo.pdf.pdfbox;

import com.fngry.monk.biz.demo.pdf.pdfbox.plugin.AutoPartsPdfParsePlugin;
import com.fngry.monk.biz.demo.pdf.pdfbox.plugin.PdfParsePluginManager;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 *
 * 按区块提取pdf内容
 *
 * @author gaorongyu
 * @Date 2018/11/15
 */
public class PDFRectangle {

    private String inputPdfFile = null;

    private String outPutPath = null;



    /**
     * 划分的区块
     */
    private Map<Integer, Rectangle> rectangleMap = new HashMap<>();

    /**
     * 重复oe码记录看
     */
    private Map<String, AtomicInteger> duplicateOeSeqMap = new HashMap<>();

    /**
     * 异常块信息 eg 可能pdf该块内容为空
     */
    private StringBuffer exContentBlock = new StringBuffer();

    /**
     * 结果信息
     */
    private StringBuffer resultMsg = new StringBuffer();


    public PDFRectangle(String inputPdfFile, String outPutPath) {
        this.inputPdfFile = inputPdfFile;
        this.outPutPath = outPutPath;
    }

    public void stripPdf(String bizCode) throws IOException {
        AutoPartsPdfParsePlugin plugin = PdfParsePluginManager.getPlugin(bizCode);

        PDDocument document = PDDocument.load(new File(this.inputPdfFile));
        PDFTextStripperByArea stripper = new PDFTextStripperByArea();
        stripper.setSortByPosition(true);

        PDPageTree allPages = document.getDocumentCatalog().getPages();

        for (int i = 0; i < allPages.getCount(); i++) {
            int pageNum = i + 1;
            if (plugin.isContentPageNum(pageNum)) {
                PDPage page = allPages.get(i);
                stripPage(stripper, page, pageNum, plugin);
            }
        }

        printParseResult();
    }

    private void printParseResult() {
        System.out.println("================================");
        System.out.println("resultMsg:");
        System.out.println(resultMsg);

        System.out.println("exception content: ");
        System.out.println(exContentBlock);

        System.out.println("duplicateOeSeqMap: ");
        duplicateOeSeqMap.entrySet().stream().forEach(e -> System.out.println(e.getKey()));
    }

    /**
     *
     * 提取每个页面
     *
     * @param stripper
     * @param page
     * @param pageNum
     * @throws IOException
     */
    private void stripPage(PDFTextStripperByArea stripper, PDPage page, int pageNum, AutoPartsPdfParsePlugin plugin)
            throws IOException {
        System.out.println("page " + pageNum + ":\n ");

        // 1. 划分页面区域 按区域提取字符
        divideRectangle(stripper, plugin);
        stripper.extractRegions(page);

        // 2. 解析图片
        PrintImageLocations imageLocations = new PrintImageLocations(Constants.PAGE_NAME_PREFIX + pageNum);
        imageLocations.processPage(page);

        // 3. 按图片所属区域 做 oe 和图片对应
        extractImageInPdf(stripper, pageNum, plugin, imageLocations);
    }

    private void extractImageInPdf(PDFTextStripperByArea stripper, int pageNum, AutoPartsPdfParsePlugin plugin, PrintImageLocations imageLocations) {
        int contentBlockCount = plugin.getPdfPageContentRowCount() * plugin.getPdfPageContentColumnCount();
        for (int i = 0; i < contentBlockCount; i++) {
            String blockContent = stripper.getTextForRegion(Constants.REGION_NAME_PREFIX + i);

            // 预处理
            String preDisposeContent = plugin.preDisposeStrContent(blockContent);
            // 提取信息
            String content = plugin.extractInfo(preDisposeContent);

            if (content == null || "".equals(content.trim())) {
                exContentBlock.append("page: ").append(pageNum).append(" block: ").append(i);
                exContentBlock.append("origin content: ").append(blockContent);
                continue;
            }
            resultMsg.append("\n").append(pageNum).append(Constants.RESULT_CONTENT_SEPARATOR);
            resultMsg.append(content);

            System.out.println(Constants.REGION_NAME_PREFIX + i + ":\n " + content);
            // 找图片
            Rectangle rectangle = rectangleMap.get(i);
            List<PrintImageLocations.ImageInfo> imageInfoList = plugin.getImageInfo(imageLocations, rectangle);

            if (imageInfoList != null) {
                imageInfoList.stream().forEach(e -> saveImageInfo(e, plugin, pageNum));
                String imageFileInfo = imageInfoList.stream().map(e -> e.getImageFullName())
                        .collect(Collectors.joining(","));
                resultMsg.append(Constants.RESULT_CONTENT_SEPARATOR).append(imageFileInfo);
            }
        }
    }

    private void divideRectangle(PDFTextStripperByArea stripper, AutoPartsPdfParsePlugin plugin) {
        for (int i = 0; i < plugin.getPdfPageContentRowCount(); i++) {
            // 自定义区块 坐标原点在左上角
            for (int j = 0; j < plugin.getPdfPageContentColumnCount(); j++) {
                int rectX = plugin.getPdfPageContentBlockWidth() * (j % plugin.getPdfPageContentColumnCount())
                        + plugin.getPdfPageContentWidthDiff();
                int rectY = plugin.getPdfPageHeadBlockHeight() + plugin.getPdfPageContentBlockHeight() * i;

                Rectangle pageRect = new Rectangle(rectX, rectY, plugin.getPdfPageContentBlockWidth(),
                        plugin.getPdfPageContentBlockHeight() + plugin.getPdfPageContentHeightDiff());

                int seq = i * plugin.getPdfPageContentColumnCount() + j;
                stripper.addRegion(Constants.REGION_NAME_PREFIX + seq, pageRect);
                rectangleMap.put(seq, pageRect);
            }
        }
    }

    private void saveImageInfo(PrintImageLocations.ImageInfo imageInfo, AutoPartsPdfParsePlugin plugin, int pageNum) {
        // 以oe码命名图片文件
        if (imageInfo != null && plugin.getOeNo() != null) {

            String fileName =  plugin.getOeNo();
            imageInfo.setFileName(fileName);

            String path = this.outPutPath + "/" + Constants.PAGE_NAME_PREFIX + pageNum;

            try {
                imageInfo.writeImageToFile(path);
            } catch (FileAlreadyExistsException ex) {
                // 图片重复oe码处理
                ex.printStackTrace();

                AtomicInteger duplicateOeSeq = duplicateOeSeqMap.get(fileName);
                if (duplicateOeSeq == null) {
                    duplicateOeSeqMap.put(fileName, new AtomicInteger(0));
                }
                duplicateOeSeq = duplicateOeSeqMap.get(fileName);
                imageInfo.setFileName(fileName + "_" + duplicateOeSeq.incrementAndGet());
                try {
                    imageInfo.writeImageToFile(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        String imageStr = imageInfo != null ? imageInfo.getImageFullName() : null;
        System.out.println(plugin.getOeNo() + ", referenceImage " + imageStr);
    }


}
