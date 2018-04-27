package lxs.pojo.command.app;

import java.util.List;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class LinkPageAddCommand extends BaseCommand {
	
	private List<String> imageInfo;
	
	private List<String> linkpageid;
	
	
	/**
	 * 图片名字
	 */
	private List<String> fileName;
	
	
	/**
	 * 图片地址
	 */
	private List<String> imagePath;
	
	private int sort;


	public List<String> getImageInfo() {
		return imageInfo;
	}


	public void setImageInfo(List<String> imageInfo) {
		this.imageInfo = imageInfo;
	}


	public List<String> getFileName() {
		return fileName;
	}


	public void setFileName(List<String> fileName) {
		this.fileName = fileName;
	}


	public List<String> getImagePath() {
		return imagePath;
	}


	public void setImagePath(List<String> imagePath) {
		this.imagePath = imagePath;
	}


	public int getSort() {
		return sort;
	}


	public void setSort(int sort) {
		this.sort = sort;
	}


	public List<String> getLinkpageid() {
		return linkpageid;
	}


	public void setLinkpageid(List<String> linkpageid) {
		this.linkpageid = linkpageid;
	}
	
	


}