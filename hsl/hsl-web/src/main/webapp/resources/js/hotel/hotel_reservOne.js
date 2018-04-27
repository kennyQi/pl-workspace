$(function(){
	$("#arrivalTime").val(QueryString("arrivalTime"));
	$("#departureTime").val(QueryString("departureTime"));
	layTime();
	$("[name='layTime']").blur(function(){
		layTime();
	});
	$("#save").click(function(){//提交表单

		$("#arrivalTimes").val($("#arrivalTime").val().trim());//入住时间
		$("#departureTimes").val($("#departureTime").val().trim());//退房时间
		$("#numberOfRooms").val(parseInt($("#num").val().trim()));
		$("#NumberOfCustomers").val(parseInt($("#num").val().trim()));
		$("#totalPrices").val($("#totalRateTwo").html().trim());
		$("#contactMobile").val($("#relationMobile").val());
		$("#contactPhone").val($("#relationMobile").val());
		$("#contactEmail").val($("#relationEmail").val());
		$("#contactName").val($("#relationName").val());
		/********得到所有客人的姓名**********/
		var customersName="";
		$("#guestName").each(function(){
			customersName+=$(this).val()+",";
		})
		$("#customersName").val(customersName.substring(0, customersName.length-1));
		var names=true;
		$(".names").each(function(){//判断提示信息是否为空
			console.info($(this).html()!=""&&$(this).html()!=null)

			if($(this).html()!=""&&$(this).html()!=null){
				console.info($(this).html()+"=")
				names=false;

			}
		})
		$("[name='guestName']").each(function(){//判断文本框否为空
			if($(this).val()==""){
				names=false;
			}
		})
		if($("#relationMobile").val==""){//手机
			names=false;
		}
		if($("#guaranteeMoney").val()=="1"||($("#radio2").hasClass("radioOn")&&$("#grarantee").val()!="0")){//需要交保证金
			var blockId=$("#blockId").val(),
			holderName=$("#holderName").val(),
			selectNavMonth=$("#selectNavMonth").html(),
			selectNavYear=$("#selectNavYear").html(),
			idNo=$("#idNo").val(),
			cvv=$("#cvv").val();

			if(IdentityCodeValid(idNo)==false){
				names=false;
			}
			if($("#blocktf").val()=="0"){
				if(selectNavYear!="请选择"){
					selectNavYear=selectNavYear.substring(0,4);
				}else{
					alert("请选择年份");
					names=false;
				}
				if(selectNavMonth!="请选择"){
					selectNavMonth=selectNavMonth.substring(0,4);
				}else{
					alert("请选择月份");
					names=false;
				}
				$("#hidenumber").val(blockId);
				$("#hidecvv").val(cvv);
				$("#hideexpirationYear").val(parseInt(selectNavYear));
				$("#hideexpirationMonth").val(parseInt(selectNavMonth));
				$("#hideholderName").val(holderName);
				$("#hideidNo").val(idNo);
				var blockNews='';
				$(".blockNews").each(function(){
					if($(this).val()==""){
						blockNews="1";
					}
				})
				if(blockNews=="1"){
					alert("信用卡信息不能为空");
					names=false;
				}

			}else{
				alert("信用卡不可用,请重新输入");
				names=false;
			}

		}else{
			$("#creditCard").remove();
		}

		if($("#arrivalTime").val().trim()!="" && $("#departureTime").val().trim()!=""&& names==true){
			$("#creteOrder").submit();
			//alert("请填入正确信息");
		}else{
			$("[name='guestName']").trigger("blur");
			$("#relationMobile").blur();
			//alert("请填入正确信息");
		}
	})
	$(document).on("blur","[name='guestName']",function(){//验证姓名
		if($(this).val()==""){
			$(this).siblings(".guestName").show().html('*请输入姓名');
			$(this).siblings(".guestName").css("color","red");
		}else{
			$(this).siblings(".guestName").hide().html("");
		}
	})
	$("#relationMobile").blur(function(){//验证手机
		pattern = /^0?(13[0-9]|15[012356789]|18[02356789]|17[0236789]|14[57])[0-9]{8}$/;
		if($(this).val()==""){
			$(this).siblings(".relationMobile").show().html('*请输入手机号');
			$(this).siblings(".relationMobile").css("color","red");
		}else{
			if (!pattern.test(this.value)) {
				$(this).siblings(".relationMobile").show().html('*请输入正确手机号');
				$(this).siblings(".relationMobile").css("color","red");
			}else{
				$(this).siblings(".relationMobile").hide().html("");
			}
		}
	})
	$("#relationEmail").blur(function(){//验证邮箱
		pattern = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
		if (!pattern.test(this.value)) {
			$(this).siblings(".relationEmail").show().html('*请输入正确邮箱号');
			$(this).siblings(".relationEmail").css("color","red");
		}else{
			$(this).siblings(".relationEmail").hide().html("");
		}
	})
	$("#blockId").blur(function(){//验证信用卡是否可用
		var blockId=$(this).val();
		$.ajax({
			url:"../jd/checkBlock",
			type:"post",
			data:"blockId="+blockId,
			dataType:"json",
			success:function(data){
				if(data!=null){
					if(data.isValid=="false"){
						$("#blocktf").val("1");
					}
					if(data.isNeedVerifyCode=="true"){
						text='<span class="inf"><i>*</i>效验码：</span><span class="creditRight"><input type="" name="" id="cvv" value="" style="width:240px;padding:0 8px 0 8px;" maxlength="3">'+
						'<span class="ts3">信用卡背面三位有效校验码</span></span>';
						$("#cvv").empty();
						$("#cvv").append(text);
					}
				}
			}
		})
	})
})
//按照传入参数获取值url值
function QueryString(name) {
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r!=null) 
		return unescape(r[2]);
	return null; 

}
function layTime(){
	if(arrivalTime!=""||departureTime!=""){
		setTimeout(function(){
			var roomTypeId=QueryString("roomTypeId");
			var ratePlan=QueryString("ratePlan");
			var hotelIds=QueryString("hotelIds");
			var arrivalTime=$("#arrivalTime").val().trim();
			var departureTime=$("#departureTime").val().trim();
			$("#payment").empty();
			$.ajax({
				url:"../jd/getRatePlans",
				type:"post",
				data:"hotelIds="+hotelIds+"&arrivalTime="+arrivalTime+"&departureTime="+departureTime+"&roomTypeId="+roomTypeId+"&ratePlan="+ratePlan,
				dataType:"json",
				success:function(data){

					var text="";
					if(data.hotelDTO!=undefined&& data.hotelDTO.rooms[0]!=undefined){
						if(data.hotelDTO.guaranteeRules[0]==undefined){
							$("#inf").css("display","none");
						}else{
							console.info(data.hotelDTO.guaranteeRules[0].isAmountGuarantee==false&&data.hotelDTO.guaranteeRules[0].isTimeGuarantee==false);
							console.info(data.hotelDTO.guaranteeRules[0].isAmountGuarantee+"="+data.hotelDTO.guaranteeRules[0].isTimeGuarantee);
							
							$("#isAmountGuarantee").val(data.hotelDTO.guaranteeRules[0].isAmountGuarantee);
							$("#isTimeGuarantee").val(data.hotelDTO.guaranteeRules[0].isTimeGuarantee);
							
							if(data.hotelDTO.guaranteeRules[0].isAmountGuarantee==false&&data.hotelDTO.guaranteeRules[0].isTimeGuarantee==false){//强制担保
								$(".guarantee").css("display","block");
								$("#guaranteeMoney").val("1");
							}else if(data.hotelDTO.guaranteeRules[0].isAmountGuarantee==true&&data.hotelDTO.guaranteeRules[0].isTimeGuarantee==false){//房量担保
								if(parseInt($("#amount").val())<parseInt($("#num").val())){
									$(".guarantee").css("display","block");
									$("#guaranteeMoney").val("1");
								}else{
									$("#guarantee").css("display","none");
								}
							}else if(data.hotelDTO.guaranteeRules[0].isAmountGuarantee==true&&data.hotelDTO.guaranteeRules[0].isTimeGuarantee==true){//房量担保or最早最晚到店时间担保
								if(parseInt($("#amount").val())<parseInt($("#num").val())){
									$(".guarantee").css("display","block");
									$("#guaranteeMoney").val("1");
								}else{
									$(".guarantee").css("display","none");
								}
							}
							
							if(data.hotelDTO.guaranteeRules[0].guaranteeType=="FirstNightCost"){
								
								$("#grarantee").html(data.hotelDTO.rooms[0].ratePlans[0].nightlyRates[0].member);
								$("#guaranteeMoneys").val(data.hotelDTO.rooms[0].ratePlans[0].nightlyRates[0].member);
							}else if(data.hotelDTO.guaranteeRules[0].guaranteeType=="FullNightCost"){
								$("#grarantee").html(data.hotelDTO.rooms[0].ratePlans[0].totalRate);
								$("#guaranteeMoneys").val(data.hotelDTO.rooms[0].ratePlans[0].totalRate);
							}
						}
						var zc="";
						if(data.hotelDTO.valueAdds[0]!=undefined){
							if(data.hotelDTO.valueAdds[0].typeCode=="01"){
								zc="有早";
							}
						}else{
							zc="无早";
						}
						$.each(data.hotelDTO.rooms[0].ratePlans[0].nightlyRates,function(i,nightlyRates){
							text+='<li class="paymentLi"><span class="paymentTime">'+
							''+getTime(new Date(nightlyRates.date))+'</span><span class="paymentBottom">'+
							'<i>￥'+nightlyRates.member+'</i>'+zc+'</span></li>';
						})
						$("#totalRateOne").html(data.hotelDTO.rooms[0].ratePlans[0].totalRate);
						$("#totalRateTwo").html(data.hotelDTO.rooms[0].ratePlans[0].totalRate);
						$("#totalPrices").html(data.hotelDTO.rooms[0].ratePlans[0].totalRate);

					}else{
						text+='<li class="paymentLi"><span class="paymentTime">'+
						'暂无数据</li>';
					}
					//得到总价格

					$("#payment").append(text);
				}
			})
		},200);
	}

}
function getTime(date) {//回月份-天-星期几
	var now = "";
	var month = (date.getMonth() + 1).toString();
	now = now + month + "月";//取月的时候取的是当前月-1如果想取当前月+1就可以了
	var d = date.getDate().toString();
	if (d.length == 1) {
		d =  + d;
	}
	now = now + d + "日 ";
	var day=date.getDay();//得到天数
	var weekDay=["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];
	now=now+""+weekDay[day];
	return now;
}
/**********************************************身份证验证规则***************************************************/
function IdentityCodeValid(code) { 
	var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "};
	var tip = "";
	var pass= true;

	if(!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)){
		tip = "身份证号格式错误";
		pass = false;
	}

	else if(!city[code.substr(0,2)]){
		tip = "地址编码错误";
		pass = false;
	}
	else{
		//18位身份证需要验证最后一位校验位
		if(code.length == 18){
			code = code.split('');
			//∑(ai×Wi)(mod 11)
			//加权因子
			var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
			//校验位
			var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
			var sum = 0;
			var ai = 0;
			var wi = 0;
			for (var i = 0; i < 17; i++)
			{
				ai = code[i];
				wi = factor[i];
				sum += ai * wi;
			}
			var last = parity[sum % 11];
			if(parity[sum % 11] != code[17]){
				tip = "身份证最后一位应该为:"+parity[sum % 11];
				pass =false;
			}
		}
	}
	if(!pass) alert(tip);
	return pass;
}
