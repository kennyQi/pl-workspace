package plfx.yeexing.pojo.dto.flight;



import java.util.List;

import plfx.jp.pojo.dto.BaseJpDTO;

/****
 * 
 * @类功能说明：乘客信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年6月17日下午1:32:39
 * @版本：V1.0
 * 
 */
@SuppressWarnings("serial")
public class YeeXingPassengerInfoDTO extends BaseJpDTO {

	/**
	 * 乘客集合
	 */
	private List<YeeXingPassengerDTO> passengerList;
	/**
	 * 联系人姓名
	 */
	private String name;
	/***
	 * 联系人电话
	 */
	private String telephone;
    
	public List<YeeXingPassengerDTO> getPassengerList() {
		return passengerList;
	}

	public void setPassengerList(List<YeeXingPassengerDTO> passengerList) {
		this.passengerList = passengerList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

}
