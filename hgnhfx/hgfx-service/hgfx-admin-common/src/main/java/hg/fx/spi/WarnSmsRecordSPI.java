package hg.fx.spi;

import java.util.List;

import hg.framework.common.base.BaseServiceProviderInterface;
import hg.fx.command.smsCodeRecord.CreateSmsCodeRecordCommand;
import hg.fx.command.smsCodeRecord.ModifySmsCodeRecordCommand;
import hg.fx.command.warnSmsCodeRecord.CreateWarnSmsRecord;
import hg.fx.domain.SmsCodeRecord;
import hg.fx.domain.WarnSmsRecord;
import hg.fx.spi.qo.SmsCodeRecordSQO;

/**
 * @类功能说明：短信预警记录SPI
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江汇购科技有限公司
 * @部门： 技术部
 * @作者： xinglj
 * @创建时间： 2016年6月1日 下午7:10:01
 * @版本： V1.0
 */
public interface WarnSmsRecordSPI extends BaseServiceProviderInterface {

	/**
	 * 
	 * @方法功能说明：添加
	 * @修改者名字：xinglj
	 * @修改时间：2016年6月1日 下午7:13:27
	 * @修改内容：
	 * @param command
	 * @return
	 */
	WarnSmsRecord create(CreateWarnSmsRecord command);
	 
}
