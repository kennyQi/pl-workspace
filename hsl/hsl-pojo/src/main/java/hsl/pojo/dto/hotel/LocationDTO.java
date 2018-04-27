package hsl.pojo.dto.hotel;
/**
 * @类功能说明：图片位置DTO
 * @类修改者：
 * @修改日期：2015年7月13日上午10:30:36
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年7月13日上午10:30:36
 */
public class LocationDTO {
	/**
	 * 链接地址
	 */
    protected String url;
    /**
     * 水印
     */
    protected String waterMark;
    /**
     * 大小类型
     */
    protected int sizeType;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getWaterMark() {
		return waterMark;
	}
	public void setWaterMark(String waterMark) {
		this.waterMark = waterMark;
	}
	public int getSizeType() {
		return sizeType;
	}
	public void setSizeType(int sizeType) {
		this.sizeType = sizeType;
	}
}
