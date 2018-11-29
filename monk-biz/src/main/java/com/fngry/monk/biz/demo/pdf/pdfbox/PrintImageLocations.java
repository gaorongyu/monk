package com.fngry.monk.biz.demo.pdf.pdfbox;
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.pdfbox.contentstream.PDFStreamEngine;
import org.apache.pdfbox.contentstream.operator.DrawObject;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.contentstream.operator.state.*;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * This is an example on how to get the x/y coordinates of image locations.
 *
 * @author Ben Litchfield
 */
public class PrintImageLocations extends PDFStreamEngine
{

    private List<ImageInfo> imageInfoList = new ArrayList<>();

    private String pageName = "default";

    /**
     * Default constructor.
     *
     * @throws IOException If there is an error loading text stripper properties.
     */
    public PrintImageLocations() throws IOException
    {
        addOperator(new Concatenate());
        addOperator(new DrawObject());
        addOperator(new SetGraphicsStateParameters());
        addOperator(new Save());
        addOperator(new Restore());
        addOperator(new SetMatrix());
    }

    public PrintImageLocations(String pageName) throws IOException {
        this();
        this.pageName = pageName;
    }

    /**
     * This will print the documents data.
     *
     * @param args The command line arguments.
     *
     * @throws IOException If there is an error parsing the document.
     */
    public static void main( String[] args ) throws IOException
    {
//        if( args.length != 1 )
//        {
//            usage();
//        }
//        else
//        {
            try (PDDocument document = PDDocument.load(new File("/Users/gaorongyu/Downloads/092517323115_cleaned.pdf")))
            {
                PrintImageLocations printer = new PrintImageLocations();
                int pageNum = 0;
                for( PDPage page : document.getPages() )
                {
                    pageNum++;
                    if (pageNum == 7) {
                        System.out.println( "==============Processing page: " + pageNum );
                        printer.processPage(page);
                    }
                }
            }
//        }
    }

    /**
     * This is used to handle an operation.
     *
     * @param operator The operation to perform.
     * @param operands The list of arguments.
     *
     * @throws IOException If there is an error processing the operation.
     */
    @Override
    protected void processOperator( Operator operator, List<COSBase> operands) throws IOException
    {
        String operation = operator.getName();
        if( "Do".equals(operation) )
        {
            COSName objectName = (COSName) operands.get( 0 );
            PDXObject xobject = getResources().getXObject( objectName );
            if( xobject instanceof PDImageXObject)
            {
                PDImageXObject image = (PDImageXObject)xobject;

                if ("png".equals(image.getSuffix())) {
                    return;
                }

                int imageWidth = image.getWidth();
                int imageHeight = image.getHeight();

//                System.out.println("Found image [" + objectName.getName() + "]");

                Matrix ctmNew = getGraphicsState().getCurrentTransformationMatrix();
                float imageXScale = ctmNew.getScalingFactorX();
                float imageYScale = ctmNew.getScalingFactorY();

                // position in user space units. 1 unit = 1/72 inch at 72 dpi
//                System.out.println("position in PDF = " + ctmNew.getTranslateX() + ", " + ctmNew.getTranslateY() + " in user space units");
//                // raw size in pixels
//                System.out.println("raw image size  = " + imageWidth + ", " + imageHeight + " in pixels");
//                // displayed size in user space units
//                System.out.println("displayed size  = " + imageXScale + ", " + imageYScale + " in user space units");


                // displayed size in inches at 72 dpi rendering
//                imageXScale /= 72;
//                imageYScale /= 72;
//                System.out.println("displayed size  = " + imageXScale + ", " + imageYScale + " in inches at 72 dpi rendering");
//                // displayed size in millimeters at 72 dpi rendering
//                imageXScale *= 25.4;
//                imageYScale *= 25.4;
//                System.out.println("displayed size  = " + imageXScale + ", " + imageYScale + " in millimeters at 72 dpi rendering");
                System.out.println();

//                BufferedImage bufferImage = image.getImage();
//                ByteArrayOutputStream os = new ByteArrayOutputStream();
//                ImageIO.write(bufferImage, image.getSuffix(), os);
//
//                String fileName = this.pageName + "_" + objectName.getName() + "." + image.getSuffix();
//                Path outputFile = new File("/Users/gaorongyu/Downloads/temp/"
//                        + this.pageName + "_" + objectName.getName() + "." + image.getSuffix()).toPath();

                ImageInfo imageInfo = new ImageInfo((int) ctmNew.getTranslateX(), (int) ctmNew.getTranslateY(),
                        (int) imageXScale, (int) imageYScale);
                imageInfo.setImage(image);
                imageInfo.setObjectName(objectName);

                imageInfoList.add(imageInfo);
//                java.nio.file.Files.copy(new ByteArrayInputStream(os.toByteArray()), outputFile);

            }
            else if(xobject instanceof PDFormXObject)
            {
                PDFormXObject form = (PDFormXObject)xobject;
                showForm(form);
            }
        }
        else
        {
            super.processOperator( operator, operands);
        }
    }

    /**
     * This will print the usage for this document.
     */
    private static void usage() {
        System.err.println( "Usage: java " + PrintImageLocations.class.getName() + " <input-pdf>" );
    }

    public List<ImageInfo> getImageList() {
        return this.imageInfoList;
    }

    /**
     * 图片信息
     */
    public static class ImageInfo {

        private String fileName;

        private COSName objectName;

        private PDImageXObject image;

        // 图片坐标系原点在左下角
        private int x;

        private int y;

        private int width;

        private int height;

        public ImageInfo(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public int getCenterX() {
            return x + width / 2;
        }

        public int getCenterY() {
            return y + height / 2;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public COSName getObjectName() {
            return objectName;
        }

        public void setObjectName(COSName objectName) {
            this.objectName = objectName;
        }

        public PDImageXObject getImage() {
            return image;
        }

        public void setImage(PDImageXObject image) {
            this.image = image;
        }

        public String getImageFullName() {
            String fullName = this.fileName + "." + pasrseImageFormat(this.image.getSuffix());
            return fullName.replace("/", "-");        }

        public void writeImageToFile(String path) throws IOException {
            BufferedImage bufferImage = image.getImage();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bufferImage, pasrseImageFormat(image.getSuffix()), os);

            File outPutPath = new File(path);
            if (!outPutPath.exists()) {
                outPutPath.mkdir();
            }

            Path outputFile = new File(path + "/" + this.getImageFullName()).toPath();
            java.nio.file.Files.copy(new ByteArrayInputStream(os.toByteArray()), outputFile);
        }

        private String pasrseImageFormat(String imageSuffix) {
            if ("jpx".equals(imageSuffix)) {
                return "jpeg";
            }
            return imageSuffix;
        }

    }

}