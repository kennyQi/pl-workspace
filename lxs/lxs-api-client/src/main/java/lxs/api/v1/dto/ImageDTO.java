package lxs.api.v1.dto;

import java.util.Map;

public class ImageDTO {

	private String imageId;

	private String title;

	private String url;

	private String fileInfoJSON;

	private String img_image_bytearry;
	
	public String getImg_image_bytearry() {
		return img_image_bytearry;
	}

	public void setImg_image_bytearry(String img_image_bytearry) {
		this.img_image_bytearry = img_image_bytearry;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFileInfoJSON() {
		return fileInfoJSON;
	}

	public void setFileInfoJSON(String fileInfoJSON) {
		this.fileInfoJSON = fileInfoJSON;
	}

}
