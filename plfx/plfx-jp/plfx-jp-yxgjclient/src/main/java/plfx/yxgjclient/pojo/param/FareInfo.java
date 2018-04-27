package plfx.yxgjclient.pojo.param;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 运价信息
 * @author guotx
 * 2015-07-08
 */
@XStreamAlias("fareInfo")
public class FareInfo {
	private String fareReference;
	private String filingAirline;
	private String arrivalAirport;
	private String departureAirport;
	private String passType;
	private String ref1;
	private String ref2;
	public String getFareReference() {
		return fareReference;
	}
	public void setFareReference(String fareReference) {
		this.fareReference = fareReference;
	}
	public String getFilingAirline() {
		return filingAirline;
	}
	public void setFilingAirline(String filingAirline) {
		this.filingAirline = filingAirline;
	}
	public String getPassType() {
		return passType;
	}
	public void setPassType(String passType) {
		this.passType = passType;
	}
	public String getRef1() {
		return ref1;
	}
	public void setRef1(String ref1) {
		this.ref1 = ref1;
	}
	public String getRef2() {
		return ref2;
	}
	public void setRef2(String ref2) {
		this.ref2 = ref2;
	}
	public String getArrivalAirport() {
		return arrivalAirport;
	}
	public void setArrivalAirport(String arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}
	public String getDepartureAirport() {
		return departureAirport;
	}
	public void setDepartureAirport(String departureAirport) {
		this.departureAirport = departureAirport;
	}
	
}
