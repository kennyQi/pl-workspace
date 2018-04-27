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
    <link rel="Stylesheet" href="${ctx}/css/fbase.css">
    <link rel="stylesheet" href="${ctx}/css/airplane_list.css" />
    <link rel="stylesheet" href="${ctx}/css/orderView.1.0.css" />
    <%@ include file="/page/common/jscommon.jsp"%>
    <script type="text/javascript" src="${ctx }/js/rili.js"></script>
    <script type="text/javascript" src="${ctx }/js/fly.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			del();//删除登机者
			$(".flight-btn-chgc").click(function() {//选择舱位
		    	window.location.href = "${ctx}/jp/clazz?openid=${openid}" + 
		    			"&flightNo=${condition.flightNo}&date=${condition.date}&${params}";
		    });
		});
		
		//删除登记人
		function del(){
		    $(".personal").delegate(".del","click",function() {
		      	if($(".del").length == 2){
		        	return true;
		      	}else{
					/* var name = $(this).parent().find(".js_name").val();
					var _names = [];
					for (var t = 0; t < names.length; t++) {
						if (names[t] == name) {
							names[t] = names[names.length - 1];
						}
					}
					for (var t = 0; t < names.length - 1; t++) {
						_names.push(names[t]);
					}
					names = _names;
					_names = null; */
		        	$(this).parent().remove();
		    	}
			});
		}
		
		//提交
		 function submit() {
			var flightNo = $("#flightNo").val();	// 航班ID
			var classCode = $("#classCode").val();	// 舱位代码
			var policyId = $("#policyId").val();	// 政策ID
			var date = $("#travelDate").val();	// 时间
			var $ps = $("#personal").find(".flight-listsim2");
			var  companyId = $("#companyId").val();
			var  companyName = $("#companyName").val();
			var  departmentId = $("#departmentId").val();
			var  departmentName = $("#departmentName").val();
			var  memberId = $("#memberId").val();
			var passangers = [];	// 登机人信息
			var names = [];
			var mobiles = [];
			var idcards = [];
			for (var i = 0; $ps != null && i < $ps.length; i++) {
				var $p = $($ps[i]);
				var name = $p.find('.js_name').val();
				var idcard = $p.find('.js_no').val();
				var mobile = $p.find('.js_mobile').val();
				if (name == null || name == ''
						|| idcard == null || idcard == ''
						|| mobile == null || mobile == '') {
					$.pop.tips("请完善登记人信息");
					return;
				}
				if (!name.match(/^[A-Za-z\u4e00-\u9fa5]+$/)) {
					$.pop.tips("请输入正确的所持证件姓名");
					return;
				}
				if (idcard.length != 15 && idcard.length != 18) {
					$.pop.tips("请输入正确的所持证件号码");
					return;
				}
				if (!mobile.match(/^[1][345789][0-9]{9}$/)) {
					$.pop.tips("请输入正确的联系手机号码");
					return;
				}
				var nameHasOne = false;
				var mobileHasOne = false;
				var idcardHasOne = false;
				for (var t = 0; t < names.length; t++) {
					if (names[t] == name) {
						nameHasOne = true;
					}
				}
				for (var t = 0; t < mobiles.length; t++) {
					if (mobiles[t] == mobile) {
						mobileHasOne = true;
					}
				}
				for (var t = 0; t < idcards.length; t++) {
					if (idcards[t] == idcard) {
						idcardHasOne = true;
					}
				}
				if (nameHasOne) {
					$.pop.tips("登机人重复,请分开下单");
					return;
				}
				if (idcardHasOne) {
					$.pop.tips("身份证号重复,请分开下单");
					return;
				}
				if (mobileHasOne) {
					$.pop.tips("联系方式重复,请分开下单");
					return;
				}
				names.push(name);
				idcards.push(idcard);
				mobiles.push(mobile);
				passangers.push({'name':name, 'cardNo':idcard, 
					'cardType': 'NI', 'identityType':'身份证', 'insueFee':0,
            		'insueSum':0, 'passangerType':'ADT', 'mobile':mobile,
            		'companyId':companyId,'companyName':companyName,
            		'departmentId':departmentId,'departmentName':departmentName,
            		'memberId':memberId});
			}
			var linkTel = $("#linkTel").val();
			if (!linkTel.match(/^[1][345789][0-9]{9}$/)) {
				$.pop.tips("请输入正确的联系手机号码");
				return;
			}
			var linker = {'mobile':linkTel, 'id':'${userId}'};
			var command = {'flightNo':flightNo, 'classCode':classCode, 'policyId':policyId, 
					'payType':1, 'date':date, 'passangers':passangers, 'linker':linker};
			$.pop.lock(true);
			$.pop.load(true, '正在下单中...');
			$.ajax({
				url:'${ctx}/jpo/confirm?openid=${openid}',
				data:{'command':encodeURI(JSON.stringify(command))},
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
						setTimeout(function() {
							if (data.result == "1") {	//下单成功
	           					$.pop.tips('下单成功', 1, function() {
		    		            	location.href = '${ctx}/jpo/pay?openid=${openid}&orderId=' + data.dealerOrderCode;
	           					});
	    		           	} else {
	    		           		$.pop.tips(data.message);
	    		           	}
	           			}, 666);
					});
				}
			});
		}
		
		function goFlightList() {
			location.href = "${ctx}/jp/list?openid=${openid}&date=${condition.date}&${params}";
		}
	</script>
</head>
<body style="position:relative;width:100%;overflow-y:hidden;">
    <!-- 公共页头  -->
    <!-- 公共页头  -->
	<input type="hidden" id="flightNo" value="${flight.flightNo }" />
	<input type="hidden" id="classCode" value="${classCode }" />
	<input type="hidden" id="policyId" value="${policy.policyId }" />
	<input type="hidden" id="travelDate" value="${condition.date}" />
	<input type="hidden" id="companyId" name="companyId"/>
	<input type="hidden" id="companyName" name="companyName"/>
	<input type="hidden" id="departmentId" name="departmentId"/>
	<input type="hidden" id="departmentName" name="departmentName"/>
	<input type="hidden" id="memberId" name="takeTicketUserInfo.id"/>
    <div id="contentWrap" style="position:absolute;width:100%;">
    <header class="header">
            <div class="header1" ><h1>订单填写</h1>
            <div class="left-head">
                <a id="goBack" href="javascript:goFlightList();" class="tc_back head-btn">
                    <span class="inset_shadow">
                        <span class="head-return"></span>
                    </span>
                </a>
            </div>
            </div>            
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
  </header>
        <!-- 页面内容块 -->

<section  id="JPContent">
      
        <!--航班信息-->
        <section class="flight" id="flightInfo">
            <div class="pd5">
                <h3 class="msgTitle">航班信息</h3>
                <ul class="flight-listsim mt0" data-flg="">
                    <li class="flight-fltinfo1">
                        <div class="fr">
                            ${flight.flightNo }
                            <br>${flight.aircraftName }</div> <i class="icon11"></i> <strong>${flight.startDate }</strong>&nbsp;${flight.airCompName }
                    </li>
                    <li class="flight-fltinfo2">
                        <p>
                            <strong>${flight.startTime }</strong>
                            ${flight.startPortName }
                        </p>
                        <p>
                            <strong>${flight.endTime }</strong>
                            ${flight.endPortName }
                        </p>
                        <iframe id="tmp_downloadhelper_iframe" style="display: none;"></iframe>
                    </li>
                    <li class="flight-fltinfo3">
                        <p class="clr-666">
                            <span data-flighttype="0" class="flight-btn-chgc">修改舱位</span>
                            <script type="text/javascript">document.write(JP.getClassType('${classCode }'));</script>&nbsp;${discount}折
                        </p>
                        <p>
                            <%-- <span class="fnt-15">机票价</span>
                            &nbsp;
                            <span class="fnt-15 clr-ff9913"> <dfn>¥</dfn>
                                <strong id="unitPrice">${policy.ticketPrice }</strong>
                            </span>
                            &nbsp;&nbsp; --%>
                            <span class="fnt-12">机建</span>
                            &nbsp;
                            <span class="fnt-12 clr-ff9913"> <dfn>¥</dfn>
                                <dfn id="airportTax"><fmt:formatNumber value="${policy.airportTax }" pattern="0"/></dfn>
                            </span>
                            &nbsp;&nbsp;
                            <span class="fnt-12">燃油</span>
                            &nbsp;
                            <span class="fnt-12 clr-ff9913"> <dfn>¥</dfn>
                                <dfn id="fuelTax"><fmt:formatNumber value="${policy.fuelSurTax }" pattern="0"/></dfn>
                            </span>
                        </p>
                        <div class="clr-666">不支持儿童、婴儿预订</div>
                        <p class="flight-arrdown flight-rmk-btn">退改签政策请咨询客服热线</p>
                    </li>
                </ul>
            </div>
        </section>
        
        <!--登机人-->
        <article class="pd5" id="flightBox">
            <section class="flight">
                <h3 class="msgTitle">登机者信息  <c:if test="${userType==2}"><span>选择登机人</span></c:if></h3>
               <div id="personal" class="personal" >
                    <!-- 登机者 1-->
                    <div id="addPassenger1" class="flight-listsim2 flight-add-psg">
                        <div class="msg">
                            <div class="flight-infoinput">
	                            <div class="flight-infoinput-pdl clear-input-box">
	                                <label  id="readmeAction" class="flight-labq">姓名</label>
	                                <input type="text"  id="personName" placeholder="所持证件姓名" class="js_name" value="" maxlength="20">
	                            </div>
	                        </div>
	                        <div class="flight-infoinput" >
	                            <div class="flight-infoinput-pdl clear-input-box">
	                                <label class="flight-arrdown">身份证号</label>
	                                <input type="text" id="personCardNo" placeholder="所持证件号码" class="js_no" data-index="0" value="" maxlength="18"> 
	                            </div>
	                        </div>
	                        <div class="flight-infoinput" style="border:none;">
	                            <div class="flight-infoinput-pdl clear-input-box">
	                                <label id="readmeAction" class="flight-labq">联系手机</label>
	                                <input type="text" id="personPhone" placeholder="联系人手机号码" class="js_mobile" value="" maxlength="11">
	                            </div>
	                        </div>
                        </div>
                        <div class="del"></div>
                    </div>
                    <!-- 添加更多联系人 -->
                    <div id="add" class="flight-listsim3" style="clear:both; display: none;">
                        <div id="js_addPass_btn" class="flight-btn-addps">
                            <span class="fr">添加更多乘机人</span>
                        </div>
                    </div>
               </div>
            </section>
            <!-- 手机号码 -->
            <section class="flight-listsim3-table">
               <div class="phone">
                    <label class="flight-arrdown">联系手机</label>
                    <input id="linkTel" data-role="tel" class="flight-linktel" placeholder="用于接收短信" type="tel" value="${curMobile }" maxlength="25">
               </div>
            </section>
            <!-- 客服咨询 -->
            <section class="flight-listsim4" id="insure" >
                <div class="flight-infoinput hotline" >获取行程单请咨询客服热线</div>
            </section>
            <section class="fpay" id="paybox">
                <div class="money js_fixed" style="background: #ff7e00; text-align: center; font-size: 15px;" id="paybtn" data-oamt="" data-amt="" onclick="submit();">下 一 步</div>
            </section>
        </article>

 

    <!-- 弹窗 -->
    <div id="windows" class="click">
        <div class="fix click">
            <div class="top">
                <ul>
                    <li>是否拨打客服电话</li>
                    <li><a href="tel:0571-28280813">0571-28280813</a></li>
                </ul>
            </div>
            <div class="cancel">取消</div>
        </div>
    </div>


	<!-- 模板 -->
	<div id="temp" style="display:none;">
	    <div id="addPassenger#p#" class="flight-listsim2 flight-add-psg">
	        <div class="msg">
	            <div class="flight-infoinput">
		            <div class="flight-infoinput-pdl clear-input-box">
		                <label id="readmeAction" class="flight-labq">姓名</label>
		                <input type="text" placeholder="所持证件姓名" class="js_name" value="">
		            </div>
		        </div>
		        <div class="flight-infoinput" >
		            <div class="flight-infoinput-pdl clear-input-box">
		                <label class="flight-arrdown">身份证号</label>
		                <input type="text" placeholder="所持证件号码" class="js_no" data-index="0" value=""> 
		            </div>
		        </div>
		        <div class="flight-infoinput" style="border:none;">
                     <div class="flight-infoinput-pdl clear-input-box">
                         <label id="readmeAction" class="flight-labq">联系方式</label>
                         <input type="text" placeholder="联系方式" class="js_mobile" value="">
                     </div>
                 </div>
	        </div>
	        <div class="del"></div>
	    </div>
	</div>
	
 </section>
 
 
 
<!-- 选择登机人时跳转到部门员工通讯录页面-->
<section id="zhizu" style="display:none"> 
	<section class="zuzhi_search">
		  <form action="" id="zuzhi_form1">
		      <div class="user_name">
		         <input type="text" value="" placeholder="搜索" class="txt_search"  id="J_txt_s" autocomplete="off">
		         <em id="z_sea_icon"></em>
		      </div>
		  </form>
	</section>
	<c:if test="${not empty companyList}">
			<c:forEach items="${companyList}" varStatus="status" var="company">
				<section class="zuzhi_style comp">
					<h2 id="${company.id}">${company.companyName}</h2>
				</section>
				<section class="plist pzuzhi dept" company="${company.id}" >
				</section>
			</c:forEach>
	</c:if>
</section>

<section id="chengyuan" style="display:none">
	<section class="zuzhi_style"><h2>成员  <span>完成</span></h2></section>
	
	<!--start 页面内容 -->
	<section class="plist pzuzhi memberList">
		<a href="javascript:;"><div><i></i><input type="radio" name="cy" class="cy">成员名称<em><u>职务</u></em></div></a> 
 		<a href="javascript:;"><div><i></i><input type="radio" name="cy" class="cy">成员名称<em><u>职务</u></em></div></a>
    	<a href="javascript:;"><div><i></i><input type="radio" name="cy" class="cy">成员名称<em><u>职务</u></em></div></a>
    	<a href="javascript:;"><div><i></i><input type="radio" name="cy" class="cy">成员名称<em><u>职务</u></em></div></a>
    	<a href="javascript:;"><div><i></i><input type="radio" name="cy" class="cy">成员名称<em><u>职务</u></em></div></a>
    	<a href="javascript:;"><div><i></i><input type="radio" name="cy" class="cy">成员名称<em><u>职务</u></em></div></a>
 		<a href="javascript:;"><div><i></i><input type="radio" name="cy" class="cy">成员名称<em><u>职务</u></em></div></a>
    	<a href="javascript:;"><div><i></i><input type="radio" name="cy" class="cy">成员名称<em><u>职务</u></em></div></a>
    	<a href="javascript:;"><div><i></i><input type="radio" name="cy" class="cy">成员名称<em><u>职务</u></em></div></a>
    	<a href="javascript:;"><div><i></i><input type="radio" name="cy" class="cy">成员名称<em><u>职务</u></em></div></a>
 	</section>
	<!--end 页面内容 -->
</section>
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
					var str = '<a href="javascript:;" class="i10" id="' + departments[x].id + '" companyId="' + companyId + '"><div><i></i><span>' + departments[x].deptName + '<span><em>'+departments[x].memberCount+'<u>></u></em></div></a>';
					$(".pzuzhi[company="+companyId+"]").append(str);
				}
				//给部门添加监听
				$("#zhizu .pzuzhi a").click(function() {
					refreshMember($(this).attr("id"),$(this).find("span").text(),companyId,companyName);
					$("#zhizu,.header2").hide();	
					$("#chengyuan,.header3").show();	
				});
			},
		});
	});
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
				var str = '<a href="javascript:;"><div><i></i><input  type="radio" id="'+members[x].id+'" departmentId="'+departmentId+'" deptName="'+deptName+'" companyId="'+companyId+'" companyName="'+companyName+'" memberName="'+members[x].name+'" memberCardNo="'+members[x].certificateID+'"memberPhone="'+members[x].mobilePhone+'" name="cy" class="cy">' + members[x].name + '<em><u>' + members[x].job + '</u></em></div></a>';
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

$(".zuzhi_style h2 span").on("click", function() {
	//拿到数据并填充
	var member = $("input.cy:checked");
	var memberId = member.attr("id");
	var memberPhone = member.attr("memberPhone");
	var memberName = member.attr("memberName");
	var memberCardNo = member.attr("memberCardNo");
	var deptName = member.attr("deptName");
	var deptId = member.attr("departmentId");
	var compName = member.attr("companyName");
	var compId = member.attr("companyId");
	$("input#personName").val(memberName);
	$("input#personPhone").val(memberPhone);
	$("#linkTel").val(memberPhone);
	$("input#personCardNo").val(memberCardNo);
	$("input#memberId").val(memberId);
	$("input#departmentName").val(deptName);
	$("input#departmentId").val(deptId);
	$("input#companyName").val(compName);
	$("input#companyId").val(compId);
	$("#chengyuan").hide();
	$("#JPContent,.header1").show();
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
							var str = '<a href="javascript:;"><div><i></i><input  type="radio" id="'+members[x].memberId+'" departmentId="'+members[x].departId+'" deptName="'+members[x].departName+'" companyId="'+members[x].companyId+'" companyName="'+members[x].companyName+'" memberName="'+members[x].memberName+'" memberCardNo="'+members[x].certificateID+'" memberPhone="'+members[x].mobile+'" name="cy" class="cy">' + members[x].memberName + '<em><u>' + members[x].job + '</u></em></div></a>';
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
							dept = dept + '<a href="javascript:;" class="i10" id="' + depts[x].departId + '" companyId="' + depts[x].companyId + '"><div><i></i><span>' + depts[x].departName + '<span><em>'+depts[x].memberCount+'<u>></u></em></div></a>';
						}
						$('section.zuzhi_cont').append(comp);
						$(".pzuzhi[company="+depts[x].companyId+"]").append(dept);
						//给部门添加监听
						$("#zhizu .pzuzhi a").click(function() {
							refreshMember($(this).attr("id"),$(this).find("span").text(),companyId,companyName);
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
</script>

<script type="text/javascript">
$(".msgTitle span").click(function(){
 	 $("#JPContent,.header1").hide();
	 $("#zhizu").show();	 
	 $(".header2").show();
	});	
$("#zhizu .pzuzhi a").click(function(){
	$("#zhizu").hide();
	$("#chengyuan").show();	
 	});		
  
$("#chengyuan .pzuzhi a").click(function(){
	$(this).addClass("hover").siblings().removeClass("hover")
 $("input[name=cy]").attr("checked",false); 
 $($(this).find('.cy')[0]).attr("checked",true)
 })	
 
$(".zuzhi_style h2 span").on("click",function(){
	$("#chengyuan").hide();
	$("#JPContent").show();	
	})

$("#zzBack").click(function(){
	$("#zhizu").hide();
	$("#JPContent").show();
 	});
$("#zzBack01").on("click",function(){
	$("#chengyuan,.header3").hide();
	$(".header2,#zhizu").show();
	})		

$("#zhizu_list a").on("click",function(){
	$("#zhizu,.header2").hide();	
	$("#chengyuan,.header3").show();		
	})	
	
$(".zuzhi_style span").on("click",function(){
	$("#chengyuan").hide();
	})	
</script>
</body>
</html>
