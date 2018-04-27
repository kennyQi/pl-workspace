package plfx.gjjp.domain.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Where;

import plfx.jp.domain.model.J;

/**
 * @类功能说明：航段信息
 * @类修改者：
 * @修改日期：2015-6-29下午5:05:22
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-6-29下午5:05:22
 */
@Embeddable
@SuppressWarnings("serial")
public class GJJPOrderSegmentInfo implements Serializable {
	
	/** 航程类型：单程 */
	public final static int LINE_TYPE_SINGLE = 1;
	/** 航程类型：往返 */
	public final static int LINE_TYPE_GO_BACK = 2;

	/**
	 * 航程信息
	 * 
	 * 1.单程2.来回程
	 */
	@Column(name = "LINE_TYPE", columnDefinition = J.TYPE_NUM_COLUM)
	private Integer lineType;
	
	/**
	 * 退改签规则
	 */
	@Column(name = "AIR_RULES", length = 2048)
	private String airRules;

	/**
	 * 始发地三字码
	 */
	@Column(name = "ORG_CITY", length = 32)
	private String orgCity;

	/**
	 * 目的地三字码
	 */
	@Column(name = "DST_CITY", length = 32)
	private String dstCity;

	/**
	 * 去程总用时(单位分钟)
	 */
	@Column(name = "TAKEOFF_TOTAL_DURATION", columnDefinition = J.NUM_COLUM)
	private Integer takeoffTotalDuration = 0;
	
	/**
	 * 去程航班
	 */
	@Where(clause = "TYPE=1")
	@OrderBy("startTime asc")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "jpOrder", cascade = CascadeType.ALL)
	private List<GJFlightCabin> takeoffFlights;

	/**
	 * 回程总用时(单位分钟)
	 */
	@Column(name = "BACK_TOTAL_DURATION", columnDefinition = J.NUM_COLUM)
	private Integer backTotalDuration = 0;

	/**
	 * 回程航班
	 */
	@Where(clause = "TYPE=2")
	@OrderBy("startTime asc")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "jpOrder", cascade = CascadeType.ALL)
	private List<GJFlightCabin> backFlights;

	public String getAirRules() {
		return airRules;
	}

	public void setAirRules(String airRules) {
		this.airRules = airRules;
	}

	public String getOrgCity() {
		return orgCity;
	}

	public void setOrgCity(String orgCity) {
		this.orgCity = orgCity;
	}

	public String getDstCity() {
		return dstCity;
	}

	public void setDstCity(String dstCity) {
		this.dstCity = dstCity;
	}

	public Integer getTakeoffTotalDuration() {
		return takeoffTotalDuration;
	}

	public void setTakeoffTotalDuration(Integer takeoffTotalDuration) {
		this.takeoffTotalDuration = takeoffTotalDuration;
	}

	public List<GJFlightCabin> getTakeoffFlights() {
		return takeoffFlights;
	}

	public void setTakeoffFlights(List<GJFlightCabin> takeoffFlights) {
		this.takeoffFlights = takeoffFlights;
	}

	public Integer getBackTotalDuration() {
		return backTotalDuration;
	}

	public void setBackTotalDuration(Integer backTotalDuration) {
		this.backTotalDuration = backTotalDuration;
	}

	public List<GJFlightCabin> getBackFlights() {
		return backFlights;
	}

	public void setBackFlights(List<GJFlightCabin> backFlights) {
		this.backFlights = backFlights;
	}

	public Integer getLineType() {
		return lineType;
	}

	public void setLineType(Integer lineType) {
		this.lineType = lineType;
	}

}
