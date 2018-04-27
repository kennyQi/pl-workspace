package hsl.spi.inter.account;

import hsl.pojo.command.account.GrantCodeRecordCommand;
import hsl.pojo.dto.account.GrantCodeRecordDTO;
import hsl.pojo.dto.account.PayCodeDTO;
import hsl.pojo.qo.account.GrantCodeRecordQO;
import hsl.spi.inter.BaseSpiService;

import java.util.List;

public interface GrantCodeRecordService extends BaseSpiService<GrantCodeRecordDTO,GrantCodeRecordQO>{
	/**
	 * 生成充值码
	 */
	public List<PayCodeDTO> issueRechargeCode(GrantCodeRecordCommand grantCodeRecordCommand)throws Exception;
	/**
	 * 审核修改充值码状态
	 */
	public String examineRechargeCode(GrantCodeRecordCommand grantCodeRecordCommand);
	/**
	 * 根据商业合作伙伴id查询充值码列表
	*/
	public List<GrantCodeRecordDTO> queryList(GrantCodeRecordQO grantCodeRecordQO);
	
	
}
