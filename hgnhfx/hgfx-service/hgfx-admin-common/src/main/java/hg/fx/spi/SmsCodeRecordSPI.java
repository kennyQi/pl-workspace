package hg.fx.spi;

import java.util.List;

import hg.framework.common.base.BaseServiceProviderInterface;
import hg.fx.command.smsCodeRecord.CreateSmsCodeRecordCommand;
import hg.fx.command.smsCodeRecord.ModifySmsCodeRecordCommand;
import hg.fx.domain.SmsCodeRecord;
import hg.fx.spi.qo.SmsCodeRecordSQO;

/**
 * @类功能说明：短信验证码记录SPI
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江汇购科技有限公司
 * @部门： 技术部
 * @作者： zhouwei
 * @创建时间： 2016年6月1日 下午7:10:01
 * @版本： V1.0
 */
public interface SmsCodeRecordSPI extends BaseServiceProviderInterface {
	
	/**
	 * 
	 * @方法功能说明：更新验证码
	 * @修改者名字：cangs
	 * @修改时间：2016年7月20日下午5:28:44
	 * @修改内容：
	 * @参数：@param id
	 * @参数：@param code
	 * @return:void
	 * @throws
	 */
	void updateCode(String id,String code);
	
	/**
	 * 
	 * @方法功能说明：删除记录
	 * @修改者名字：cangs
	 * @修改时间：2016年7月20日下午5:28:56
	 * @修改内容：
	 * @参数：@param id
	 * @return:void
	 * @throws
	 */
	void deleteSMSCodeRecord(String id);

	/**
	 * 
	 * @方法功能说明：添加
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月1日 下午7:13:27
	 * @修改内容：
	 * @param command
	 * @return
	 */
	SmsCodeRecord create(CreateSmsCodeRecordCommand command);
	
	/**
	 * 
	 * @方法功能说明：修改是否被使用的状态
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月2日 下午4:39:15
	 * @修改内容：
	 * @param command
	 * @return
	 */
	SmsCodeRecord modifyUsedStatus(ModifySmsCodeRecordCommand command);
	
	/**
	 * 
	 * @方法功能说明：查询
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月2日 上午9:43:27
	 * @修改内容：
	 * @param sqo
	 * @return
	 */
	List<SmsCodeRecord> queryList(SmsCodeRecordSQO sqo);
	
	/**
	 * 
	 * @方法功能说明：查询第一条
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月2日 上午10:32:13
	 * @修改内容：
	 * @param sqo
	 * @return
	 */
	SmsCodeRecord queryFirst(SmsCodeRecordSQO sqo);
	
	/**
	 * 
	 * @方法功能说明：查询条数
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月2日 上午10:32:13
	 * @修改内容：
	 * @param sqo
	 * @return
	 */
	Integer queryCount(SmsCodeRecordSQO sqo);
}
