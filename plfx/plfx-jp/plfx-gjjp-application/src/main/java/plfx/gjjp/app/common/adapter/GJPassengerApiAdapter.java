package plfx.gjjp.app.common.adapter;

import hg.common.util.BeanMapperUtils;
import hg.common.util.DateUtil;
import plfx.gjjp.domain.model.GJPassenger;
import plfx.gjjp.domain.model.GJPassengerBaseInfo;
import plfx.yxgjclient.pojo.param.PassengerInfo;

/**
 * @类功能说明：乘客适配器
 * @类修改者：
 * @修改日期：2015-7-24下午3:46:14
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-24下午3:46:14
 */
public class GJPassengerApiAdapter {

	/**
	 * @方法功能说明：将乘客实体转为易行的对象
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-24下午3:46:21
	 * @修改内容：
	 * @参数：@param passenger
	 * @参数：@return
	 * @return:PassengerInfo
	 * @throws
	 */
	public PassengerInfo convertYXGJDTO(GJPassenger passenger) {
		GJPassengerBaseInfo passengerBaseInfo = passenger.getBaseInfo();
		PassengerInfo passengerInfo = BeanMapperUtils.map(passengerBaseInfo, PassengerInfo.class);
		passengerInfo.setBirthday(DateUtil.formatDateTime(passengerBaseInfo.getBirthday(), "yyyy-MM-dd"));
		passengerInfo.setExpiryDate(DateUtil.formatDateTime(passengerBaseInfo.getExpiryDate(), "yyyy-MM-dd"));
		return passengerInfo;
	}

}
