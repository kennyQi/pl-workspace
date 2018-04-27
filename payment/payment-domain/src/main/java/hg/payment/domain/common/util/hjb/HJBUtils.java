package hg.payment.domain.common.util.hjb;

import hg.common.util.DateUtil;
import hg.log.util.HgLogger;

import java.io.StringWriter;
import java.util.Date;
import java.util.Random;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;


public class HJBUtils {

	/**
	 * 生成汇金宝请求报文中的MessageId
	 * @return
	 */
    public static String buildMessageId(){
		
		String messageId = "YCTECHMESSAGE";
		Random r = new Random();
		int rd = r.nextInt(100000);
		if(rd < 0)
		{
			rd = -rd;
		}
		String rdStr = String.valueOf(rd);
		int length = rdStr.length();
		switch(length)
		{
			case 1:
				rdStr = "0000" + rdStr;
				break;
			case 2:
				rdStr = "000" + rdStr;
				break;
			case 3:
				rdStr = "00" + rdStr;
				break;
			case 4:
				rdStr = "0" + rdStr;
				break;
			default:
				break;
		}
		messageId = messageId + DateUtil.formatDateTime(new Date(),"yyyyMMddHHmmss");
		messageId = messageId + rdStr;
		
		return messageId;
	}
    
    
    /**
     * 生成签名，加入到报文中，再对整个报文DES加密
     * @param requestXml 请求报文
     * @param reqCode  请求的接口代码
     * @return
     */
    public static String singXml(String requestXml,String reqCode){
    	
    	//获取参数签名算法
    	String signatureAlgorithm = XmlUtil.getNodeVal(requestXml, "signatureAlgorithm");
    	//获取需要加密的字符串
    	int begin = requestXml.indexOf("<"+reqCode);
    	int end = requestXml.indexOf("</Message>");
    	String md5Str = requestXml.substring(begin,end);
    	//生成签名
    	String md5ReqData = "";
		try {
			md5ReqData = Md5DegistUtil.getMd5Str(md5Str, signatureAlgorithm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//将签名加入到请求报文中
		requestXml = requestXml.split("</Message>")[0]+"<hmac>"+md5ReqData+"</hmac></Message>"+requestXml.split("</Message>")[1];
		//DES加密报文
		String signXml = DesUtil.getEncryptString(requestXml);
		return signXml;
    }
    
    
    
    
    /**
	 * 组装接收到汇金宝异步回调后，平台返回给汇金宝的报文
	 * @param status 1成功，2失败  为空表示成功
	 * @param msg 错误详情
	 * @param hjbOrderNo 汇金宝订单号
	 * @return
	 */
	public static String buildNotifyResponseParam(HJBNotifyResp hjbNotifyResp){
		
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("HJBEB");
		Element cashierPay = root.addElement("CashierPay");
		cashierPay.addAttribute("id", "CashierPay");
		
		cashierPay.addElement("status").addText(hjbNotifyResp.getStatus());
		cashierPay.addElement("message").addText(hjbNotifyResp.getMessage());
		cashierPay.addElement("orderNo").addText(hjbNotifyResp.getOrderNo());
		
		StringWriter writer = new StringWriter();
	    XMLWriter output = new XMLWriter(writer);
	    try{
	    	output.write(document);
	    	writer.close();
	    	output.close();
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    
	    String requestXml = writer.toString();
	    HgLogger.getInstance().debug("luoyun", "【汇金宝】异步回调响应汇金宝报文" + requestXml);
	    String desXml = DesUtil.getEncryptString(requestXml);
		return desXml;
		
	}
	
	 /**
     * 生成汇金宝请求HTML字符串
     * @param requestUrl
     * @param requestParams
     * @return
     */
    public static String buildRequest(String requestUrl,String requestParams){
    	StringBuffer submitHtml = new StringBuffer();
		submitHtml.append("<form id=\"hjbsubmit\" name=\"hjbsubmit\" action=\"" + requestUrl
                + "\" _input_charset=\"UTF-8\" method=\"POST\"" +  ">");
		
		submitHtml.append("<input type=\"hidden\" name=\"reqData\" value=\"" + requestParams + "\"/></form>");
		submitHtml.append("<script>document.forms['hjbsubmit'].submit();</script>");
		
		return submitHtml.toString();
    }
    
    
    
}
