import java.util.HashMap;
import java.util.Map;



public class TestSign {
public static void main(String[] args) {
//	Calendar now=Calendar.getInstance();
//	int day_of_week = now.get(Calendar.DAY_OF_WEEK);
//	int hour = now.get(Calendar.HOUR_OF_DAY);
//	int minute = now.get(Calendar.MINUTE);
//	System.out.println(day_of_week);
//	System.out.println(hour);
//	System.out.println(minute);
	Map<String, String> params = new HashMap<String, String>();
	//退废票成功
	params.put("orderid", "T2015090820165");
	params.put("airId", "9999635853742");
	params.put("refundPrice", "845.0");
	params.put("refundStatus", "1");
	params.put("refuseMemo", "zytp");
	params.put("procedures", "200.0");
	params.put("type", "4");
	//出票成功
//	params.put("orderid", "T2015090820165");
//	params.put("type", "1");
//	params.put("passengerName", "朱睿");
//	params.put("airId", "9999635853742");
	System.out.println(SignTool.sign(params, "zjply$sjkj301"));
//	System.out.println(params.get("getOrderid"));
}
}
