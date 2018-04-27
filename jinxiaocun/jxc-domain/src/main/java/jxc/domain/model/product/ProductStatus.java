package jxc.domain.model.product;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

@Embeddable
public class ProductStatus {

	/**
	 * 是否启用
	 */
	@Type(type = "yes_no")
	@Column(name = "STAUTS_USING")
	private Boolean using;

	/**
	 * 是否已设置出库重量/体积
	 */
	@Type(type = "yes_no")
	@Column(name = "STAUTS_SETTING_OUTSTOCK_PARAM")
	private Boolean settingOutStockParam;

	public Boolean getUsing() {
		return using;
	}

	public void setUsing(Boolean using) {
		this.using = using;
	}

	public Boolean getSettingOutStockParam() {
		return settingOutStockParam;
	}

	public void setSettingOutStockParam(Boolean settingOutStockParam) {
		this.settingOutStockParam = settingOutStockParam;
	}

}
