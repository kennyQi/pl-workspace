package hsl.app.service.local.hotel;

import java.util.List;

import hg.common.component.BaseServiceImpl;
import hsl.app.dao.hotel.CustomerDao;
import hsl.domain.model.hotel.order.Customer;
import hsl.pojo.qo.hotel.CustomerQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerLocalService extends BaseServiceImpl<Customer, CustomerQO, CustomerDao>{
	@Autowired
	private CustomerDao customerDao;
	
	@Override
	protected CustomerDao getDao() {
		return this.customerDao;
	}
	
	public boolean saveCustomerList(List<Customer> customers){
		this.customerDao.saveList(customers);
		return true;
	}

}
