package hsl.admin.alipay.services;
import hg.log.util.HgLogger;
import hsl.admin.alipay.config.AlipayConfig;
import hsl.admin.alipay.util.AlipaySubmit;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
/**
* @类功能说明：支付宝退款工具类（新）
* @类修改者：
* @公司名称： 浙江票量云科技有限公司
* @部门： 技术部
* @作者： chenxy
* @创建时间：  2015-12-08 13:49:12
* @版本： V1.0
*/
public class AlipayRefundService {
    
    private static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";
    
    public static String refund_fastpay_by_platform_nopwd(Map<String, String> sParaTemp) throws Exception {
        sParaTemp.put("service", "refund_fastpay_by_platform_nopwd");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("notify_url", AlipayConfig.notify_url);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        HgLogger.getInstance().info("chenxy", "申请退款>>>发送的字符串>>>"+sParaTemp);
        return AlipaySubmit.sendPostInfo(sParaTemp, ALIPAY_GATEWAY_NEW);
    }

    public void buildRefundString(){

    }
}
