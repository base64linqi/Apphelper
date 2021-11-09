package com.packages.helper.PackageHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.io.File;
import java.util.List;

public class DragableLabel extends JLabel implements IBaseDragable {
    public DragableLabel() throws HeadlessException {
        // TODO Auto-generated constructor stub

        new DropTarget(this, DnDConstants.ACTION_LINK, this);
    }
    /**
     * 用于在拖拽完成后执行方法
     */
    private IDropHandler dropHandler;

    public IDropHandler getDropHandler() {
        return dropHandler;
    }

    public void setDropHandler(IDropHandler dropHandler) {
        this.dropHandler = dropHandler;
    }
    @Override
    public void afterDrop(List<File> files) {
        this.dropHandler.handleDrop(files);
    }
}
