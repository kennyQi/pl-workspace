package slfx.jp.app.service.spi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import slfx.jp.app.service.local.PlatformAskOrderTicketLocalService;
import slfx.jp.command.client.YGAskOrderTicketCommand;
import slfx.jp.spi.inter.PlatformAskOrderTicketService;

/**
 * 
 * @类功能说明：平台请求出票SERVICE实现
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:47:40
 * @版本：V1.0
 *
 */
@Service("jpPlatformAskOrderTicketService")
public class PlatformAskOrderTicketServiceImpl implements PlatformAskOrderTicketService {

	@Autowired
	private PlatformAskOrderTicketLocalService localPlatformAskOrderTicketService;
	
	@Override
	public boolean askOrderTicket(YGAskOrderTicketCommand command) {
		
		return localPlatformAskOrderTicketService.askOrderTicket(command);
	}

}
