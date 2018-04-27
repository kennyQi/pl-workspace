package zzpl.pojo.dto.jp;

import java.util.*;

/**
 * 航班起飞信息
 * DTO
 * @author tandeng
 *
 */
public class FlightStartInfoDTO {
	
   /** 起飞机场简称 */
   private String startAirportShortName;
   
   /** 起飞机场代码 */
   private String startAirportCode;
   
   /** 起飞机场所在城市 */
   private String startAirportCity;
   
   /** 起飞航站楼 */
   private String startTerminal;
   
   /** 起飞日期序号 */
   private int startTimeNum;
   
   /** 起飞时间 */
   private String startTime;
   
   /** 起飞机场全称 */
   private int startAirportName;
   
   /** 起飞日期 */
   private Date startDate;

	public String getStartAirportShortName() {
		return startAirportShortName;
	}
	
	public void setStartAirportShortName(String startAirportShortName) {
		this.startAirportShortName = startAirportShortName;
	}
	
	public String getStartAirportCode() {
		return startAirportCode;
	}
	
	public void setStartAirportCode(String startAirportCode) {
		this.startAirportCode = startAirportCode;
	}
	
	public String getStartAirportCity() {
		return startAirportCity;
	}
	
	public void setStartAirportCity(String startAirportCity) {
		this.startAirportCity = startAirportCity;
	}
	
	public String getStartTerminal() {
		return startTerminal;
	}
	
	public void setStartTerminal(String startTerminal) {
		this.startTerminal = startTerminal;
	}
	
	public int getStartTimeNum() {
		return startTimeNum;
	}
	
	public void setStartTimeNum(int startTimeNum) {
		this.startTimeNum = startTimeNum;
	}
	
	public String getStartTime() {
		return startTime;
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public int getStartAirportName() {
		return startAirportName;
	}
	
	public void setStartAirportName(int startAirportName) {
		this.startAirportName = startAirportName;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
   
}