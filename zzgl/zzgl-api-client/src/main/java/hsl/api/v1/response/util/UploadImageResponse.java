package hsl.api.v1.response.util;

import hsl.api.base.ApiResponse;

public class UploadImageResponse extends ApiResponse{
	private String imagePath;

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
}