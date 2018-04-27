package slfx.jp.app.service.spi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import slfx.jp.app.component.base.BaseJpSpiServiceImpl;
import slfx.jp.app.service.local.JPOrderLocalService;
import slfx.jp.app.service.local.JPOrderLogLocalService;
import slfx.jp.command.admin.orderLog.CreateJPOrderLogCommand;
import slfx.jp.domain.model.order.JPOrder;
import slfx.jp.pojo.dto.order.JPOrderLogDTO;
import slfx.jp.qo.admin.JPOrderLogQO;
import slfx.jp.spi.inter.JPOrderLogService;
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
	JPOrderLocalService jpOrderLocalService;
	
	/**
	 * 
	 */
	@Override
	public boolean create(CreateJPOrderLogCommand command) {
		JPOrder jpOrder = jpOrderLocalService.get(command.getJpOrderId());
		if(jpOrder != null){
			return jpOrderLogLocalService.create(command,jpOrder);
		}else{
			return false;
		}
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
