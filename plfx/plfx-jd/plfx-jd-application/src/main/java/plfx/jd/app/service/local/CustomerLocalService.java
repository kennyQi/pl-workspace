package plfx.jd.app.service.local;

import hg.common.component.BaseQo;
import hg.common.component.BaseServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.jd.domain.model.order.Customer;
import plfx.jd.domain.model.order.HotelOrder;
import plfx.yl.ylclient.yl.command.OrderCreateCommand;
import plfx.jd.app.dao.CustomerDAO;
import plfx.jd.pojo.dto.ylclient.order.ContactDTO;
import plfx.jd.pojo.dto.ylclient.order.CreateOrderRoomDTO;
import plfx.jd.pojo.dto.ylclient.order.CustomerDTO;
@Service
@Transactional
public class CustomerLocalService extends BaseServiceImpl<Customer, BaseQo, CustomerDAO>{
	@Autowired
	private CustomerDAO customerDAO;
	
	public List<Customer> batchSaveCustomer(List<CreateOrderRoomDTO>  list,HotelOrder order){
		List<Customer> customerList = new ArrayList<Customer>();
		for (CreateOrderRoomDTO createOrderRoomDTO : list) {
			for(CustomerDTO customerDTO : createOrderRoomDTO.getCustomers()){
				Customer customer = new Customer();
				customer.create(customerDTO,order,"0");
				customer = save(customer);
				customerList.add(customer);
			}
		}
		return customerList;
	}
	@Override
	protected CustomerDAO getDao() {
		return customerDAO;
	}
	public void saveContact(ContactDTO contactDTO,HotelOrder order) {
		Customer customer = new Customer();
		customer.create(contactDTO, order);
		save(customer);
	}
	
}
