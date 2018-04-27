package hsl.pojo.dto.line.ad;
import hsl.pojo.dto.ad.HslAdDTO;
import hsl.pojo.dto.programa.ProgramaContentDTO;
/**
 * @类功能说明：线路首页广告
 * @类修改者：
 * @修改日期：2015年4月22日下午4:36:57
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年4月22日下午4:36:57
 */
@SuppressWarnings("serial")
public class LineIndexAdDTO extends HslAdDTO{
	/**
	 * 线路价格
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
	 * 所属的栏目的内容
	 */
	private ProgramaContentDTO programaContent;
	
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
	public ProgramaContentDTO getProgramaContent() {
		return programaContent;
	}
	public void setProgramaContent(ProgramaContentDTO programaContent) {
		this.programaContent = programaContent;
	}
}