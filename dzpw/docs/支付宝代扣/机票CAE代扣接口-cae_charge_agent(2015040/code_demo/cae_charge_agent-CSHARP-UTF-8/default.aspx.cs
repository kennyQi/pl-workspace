using System;
using System.Data;
using System.Configuration;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using System.Collections.Generic;
using System.Text;
using System.IO;
using System.Xml;
using Com.Alipay;

/// <summary>
/// 功能：机票CAE代扣接口接入页
/// 版本：3.3
/// 日期：2012-07-05
/// 说明：
/// 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
/// 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
/// 
/// /////////////////注意///////////////////////////////////////////////////////////////
/// 如果您在接口集成过程中遇到问题，可以按照下面的途径来解决
/// 1、商户服务中心（https://b.alipay.com/support/helperApply.htm?action=consultationApply），提交申请集成协助，我们会有专业的技术工程师主动联系您协助解决
/// 2、商户帮助中心（http://help.alipay.com/support/232511-16307/0-16307.htm?sh=Y&info_type=9）
/// 3、支付宝论坛（http://club.alipay.com/read-htm-tid-8681712.html）
/// 
/// 如果不想使用扩展功能请把扩展功能参数赋空值。
/// </summary>
public partial class _Default : System.Web.UI.Page 
{
    protected void Page_Load(object sender, EventArgs e)
    {
    }

    protected void BtnAlipay_Click(object sender, EventArgs e)
    {
        ////////////////////////////////////////////请求参数////////////////////////////////////////////

        //服务器异步通知页面路径
        string notify_url = "http://商户网关地址/cae_charge_agent-CSHARP-UTF-8/notify_url.aspx";
        //需http://格式的完整路径，不允许加?id=123这类自定义参数
        //商户订单号
        string out_order_no = WIDout_order_no.Text.Trim();
        //商户网站唯一订单号(商户自定义)
        //金额
        string amount = WIDamount.Text.Trim();
        //代扣订单金额
        //支付宝标题
        string subject = WIDsubject.Text.Trim();
        //订单名称摘要
        //转出支付宝账号
        string trans_account_out = WIDtrans_account_out.Text.Trim();
        //转出的支付宝人民币资金账号（user_id+0156）【该字段还可传递支付宝登录账户（邮箱或手机号）】
        //转入支付宝账号
        string trans_account_in = WIDtrans_account_in.Text.Trim();
        //转入的支付宝人民币资金账号（user_id+0156）【该字段还可传递支付宝登录账户（邮箱或手机号）】
        //代扣模式
        string charge_type = WIDcharge_type.Text.Trim();
        //机票代扣时走的是交易模式（固定值为：trade）
        //代理业务编号
        string type_code = WIDtype_code.Text.Trim();
        //唯一标识商户和支付宝签署的某项业务码(机票CAE代扣的type_code为pid+1000310004)


        ////////////////////////////////////////////////////////////////////////////////////////////////

        //把请求参数打包成数组
        SortedDictionary<string, string> sParaTemp = new SortedDictionary<string, string>();
        sParaTemp.Add("partner", Config.Partner);
        sParaTemp.Add("_input_charset", Config.Input_charset.ToLower());
        sParaTemp.Add("service", "cae_charge_agent");
        sParaTemp.Add("notify_url", notify_url);
        sParaTemp.Add("out_order_no", out_order_no);
        sParaTemp.Add("amount", amount);
        sParaTemp.Add("subject", subject);
        sParaTemp.Add("trans_account_out", trans_account_out);
        sParaTemp.Add("trans_account_in", trans_account_in);
        sParaTemp.Add("charge_type", charge_type);
        sParaTemp.Add("type_code", type_code);

        //建立请求
        string sHtmlText = Submit.BuildRequest(sParaTemp);

        //请在这里加上商户的业务逻辑程序代码

        //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

        XmlDocument xmlDoc = new XmlDocument();
        try
        {
            xmlDoc.LoadXml(sHtmlText);
            string strXmlResponse = xmlDoc.SelectSingleNode("/alipay").InnerText;
            Response.Write(strXmlResponse);
        }
        catch (Exception exp)
        {
            Response.Write(sHtmlText);
        }

        //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
        
    }
}
