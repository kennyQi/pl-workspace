package jxc.domain.model.warehouse;

import hg.common.component.BaseModel;
import hg.pojo.command.CreateWarehouseCommand;
import hg.pojo.command.ModifyWarehouseCommand;
import hg.system.model.meta.Area;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.M;
import jxc.domain.model.system.Zone;
import jxc.domain.util.CodeUtil;

@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_JXC_WAREHOUSE + "WAREHOUSE")
public class Warehouse extends JxcBaseModel {

	/**
	 * 仓库名称
	 */
	@Column(name = "WAREHOUSE_NAME", length = 30)
	private String name;

	/**
	 * 仓库类型
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "WAREHOUSE_TYPE_ID")
	private WarehouseType type;

	/**
	 * 仓库联系人
	 */
	@Column(name = "WAREHOUSE_LINKMAN", length = 10)
	private String linkMan;

	/**
	 * 联系电话
	 */
	@Column(name = "WAREHOUSE_PHONE", length = 30)
	private String linkPhone;

	/**
	 * 仓库所在省
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PROVINCE_ID")
	private Province province;

	/**
	 * 仓库所在市
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CITY_ID")
	private City city;

	/**
	 * 仓库所在区、县
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "AREA_ID")
	private Area area;

	/**
	 * 仓库地址
	 */
	@Column(name = "WAREHOUSE_ADDRESS", length = 255)
	private String address;

	/**
	 * 覆盖区域
	 */
	@OneToMany(mappedBy = "warehouse")
	private List<Zone> zoneItems;

	/**
	 * 备注
	 */
	@Column(name = "REMARK", length = 255)
	private String remark;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public WarehouseType getType() {
		return type;
	}

	public void setType(WarehouseType type) {
		this.type = type;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Zone> getZoneItems() {
		return zoneItems;
	}

	public void setZoneItems(List<Zone> zoneItems) {
		this.zoneItems = zoneItems;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void createWarehouse(CreateWarehouseCommand command,Province province,City city,Area area,WarehouseType type) {

		setArea(area);
		setCity(city);
		setProvince(province);
		setType(type);
		setId(CodeUtil.createWarehouseCode());
		setAddress(command.getAddress());
		setLinkMan(command.getLinkMan());
		setLinkPhone(command.getLinkPhone());
		setName(command.getName());
		setRemark(command.getRemark());
		
		setStatusRemoved(false);
	}

	public void modifyWarehouse(ModifyWarehouseCommand command,Province province,City city,Area area) {
		
		setArea(area);
		setCity(city);
		setProvince(province);
		setAddress(command.getAddress());		
		setLinkMan(command.getLinkMan());
		setLinkPhone(command.getLinkPhone());
		setName(command.getName());
		setRemark(command.getRemark());
	}
}
