package lxs.domain.model.line;

import hg.common.component.BaseModel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lxs.domain.model.M;

@Entity
@Table(name=M.TABLE_PREFIX_XL +"ACTIVITY")
@SuppressWarnings("serial")
public class LineActivity extends BaseModel{

	/**
	 * 活动类型
	 * 1：价格活动
	 * 2：人数活动
	 */
	@Column(name = "ACTIVITY_TYPE", columnDefinition = M.CHAR_COLUM)
	private String activityType;
	
	/**
	 * 游玩人数
	 */
	@Column(name = "TRAVLER_NO", columnDefinition = M.NUM_COLUM)
	private String travlerNO;
	
	/**
	 * 成人价格
	 */
	@Column(name = "ADULT_UNIT_PRICE", columnDefinition = M.DOUBLE_COLUM)
	private Double adultUnitPrice;
	
	/**
	 * 儿童价格
	 */
	@Column(name = "CHILD_UNIT_PRICE", columnDefinition = M.DOUBLE_COLUM)
	private Double childUnitPrice;
	
	/**
	 * 最低价格
	 */
	@Column(name = "MIN_PRICE", columnDefinition = M.DOUBLE_COLUM)
	private Double minPrice;
	
	/**
	 * 添加时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;
	
	/**
	 * 关联线路
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LINE_ID")
	private Line line;

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getTravlerNO() {
		return travlerNO;
	}

	public void setTravlerNO(String travlerNO) {
		this.travlerNO = travlerNO;
	}

	public Double getAdultUnitPrice() {
		return adultUnitPrice;
	}

	public void setAdultUnitPrice(Double adultUnitPrice) {
		this.adultUnitPrice = adultUnitPrice;
	}

	public Double getChildUnitPrice() {
		return childUnitPrice;
	}

	public void setChildUnitPrice(Double childUnitPrice) {
		this.childUnitPrice = childUnitPrice;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}
	
	
}
