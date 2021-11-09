package com.packages.helper.PackageHelper;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public interface IBaseDragable extends DropTargetListener {

    @Override
    public default void dragEnter(DropTargetDragEvent dtde) {
        // TODO Auto-generated method stub
    }

    @Override
    public default void dragExit(DropTargetEvent dte) {
        // TODO Auto-generated method stub

    }

    @Override
    public default void dragOver(DropTargetDragEvent dtde) {
        // TODO Auto-generated method stub

    }

    @Override
    public default void drop(DropTargetDropEvent dtde) {
        // TODO Auto-generated method stub
        try {
            Transferable tr = dtde.getTransferable();

            if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                dtde.acceptDrop(DnDConstants.ACTION_COPY);
                System.out.println("file cp");
                List list = (List) (dtde.getTransferable()
                        .getTransferData(DataFlavor.javaFileListFlavor));
                Iterator iterator = list.iterator();
                List<File> files = new ArrayList<File>();
                while (iterator.hasNext()) {
                    File f = (File) iterator.next();
                    files.add(f);
                    System.out.println(f.getName());
                }
                dtde.dropComplete(true);
                afterDrop(files);
            }else {
                dtde.rejectDrop();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (UnsupportedFlavorException ufe) {
            ufe.printStackTrace();
        }
    }

    /**
     * 在拖拽发生之后的处理, 所有实现此方法的类都要实现此方法，这样就可以在拖拽完成后执行方法
     */
    public void afterDrop(List<File> files);

    @Override
    public default void dropActionChanged(DropTargetDragEvent dtde) {
        // TODO Auto-generated method stub

    }
}
