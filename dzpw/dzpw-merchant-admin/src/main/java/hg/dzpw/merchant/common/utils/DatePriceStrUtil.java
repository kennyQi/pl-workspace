package hg.dzpw.merchant.common.utils;

import java.util.List;

import hg.dzpw.domain.model.policy.TicketPolicyDatePrice;

import com.alibaba.fastjson.JSONObject;

/**
 * @类功能说明：转换价格日历JSON串
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2015-3-11下午3:27:10
 * @版本：V1.0
 *
 */
public class DatePriceStrUtil {
	
	
	public static String datePriceToStr(String priceJson){
		
		List<TicketPolicyDatePrice> list = JSONObject.parseArray(priceJson, TicketPolicyDatePrice.class);
		
		StringBuffer sb = new StringBuffer();
		
		if(list==null || list.size()<1)
			return "";
		
		//转换成格式：20150312:100;20150313:101
		for(int i=0; i<list.size(); i++){
			if(i==list.size()-1)
				sb.append(list.get(i).getDate()).append(":").append(list.get(i).getPrice());
			else
				sb.append(list.get(i).getDate()).append(":").append(list.get(i).getPrice()).append(";");
		}
		return sb.toString();
	}
	
}
