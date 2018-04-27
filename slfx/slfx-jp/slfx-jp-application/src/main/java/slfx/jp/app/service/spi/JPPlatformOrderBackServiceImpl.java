package slfx.jp.app.service.spi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import slfx.jp.app.component.base.BaseJpSpiServiceImpl;
import slfx.jp.app.service.local.JPOrderBackLocalService;
import slfx.jp.pojo.dto.order.JPOrderBackDTO;
import slfx.jp.qo.admin.PlatformOrderBackQO;
import slfx.jp.spi.inter.JPPlatformOrderBackService;

/**
 * 
 * @类功能说明：平台退废订单SERVICE实现
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：qiuxianxiang
 * @创建时间：2014年7月31日下午4:47:08
 * @版本：V1.0
 *
 */
@Service("jpPlatformOrderBackService")
public class JPPlatformOrderBackServiceImpl extends BaseJpSpiServiceImpl<JPOrderBackDTO, PlatformOrderBackQO, JPOrderBackLocalService>
		implements JPPlatformOrderBackService {

	@Autowired
	JPOrderBackLocalService jPOrderBackLocalService;
	
	@Override
	protected JPOrderBackLocalService getService() {
		return jPOrderBackLocalService;
	}

	@Override
	protected Class<JPOrderBackDTO> getDTOClass() {
		return JPOrderBackDTO.class;
	}
}
