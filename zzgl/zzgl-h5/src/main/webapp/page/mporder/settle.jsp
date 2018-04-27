<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!-- 公共样式 -->
<link rel="stylesheet" href="${ctx}/css/base.2.0.css" />
<link rel="stylesheet" href="${ctx}/css/ticket.css" />
<link rel="stylesheet" href="${ctx}/css/mCal.css" />
 <script type="text/javascript" src="${ctx}/js/jquery.js"></script> 	 <script type="text/javascript" src="${ctx}/js/jquery-1.10.2.js"></script> 	 <script type="text/javascript" src="${ctx}/js/jquery.form.js"></script> 	 <script type="text/javascript" src="${ctx}/js/json2.js"></script> 	 <script type="text/javascript" src="${ctx}/js/jsbox.js"></script> 	 <script type="text/javascript" src="${ctx}/js/pop.ups/style.js"></script><script type="text/javascript" src="${ctx}/js/pop.ups/pop.ups.js"></script>	 <script type="text/javascript" src="${ctx}/js/hsl.js"></script>
<script type="text/javascript" src="${ctx}/js/rili_price.js"></script>
<script type="text/javascript" src="${ctx}/js/ticket.js"></script>
<script>
	$(function() {
		dateHide();
		var datePrices = $.parseJSON('${datePrices}');
		rili(datePrices);//日历
		dateTime();//日历显示隐藏
		dateDay();//选择日期
		$("#delBtn").click(function() {
			$("#takeTicketUserName").val("");
			$("#takeTicketUserMobile").val("");
		});
		$("#takeTicketUserName,#takeTicketUserMobile").keyup(
				function() {
					var name = $("#takeTicketUserName").val();
					var mobile = $("#takeTicketUserMobile").val();
					if ((name == null || name == '')
							&& (mobile == null || mobile == '')) {
						$("#delBtn").hide();
					} else {
						$("#delBtn").show();
					}
				});
	});

	function confirm() {
		var userName = $("#takeTicketUserName").val();
		var userMobile = $("#takeTicketUserMobile").val();
		var travelDate = $("#travelDate").val();
		var memberId = $("input#memberId").val();
		var deptName = $("input#departmentName").val();
		var deptId = $("input#departmentId").val();
		var compName = $("input#companyName").val();
		var compId = $("input#companyId").val();
		if (travelDate == null || travelDate == '') {
			$.pop.tips("请选择游玩日期");
			return;
		}
		var amount = $("#amount").val();
		if (amount != parseInt(amount)) {
			$.pop.tips("请输入正确格式的购票数量");
			$("#amount").val(1);
			return;
		}
		var min = '${policy.minTicket}';
		var max = '${policy.maxTicket}';
		if (parseInt(amount) > parseInt(max)) {
			$.pop.tips("最多可购买" + max + "张");
			return;
		}
		if (parseInt(amount) < parseInt(min)) {
			$.pop.tips("最少须购买" + min + "张");
			return;
		}
		if (userName == null || userName == '') {
			$.pop.tips("请输入所持证件姓名");
			return;
		}
		if (!/^[\u4E00-\u9FA5]{1,10}$/.test(userName)){
			$.pop.tips("请输入正确的取票人姓名");
			return;
		}
		if (userMobile == null || userMobile == '') {
			$.pop.tips("请输入联系方式");
			return;
		}
		if (!userMobile.match(/1[34578][0-9]{9}/)) {
			$.pop.tips("请输入正确格式的联系方式");
			return;
		}
		var command = {
			price : $("#priceVal").html(),
			number : $("#amount").val(),
			policyId : $("#policyId").val(),
			travelDate : $("#travelDate").val(),
			companyId:compId,
			companyName:compName,
			departmentId:deptId,
			departmentName:deptName,
			takeTicketUserInfo : {
				id:memberId,
				baseInfo : {
					name : userName
				},
				contactInfo : {
					mobile : userMobile
				}
			}
		};
		$.pop.lock(true);
		$.pop.load(true, '正在预定中...');
		$.ajax({
					url : '${ctx}/mpo/confirm?openid=${openid}',
					data : {
						'command' : encodeURI(JSON.stringify(command))
					},
					type : 'post',
					timeout : 60000,
					dataType : 'json',
					error : function() {
						$.pop.load(false, function() {
							$.pop.lock(false);
							setTimeout(function() {
								$.pop.tips('系统繁忙,请稍候');
							}, 666);
						});
					},
					success : function(data) {
						$.pop.load(
										false,
										function() {
											$.pop.lock(false);
											setTimeout(
													function() {
														if (data.result == "1") { //预定成功
															$.pop
																	.tips(
																			'预定成功',
																			1,
																			function() {
																				var orderInfo = encodeURI($(
																						"#ticketName")
																						.html()
																						+ "*"
																						+ $(
																								"#amount")
																								.val());
																				orderInfo = orderInfo
																						.replace(
																								/\%/gi,
																								'-');
																				location.href = "${ctx}/mpo/success?openid=${openid}&orderInfo="
																						+ orderInfo
																						+ "&orderId="
																						+ data.orderId;
																			});
														} else {
															$.pop
																	.tips(data.message);
														}
													}, 666);
										});
					}
				});
	}

	function dateHide() {
		$("#gohide").click(function() {
			$("#calContainer").hide();
			$("#contWrap").show();
		});
	}
</script>

</head>
<body class="order">
	<input type="hidden" id="policyId" value="${policy.policyId }" />
	<input type="hidden" id="travelDate" />
	<input type="hidden" id="companyId" name="companyId"/>
	<input type="hidden" id="companyName" name="companyName"/>
	<input type="hidden" id="departmentId" name="departmentId"/>
	<input type="hidden" id="departmentName" name="departmentName"/>
	<input type="hidden" id="memberId" name="takeTicketUserInfo.id"/>
	<!--start 公共页头  -->
	<div id="contWrap">
		<header class="header">
				<div class="header1">	
				<h1>填写订单</h1>
			<div class="left-head">
				<a id="goBack" href="javascript:history.go(-1);"
					class="tc_back head-btn"> <span class="inset_shadow"> <span
						class="head-return"></span>
				</span>
				</a>
			</div></div>
			
  <div class="header2" style="display:none">
    <h1>组织部门</h1>	  
	  <div class="left-head"> 
	    <a id="zzBack" class="tc_back head-btn">
	        <span class="inset_shadow">
	            <span class="head-return"></span>
	        </span>
	    </a> 
	  </div>
    </div>
     <div class="header3" style="display:none">
    <h1>组织成员</h1>	  
	  <div class="left-head"> 
	    <a id="zzBack01" class="tc_back head-btn">
	        <span class="inset_shadow">
	            <span class="head-return"></span>
	        </span>
	    </a> 
	  </div>
    </div>

			<!-- <div class="right-head">
	    <a href="/home/index.html" class="head-btn fn-hide">
	        <span class="inset_shadow">
	            <span class="head-home"></span>
	        </span>
	    </a> 
	  </div> -->
		</header>
		<!--end 公共页头  -->

		<!--start 页面内容 -->
		<div id="tickerContent" class="content">
			<div class="msg">
				<p>
					门票：<span id="ticketName">${policy.name }</span>
				</p>
				<p>
					优惠价格：&yen;<span id="priceVal"><fmt:formatNumber
							value="${policy.tcPrice }" pattern="0.00" /></span>
				</p>
				<p>
					支付方式：<span>景区现付<!-- ${policy.pMode == 0 ? '景区现付' : '其他' } --></span>
				</p>
				<p>
					取票地址：<span>${policy.ticketDelivery }</span>
				</p>
			</div>
			<div class="time">
				<ul>
					<li class="select" style="padding-bottom: 5px;"><span>选择游玩日期</span><em
						style="margin-top: 5px;"></em></li>
					<li>选择购买张数 <span id="minus" style="color:#d7d7d7;">.</span> <input
						id="amount" type="text" value="1"> <span id="plus"
						style="color:#d7d7d7;">.</span>
					</li>
				</ul>
			</div>
			<form class="people">
				<h3>
					取票人信息<c:if test="${userType==2}"><span>选择取票人</span></c:if>
				</h3>
				<div class="name">
					<ul>
						<li><label for="">姓名：</label> <input type="text"
							id="takeTicketUserName" maxlength="10" placeholder="所持证件姓名"
							style="line-height:normal;"></li>
						<li><label for="">手机号码：</label> <input type="text"
							id="takeTicketUserMobile" placeholder="联系方式"
							style="line-height:normal;"></li>
					</ul>
				</div>
				<p id="delBtn" class="del" style="display: none;"></p>
			</form>
			<a href="javascript:confirm();">立即预定</a>
		</div>

	</div>

	<!-- 日历 -->
	<div id="calContainer" class="slider" style="display:none;">
		<header class="header">
			<h1>选择游玩时间</h1>
			<div class="left-head">
				<a id="gohide" href="javascript:;" class="tc_back head-btn"> <span
					class="inset_shadow"> <span class="head-return"></span>
				</span>
				</a>
			</div>

			<!-- <div class="right-head">
	    <a href="/home/index.html" class="head-btn fn-hide">
	        <span class="inset_shadow">
	            <span class="head-home"></span>
	        </span>
	    </a> 
	  </div> -->
		</header>
		<!--end 公共页头  -->

	</div>
	<!--end 页面内容 -->

	<!-- 页头 -->
	<section id="zhizu" class="zuzhi_cont" style="display:none">
		<section class="zuzhi_search">
				<div class="user_name">
					<input type="text" value="" placeholder="搜索" class="txt_search" id="J_txt_s"
						autocomplete="off"> <em id="z_sea_icon" ></em>
				</div>
		</section>
		<c:if test="${not empty companyList}">
			<c:forEach items="${companyList}" varStatus="status" var="company">
				<section class="zuzhi_style comp">
					<h2 id="${company.id}">${company.companyName}</h2>
				</section>
				<section class="plist pzuzhi dept zuzhi_list" company="${company.id}" >
				</section>
			</c:forEach>
		</c:if>
	</section>


	<section id="chengyuan" style="display:none">
		<section class="zuzhi_style">
			<h2>
				成员 <span>完成</span>
			</h2>
		</section>
		<!--start 页面内容 -->
		<section class="plist pzuzhi memberList">
			<a href="javascript:;"><div><i></i><input type="radio" name="cy" class="cy">成员名称<em><u>职务</u></em></div></a> 
			<a href="javascript:;"><div>
					<i></i><input type="radio" name="cy" class="cy">成员名称<em><u>职务</u></em>
				</div></a> <a href="javascript:;"><div>
					<i></i><input type="radio" name="cy" class="cy">成员名称<em><u>职务</u></em>
				</div></a> <a href="javascript:;"><div>
					<i></i><input type="radio" name="cy" class="cy">成员名称<em><u>职务</u></em>
				</div></a> <a href="javascript:;"><div>
					<i></i><input type="radio" name="cy" class="cy">成员名称<em><u>职务</u></em>
				</div></a> <a href="javascript:;"><div>
					<i></i><input type="radio" name="cy" class="cy">成员名称<em><u>职务</u></em>
				</div></a> <a href="javascript:;"><div>
					<i></i><input type="radio" name="cy" class="cy">成员名称<em><u>职务</u></em>
				</div></a> <a href="javascript:;"><div>
					<i></i><input type="radio" name="cy" class="cy">成员名称<em><u>职务</u></em>
				</div></a> <a href="javascript:;"><div>
					<i></i><input type="radio" name="cy" class="cy">成员名称<em><u>职务</u></em>
				</div></a> <a href="javascript:;"><div>
					<i></i><input type="radio" name="cy" class="cy">成员名称<em><u>职务</u></em>
				</div></a>
		</section>
		<!--end 页面内容 -->
	</section>

</body>
<script type="text/javascript">
	$(function(){
		//根据组织初始化出部门
		$(".comp h2").each(function(){
			var companyId = $(this).attr("id");
			var companyName = $(this).text();
			//alert(companyId);
			$.ajax({
				url : '${ctx}/company/organize/getDepartments?companyId='+companyId,
				type : 'post',
				dataType :'text',
				async:false,
				error : function() {
					$.pop.load(false, function() {
						$.pop.lock(false);
						setTimeout(function() {
							$.pop.tips('系统繁忙,请稍候');
						}, 666);
					});
				},
				success:function(data){
					var departments = eval(data);
					for(var x in departments){
						var str = '<a href="javascript:;" class="i10" id="' + departments[x].id + '"  deptName="' + departments[x].deptName + '" companyId="' + companyId + '" companyName="' + companyName + '"><div><i></i><span>' + departments[x].deptName + '<span><em>'+departments[x].memberCount+'<u>></u></em></div></a>';
						$(".pzuzhi[company="+companyId+"]").append(str);
					}
				},
			});
		});
		
		//给部门添加监听
		$("#zhizu .pzuzhi a").click(function() {
			refreshMember($(this).attr("id"),$(this).attr("deptName"),$(this).attr("companyId"),$(this).attr("companyName"));
			$("#zhizu,.header2").hide();	
			$("#chengyuan,.header3").show();	
			
		});
		
		/**
		* 刷新成员
		*/
		function refreshMember(departmentId,deptName,companyId,companyName){
			//alert(departmentId);
			//alert(deptName);
			//alert(companyId);
			//alert(companyName);
			$(".memberList").empty();
			$.ajax({
				url : '${ctx}/company/organize/getMembers?deptId='+departmentId,
				type : 'post',
				dataType :'text',
				async:false,
				error : function() {
					$.pop.load(false, function() {
						$.pop.lock(false);
						setTimeout(function() {
							$.pop.tips('系统繁忙,请稍候');
						}, 666);
					});
				},
				success:function(data){
					var members = eval(data);
					for(var x in members){
						var str = '<a href="javascript:;"><div><i></i><input  type="radio" id="'+members[x].id+'" departmentId="'+departmentId+'" deptName="'+deptName+'" companyId="'+companyId+'" companyName="'+companyName+'" memberName="'+members[x].name+'" memberPhone="'+members[x].mobilePhone+'" name="cy" class="cy">' + members[x].name + '<em><u>' + members[x].job + '</u></em></div></a>';
						$(".memberList").append(str);
					}
					
					$("#chengyuan .pzuzhi a").click(function() {
						$(this).addClass("hover").siblings().removeClass("hover")
						$("input[name=cy]").attr("checked", false);
						$($(this).find('.cy')[0]).attr("checked", true)
					})
				},
			});
		}
		
		//点击选择取票人，显示部门员工通讯录
		$(".people span").click(function() {
			$("#tickerContent,.header1").hide();
			$("#zhizu,.header2").show();
		});
		
		$(".zuzhi_style h2 span").on("click", function() {
			//拿到数据并填充
			var member = $("input.cy:checked");
			var memberId = member.attr("id");
			var memberPhone = member.attr("memberPhone");
			var memberName = member.attr("memberName");
			var deptName = member.attr("deptName");
			var deptId = member.attr("departmentId");
			var compName = member.attr("companyName");
			var compId = member.attr("companyId");
			$("input#takeTicketUserName").val(memberName);
			$("input#takeTicketUserMobile").val(memberPhone);
			$("input#memberId").val(memberId);
			$("input#departmentName").val(deptName);
			$("input#departmentId").val(deptId);
			$("input#companyName").val(compName);
			$("input#companyId").val(compId);
			$("#tickerContent").show();
			$("#zhizu,.header2").hide();	
			$("#chengyuan,.header3").hide();
		})
		
		/**
		* 搜索成员或者部门
		*/
		$("em#z_sea_icon").on("click",function(){
			var text = $(".txt_search").val();
			$.ajax({
				url : '${ctx}/company/search',
				type : 'post',
				dataType :'text',
				data:{"searchName":text},
				async:false,
				error : function() {
					$.pop.load(false, function() {
						$.pop.lock(false);
						setTimeout(function() {
							$.pop.tips('系统繁忙,请稍候');
						}, 666);
					});
				},
				success:function(data){
					//alert(data);
					var data = eval("("+data+")");
					if(data.result==1){
						$(".memberList").empty();
						var members = data.companySearchList;
						for(var x in members){
							var str = '<a href="javascript:;"><div><i></i><input  type="radio" id="'+members[x].memberId+'" departmentId="'+members[x].departId+'" deptName="'+members[x].departName+'" companyId="'+members[x].companyId+'" companyName="'+members[x].companyName+'" memberName="'+members[x].memberName+'" memberPhone="'+members[x].mobile+'" name="cy" class="cy">' + members[x].memberName + '<em><u>' + members[x].job + '</u></em></div></a>';
							$(".memberList").append(str);
						}
						$("#chengyuan .pzuzhi a").click(function() {
							$(this).addClass("hover").siblings().removeClass("hover")
							$("input[name=cy]").attr("checked", false);
							$($(this).find('.cy')[0]).attr("checked", true)
						});
						$(".header2").hide();
						$(".header3").show();
						$("#zhizu").hide();
						$("#chengyuan").show();
					}else if(data.result==2){
						var depts = data.companySearchList;
						$("section.comp").remove();
						$("section.dept").remove();
						var dept = '';
						for(var x in depts){
							var comp = '<section class="zuzhi_style comp"><h2 id="'+depts[x].companyId+'">'+'部门结果'+'</h2></section><section class="plist pzuzhi dept" company="'+depts[x].companyId+'" ></section>';
							dept = dept + '<a href="javascript:;" class="i10" id="' + depts[x].departId + '" companyId="' + depts[x].companyId + '"><div><i></i><span>' + depts[x].departName + '<span><em><u></u></em></div></a>';
						}
						$('section.zuzhi_cont').append(comp);
						$(".pzuzhi[company="+depts[x].companyId+"]").append(dept);
						//给部门添加监听
						//给部门添加监听
						$("#zhizu .pzuzhi a").click(function() {
							refreshMember($(this).attr("id"),$(this).attr("deptName"),$(this).attr("companyId"),$(this).attr("companyName"));
							$("#zhizu,.header2").hide();	
							$("#chengyuan,.header3").show();	
							
						});
						$(".header3").hide();
						$(".header2").show();
					}else if(data.result==-1){
						$.pop.tips("查询无结果");
					}
				},
			});
		});
	});
	
	$(function(){
		document.onkeydown = function(e){
			if(e.keyCode==13){
				return ;
			}
		}
	});
	
	$("#zzBack").click(function(){
		$("#zhizu,.header2").hide();
		$("#tickerContent,.header1").show();
	 	});
	$("#zzBack01").on("click",function(){
		$("#chengyuan,.header3").hide();
		$(".header2,#zhizu").show();
		})			
	$(".zuzhi_style span").on("click",function(){
		$("#chengyuan,.header2").hide();
		$(".header1").show();		
		})	

</script>
</html>