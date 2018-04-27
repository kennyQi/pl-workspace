/**
 * 
 * @authors Your Name (you@example.org)
 * @date    2014-08-05 09:30:42
 * @version $Id$
 */
$(function() {

	//交换城市
	chageCity();
	subswitch();//航班详情slide
//	tab();//tab
	cities();//城市选择
	dateTime();//出发日期选择
//	selectCanwei();//选择舱位
	addPass();//添加登机者
	windows();//弹窗
//	del()//删除登机者
	rili();
	dateDay();//选择日期
	initTime();//选择默认日期
	searchShow();
	//默认
	$(".list .f_detail").hide();
	$(".list:first .f_detail").show();

	//默认值
	/*$("#city1_tab").show();
	$("#city2_tab").hide();
	$($(".tab_title")[0]).addClass("active");*/

	$("#indexContent").show();

	/*$(".bankbox .link").click(function(){
	  alert(1)
	  $("#loading").show();
	  setTimeout(function(){
	    //window.location.href=""//支付宝链接
	  })
	})*/
});
var index = 0;

function cities() {
	var id="";
	$(".c_name").each(function() {
		$(this).focus(function() {
			id = $(this).attr("id");
			$("#indexContent").hide();
			$("#citiesPage").show();
			var val = $("#" + id + "_hid_search").val();
			showSearchCities(val);
			$("#search input").focus().val(val);
		});
	});
	/*$("#citiesPage dd").each(function(){
	  $(this).click(function(){
	    var city=$(this).html();
	    $("#"+id).val(city);
	    $("#"+id+"_hid").val($(this).attr("title"));
	    $("#indexContent").show();
	    $("#citiesPage").hide();
	  });
	});*/

	//默认城市列表
	$("#citiesPage dd").each(function() {
		$(this).click(function() {
			var city = $(this).html();
			var city_num = $(this).attr("cac");
			$("#" + id).val(city);
			$("#" + id + "_hid").val(city_num);
			$("#" + id + "_hid_search").val($("#search input").val());
			$("#indexContent").show();
			$("#citiesPage").hide();
		});
	});

	/*//搜索结果列表
	$(".search_result").delegate("dd","click",function(){
	  var city=$(this).html();
	  var city_num=$(this).attr("cac");
	  $("#"+id).val(city);
	  $("#"+id+"_hid").val(city_num);
	  $("#"+id+"_hid_search").val($("#search input").val());
	  $("#indexContent").show();
	  $("#citiesPage").hide();
	  $(".search_result").html("");
	  $("#search input").val("");
	  $("#city1_tab").show();
	});*/
}

function dateTime() {
	$("#date").click(function() {
		$("#indexContent").hide();
		$("#calContainer").show();
	});
}
//选着日期
function dateDay() {
	$("#calContainer .low_calendar tr").each(function() {
		$(this).delegate("td", "click", function() {
			$(".low_calendar tr td").removeClass("active");
			$(this).addClass("active");//选中的日期
			var date = $(this).attr("date");
			$("#traveldate").val(date);
			var index = $(this).index();
			var arr = [ "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" ];
			var weekday = arr[index];
			date = date + " &nbsp;" + weekday;
			$("#select_Date").html(date);
			$("#calContainer").hide();
			$("#indexContent").show();
		});
	});
}

function tab() {
	$(".tab_by li").each(function() {
		$(this).click(function() {
			var icon = $(this).find("em").html();

			$(".tab_by li").removeClass("hover");
			$(this).addClass("hover");
			if (icon == "↑") {
				$(this).find("em").html("↓");
			} else if (icon == "↓") {
				$(this).find("em").html("↑");
			}
		});
	});
}

function subswitch() {
	$(".js_subswitch").click(function() {
		if (index % 2 == 0) {
			$(this).removeClass("flight-packup");
			$(this).parents(".list").find(".f_detail").slideUp();
		} else {
			$(this).addClass("flight-packup");
			$(this).parents(".list").find(".f_detail").slideDown();
		}
		index++;
	});
}

/*function cityTab(){
  $(".tab_title").each(function(){
    $(this).click(function(){
      $(".tab_title").removeClass("active");
      $(this).addClass("active");
      if($(this).attr("id")=="city1"){
        $("#city1_tab").show();
        $("#city2_tab").hide();
      }else if($(this).attr("id")=="city2"){
        $("#city2_tab").show();
        $("#city1_tab").hide();
      }
    })
  })
}*/

function chageCity() {
	$('.pic_change').on('click', function() {
		var temp = $('#goCity').val();
		$('#goCity').val($('#backCity').val());
		$('#backCity').val(temp);
		var temp_hid = $('#goCity_hid').val();
		$('#goCity_hid').val($('#backCity_hid').val());
		$('#backCity_hid').val(temp_hid);
		var temp_hid_search = $('#goCity_hid_search').val();
		$('#goCity_hid_search').val($('#backCity_hid_search').val());
		$('#backCity_hid_search').val(temp_hid_search);
	});
}

function addPass() {
	var p = 1;
	$("#js_addPass_btn").click(function() {
		p++;
		var mo = $("#temp").html();
		mo = mo.replace("#p#", p);
		$("#add").before(mo);
	});
}

function windows() {
	$("#windows").css("height", $("#contentWrap").height());

	$(".hotline,.flight-rmk-btn,.banklist").click(function() {
		$("#windows").show();
		$("#windows").animate({
			top : 0
		}, 300);
		$(".fix").animate({
			bottom : 0
		}, 300);
	});

	$(".cancel").click(function() {
		$("#windows").animate({
			top : 1000
		}, 300);
		$(".fix").animate({
			bottom : -1000
		}, 300);
		$("#windows").hide();
	});

	windowHide();
}

function windowHide() {
	//防止误点
	$(".click").each(function() {
		$(this).click(function() {
			stopEvent();
			var id = $(this).attr("id");
			if (id == "windows") {
				$("#windows").hide();
			}
		});
	});
}

function stopEvent() { //阻止冒泡事件
	//取消事件冒泡 
	var e = arguments.callee.caller.arguments[0] || event; //若省略此句，下面的e改为event，IE运行可以，但是其他浏览器就不兼容
	if (e && e.stopPropagation) {
		// this code is for Mozilla and Opera
		e.stopPropagation();
	} else if (window.event) {
		// this code is for IE 
		window.event.cancelBubble = true;
	}
}

/*function del(){
  $(".personal").delegate(".del","click",function(){
    if($(".del").length==2){
      return true;
    }else{
      $(this).parent().remove();
    }
  })
}*/

//默认显示当天时间
function initTime() {
	var date = new Date();
	var weekday = date.getDay();
	var year = date.getFullYear();
	var month = date.getMonth();
	var day = date.getDate();

	var _month = month + 1;
	if (_month < 10) {
		_month = "0" + _month;
	}
	var _day = day;
	if (_day < 10) {
		_day = "0" + _day;
	}
	var arr = [ "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" ];
	var init = year + "-" + _month + "-" + _day;
	var traveldate = $("#traveldate").val();

	if (init != traveldate) {
		var index="";
		$("#calContainer .low_calendar tr").each(function() {
			$(this).find('td').each(function() {
				var date = $(this).attr("date");
				if (traveldate == date) {
					index = $(this).index();
				}
				;
			});
		});
		init = traveldate + " &nbsp; " + arr[index];
	} else {
		$("#calContainer .low_calendar tr").each(function() {
			$(this).find('td').each(function() {
				var date = $(this).attr("date");
				if (init == date) {
					$(this).addClass("active");
				}
				;
			});
		});
		init = init + " &nbsp; " + arr[weekday];
	}
	$("#select_Date").html(init);

}
//搜索
var fkutid, ftimer;
function searchShow() {
	/*$("#search input").bind("keyup",function(){
	showSearchCities($(this).val());
	});*/
	$("#search input").focus(function() {
		$this = $(this);
		if (!ftimer) {
			ftimer = true;
			fkutid = setInterval(function() {
				showSearchCities($this.val());
			}, 100);
		}
	}).blur(function() {
		clearInterval(fkutid);
		ftimer = false;
	});
}
function showSearchCities(val) {
	  if (val != null && val != '') {
		val = val.toUpperCase();
		$("#city1_tab dd").hide();
		$("#city1_tab dt").hide();
		var $list_1 = $("#city1_tab dd[cac*=" + val + "]");
		var $list_2 = $("#city1_tab dd[cjp*=" + val + "]");
		var $list_3 = $("#city1_tab dd[cqp*=" + val + "]");
		var $list_4 = $("#city1_tab dd[ccn*=" + val + "]");
		if (($list_1 == null || $list_1.length == 0)
				&& ($list_2 == null || $list_2.length == 0)
				&& ($list_3 == null || $list_3.length == 0)
				&& ($list_4 == null || $list_4.length == 0)) {
			$("#search_tip").show();
		} else {
			$("#search_tip").hide();
			$list_1.show();
			$list_2.show();
			$list_3.show();
			$list_4.show();
		}
	} else {
		$("#city1_tab dd").show();
		$("#search_tip").hide();
		$("#city1_tab dt").show();
	}
}