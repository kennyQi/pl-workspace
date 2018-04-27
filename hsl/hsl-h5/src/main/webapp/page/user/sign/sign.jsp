<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" />
	<title>汇购6周年签到</title>
	<style>
		*{margin:0px;padding:0px;list-style: none;font-size:14px;font-family: "Microsoft YaHei"}
		        .in{border:1px solid #b8b8b8;border-radius:20px;display: block;height:32px;line-height:32px;
		        width:180px;margin:0px auto 0px auto;outline: none;padding:0px 20px;}
		.in::-webkit-input-placeholder{text-align: center;color:#929292;}
		.in::-webkit-inner-spin-button{
		    -webkit-appearance: none !important;
		    margin: 0;
		}
		.btn{border:1px solid #6a79bd;border-radius:20px;display:block;height:32px;line-height:32px;
		    width:222px;margin:20px auto 0px auto;outline: none;text-align: center;;
		    background:#1495f8;color:#fff;}
		    .tips{width:222px;display: block;height: 32px;background: #004c79;margin: auto;color: #fff;text-align:center;
		    line-height:32px;border-radius: 10px;margin-top:20px;
		}
		
		.success h1{font-size:17px;text-align: center;color: #ff3194;margin-top:20px;}
		.success span{display: block;text-align: center;color:#249bf9;font-size: 13px;margin-top: 5px;;}
		.ewm{display: block;margin: auto;margin-top:15px;}
	</style>
	<script TYPE="text/javascript" src="${ctx}/js/jquery.js"></script>
</head>
<body>

	<img src="${ctx}/img/sign/banner.jpg" style="width:100%;" />
	<div class="info" id="info">
		<span class="tips" id="tips" style="opacity: 0">手机号不对呀，再试试！</span>
	    <input type="tel" id="mobile" class="in" placeholder="请输入手机号" />
	    <span id="submit" class="btn" >立即签到</span>
	</div>
	
	<div class="success" id="success" style="display: none;">
	    <h1>签到成功</h1>
	    <span>立马关注公众号，参与互动</span>
	</div>
	
	<div id="ewm" style="display: none;">
		<img src="${ctx}/img/sign/ewm.png" class="ewm" width="135">
		<br>
		<div style="bottom: 0px;position: relative;text-align: center;margin: auto;">
			<a href="${ctx}/activity/signDetail?activityName=<%=URLEncoder.encode(request.getAttribute("activityName").toString(), "UTF-8")%>">查看签到详情</a>
		</div>
	</div>
	
	<input type="hidden" value="${activityName}" id="activityName"/>
	<script>
	    $(function(){
	    	var activityNow = $("#activityName").val();
	    	var activityName = localStorage.getItem('sign-activity');
	    	var mobile = localStorage.getItem('sign-mobile');
	    	if(activityName != null && activityNow == activityName){
	    		if(mobile != null){
	    			info.style.display="none";
		            success.style.display="block";
		            ewm.style.display="block";
	    		}
	    	}
	    })
	    
	    var submit=document.getElementById("submit")
	    ,mobile=document.getElementById("mobile")
	            ,tips=document.getElementById("tips")
	            ,info=document.getElementById("info")
	            ,ewm=document.getElementById("ewm")
	            ,success=document.getElementById("success")
	            ,activityName=$("#activityName").val();
	
	    submit.onclick=function(){
	        var pattern=/1[3|4|5|7|8][0-9]/i;
	        if(pattern.test(mobile.value)&&mobile.value.length==11){
	        	
	        	$.ajax({
	            	type:"POST",
	            	url:"${ctx}/activity/userSign",
	       		    data:{"mobile":mobile.value},
	       		    success:function(data){
	       		    	var dats = eval('('+data+')');
	       		    	if(dats == "签到成功"){
	       		    		info.style.display="none";
	       		            success.style.display="block";
	       		            ewm.style.display="block";
	       		         	localStorage.setItem('sign-activity', activityName);
	       		      		localStorage.setItem('sign-mobile', mobile.value);
	       		    	}else{
	       		    	 tips.style.opacity="1";
	       		    	}
	       		    }
	            });
	        	
	        }else{
	            tips.style.opacity="1";
	        }
	    };
	    mobile.onfocus=function(){
	        tips.style.opacity="0";
	    }
	</script>
</body>
</html>
