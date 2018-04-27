package hgria.admin.common.hgUtil;


public class ImageInfo {

	/**
	 * 图片名称
	 */
	private String fileName;
	
	/**
	 * 图片类型
	 */
	private String fileType;

	/**
	 * 图片高度
	 */
	private int height;

	/**
	 * 图片宽度
	 */
	private int width;

	/**
	 * 图片文件大小
	 */
	private Long fileSize;

	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

}