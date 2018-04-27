package hsl.pojo.command;

import hg.common.component.BaseCommand;
@SuppressWarnings("serial")
public class UploadImageCommand extends BaseCommand{
	/**
	 * 图片流
	 */
	private byte[] bytes;
	
	/**
	 * 图片类型
	 */
	private String imageType;
	
	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
}
