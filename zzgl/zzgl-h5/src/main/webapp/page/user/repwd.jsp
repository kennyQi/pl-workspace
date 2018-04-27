<%@ include file="/page/common/common.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<!-- meta信息，可维护 -->
<meta charset="UTF-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta content="telephone=no" name="format-detection" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!-- 公共样式 -->
<link rel="stylesheet" href="${ctx }/css/base.2.0.css" />
<link rel="stylesheet" href="${ctx }/css/hgsl_base.css">
<script type="text/javascript" src="${ctx }/js/jquery.js"></script>
 <script type="text/javascript" src="${ctx}/js/jquery.js"></script> 	 <script type="text/javascript" src="${ctx}/js/jquery-1.10.2.js"></script> 	 <script type="text/javascript" src="${ctx}/js/jquery.form.js"></script> 	 <script type="text/javascript" src="${ctx}/js/json2.js"></script> 	 <script type="text/javascript" src="${ctx}/js/jsbox.js"></script> 	 <script type="text/javascript" src="${ctx}/js/pop.ups/style.js"></script><script type="text/javascript" src="${ctx}/js/pop.ups/pop.ups.js"></script>	 <script type="text/javascript" src="${ctx}/js/hsl.js"></script>
</head>
<body>
	<!-- 公共页头  -->
	<header class="header">
	  <h1>修改密码</h1>	  
	  <div class="left-head"> 
	    <a id="goBack" href="javascript:history.go(-1);" class="tc_back head-btn">
	        <span class="inset_shadow">
	            <span class="head-return"></span>
	        </span>
	    </a> 
	  </div>
	 </header> 

<div page="login" class="login">
    <form action="${ctx}/user/updatePwd" class="listForm" method="post" id="listForm">
      <input name="" type="hidden" value="">         
      <input name="" type="hidden" value="">
        <article class="circle_b bottom_c radius passinfo" id="payInfo">
            <section class="secure selectBank" style="position: relative;">
                <span class="passname">旧密码</span>
                <span class="fRight">                    
                    <input name="oldPwd" id="oldPwd" placeholder="请输入旧密码" type="password" maxlength="20" value="">
                </span>
            </section>
            <section class="secure selectBank" style="position: relative;">
                <span class="passname">新密码</span>
                <span class="fRight">
                    <input name="newPwd" id="newPwd" placeholder="请输入新密码" maxlength="20" type="password" value="" >
                </span>
            </section>
            <section class="secure " style="position: relative;">
                <span class="passname">确认新密码</span>
                <span class="fRight">
                    <input name="verify" id="verify" placeholder="确认新密码" maxlength="20" type="password" value="" >
                </span>
            </section>           
        </article>       
        <div class="col_div">
            <a id="btn_click" class="btn btn-green radius" title="">确认</a>
        </div>
    </form>
  </div>
<script type="text/javascript" src="${ctx }/js/showtip.js"></script>
<script type="text/javascript">
    $("#btn_click").click(function(){
        var oldPwd = $("#oldPwd").val();        
        var newPwd = $("#newPwd").val();        
        var verify = $("#verify").val();
        var passreg = /^[0-9a-zA-Z]\d{5,19}$/;
        var regSpecial =new RegExp(/[(\~)(\!)(\！)(\@)(\#)(\￥)(\%)(\……)(\&)(\*)(\（)(\）)(\—)(\+)(\=)(\-)(\、)(\|)(\\)(\/)(\？)(\》)(\《)(\.)(\,)(\。)(\，)(\>)(\<)(\：)(\;)(\")(\‘)(\’)(\;)(\:)(\")(\')]+/);
        if($("#oldPwd").val()==''){
             showTip('请输入你的旧密码','好的');
             $("#oldPwd").focus();
             return false;
        }else if(regSpecial.test(oldPwd)){
    		showTip("密码不能包含特殊字符","好的");
    		$("#oldPwd").focus();
    		return false;
    	}else if(oldPwd.length <6 || oldPwd.length >20){
            showTip('请输入6-20位字符密码','好的');
            $("#oldPwd").focus();
            return false;
        }

        if($("#newPwd").val()==''){
             showTip('请输入你的新密码','好的');
             $("#newPwd").focus();
             return false;
        }else if(regSpecial.test(newPwd)){
    		showTip("密码不能包含特殊字符","好的");
    		$("#newPwd").focus();
    		return false;
    	}else if(newPwd.length <6 || newPwd.length >20){
            showTip('请输入6-20位字符密码','好的');
            $("#newPwd").focus();
            return false;
        }
        if(newPwd == verify){
			
   			$.pop.lock(true);
   			$.pop.load(true, '正在重置中...');
   			$("#listForm").ajaxSubmit({
   				timeout:60000,
   				dataType:'json',
   				error:function() {
   				    $.pop.load(false, function() {
   		    			$.pop.lock(false);
   				    	setTimeout(function() {
   				    		showTip('系统繁忙,请稍候','好的');
   				    	}, 666);
   				    });
   		    	},
   				success:function(data) {
   	           		$.pop.load(false, function() {
   	           			$.pop.lock(false);
   	           			setTimeout(function() {
   	           				if (data.result == "1") {	//密码重置成功
   	           					$.pop.tips('重置成功，请使用新密码登录！', 3, function() {
   	           						location.href = "${ctx}/user/login";
   	           						/* if ('${isWCBrowser}' == 'true') {
   	           							var url = Js.decodeURL($("#redirectUrl").val());
   						            	if (url == null || url == '') {
   						            		url = "${ctx}/user/center?openid=${openid}";
   						            	}
   						            	location.href = url;
   	           						} else {
   	           							var redirectUrl = encodeURIComponent($("#redirectUrl").val());
   		    		            		location.href = "${ctx}/user/login?openid=${openid}&redirectUrl=" + redirectUrl;
   	           						} */
   	           					});
   	    		           	} else {
   	           					showTip(data.message,'好的');
   	    		           	}
   	           			}, 666);
   	           		});
   				}
   			});
        	
        }
        else{
             showTip('两者密码不一致','好的');
            return false  
        }  
});
</script>
</body>
</html>