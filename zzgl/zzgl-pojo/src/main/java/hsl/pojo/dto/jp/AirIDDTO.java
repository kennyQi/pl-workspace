package hsl.pojo.dto.jp;
import hsl.pojo.dto.BaseDTO;
@SuppressWarnings("serial")
public class AirIDDTO extends BaseDTO {
	private String airID;

	private String status;

	public String getAirID() {
		return airID;
	}

	public void setAirID(String airID) {
		this.airID = airID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
