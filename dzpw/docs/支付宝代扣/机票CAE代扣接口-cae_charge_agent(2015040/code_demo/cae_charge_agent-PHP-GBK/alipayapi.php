<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<title>֧������ƱCAE���۽ӿڽӿ�</title>
</head>
<?php
/* *
 * ���ܣ���ƱCAE���۽ӿڽ���ҳ
 * �汾��3.3
 * �޸����ڣ�2012-07-23
 * ˵����
 * ���´���ֻ��Ϊ�˷����̻����Զ��ṩ���������룬�̻����Ը����Լ���վ����Ҫ�����ռ����ĵ���д,����һ��Ҫʹ�øô��롣
 * �ô������ѧϰ���о�֧�����ӿ�ʹ�ã�ֻ���ṩһ���ο���

 *************************ע��*************************
 * ������ڽӿڼ��ɹ������������⣬���԰��������;�������
 * 1���̻��������ģ�https://b.alipay.com/support/helperApply.htm?action=consultationApply�����ύ���뼯��Э�������ǻ���רҵ�ļ�������ʦ������ϵ��Э�����
 * 2���̻��������ģ�http://help.alipay.com/support/232511-16307/0-16307.htm?sh=Y&info_type=9��
 * 3��֧������̳��http://club.alipay.com/read-htm-tid-8681712.html��
 * �������ʹ����չ���������չ���ܲ�������ֵ��
 */

require_once("alipay.config.php");
require_once("lib/alipay_submit.class.php");

/**************************�������**************************/

        //�������첽֪ͨҳ��·��
        $notify_url = "http://�̻����ص�ַ/cae_charge_agent-PHP-GBK/notify_url.php";
        //��http://��ʽ������·�����������?id=123�����Զ������
        //�̻�������
        $out_order_no = $_POST['WIDout_order_no'];
        //�̻���վΨһ������(�̻��Զ���)
        //���
        $amount = $_POST['WIDamount'];
        //���۶������
        //֧��������
        $subject = $_POST['WIDsubject'];
        //��������ժҪ
        //ת��֧�����˺�
        $trans_account_out = $_POST['WIDtrans_account_out'];
        //ת����֧����������ʽ��˺ţ�user_id+0156�������ֶλ��ɴ���֧������¼�˻���������ֻ��ţ���
        //ת��֧�����˺�
        $trans_account_in = $_POST['WIDtrans_account_in'];
        //ת���֧����������ʽ��˺ţ�user_id+0156�������ֶλ��ɴ���֧������¼�˻���������ֻ��ţ���
        //����ģʽ
        $charge_type = $_POST['WIDcharge_type'];
        //��Ʊ����ʱ�ߵ��ǽ���ģʽ���̶�ֵΪ��trade��
        //����ҵ����
        $type_code = $_POST['WIDtype_code'];
        //Ψһ��ʶ�̻���֧����ǩ���ĳ��ҵ����(��ƱCAE���۵�type_codeΪpid+1000310004)


/************************************************************/

//����Ҫ����Ĳ������飬����Ķ�
$parameter = array(
		"service" => "cae_charge_agent",
		"partner" => trim($alipay_config['partner']),
		"notify_url"	=> $notify_url,
		"out_order_no"	=> $out_order_no,
		"amount"	=> $amount,
		"subject"	=> $subject,
		"trans_account_out"	=> $trans_account_out,
		"trans_account_in"	=> $trans_account_in,
		"charge_type"	=> $charge_type,
		"type_code"	=> $type_code,
		"_input_charset"	=> trim(strtolower($alipay_config['input_charset']))
);

//��������
$alipaySubmit = new AlipaySubmit($alipay_config);
$html_text = $alipaySubmit->buildRequestHttp($parameter);
//����XML
//ע�⣺�ù���PHP5����������֧�֣��迪ͨcurl��SSL��PHP���û��������鱾�ص���ʱʹ��PHP�������
$doc = new DOMDocument();
$doc->loadXML($html_text);

//������������̻���ҵ���߼��������

//�������������ҵ���߼�����д�������´�������ο�������

//��ȡ֧������֪ͨ���ز������ɲο������ĵ���ҳ����תͬ��֪ͨ�����б�

//����XML
if( ! empty($doc->getElementsByTagName( "alipay" )->item(0)->nodeValue) ) {
	$alipay = $doc->getElementsByTagName( "alipay" )->item(0)->nodeValue;
	echo charsetEncode($alipay,'gbk','utf-8');
}

//�������������ҵ���߼�����д�������ϴ�������ο�������

?>
</body>
</html>