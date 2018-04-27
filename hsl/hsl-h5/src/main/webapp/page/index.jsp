<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>票量旅游</title>
<!-- meta信息，可维护 -->
<meta charset="UTF-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta content="telephone=no" name="format-detection" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!-- 公共样式 -->
<link rel="stylesheet" href="${ctx}/css/base.2.0.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/ticket.css">
<%@ include file="/page/common/jscommon.jsp"%>
<script type="text/javascript" src="${ctx}/js/ticket.js"></script>
<%-- <script type="text/javascript" src="${ctx}/js/jquery-1.10.2.js"></script> --%>
<script type="text/javascript" src="${ctx}/js/iscroll.js" ></script>
<script type="text/javascript">

	var myScroll;
	
	function loaded() {
		console.log('now loaded method is invoked');
		setTimeout(function(){
			myScroll = new iScroll('wrapper', {
				  snap: true,
				  momentum: false,
				  hScrollbar: false,
				  onScrollEnd: function () {
					  document.querySelector('#indicator > li.active').className = '';
					  console.log(this.currPageX);
					  document.querySelector('#indicator > li:nth-child(' + (this.currPageX+1) + ')').className = 'active';
						//页码到了最大，重置为负值重新开始
					  if(this.currPageX >= this.pagesX.length-1){
							this.currPageX = -2;
						}
				  }
				   });
		}, 200)
	  
	}
	document.addEventListener('DOMContentLoaded', loaded, false);//触发时机在jQuery()之后
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	WeixinJSBridge.call('hideToolbar');
	
	});
	


	
	$(document).ready(function() {
		$.pop.lock(true);
		$.pop.load(true, '努力加载中...');
		$.ajax({
			url:'${ctx}/mp/ajaxList?openid=${openid}',
			data:{pageNo:1,pageSize:6,hot:true},
			type:'post',
			timeout:60000,
			dataType:'json',
			error:function() {
			    $.pop.load(false, function() {
	    			$.pop.lock(false);
			    	setTimeout(function() {
			    		$.pop.tips('系统繁忙,请稍候');
			    	}, 666);
			    });
	    	},
			success:function(data) {
				$.pop.load(false, function() {
					$.pop.lock(false);
					if (data.result != 1 &&data.result != -1) {
						setTimeout(function() {
				    		$.pop.tips('系统繁忙,请稍候');
				    	}, 666);
					} else {
						var list = data.scenicSpots;
						if (list == null || list.length == 0) {
							$("#noDataTip").show();
						} else {
							var temp = $("#temp").html();
							for (var i = 0; i < list.length; i++) {
								var obj = list[i];
								var children = temp;
								children = children.replace(/#link#/gi, "${ctx}/mp/detail?openid=${openid}&scenicSpotId=" + obj.id)
									.replace(/#image#/gi, obj.scenicSpotBaseInfo.image).replace(/#name#/gi, obj.scenicSpotBaseInfo.name);
								$("#addlist").append($(children));
							}
						}
					}
				});
			}
		});
	});
</script>
</head>
<body id="bf">
	<div class="banner" style="margin:0px; padding:0px;">
		<div style="overflow: hidden;" id="wrapper">
			<div id="scroller">
				<ul id="thelist">
				</ul>
			</div>
		</div>
		<div id="nav">
			<ul id="indicator">
			</ul>
		</div>
	</div>
	<!-- 页面内容 -->
	<section class="content " id="index">
		<!--start 头部-->
		<div class="enter_hd">
			<div class="enter">
				<%--
				<a href="${ctx}/jp/init?openid=${openid}">
					<div class="plane">
						<i class="i_01"></i><span>国内机票</span>
					</div>
				</a>
				--%>
				<a href="${ctx}/mp/list?openid=${openid}">
					<div class="plane">
						<i class="i_02"></i><span>景区门票</span>
					</div>
				</a> 
				<a  href="${ctx}/line/list">
					<div class="plane">
						<i class="i_04"></i><span>线路</span>
					</div>
				</a>
				<a href="${ctx}/user/nologin?openid=${openid}">
					<div class="plane">
						<i class="i_03"></i><span>个人中心</span>
					</div>
				</a>
			</div>
		</div>
		<!--end 头部-->
		<!--start 景点门票列表-->
		<div class="hot_ticket uktg">
			<div class="hot_t_hd">
				<i class="hot_online"></i>
				<div class="hot_info">
					<i></i>热门推荐 >>
				</div>
			</div>
			<div class="hot_ticket_list pb60">
				<ul id="addlist">
				</ul>
			</div>
		</div>
		<!--end 门票列表-->
	</section>

	<footer class="index_f">
		<div class="f_nav">
			<a href="${ctx}/user/login">登录</a> <a href="${ctx}/user/reg">注册</a> <a id="J_phone">客服电话</a>
		</div>
		<p>©2015 票量旅游</p>
	</footer>
	<section id="f_tel" style="display:none">
		<div class="f_tel_title">
			<div class="f_tel_hd">
				<a href="tel:0571-28280813">0571-28280813</a> <a id="J_hide">取消</a>
			</div>
		</div>
	</section>

	<div id="temp" style="display: none;">
		<li><a href="#link#"> <img src="#image#">
				<p>
					<span>#name#</span>
				</p>
		</a></li>
	</div>
	<script type="text/javascript">
		
		 
		 
 		$(window).load(function(){     
	 		$("#J_hide").on("click",function(ev){
			  $("#f_tel").hide();
			  $("body").removeClass("f_tel"); 
				$("#f_tel").removecss();     
	
	 		});

 		$("#J_phone").click(function(ev){
       	ev.preventDefault();
    	var dh = $("#bf").scrollTop();
 		 	 $("#f_tel").show();
		    $("body").addClass("f_tel");		    
			 $("#f_tel").css({"top":dh,"position":"absolute"});     
 		});
 		});
 		
 		
 		/******* 首页广告 ********/
 		$(function(){
 			console.log('now add adlist');
 	 		var nadlist ;
 	 		try{
 	 			nadlist = ${adList};
 	 		}catch(e){
 	 			console.log(e.message);
 	 		}
 			
 	 		var adTemplate = '<li><a href="#href#"><h3>#name#</h3> <img src="#imageURL#"></a></li>';
 	 		/**
 	 		*	添加数据
 	 		*/
 	 		//var nadlist = eval("("+adlist+")");
 	 		for(var x in nadlist){
 	 			var ad = nadlist[x];
 	 			try{
 	 				var adInfo = adTemplate;
 		 			adInfo = adInfo.replace(/#href#/gi, ad.adBaseInfo.url)
 		 							.replace(/#name#/gi, ad.adBaseInfo.title)
 		 							.replace(/#imageURL#/gi, ad.adBaseInfo.imagePath);
 		 			$('#thelist').append(adInfo);
 		 			if(x==0){
	 		 			$('#indicator').append('<li class="active">'+(x+1)+'</li>');
 		 			}else{
 		 				$('#indicator').append('<li class="">'+(x+1)+'</li>');
 		 			}
 	 			}catch(e){
 	 				console.info(e.message);
 	 			}
 	 		}
 	 		
 	 		//loaded();
 	 		
 	 		var count = document.getElementById("thelist").getElementsByTagName("img").length;	
 			var count2 = document.getElementsByClassName("menuimg").length;
 			for(var i=0;i<count;i++){
 	 			document.getElementById("thelist").getElementsByTagName("img").item(i).style.cssText = " width:"+document.body.clientWidth+"px";
 			} 
 			
 			document.getElementById("scroller").style.cssText = " width:"+document.body.clientWidth*count+"px";
			setInterval(function(){
				myScroll.scrollToPage('next', 0,400,count);
			},3500 ); 
			
			window.onresize = function(){ 
			for(var i=0;i<count;i++){
				document.getElementById("thelist").getElementsByTagName("img").item(i).style.cssText = " width:"+document.body.clientWidth+"px";
			}
		    document.getElementById("scroller").style.cssText = " width:"+document.body.clientWidth*count+"px";
		}; 
			
 		});
 		
 		
		
 	</script>
</body>
</html>
