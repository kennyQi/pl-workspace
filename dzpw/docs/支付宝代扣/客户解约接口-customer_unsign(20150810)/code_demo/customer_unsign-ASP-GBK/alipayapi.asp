<%
' ���ܣ��ͻ���Լ�ӿڽ���ҳ
' �汾��3.3
' ���ڣ�2012-07-17
' ˵����
' ���´���ֻ��Ϊ�˷����̻����Զ��ṩ���������룬�̻����Ը����Լ���վ����Ҫ�����ռ����ĵ���д,����һ��Ҫʹ�øô��롣
' �ô������ѧϰ���о�֧�����ӿ�ʹ�ã�ֻ���ṩһ���ο���
	
' /////////////////ע��/////////////////
' ������ڽӿڼ��ɹ������������⣬���԰��������;�������
' 1���̻��������ģ�https://b.alipay.com/support/helperApply.htm?action=consultationApply�����ύ���뼯��Э�������ǻ���רҵ�ļ�������ʦ������ϵ��Э�����
' 2���̻��������ģ�http://help.alipay.com/support/232511-16307/0-16307.htm?sh=Y&info_type=9��
' 3��֧������̳��http://club.alipay.com/read-htm-tid-8681712.html��
' /////////////////////////////////////

%>
<html>
<head>
	<META http-equiv=Content-Type content="text/html; charset=gb2312">
<title>֧�����ͻ���Լ�ӿ�</title>
</head>
<body>

<!--#include file="class/alipay_submit.asp"-->

<%
'/////////////////////�������/////////////////////

        '�̻����
        customer_code = Request.Form("WIDcustomer_code")
        'type_codeЭ���Ӧ��Ψһ�ı�š�
        '����ҵ����
        type_code = Request.Form("WIDtype_code")
        '��ʶ�̻���֧����ǩ���ҵ���롣
        '�ͻ��˺�
        trans_account_out = Request.Form("WIDtrans_account_out")
        'ǩԼ��֧����������ʽ��˺š�
        '����ҵ������
        biz_type = Request.Form("WIDbiz_type")
        'ȡֵ�μ��ӿڼ����ĵ�
        '�ͻ�֧�����˺�
        user_email = Request.Form("WIDuser_email")
        '���ڽ�Լ��ʽ����������������в�����������μ��ӿڼ����ĵ�

'/////////////////////�������/////////////////////

'���������������
sParaTemp = Array("service=customer_unsign","partner="&partner,"_input_charset="&input_charset  ,"customer_code="&customer_code   ,"type_code="&type_code   ,"trans_account_out="&trans_account_out   ,"biz_type="&biz_type   ,"user_email="&user_email  )

'��������
Set objSubmit = New AlipaySubmit
'������������̻���ҵ���߼��������

'�������������ҵ���߼�����д�������´�������ο�������

'�˴�����������Ҫ��ȡ�Ľڵ㣬��Ѻ���·���Ľڵ������õ������С�
sParaNode = Array("alipay")
'���磺sParaNode = Array("response/tradeBase/trade_no","is_success")

'���ָ���ڵ��ֵ
sParaXml = objSubmit.BuildRequestHttpXml(sParaTemp, sParaNode)

response.Write sParaXml(0)

'�������������ҵ���߼�����д�������ϴ�������ο�������


%>
</body>
</html>
