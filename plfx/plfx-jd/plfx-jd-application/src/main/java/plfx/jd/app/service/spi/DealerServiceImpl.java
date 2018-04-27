package plfx.jd.app.service.spi;

import hg.common.component.BaseQo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.jd.app.component.base.BaseJDSpiServiceImpl;
import plfx.jd.app.service.local.HotelDealerLocalService;
import plfx.jd.pojo.dto.plfx.order.DealerDTO;
import plfx.jd.spi.inter.DealerService;

/**
 * 
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：qiuxianxiang
 * @创建时间：2014年8月4日下午3:39:50
 * @版本：V1.0
 * 
 */

@Service("dealerService")
public class DealerServiceImpl extends BaseJDSpiServiceImpl<DealerDTO, BaseQo, HotelDealerLocalService> implements DealerService {

	@Autowired
	HotelDealerLocalService dealerLocalService;

	@Override
	protected HotelDealerLocalService getService() {
		return dealerLocalService;
	}

	@Override
	protected Class<DealerDTO> getDTOClass() {
		return DealerDTO.class;
	}


	
}
