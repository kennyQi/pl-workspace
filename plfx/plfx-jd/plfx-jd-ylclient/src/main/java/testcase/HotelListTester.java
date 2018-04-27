package testcase;

import java.util.Date;

import testcase.result.HotelListResult;
import elong.HotelListCondition;

public class HotelListTester extends BaseTester<HotelListCondition, HotelListResult> {

	@Override
	public String method() {
		return "hotel.list";
	}

	@Override
	public boolean isRequiredSSL() {
		return false;
	}
	
	@Override
	public HotelListCondition getConditon() {
		HotelListCondition condition = new HotelListCondition();
				
			
			Date date = new Date();
			Date date2 = util.Tool.addDate(date, 1);
			
			condition.setArrivalDate(date);
			condition.setDepartureDate(date2);
			condition.setCityId("0101");
			//condition.setCityId("10101129");
			condition.setPaymentType(elong.EnumPaymentType.SelfPay);
			condition.setProductProperties(elong.EnumProductProperty.All);
			condition.setSort(elong.EnumSortType.Default);
		
		
		return condition;
	}


	
}
