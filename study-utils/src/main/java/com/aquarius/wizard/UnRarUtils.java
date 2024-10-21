package com.aquarius.wizard;

import com.aquarius.wizard.callback.UnRarSaveFileAction;
import com.aquarius.wizard.callback.UnrarProcessMonitor;
import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * rar解压工具
 *
 * @author zhaoyijie
 * @since 2022/3/29 09:33
 */
public class UnRarUtils {

    private UnRarUtils() {
        throw new UnsupportedOperationException("Construct UnGzTarUtils");
    }

    /**
     * @param rarFileName rar file name
     * @param outFilePath output file path
     */
    public static void unrar(String rarFileName, String outFilePath) throws RarException, IOException {
        Archive archive = new Archive(new File(rarFileName), new UnrarProcessMonitor(rarFileName));
        if (archive.isEncrypted()) {
            throw new IOException(rarFileName + " IS ENCRYPTED!");
        }
        List<FileHeader> files = archive.getFileHeaders();
        for (FileHeader fh : files) {
            if (fh.isEncrypted()) {
                throw new IOException(rarFileName + " IS ENCRYPTED!");
            }
            String fileName = fh.getFileName();
            if (fileName != null && fileName.trim().length() > 0) {
                String saveFileName = outFilePath + File.separator + fileName;
                File saveFile = new File(saveFileName);
                File parent = saveFile.getParentFile();
                if (!parent.exists()) {
                    parent.mkdirs();
                }
                if (!saveFile.exists()) {
                    saveFile.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(saveFile);
                try {
                    archive.extractFile(fh, fos);
                    fos.flush();
                } catch (RarException e) {
                    System.err.println(e);
                } finally {
                    fos.close();
                }
            }
        }
    }


    public static void unrarWithPassWord(String rarFileName, String outFilePath, String password) throws RarException, IOException {
        unrarWithPassWord(new File(rarFileName), outFilePath, password);
    }

    public static void unrarWithPassWord(File rarFile, String outFilePath, String password) throws RarException, IOException {
        Archive archive = new Archive(rarFile, new UnrarProcessMonitor(rarFile.getAbsolutePath()), password);
        System.out.println("archive.isEncrypted() = " + archive.isEncrypted());
        List<FileHeader> files = archive.getFileHeaders();
        for (FileHeader fh : files) {
            String fileName = fh.getFileName();
            // 获取文件名并将 Windows 路径分隔符 '\' 替换为系统默认的分隔符
            fileName = fileName.replace("\\", File.separator);
            System.out.println("fileName = " + fileName);
            if (fh.isDirectory()) {
                System.out.println("directory name = " + fileName);
                // 如果是目录，创建目录
                File dir = new File(outFilePath, fileName);
                dir.mkdirs();
            } else {
                if (fileName != null && fileName.trim().length() > 0) {
                    String saveFileName = outFilePath + File.separator + fileName;
                    File saveFile = new File(saveFileName);
                    File parent = saveFile.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    FileOutputStream fos = new FileOutputStream(saveFile);
                    try {
                        archive.extractFile(fh, fos);
                        fos.flush();
                    } catch (RarException e) {
                        System.err.println(e);
                    } finally {
                        fos.close();
                    }
                }
            }
        }
    }

    public static void unrarWithPassWord(File rarFile, String password, UnRarSaveFileAction saveFileAction) throws RarException, IOException {
        System.out.println("rarFile = " + rarFile);
        Archive archive = new Archive(rarFile, new UnrarProcessMonitor(rarFile.getAbsolutePath()), password);
        List<FileHeader> files = archive.getFileHeaders();
        for (FileHeader fh : files) {
            saveFileAction.unRarSaveFile(archive, fh);
        }
    }


    public static void unrarWithPassWord(String rarFileName, String password, UnRarSaveFileAction saveFileAction) throws RarException, IOException {
        unrarWithPassWord(new File(rarFileName), password, saveFileAction);
    }
}
