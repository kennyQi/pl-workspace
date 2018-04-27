package plfx.gnjp.app.service.spi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.gnjp.app.service.local.GNJPOrderLocalService;
import plfx.gnjp.app.service.local.JPOrderLogLocalService;
import plfx.gnjp.domain.model.order.GNJPOrder;
import plfx.jp.app.service.spi.base.BaseJpSpiServiceImpl;
import plfx.jp.command.admin.jpOrderLog.CreateJPOrderLogCommand;
import plfx.jp.spi.inter.JPOrderLogService;
import plfx.yeexing.pojo.dto.order.JPOrderLogDTO;
import plfx.yeexing.qo.admin.JPOrderLogQO;
/**
 * 
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江票量云数据科技股份有限公司
 * @部门：技术部
 * @作者：caizhenghao
 * @创建时间：2015年1月21日上午11:26:24
 * @版本：V1.0
 *
 */
@Service("jpOrderLogService")
public class JPOrderLogServiceImpl extends
		BaseJpSpiServiceImpl<JPOrderLogDTO, JPOrderLogQO, JPOrderLogLocalService> implements
		JPOrderLogService {

	@Autowired
	JPOrderLogLocalService jpOrderLogLocalService;
	@Autowired
	GNJPOrderLocalService jpOrderLocalService;
	
	/**
	 * 
	 */
	@Override
	public boolean create(CreateJPOrderLogCommand command) {
		
		return jpOrderLogLocalService.create(command);
		
	}


	@Override
	protected Class<JPOrderLogDTO> getDTOClass() {
		return JPOrderLogDTO.class;
	}


	@Override
	protected JPOrderLogLocalService getService() {
		return jpOrderLogLocalService;
	}

}
