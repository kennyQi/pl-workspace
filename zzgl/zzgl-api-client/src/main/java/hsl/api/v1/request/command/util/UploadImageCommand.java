package hsl.api.v1.request.command.util;

import java.io.FileInputStream;

import hsl.api.base.ApiPayload;

@SuppressWarnings("serial")
public class UploadImageCommand extends ApiPayload{
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
