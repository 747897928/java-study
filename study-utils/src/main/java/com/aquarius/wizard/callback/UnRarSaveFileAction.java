package com.aquarius.wizard.callback;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;

/**
 * @author zhaoyijie
 * @since 2024/10/21 16:26
 */
@FunctionalInterface
public interface UnRarSaveFileAction {
    void unRarSaveFile(Archive archive, FileHeader fh);
}
