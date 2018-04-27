package lxs.pojo.command.app;

import hg.common.component.BaseCommand;
@SuppressWarnings("serial")
public class UpdateCarouselCommand extends BaseCommand {
	private String Id;
	
	private Integer status;
	
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

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
}
