//阿牛 http://www.aniu.com 
//时间:2014-09-24
   var regregdecmal=/^([+-]?)\\d*\\.\\d+$/ //浮点数
   var regregdecmal1=/^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*$/ //正浮点数
   var regdecmal2=/^-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*)$/ //负浮点数
   var regdecmal3=/^-?([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0)$/ //浮点数
   var regdecmal4=/^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0$/ //非负浮点数（正浮点数 + 0）
   var regdecmal5=/^(-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*))|0?.0+|0$/ //非正浮点数（负浮点数 + 0）
   var regintege=/-?[1-9]\\d*$/ //整数
   var regintege1=/^[1-9]\\d*$/ //正整数
   var regintege2=/^-[1-9]\\d*$/ //负整数
   var regnum=/^([+-]?)\\d*\\.?\\d+$"/ //数字
   var regnum1=/^[1-9]\\d*|0$"/ //正数（正整数 + 0）
   var regnum2=/-[1-9]\\d*|0$"/ //负数（负整数 + 0）
   var regascii=/[\\x00-\\xFF]+$"/ //仅ACSII字符
   var regchinese=/^([\u4E00-\u9FA5]+，?)+$/  //仅中文
   var regcolor=/[a-fA-F0-9]{6}$"/ //颜色
   var regemail=/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/ //邮件
   var regidcard=/[1-9]([0-9]{14}|[0-9]{17})$"/ //身份证
   var regip4=/(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$"/ //ip地址
  var regletter=/[A-Za-z]+$/ //字母
  var regletter_l=/[a-z]+$"/ //小写字母
  var regletter_u=/[A-Z]+$"/ //大写字母
  var regmobile=/0?(13|15|18)[0-9]{9}$/ //手机
  var regnotempty=/\\S+$"/ //非空
  var regpassword=/[A-Za-z0-9]{5,20}/ //密码
  var regfullNumber=/[0-9]+$"/ //数字
  var regpicture=/"(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$"/ //图片
  var regqq=/[1-9]*[1-9][0-9]*$"/ //QQ号码
  var regrar=/"(.*)\\.(rar|zip|7zip|tgz)$"/ //压缩文件
  var regtel=/[0-9\-()（）]{7,18}$"/ //电话号码的函数(包括验证国内区号,国际区号,分机号)
  var regusername=/[A-Za-z0-9_\\-\\u4e00-\\u9fa5]+$"/ //用户名
  var regdeptname=/[A-Za-z0-9_()（）\\-\\u4e00-\\u9fa5]+$"/ //单位名
  var regzipcode=/\\d{6}$"/ //邮编
  var regrealname=/[A-Za-z\\u4e00-\\u9fa5]+$"/ // 真实姓名
  var regcompanyname=/[A-Za-z0-9_()（）\\-\\u4e00-\\u9fa5]+$/
  var regcompanyaddr=/[A-Za-z0-9_()（）\\#\\-\\u4e00-\\u9fa5]+$/

 $(".J_username").blur(function(){
  var username = $(this).val();
 	if(regchinese.test(username)){
 $(this).parent().next("span").removeClass("worrg").addClass("ok eorr").html("ok")
 		}
	else{
  $(this).parent().next("span").removeClass("ok").addClass("worrg eorr").html("worrg")
 		}	
	});
 $(".J_tel").blur(function(){
  var tel = $(this).val();
 	if(regmobile.test(tel)){
 $(this).parent().next("span").removeClass("worrg").addClass("ok eorr").html("ok")
 		}
	else{
  $(this).parent().next("span").removeClass("ok").addClass("worrg eorr").html("请输入常用的手机号码")
 		}	
	});	
	
$(".J_em").blur(function(){
  var em = $(this).val();
 	if(regemail.test(em)){
 $(this).parent().next("span").removeClass("worrg").addClass("ok eorr").html("<i></i>ok")
 		}
	else{
  $(this).parent().next("span").removeClass("ok").addClass("worrg eorr").html("亲 填写的邮箱不对 例如：xxxx@xx.com")
 		}	
	});		
	
 $(".J_pwd").blur(function(){
  var pwd = $(this).val();
 	if(regpassword.test(pwd)){
 $(this).parent().next("span").removeClass("worrg").addClass("ok eorr").html("ok")
 		}
	else{
  $(this).parent().next("span").removeClass("ok").addClass("worrg eorr").html("请输入密码5-20字符")
 		}	
	});		
 $(".J_pwd2").blur(function(){
	var pwd = $("#pwd").val();
  var pwd2 = $(this).val();
 	if(pwd==pwd2&&pwd.length>=5&&pwd2.length<=20){
 $(this).parent().next("span").removeClass("worrg").addClass("ok eorr").html("ok")
 		}
	else if(pwd2.length<5){
		$(this).parent().next("span").removeClass("ok").addClass("worrg eorr").html("请输入密码5-20字符")
 		}	
	else{
  $(this).parent().next("span").removeClass("ok").addClass("worrg eorr").html("密码部一致")
 		}	
});			

//点击验证
$("#j_save").click(function(){
$("input",document.forms[0]).each(function(){
var username = $("#username").val();
var tel = $("#tel").val();
var email = $("#email").val();
var pwd = $("#pwd").val();
var pwd2 = $("#pwd2").val();
   
//用户名
	 if('username'==$(this).attr("data-type")){
		 if(username){
	 $(this).parent().next("span").removeClass("worrg").addClass("ok eorr").html("ok")
  	}
else{
	 $(this).parent().next("span").removeClass("ok").addClass("worrg eorr").html("worrg")
		return false
   }}
	 
//手机 
if('tel'==$(this).attr("data-type")){
		 if(tel.length===11){
	 $(this).parent().next("span").removeClass("worrg").addClass("ok eorr").html("ok")
  	}
else{
	 $(this).parent().next("span").removeClass("ok").addClass("worrg eorr").html("worrg")
		return false
   }}	 
	 return true
   $("form").submit();	
 });
 
 });