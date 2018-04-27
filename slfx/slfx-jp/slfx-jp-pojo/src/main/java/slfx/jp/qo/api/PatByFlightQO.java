package slfx.jp.qo.api;

import java.util.List;

import hg.common.component.BaseQo;

/**
 * 
 * @类功能说明：验价QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:15:01
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class PatByFlightQO extends BaseQo {
	
	/**
	 * 订座记录编码(PNR) 
	 * (与Flights必须且只能填一项)
	 */
	private String pnr;
	
	/**
	 * 航班查询信息
	 */
	private List<WebFlightQO> flights;

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public List<WebFlightQO> getFlights() {
		return flights;
	}

	public void setFlights(List<WebFlightQO> flights) {
		this.flights = flights;
	}

}
