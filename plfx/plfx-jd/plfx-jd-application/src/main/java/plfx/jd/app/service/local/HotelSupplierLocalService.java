package plfx.jd.app.service.local;

import hg.common.component.BaseQo;
import hg.common.component.BaseServiceImpl;
import hg.log.repository.DomainEventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.jd.app.dao.HotelSupplierDAO;
import plfx.jd.domain.model.crm.HotelSupplier;

@Service
@Transactional
public class HotelSupplierLocalService extends BaseServiceImpl<HotelSupplier, BaseQo, HotelSupplierDAO>{

	@Autowired
	private DomainEventRepository domainEventRepository;

	@Autowired
	HotelSupplierDAO hotelSupplierDAO;
	
	@Override
	protected HotelSupplierDAO getDao() {
		return hotelSupplierDAO;
	}
	
}
