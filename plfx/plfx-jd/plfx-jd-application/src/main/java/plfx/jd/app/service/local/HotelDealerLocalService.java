package plfx.jd.app.service.local;

import hg.common.component.BaseQo;
import hg.common.component.BaseServiceImpl;
import hg.log.repository.DomainEventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.jd.app.dao.HotelDealerDAO;
import plfx.jd.domain.model.crm.HotelDealer;

/**
 * 
 * @类功能说明：LOCAL订单SERVICE(操作数据库)实现
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tuhualiang
 * @创建时间：2014年7月31日下午4:42:43
 * @版本：V1.0
 *
 */
@Service
@Transactional
public class HotelDealerLocalService extends BaseServiceImpl<HotelDealer, BaseQo, HotelDealerDAO>{
	
	@Autowired
	private DomainEventRepository domainEventRepository;

	@Autowired
	HotelDealerDAO hotelDealerDAO;
	
	@Override
	protected HotelDealerDAO getDao() {
		return hotelDealerDAO;
	}
	
}
