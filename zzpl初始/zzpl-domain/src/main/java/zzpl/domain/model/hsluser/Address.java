package zzpl.domain.model.hsluser;

import hg.common.component.BaseModel;
import hg.system.model.meta.Area;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;

/**
 * 
 * @类功能说明：地址类，用于收快递等
 * @类修改者：yuxx
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年7月30日下午2:06:18
 *
 */
@SuppressWarnings("serial")
public class Address extends BaseModel {

	private Province province;

	private City city;

	private Area area;

	private String addressDetail;

	private CommonUserInfo contactUserInfo;

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

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public CommonUserInfo getContactUserInfo() {
		return contactUserInfo;
	}

	public void setContactUserInfo(CommonUserInfo contactUserInfo) {
		this.contactUserInfo = contactUserInfo;
	}

}
