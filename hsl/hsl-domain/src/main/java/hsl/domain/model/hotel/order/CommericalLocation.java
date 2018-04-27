package hsl.domain.model.hotel.order;

import hg.common.component.BaseModel;
import hsl.domain.model.M;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = M.TABLE_PREFIX_HSL_JD + "COMMERICALLOCATION")
@SuppressWarnings("serial")
public class CommericalLocation extends BaseModel{
	@Column(name = "DISTRICTID")
	private String commericalLocationId;
	@Column(name = "NAME", length = 20)
	private String name;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HOTELGEO_ID")
	private HotelGeo hotelGeo;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCommericalLocationId() {
		return commericalLocationId;
	}
	public void setCommericalLocationId(String commericalLocationId) {
		this.commericalLocationId = commericalLocationId;
	}
	public HotelGeo getHotelGeo() {
		return hotelGeo;
	}
	public void setHotelGeo(HotelGeo hotelGeo) {
		this.hotelGeo = hotelGeo;
	}
	
}
