package Sign;

import java.util.HashMap;
import java.util.Map;


public class Input{
public static void main(String[] args) {
	Map<String, String> params = new HashMap<String, String>();
	params.put("orderid", "T2015111813799");
	params.put("airId", "7818515418726");
	params.put("refundPrice", "563.0");
	params.put("refundStatus", "1");
	params.put("refuseMemo", "success");
	params.put("procedures", "27.0");
	params.put("type", "4");
	System.out.println(SignTool.sign(params, "zjply$sjkj301"));
}
}
