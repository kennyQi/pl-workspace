var frontmoney=0;//得到是全款支付还是定金支付
$(function(){
	//判断金额是否为0 如果为0隐藏卡券
	if($("#shouldmoney").html()==0.00){
		$(".g_discount").hide();
		$(".hiyh").hide();
	}
	frontmoney=parseFloat($("#frontmoney").val());
	var subNavTop=$("#float").offset().top;
	var cssTop=parseInt($("#float").css("top"));
	var g_histLeft=$("#float").offset().left;
	var g_histAb_Right=parseInt($("#float").css("right"));
	var footTop=$(".gd_foot").offset().top;
	$(window).scroll(function(){
		//获取float层距离顶端的高度
		if($(window).scrollTop()>(footTop)){
			$("#float").css({"top":cssTop,left:"","right":g_histAb_Right,"position":"absolute"});
		}else if($(window).scrollTop()>subNavTop){
			$("#float").css({"top":0,left:g_histLeft,"right":"","position":"fixed"});
			//$("#float").css({"position":"fixed","top":"0px"});
		}else {
			$("#float").css({"top":cssTop,left:"","right":g_histAb_Right,"position":"absolute"});
		};
	});
	/*计算初始金额*/

	/*	var adultPrices=parseInt($("#adultPrice").html());
	 var adultPricenumbers=parseInt($("#adultPricenumber").html());
	 $(".daijinquan").val("");
	 //成人zongjine
	 $("#SumadultPrice").html((adultPrices*adultPricenumbers).toFixed(2));
	 //儿童总金额
	 var childPricenumbers=parseInt($("#childPricenumber").html());
	 var childPrices=parseInt($("#childPrice").html());
	 $("#SumchildPrice").html(childPricenumbers*childPrices);
	 //定金总金额
	 var zongjine=(childPricenumbers*childPrices)+(adultPrices*adultPricenumbers);
	 $("#zongjine").html(zongjine);
	 //定金
	 var downPayment=$(".downPayment").val();

	 downPayment=downPayment/100;
	 var earnestmoney=zongjine*downPayment;
	 earnestmoney=earnestmoney+"";
	 //判断定金是否超过长度
	 if(earnestmoney.length>3){
	 earnestmoney=earnestmoney.substring(0, earnestmoney.lastIndexOf(".")+2);
	 }
	 $("#earnestmoney").html(earnestmoney);
	 //尾款
	 $("#finalpayment").html(zongjine-earnestmoney);
	 //应付金额
	 if(frontmoney==1){

	 $("#shouldmoney").html(earnestmoney);
	 $(".shouldmoneys").val(earnestmoney);
	 }else{
	 $("#shouldmoney").html(zongjine);
	 $(".shouldmoneys").val(zongjine);
	 }
	 */

	$("#addTourist").click(function(){      //添加游客
		var rel=parseInt($(this).attr("rel"));
		var html='<ul class="tourist ov">'+
			'<li class="touristName yahei"><span>游客'+rel+'</span><a href="javascript:;" class="delTourist">删除</a></li>'+
			'<li class="inp touristInp"><span class="travelTitle">姓名：</span><input type="text" name="" id="check" class="youkename" style="width:240px;padding:0 8px 0 8px;"><span style="color:red; margin-left:9px;"  class="mo ts">请填写真实姓名</span></li>'+
			' <li class="inp touristInp"><span class="travelTitle">手机：</span><input type="text" name="" id="check" class="mobile youkeshouji" style="width:240px;padding:0 8px 0 8px;"><span style="color:red; margin-left:9px;" class="mo ts">请填写正确的手机号码</span></li>'+
			' <li class="inp touristInp"><span class="travelTitle">身份证号码：</span><input type="text" name=""id="check" class="ID youkeid" style="width:240px;padding:0 8px 0 8px;"><span style="color:red; margin-left:9px;" class="mo ts">请填写正确的身份证号码</span></li>'+
			'<li><span class="line line1"></span></li>'+
			'</ul>';
		$(this).attr("rel",rel+1);
		/*修改人数*/
		//得到当前人数
		var adultPricenumber=$("#adultPricenumber").html()*1+1;
		//新添加一位,给人数赋值
		$("#adultPricenumber").html(adultPricenumber);
		$("#adult").val(adultPricenumber);
		//计算成人价格
		var adultPrice=$("#adultPrice").html();
		$("#SumadultPrice").html((adultPricenumber*adultPrice).toFixed(2));
		//计算儿童价格
		var childPricenumber=$("#childPricenumber").html();
		var childPrice=$("#childPrice").html();
		$("#SumchildPrice").html((childPricenumber*childPrice).toFixed(2));
		//修改总金额

		var SumadultPrice=$("#SumadultPrice").html();
		var SumchildPrice=$("#SumchildPrice").html();
		var zongjinge=(SumadultPrice*1+SumchildPrice*1).toFixed(2);
		/*	if(zongjinge.length>3){
		 zongjinge=zongjinge.subString(0,3)
		 }*/
		$("#zongjine").html(zongjinge);
		//定金
		var downPayment=$(".downPayment").val();
		downPayment=downPayment/100;
		var earnestmoney=(zongjinge*downPayment).toFixed(2);
		/*//判断定金是否超过长度
		 if(earnestmoney.length>3){
		 earnestmoney=earnestmoney.substring(0, earnestmoney.lastIndexOf(".")+2);
		 }*/
		$("#earnestmoney").html(earnestmoney);
		var frontmoney=$(".frontmoney").val();
		//应付金额
		if(frontmoney=="1"){
			$("#shouldmoney").html(earnestmoney);
			$(".shouldmoneys").val(earnestmoney);
		}else{
			$("#shouldmoney").html(zongjinge);
			$(".shouldmoneys").val(zongjinge);
		}
		//尾款
		$("#finalpayment").html(((SumadultPrice*1+SumchildPrice*1)-earnestmoney).toFixed(2));
		var salePrice=$("#shouldmoney").html();
		if(rel==1){$(".tourist").show();return;}else{
			$("#tourists").append(html);
		}

	});

	$("#addChildren").click(function(){    //添加儿童
		var rel=parseInt($(this).attr("rel"));
		var html='<ul class="children ov">'+
			' <span class="line line1" rel="'+rel+'"></span>'+
			'<li class="touristName yahei"><span>儿童'+rel+'</span><a dir="'+rel+'" href="javascript:;" class="delTourist">删除</a></li>'+
			'<li class="inp touristInp"><span class="travelTitle">姓名：</span><input type="text" name="" class="youkename" id="check" style="width:240px;padding:0 8px 0 8px;"><span style="color:red; margin-left:9px;" class="mo ts">请填写真实姓名</span></li>'+
			'<li class="inp touristInp"><span class="travelTitle">手机：</span><input type="text" name="" id="check" class="mobile youkeshouji" style="width:240px;padding:0 8px 0 8px;"><span style="color:red; margin-left:9px;" class="mo ts">请填写正确的手机号码</span></li>'+
			'</ul>';
		/*修改人数*/
		//得到当前人数
		var childPricenumber=$("#childPricenumber").html()*1+1;
		//新添加一位,给人数赋值
		$("#childPricenumber").html(childPricenumber);
		$("#children").val(childPricenumber);
		//计算价格
		var childPrice=$("#childPrice").html();
		$("#SumchildPrice").html((childPricenumber*childPrice).toFixed(2));
		//修改总金额
		var SumadultPrice=$("#SumadultPrice").html();
		var SumchildPrice=$("#SumchildPrice").html();

		var zongjinge=(SumadultPrice*1+SumchildPrice*1).toFixed(2);
		$("#zongjine").html((SumadultPrice*1+SumchildPrice*1).toFixed(2));
		//定金
		var downPayment=$(".downPayment").val();
		downPayment=downPayment/100;
		var earnestmoney=((SumadultPrice*1+SumchildPrice*1)*downPayment).toFixed(2);
		//判断定金是否超过长度
		/*	if(earnestmoney.length>3){
		 earnestmoney=earnestmoney.substring(0, earnestmoney.lastIndexOf(".")+2);
		 }*/
		$("#earnestmoney").html(earnestmoney);
		$("#earnestmoney").html(earnestmoney);
		var frontmoney=$(".frontmoney").val();
		//应付金额
		if(frontmoney=="1"){
			$("#shouldmoney").html(earnestmoney);
			$(".shouldmoneys").val(earnestmoney);
		}else{
			$("#shouldmoney").html(zongjinge);
			$(".shouldmoneys").val(zongjinge);
		}
		//尾款
		$("#finalpayment").html(((SumadultPrice*1+SumchildPrice*1)-earnestmoney).toFixed(2));
		var salePrice=$("#shouldmoney").html();
		$(this).attr("rel",rel+1);

		if(rel==1){$(".children").show();}else{
			$("#childrens").append(html);
		}
	});


	$(".radio").click(function(){
		$(this).closest(".discount").find(".radio").removeClass("radio_on");

		$(this).closest(".invoice").find(".radio").removeClass("radio_on");
		$(this).addClass("radio_on");

	});


	$(document).on("click",".choice:not(.usecoupons)",function(){

		if($(this).attr("can")=="false") return;
		if($(this).attr("class").indexOf("choice_on")!=-1){
			$(this).removeClass("choice_on");
			//$(this).attr("choices","");
		}else{
			$(this).addClass("choice_on");
			//$(this).attr("choices","choice_on");
		}
	});

	$("#select").click(function(){       //下拉列表选择值

		var displayValue=$("#options").css("display");
		if(displayValue == "none"){
			$("#options").css({"display":"block"});
		}else{
			$("#options").css({"display":"none"});
		}
	});

	$("#options li").click(function(){
		var optionsValue=$(this).html();
		$("#select span:eq(0)").html(optionsValue);
		$("#options").css({"display":"none"});
	});

	for(var i=0,l=parseInt($("#adult").attr("rel"));i<l;i++){
		$("#addTourist").trigger("click");
	}

	for(var i=0,l=parseInt($("#children").attr("rel"));i<l;i++){
		$("#addChildren").trigger("click");
	}
//验证邮箱

	var reg = /^([a-zA-Z0-9])+([a-zA-Z0-9\_])+@([a-zA-Z0-9\_-])+((\.[a-zA-Z0-9\_-]{2,3}){1,2})$/;
	$(".email").blur(function(){
		$(this).val($(this).val().replace(/ /g,""));
		if(!(reg.test($(this).val()))){
			$(this).siblings("span.mo").show();
			$(this).attr("id","false");
		}
	});
	$(".email").focus(function(){
		$(this).siblings("span.mo").hide();
		$(this).attr("id","true");
	});



});
///删除人数
$(document).on("click","#childrens .delTourist",function(){
	var rel=parseInt($(this).closest("#childrens").find("#addChildren").attr("rel"));
	var dir=$(this).attr("dir");
	if(rel==1){return false;}
	$(this).closest("#childrens").find("#addChildren").attr("rel",(rel-1));
	//$(this).closest("#childrens").find(".children:last").remove();
	if($(this).attr("rel")==undefined){
		//$(".line1[rel='"+dir+"']").remove();
		$(this).closest(".children").remove();
	}else{
		$(this).closest("#childrens").find(".children:last").remove();
		//$(".line1:last").remove();
	}


	//遍历人数排序
	$("#childrens .touristName span").each(function(i){
		$(this).html("儿童"+(i+1));

		$(this).next().attr("dir",(i+1));
	});

	/*修改人数*/
	//得到当前人数
	var childPricenumber=$("#childPricenumber").html()*1-1;
	//新添加一位,给人数赋值
	$("#childPricenumber").html(childPricenumber);
	$("#children").val(childPricenumber);
	//计算价格
	var SumchildPrice=$("#SumchildPrice").html();
	var childPrice=$("#childPrice").html();
	$("#SumchildPrice").html(SumchildPrice*1-childPrice*1);
	//修改总金额
	var SumadultPrice=$("#SumadultPrice").html();
	var SumchildPrice=$("#SumchildPrice").html();

	var zongjinge=(SumadultPrice*1+SumchildPrice*1).toFixed(2);
	$("#zongjine").html(zongjinge);
	//定金
	var downPayment=$(".downPayment").val();
	downPayment=downPayment/100;
	var earnestmoney=((SumadultPrice*1+SumchildPrice*1)*downPayment).toFixed(2);
	//判断定金是否超过长度
	/*if(earnestmoney.length>3){
	 earnestmoney=earnestmoney.substring(0, earnestmoney.lastIndexOf(".")+2);
	 }*/
	$("#earnestmoney").html(earnestmoney);
	var frontmoney=$(".frontmoney").val();
	//应付金额
	if(frontmoney=="1"){
		$("#shouldmoney").html(earnestmoney);
		$(".shouldmoneys").val(earnestmoney);
	}else{
		$("#shouldmoney").html(zongjinge);
		$(".shouldmoneys").val(zongjinge);
	}
	var salePrice=$("#shouldmoney").html();
	if($(this).attr("rel")==undefined){
		//$(this).closest(".children").remove();
		//$(".line1:last").remove();
	}else{
		//尾款
		$("#finalpayment").html(((SumadultPrice*1+SumchildPrice*1)-earnestmoney).toFixed(2));
	}

});


$(document).on("click","#tourists .delTourist",function(){
	var rel=parseInt($(this).closest("#tourists").find("#addTourist").attr("rel"));
	if(rel==2){return false;}
	$(this).closest("#tourists").find("#addTourist").attr("rel",(rel-1));

	/*修改人数*/
	/*var rel=parseInt($(this).attr("rel"));
	 $(this).attr("rel",rel-1);*/
	//得到当前人数
	var adultPricenumber=$("#adultPricenumber").html()*1-1;
	//新添加一位,给人数赋值
	$("#adultPricenumber").html(adultPricenumber);
	$("#adult").val(adultPricenumber);
	//计算价格
	var SumadultPrice=$("#SumadultPrice").html();
	var adultPrice=$("#adultPrice").html();
	$("#SumadultPrice").html(SumadultPrice*1-adultPrice*1);
	//修改总金额
	var SumadultPrice=$("#SumadultPrice").html();
	var SumchildPrice=$("#SumchildPrice").html();
	var zongjinge=(SumadultPrice*1+SumchildPrice*1).toFixed(2);
	$("#zongjine").html(zongjinge);

	//定金
	var downPayment=$(".downPayment").val();
	downPayment=downPayment/100;
	var earnestmoney=((SumadultPrice*1+SumchildPrice*1)*downPayment).toFixed(2);
	//判断定金是否超过长度
	/*if(earnestmoney.length>3){
	 earnestmoney=earnestmoney.substring(0, earnestmoney.lastIndexOf(".")+2);
	 }*/
	$("#earnestmoney").html(earnestmoney);
	var frontmoney=$(".frontmoney").val();
	//应付金额
	if(frontmoney=="1"){
		$("#shouldmoney").html(earnestmoney);
		$(".shouldmoneys").val(earnestmoney);
	}else{
		$("#shouldmoney").html(zongjinge);
		$(".shouldmoneys").val(zongjinge);
	}
	//尾款
	$("#finalpayment").html(((SumadultPrice*1+SumchildPrice*1)-earnestmoney).toFixed(2));
	var salePrice=$("#shouldmoney").html();
	if($(this).attr("rel")==undefined){
		$(this).closest(".tourist").remove();
	}else{
		if($(".tourist").length>1){
			$(this).closest("#tourists").find(".tourist:last").remove();
		}else{
			return ;
		}
	}
	//遍历人数排序
	$("#tourists .tourist .touristName span").each(function(i){
		$(this).html("游客"+(i+1));
	});



});

//验证手机

$(document).on("blur",".mobile",function(){
	var patrn=/^1[3|4|5|8][0-9]\d{8}$/;
	$(this).val($(this).val().replace(/ /g,""));
	if(!(patrn.test($(this).val()))){
		$(this).siblings("span.mo").show();
		$(this).attr("judge","false");
	}
});

$(document).on("focus",".mobile",function(){
	$(this).siblings("span.mo").hide();
	$(this).attr("judge","true");
});
//验证身份证号

$(document).on("blur",".ID",function(){
	var patrn=/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
	$(this).val($(this).val().replace(/ /g,""));
	if(!(patrn.test($(this).val()))){
		$(this).siblings("span.mo").show();
		$(this).attr("judge","false");
	}
});

$(document).on("focus",".ID",function(){
	$(this).siblings("span.mo").hide();
	$(this).attr("judge","true");
});
//姓名验证

$(document).on("blur",".youkename",function(){
	var patrn=$(this).val().replace(/(^\s*)|(\s*$)/g,'');
	if(patrn==""){
		$(this).siblings("span.mo").show();
		$(this).attr("judge","false");
	}
});

$(document).on("focus",".youkename",function(){
	$(this).siblings("span.mo").hide();
	$(this).attr("judge","true");
});


