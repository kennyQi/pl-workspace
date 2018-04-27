<%
/* *
 *功能：机票CAE代扣接口接入页
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
		<title>支付宝机票CAE代扣接口</title>
	</head>
	<%
		////////////////////////////////////请求参数//////////////////////////////////////

		//服务器异步通知页面路径
		String notify_url = "http://商户网关地址/cae_charge_agent-JAVA-UTF-8/notify_url.jsp";
		//需http://格式的完整路径，不允许加?id=123这类自定义参数
		//商户订单号
		String out_order_no = new String(request.getParameter("WIDout_order_no").getBytes("ISO-8859-1"),"UTF-8");
		//商户网站唯一订单号(商户自定义)
		//金额
		String amount = new String(request.getParameter("WIDamount").getBytes("ISO-8859-1"),"UTF-8");
		//代扣订单金额
		//支付宝标题
		String subject = new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"),"UTF-8");
		//订单名称摘要
		//转出支付宝账号
		String trans_account_out = new String(request.getParameter("WIDtrans_account_out").getBytes("ISO-8859-1"),"UTF-8");
		//转出的支付宝人民币资金账号（user_id+0156）【该字段还可传递支付宝登录账户（邮箱或手机号）】
		//转入支付宝账号
		String trans_account_in = new String(request.getParameter("WIDtrans_account_in").getBytes("ISO-8859-1"),"UTF-8");
		//转入的支付宝人民币资金账号（user_id+0156）【该字段还可传递支付宝登录账户（邮箱或手机号）】
		//代扣模式
		String charge_type = new String(request.getParameter("WIDcharge_type").getBytes("ISO-8859-1"),"UTF-8");
		//机票代扣时走的是交易模式（固定值为：trade）
		//代理业务编号
		String type_code = new String(request.getParameter("WIDtype_code").getBytes("ISO-8859-1"),"UTF-8");
		//唯一标识商户和支付宝签署的某项业务码(机票CAE代扣的type_code为pid+1000310004)
		
		
		//////////////////////////////////////////////////////////////////////////////////
		
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "cae_charge_agent");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("out_order_no", out_order_no);
		sParaTemp.put("amount", amount);
		sParaTemp.put("subject", subject);
		sParaTemp.put("trans_account_out", trans_account_out);
		sParaTemp.put("trans_account_in", trans_account_in);
		sParaTemp.put("charge_type", charge_type);
		sParaTemp.put("type_code", type_code);
		
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest("", "", sParaTemp);
		out.println(sHtmlText);
	%>
	<body>
	</body>
</html>
