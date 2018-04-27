// 使用页面：company_cardCoupons.html
// 日期：2015、4/23
// 作者：lw
$(document).ready(function() {

	
	
var documentWidth = $(document).width();    //屏幕可视区宽度
var documentHeight = $(document).height();   //屏幕可视区高度 	

var mobile={                              //ajax获取数据
       method:"post",  
       url :"js/users.js", 
	   async:false,  
       dataType :"html", 
       success :function(data){
			var jsonData=eval(data);
			for( var i=0;i<jsonData.length;i++){
				if(jsonData[i].mobile == $(".mobileNum").val() && jsonData[i].state == 0 && jsonData[i].registration == 1){
				     var ts='<li><span>用户名：</span><i>'+jsonData[i].userName+'</i></li>';
		             $(".boxMobile").append(ts);
				}else if(jsonData[i].mobile == $(".mobileNum").val() && jsonData[i].registration == 0){
					 var ts='<li><span>用户名：</span><i>'+jsonData[i].userName+'</i></li><li><i style="color:#ff6600;">手机号码未注册</i></li>';
		             $(".boxMobile").append(ts);
					}else if(jsonData[i].mobile == $(".mobileNum").val() && jsonData[i].state == 1 && jsonData[i].registration == 1){
						var ts='<li><span>用户名：</span><i>'+jsonData[i].userName+'</i></li><li><i style="color:#ff6600;">该用户已领取该活动</i></li>';
		             $(".boxMobile").append(ts);
						}else if(jsonData[i].mobile != $(".mobileNum").val()){
							var ts='<li><i style="color:#ff6600;">查无此号码</i></li>';
		                    $(".boxMobile").append(ts);
							return;
							}
				}
		   }
    };





$(".seeMore").click(function(){    //点击弹出查看更多
		var index=$(".seeMore").index($(this));
		$(".more_float").remove();
		$(".boxMessage").remove();
		var top= 295+index*73+'px';
		var html='<div class="more_float" style="margin-top:'+top+'"><span class="float_title boxTitle floating_p yahei h3">使用规则<a href="javascript:;"></a></span><p>1.活动时间：2014年12月8日—12月12日。<br/> 2.本场红包适用于票量游PC端（www.ply365.com）及移动端（微信公众号：plyou365）购买机票。<br/> 3.红包使用期限：2014年12月8日—2015年12月31日。<br/>4.红包不能与其他优惠同时使用。 <br/>5.使用代金券预订的机票按照政策规定操作退票，收取退票费，退票时优先退回代金券，代金券不能用于支付退票费，若用户自付的费用小于本次退票的退票费则不予以退票。 <br/>6.本活动最终解释权归浙江票量云数据科技股份有限公司所有，如您有任何疑问，请致电热线：0571-23288385<br/> </p></div>';
		$(".more_box").append(html);
		});
	$(document).on("click",".float_title a",function(){    //关闭按钮
		 $(this).parents(".more_float").remove();
	});



$(document).on("click",".donation",function(){     //点击转赠弹出窗口
    $(".more_float").remove();
	$(".boxMessage").remove();
    var index=$(".donation").index($(this));
    var top1= 290+index*73+'px';
	var zzHTML='<div class="boxMessage" style="margin-top:'+top1+'"><div class="boxTitle floating_p">转赠<a href="javascript:;"></a></div><ul class="boxMobile"><li><span>手机号码：</span><input class="mobileNum input_p" type="text" name="" id="" maxlength="11"><a href="javascript:;">查询</a></li></ul><a class="yes" href="javascript:;">确 定</a></div>'
	$(".donationBox").append(zzHTML);
	$(".donationBox").css({"display":"block"});
	});
$(document).on("click",".boxTitle a",function(){
    $(".donationBox").css({"display":"none"});
	$(".boxMessage").remove();
	});

	
//验证手机

$(document).on("blur",".boxMobile input",function(){
	var patrn=/^1[3|4|5|8][0-9]\d{8}$/; 
	$(this).val($(this).val().replace(/ /g,""));
	if(!(patrn.test($(this).val()))){
		var ts='<li><i style="color:red;">请输入正确的手机号码</i></li>';
		$(".boxMobile").append(ts);
	}else if(patrn.test($(this).val())){
		$.ajax(mobile);
		$(".mobileNum").val($(".mobileNum").val().substring(0,3)+"****"+$(".mobileNum").val().substring(8,11));
		}
});
$(document).on("focus",".boxMobile input",function(){
		$(".boxMobile li:not(:eq(0))").remove();
});

});

