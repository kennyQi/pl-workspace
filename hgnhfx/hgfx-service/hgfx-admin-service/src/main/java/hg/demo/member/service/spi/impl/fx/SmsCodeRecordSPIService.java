package hg.demo.member.service.spi.impl.fx;

import java.util.List;

import hg.demo.member.service.dao.hibernate.fx.SmsCodeRecordDAO;
import hg.demo.member.service.domain.manager.fx.SmsCodeRecordManager;
import hg.demo.member.service.qo.hibernate.fx.SmsCodeRecordQO;
import hg.framework.service.component.base.BaseService;
import hg.fx.command.smsCodeRecord.CreateSmsCodeRecordCommand;
import hg.fx.command.smsCodeRecord.ModifySmsCodeRecordCommand;
import hg.fx.domain.SmsCodeRecord;
import hg.fx.spi.SmsCodeRecordSPI;
import hg.fx.spi.qo.SmsCodeRecordSQO;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @类功能说明：doto
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江汇购科技有限公司
 * @部门： 技术部
 * @作者： zhouwei
 * @创建时间： 2016年6月1日 下午7:29:25
 * @版本： V1.0
 */
@Transactional
@Service("smsCodeRecordSPIService")
public class SmsCodeRecordSPIService extends BaseService implements SmsCodeRecordSPI {

	@Autowired
	private SmsCodeRecordDAO smsCodeRecordDAO;

	@Override
	public SmsCodeRecord create(CreateSmsCodeRecordCommand command) {
		// 保存的时候 同一个手机号码 在失效时间内 仅保存一条记录吧
		SmsCodeRecord smsCodeRecord = checkCodeInvalid(command);
		if(smsCodeRecord == null){
//			System.out.println("不存在：新建");
			return smsCodeRecordDAO.save(new SmsCodeRecordManager(new SmsCodeRecord()).create(command).get());
		} else{
//			System.out.println("存在：先修改存在的状态为已使用");
			ModifySmsCodeRecordCommand cmd = new ModifySmsCodeRecordCommand();
			cmd.setId(smsCodeRecord.getId());
			modifyUsedStatus(cmd);
			
//			System.out.println("存在：新建");
			return smsCodeRecordDAO.save(new SmsCodeRecordManager(new SmsCodeRecord()).create(command).get());
		}	
	}

	/**
	 * 
	 * @方法功能说明：判断command的createdate时间是否已经存在code,null不存在,反之存在
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月2日 上午10:44:26
	 * @修改内容：
	 * @param command
	 * @return
	 */
	private SmsCodeRecord checkCodeInvalid(CreateSmsCodeRecordCommand command){
		//查询该手机号的最近的一条没有被使用的code
		SmsCodeRecordSQO sqo = new SmsCodeRecordSQO();
		sqo.setMobile(command.getMobile());
		sqo.setType(command.getType());
		sqo.setUsed(false);
		SmsCodeRecord smsCodeRecord = queryFirst(sqo);
		
		if(StringUtils.isEmpty(smsCodeRecord))
			return null;
		//判断这条信息的创建时间，失效时间区间  与 command提供的创建时间是否包含
		if(smsCodeRecord.getCreateDate().getTime() > command.getCreateDate().getTime())
			return null;
		if(smsCodeRecord.getInvalidDate().getTime() < command.getCreateDate().getTime())
			return null;
		
		return smsCodeRecord;
	}

	@Override
	public List<SmsCodeRecord> queryList(SmsCodeRecordSQO sqo) {
		return smsCodeRecordDAO.queryList(SmsCodeRecordQO.build(sqo));
	}

	@Override
	public SmsCodeRecord queryFirst(SmsCodeRecordSQO sqo) {
		return smsCodeRecordDAO.queryFirst(SmsCodeRecordQO.build(sqo));
	}

	@Override
	public SmsCodeRecord modifyUsedStatus(ModifySmsCodeRecordCommand command) {
		SmsCodeRecord smsCodeRecord = smsCodeRecordDAO.get(command.getId());
		return smsCodeRecordDAO.update(new SmsCodeRecordManager(smsCodeRecord).modifyUsedStatus(command).get());
	}

	@Override
	public Integer queryCount(SmsCodeRecordSQO sqo) {
		return smsCodeRecordDAO.queryCount(SmsCodeRecordQO.build(sqo));
	}

	@Override
	public void updateCode(String id, String code) {
		SmsCodeRecord smsCodeRecord = smsCodeRecordDAO.get(id);
		smsCodeRecordDAO.update(new SmsCodeRecordManager(smsCodeRecord).updateCode(id, code).get());
	}

	@Override
	public void deleteSMSCodeRecord(String id) {
		SmsCodeRecord smsCodeRecord = smsCodeRecordDAO.get(id);
		smsCodeRecordDAO.delete(smsCodeRecord);
	}
	
}
