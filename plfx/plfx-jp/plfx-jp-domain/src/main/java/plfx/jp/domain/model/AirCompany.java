package plfx.jp.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import hg.common.component.BaseModel;

@Entity
@Table(name = J.TABLE_PREFIX + "AIRCOMPANY")
public class AirCompany extends BaseModel{
	private static final long serialVersionUID = 4925801229570047885L;
	/**
	 * 航空公司全名
	 */
	@Column(name = "AIRCOMPANYNAME", length = 64)
	private String airCompanyName;
	
	/**
	 * 航空公司
	 */
	@Column(name = "AIRCOMPANYCODE", length = 2)
	private String airCompanyCode;
	
	/**
	 * 航空公司简称
	 */
	@Column(name = "AIRCOMPANYSHOTNAME", length = 64)
	private String airCompanyShotName;

	public String getAirCompanyName() {
		return airCompanyName;
	}

	public void setAirCompanyName(String airCompanyName) {
		this.airCompanyName = airCompanyName;
	}

	public String getAirCompanyCode() {
		return airCompanyCode;
	}

	public void setAirCompanyCode(String airCompanyCode) {
		this.airCompanyCode = airCompanyCode;
	}

	public String getAirCompanyShotName() {
		return airCompanyShotName;
	}

	public void setAirCompanyShotName(String airCompanyShotName) {
		this.airCompanyShotName = airCompanyShotName;
	}
}
