// liwei
//2015-05-19

var check='0';
$(document).ready(function(){

	$("#num").val("1");
	 
	$(".min").on("click",function(){  //减少
		if($(this).next("#selectNum").val()>1){
			$(this).next("#selectNum").val(parseInt($(this).next("#selectNum").val()) - 1);
			$(this).css({"color":"#777"});
			var price = $("#p_price").val();
		    var selectNum = $("#selectNum").val();
		    var zj = price*selectNum;
		    $(".zj").html(zj);
		    $(".oder_p_val").val(zj);
		}else{
			$(this).css({"color":"#ddd"});
		}
	});
	$(".add").on("click",function(){  //增加
	   /* *//* if($(this).prev("#num").val() <= 4){*/
		    $(this).prev("#selectNum").val(parseInt($(this).prev("#selectNum").val()) + 1);
		    $(".min").css({"color":"#777"});
		    var price = $("#p_price").val();
		    var selectNum = $("#selectNum").val();
		    var zj = price*selectNum;
		    $(".zj").html(zj);
		    $(".oder_p_val").val(zj);
		    $("#totalPrice").val(zj);
		/* }*/
	});
	$(document).on("mouseout",".mouse",function(){
		$(this).find('.money').css({"color":"#6fbf4d"});
	});
	$(document).on("mouseover",".mouse",function(){
		$(this).find('.money').css({"color":"#ff6600"});
	});
	$(".mobile").blur(function() {    //验证手机号码
	  if(this.value){
	    pattern = /^1[3|4|5|8][0-9]\d{8}$/;
	    if (!pattern.test(this.value)) {
	      $(this).siblings(".tick_ts").html('请输入正确的手机号码');
		  $(this).siblings(".tick_ts").css({"color":"red"});

	    }else{
		  $(this).siblings(".tick_ts").html('接收订单确认短信，请务必填写填写正确');
		  $(this).siblings(".tick_ts").css({"color":"#888"});
		  check='1';
		}
	  }
	});
	
	
	var reg = /^[\u4E00-\u9FA5]{1,10}$/;
	//取票人姓名验证
	$("#goname").blur(function(){
		
	  if(!reg.test($(this).val())){
		  $(this).siblings(".tick_ts").html('取票人格式错误');
		  $(this).siblings(".tick_ts").css({"color":"red"});
	  }else{
		  $(this).siblings(".tick_ts").html('输入姓名须与身份证上一致，取票时须出示身份证');
		  $(this).siblings(".tick_ts").css({"color":"#888"});
		  check='1';
	   }
	   
	});
	//预定人姓名
	$("#ydname").blur(function(){
		
	  if(reg.test($(this).val())){
		  $(this).siblings(".tick_ts").html('预定人格式错误');
		  $(this).siblings(".tick_ts").css({"color":"red"});
		  
	  }else{
		  
		  $(this).siblings(".tick_ts").html('');
		  $(this).siblings(".tick_ts").css({"color":"#888"});
		  check='1';
		  
	  }
	   
	});
  //景点通讯录
	$(document).ready(function(e) {
		
		$(document).on("click",".radio",function(){   //乘客类型选择
			//$(this).toggleClass('radio_click');
			}); 
		//下拉框通用
		$(document).on("click",".companySelect",function(){
			$('.selectNav').hide();
	        $(this).next(".selectNav").show();
	    });
		
		$("#cardType").val("NI");
		$(document).on("click",".selectNav li",function(){//下拉列表选值
	        var optionsValue=$(this).html();
	        var forDom=$(this).parent().attr("for");
	        $("#"+forDom).html(optionsValue);
	        $(this).parents(".selectNav").css({"display":"none"});
	        //给需要提交的隐藏域复制证件类型
	        $("#cardType").val($(this).attr("name"));
	    });
	$("body").bind("click",function(evt){     //点击空白处隐藏
	if($(evt.target).parents("ul > li").length==0) { 
	   $('.selectNav').hide(); 
	  } 
	});
	$(".companyLink").click(function(){
		$(".companyEmployee").show();
		});
	$(".close").click(function(){
		$(".companyEmployee").hide();
		});
	var contextPath=$("#contextPath").val();
	/*****************************************员工信息选择**********************************************/
	var company={                              //ajax获取数据
	       method:"post",  
	       url :"../jp/companys",
		   async:false,  
	       dataType :"script", 
	       success :function(data){
	    	   var list = eval(data);
	    	   $.each(list,function(i,bt){
	    		   var html="<li class='l' co="+bt.id+">"+bt.companyName+"</li>";
	 			  $(".com1").append(html); 
	    	   })
	       }
	    };
	$.ajax(company);
	$(document).on("click",".com1 li",function(){    //获取公司
		$(".employeeMessBox").html("");
		$("#optionValue3").html("请选择部门");
		$("#optionValue4").html("请选择人员");
		$(".com2 li").remove();
		$(".com3 li").remove();
		coms = $(this).attr('co');
		$.ajax({
			  url :"../jp/getDepartments?companyId="+coms+"",
			dataType:"script",
			success: function(data){
				var list = eval(data);
				if(list.length<=0){
					$("#optionValue3").html("无更多部门");
					return;
				}
				 $.each(list,function(i,bt){
					var html="<li class='l' pe="+bt.id+">"+bt.deptName+"</li>";
					$(".com2").append(html);
		    	   })
				}
			});
	});
	$(document).on("click",".com2 li",function(){    //获取部门
		$(".employeeMessBox").html("");
		$("#optionValue4").html("请选择人员");
		$(".com3 li").remove();
	    pers = $(this).attr('pe');
		$.ajax({
			url :"../jp/getMembers?deptId="+pers+"",
			dataType:"script",
			success: function(data){
				var list = eval(data);
				if(list.length<=0){
					$("#optionValue4").html("无更多人员");
					return;
				}
				$.each(list,function(i,bt){
					var html="<li class='l' de="+bt.id+">"+bt.name+"</li>";
					 $(".com3").append(html);
		    	  })
				
				}
			});
	});
	var me1=""
	,me2=""
	,me3=""
	,me4=""
	,me5=""
	,me6="";
	$(document).on("click",".com3 li",function(){   //获取个人信息
		depa = $(this).attr('de');
		$(".employeeMessBox li").remove();
		$.ajax({
			  url :"../jp/getMembersInfomation?memberId="+depa+"",
			dataType:"script",
			success: function(data){
				var list = eval(data);
				 me1=$("#optionValue2").html();   //公司名称
				 me2=$("#optionValue3").html();    //部门名称
				 me3=$("#optionValue4").html();     //员工姓名
				 me4="身份证";    //证件类型
				 me5=list[0].certificateID;    //身份证号码
				 me6=list[0].mobilePhone;    //手机号码
				 messHtml = '<li><i>企业信息：</i><span>'+me1+'</span><span>'+me2+'</span></li><li><i>姓名：</i><span>'+me3+'</span></li><li><i>证件信息：</i><span>'+me4+'</span><span>'+me5+'</span></li><li><i>手机号码：</i><span>'+me6+'</span></li>';
			
				 $(".employeeMessBox").append(messHtml);
			}
			});
	});
	$(".com_tj").click(function(){
		
		$("#goname").val(me3);    //姓名
		//$("#optionValue1").html(me4);   //证件类型
		//$(".idNum").val(me5);   //证件号码
		$("#tel").val(me6);    //手机号码
		//$("#linkMobile").val(me6);//给手机号码赋值
		$(".companyEmployee").hide();
	});
	/*****************************************员工信息选择**********************************************/
	/**********************************************表单验证***************************************************/
	//$('input').val(null);   //清空所有表单内容    暂时 注释
	var pattern = null;
	$(".mobile").blur(function() {    //验证手机号码
	  if(this.value){
	    pattern = /^1[3|4|5|8][0-9]\d{8}$/;
	    	if (!pattern.test(this.value)) {
	    	      $(this).siblings(".ts").show().html('请填写正确的手机号码');
	    	      $(this).addClass("ban");
	    	    }else{
	    		  $(this).siblings(".ts").hide();
	    		  $(this).removeClass("ban");
	    	   }
	    }else{
	    	$(this).siblings(".ts").show().html('请填写手机号码');
		      $(this).addClass("ban");
	    }
	    
	});
	$(".mobileNum").blur(function() {
		$("#linkMobile").val($(this).val());
	})

	/**********************************************验证身份证号码***************************************************/
	$(".idNum").blur(function() {    //验证身份证号码
		var idCardNo=isIdCardNo(this.value);
		if(idCardNo==false){
			 $(this).addClass("ban");
		}else{
			 $(this).removeClass("ban");
			 $(this).siblings(".ts").hide();
		}
		});
	/**********************************************验证姓名***************************************************/
	$(".name").blur(function() {    //验证姓名
		var  name =this.value;
		if(name.length<1){
			 $(this).siblings(".ts").show().html('请输入乘客姓名');
			 $(this).addClass("ban");
		}else{
			$(this).siblings(".ts").hide();
			  $(this).removeClass("ban");
		}
		});
	/**********************************************判断是企业用户还是个人***************************************************/
	var usertype=$("#usertype").val();//1个人  2企业
	/*if(usertype=="2"){
			//获取企业用户名（登录名）
			$.ajax({
				type : "get",
				async : false,
				url : '${contextPath}/jp/getLoginName',
				success : function(data) {
					var data = eval("(" + data + ")");
					$(".c1 .loginName").html(data.loginName);
				}
			});
		}*/
		if(usertype=="1"){
			$(".companyLink").hide();
		}
	/**********************************************提交表单***************************************************/	
		$("#createOrder").click(function(){
			$(".idNum").blur();
			$(".mobile").blur();
			$(".name").blur();
	/*		$("#flightNo").val($("#flightCartFlightNo").val());
			$("#startDate").val($("#flightCartStartDate").val());
			$("#classCode").val($("#flightCartClassCode").val());*/
			//调用所有的验证方法
			var msg=true;
			$(".yanz").each(function(){
				var ban=$(this).hasClass("ban");
				//ban存在返回true
				if(ban==true){
					msg=false;
				}
			})
			if(msg==true){
				$("#tickerform").submit();
				$(".fly_load").show();
				$(".fly_load .loadbg").height($(window).height());
				$(".fly_load .loadIcon").css({"margin-top":($(window).height()-$(this).height())/2});
			}
			
		})
		
	});

	/**********************************************身份证验证规则***************************************************/
	function  isIdCardNo(num){
	   num = num.toUpperCase();  
	   //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。   
	   if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num))){ 
	          // alert('输入的身份证号长度不对，或者号码不符合规定'); 
	           $("#idcardv").show().html('输入的身份证号长度不对，或者号码不符合规定');
	           return false; 
	   } 
		 //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
		 //下面分别分析出生日期和校验位
		   var len, re; 
		   len = num.length; //身份证号的长度
		   if(len == 15){
			    re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
			    var arrSplit = num.match(re);
			    
			  //检查生日日期是否正确 
			    var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/' + arrSplit[4]); 
			    var bGoodDay; 
			    bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
			    if (!bGoodDay) 
			    {  
			    		 $("#idcardv").show().html('输入的身份证号里出生日期不对');
			               return false; 
			    }else{
			    	//将15位身份证转成18位 
			    	//校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
			    	           var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); 
			    	           var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'); 
			    	           var nTemp = 0, i;   
			    	           num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6); 
			    	           for(i = 0; i < 17; i ++){ 
			    	                nTemp += num.substr(i, 1) * arrInt[i]; 
			    	           } 
			    	           num += arrCh[nTemp % 11];   
			    	           return num; 
			    } 
		   }
		   
		   if(len == 18){
			   re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/); 
			   var arrSplit = num.match(re); 
			   
			 //检查生日日期是否正确 
			   var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/" + arrSplit[4]); 
			   var bGoodDay; 
			   bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
			   if (!bGoodDay){
				   $("#idcardv").show().html('输入的身份证号里出生日期不对');
				   return false; 
			   }else{
				 //检验18位身份证的校验码是否正确。 
				 //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
				   var valnum; 
				   var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); 
				   var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'); 
				   var nTemp = 0, i; 
				   for(i = 0; i < 17; i ++){ 
				   		nTemp += num.substr(i, 1) * arrInt[i]; 
				   } 
				   valnum = arrCh[nTemp % 11]; 
				   if (valnum != num.substr(17, 1)){ 
					   $("#idcardv").show().html('身份证号的末位应该为：' + valnum);
					   return false; 
				   } 
				   return num; 
			   }  
		   }
		   return false; 
	}	
	
 //景点通讯录
});

//提交表单
function reseve(){
	if(window.lock){
		alert("表单信息已经提交，请不要重复点击或刷新页面，等待服务器响应！");
		return false;
	}
	
	//整合总价
	var rmb = $("#oder_p_val").val();
	
	if(rmb==""){
		alert("您还没有选择游玩日期");
		return false;
	}else{
		$('#totalPrice').val(rmb*$('#selectNum').val());
	}
	
	
	if(check=='0'){
		alert('页面输入有错，请检查');
		return false;
	}
	
	
	$('#reseveForm').submit();
}	
