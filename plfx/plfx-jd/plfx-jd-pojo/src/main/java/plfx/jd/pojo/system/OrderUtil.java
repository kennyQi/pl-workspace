package plfx.jd.pojo.system;
import java.util.ArrayList;
import java.util.List;

import plfx.jd.pojo.system.enumConstants.EnumGender;
import plfx.jd.pojo.dto.ylclient.order.ContactDTO;
import plfx.jd.pojo.dto.ylclient.order.CreateOrderRoomDTO;
import plfx.jd.pojo.dto.ylclient.order.CustomerDTO;

public class OrderUtil {
	public static List<CreateOrderRoomDTO> getRooms() {

		List<CustomerDTO> customers = new ArrayList<CustomerDTO>(1);
		CustomerDTO c = new CustomerDTO();
		c.setName("王宝强");
		c.setEmail("14661233@qq.com");
		c.setPhone("18271260983");
		c.setMobile("18271260983");
		c.setNationality("chinese");
		c.setFax("123456");
		c.setGender(EnumGender.Female);
		customers.add(c);

		List<CreateOrderRoomDTO> rooms = new ArrayList<CreateOrderRoomDTO>(1);
		CreateOrderRoomDTO room = new CreateOrderRoomDTO();
		room.setCustomers(customers);
		rooms.add(room);

		return rooms;
	}

	public static ContactDTO getContact() {
		ContactDTO c = new ContactDTO();
		c.setName("王宝强");
		c.setMobile("18271260983");
        c.setGender(EnumGender.Female);//加个性别
		return c;
	}

}
