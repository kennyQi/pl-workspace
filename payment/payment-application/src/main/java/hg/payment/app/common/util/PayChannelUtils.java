package hg.payment.app.common.util;

import hg.payment.domain.model.channel.PayChannel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PayChannelUtils {

	/**
	 * 查询支付渠道列表
	 * @return
	 */
	public static List<PayChannel> getPayChannelList(){
		List<PayChannel> payChannelList = new ArrayList<PayChannel>();
		HashMap<String,PayChannel> payChannelMap = (HashMap<String, PayChannel>) BeanUtils.getBean(PayChannel.class);
		for(PayChannel payChannel:payChannelMap.values()){
			payChannelList.add(payChannel);
		}
		Collections.sort(payChannelList);
		return payChannelList;
	}
	
	/**
	 * 查询指定渠道基本信息
	 * @param type
	 * @return
	 */
	public static PayChannel getPayChannel(Integer type){
		HashMap<String,PayChannel> payChannelMap = (HashMap<String, PayChannel>) BeanUtils.getBean(PayChannel.class);
		for(PayChannel channel:payChannelMap.values()){
			if(channel.getType() == type){
				return channel;
			}
		}
		return null;
	}
}
