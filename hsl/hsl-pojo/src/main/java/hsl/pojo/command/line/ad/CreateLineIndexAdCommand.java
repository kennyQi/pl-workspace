package hsl.pojo.command.line.ad;
import hsl.pojo.command.ad.HslCreateAdCommand;
@SuppressWarnings("serial")
/**
 * @类功能说明：新增线路首页广告
 * @类修改者：
 * @修改日期：2015年4月22日下午4:41:47
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年4月22日下午4:41:47
 */
public class CreateLineIndexAdCommand extends HslCreateAdCommand{
	
	/**
	 * 价格
	 */
	private Double price;
	/**
	 * 线路类型
	 */
	private Integer lineType;
	/**
	 * 广告ID
	 */
	private String adId;
	/**
	 * 栏目内容ID
	 */
	private String contentId;
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getLineType() {
		return lineType;
	}
	public void setLineType(Integer lineType) {
		this.lineType = lineType;
	}
	public String getAdId() {
		return adId;
	}
	public void setAdId(String adId) {
		this.adId = adId;
	}
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	
}
