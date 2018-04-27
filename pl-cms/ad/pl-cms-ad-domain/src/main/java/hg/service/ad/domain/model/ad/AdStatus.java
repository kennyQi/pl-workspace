package hg.service.ad.domain.model.ad;

import hg.system.model.M;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

/**
 * @类功能说明：广告状态
 * @类修改者：
 * @修改日期：2014年12月11日下午3:57:39
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年12月11日下午3:57:39
 * 
 */
@Embeddable
public class AdStatus {
	/**
	 * 按广告优先级和广告位的加载条数，自动维护是否显示状态
	 */
	@Type(type = "yes_no")
	@Column(name = "STATUS_SHOW")
	private Boolean show;

	/**
	 * 点击数
	 */
	@Column(name = "CLICKNO", columnDefinition = M.NUM_COLUM)
	private Integer clickNo;

	public Integer getClickNo() {
		return clickNo;
	}

	public void setClickNo(Integer clickNo) {
		this.clickNo = clickNo;
	}

	public Boolean getShow() {
		return show;
	}

	public void setShow(Boolean show) {
		this.show = show;
	}

}