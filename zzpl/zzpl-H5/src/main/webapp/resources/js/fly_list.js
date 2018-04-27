require.config({
    //通用js模块路径
	baseUrl:request_path+"/resources/js/",
    //通用框架
    paths: {
        jquery: 'libs/jquery-2.1.4.min',
        moment: 'libs/moment.min'
    },

    waitSeconds: 0
});

//默认调用js
require(["jquery","moment"],
function($,moment) {
	$ticket = null;
	$flightJSON = null;
    $(function(){
        //loading
        $(".mainLoad").remove();
//        $("body").append('<div class="mainLoad"></div>');
        //loadingClosed
//        setTimeout(function(){
//            $(".mainLoad").remove();
//        },900);

        setTimeout(function() {
            if(window.pageYOffset !== 0) return;
            window.scrollTo(0, window.pageYOffset + 1);
        }, 100);
        var width=$(window).width;
        var dayTime=$(".dayTime");
        var nowDay=moment().format("YYYYMMDD");
        var dayCh=["今天","明天","后天"];
        var dayEn=["周日","周一","周二","周三","周四","周五","周六"];
        
      //展开筛选框
        $("nav .s").click(function(){
            $('.flight-filter-bg').show();
            $(".flight-filter").animate({bottom:'0px'},300);
        })
        //条目切换
        var $flightChooseMenusLi = $('.flight-chooseMenus li');
        var $itemsboxUl = $('.itemsbox ul');
        $flightChooseMenusLi.click(function(){
            var ind = $(this).index();
            $(this).addClass('current').siblings().removeClass('current');
            $itemsboxUl.eq(ind).show().siblings().hide();
        });
        // 选中状态切换
        var $flightTimeLi = $('.flightTime li');
        $flightTimeLi.click(function(){
            $(this).addClass('checked').siblings().removeClass('checked');
        });
        var $flightNameLi = $('.flightName li');
        $flightNameLi.click(function(){
            $(this).addClass('checked').siblings().removeClass('checked');
        });

        // 重置按钮
        var $flightResetBtn = $('.flight-reset-btn');
        $flightResetBtn.click(function(){
            $('.itemsbox').find('.checked').removeClass('checked');
            $('.itemsbox').find('.dot').addClass('checked');
        });
        // 取消按钮
        var $flightCancelBtn = $('.flight-cancel-btn');
        $flightCancelBtn.click(function(){
            $('.flight-filter-bg').hide();
            $('.flight-filter').animate({bottom:'-222px'},300);
        });
        
      //确认按钮
        var $flightOkBtn = $('.flight-ok-btn');
        $flightOkBtn.click(function(){
            $('.flight-filter-bg').hide();
            $('.flight-filter').animate({bottom:'-222px'},300);
            var time = $('.flightTime li.checked').attr("data");
            var company = $('.flightName li.checked').attr("data");
//            console.log(time + company);
            $("#startTime").attr("value",time);
            $("#airCompany").attr("value",company);
            $("#selectFlight").submit();
            var html="<div class='loading_bg'></div>";
        	$("body").append(html);
        	$("body").append('<div class="mainLoad"></div>');
        });

        
        //确认筛选条件
//        $("#filter .sure").click(function(){
//            var searchTime=$(".fliterList").eq(0).find("dt label").html();
//            var searchCompany=$(".fliterList").eq(1).find("dt label").html();
//            if(searchTime!="不限"||searchCompany!="不限"){
//                $(".double").addClass("on");
//            }else{
//                $(".double").removeClass("on");
//            }
//            $("#filter").css({
//                "z-index":"50"
//            }).animate({ "z-index":"580"}, {
//                duration:350,
//                step: function(now,fx) {
//                    $("#filter").css('-webkit-transform','translateX('+now+'px)');
//                }});
//            $("#selectFlight").submit();
//        });

        //选择筛选
        $(".fliterList dd").click(function(){
            $(this).closest(".fliterList").find("dd").removeClass("on");
            $(this).addClass("on").closest(".fliterList").find("dt label").html($(this).html());
            if($(this).attr("for")=="time"){
            	$("#startTime").attr("value",$(this).attr("data"))
            }else if($(this).attr("for")=="company"){
            	$("#airCompany").attr("value",$(this).attr("data"))
            }
        });

        //下拉和收起筛选框
//        $(".fliterList dt").click(function(){
//            $(this).nextAll().slideToggle();
//            $(this).find("label").toggleClass("down");
//        });

        //重置

//        $("#filter .reset").click(function(){
//            $(".fliterList").each(function(){
//                $(this).find("dd").eq(0).click();
//            })
//        });




        //展开机票
        $(".fly_box .found ").click(function(){
        	var flightID = $(this).closest(".fly_box").find("#flightID").val();
            $ticket = $(this).closest(".fly_box").find(".ticket");
            $flightJSON = $(this).closest(".fly_box").find(".flightJSON");
            $(this).find(".moreIcon").toggleClass("moreIconOn");
        	//首次打开
        	if($(this).data("ajaxTrue")!="true"){
        	   $(this).data("ajaxTrue","true");
	        	 //请求机票
	       		$(this).closest(".fly_box").find(".loadIngMsg").show();
	       		$(this).closest(".fly_box").find(".ticket").show();
	           	$.ajax({ 
	       			url : "../flight/getCabinList?flightID="+flightID, 
	       			type : "post", 
	       			cache : false, 
	       			dataType : "json", //返回json数据 
	       			error: function(){ 
	       			alert('error'); 
	       			}, 
	       			success:onchangecallback
	       		});
        	}else{
        		if(!$(this).find(".moreIcon").hasClass("moreIconOn")){
            		//收起
        			$(this).closest(".fly_box").find(".ticket").slideUp();
            	}else{
        			$(this).closest(".fly_box").find(".ticket").slideDown();
            	}
            	
        	}
        	
            
        });

        //切换时间*****************************************************
        //后一天
        $(".dayNext").click(function(){
        	var html="<div class='loading_bg'></div>";
        	$("body").append(html);
        	$("body").append('<div class="mainLoad"></div>');
            var time=dayTime.attr("data-time");
            var momentTime=moment(time).subtract(-1,"day");
            var momentDay=moment(momentTime).format("YYYYMMDD");
            var showDay="";
            if((momentDay-nowDay)<3&&(momentDay-nowDay)>=0){
                showDay=dayCh[(momentDay-nowDay)];
            }else{
                showDay=dayEn[momentTime.day()];
            }
            //动画
            dayTime.addClass("changeDayFlash").html(momentTime.format("YYYY-MM-DD")+" "+showDay).attr("data-time",momentTime.format("YYYY-MM-DD"));
            $("#startDate").attr("value",momentTime.format("YYYY-MM-DD"));
            $("#flyDay").attr("value",showDay);
            setTimeout(function(){
                dayTime.removeClass("changeDayFlash");
            },600);
            $("#selectFlight").submit();
            
        });
        //前一天
        $(".dayUp").click(function(){
        	
            var time=dayTime.attr("data-time");
            var today = new Date();
            var year = today.getFullYear();
            var month = today.getMonth() + 1;
            var strDate = today.getDate();
            if(month<10){
            	today = year+"0"+month+"";
            }else{
            	today = year+month+"";
            }
            if(strDate<10){
            	today = today+"0"+strDate+"";
            }else{
            	today = today+strDate+"";
            }
            var momentTime=moment(time).subtract(1,"day");
            var momentDay=moment(momentTime).format("YYYYMMDD");
            if(momentDay<today){
            	return false;
            }
            var html="<div class='loading_bg'></div>";
        	$("body").append(html);
        	$("body").append('<div class="mainLoad"></div>');
            var showDay="";
            if((momentDay-nowDay)<3&&(momentDay-nowDay)>=0){
                showDay=dayCh[(momentDay-nowDay)];
            }else{
                showDay=dayEn[momentTime.day()];
            }
            //动画
            dayTime.addClass("changeDayFlash").html(momentTime.format("YYYY-MM-DD")+" "+showDay).attr("data-time",momentTime.format("YYYY-MM-DD"));
            $("#startDate").attr("value",momentTime.format("YYYY-MM-DD"));
            $("#flyDay").attr("value",showDay);
            setTimeout(function(){
                dayTime.removeClass("changeDayFlash")
            },600);
            $("#selectFlight").submit();
        });

        //底部筛选
        $("nav .single").click(function(){
        	var html="<div class='loading_bg'></div>";
        	$("body").append(html);
        	$("body").append('<div class="mainLoad"></div>');
            var that=$(this);
            if(that.hasClass("on")){
                if(that.hasClass("p")){
                    //价格排序
                    if(that.find("label").hasClass("down")){
                        //进行升序
                        $("#orderBy").attr("value","2");
                        $("#orderType").attr("value","1");
                        $("#selectFlight").submit();
                        
                    }else{
                        $("#orderBy").attr("value","2");
                        $("#orderType").attr("value","2");
                        $("#selectFlight").submit();
                    }
                }
                if(that.hasClass("time")){
                    //起飞时间排序
                    if(that.find("label").hasClass("down")){
                        //进行升序
                        $("#orderBy").attr("value","1");
                        $("#orderType").attr("value","1");
                        $("#selectFlight").submit();
                    }else{
                        $("#orderBy").attr("value","1");
                        $("#orderType").attr("value","2");
                        $("#selectFlight").submit();
                    }
                }
                that.find("label").toggleClass("down");
            }else{
                $("nav .single").removeClass("on");
                that.addClass("on");
                if(that.hasClass("p")){
                    //价格排序
                    if(that.find("label").hasClass("down")){
                        $("#orderBy").attr("value","2");
                        $("#orderType").attr("value","2");
                        $("#selectFlight").submit();
                    }else{
                        //进行升序
                        $("#orderBy").attr("value","2");
                        $("#orderType").attr("value","1");
                        $("#selectFlight").submit();
                    }
                }
                if(that.hasClass("time")){
                    //起飞时间排序
                    if(that.find("label").hasClass("down")){
                        $("#orderBy").attr("value","1");
                        $("#orderType").attr("value","2");
                        $("#selectFlight").submit();
                    }else{
                        //进行升序
                        $("#orderBy").attr("value","1");
                        $("#orderType").attr("value","1");
                        $("#selectFlight").submit();

                    }
                }
            }

        });
        function onchangecallback(data){
        	var str = "";
        	var flightJSON = $flightJSON.attr("value")+"";
    		for(var i=0;i<data.length;i++){
        		var cabin = JSON.stringify(data[i])+"";
        		var discount = (data[i].cabinDiscount+0)/10;
        		if(data[i].cabinSales == "A"){
        			str += "<li>"+
                    "<span class='off'>"+discount+"折"+data[i].cabinName+data[i].cabinCode+"</span>"+
                    "<a onclick='order(this);' class='sub'flightJSON='"+flightJSON+"'cabinInfo='"+cabin+"'>订</a>" +
                    "<span class='num'>(充足)</span>"+
                    "<span class='price'>"+data[i].ibePrice+"</span>"+
                    "</li>";
        		}else{
        			str += "<li>"+
                    "<span class='off'>"+discount+"折"+data[i].cabinName+data[i].cabinCode+"</span>"+
                    "<a onclick='order(this);' class='sub'flightJSON='"+flightJSON+"'cabinInfo='"+cabin+"'>订</a>" +
                    "<span class='num'>("+data[i].cabinSales+"张)</span>"+
                    "<span class='price'>"+data[i].ibePrice+"</span>"+
                    "</li>";
        		}
        	}
        	$ticket.html(str);
        }

    });
    
    //查询舱位
    function selectCabin(flightID){
    	$.ajax({ 
			url : "../flight/getCabinList?flightID="+flightID, 
			type : "post", 
			cache : false, 
			dataType : "json", //返回json数据 
			error: function(){ 
			alert('error'); 
			}, 
			success:onchangecallback
		});
    	
		/*$.ajax({ 
			url : request_path+"/flight/getCabinList?flightID="+flightID, 
			type : "post", 
			cache : false, 
			dataType : "json", //返回json数据 
			error: function(){ 
			alert('error'); 
			}, 
			success:onchangecallback 
		});*/
    }
    
   
    
});
