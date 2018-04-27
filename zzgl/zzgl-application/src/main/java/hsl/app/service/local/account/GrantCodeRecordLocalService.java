package hsl.app.service.local.account;
import hg.common.component.BaseServiceImpl;
import hg.common.util.EntityConvertUtils;
import hg.common.util.UUIDGenerator;
import hsl.app.dao.account.GrantCodeRecordDao;
import hsl.app.dao.account.PayCodeDao;
import hsl.domain.model.user.account.GrantCodeRecord;
import hsl.domain.model.user.account.PayCode;
import hsl.pojo.command.account.GrantCodeRecordCommand;
import hsl.pojo.dto.account.GrantCodeRecordDTO;
import hsl.pojo.qo.account.GrantCodeRecordQO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class GrantCodeRecordLocalService extends BaseServiceImpl<GrantCodeRecord, GrantCodeRecordQO, GrantCodeRecordDao>{
	@Autowired
	private GrantCodeRecordDao grantCodeRecordDao;

	@Autowired
	private PayCodeDao payCodeDao;
	/**
	 * @throws Exception 
	 * @方法功能说明：发放充值码
	 * @创建者名字：zhaows
	 * @创建时间：2015-8-31下午4:07:49
	 * @参数：@param grantCodeRecordCommand
	 * @return:void
	 * @throws
	 */
	public List<PayCode> issueRechargeCode(GrantCodeRecordCommand grantCodeRecordCommand) throws Exception {
		GrantCodeRecord grantCodeRecord=new GrantCodeRecord();
		grantCodeRecord.creatGrantCodeRecord(grantCodeRecordCommand);
		grantCodeRecordDao.save(grantCodeRecord);
		List<PayCode> payCodeList=new ArrayList<PayCode>();
		int payCodeNum=grantCodeRecordCommand.getPayCodeNum();
		for(int i=0;i<payCodeNum;i++){
			String uuid=UUIDGenerator.getUUID();
			Date date =new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			PayCode payCode=new PayCode();
			payCode.setCreateDate(date);
			payCode.setId(uuid);
			payCode.setPrice(grantCodeRecordCommand.getPayCodeMoney());
			//payCode.setSource(grantCodeRecordCommand.getPayCodes().getSource());
			payCode.setGrantCodeRecord(grantCodeRecord);
			String md5=MD5Security.md5(uuid+grantCodeRecordCommand.getPayCodeMoney().toString()+sdf.format(date));
			payCode.setCode(md5);//设置充值码code
			payCodeDao.save(payCode);
			payCodeList.add(payCode);
		}
		return payCodeList;
	}
	/**
	 * @方法功能说明：修改充值码状态
	 * @创建者名字：zhaows
	 * @创建时间：2015-8-31下午4:08:22
	 * @参数：@param grantCodeRecordDTO
	 * @return:void
	 * @throws
	 */
	public String examineRechargeCode(GrantCodeRecordCommand grantCodeRecordCommand) {
		//GrantCodeRecord grantCodeRecord=EntityConvertUtils.convertDtoToEntity(grantCodeRecordCommand, GrantCodeRecord.class);
		GrantCodeRecord grantCodeRecord=grantCodeRecordDao.get(grantCodeRecordCommand.getId());
		if(grantCodeRecord!=null){
			System.out.println(grantCodeRecordCommand.getStatus());
			grantCodeRecord.setStatus(grantCodeRecordCommand.getStatus());
			grantCodeRecordDao.update(grantCodeRecord);
			return "success";
		}else{
			return "error";
		}


	}

	public List<GrantCodeRecordDTO> queryListGrantCodeRecordDTO(GrantCodeRecordQO grantCodeRecordQO){
		List<GrantCodeRecord> grantCodeRecords=grantCodeRecordDao.queryList(grantCodeRecordQO);
		List<GrantCodeRecordDTO> listGrantCodeRecordDTO=new ArrayList<GrantCodeRecordDTO>();
		for(GrantCodeRecord grantCodeRecord:grantCodeRecords){
			Hibernate.initialize(grantCodeRecord.getPayCodes());
			GrantCodeRecordDTO dto=EntityConvertUtils.convertDtoToEntity(grantCodeRecord, GrantCodeRecordDTO.class);
			listGrantCodeRecordDTO.add(dto);
		}
		return listGrantCodeRecordDTO;

	}

	@Override
	protected GrantCodeRecordDao getDao() {
		return grantCodeRecordDao;
	}
}
