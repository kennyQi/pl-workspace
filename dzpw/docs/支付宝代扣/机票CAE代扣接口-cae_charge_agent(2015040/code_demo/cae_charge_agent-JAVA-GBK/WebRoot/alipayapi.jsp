<%
/* *
 *���ܣ���ƱCAE���۽ӿڽ���ҳ
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
		<title>֧������ƱCAE���۽ӿ�</title>
	</head>
	<%
		////////////////////////////////////�������//////////////////////////////////////

		//�������첽֪ͨҳ��·��
		String notify_url = "http://�̻����ص�ַ/cae_charge_agent-JAVA-GBK/notify_url.jsp";
		//��http://��ʽ������·�����������?id=123�����Զ������
		//�̻�������
		String out_order_no = new String(request.getParameter("WIDout_order_no").getBytes("ISO-8859-1"),"GBK");
		//�̻���վΨһ������(�̻��Զ���)
		//���
		String amount = new String(request.getParameter("WIDamount").getBytes("ISO-8859-1"),"GBK");
		//���۶������
		//֧��������
		String subject = new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"),"GBK");
		//��������ժҪ
		//ת��֧�����˺�
		String trans_account_out = new String(request.getParameter("WIDtrans_account_out").getBytes("ISO-8859-1"),"GBK");
		//ת����֧����������ʽ��˺ţ�user_id+0156�������ֶλ��ɴ���֧������¼�˻���������ֻ��ţ���
		//ת��֧�����˺�
		String trans_account_in = new String(request.getParameter("WIDtrans_account_in").getBytes("ISO-8859-1"),"GBK");
		//ת���֧����������ʽ��˺ţ�user_id+0156�������ֶλ��ɴ���֧������¼�˻���������ֻ��ţ���
		//����ģʽ
		String charge_type = new String(request.getParameter("WIDcharge_type").getBytes("ISO-8859-1"),"GBK");
		//��Ʊ����ʱ�ߵ��ǽ���ģʽ���̶�ֵΪ��trade��
		//����ҵ����
		String type_code = new String(request.getParameter("WIDtype_code").getBytes("ISO-8859-1"),"GBK");
		//Ψһ��ʶ�̻���֧����ǩ���ĳ��ҵ����(��ƱCAE���۵�type_codeΪpid+1000310004)
		
		
		//////////////////////////////////////////////////////////////////////////////////
		
		//������������������
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
		
		//��������
		String sHtmlText = AlipaySubmit.buildRequest("", "", sParaTemp);
		out.println(sHtmlText);
	%>
	<body>
	</body>
</html>
