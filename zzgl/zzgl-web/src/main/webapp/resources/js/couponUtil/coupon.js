$(function(){
/*     卡券                        */
//页面加载隐藏现金券下拉框
$("#select").hide();
$("#useCondition").hide();
$("#couponid").val("");//清空代金券id
//页面刷新去掉所有复选框禁用
$(":input[checkbox='checkbox']").each(function(){
$(this).removeAttr("checked");
})
/*点击代金券单选框chit*/
$(".chit").click(function(){
	var couponId=$("#couponid").val();//得到当前卡券id
	var shouldmoney=$("#shouldmoney").html();//得到当前总金额
	var couponValuess=$("#diyongquan").html();//得到当前使用卡券的总金额
	var chit=$(this).attr("checkd");
	if(chit=="checkd"){
	$("#select").show();
	}else{
		calculate(1,couponId,couponValuess,shouldmoney,2);//identification 1为选中卡券 2为不选中
		$("#select").html("请选择优惠券");	
		$("#select").hide();
		$("#useCondition").hide();
			$(":input[disabled='disabled']").each(function(){
				$(this).removeAttr("disabled");
			});
			$("#couponid").val("");
		}
	})
/*选择下拉框中的代金券的金额*/
$(".checekdfaceValue").on("click",function() {
	var couponId=$(this).attr("name");//得到当前卡券id
	var shouldmoney=$("#shouldmoney").html();//得到当前总金额
	var couponValuess=$("#diyongquan").html();//得到当前使用卡券的总金额
	if(couponValuess==""){
		couponValuess==0;
	}
		//禁止掉现金券不能和代金券同时使用的
		$("[issharenotsametype='']").each(function(){
			$(this).removeClass("choice_on");
			$(this).attr("checkd","checkd");
		})
	$("#couponid").val(couponId);
	calculate(1,couponId,couponValuess,shouldmoney,1);//identification 1为选中卡券 2为不选中
	})
	/*封装一个ajax发送请求进行计算 type卡券类别 1代金券 2现金券*/
function calculate(type,couponId,youhui,pay,identification){
	var contextPath=$("#contextPath").val();
	var practicalprice=$("#practicalprice").val();//得到实际价格
	alert(practicalprice)
	$.ajax({type:"post",
		contentType:"application/x-www-form-urlencoded; charset=utf-8",
		url : ""+contextPath+"/coupon/estimateCouponFold?" +
				"couponId="+ couponId
				+ "&youhui="+ youhui 
				+ "&pay=" + pay 
				+ "&identification="+identification,
		dataType : "json",
		async:false,
		success : function(data) {
			var faceValue=data.faceValue;
			var pay=data.pay;
			var youhui=data.youhui;
			//保存每次计算的实际价格
			$("#practicalprice").val(pay);
			if(pay<0){
				pay=0;
			}
			if(youhui<0){
				youhui=0;
			}
			$("#shouldmoney").html(pay);
			$("#diyongquan").html(youhui);
			}
		})
	}
});
