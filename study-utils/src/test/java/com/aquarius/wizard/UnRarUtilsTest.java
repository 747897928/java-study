package com.aquarius.wizard;

import com.aquarius.wizard.callback.UnRarSaveFileAction;
import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoyijie
 * @since 2024/10/21 16:04
 */
public class UnRarUtilsTest {

    @Test
    public void test1() throws RarException, IOException {
        String inputRarFileName = "/Users/zhaoyijie/Downloads/RO143170.rar";
        String outFilePath = "/Users/zhaoyijie/Downloads";
        String password = "xddownload";
        UnRarUtils.unrarWithPassWord(inputRarFileName, outFilePath, password);
    }

    @Test
    public void test2() throws RarException, IOException {

        File baseDirFile = new File("/Users/zhaoyijie/Downloads/install/");
        String outFilePath = "/Users/zhaoyijie/Downloads/install/output";
        String password = "xddownload";

        UnRarSaveFileAction saveFileAction = new UnRarSaveFileAction() {

            @Override
            public void unRarSaveFile(Archive archive, FileHeader fh) {
                String fileName = fh.getFileName();
                if (fh.isDirectory()) {
                    System.out.println("directory name = " + fileName);
                    return;
                }
                if (fileName != null && fileName.trim().length() > 0) {
                    System.out.println("fileName = " + fileName);
                    if (fileName.contains("/")) {
                        fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
                        System.out.println("substring fileName = " + fileName);
                    }
                    if (fileName.contains("\\")) {
                        fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
                        System.out.println("substring fileName = " + fileName);
                    }
                    String saveFileName = outFilePath + File.separator + fileName;
                    File saveFile = new File(saveFileName);
                    File parent = saveFile.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(saveFile);
                        archive.extractFile(fh, fos);
                        fos.flush();
                    } catch (FileNotFoundException e) {
                        System.err.println(e);
                    } catch (RarException e) {
                        System.err.println(e);
                    } catch (IOException e) {
                        System.err.println(e);
                    } finally {
                        if (fos != null) {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                System.err.println(e);
                            }
                        }

                    }
                }

            }
        };

        List<String> errorFileNames = new ArrayList<>();
        for (File inputRarFile : baseDirFile.listFiles()) {
            if (!inputRarFile.getName().endsWith(".rar")) {
                System.out.println("is not rar file = " + inputRarFile.getName());
                continue;
            }
            if (inputRarFile.isDirectory()) {
                System.out.println("is directory = " + inputRarFile.getName());
                continue;
            }
            try {
                UnRarUtils.unrarWithPassWord(inputRarFile, password, saveFileAction);
            } catch (Exception e) {
                e.printStackTrace();
                errorFileNames.add(inputRarFile.getName());
            }
        }
        System.out.println("errorFileNames = " + errorFileNames);

    }

}