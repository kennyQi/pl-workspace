package hg.demo.member.service.domain.manager.fx;

import hg.framework.common.base.BaseDomainManager;
import hg.framework.common.util.UUIDGenerator;
import hg.fx.command.smsCodeRecord.CreateSmsCodeRecordCommand;
import hg.fx.command.smsCodeRecord.ModifySmsCodeRecordCommand;
import hg.fx.domain.SmsCodeRecord;

/**
 * @类功能说明：doto
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江汇购科技有限公司
 * @部门： 技术部
 * @作者： zhouwei
 * @创建时间： 2016年6月1日 下午7:23:37
 * @版本： V1.0
 */
public class SmsCodeRecordManager extends BaseDomainManager<SmsCodeRecord> {

	public SmsCodeRecordManager(SmsCodeRecord entity) {
		super(entity);
	}
	
	public SmsCodeRecordManager create(CreateSmsCodeRecordCommand command){
		entity.setId(UUIDGenerator.getUUID());
		entity.setCode(command.getCode());
		entity.setMobile(command.getMobile());
		entity.setCreateDate(command.getCreateDate());
		entity.setInvalidDate(command.getInvalidDate());
		entity.setUsed(false);
		entity.setType(command.getType());
		return this;
	}
	
	/**
	 * 
	 * @方法功能说明：组装修改被使用的bean参数
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月2日 下午4:41:48
	 * @修改内容：
	 * @param command
	 * @return
	 */
	public SmsCodeRecordManager modifyUsedStatus(ModifySmsCodeRecordCommand command){
		entity.setId(command.getId());
		entity.setUsed(true);
		return this;
	}
	
	public SmsCodeRecordManager updateCode(String id,String code){
		entity.setId(id);
		entity.setCode(code);
		return this;
	}

}
