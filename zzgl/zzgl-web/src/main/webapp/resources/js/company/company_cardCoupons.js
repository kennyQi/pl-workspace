//使用页面：company_cardCoupons.html
//日期：2015、4/23
//作者：lw
$(document).ready(function() {



	var documentWidth = $(document).width();    //屏幕可视区宽度
	var documentHeight = $(document).height();   //屏幕可视区高度 	

	var mobile={                              //ajax获取数据
			method:"post",  
			url :"../../js/company/js/users.js", 
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





	$("[seeMore='seeMore']").click(function(){    //点击弹出查看更多
		
		var index=$("[seeMore='seeMore']").index($(this));
		$(".more_float").remove();
		$(".boxMessage").remove();
		var top= 295+index*73+'px';
		var couponid=$(this).attr("id");
		//
		$.ajax({
			type: "POST",
			url: "../couponcontroller/checkRule",
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			dataType:"json",
			data:"couponid="+couponid,
			success: function(data) {
				/*if(data.length>400){
					data=data.substring(0,400)+"......";
				}*/
				var html='<div class="more_float" style="margin-top:'+top+'"><span class="float_title boxTitle floating_p yahei h3">使用规则<a href="javascript:;"></a></span><div class="userCoupan">'+data+'</div></div>';	
				$(".more_box").append(html);
			}
		})

	});
	$(document).on("click",".float_title a",function(){    //关闭按钮
		$(this).parents(".more_float").remove();
	});



	$(document).on("click",".donation",function(){     //点击转赠弹出窗口
		$(".more_float").remove();
		$(".boxMessage").remove();
		//判断session是否过期
		$(".initiativeuserid").val($(this).attr("name"));
		$.ajax({
			type: "POST",
			url: "../couponcontroller/checkingsession",
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			dataType:"json",
			success: function(data) {
				if(data.session=="yes"){
					// $(".msgs").show(); 

					var index=$(".donation").index($(this));
					var top1= 290+index*73+'px';
					var zzHTML='<div class="boxMessage" style="margin-top:'+top1+'"><div class="boxTitle floating_p">转赠<a href="javascript:;"></a></div><ul class="boxMobile"><li><span>手机号码：</span><input class="mobileNum input_p" type="text" name="" id="" maxlength="11"><a href="javascript:;" id="search">查询</a></li></ul><a class="yes" href="javascript:;">确 定</a></div>'
					$(".donationBox").append(zzHTML);
					$(".donationBox").css({"display":"block"});

				}else{
					document.location.href = "../user/login";
				}

			}
		});

	});
	$(document).on("click",".boxTitle a",function(){
		$(".donationBox").css({"display":"none"});
		$(".boxMessage").remove();
	});


//	验证手机

	$(document).on("click","#search",function(){
		$(".boxMobile #re").remove();
		var patrn=/^1[3|4|5|7|8][0-9]\d{8}$/; 
		$(".boxMobile :input").val($(".boxMobile :input").val().replace(/ /g,""));
		if(!(patrn.test($(".boxMobile :input").val()))){
			var ts='<li id="re"><i style="color:red;">请输入正确的手机号码</i></li>';
			$(".boxMobile").append(ts);
		}else if(patrn.test($(".boxMobile :input").val())){
			var mobile = $(".mobileNum").val();
			$.ajax({
				type: "POST",
				url: "../company/mobilequeryname?mobile="+mobile+"",
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				dataType:"json",
				success: function(data) {
					
					var name=data.name;
					$("#name").remove();
					if(name!=="所查询的用户不存在"){
						
						if(name.length==4){
							name="**"+name.substring(2,4);
						}else{
							name="**"+name.substring(2,name.length-2)+"**" ;
						}
					}
					$(".boxMobile").append('<li id="name"  style="color:red;">'+name+'</li>');
					$(".passivityuserid").val(data.passivityuserid);
				}
			});

			//$(".mobileNum").val($(".mobileNum").val().substring(0,3)+"****"+$(".mobileNum").val().substring(8,11));暂时屏蔽
		}
	});
//	点击确定
	$(document).on("click",".yes",function(){

		$(".boxMobile #re").remove();
		if($(".mobileNum").val()==""){
			var ts='<li id="re"><i style="color:red;">请输入手机号码</i></li>';
			$(".boxMobile").append(ts);
		}else{
			var boxMobile=$("#name").html();
			//得到所持当前劵的用户id
			var initiativeuserid=$(".initiativeuserid").val();
			//得到被转人的id
			var passivityuserid=$(".passivityuserid").val();
			if(passivityuserid!=""&&boxMobile!=undefined){
				$.ajax({
					type: "POST",
					url: "../couponcontroller/updateholdcoupon?initiativeuserid="+initiativeuserid+"&passivityuserid="+passivityuserid+"",
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
					dataType:"json",
					success: function(data) {
						$(".phone").val("");
						$(".usename").html("");
						$(".meorr").html("");
						//刷新页面
						location.reload();
					}
				})	
			}else{
				var ts='<li id="re"><i style="color:red;">请点击查询校验</i></li>';
				$(".boxMobile").append(ts);
			}
		}
	});


	$(document).on("focus",".boxMobile input",function(){
		$(".boxMobile li:not(:eq(0))").remove();
	});

});

