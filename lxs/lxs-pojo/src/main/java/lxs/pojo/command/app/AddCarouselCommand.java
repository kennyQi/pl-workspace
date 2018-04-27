package lxs.pojo.command.app;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class AddCarouselCommand extends BaseCommand {
	private String imageInfo;
	/**
	 * 图片名字
	 */
	private String fileName;
	/**
	 * 图片地址
	 */
	private String imagePath;
	
	private String note;
	
	private Integer linetype;
	
	private String carouselAction;
	
	private String carouselActionCheck;
	/**
	 * 轮播图位置
	 */
	private Integer carouselLevel;
	

	public String getImageInfo() {
		return imageInfo;
	}

	public void setImageInfo(String imageInfo) {
		this.imageInfo = imageInfo;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getLinetype() {
		return linetype;
	}

	public void setLinetype(Integer linetype) {
		this.linetype = linetype;
	}

	public String getCarouselAction() {
		return carouselAction;
	}

	public void setCarouselAction(String carouselAction) {
		this.carouselAction = carouselAction;
	}

	public String getCarouselActionCheck() {
		return carouselActionCheck;
	}

	public void setCarouselActionCheck(String carouselActionCheck) {
		this.carouselActionCheck = carouselActionCheck;
	}

	public Integer getCarouselLevel() {
		return carouselLevel;
	}

	public void setCarouselLevel(Integer carouselLevel) {
		this.carouselLevel = carouselLevel;
	}

}
