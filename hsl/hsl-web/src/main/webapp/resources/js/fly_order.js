/*$(function(){
	$("#hotel_img .picR .pic").hover(function(){
		$(this).parent().find(".textTm").show();
	},function(){
		$(this).parent().find(".textTm").hide();
	});
	//图片浮动
	ply.imgFloat($("#hotel_img .pic"));
	
	//中部导航
	var subNavhei=$("#m_subNav").height();
	var $subNavA=$("#m_subNav .nav a");
	var oldsubNavAOn=0;
	$subNavA.each(function(i){
		$(this).click(function(){
			var id=$(this).attr("rel")
			,hei=$("#"+id).offset().top
			;
			$(window).scrollTop(hei-subNavhei);
			
			$subNavA.removeClass("on").eq(i).addClass("on");
			$("#subNav_bg").stop().animate({left:i*113},300);
			oldsubNavAOn=i;
		});
		
		$(this).hover(function(){
				$subNavA.removeClass("on");
				$("#subNav_bg").stop().animate({left:i*113},200);
				$subNavA.eq(i).addClass("on");
		},function(){
				$subNavA.removeClass("on");
				$("#subNav_bg").stop().animate({left:oldsubNavAOn*113},200);
					$subNavA.eq(oldsubNavAOn).addClass("on");
		});
	});
	
	
	//中部导航浏览记录跟随
	var subNavTop=$("#m_subNav").offset().top;
	$(window).scroll(function(){
		//回顶部按钮
		if($(window).scrollTop()>subNavTop){
			//$("#m_subNav").css({"top":0,"position":"fixed","background":"#f4f4f4"});
            $("#m_subNav").css({"top":0,"position":"fixed"});
			//回顶部按钮
			$(".g_totop").show();
		}else{
			$("#m_subNav").css({"top":0,"position":"absolute","background":"#fff"});
			//回顶部按钮
			$(".g_totop").hide();
		}
		for(var i=0,l=$subNavA.length;i<l;i++){
				var id=$subNavA.eq(i).attr("rel")
					,hei=$("#"+id).offset().top
					;
				if($(window).scrollTop()>=(hei-subNavhei)){
					$subNavA.removeClass("on");
					$("#subNav_bg").stop().animate({left:i*113},200);
					$subNavA.eq(i).addClass("on");
					oldsubNavAOn=i;
				}else{
					break;
				}
			}
	});
	
	
	//查看房型具体信息
	$(".tr .de").click(function(){
		if($(this).attr("class").indexOf("de_on")!=-1){
			$(this).removeClass("de_on").closest(".tr").find(".tr_de").hide();
		}else{
			$(this).addClass("de_on").closest(".tr").find(".tr_de").show();
		}
	});
	
	//查看房型鼠标滑动效果
	$(".tr .hs_word").hover(function(){
		$(this).addClass("hs_word_on");
	},function(){
		$(this).removeClass("hs_word_on");});
		
	//弹出相册
	$("#hotel_img .pic").click(function(){
        var rel=$(this).attr("rel");
		if($("#m_albums").length>0){
			$("#m_albums").show().find(".img_list_Img li:eq("+rel+")").trigger("click");
		}else{
            //相册数据ajax获取到albumShowList
            var albumShowList=[{title:"第一张标题",src:"images/jd_img01.jpg"},{title:"第二张标题",src:"images/index_img1.jpg"},{title:"第三张标题",src:"images/index_img2.jpg"},
                {title:"第四张标题",src:"images/index_img3.png"},{title:"第一张标题",src:"images/index_img4.png"
                },{title:"第一张标题",src:"images/index_img1.jpg"},{title:"第一张标题",src:"images/index_img2.jpg"},
                {title:"第一张标题",src:"images/index_img3.png"},{title:"第一张标题",src:"images/index_img4.png"},
                {title:"第一张标题",src:"images/index_img1.jpg"},{title:"第一张标题",src:"images/index_img2.jpg"},
                {title:"第一张标题",src:"images/index_img3.png"},{title:"第一张标题",src:"images/index_img4.png"}];
            ply.albumShow(albumShowList,rel);
		}
	});
	/*if($(window).width()>1200){
		*//*var g_histTop=$(".g_hist").offset().top;
		var g_histLeft=$(".g_hist").offset().left;
		var g_histAb_Right=parseInt($(".g_hist").css("right"));
		var footTop=$(".gd_foot").offset().top;*//**//*
		$(window).scroll(function(){
			//回顶部按钮
			if($(window).scrollTop()>(footTop-parseInt($(".g_hist").height()))){
				$(".g_hist").css({"top":0,left:"","right":g_histAb_Right,"position":"absolute"});
			}else if($(window).scrollTop()>g_histTop){
				$(".g_hist").css({"position":"fixed","left":g_histLeft,"top":"0"});
				//回顶部按钮
				$(".g_totop").show();
			}else{
				$(".g_hist").css({"top":0,left:"","right":g_histAb_Right,"position":"absolute"});
				//回顶部按钮
				$(".g_totop").hide();
			}
		});
		//关闭浏览记录
		$("#closeHist").click(function(){
			$(this).closest(".g_hist").hide();
		});*//*
        //浏览记录跟随
        var g_histTop=$(".g_hist").offset().top;
        var g_histLeft=$(".g_hist").offset().left;
        var g_histAb_Right=parseInt($(".g_hist").css("right"));
        var footTop=$(".gd_foot").offset().top;
        var g_hist_Width=$(".g_hist").width();
        var winWidth=$("body").width();
        var m_listWidth=$(".m_list").width();
        //设置定位

        if(((winWidth-m_listWidth)/2-g_hist_Width)>15){
            g_histAb_Right=(winWidth-m_listWidth)/2-g_hist_Width-15;
            $(".g_hist").css({"right":g_histAb_Right});
        }

        $(window).resize(function(){

            var winWidth=$("body").width();
            var m_listWidth=$(".m_list").width();
            if(((winWidth-m_listWidth)/2-g_hist_Width)>15){
                g_histAb_Right=(winWidth-m_listWidth)/2-g_hist_Width-15;
                $(".g_hist").css({"right":g_histAb_Right});
            }
        });

        $(window).scroll(function(){
            //回顶部按钮
            if($(window).scrollTop()>(footTop-parseInt($(".g_hist").height()))){
                $(".g_hist").css({"top":20,left:"","position":"absolute"});
            }else if($(window).scrollTop()>g_histTop){
                //$(".g_hist").css("top",$(window).scrollTop()-g_histTop);
                //if(!($(".g_hist").is(":animated"))){
                //$(".g_hist").animate({"top":$(window).scrollTop()-g_histTop},300);
                //}
                $(".g_hist").css({"position":"fixed","top":"0"});
                //回顶部按钮
                $(".g_totop").show();
            }else{
                $(".g_hist").css({"top":20,"position":"absolute"});
                //回顶部按钮
                $(".g_totop").hide();
            }
        });
	}else{
		var g_hist=$(".g_hist");
		var g_histTop=g_hist.offset().top;
		var footTop=$(".gd_foot").offset().top;
		g_hist.addClass("g_hist_bottom").removeClass("g_hist");
		g_hist.find(".close").attr({"a":"#ffffff",b:"#6b6b6a"});
		$(window).scroll(function(){
			//回顶部按钮
			if($(window).scrollTop()>footTop){
				g_hist.css("height",0);
			}else if($(window).scrollTop()>g_histTop){
				g_hist.css("height",208);
				//回顶部按钮
				$(".g_totop").show();
			}else{
				g_hist.css("height",0);
				//回顶部按钮
				$(".g_totop").hide();
			}
		});
		//关闭浏览记录
		$("#closeHist").click(function(){
			g_hist.hide();
		});
	}*/
	
/*	//底部叉叉
	$("#needHistory #closeHist").hover(function(){
		$(this).css({"border":"1px solid #d4d4d4"});
	},function(){
		$(this).css({"border":"0px"});
	});*/
	
	

/*});*/



//liwei 
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
       url :""+contextPath+"/jp/companys",
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
		  url :""+contextPath+"/jp/getDepartments?companyId="+coms+"",
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
		url :""+contextPath+"/jp/getMembers?deptId="+pers+"",
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
		  url :""+contextPath+"/jp/getMembersInfomation?memberId="+depa+"",
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
	
	$(".name").val(me3);    //姓名
	$("#optionValue1").html(me4);   //证件类型
	$(".idNum").val(me5);   //证件号码
	$(".mobileNum").val(me6);    //手机号码
	$("#linkMobile").val(me6);//给手机号码赋值
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

