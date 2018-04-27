package hg.demo.member.service.domain.manager.fx;

import java.util.Date;

import hg.demo.member.common.domain.model.AuthUser;
import hg.framework.common.base.BaseDomainManager;
import hg.framework.common.util.UUIDGenerator;
import hg.fx.command.reserveRecord.AuditReserveRecordCommand;
import hg.fx.command.reserveRecord.CreateReserveRecordCommand;
import hg.fx.domain.Distributor;
import hg.fx.domain.ReserveRecord;

/**
 * @author cangs
 */
public class ReserveRecordManager extends BaseDomainManager<ReserveRecord>{

	public ReserveRecordManager(ReserveRecord entity) {
		super(entity);
	}

	/**
	 * 新增里程余额变更记录
	 */
	public ReserveRecordManager create(CreateReserveRecordCommand command,Distributor distributor,AuthUser authUser){
		entity.setId(UUIDGenerator.getUUID());
		entity.setDistributor(distributor);
		entity.setIncrement(command.getIncrement());
		entity.setTradeDate(new Date());
		entity.setType(command.getType());
		entity.setProdName(command.getProdName());
		/**
		 * type=3,4时 商品名字=备付金充值
		 * type=5时 商品名字=月末返利
		 * 其他类型名字为product里的商品名称
		 * */
		if(command.getType().equals(ReserveRecord.RECORD_TYPE_RECHARGE)||command.getType().equals(ReserveRecord.RECORD_TYPE_RECHARGE_ONLINE)){
			entity.setProdName(ReserveRecord.RECORD_TYPE_RECHARGE_MESSAGE);
			if(command.getType().equals(ReserveRecord.RECORD_TYPE_RECHARGE)){
				entity.setProvePath(command.getProvePath());
				entity.setApplyDate(new Date());
				entity.setCheckStatus(ReserveRecord.CHECK_STATUS_WAITTING);
				entity.setOperator(authUser);
			}
		}else if(command.getType().equals(ReserveRecord.RECORD_TYPE_REBATE)){
			entity.setProdName(ReserveRecord.RECORD_TYPE_REBATE_MESSAGE);
		}
		entity.setOrderCode(command.getOrderCode());
		entity.setTradeNo(command.getTradeNo());
		entity.setCardNo(command.getCardNo());
		entity.setStatus(command.getStatus());
		return this;
	}
	
	/**
	 * 里程余额变更记录审核
	 */
	public ReserveRecordManager audit(AuditReserveRecordCommand command){
		if(entity.getCheckStatus().equals(ReserveRecord.CHECK_STATUS_WAITTING)&&entity.getType().equals(ReserveRecord.RECORD_TYPE_RECHARGE)){
			entity.setCheckStatus(command.getCheckStatus());
			if(command.getCheckStatus().equals(AuditReserveRecordCommand.CHECK_STATUS_PASS)){
				// 添加里程 审核通过后 修改记录status为充值成功
				entity.setStatus(ReserveRecord.RECORD_STATUS_CHONGZHI_SUCC);
			}
		}
		return this;
		
	}
}
