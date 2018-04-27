package hsl.domain.model.xl.ad;
import java.util.UUID;

import hg.common.component.BaseModel;
import hsl.domain.model.M;
import hsl.domain.model.programa.ProgramaContent;
import hsl.pojo.command.line.ad.CreateLineIndexAdCommand;
import hsl.pojo.command.line.ad.ModifyLineIndexAdCommand;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 * @类功能说明：线路首页广告
 * @类修改者：
 * @修改日期：2015年4月22日下午3:32:56
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年4月22日下午3:32:56
 */
@Entity
@Table(name=M.TABLE_PREFIX_HSL_XL+"LINE_INDEX_AD")
public class LineIndexAd extends BaseModel{
	private static final long serialVersionUID = 3163163553906884682L;
	/**
	 * 线路价格
	 */
	@Column(name = "PRICE", columnDefinition = M.MONEY_COLUM)
	private Double price;
	/**
	 * 线路类型
	 */
	@Column(name = "LINE_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer lineType;
	/**
	 * 周边游
	 */
	public static final Integer LINETYPE_RIM=1;
	/**
	 * 跟团游
	 */
	public static final Integer LINETYPE_GROUP=2;
	/**
	 * 广告ID
	 */
	@Column(name = "AD_ID", length = 64)
	private String adId;
	/**
	 * 所属的栏目的内容
	 */
	@ManyToOne
	@JoinColumn(name="content_ID")
	private ProgramaContent programaContent;
	/**
	 * @方法功能说明：创建线路首页广告
	 * @修改者名字：chenxy
	 * @修改时间：2015年4月22日下午5:35:39
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void createIndexAd(CreateLineIndexAdCommand command){
		this.setId(UUID.randomUUID().toString());
		this.setPrice(command.getPrice());
		this.setAdId(command.getAdId());
		this.setLineType(command.getLineType());
		ProgramaContent content=new ProgramaContent();
		content.setId(command.getContentId());
		this.setProgramaContent(content);
	}
	/**
	 * @方法功能说明：修改线路首页广告
	 * @修改者名字：chenxy
	 * @修改时间：2015年4月22日下午5:52:32
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void modifyLineIndexAd(ModifyLineIndexAdCommand command){
		this.setPrice(command.getPrice());
		this.setAdId(command.getAdId());
		this.setLineType(command.getLineType());
		ProgramaContent content=new ProgramaContent();
		content.setId(command.getContentId());
		this.setProgramaContent(content);
	}
	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

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

	public ProgramaContent getProgramaContent() {
		return programaContent;
	}

	public void setProgramaContent(ProgramaContent programaContent) {
		this.programaContent = programaContent;
	}
}