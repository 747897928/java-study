package com.aquarius.wizard.common.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoyijie
 * @since 2024/4/23 16:25
 */
public class PdfMergeUtil {

    /**
     * 合并多个PDF文档为一个
     *
     * @param sourceFiles 要合并的PDF文件路径列表
     * @param outputFile  合并后的PDF文件输出路径
     * @throws IOException       如果读取文件或写入文件时发生错误
     * @throws DocumentException 如果创建PDF文档时发生错误
     */
    public static void mergePdfFiles(List<String> sourceFiles, String outputFile) throws IOException, DocumentException {
        Document document = new Document();
        PdfCopy copy = new PdfCopy(document, new FileOutputStream(outputFile));
        document.open();
        for (String sourceFile : sourceFiles) {
            PdfReader reader = new PdfReader(sourceFile);
            int totalPages = reader.getNumberOfPages();
            for (int page = 1; page <= totalPages; page++) {
                PdfImportedPage importedPage = copy.getImportedPage(reader, page);
                copy.addPage(importedPage);
            }
            reader.close();
        }
        document.close();
    }

    public static void main(String[] args) throws DocumentException, IOException {
        String basePath = "/Users/zhaoyijie/Library/Containers/com.tencent.xinWeChat/Data/Library/Application Support/com.tencent.xinWeChat/2.0b4.0.9/8cc2ff7ead6cffdb7a237a019c535304/Message/MessageTemp/7e5d7b097d94f95af5564f486ac64db2/File/";
        String filePath1 = basePath + "1.pdf";
        String filePath2 = basePath + "2.pdf";
        List<String> sourceFiles = new ArrayList<>();
        sourceFiles.add(filePath1);
        sourceFiles.add(filePath2);
        String outputFile = basePath + "3.pdf";
        mergePdfFiles(sourceFiles, outputFile);
    }
}
