package hsl.admin.alipay.util;

import hsl.admin.alipay.config.AlipayConfig;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class AlipayNotify {

    private static final String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";

    public static boolean verify(Map<String, String> params) {
        String mysign = getMysign(params);
        String responseTxt = "true";
	if(params.get("notify_id") != null) {responseTxt = verifyResponse(params.get("notify_id"));}
        String sign = "";
	if(params.get("sign") != null) { sign = params.get("sign"); }

        if (mysign.equals(sign) && responseTxt.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    private static String getMysign(Map<String, String> Params) {
        Map<String, String> sParaNew = AlipayCore.paraFilter(Params);
        String mysign = AlipayCore.buildMysign(sParaNew);
        return mysign;
    }

    private static String verifyResponse(String notify_id) {

        String partner = AlipayConfig.partner;
        String veryfy_url = HTTPS_VERIFY_URL + "partner=" + partner + "&notify_id=" + notify_id;

        return checkUrl(veryfy_url);
    }

    private static String checkUrl(String urlvalue) {
        String inputLine = "";

        try {
            URL url = new URL(urlvalue);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection
                .getInputStream()));
            inputLine = in.readLine().toString();
        } catch (Exception e) {
            e.printStackTrace();
            inputLine = "";
        }

        return inputLine;
    }
}
