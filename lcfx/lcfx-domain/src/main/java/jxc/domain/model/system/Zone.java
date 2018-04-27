package jxc.domain.model.system;

import hg.common.component.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import jxc.domain.model.M;
import jxc.domain.model.warehouse.Warehouse;

/**
 * 区域表
 * @author liujz
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name=M.TABLE_PREFIX_JXC_SYETEM+"ZONE")
public class Zone extends BaseModel {

	/**
	 * 所属仓库
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "WAREHOUSE_ID")
	private Warehouse warehouse;
	
	/**
	 * 区域名称
	 */
	@Column(name="ZONE_NAME",length=50)
	private String zoneName;
	

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	
}

