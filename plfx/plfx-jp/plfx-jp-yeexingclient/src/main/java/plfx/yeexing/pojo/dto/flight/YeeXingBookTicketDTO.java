package plfx.yeexing.pojo.dto.flight;



import java.util.ArrayList;
import java.util.List;

import plfx.jp.pojo.dto.BaseJpDTO;

/****
 * 
 * @类功能说明：易行天下生成订单DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年6月17日上午10:12:45
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class YeeXingBookTicketDTO extends BaseJpDTO{

	/***
	 * 合作伙伴用户名
	 */
	private String userName;
	
	/**
	 * 加密字符串             来自于QueryAirpolicy的结果
	 */
	private String encryptString;

	/***
	 * 成人订单号          订儿童票时必填，且需先订成人票
	 */
	private String orderid;
	/***
	 * 外部订单号       用户系统中的订单号
	 */
	private String outOrderid;
	
	/***
	 * 乘客信息对象 
	 */
	private YeeXingPassengerInfoDTO passengerInfoDTO;
	/***
	 * 签名  所有参数经MD5加密算法后得出的结果
	 */
	private String sign;
	
	/***
	 * 乘客信息xml
	 */
	private String passengerInfo;
	
	public YeeXingBookTicketDTO(YeeXingPassengerInfoDTO passengerInfoDTO) {
		StringBuilder sb=new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
		sb.append("<input>");
		sb.append("<passengers>");
		for(YeeXingPassengerDTO p:passengerInfoDTO.getPassengerList()){
		  sb.append("<passenger Name=\""+p.getPassengerName()+"\" Type=\""+p.getPassengerType()+"\" IdType=\""+p.getIdType()+"\" IdNo\""+p.getIdNo()+"\"/>");
		}
		sb.append("<passengers>");  
		sb.append("<contacter Name=\""+passengerInfoDTO.getName()+"\" Telephone\""+passengerInfoDTO.getTelephone()+"\"/>");
		sb.append("<input>");
		passengerInfo=sb.toString();
		//System.out.println(passengerInfo);
	}
	public YeeXingPassengerInfoDTO getPassengerInfoDTO() {
		return passengerInfoDTO;
	}
	public void setPassengerInfoDTO(YeeXingPassengerInfoDTO passengerInfoDTO) {
		this.passengerInfoDTO = passengerInfoDTO;
	}
	
	public String getPassengerInfo() {
		return passengerInfo;
	}
	public void setPassengerInfo(String passengerInfo) {
		this.passengerInfo = passengerInfo;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEncryptString() {
		return encryptString;
	}
	public void setEncryptString(String encryptString) {
		this.encryptString = encryptString;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getOutOrderid() {
		return outOrderid;
	}
	public void setOutOrderid(String outOrderid) {
		this.outOrderid = outOrderid;
	}

	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public static void main(String[] args) {

		YeeXingPassengerInfoDTO p=new YeeXingPassengerInfoDTO();
        List<YeeXingPassengerDTO> passengerList=new ArrayList<YeeXingPassengerDTO>();
        YeeXingPassengerDTO oo=new YeeXingPassengerDTO();
        oo.setPassengerName("1");
        oo.setPassengerType("2");
        oo.setIdNo("5");
        oo.setIdType("6");
        YeeXingPassengerDTO ooo=new YeeXingPassengerDTO();
        ooo.setPassengerName("11");
        ooo.setPassengerType("21");
        ooo.setIdNo("51");
        ooo.setIdType("61");
        passengerList.add(ooo);
        passengerList.add(oo);
	    p.setName("3");
	    p.setTelephone("4");
	    p.setPassengerList(passengerList);
		YeeXingBookTicketDTO b=new YeeXingBookTicketDTO(p);
		
	}
	
	
}
