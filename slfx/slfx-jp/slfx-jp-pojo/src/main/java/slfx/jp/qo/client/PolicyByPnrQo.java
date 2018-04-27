package slfx.jp.qo.client;

import java.util.Date;

/**
 * 
 * @类功能说明：用户下单前的比价QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：qiuxianxiang
 * @创建时间：2014年7月31日下午5:16:38
 * @版本：V1.0
 *
 */
public class PolicyByPnrQo {
	
	/**
	 * 
		 1.{0} {1}                                                                
		 2.  {2} {3}   {4}  {5} HK1   {6} {7}          E T2--                 
		 3.SHA/T SHA/T021-62277798/SHANGHAI EVER BRIGHT TOWN INT'L TRAVEL SERVICE CO.,LTD ABCDEFG                                                            
		 4.END                                                                          
		 5.TL/{8}/{9}/SHA255                                                         
		 6.SSR FOID {10} HK1 NI12345678932165478/P1                                       
		 7.SSR ADTK 1E BY SHA{11}/{12} OR CXL {13} {14}                          
		 8.RMK AO/ABE/KH-104220941001/OP-NN ADMIN/DT-20121205142736                     
		 9.RMK CA/MXNT4G                                                                
		10.SHA255
	 * 
	  	0 乘客姓名				passengerName
		1 PNR					pnr			
		2 航班号				flightNo
		3 舱位					classNo
		4 星期的缩写+日期+月份的缩写	departureDate.星期+departureDate.日期
		5 起飞机场代码+到达机场代码		boardPoint+offPoint
		6 出发时间						departureDate.时间		0606
		7 到达时间						arrivalDate.时间			0806
		8 出票时限						tktStart.时间	     ----在某一个时间段出票，一般在出发时间前几个小时内			
		9 出票时限日期					tktStart.日期	     ----在某一个时间段出票，一般在出发时间前几个小时内		
		10 航司编码						Carrier	
		11 航空公司的出票时限日期 		tktStart.日期+tktStart.年      ---- 该时间大约等同与出票时限
		12 航空公司的出票时限小时 		tktStart.时间
		13 航司编码+航班号(等同第二个参数) 				flightNo
		14 舱位+日期+月份缩写(出发时间)			classNo+departureDate.日期
	 *
	 */
	
	private String pnr;					//  PNR 
	private String passengerName;		//	乘客姓名
	private String flightNo;			//	航班号码
	private String classNo;				//  舱位
	private Date   departureDate;		//	出发时间
	private Date   arrivalDate;			//	到达时间
	private String boardPoint;			//	起飞机场代码
	private String offPoint;			//	到达机场代码
	private Date   tktStart;			//	出票时间
	private String carrier;				//	航司编码	
	
	
	private static final long INTERVAL_DEP = 1000 * 60 * 3;  //出票时间距离出发时间的间隔。 
	
	public PolicyByPnrQo(){
		
	}
	
	/**
	 * 航班号、仓位、出发日期、到达日期、起飞机场代码、到达机场代码、航司编码(取航班号前2位)
	 * @param flightNo
	 * @param classNo
	 * @param departureDate
	 * @param arrivalDate
	 * @param boardPoint
	 * @param offPoint
	 * @param carrier
	 */
	public PolicyByPnrQo(String flightNo, String classNo, Date departureDate, Date arrivalDate,
			String boardPoint, String offPoint, String carrier) {
		super();
		this.flightNo = flightNo;
		this.classNo = classNo;
		this.departureDate = departureDate;
		this.arrivalDate = arrivalDate;
		this.boardPoint = boardPoint;
		this.offPoint = offPoint;
		this.carrier = carrier;
	}
	
	/**
	 * 
	 * @类名：PolicyByPnrQo.java
	 * @描述：TODO
	 * @@param flightNo
	 * @@param classNo
	 * @@param departureDate
	 * @@param arrivalDate
	 * @@param boardPoint
	 * @@param offPoint
	 * @@param carrier
	 * @@param pnr 真实PNR
	 * @@param passengerName 真实乘客姓名
	 * @@param tktStart 起飞日期
	 */
	public PolicyByPnrQo(String flightNo, String classNo, Date departureDate, Date arrivalDate,
			String boardPoint, String offPoint, String carrier,String pnr,String passengerName,Date tktStart) {
		super();
		this.flightNo = flightNo;
		this.classNo = classNo;
		this.departureDate = departureDate;
		this.arrivalDate = arrivalDate;
		this.boardPoint = boardPoint;
		this.offPoint = offPoint;
		this.carrier = carrier;
		this.pnr = pnr;
		this.passengerName = passengerName;
		this.tktStart = tktStart;
	}
	
	
	public String getPnr() {
		if (null == pnr) {
			pnr = "MLGBSD";
		}
		return pnr;
	}
	public void setPnr(String pnr) {
		this.pnr = pnr;
	}
	public String getPassengerName() {
		if (null == passengerName) {
			passengerName ="王武";
		}
		return passengerName;
	}
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	public String getClassNo() {
		return classNo;
	}
	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}
	public Date getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
	public Date getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public String getBoardPoint() {
		return boardPoint;
	}
	public void setBoardPoint(String boardPoint) {
		this.boardPoint = boardPoint;
	}
	public String getOffPoint() {
		return offPoint;
	}
	public void setOffPoint(String offPoint) {
		this.offPoint = offPoint;
	}
	public Date getTktStart() {
		if (null == null) {
			if (null != departureDate) {
				tktStart = new Date(departureDate.getTime()-INTERVAL_DEP);
			}
		}
		return tktStart;
	}
	public void setTktStart(Date tktStart) {
		this.tktStart = tktStart;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	
}
