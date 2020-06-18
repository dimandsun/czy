package com.czy.fx.myfx;

import com.czy.util.ListUtil;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.util.List;

/**
 * @author chenzy
 * @description
 * @since 2020-05-23
 */
public class MyFileChooser {
    private FileChooser fileChooser;
    private Window window;

    public MyFileChooser() {
        this.fileChooser = new FileChooser();
    }
    public MyFileChooser(String title) {
        this.fileChooser = new FileChooser();
        fileChooser.setTitle(title);
    }
    public MyFileChooser(String title,String dir) {
        this.fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        defaultDir(dir);
    }

    public File chooseFile() {
        if (window == null) {
            window = new Stage();
        }
        return fileChooser.showOpenDialog(window);
    }
    public List<File> chooseFiles() {
        if (window == null) {
            window = new Stage();
        }
        return fileChooser.showOpenMultipleDialog(window);
    }
    public File saveFile(){
        if (window == null) {
            window = new Stage();
        }
        var file= fileChooser.showSaveDialog(window);
        return file;

    }
    /**
     * 默认打开路径
     *
     * @param dir
     * @return
     */
    public MyFileChooser defaultDir(String dir) {
        fileChooser.setInitialDirectory(new File(dir));
        return this;
    }

    /**
     * 保存文件时所默认的文件名
     * @return
     */
    public MyFileChooser defaultFileName(String fileName) {
        fileChooser.setInitialFileName(fileName);
        return this;
    }

    /**
     * 文件过滤器
     * @param dec
     * @param extensions
     * @return
     */
    public MyFileChooser fileFilter(String dec, String... extensions) {
        if (ListUtil.isEmpty(extensions)){
            return this;
        }
//        new FileChooser.ExtensionFilter("图片类型", "*.jpg", "*.png")
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(dec,extensions));
        return this;
    }
}
