package hg.dzpw.app.service.api.alipay;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;


import hg.common.util.DateUtil;
import hg.dzpw.app.common.AlipayConfig;
import hg.dzpw.app.common.util.alipay.AlipayReflectUtil;
import hg.dzpw.app.common.util.alipay.AlipayResponseUtil;
import hg.dzpw.app.common.util.alipay.AlipaySubmitUtil;
import hg.dzpw.pojo.api.alipay.RefundDetailData;
import hg.dzpw.pojo.api.alipay.RefundFastParameter;
import hg.dzpw.pojo.api.alipay.RefundFastResponse;

/**
 * 
 * @类功能说明：支付宝批量退款接口
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2016-3-10下午1:19:37
 * @版本：
 */
@Service
public class AliPayRefundFastService {
	Logger logger=Logger.getLogger(AliPayRefundFastService.class);
	/**
	 * 
	 * @描述： 支付宝退款接口
	 * @param refundFastParameter 退款请求参数
	 * @param detailDatas 退款详情数据集，对应refundFastParameter里的detail_data
	 * @author: guotx 
	 * @version: 2016-3-14 下午2:03:12
	 */
	public RefundFastResponse refundFastRequest(RefundFastParameter refundFastParameter,List<RefundDetailData> detailDatas){
//		设置基础退款参数
		refundFastParameter.set_input_charset(AlipayConfig.input_charset);
		refundFastParameter.setService(AlipayConfig.REFUND_FAST_SERVICE_NAME);
		refundFastParameter.setPartner(AlipayConfig.partner);
//		refundFastParameter.setSign_type(AlipayConfig.sign_type);
		refundFastParameter.setBatch_num(String.valueOf(detailDatas.size()));
		refundFastParameter.setRefund_date(DateUtil.formatDateTime(new Date()));
		//异步通知地址
		if (StringUtils.isBlank(refundFastParameter.getNotify_url())) {
			refundFastParameter.setNotify_url(AlipayConfig.refund_notify_url);
		}
		
		StringBuffer stringBuffer=new StringBuffer();
		for (RefundDetailData detailData:detailDatas) {
			stringBuffer.append(detailData).append("#");
		}
		//删除最后一个#
		stringBuffer.deleteCharAt(stringBuffer.lastIndexOf("#"));
		refundFastParameter.setDetail_data(stringBuffer.toString());
		Map<String, String> parameterMap=AlipayReflectUtil.buildParameterMap(refundFastParameter);
//		AlipaySubmitUtil.encodeParameter(parameterMap);
		logger.debug("buildParameter:"+parameterMap);
		String result = null;
		try {
			result = AlipaySubmitUtil.buildRequest("", "", parameterMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AlipayResponseUtil.refundStrToBean(result);
	}
}
