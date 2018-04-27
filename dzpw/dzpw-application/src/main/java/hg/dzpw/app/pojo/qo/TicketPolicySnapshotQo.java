package hg.dzpw.app.pojo.qo;

import java.util.Date;

import hg.common.component.BaseQo;

/**
 * @类功能说明: 联票政策快照QO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：zzx
 * @创建时间：2014-11-11 下午5:10:11
 * @版本：V1.0
 */
public class TicketPolicySnapshotQo extends BaseQo {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 票务名称
	 */
	private String name;
	
	/**
	 * 票务类型
	 */
	private Integer type;
	
	/**
	 * 景区查找对象
	 */
	private ScenicSpotQo scenicSpotQo;

	/**
	 * 有效期开始时间
	 */
	private Date fixedUseDateStart;
	
	/**
	 * 固定有效期结束时间
	 */
	private Date fixedUseDateEnd;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public ScenicSpotQo getScenicSpotQo() {
		return scenicSpotQo;
	}

	public void setScenicSpotQo(ScenicSpotQo scenicSpotQo) {
		this.scenicSpotQo = scenicSpotQo;
	}

	public Date getFixedUseDateStart() {
		return fixedUseDateStart;
	}

	public void setFixedUseDateStart(Date fixedUseDateStart) {
		this.fixedUseDateStart = fixedUseDateStart;
	}

	public Date getFixedUseDateEnd() {
		return fixedUseDateEnd;
	}

	public void setFixedUseDateEnd(Date fixedUseDateEnd) {
		this.fixedUseDateEnd = fixedUseDateEnd;
	}
	
}