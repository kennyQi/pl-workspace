package hg.demo.member.common.spi;

import hg.demo.member.common.domain.model.OpLog;
import hg.demo.member.common.domain.result.Result;
import hg.demo.member.common.spi.command.userinfo.CreateOpLogCommand;
import hg.framework.common.base.BaseServiceProviderInterface;

/**
 * 
* <p>Title: UserSPI</p>
* <p>Description: </p>
* <p>Company: </p> 
* @author xuww
* @date 2016年6月27日 上午11:45:09
 */
public interface OpLogSPI extends BaseServiceProviderInterface {

	Result<OpLog> createOpLog(CreateOpLogCommand createOpLogCommand);
	
}
