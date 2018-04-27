package hsl.admin.alipay.services;

import hg.log.util.HgLogger;
import hsl.admin.alipay.config.AlipayConfig;
import hsl.admin.alipay.util.AlipaySubmit;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class AlipayService {
    
    private static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";
    
    public static String refund_fastpay_by_platform_nopwd(Map<String, String> sParaTemp,String notifyUrl) throws Exception {

        sParaTemp.put("service", "refund_fastpay_by_platform_nopwd");
        sParaTemp.put("partner", AlipayConfig.partner);
        //如果不传异步通知地址,默认机票的接口地址
        if(StringUtils.isNotBlank(notifyUrl)){
            sParaTemp.put("notify_url",notifyUrl);
        }else{
            sParaTemp.put("notify_url", AlipayConfig.notify_url);
        }
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        HgLogger.getInstance().info("chenxy", "申请退款>>>发送的字符串>>>"+sParaTemp);
        return AlipaySubmit.sendPostInfo(sParaTemp, ALIPAY_GATEWAY_NEW);
    }

    @SuppressWarnings("unchecked")
	public static String query_timestamp() throws MalformedURLException, DocumentException, IOException {

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
