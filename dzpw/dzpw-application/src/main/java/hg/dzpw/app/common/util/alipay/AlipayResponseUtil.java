package hg.dzpw.app.common.util.alipay;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import hg.common.util.DateUtil;
import hg.dzpw.pojo.api.alipay.CaeChargeResponse;
import hg.dzpw.pojo.api.alipay.RefundFastResponse;

/**
 * 
 * @类功能说明：支付宝接口返回结果处理工具类
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2016-3-4下午4:58:31
 * @版本：
 */
public class AlipayResponseUtil {

	/**
	 * 
	 * @描述： 将支付宝返回的xml封装成实体
	 * @author: guotx
	 * @version: 2016-3-4 下午4:59:43
	 */
	public static CaeChargeResponse caeChargeStrToBean(String resStr) {
		SAXReader saxReader = new SAXReader();
		StringReader stringReader = new StringReader(resStr);
		InputSource inputSource = new InputSource(stringReader);
		CaeChargeResponse response=new CaeChargeResponse();
		boolean success = false;
		try {
			Document document = saxReader.read(inputSource);
			Element rootElement = document.getRootElement();
			Element isSuccess = rootElement.element("is_success");
			if (isSuccess.getTextTrim().equals("T")) {
				success=true;
			}
			if (success) {
				Element orderElement=rootElement.element("response").element("order");
				response.setOut_order_no(orderElement.element("out_order_no").getTextTrim());
				response.setPay_date(DateUtil.parseDateTime(orderElement.elementTextTrim("pay_date")));
				response.setSign(rootElement.elementTextTrim("sign"));
				response.setSign_type(rootElement.elementTextTrim("sign_type"));
				response.setStatus(orderElement.elementTextTrim("status"));
				response.setTrade_no(orderElement.elementTextTrim("trade_no"));
				response.setSuccess(true);
			}else {
				response.setSuccess(false);
				response.setError(rootElement.element("error").getTextTrim());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
		return response;
	}
	/**
	 * 
	 * @描述： 将支付宝退款接口返回xml解析成实体
	 * @author: guotx 
	 * @version: 2016-3-10 下午3:22:29
	 */
	public static RefundFastResponse refundStrToBean(String resStr){
		SAXReader saxReader = new SAXReader();
		StringReader stringReader = new StringReader(resStr);
		InputSource inputSource = new InputSource(stringReader);
		RefundFastResponse response=new RefundFastResponse();
		boolean success = false;
		try {
			Document document = saxReader.read(inputSource);
			Element rootElement = document.getRootElement();
			String isSuccess = rootElement.elementText("is_success");
			if (isSuccess.equals("P")) {
				response.setSuccess(true);
				response.setMessage("退款处理中");
			}
			if (isSuccess.equals("T")) {
				success=true;
			}
			if (success) {
				response.setSuccess(true);
			}else {
				response.setSuccess(false);
				response.setMessage(rootElement.elementText("error"));
			}
		}catch(DocumentException e){
			
		}
		return response;
	}

	public static void main(String[] args) {
		File file = new File("F:\\alipayResponse.xml");
		StringBuilder stringBuilder = new StringBuilder();
		String temp = null;
		try {
			FileReader reader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(reader);
			while ((temp = bufferedReader.readLine()) != null) {
				stringBuilder.append(temp).append("\n");
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		CaeChargeResponse response=caeChargeStrToBean(stringBuilder.toString());
		System.out.println(stringBuilder);
	}
}
