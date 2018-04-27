package zzpl.pojo.dto.jp;

/**
 * 航班到达信息
 * DTO
 * @author tandeng
 *
 */
public class FlightEndInfoDTO {
	
   /** 到达机场简称 */
   private String endAirportShortName;
   
   /** 到达机场全称 */
   private String endAirportName;
   
   /** 到达机场代码 */
   private String endAirportCode;
   
   /** 到达机场所在城市 */
   private String endAirportCity;
   
   /** 到达航站楼 */
   private String endTerminal;
   
   /** 到达日期序号 */
   private int endTimeNum;
   
   /** 到达时间 */
   private String endTime;
   
   /** 到达日期 */
   private String endDate;

	public String getEndAirportShortName() {
		return endAirportShortName;
	}
	
	public void setEndAirportShortName(String endAirportShortName) {
		this.endAirportShortName = endAirportShortName;
	}
	
	public String getEndAirportName() {
		return endAirportName;
	}
	
	public void setEndAirportName(String endAirportName) {
		this.endAirportName = endAirportName;
	}
	
	public String getEndAirportCode() {
		return endAirportCode;
	}
	
	public void setEndAirportCode(String endAirportCode) {
		this.endAirportCode = endAirportCode;
	}
	
	public String getEndAirportCity() {
		return endAirportCity;
	}
	
	public void setEndAirportCity(String endAirportCity) {
		this.endAirportCity = endAirportCity;
	}
	
	public String getEndTerminal() {
		return endTerminal;
	}
	
	public void setEndTerminal(String endTerminal) {
		this.endTerminal = endTerminal;
	}
	
	public int getEndTimeNum() {
		return endTimeNum;
	}
	
	public void setEndTimeNum(int endTimeNum) {
		this.endTimeNum = endTimeNum;
	}
	
	public String getEndTime() {
		return endTime;
	}
	
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String getEndDate() {
		return endDate;
	}
	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
 
}