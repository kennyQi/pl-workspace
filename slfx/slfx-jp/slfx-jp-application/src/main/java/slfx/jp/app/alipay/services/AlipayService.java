package slfx.jp.app.alipay.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import slfx.jp.app.alipay.config.AlipayConfig;
import slfx.jp.app.alipay.util.AlipaySubmit;

public class AlipayService {
    
    private static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";
    
    public static String refund_fastpay_by_platform_nopwd(Map<String, String> sParaTemp) throws Exception {

        sParaTemp.put("service", "refund_fastpay_by_platform_nopwd");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("notify_url", AlipayConfig.notify_url);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        return AlipaySubmit.sendPostInfo(sParaTemp, ALIPAY_GATEWAY_NEW);
    }

    @SuppressWarnings("unchecked")
	public static String query_timestamp() throws MalformedURLException,
                                                        DocumentException, IOException {

        String strUrl = ALIPAY_GATEWAY_NEW + "service=query_timestamp&partner=" + AlipayConfig.partner;
        StringBuffer result = new StringBuffer();

        SAXReader reader = new SAXReader();
        Document doc = reader.read(new URL(strUrl).openStream());

        List<Node> nodeList = doc.selectNodes("//alipay/*");

        for (Node node : nodeList) {
            if (node.getName().equals("is_success") && node.getText().equals("T")) {
                List<Node> nodeList1 = doc.selectNodes("//response/timestamp/*");
                for (Node node1 : nodeList1) {
                    result.append(node1.getText());
                }
            }
        }

        return result.toString();
    }
}
