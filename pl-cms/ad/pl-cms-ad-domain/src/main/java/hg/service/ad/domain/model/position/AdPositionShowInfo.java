package hg.service.ad.domain.model.position;

import hg.system.model.M;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @类功能说明：广告位的展示信息
 * @类修改者：
 * @修改日期：2014年12月11日下午4:05:32
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：yuxx
 * @创建时间：2014年12月11日下午4:05:32
 * 
 */
@Embeddable
public class AdPositionShowInfo {
	
	/**
	 * 显示条数
	 */
	@Column(name="SHOW_NO",columnDefinition=M.NUM_COLUM)
	private Integer showNo;
	
	/**
	 * 加载条数
	 */
	@Column(name="LOAD_NO",columnDefinition=M.NUM_COLUM)
	private Integer loadNo;
	
	/**
	 * 切换速度
	 */
	@Column(name="CHANGE_SPEED_SECOND",columnDefinition=M.NUM_COLUM)
	private Integer changeSpeedSecond;
	
	public Integer getShowNo() {
		return showNo;
	}

	public void setShowNo(Integer showNo) {
		this.showNo = showNo;
	}

	public Integer getLoadNo() {
		return loadNo;
	}

	public void setLoadNo(Integer loadNo) {
		this.loadNo = loadNo;
	}

	public Integer getChangeSpeedSecond() {
		return changeSpeedSecond;
	}

	public void setChangeSpeedSecond(Integer changeSpeedSecond) {
		this.changeSpeedSecond = changeSpeedSecond;
	}
	
}