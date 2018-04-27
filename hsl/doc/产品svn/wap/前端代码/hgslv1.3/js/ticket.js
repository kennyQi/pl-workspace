/**
 * 
 * @authors Your Name (you@example.org)
 * @date    2014-08-10 23:42:03
 * @version $Id$
 */

$(function(){
	plus_minus();
	del();//清楚联系人信息
	tab();//tab切换 ticketSearch.html
	tabs();//tab切换 tickerDetails.html
	slides();//notes显示隐藏
	//initTime()//获取当天默认时间;
	dateTime();
	//slidesPic();//幻灯片
})


/*************ticketOrder.html******************/


function plus_minus(){
	$("#minus").click(function(){
		var value=$("#amount").val();
		if(value!=1){
			value--;
		}
		$("#amount").val(value);
	})

	$("#plus").click(function(){
		var value=$("#amount").val();
		value++;
		$("#amount").val(value);
	})
}

function del(){
	$(".del").click(function(){
		$(".people input").val("");
	})
}

/*************ticketSearch.html tab , tickerDetails.html tabs******************/

function tab(){
	$(".list_tour .tab li").each(function(){
		$(this).click(function(){
			$(".list_tour .tab li").removeClass("active");
			$(this).addClass("active");
		})
	})
}

function tabs(){
	$(".details .tabs li").each(function(){
		$(this).click(function(){
			var index=$(this).index();

			$(".details .tabs li").removeClass("active");
			$(this).addClass("active");

			if(index==0){
				$("#in").hide();
				$("#book").show();
			}else if(index=1){
				$("#in").show();
				$("#book").hide();
			}
		})
	})
}

/******************tickerDetails.html tab******************/


function  slides(){
	$(".notes:first h3").click(function(){
		if($(this).parent().hasClass("open")){
			$(this).parent().removeClass("open");
			$(this).siblings(".info_notes").slideUp();
			$(this).find("span").css("-webkit-transform","rotate(-90deg)");
		}else{
			$(this).parent().addClass("open");
			$(this).siblings(".info_notes").slideDown();
			$(this).find("span").css("-webkit-transform","rotate(90deg)");
		}
	})
}


function dateTime(){
    $("#tickerContent .select").click(function(){			
      $("#contWrap").hide();
      $("#calContainer").show();
    })
}

 //选着日期
function dateDay(){
	$("#calContainer .low_calendar tr").each(function(){
	  $(this).delegate("td","click",function(){
	    var date=$(this).attr("date");
	    $(".low_calendar tr td").removeClass("active");
      	$(this).addClass("active");//选中的日期
	    var index=$(this).index();
	    var price=$(this).find("span").html();
	    if (price != null && price != '') {
	    	price = parseInt(price.substring(1, price.length));
	    	$("#priceVal").html(price.toFixed(2));
	    }
	    $("#travelDate").val(date);
	    var arr=["星期日","星期一","星期二","星期三","星期四","星期五","星期六"]; 
	    var weekday=arr[index];
	    date=date+" &nbsp;"+weekday;
	    $(".select span").html(date);
	    $("#calContainer").hide();
	    $("#contWrap").show();
	  })
	})
}

//默认显示当天时间
function initTime(){
	var date=new Date();
	var arr=["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];
	var weekday=date.getDay();
	var year=date.getFullYear();
	var month=date.getMonth();
	var day=date.getDate();
	var init=year+"-"+(month+1)+"-"+day+" &nbsp;"+arr[weekday];
	$(".select span").html(init);
}

//轮播图
function  slidesPic(){
    $("#picShow").slidesjs({
    	play: {//播放设置
            auto: true,//设置为自动播放
			interval:3000//设置为每次轮番的间隔时间以毫秒为单位
       }
    });
    $(".slidesjs-navigation").remove();
    $(".slidesjs-pagination li a").html("");
    $(".slidesjs-pagination li").css({"display":"inline-block"});
    
}
