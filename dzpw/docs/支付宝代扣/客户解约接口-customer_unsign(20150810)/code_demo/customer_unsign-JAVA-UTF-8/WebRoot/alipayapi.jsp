<%
/* *
 *功能：客户解约接口接入页
 *版本：3.3
 *日期：2012-08-14
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

 *************************注意*****************
 *如果您在接口集成过程中遇到问题，可以按照下面的途径来解决
 *1、商户服务中心（https://b.alipay.com/support/helperApply.htm?action=consultationApply），提交申请集成协助，我们会有专业的技术工程师主动联系您协助解决
 *2、商户帮助中心（http://help.alipay.com/support/232511-16307/0-16307.htm?sh=Y&info_type=9）
 *3、支付宝论坛（http://club.alipay.com/read-htm-tid-8681712.html）
 *如果不想使用扩展功能请把扩展功能参数赋空值。
 **********************************************
 */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.alipay.config.*"%>
<%@ page import="com.alipay.util.*"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>支付宝客户解约接口</title>
	</head>
	<%
		////////////////////////////////////请求参数//////////////////////////////////////

		//商户编号
		String customer_code = new String(request.getParameter("WIDcustomer_code").getBytes("ISO-8859-1"),"UTF-8");
		//type_code协议对应的唯一的编号。
		//代理业务编号
		String type_code = new String(request.getParameter("WIDtype_code").getBytes("ISO-8859-1"),"UTF-8");
		//标识商户和支付宝签署的业务码。
		//客户账号
		String trans_account_out = new String(request.getParameter("WIDtrans_account_out").getBytes("ISO-8859-1"),"UTF-8");
		//签约的支付宝人民币资金账号。
		//代理业务类型
		String biz_type = new String(request.getParameter("WIDbiz_type").getBytes("ISO-8859-1"),"UTF-8");
		//取值参见接口技术文档
		//客户支付宝账号
		String user_email = new String(request.getParameter("WIDuser_email").getBytes("ISO-8859-1"),"UTF-8");
		//由于解约方式多样化因此以上所有参数的设置请参见接口技术文档
		
		
		//////////////////////////////////////////////////////////////////////////////////
		
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "customer_unsign");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("customer_code", customer_code);
		sParaTemp.put("type_code", type_code);
		sParaTemp.put("trans_account_out", trans_account_out);
		sParaTemp.put("biz_type", biz_type);
		sParaTemp.put("user_email", user_email);
		
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest("", "", sParaTemp);
		out.println(sHtmlText);
	%>
	<body>
	</body>
</html>
