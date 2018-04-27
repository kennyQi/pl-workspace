<%
' 功能：即时到账批量退款无密接口接入页
' 版本：3.3
' 日期：2012-07-17
' 说明：
' 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
' 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
' /////////////////注意/////////////////
' 如果您在接口集成过程中遇到问题，可以按照下面的途径来解决
' 1、商户服务中心（https://b.alipay.com/support/helperApply.htm?action=consultationApply），提交申请集成协助，我们会有专业的技术工程师主动联系您协助解决
' 2、商户帮助中心（http://help.alipay.com/support/232511-16307/0-16307.htm?sh=Y&info_type=9）
' 3、支付宝论坛（http://club.alipay.com/read-htm-tid-8681712.html）
' /////////////////////////////////////

%>
<html>
<head>
	<META http-equiv=Content-Type content="text/html; charset=gb2312">
<title>支付宝即时到账批量退款无密接口</title>
</head>
<body>

<!--#include file="class/alipay_submit.asp"-->

<%
'/////////////////////请求参数/////////////////////

        '服务器异步通知页面路径
        notify_url = "http://www.xxx.com/refund_fastpay_by_platform_nopwd-ASP-GBK/notify_url.asp"
        '需http://格式的完整路径，不允许加?id=123这类自定义参数
        '退款批次号
        batch_no = Request.Form("WIDbatch_no")
        '必填，每进行一次即时到账批量退款，都需要提供一个批次号，必须保证唯一性
        '退款请求时间
        refund_date = Request.Form("WIDrefund_date")
        '必填，格式为：yyyy-MM-dd hh:mm:ss
        '退款总笔数
        batch_num = Request.Form("WIDbatch_num")
        '必填，即参数detail_data的值中，“#”字符出现的数量加1，最大支持1000笔（即“#”字符出现的最大数量999个）
        '单笔数据集
        detail_data = Request.Form("WIDdetail_data")
        '必填，格式详见“4.3 单笔数据集参数说明”

'/////////////////////请求参数/////////////////////

'构造请求参数数组
sParaTemp = Array("service=refund_fastpay_by_platform_nopwd","partner="&partner,"_input_charset="&input_charset  ,"notify_url="&notify_url   ,"batch_no="&batch_no   ,"refund_date="&refund_date   ,"batch_num="&batch_num   ,"detail_data="&detail_data  )

'建立请求
Set objSubmit = New AlipaySubmit
'请在这里加上商户的业务逻辑程序代码

'——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

'此处请设置您想要获取的节点，请把含有路径的节点名放置到数组中。
sParaNode = Array("is_success")
'例如：sParaNode = Array("response/tradeBase/trade_no","is_success")

'获得指定节点的值
sParaXml = objSubmit.BuildRequestHttpXml(sParaTemp, sParaNode)

response.Write sParaXml(0)

'——请根据您的业务逻辑来编写程序（以上代码仅作参考）——


%>
</body>
</html>
