package com.packages.helper.PackageHelper;

import java.awt.HeadlessException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.io.File;
import java.util.List;

import javax.swing.JFrame;

/**
 * 实现拖拽接口，拖拽后，执行afterDrop方法
 */
public class DragFrame extends JFrame implements IBaseDragable {

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

	public DragFrame() throws HeadlessException {
		// TODO Auto-generated constructor stub

		new DropTarget(this, DnDConstants.ACTION_LINK, this);
	}

	@Override
	public void afterDrop(List<File> files) {
		this.dropHandler.handleDrop(files);
	}
}
