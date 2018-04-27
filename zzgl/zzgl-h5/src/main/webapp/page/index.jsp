<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>中智票量</title>
<!-- meta信息，可维护 -->
<meta charset="UTF-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta content="telephone=no" name="format-detection" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!-- 公共样式 -->
<link rel="stylesheet" href="${ctx}/css/base.2.0.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/ticket.css">
<script type="text/javascript" src="${ctx}/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="${ctx}/js/ticket.js"></script>
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
				<a href="${ctx}/jp/init?openid=${openid}">
					<div class="plane">
						<i class="i_01"></i><span>国内机票</span>
					</div>
				</a> 
				
				<a  href="${ctx}/line/list">
					<div class="plane">
						<i class="i_04"></i><span>线路</span>
					</div>
				</a>
				
				<a  href="${ctx}/lineSalesPlan/buyActivitylist">
					<div class="plane">
						<i class="i_05"></i><span>抢购活动</span>
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
		<!--start 线路列表-->
		<div class="hot_ticket uktg">
			<div class="hot_t_hd">
				<i class="hot_online"></i>
				<div class="hot_info">
					<i></i>热门推荐 >>
				</div>
			</div>
			<div class="hot_ticket_list pb60">
				<ul id="addlist">
				<c:forEach items="${lineList}" var="line">
					<li>
						<a href="${ctx}/hslH5/line/lineDetail?id=${line.id}"> 
						<c:choose>
							<c:when test="${!empty line.lineImageList}">
							
								<c:forEach items="${line.lineImageList}" var="lineImage" varStatus="status">
									
									<c:choose>
									 	<c:when test="${status.index == 0 && !empty lineImage.urlMaps}">
									 		<img src="${image_host}${imageBaseUrl}${lineImage.urlMaps['H5']}" title=1>
									 	</c:when>
									 </c:choose>
								</c:forEach>
							</c:when>
							<c:when test="${ empty lineImage.urlMaps}">
						 		<img src="${ctx}/images/noPicture_default.jpg">
						 	</c:when>
						</c:choose>
						<p>
							<span>${line.baseInfo.name}</span>
						</p>
						</a>
					</li>
				</c:forEach>
				</ul>
			</div>
		</div>
		<!--end 线路列表-->
	</section>

	<footer class="index_f">
		<div class="f_nav">
			 <c:choose>
			 	<c:when test="${user==null&&openid==null}">
			 		<a href="${ctx}/user/login">登录</a>
			 	</c:when>
			 	<c:otherwise>
			 		<a href='${ctx}/user/nologin?openid=${openid}'>个人中心</a>
			 	</c:otherwise>
			 </c:choose>
			 <a href="${ctx}/user/reg">注册</a> 
			 <a id="J_phone">客服电话</a>
		</div>
		<p>©2015 中智票量</p>
	</footer>
	<section id="f_tel" style="display:none">
		<div class="f_tel_title">
			<div class="f_tel_hd">
				<a href="tel:400—660—6660">400—660—6660</a> <a id="J_hide">取消</a>
			</div>
		</div>
	</section>
	
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
