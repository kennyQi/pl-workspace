package hgtech.jf.coupon;

import hg.common.util.UUIDGenerator;
import hgtech.web.util.WebServiceUtil;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
/**
 * 券倾天下优惠券接口
 * @author admin
 *
 */
public class QQTXCoupon implements CouponInterface {

	static String checkCouponPara = "discount_code=%s&request_id=%s&sign=%s";
	static String userCouponPara = "discount_code=%s&order_id=%s&sign=%s";
	//约定的key
	private String key="23j8lnkTNgOjBsN3sGV5wG30Jzq48Tfl";
	private String ip="http://192.168.2.226:8080/";
	private String checkAddr = "e-ticket-merchant/cooperation_merchant/huijif_check_ticket_validity.do";
	private String useAddr = "e-ticket-merchant/cooperation_merchant/huijif_use_ticket.do";
	@Override
	public Map<String, String> checkCoupon(String discountCode) {
		//返回的结果集
		Map<String, String> resultMap = new HashMap<String, String>();
		if(discountCode==null||StringUtils.isBlank(discountCode.trim())){
			resultMap.put("out_resultText", "该券无效");
			resultMap.put("out_resultCode", "N");
			return resultMap;
		}
		//请求号
		String requestId = UUIDGenerator.getUUID();
		//签名
		String sign = MD5.digest(discountCode+requestId+key);
		//拼接url
		try {
			String url =  ip+"/"+checkAddr+"?"+String.format( checkCouponPara ,URLEncoder.encode(discountCode,"utf-8"),requestId,sign);
			String result = WebServiceUtil.callWebService(url, "utf-8");
			resultMap = (Map<String, String>)JSONObject.parse(result);
		} catch (Exception e) {
			resultMap.put("out_resultText", "网络繁忙请重试");
			resultMap.put("out_resultCode", "N");
			e.printStackTrace();
			System.out.println("连接失败");
			return resultMap;
		}
		if(resultMap!=null){
			//验证签名
			String resultSign = MD5.digest(resultMap.get("out_resultText")+resultMap.get("out_resultCode")+requestId+key);
			if(resultSign.equals(resultMap.get("out_sign"))){
				return resultMap;
			}else{
				resultMap.put("out_resultText", "签名错误");
				resultMap.put("out_resultCode", "N");
				return resultMap;
			}
		}else{
			resultMap = new HashMap<String, String>();
			resultMap.put("out_resultText", "请求错误");
			resultMap.put("out_resultCode", "N");
			return resultMap;
		}
	}

	@Override
	public Map<String, String> userCoupon(String discountCode) {
		//返回的结果集
		Map<String, String> resultMap = new HashMap<String, String>();
		if(discountCode==null||StringUtils.isBlank(discountCode.trim())){
			resultMap.put("out_resultText", "该券无效");
			resultMap.put("out_resultCode", "N");
			return resultMap;
		}
		//请求号
		String orderId = UUIDGenerator.getUUID();
		//签名
		String sign = MD5.digest(discountCode+orderId+key);
		//拼接url
		try {
			//拼接url
			String url =  ip+"/"+useAddr+"?"+String.format( userCouponPara ,URLEncoder.encode(discountCode,"utf-8"),orderId,sign);
			String result = WebServiceUtil.callWebService(url, "utf-8");
			resultMap = (Map<String, String>)JSONObject.parse(result);
		} catch (Exception e) {
			resultMap.put("out_resultText", "连接超时当作核销成功");
			resultMap.put("out_resultCode", "Y");
			e.printStackTrace();
			System.out.println("连接失败");
			return resultMap;
		}
		if(resultMap!=null){
			//验证签名
			String resultSign = MD5.digest(resultMap.get("out_resultText")+resultMap.get("out_resultCode")+orderId+key);
			if(resultSign.equals(resultMap.get("out_sign"))){
				return resultMap;
			}else{
				resultMap.put("out_resultText", "签名有问题！");
				resultMap.put("out_resultCode", "N");
				return resultMap;
			}
		}else{
			resultMap = new HashMap<String, String>();
			resultMap.put("out_resultText", "请求错误");
			resultMap.put("out_resultCode", "N");
			return resultMap;
		}
	}
	
	public static void main(String[] args) {
		QQTXCoupon qq = new QQTXCoupon();
		System.out.println(StringUtils.isBlank(null));
		System.out.println(qq.checkCoupon("3554474641"));
		QQTXCoupon qqtxCoupon = new QQTXCoupon();
		System.out.println(qqtxCoupon.checkCoupon("*****"));
		System.out.println("3554474641");
		//System.out.println(qq.userCoupon("5640163212"));
	}

}
