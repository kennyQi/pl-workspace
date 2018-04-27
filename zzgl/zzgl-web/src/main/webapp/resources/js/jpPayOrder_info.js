$(function(){
	queryAccount();//查询账户余额
	nouse();//初始化卡券状态
	/**********************************************页面加载查询卡券列表***************************************************/
	var orderPrice=$(".yue").html();
	$(".fly_sales").hide();//隐藏优惠券列表
	var contextPath=$("#contextPath").val();
	$.ajax({
		type : "post",
		async : false,
		url : contextPath+"/coupon/selectCoupon?couponType=1&orderPrice="+ orderPrice,
		success : function(data) {
			var coupons=eval('(' + data+ ')');
			var voucher="";
			var cashcoupon="";
			var count=0;
			$.each(coupons,function(msg,item){
				count+=1;
				if(item.baseInfo.couponActivity.baseInfo.couponType==1){
					voucher+=" <li class='checekdfaceValue l' name='"+item.id+"' style='cursor:pointer;' data-price='"+item.baseInfo.couponActivity.baseInfo.faceValue+"'>"+item.baseInfo.couponActivity.baseInfo.faceValue+"元优惠券</li>";
				}else{
					var date = new Date(item.baseInfo.couponActivity.useConditionInfo.endDate);
					cashcoupon+="<li><i class='checkboxq usecoupons orderbox Vouchers' re='false' value='"+item.id+"' ondblclick='this.click()'"
					+"isShareNotSameType='"+item.baseInfo.couponActivity.useConditionInfo.isShareNotSameType+"'"
					+"isShareSameKind='"+item.baseInfo.couponActivity.useConditionInfo.isShareSameKind+"'"
						+"data-price='"+item.baseInfo.couponActivity.baseInfo.faceValue+"'/>"+"<span class='money'> 使用"+item.baseInfo.couponActivity.baseInfo.faceValue+"元</span>"
					+"<span class='grey fly_payTs yahei h4 color9'>订单满 "+item.baseInfo.couponActivity.useConditionInfo.minOrderPrice+"元可使用,有效期至"+getTime(date)+"</span>";
					if(item.baseInfo.couponActivity.useConditionInfo.isShareNotSameType!=true){
						cashcoupon+="<span class='tips'>不能与代金券同时使用</span>";
					}else if(item.baseInfo.couponActivity.useConditionInfo.isShareSameKind!=true){
						cashcoupon+="<span class='tips'>不能与现金券同时使用</span>"	;
					}
					cashcoupon+="</li>";
				}
			})
			$("#position").html(count);
			$("#cashcoupon").append(cashcoupon);
			if(voucher!=""){
				$(".selectNav").append(voucher);
			}else{
				$("#options").remove();
			}
		}
	})
	/**********************************************点击代金券,查询使用条件和期限***************************************************/
	$(document).on("click",".selectNav li",function(){//下拉列表选值
		/************前端代码start***************/
		var optionsValue=$(this).html();
		var forDom=$(this).parent().attr("for");
		$("#"+forDom).html(optionsValue);
		$(this).parents(".selectNav").css({"display":"none"});
		/************前端代码end***************/
		var couponId=$(this).attr("name");
		var selectd = $(".selectd").html();

		//去掉现金券不能和代金券同时使用的
		$("[issharenotsametype='false']").each(function(){
			$(this).removeClass("VouchersClick");
		})
		//保存卡券id
		$("#useCouponIDs").val(couponId);
		//保存金额
		$("#daijinquan").val($(this).attr("data-price"));
		//计算优惠
		finalTotal();
	});
	/**********************************************点击复选框选择卡券***************************************************/
	$(".usecoupons").on("click",function(event) {
//var dianji=$(this).attr("re");
		//去掉或者选中标识符 1为选中 2为取消
		var identification=1;
		//判断当前的复选框是否选中
		var checked=$(this).hasClass("VouchersClick");
		var benshen=$(this);


		if(checked==false){
			benshen.addClass("VouchersClick");
			//得到当前的是否可以叠加
			var IsShareNotSameTypes=$(this).attr("isShareNotSameType");
			var IsShareSameKinds=$(this).attr("isShareSameKind");

			//不同类型和同意类型都不能叠加
			if(IsShareNotSameTypes != "true"&&IsShareSameKinds!= "true"){

				//取消其他已选中代金券和现金券
				$(".Vouchers").each(function(){
					$(".Vouchers").removeClass("VouchersClick");
					$(this).addCLass("VouchersClick");
				});
				//取消代金券选择
				if($("#use").hasClass("VouchersClick")){
					$("#use").click();
				}
			}
			//同类型能叠加,不同类型不能叠加
			if(IsShareNotSameTypes!="true"&&IsShareSameKinds=="true"){

				//取消代金券选择
				if($("#use").hasClass("VouchersClick")){
					$("#use").click();
				}
			}
			//不同同类型能叠加,同类型不能叠加
			if(IsShareNotSameTypes=="true"&&IsShareSameKinds!="true"){

				//取消其他已选中代金券和现金券
				$(".Vouchers").each(function(){
					$(".Vouchers").removeClass("VouchersClick");
					$(this).addCLass("VouchersClick");
				});
			}

		}else{
			$(this).removeClass("VouchersClick");

		}

		finalTotal();
	});
	//下拉框通用
	$(document).on("click",".companySelect",function(){
		$('.selectNav').hide();
		$(this).next(".selectNav").show();
	});
	/**********************************************点击代金券单选框***************************************************/
	$("#use").click(function(){

		if($(this).hasClass("VouchersClick")){
			//取消代金券
			$(this).removeClass("VouchersClick");
			//保存卡券id
			$("#useCouponIDs").val("");
			//保存金额
			$("#daijinquan").val(0);
			$("#optionValue").hide();
			$("#useCondition").hide();
		}else {
			//选中代金券
			$(this).addClass("VouchersClick");
			$("#optionValue").show();
			$("#optionValue").html("请选择现金券");
		}

	})

	/**********************************************提交支付信息***************************************************/
	$(".bth1").click(function() {
		if($("#service").hasClass("Agreement")) {
			//判断代金券是否勾选
			var use=$("#use").hasClass("VouchersClick");
			var selectd=$("#optionValue").html();
			var useCouponIDs="";
			if(use==true){
				var useCouponIDs = $("#useCouponIDs").val();//卡券的id
			}
			if(useCouponIDs!=""){
				useCouponIDs+=",";
			}
			var msg=useCouponIDs;
			//得到复选框所以的ID
			$(".usecoupons").each(function(i){
				if($(this).hasClass("VouchersClick")){
					msg+=$(this).attr("value")+",";
				}

			})
			msg= msg.substring(0, msg.length - 1);
			$("#useCouponIDs").val(msg);
			$("#couponPrice").val($("#youhui").html());
			//判断选中的是使用还是不使用
			var notuse=$("#nouse").hasClass("fly_pay_cli");
			if(notuse){
				$("#totalPrice").val($(".yue").html());
				$(".fly_loadWait").height($(window).height()).show();
				$("#myform").submit();
			}else{

				//获取卡券进行验证时需要的各个参数
				if(use==true&&selectd=="请选择优惠券"&&msg!=""){
					alert("请选择优惠券");
				}else{
					var dealerOrderId = $("#dealerOrderId").val();//订单号
					var payPrice = $("#payPrice").val();//最低限额
					//提交订单之前验证用户选择的卡券是否可用
					$.ajax({
						type : "post",
						async : false,
						url : contextPath+'/jp/getCouponUsed',
						dataType : "text",
						data : {
							'useCouponIDs' : msg,
							'dealerOrderId' : dealerOrderId,
							'payPrice' : payPrice
						},
						success : function(data) {
							if (data == "success") {
								$("#totalPrice").val($(".yue").html());
								$("#myform").submit();
							} else {
								alert(data);
								return false;
							}
						},
						error : function(msg) {
							alert(msg);
						}
					});
				}
			}
		}else{
			alert("请选择服务条款");
		}
	});

	//切换卡券时 重新计算
	$(".fly_saleSel i,.fly_sales").click(function(){
		setTimeout(function(){
			finalTotal();
		},100);
	});
})


var contextPath=$("#contextPath").val();
$(document).ready(function(e) {
	$("#del").eq(0).hide();
	$(document).on("click",".radio",function(){   //乘客类型选择
		$(this).toggleClass('radio_click');
	});

	$("body").bind("click",function(evt){     //点击空白处隐藏
		if($(evt.target).parents("ul > li").length==0) {
			$('.selectNav').hide();
		}
	});

	/*****************************************员工信息选择**********************************************/
	var company={                              //ajax获取数据
		method:"post",
		url :contextPath+"test_company.js",
		async:false,
		dataType :"script",
		success :function(data){
			for(var i=0;i<companys.length;i++){
				var html="<li class='l' co="+i+">"+companys[i]+"</li>";
				$(".com1").append(html);
			}

		}
	};

	/*****************************************员工信息选择**********************************************/
	/**********************************************表单验证***************************************************/
	var pattern = null;

	/**********************************************表单验证***************************************************/
	/*选择*/
	$(".fly_saleSel i").click(function(){
		$(".fly_saleSel .fly_pay_cli").removeClass();
		$(this).addClass("fly_pay_cli");
		var className = $(".fly_use i").attr("class");
		if(className == 'fly_pay_cli'){
			$(".fly_sales").show();
		}else{
			$(".fly_sales").hide();
		}
	});
	/*$(".Vouchers").click(function(){
	 // $(".Vouchers").removeClass("VouchersClick");
	 if($(this).hasClass("disabled")==false){//判断本次点击的复选框上是否有disable属性
	 $(this).addClass("VouchersClick");
	 }else{
	 alert(1)
	 $(this).removeClass("VouchersClick");
	 }

	 });*/
	$(".payfor").click(function(){
		//$(this).toggleClass("alipay");
	});
	$(".fly_Clause i").click(function(){
		$(this).toggleClass("Agreement");
	});


	//使用余额
	$(".fly_car_money .checkbox").click(function(){

		var that=$(this);
		if(that.hasClass("checkboxClick")){
			that.removeClass("checkboxClick");
			//$(".fly_car_pay").hide();
		}else{
			that.addClass("checkboxClick");
			//$(".fly_car_pay").show();
		}
		setTimeout(function(){
			finalTotal();
		},100);
	});

	//余额计算
	$(".Vouchers,.fly_saleSel i,.selectNav li").click(function(){
		setTimeout(function(){
			finalTotal();
		},100);
	});

});
/**********************************************时间格式化***************************************************/
function getTime(date) {
	var now = "";
	now = date.getFullYear() + "-"; //读英文就行了
	var month = (date.getMonth() + 1).toString();
	if (month.length == 1) {
		month = "0" + month;
	}
	now = now + month + "-";//取月的时候取的是当前月-1如果想取当前月+1就可以了
	var d = date.getDate().toString();
	if (d.length == 1) {
		d = "0" + d;
	}
	now = now + d + " ";
	var hour = date.getHours().toString();
	if (hour.length == 1) {
		hour = "0" + hour;
	}
	now = now + hour + ":";
	var minute = date.getMinutes().toString();
	if (minute.length == 1) {
		minute = "0" + minute;
	}
	now = now + minute + ":";
	var sec = date.getSeconds().toString();
	if (sec.length == 1) {
		sec = "0" + sec;
	}
	now = now + sec + "";
	return now;
}
/**********************************************用户选择使用卡券***************************************************/
function nouse(){
	$("#optionValue").hide();
	$("#useCondition").hide();
	$("#daijinquan").val("0");//初始化代金券金额
	$("#couponValue").val("");//初始化卡券
	$(".Vouchers").each(function(){
		$(this).removeClass("VouchersClick");
		$(this).removeClass("disabled");
	})
	$("#youhui").html("0.00");//初始化卡券金额
	$("#youhuis").html("0.00");
	$(".yue").html(parseFloat($(".payAmount").html()).toFixed(2));
}
/**********************************************查询帐号余额***************************************************/
function queryAccount(){
	$.ajax({
		type : "post",
		async : false,
		url : contextPath+"/accountController/queryAccount",
		success : function(data) {
			$("#totalMoney").html(parseFloat(data).toFixed(2));
		}
	})
}


function youhuiJisuan() {
	var xianjinPrice = 0;
	var daijinquanPrice = parseFloat($("#daijinquan").val());
	$("#cashcoupon .VouchersClick").each(function () {
		xianjinPrice += parseFloat($(this).attr("data-price"));
	});
	$("#youhui,#youhuis").html(xianjinPrice+daijinquanPrice);//赋值
	return xianjinPrice+daijinquanPrice;
}

function qianbaoJisuan(){
	var total=parseFloat($(".payAmount").html());
	var youhui=youhuiJisuan();
	var balance=parseFloat($("#totalMoney").html());//余额
	var acountMoney=0.00;
	if($(".fly_car_money .checkbox").hasClass("checkboxClick")) {
		if (total - youhui > 0) {
			//可以使用优惠券
			if(balance<=(total - youhui)){
				//余额不足
				acountMoney=balance;
			}else{
				//余额充足
				acountMoney=total - youhui;

			}

		} else {
			//无需使用账户余额
			acountMoney=0.00
		}
	}else{
		//未选择使用账户余额
		acountMoney=0.00;
	}
	var acountMoney=parseFloat(acountMoney).toFixed(2);
	$("#qianbao,#payMoney").html(acountMoney);//赋值
	$("#accountBalance").val(acountMoney);
	return acountMoney;
}

function finalTotal(){
	var total=parseFloat($(".payAmount").html());//总价
	var youhui=youhuiJisuan();//优惠
	var acountMoney=qianbaoJisuan();//余额
	$(".yue").html(((total-youhui-acountMoney)>0?(total-youhui-acountMoney):0.00).toFixed(2));
}

