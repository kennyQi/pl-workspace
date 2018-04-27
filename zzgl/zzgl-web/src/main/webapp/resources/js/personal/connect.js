$(function(){
    //弹出框
    var documentWidth = $(window).width();    //屏幕可视区宽度
    var documentHeight = $(window).height();   //屏幕可视区高度

    $("[rel='dialog']").click(function(){

    	$("#contact .boxTitle").html('添加联系人<a href="javascript:;" style="background-position: 0px -800px;"></a>');
    	$("#contact .error").html("");
        var forDialog=$(this).attr("for")
            ,msgbox=$("."+forDialog+" .boxMessage")
            ,boxWidth=msgbox.width()
            ,boxHeight=msgbox.height()
            ;
        $("."+forDialog).fadeIn(300);
        msgbox.css({"margin-left":(documentWidth-boxWidth)/2});
        msgbox.css({"margin-top":(documentHeight-boxHeight)/2});
        
    	var form= $("#contact");
        
        form.find("input").each(function(i){
           $(this).val("");
        });
        form.find("input[name='cardType']").val("");
        $(".selectNav li[value='1']").click();
        
      /*  form.find("input[name='type']").val("1");
        $(".selectNav1 li[value='1']").click();*/
        
        
    });
    $(".boxTitle").on("click","a",function(){
        $(this).closest("[opt='box']").hide();
    });
    
   /* $(".selectNav1 li").click(function(){
    	$("input[name='type']").val($(this).attr("value"));
    });
    
    $(".selectNav li").click(function(){
    	$("input[name='cardType']").val($(this).attr("value"));
    });*/
    //添加联系人
    var pattern = /^1[3|4|5|7|8][0-9]\d{8}$/;
    var namepattern = /^[\u4E00-\u9FA5]{0,20}$/;
    
    $("#contact .yes").click(function(){
        var form= $("#contact");
        
        var flag=true;
        
        form.find("input").each(function(i){
            if($(this).val()==""&&$(this).attr("name")!="id"){
                $(this).next("span").html($(this).attr("for"));
                flag=false;
            }
            $(this).focus(function(){
                $(this).next("span").html("");
                flag=true;
            });
            
        });

        //姓名验证
          form.find("input[name='name']").each(function(){
          	if (!namepattern.test($(this).val())) {
         		 $(this).next("span").html('姓名不正确');
         		 flag=false;
  			}else{
  				 $(this).next("span").html("");
  			}
          });
         
      //手机验证
        form.find("input[name='mobile']").each(function(){
        	if (!pattern.test($(this).val())||$(this).val().length!=11) {
       		 $(this).next("span").html('手机号码有误');
       		 flag=false;
			}else{
				 $(this).next("span").html("");
			}
        });
       
        //身份证验证
        form.find("input[name='cardNo']").each(function(){
        	var idCardNo=isIdCardNo($(this).val(),$(this).next("span"));
    		if(idCardNo==false){
    			flag=false;
    		}else{
    			$(this).next("span").html("");
    		}
        });
        if(!flag)return;
        $.ajax({
     		cache: true,
     		type: "POST",
     		url:'../company/saveCommonContact',
     		data:$('#contact').serialize(),
     		async: false,
     	    error: function(request) {
     	        alert("连接失败");
     	    },
     	    success: function(data) {
     	    	var msg = eval("("+data+")");
     		    if(msg.status=="success"){
     		    	
     		    	location.reload();
     		    }else{
     		    	alert(msg.status);
     		    }
     	    }
     	});
    });
    
    //删除联系人
    $(".delContact").click(function(){

		var id=this.rel;
    	$(".alertBox").fadeIn(300).find(".userCoupan div").html("确定要删除该联系人吗？");
    	$("#sure").attr("rel",id);
    	/**/
    });
    //编辑联系人
    $(".editContact").click(function(){
    	var that=$(this);
    	
    	var option={
				cardType:that.closest("li").find(".position").attr("rel"),
				id:that.attr("forid"),
				name:that.closest("li").find(".name").html(),
				cardNo:that.closest("li").find(".id").html(),
				mobile:that.closest("li").find(".mobile").html()
    	}
    	$("#contact .boxTitle").html('编辑联系人<a href="javascript:;" style="background-position: 0px -800px;"></a>');
    	for(var x in option){
    		$("#contact input[name='"+x+"']").val(option[x]);
    	}
    	$(".selectNav li[value='"+option.cardType+"']").click();

    });
    
    
    //确认删除
   
	
	$("#cancel").click(function(){
		$(".alertBox").hide();
	});
	$("#sure").click(function(){
		var id=this.rel;
		$.ajax({
      		cache: true,
      		type: "POST",
      		url:'../company/deleteCommonContact?id='+id,
      		async: false,
      	    error: function(request) {
      	        alert("连接失败");
      	    },
      	    success: function(data) {
      	    	var msg = eval("("+data+")");
      		    if(msg.status=="success"){
      		   		
      		   	
      		    	location.reload();
      		    }else{
      		    	alert("删除失败");
      		    }
      	    }
      	});
	});
    
});

function  isIdCardNo(num,obj){
	num = num.toUpperCase();  
	//身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。   
	if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num))){ 
		// alert('输入的身份证号长度不对，或者号码不符合规定'); 
		obj.show().html('身份证有误！');
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
			obj.show().html('身份证有误！');
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
			obj.show().html('身份证有误！');
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
				obj.show().html('身份证有误！');
				return false; 
			} 
			return num; 
		}  
	}
	return false; 
}	