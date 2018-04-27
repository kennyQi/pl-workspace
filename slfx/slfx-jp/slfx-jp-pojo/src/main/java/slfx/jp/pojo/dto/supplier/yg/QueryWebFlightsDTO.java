package slfx.jp.pojo.dto.supplier.yg;

import java.util.HashMap;
import java.util.List;

import slfx.jp.pojo.dto.BaseJpDTO;

/**
 * 
 * @类功能说明：平台航班列表DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:57:23
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class QueryWebFlightsDTO extends BaseJpDTO{
	
	/**
	 * 航班list
	 */
	private List<YGFlightDTO> flightList;
	
	/**
	 * 退改签政策map  key为退改签政策KEY
	 */
	private HashMap<String, String> tgNoteMap;
	

	public List<YGFlightDTO> getFlightList() {
		return flightList;
	}


	public void setFlightList(List<YGFlightDTO> flightList) {
		this.flightList = flightList;
	}


	public HashMap<String, String> getTgNoteMap() {
		return tgNoteMap;
	}


	public void setTgNoteMap(HashMap<String, String> tgNoteMap) {
		this.tgNoteMap = tgNoteMap;
	}
	
}
