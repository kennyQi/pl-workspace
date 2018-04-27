package plfx.yl.ylclient.yl.util;

import java.util.ArrayList;
import java.util.List;

import util.Tool;
import elong.Contact;
import elong.CreateOrderRoom;
import elong.CreditCard;
import elong.Customer;
import elong.EnumIdType;

public class OrderUtil {
	public static List<CreateOrderRoom> getRooms() {

		List<Customer> customers = new ArrayList<Customer>(1);
		Customer c = new Customer();
		c.setName("Jack White");
		customers.add(c);

		List<CreateOrderRoom> rooms = new ArrayList<CreateOrderRoom>(1);
		CreateOrderRoom room = new CreateOrderRoom();
		room.setCustomers(customers);
		rooms.add(room);

		return rooms;
	}

	public static Contact getContact() {
		Contact c = new Contact();
		c.setName("Ms White");
		c.setMobile("18600000001");

		return c;
	}

	public static CreditCard getCreditCard() {
		CreditCard cc = new CreditCard();
		String appKey = "f56b4a50c43dac0dc15ffc4b85669785";
		String num = "4033910000000000"; // CREDIT CARD FOR TEST ENV
		long ts = System.currentTimeMillis() / 1000;
		String key = appKey.substring(appKey.length() - 8, appKey.length());
		//System.out.println("Key: " + key);

		try {
			num = ts + "#" + num;

			//System.out.println("raw data before DES: " + num);
			num = Tool.encryptDES(num, key);
			//System.out.println("DES encrypt result: " + num);
			//System.out.println();

			cc.setCVV(Tool.encryptDES(ts + "#" + "007", key));
			cc.setExpirationMonth(10);
			cc.setExpirationYear(2016);
			cc.setHolderName(Tool.encryptDES(ts + "#" + "NEED Real HolderName",
					key));
			cc.setIdType(EnumIdType.IdentityCard);
			cc.setIdNo(Tool.encryptDES(ts + "#" + "110101198205169939", key));
			cc.setNumber(num);

		} catch (Exception e) {
			// TODO .....
			e.printStackTrace();
		}

		// cc = null;
		return cc;
	}
}
