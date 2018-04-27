﻿<%@ Page Language="C#" AutoEventWireup="true" CodeFile="default.aspx.cs" Inherits="_Default" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>支付宝机票CAE代扣接口</title>
    <style>
*{
	margin:0;
	padding:0;
}
ul,ol{
	list-style:none;
}
.title{
    color: #ADADAD;
    font-size: 14px;
    font-weight: bold;
    padding: 8px 16px 5px 10px;
}
.hidden{
	display:none;
}

.new-btn-login-sp{
	border:1px solid #D74C00;
	padding:1px;
	display:inline-block;
}

.new-btn-login{
    background-color: #ff8c00;
	color: #FFFFFF;
    font-weight: bold;
	border: medium none;
	width:82px;
	height:28px;
}
.new-btn-login:hover{
    background-color: #ffa300;
	width: 82px;
	color: #FFFFFF;
    font-weight: bold;
    height: 28px;
}
.bank-list{
	overflow:hidden;
	margin-top:5px;
}
.bank-list li{
	float:left;
	width:153px;
	margin-bottom:5px;
}

#main{
	width:750px;
	margin:0 auto;
	font-size:14px;
	font-family:'宋体';
}
#logo{
	background-color: transparent;
    background-image: url("images/new-btn-fixed.png");
    border: medium none;
	background-position:0 0;
	width:166px;
	height:35px;
    float:left;
}
.red-star{
	color:#f00;
	width:10px;
	display:inline-block;
}
.null-star{
	color:#fff;
}
.content{
	margin-top:5px;
}

.content dt{
	width:160px;
	display:inline-block;
	text-align:right;
	float:left;
	
}
.content dd{
	margin-left:100px;
	margin-bottom:5px;
}
#foot{
	margin-top:10px;
}
.foot-ul li {
	text-align:center;
}
.note-help {
    color: #999999;
    font-size: 12px;
    line-height: 130%;
    padding-left: 3px;
}

.cashier-nav {
    font-size: 14px;
    margin: 15px 0 10px;
    text-align: left;
    height:30px;
    border-bottom:solid 2px #CFD2D7;
}
.cashier-nav ol li {
    float: left;
}
.cashier-nav li.current {
    color: #AB4400;
    font-weight: bold;
}
.cashier-nav li.last {
    clear:right;
}
.alipay_link {
    text-align:right;
}
.alipay_link a:link{
    text-decoration:none;
    color:#8D8D8D;
}
.alipay_link a:visited{
    text-decoration:none;
    color:#8D8D8D;
}
</style>
</head>
<body>
    <form id="Form1" runat="server">
        <div id="main">
            <div id="head">
                <dl class="alipay_link">
                    <a target="_blank" href="http://www.alipay.com/"><span>支付宝首页</span></a>| <a target="_blank"
                        href="https://b.alipay.com/home.htm"><span>商家服务</span></a>| <a target="_blank" href="http://help.alipay.com/support/index_sh.htm">
                            <span>帮助中心</span></a>
                </dl>
                <span class="title">支付宝机票CAE代扣接口快速通道</span>
            </div>
            <div class="cashier-nav">
                <ol>
                    <li class="current">1、确认信息 →</li>
                    <li>2、点击确认 →</li>
                    <li class="last">3、确认完成</li>
                </ol>
            </div>
            <div id="body" style="clear: left">
                <dl class="content">
                    <dt>商户订单号：</dt>
                    <dd>
                        <span class="null-star">*</span>
                        <asp:TextBox ID="WIDout_order_no" name="WIDout_order_no" runat="server"></asp:TextBox>
                        <span>商户网站唯一订单号(商户自定义)</span>
                    </dd>
                    <dt>金额：</dt>
                    <dd>
                        <span class="null-star">*</span>
                        <asp:TextBox ID="WIDamount" name="WIDamount" runat="server"></asp:TextBox>
                        <span>代扣订单金额</span>
                    </dd>
                    <dt>支付宝标题：</dt>
                    <dd>
                        <span class="null-star">*</span>
                        <asp:TextBox ID="WIDsubject" name="WIDsubject" runat="server"></asp:TextBox>
                        <span>订单名称摘要</span>
                    </dd>
                    <dt>转出支付宝账号：</dt>
                    <dd>
                        <span class="null-star">*</span>
                        <asp:TextBox ID="WIDtrans_account_out" name="WIDtrans_account_out" runat="server"></asp:TextBox>
                        <span>转出的支付宝人民币资金账号（user_id+0156）【该字段还可传递支付宝登录账户（邮箱或手机号）】</span>
                    </dd>
                    <dt>转入支付宝账号：</dt>
                    <dd>
                        <span class="null-star">*</span>
                        <asp:TextBox ID="WIDtrans_account_in" name="WIDtrans_account_in" runat="server"></asp:TextBox>
                        <span>转入的支付宝人民币资金账号（user_id+0156）【该字段还可传递支付宝登录账户（邮箱或手机号）】</span>
                    </dd>
                    <dt>代扣模式：</dt>
                    <dd>
                        <span class="null-star">*</span>
                        <asp:TextBox ID="WIDcharge_type" name="WIDcharge_type" runat="server"></asp:TextBox>
                        <span>机票代扣时走的是交易模式（固定值为：trade）</span>
                    </dd>
                    <dt>代理业务编号：</dt>
                    <dd>
                        <span class="null-star">*</span>
                        <asp:TextBox ID="WIDtype_code" name="WIDtype_code" runat="server"></asp:TextBox>
                        <span>唯一标识商户和支付宝签署的某项业务码(机票CAE代扣的type_code为pid+1000310004)</span>
                    </dd>
                    <dt></dt>
                    <dd>
                        <span class="new-btn-login-sp">
                            <asp:Button ID="BtnAlipay" name="BtnAlipay" class="new-btn-login" Text="确 认" Style="text-align: center;"
                                runat="server" OnClick="BtnAlipay_Click"/></span></dd></dl>
            </div>
            <div id="foot">
                <ul class="foot-ul">
                    <li><font class="note-help">如果您点击“确认”按钮，即表示您同意该次的执行操作。 </font></li>
                    <li>支付宝版权所有 2011-2015 ALIPAY.COM </li>
                </ul>
                <ul>
            </div>
        </div>
    </form>
</body>
</html>