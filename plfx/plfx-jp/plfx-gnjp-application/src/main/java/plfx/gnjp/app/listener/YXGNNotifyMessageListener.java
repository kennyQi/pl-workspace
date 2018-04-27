package plfx.gnjp.app.listener;


import hg.common.component.BaseAmqpMessage;
import hg.common.util.HttpUtil;
import hg.log.util.HgLogger;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import plfx.jp.pojo.dto.dealer.DealerDTO;
import plfx.jp.pojo.system.DealerConstants;
import plfx.jp.qo.admin.dealer.DealerQO;
import plfx.jp.spi.inter.dealer.DealerService;
import plfx.yeexing.pojo.command.message.JPNotifyMessageApiCommand;
import plfx.yeexing.pojo.message.SynchronizationJPNotifyMessage;

import com.alibaba.fastjson.JSON;
/**
 * 
 * @类功能说明：机票同步消息监听器
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年7月7日上午10:35:42
 * @版本：V1.0
 *
 */
public class YXGNNotifyMessageListener {
	
	@Autowired
	private DealerService dealerService;

	
	public void listen(SynchronizationJPNotifyMessage message) {
		BaseAmqpMessage<JPNotifyMessageApiCommand> msg = (BaseAmqpMessage<JPNotifyMessageApiCommand>)message;
		HgLogger.getInstance().info("yuqz", "YXGNNotifyMessageListener->发送通知开始msg：" + JSON.toJSONString(msg));
		if(msg.getType()!=null&&msg.getContent()!=null){
			DealerQO qo = new DealerQO();
			if(msg.getContent().getOrderNo() != null){
				qo.setCode(msg.getContent().getOrderNo().substring(0,5));//DAO里目前是like
			}
			qo.setStatus(DealerConstants.USE);
			List<DealerDTO> dealerList = dealerService.queryList(qo);
			for (DealerDTO dto : dealerList) {
				//设置sign
				String secretKey = dto.getSecretKey();
				String sign = MD5(secretKey, JSON.toJSONString(msg.getContent()));
				msg.getContent().setSign(sign);
				HgLogger.getInstance().info("yuqz", "通知:机票同步经销商信息dto:" + JSON.toJSONString(dto));
				HttpUtil.reqForPost(dto.getNotifyUrl(), dto.getNotifyValue() + "=" + JSON.toJSONString(msg.getContent()), 30000);
			}
		}
	}
	
//	public void listen(SynchronizationJPRefundStateMessage message) {
//		BaseAmqpMessage<GNJPSyncRefundStateCommand> msg = (BaseAmqpMessage<GNJPSyncRefundStateCommand>)message;
//		if(msg.getContent()!=null){
//			DealerQO qo = new DealerQO();
//			if(msg.getContent().getOrderNo() != null){
//				qo.setCode(msg.getContent().getOrderNo().substring(0,5));//DAO里目前是like
//			}
//			qo.setStatus(DealerConstants.USE);
//			List<DealerDTO> dealerList = dealerService.queryList(qo);
//			for (DealerDTO dto : dealerList) {
//				//设置sign
//				String secretKey = dto.getSecretKey();
//				String sign = MD5(secretKey, JSON.toJSONString(msg.getContent()));
//				msg.getContent().setSign(sign);
//				HgLogger.getInstance().info("yuqz", "通知:机票同步退废票状态经销商信息dto:" + JSON.toJSONString(dto));
//				HttpUtil.reqForPost(dto.getNotifyUrl(), dto.getNotifyValue() + "=" + JSON.toJSONString(msg.getContent()), 30000);
//			}
//		}
//	}
	
//	@SuppressWarnings("unchecked")
//	public void listen(Object message) {
//		if(!(message instanceof BaseAmqpMessage)){
//			return;			
//		}
//		HgLogger.getInstance().info("yuqz", "机票通知消息监听器>>监听到消息：" + JSON.toJSONString(message, true));
//		
//		if(message instanceof SynchronizationJPNotifyMessage){
//			BaseAmqpMessage<JPNotifyMessageApiCommand> msg = (BaseAmqpMessage<JPNotifyMessageApiCommand>)message;
//			if(msg.getType()!=null&&msg.getContent()!=null){
//				DealerQO qo = new DealerQO();
//				if(msg.getContent().getOrderNo() != null){
//					qo.setCode(msg.getContent().getOrderNo().substring(0,5));//DAO里目前是like
//				}
//				qo.setStatus(DealerConstants.USE);
//				List<DealerDTO> dealerList = dealerService.queryList(qo);
//				for (DealerDTO dto : dealerList) {
//					//设置sign
//					String secretKey = dto.getSecretKey();
//					String sign = MD5(secretKey, JSON.toJSONString(msg.getContent()));
//					msg.getContent().setSign(sign);
//					HgLogger.getInstance().info("yuqz", "通知:机票同步经销商信息dto:" + JSON.toJSONString(dto));
//					HttpUtil.reqForPost(dto.getNotifyUrl(), dto.getNotifyValue() + "=" + JSON.toJSONString(msg.getContent()), 30000);
//				}
//			}
//		}
//		
//		if(message instanceof SynchronizationJPRefundStateMessage){
//			BaseAmqpMessage<GNJPSyncRefundStateCommand> msg = (BaseAmqpMessage<GNJPSyncRefundStateCommand>)message;
//			if(msg.getContent()!=null){
//				DealerQO qo = new DealerQO();
//				if(msg.getContent().getOrderNo() != null){
//					qo.setCode(msg.getContent().getOrderNo().substring(0,5));//DAO里目前是like
//				}
//				qo.setStatus(DealerConstants.USE);
//				List<DealerDTO> dealerList = dealerService.queryList(qo);
//				for (DealerDTO dto : dealerList) {
//					//设置sign
//					String secretKey = dto.getSecretKey();
//					String sign = MD5(secretKey, JSON.toJSONString(msg.getContent()));
//					msg.getContent().setSign(sign);
//					HgLogger.getInstance().info("yuqz", "通知:机票同步退废票状态经销商信息dto:" + JSON.toJSONString(dto));
//					HttpUtil.reqForPost(dto.getNotifyUrl(), dto.getNotifyValue() + "=" + JSON.toJSONString(msg.getContent()), 30000);
//				}
//			}
//		}
//	}
	
	/**
	 * 求一个字符串的md5值
	 * 
	 * @param target 字符串
	 * @return md5 value
	 */
	public static String MD5(String secretKey, String msg) {
		return DigestUtils.md5Hex(secretKey + msg);
	}
}
