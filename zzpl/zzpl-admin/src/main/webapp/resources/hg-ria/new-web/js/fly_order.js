var memberHtml="";
var leaveFn=function(event){
	 if (event) {
	        event.returnValue = "关闭窗口会清空页面！";
	    }
	    return '关闭窗口会清空页面！';
};
$(function(){
	memberHtml=$("#temp").html();
	var pattern = /^1[3|4|5|7|8][0-9]\d{8}$/;
	var nameReg=/[\u4E00-\u9FA5]/i;
   
	 $("#submitAndPay").click(function(){
		 ply.loading();
	 });
   
    $("#add_connect").click(function(){
        if($("#company").val()==0){
            $("#company_select").css({
                "border-color":"#ed524f",
                "background-color":"#fdeded"
            });
            return;
        }
        ply.alertBox("form");
    });

    //添加非本公司的人
    $("#add_member").click(function(){
        if($("#company").val()==0){
            $("#company_select").css({
                "border-color":"#ed524f",
                "background-color":"#fdeded"
            });
            return;
        }
        if($(".order_member").find(".member_box").length<5){
        	/*if($(".order_member").find(".member_box").length<2){
        		memberHtml=$(".order_member").html();
        	}*/
    		$(".order_member").append(memberHtml);
        }else{
            ply.alertBox("warm","最多添加五个乘客","fail");
        }
    });

    $("#company_select").click(function(){
        $(this).removeAttr("style");
    });

    //删除
    $(".order_member").on("click"," .member_del",function(){
    	var that=$(this);
    	var length = $(".order_member").find(".member_box").length;
    	ply.alertBox("confirm","确认删除乘机人？");
    	
    	$(".a-btn-sure").click(function(){
    		that.closest(".member_box").remove();
    		$("#alert_form input[rel='"+that.attr("rel")+"']").attr("checked",false);
        	ply.alertBoxClose("confirm");
    	})
    	/*if(length == 1){
    		ply.alertBox("warm","最少一名旅客出行","fail");
            return;
    	}else{*/
    		
    	/*}*/

    });

    //通讯录选择
    $("#alert_form").on("change",".plist li input",function(e){
        if($(this).prop("checked")){
            //选中
            var length = $(".order_member").find(".member_box").length;
            var personList = {};

            if (length >= 5) {
            	ply.alertBoxClose("form");
                ply.alertBox("warm","最多添加五个乘客","fail");
                $(this).prop("checked",false);
                return;
            }
            personList.rel=$(this).attr("rel");
            personList.passengerName = $(this).closest("li").find(".name").html();
            personList.telephone = $(this).closest("li").find(".mobile").html();
            personList.idNO = $(this).closest("li").find(".cardid").html();
            personList.userID = $(this).closest("li").find(".id").html();
            
            /*if($(".order_member").find(".member_box").length<2){
        		memberHtml=$(".order_member").html();
        	}*/
    		$(".order_member").append(memberHtml);
    			if($("#first_member_div input[name='passengerName']").val()==""){
//    				$("#first_member_div").html("");
    				$("#first_member_div").css("display","none");
    			}
                for (var x in personList) {
                    $(".order_member .member_box").eq(length).find("input[name='" + x + "']").attr("value", personList[x]).attr("readonly", "readonly");
                }
                //赋值联系人
                $(".order_member .member_box").eq(length).find("input[name='linkName']").attr("value", personList.passengerName);
                $(".order_member .member_box").eq(length).find("input[name='linkTelephone']").attr("value", personList.telephone);
                $(".order_member .member_box").eq(length).find(".member_del").attr("rel", personList.rel);
                $(".order_member .member_box").eq(length).find(".member_del").attr("data", personList.id);
            $(".order_member .member_box").eq(length).find(".input-select").unbind("click");
        }else{
            //取消选中
        	var length = $(".order_member").find(".member_box").length;
        	$(".member_del[rel='"+ $(this).attr("rel")+"']").closest(".member_box").remove();
        	if(length == 2){
        		$("#first_member_div").css("display","block");
        	}
            
        }
    });
    
    
    //表单验证
    $(".btn_order").click(function(){
    	if($("#company").val()==0){
            $("#company_select").css({
                "border-color":"#ed524f",
                "background-color":"#fdeded"
            });
            return;
        }
    	if($(".order_member .member_box").length<1){
            ply.alertBox("warm","请添加乘机人！","fail");
            return;
        }
    	var flag=true;
    	$(".order_member input").each(function(){
    		if(flag){
                if($(this).val()==""){
                    msg=$(this).attr("for")+"不能为空！";
                    $(this).closest(".member_box").find(".formWarn").html(msg);
                    flag=false;
                }
            }
    	});
    	
    	$(".order_member [name='idNO']").each(function(){
    		if(flag){
                if(!isIdCardNo($(this).val())){
                    msg=$(this).attr("for")+"不对！";
                    $(this).closest(".member_box").find(".formWarn").html(msg);
                    flag=false;
                }
            }
    	});
    	$(".order_member [name='passengerName'],.order_member [name='linkName']").each(function(){
    		if(flag){
                if(!nameReg.test($(this).val())){
                    msg=$(this).attr("for")+"不对！";
                    $(this).closest(".member_box").find(".formWarn").html(msg);
                    flag=false;
                }
            }
    	});

    	$(".order_member [name='telephone'],.order_member [name='linkTelephone']").each(function(){
    		if(flag){
                if(!pattern.test($(this).val())){
                    msg=$(this).attr("for")+"不对！";
                    $(this).closest(".member_box").find(".formWarn").html(msg);
                    flag=false;
                }
            }
    	});
    	
    	/*if($(".order_member [name='costCenterIDs']").val()=="0"){
    		$(this).closest(".member_box").find("请选择成本中心");
    		 flag=false;
    	}*/
    	
    	$(".order_member [name='costCenterIDs']").each(function(){
    		if(flag){
                if($(this).val() =="0" ){
                    msg="请选择成本中心";
                    $(this).closest(".member_box").find(".formWarn").html(msg);
                    flag=false;
                }
            }
    	});
    	
    	var length = $(".order_member").find(".member_box").length;
    	if(length == 0){
    		 flag=false;
    	}
    	
    	if(!flag){
    		return flag;
    	}else{
    		$(".formWarn").html("");
    	}
    	
    	leaveFn=function(){};
    	$("#order").submit();
    	ply.loading();
    }); 
    
    
    //公司切换
    /*$(document).on("click",".a-btn-sure",function(){
    	if($(this).attr("rel")=="delete") return;
    	
    });*/
    
    $(document).on("click","#cancelCompany",function(){
    	var data=$(".a-btn-sure").attr("data");
    	if(data){
    		$("li[data='"+data+"']").click();
        	$(".a-btn-sure").attr("data","");
    	}
    	ply.alertBoxClose('confirm');
    	
    	
    });
    
    //乘机人删除
    
});
function companyChange(){
	$(".order_member").html("");
	var data=$(".a-btn-sure").attr("data");
	ply.alertBoxClose("confirm");
	selectUser();
	selectCost();
}

/*function removePerson(){
	$(".order_member").html("");
	var data=$(".a-btn-sure").attr("data");
	ply.alertBoxClose("confirm");
	selectUser();
	selectCost();
}*/
window.onbeforeunload = function (event) {
	
    var event = event || window.event;
    leaveFn(event);
   
};



/**********************************************身份证验证规则***************************************************/
function  isIdCardNo(num,obj){
	num = num.toUpperCase();  
	//身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。   
	if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num))){ 
		// alert('输入的身份证号长度不对，或者号码不符合规定'); 
		//obj.show().html('输入的身份证号长度不对，或者号码不符合规定');
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
			//obj.show().html('输入的身份证号里出生日期不对');
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
			//obj.show().html('输入的身份证号里出生日期不对');
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
				//obj.show().html('身份证号的末位应该为：' + valnum);
				return false; 
			} 
			return num; 
		}  
	}
	return false; 
}	



function selectUser(){
	var comanyID = $("#company").val();
	if(comanyID=="" || comanyID=="0"){
		$(".plist").html("");
	}else{
		$.ajax({ 
			url : "../CLGLYBookFlight/selectUsers?companyID="+comanyID, 
			type : "post", 
			cache : false, 
			dataType : "json", //返回json数据 
			error: function(){ 
			alert('error'); 
			}, 
			success:onchangecallback 
		});
	}
}
function onchangecallback(data){ 
	$(".plist").html("");
	var str=""; 
	for(var i=0;i<data.length;i++){ 
		str+="<li><label>"+"<span class='id' style='display: none;'>"+data[i].id+"</span>"+
            "<input type='checkbox' rel='"+(i+1)+"' data-type='"+(i+1)+"'>"+
        "<div class='plist-tb'>"+
            "<span class='name'>"+data[i].name+"</span>"+
            "<span class='sex'>男</span>"+
            "<span class='cardid'>"+data[i].idCardNO+"</span>"+
            "<span class='mobile'>"+data[i].linkMobile+"</span>"+
        "</div> </label></li>" 
	} 
	$(".plist").html(str); 
	
}
function selectCost(){
	var comanyID = $("#company").val();
	if(comanyID=="" || comanyID=="0"){
		$("#costCentList").html(" <li data='0'>请选择成本中心</li>");
	}else{
		$.ajax({ 
			url : "../CLGLYBookFlight/selectCost?companyID="+comanyID, 
			type : "post", 
			cache : false, 
			dataType : "json", //返回json数据 
			error: function(){ 
			alert('error'); 
			}, 
			success:onchangeCostCentList
		});
	}
}
function onchangeCostCentList(data){ 
	$("#costCentList").html(" <li data='0'>请选择成本中心</li>");
	var str="<li data='0'>请选择成本中心</li>"; 
	for(var i=0;i<data.length;i++){ 
		str+="<li data='"+data[i].id+"'>"+data[i].costCenterName+"</li>" 
	} 
	$("#costCentList").html(str); 
	memberHtml=$("#temp").html();
	
}