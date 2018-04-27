
package plfx.jd.pojo.dto.ylclient.order;

import java.io.Serializable;
import java.util.List;

public class OrderRoomDTO implements Serializable{

	/***
	 * 客户集
	 */
    protected List<CustomerForOrderDTO> customers;

	public List<CustomerForOrderDTO> getCustomers() {
		return customers;
	}

	public void setCustomers(List<CustomerForOrderDTO> customers) {
		this.customers = customers;
	}

    

}
