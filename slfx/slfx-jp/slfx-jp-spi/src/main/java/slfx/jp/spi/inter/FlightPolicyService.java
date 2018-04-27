package slfx.jp.spi.inter;


import slfx.jp.pojo.dto.flight.SlfxFlightPolicyDTO;
import slfx.jp.pojo.exception.JPException;
import slfx.jp.qo.api.JPPolicySpiQO;

/**
 * 
 * @类功能说明：平台政策查询接口SERVICE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:09:36
 * @版本：V1.0
 *
 */
public interface FlightPolicyService {
	
	/**
	 * 查询政策
	 * 实现时需要将QO转化
	 * @param QO
	 * @return	DTO
	 * @author tandeng
	 * @throws JPException 
	 * @updateDate 2014-07-25
	 */
	SlfxFlightPolicyDTO queryPlatformPolicy(JPPolicySpiQO qo) throws JPException;

}
