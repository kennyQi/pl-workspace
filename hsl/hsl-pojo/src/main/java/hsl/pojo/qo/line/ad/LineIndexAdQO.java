package hsl.pojo.qo.line.ad;
import hg.common.component.BaseQo;
import hsl.pojo.qo.mp.HslADQO;
/**
 * @类功能说明：线路首页广告
 * @类修改者：
 * @修改日期：2015年4月22日下午4:10:24
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年4月22日下午4:10:24
 */
@SuppressWarnings("serial")
public class LineIndexAdQO extends HslADQO{
	
	private String programaId;
	/**
	 * 线路类型
	 */
	private Integer lineType;
	/**
	 * 栏目内容ID
	 */
	private String contentId;
	/**
	 * 广告ID
	 */
	private String adId;
	
	private Boolean isFecthContent=false;

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public Integer getLineType() {
		return lineType;
	}

	public void setLineType(Integer lineType) {
		this.lineType = lineType;
	}

	public Boolean getIsFecthContent() {
		return isFecthContent;
	}

	public void setIsFecthContent(Boolean isFecthContent) {
		this.isFecthContent = isFecthContent;
	}

	public String getProgramaId() {
		return programaId;
	}

	public void setProgramaId(String programaId) {
		this.programaId = programaId;
	}
	
}
