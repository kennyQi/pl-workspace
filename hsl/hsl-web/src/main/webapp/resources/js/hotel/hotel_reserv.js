// JavaScript Document
$(document).ready(function(){
	var isAmountGuarantee=$("#isAmountGuarantee").val();
	$("#num").val("1");
	$(".min").on("click",function(){  //减少
		if($(this).next("#num").val()>1){
			$("#tourists").children("li:last").remove();
			$(this).next("#num").val(parseInt($(this).next("#num").val()) - 1);
			$(this).css({"color":"#777"});
		}else{
			$(this).css({"color":"#ddd"});
		}
		
		if(isAmountGuarantee=="true"&&parseInt($("#amount").val())>parseInt($("#num").val())){
				$(".guarantee").css("display","none");
		}
		
		guaranteeMoney();
		totalPrice();
	});
	$(".add").on("click",function(){  //增加
	     if($(this).prev("#num").val() <= 4){
		    $(this).prev("#num").val(parseInt($(this).prev("#num").val()) + 1);
		    $(".min").css({"color":"#777"});
		
			var rel=parseInt($(this).prev("#num").val());
			var relCn=["一","二","三","四","五"];
			var html='<li><span class="inf"><i>*</i>客人'+relCn[rel-1]+'姓名：</span>'+ 
			'<input type="" name="guestName" id="guestName" value="" style="width:240px;padding:0 8px 0 8px;">'+
			'<i class="guestName names"></i></li>';
			$(this).attr("rel",rel+1);
			$("#tourists").append(html);
			
			guaranteeMoney();
		 }
	     	if(isAmountGuarantee=="true"&&parseInt($("#amount").val())<parseInt($("#num").val())){
	     		$(".guarantee").css("display","block");
				$("#guaranteeMoney").val("1");
			}
	     	totalPrice();
	});
	
	
	
		/*$("#radio1").click(function(){      //radio1点击
			$("#radio1").css({"background":"url(../images/payScript.png) 0 -157px"});
			$("#radio2").css({"background":"url(../images/payScript.png) 16px -157px"});
		$(".guarantee").css({"display":"none"});
		$(".retain").css({"margin-bottom":"60px"});
	});
	$("#radio2").click(function(){      //radio2点击
	$("#radio2").css({"background":"url(../images/payScript.png) 0 -157px"});
	//$("#radio1").attr({"disabled":"disabled"});
	$("#radio1").css({"background":"url(../images/payScript.png) 16px -157px"});
	$(".guarantee").css({"display":"block"});
	$(".retain").css({"margin-bottom":"0px"});
	});*/
	
	$("#roomSave i.radio").each(function(i,e){
		$(this).click(function(){
			$("#roomSave i.radio").removeClass("radioOn").eq(i).addClass("radioOn");
			if(i==1){
				$(".guarantee").css({"display":"block"});
				$(".retain").css({"margin-bottom":"0px"});
			}else{
				$(".guarantee").css({"display":"none"});
				$(".retain").css({"margin-bottom":"60px"});
			}
		});
	});
	
	
	$(".select").click(function(){//下拉列表选择值
		var displayValue=$(this).children(".selectNav").css("display");
		$(".selectNav").css({"display":"none"});
		if(displayValue == "none"){
			$(this).children(".selectNav").css({"display":"block"});
		}else{
			$(this).children(".selectNav").css({"display":"none"})
		}
	});
	$(document).on("click",".selectNav li",function(){
		//下拉列表选值
		var optionsValue=$(this).html();
		$(this).parents(".select").children(".optionValue").html(optionsValue);
		});
	$("body").bind("click",function(evt){     //点击空白处隐藏
	if($(evt.target).parents("div > ul > li").length==0) { 
	   $('.selectNav').hide(); 
	  } 
	});
	
	var dateTime=new Date();
	var dateTimeHtml="";
	for(var i=0;i<20;i++){
		dateTimeHtml+='<li class="y">'+(dateTime.getFullYear()+i)+'年</li>'
	}
	$(".selectNavYear").html(dateTimeHtml);


});

 function totalPrice()
 {
	 var price = Number($("#totalRateTwo").attr("data-old"));
	 var num = parseInt($("#num").val());
	 var totalPrices = price*num;
	 $("#totalRateTwo").text(totalPrices);
 }
 
 function guaranteeMoney(){
	//更新保证金
	var num=parseInt($("#num").val());
	var guaranteeMoneys=parseInt($("#guaranteeMoneys").val());
	guaranteeMoneys=num*guaranteeMoneys;
	
	$("#grarantee").html(guaranteeMoneys);
	 
 }