package hg.pojo.command;

import java.util.List;

/**
 * 修改仓库
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class ModifyWarehouseCommand extends JxcCommand {

	/**
	 * 仓库id
	 */
	private String warehouseId;

	/**
	 * 仓库名称
	 */
	private String name;

	/**
	 * 仓库联系人
	 */
	private String linkMan;

	/**
	 * 联系电话
	 */
	private String linkPhone;

	/**
	 * 仓库所在省
	 */
	private String provinceId;

	/**
	 * 仓库所在市
	 */
	private String cityId;

	/**
	 * 仓库所在区、县
	 */
	private String areaId;

	/**
	 * 仓库地址
	 */
	private String address;

	/**
	 * 覆盖区域
	 */
	private List<String> zoneIdList;

	/**
	 * 备注
	 */
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<String> getZoneIdList() {
		return zoneIdList;
	}

	public void setZoneIdList(List<String> zoneIdList) {
		this.zoneIdList = zoneIdList;
	}

}
