package plfx.jp.qo.api;

import hg.common.annotation.QOConfig;
import hg.common.component.BaseQo;

@SuppressWarnings("serial")
@QOConfig(daoBeanId = "airCompanyDao")
public class AirCompanyQO extends BaseQo{
	
	/**
	 * 航空公司代码
	 */
	private String airCompanyCode;
	
	/**
	 * 航空公司名字
	 */
	private String airCompanyName;
	
	/**
	 * 航空公司名字 简称
	 */
	private String airCompanyShotName;

	public String getAirCompanyCode() {
		return airCompanyCode;
	}

	public void setAirCompanyCode(String airCompanyCode) {
		this.airCompanyCode = airCompanyCode;
	}

	public String getAirCompanyName() {
		return airCompanyName;
	}

	public void setAirCompanyName(String airCompanyName) {
		this.airCompanyName = airCompanyName;
	}

	public String getAirCompanyShotName() {
		return airCompanyShotName;
	}

	public void setAirCompanyShotName(String airCompanyShotName) {
		this.airCompanyShotName = airCompanyShotName;
	}
}
