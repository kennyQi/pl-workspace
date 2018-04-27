

package plfx.jd.pojo.dto.ylclient.order;

import java.io.Serializable;
import java.util.List;


@SuppressWarnings("serial")
public class CreateOrderRoomDTO implements Serializable{

	/**
	 * 客户集
	 */
    protected List<CustomerDTO> customers;

	public List<CustomerDTO> getCustomers() {
		return customers;
	}

	public void setCustomers(List<CustomerDTO> customers) {
		this.customers = customers;
	}

    
}
