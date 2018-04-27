// 使用页面：company_accountInf.html
// 日期：2015、4/29
// 作者：lw
$(document).ready(function() {

	var documentWidth = $(document).width();    //屏幕可视区宽度
	var documentHeight = $(document).height();   //屏幕可视区高度
	var step1 = '<span><i>已发送手机验证至您原始的手机号</i><i class="bold" style="margin-left:12px;" id="old_mobile"></i></span><br/><span><i>请输入您收到的验证码</i><input maxlength="6" type="text" class="input_p inp yz" name="validCode" id="validCode"><a href="javascript:" class="sendCode1" id="sendCode1">发送验证码</a></span><span><a class="determine1 determine yahei h3 tc fl" href="javascript:;">确定</a></span>';
	var step2 = '<span><i>请输入新手机号</i><input type="text" name="" id="changeMobile" maxlength="11" class="mobile input_p inp fl yahei h3"><i class="countdown2"><a href="javascript:;" class="verification verifications hover tc yahei h3 color3 normal fl" >获取验证码</a></i></span><br/><span><i>请输入您收到的验证码</i><input type="text" class="inp input_p yz" maxlength="6" name="" id="new_validCode"></span><span><a class="determine2 determine yahei h3 tc fl" href="javascript:;">确定</a></span>';
	var step3 = '<span><i id="mobile_success">手机133****5698已经验证通过，手机号码已更改</i></span><br/><span><a class="determine3 determine yahei h3 tc fl" href="javascript:;">确定</a></span>';
	var step1Send=true,step2Send=true,step3Send=true;

	/*************************************************************************************************/



	/*ajax获取城市列表*/
	/*城市选择级联菜单*/
	$(document).on("click",".province li",function(){  //选择省份
		$('.citys').html('请选择');
		$(".city .l").remove();
		var optionsValue=$(this).html();
		var optionId=$(this).attr("value");
		$(this).parents(".selectNav").prev(".select").html(optionsValue);
		$(this).parents(".selectNav").css({"display":"none"});


		$.ajax({
			type: "POST",
			url: "../comRegister/searchCity?province="+optionId,
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			dataType:"json",
			success: function(data) {
				if (data.length > 0) {
					var html="";
					$.each(data, function (idx, item) {
						html+="<li class='l' value='"+item.id+"' id='city_"+item.id+"'>"+item.name+"</li>";
					});

					$("#city_id").empty();
					$("#city_id").append(html);
					if($("#city").val()!=""){
						$("#city_"+$("#city").val()).click();
					}
				}
			}
		});

	});
	$(document).on("click",".city li",function(){   //选择城市
		var optionsValue=$(this).html();
		$(this).parents(".selectNav").prev(".select").html(optionsValue);
		$(this).parents(".selectNav").css({"display":"none"});
	});
	/*城市选择级联菜单*/
	/*************************************************************************************************/
	/*点击更换手机号码*/
	$(document).on("click",".checkMobis",function(){
		$(".checkMobileBox").show();
		var marginLeft = documentWidth/2-250+'px';
		var marginTop = documentWidth/12+'px';
		var secret_mobile=$("#per_mobile").html()
		$(".checkMobileBox").css({"margin-left":marginLeft,"margin-top":marginTop}).show();
		$(".checkBox").html(step1);   //添加第一部验证
		$("#old_mobile").html(secret_mobile);


	});

	//发送旧手机验证码

	$(document).on("click","#sendCode1",function(){
		var mobile=$("#per_mobile").attr("value"),
			secret_mobile=$("#per_mobile").html()
			;
		if(step1Send==true){
			step1Send=false;
			$.ajax({
				type: "POST",
				url: "../comRegister/sendCode2?mobile="+mobile+"&scene=3",
				dataType:"json",
				success: function(result) {

					if(result.status=="success"){
						var wait = 60;
						function time(){
							if (wait == 0) {
								$('#sendCode1').html("重新发送");
								wait = 60;
								step1Send=true;
							} else {
								$('#sendCode1').html("重新发送("+wait+")");
								wait--;
								setTimeout(function() {
									time();
								},1000)
							}
						}
						time();
						//弹出更换手机号码弹框

						$("#old_mobile").html(secret_mobile);
						$("#token").val(result.validToken);

					}else{
						alert(result.error);
						$("#sendCode1").html("发送失败");
						step1Send=true;
					}
				},
				error:function(result) {
					step1Send=true;
					$("#sendCode1").html("发送失败");
				}
			});

		}
	});
//提交验证码
	$(document).on("click",".determine1",function(){
		var token=$("#token").val()
			,validCode=$("#validCode").val();
		if(validCode!=""){
			$.ajax({
				type: "POST",
				url: "../company/infoManage/validateTel?token="+token+"&validCode="+validCode,
				dataType:"json",
				success: function(result) {
					if(result.flag=="success"){
						$(".checkBox").html(step2);
					}else{
						alert(result.flag);
						$("#validCode").css("border","1px solid　red");
						setTimeout(function(){
							$("#validCode").css("border","1px solid　rgb(223, 232, 239)");
						},1500);
					}
				}
			});
		}else{
			alert("验证码无效");
		}


	});
//获取新手机的验证码

	$(document).on("click",".countdown2",function(){
		var mobile=$("#changeMobile").val();
		if(step2Send==true){
			step2Send=false;
			var reg = /^0?(13|15|17|18)[0-9]{9}/;
			if(mobile!=""&&mobile.length==11&&reg.test(mobile)){
				$.ajax({
					type: "POST",
					url: "../comRegister/sendCode2?mobile="+mobile+"&scene=4",
					dataType:"json",
					success: function(result) {
						if(result.status=="success"){
							var wait = 60;

							$("#token").val(result.validToken);
							//alert($("#token").val());
							function time(){
								if (wait == 0) {
									$('.countdown2 a').html("获取手机验证码");
									wait = 60;
									step2Send=true;
								} else {
									$('.countdown2 a').html("重新发送("+wait+")");
									wait--;
									setTimeout(function() {
											time();
										},
										1000)
								}
							}


							time();

						}else{
							step2Send=true;
							alert(result.error);
						}
					},
					error: function(result){
						console.log("网络错误");
						step2Send=true;
					}
				});
			}else{
				step2Send=true;
				alert("手机号码不正确");
			}
		}
	});

	$(document).on("click",".determine2",function(){
		var mobile=$("#changeMobile").val()
			,token=$("#token").val()
			,validCode=$("#new_validCode").val()	;

		if(step3Send==true){
			step3Send=false;
			$.ajax({
				type: "POST",
				url: "../company/infoManage/validateTel?token="+token+"&validCode="+validCode+"&mobile="+mobile,
				success: function(result) {
					var msg = eval('('+result+')');
					if(msg.flag=="success"){
						$(".checkBox").html(step3);
						$("#mobile_success").html("手机"+mobile+"已经验证通过，手机号码已更改");
						$("#per_mobile").html(mobile.substring(0,4)+"****"+mobile.substring(8));
						setTimeout(function(){
							$(".checkMobileBox").hide();
						},3000);
					}else{
						step3Send=true;
						alert(msg.flag);
					}
				}
			});

		}

	});

	$(document).on("click",".determine3",function(){
		$(".successBox").hide();                   //完成验证，手机号码成功更改
	});

});
