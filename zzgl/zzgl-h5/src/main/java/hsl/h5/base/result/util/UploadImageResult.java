package hsl.h5.base.result.util;
import hsl.h5.base.result.api.ApiResult;
public class UploadImageResult extends ApiResult{
	private String imagePath;

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
}
