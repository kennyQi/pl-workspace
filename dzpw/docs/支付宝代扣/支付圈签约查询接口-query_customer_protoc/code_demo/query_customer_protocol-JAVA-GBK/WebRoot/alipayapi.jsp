<%
/* *
 *���ܣ�֧��ȦǩԼ��ѯ�ӿڽ���ҳ
 *�汾��3.3
 *���ڣ�2012-08-14
 *˵����
 *���´���ֻ��Ϊ�˷����̻����Զ��ṩ���������룬�̻����Ը����Լ���վ����Ҫ�����ռ����ĵ���д,����һ��Ҫʹ�øô��롣
 *�ô������ѧϰ���о�֧�����ӿ�ʹ�ã�ֻ���ṩһ���ο���

 *************************ע��*****************
 *������ڽӿڼ��ɹ������������⣬���԰��������;�������
 *1���̻��������ģ�https://b.alipay.com/support/helperApply.htm?action=consultationApply�����ύ���뼯��Э�������ǻ���רҵ�ļ�������ʦ������ϵ��Э�����
 *2���̻��������ģ�http://help.alipay.com/support/232511-16307/0-16307.htm?sh=Y&info_type=9��
 *3��֧������̳��http://club.alipay.com/read-htm-tid-8681712.html��
 *�������ʹ����չ���������չ���ܲ�������ֵ��
 **********************************************
 */
%>
<%@ page language="java" contentType="text/html; charset=gbk" pageEncoding="gbk"%>
<%@ page import="com.alipay.config.*"%>
<%@ page import="com.alipay.util.*"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title>֧����֧��ȦǩԼ��ѯ�ӿ�</title>
	</head>
	<%
		////////////////////////////////////�������//////////////////////////////////////

		//ҵ������
		String biz_type = new String(request.getParameter("WIDbiz_type").getBytes("ISO-8859-1"),"GBK");
		//����
		//֧������¼�˺�
		String user_email = new String(request.getParameter("WIDuser_email").getBytes("ISO-8859-1"),"GBK");
		//��account_no����ͬʱΪ��
		//�û��˺�
		String acount_no = new String(request.getParameter("WIDacount_no").getBytes("ISO-8859-1"),"GBK");
		//֧�����˺Ŷ�Ӧ��֧����Ψһ�û��ţ���2088��ͷ��16λ��������ɣ���email����ͬʱΪ��
		
		
		//////////////////////////////////////////////////////////////////////////////////
		
		//������������������
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "query_customer_protocol");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("biz_type", biz_type);
		sParaTemp.put("user_email", user_email);
		sParaTemp.put("acount_no", acount_no);
		
		//��������
		String sHtmlText = AlipaySubmit.buildRequest("", "", sParaTemp);
		out.println(sHtmlText);
	%>
	<body>
	</body>
</html>
