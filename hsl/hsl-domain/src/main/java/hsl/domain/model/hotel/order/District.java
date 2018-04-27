package hsl.domain.model.hotel.order;

import hg.common.component.BaseModel;
import hsl.domain.model.M;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name = M.TABLE_PREFIX_HSL_JD + "DISTRICT")
@SuppressWarnings("serial")
public class District extends BaseModel{
	@Column(name = "DISTRICTID")
	private String districtId;
	@Column(name = "NAME", length = 20)
	private String name;
	@ManyToOne
	@JoinColumn(name = "HOTELGEO_ID")
	private HotelGeo hotelGeo;

	public String getDistrictId() {
		return districtId;
	}
	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}
	public HotelGeo getHotelGeo() {
		return hotelGeo;
	}
	public void setHotelGeo(HotelGeo hotelGeo) {
		this.hotelGeo = hotelGeo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
