package plfx.jp.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import plfx.jp.domain.model.J;
import hg.common.component.BaseModel;

@Entity
@Table(name = J.TABLE_PREFIX + "AIRPORTCODE")
public class AirPortCode extends BaseModel{
	private static final long serialVersionUID = -8263900850339727071L;

	/**
	 * 机场名称
	 */
	@Column(name = "AIRNAME", length = 12)
	private String airName;
	
	/**
	 * 机场三字码
	 */
	@Column(name = "AIRCODE", length = 3)
	private String airCode;

	public String getAirName() {
		return airName;
	}

	public void setAirName(String airName) {
		this.airName = airName;
	}

	public String getAirCode() {
		return airCode;
	}

	public void setAirCode(String airCode) {
		this.airCode = airCode;
	}
}
