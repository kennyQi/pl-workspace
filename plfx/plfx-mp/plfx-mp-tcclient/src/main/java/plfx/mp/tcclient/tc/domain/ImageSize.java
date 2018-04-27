package plfx.mp.tcclient.tc.domain;

public class ImageSize {
	/**
	 * 尺寸代码
	 */
	private String sizeCode  ;
	/**
	 * 尺寸
	 */
	private String size;
	/**
	 * 是否为默认的
	 */
	private Boolean isDefault;
	public String getSizeCode() {
		return sizeCode;
	}
	public void setSizeCode(String sizeCode) {
		this.sizeCode = sizeCode;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public Boolean getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	
}
