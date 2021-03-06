package com.packages.helper.PackageHelper;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

public class DragFrame extends JFrame implements DropTargetListener{
	
	private IDropHandler dropHandler;
	
	

	public IDropHandler getDropHandler() {
		return dropHandler;
	}

	public void setDropHandler(IDropHandler dropHandler) {
		this.dropHandler = dropHandler;
	}

	public DragFrame() throws HeadlessException {
		// TODO Auto-generated constructor stub

		new DropTarget(this, DnDConstants.ACTION_COPY, this);
	}

	public DragFrame(GraphicsConfiguration arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public DragFrame(String arg0) throws HeadlessException {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public DragFrame(String arg0, GraphicsConfiguration arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragExit(DropTargetEvent dte) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drop(DropTargetDropEvent dtde) {
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
                if (this.dropHandler != null) {
                	dropHandler.handleDrop(files);
                }
            }else {
                dtde.rejectDrop();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (UnsupportedFlavorException ufe) {
            ufe.printStackTrace();
        }
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub

	}


}
