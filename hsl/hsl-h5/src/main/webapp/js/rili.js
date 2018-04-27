/*
 *
 * @authors Liuyz (you@example.org)
 * @date    2014-08-11 01:07:58
 * @version $Id$
 */

var monthAmount = 5; //选择显示的月份个数

function rili() {
	monthShow();
	dayShow();
	del();
	unclick();
}

//月份Show
function monthShow() {
	//获取系统时间
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth();

//	var index = 0;
//	var monthDays = 0;

	for (var i = 0; i < monthAmount; i++) {
		if (month > 11) {
			month = 0;
			year++;
		}
		$("#calContainer").append("<div class='low_calendar'></div>");
		var tag = "<div class='calGrid'><table><tbody><tr></tr></tbody></table></div>";
		$(".low_calendar:last").html(tag);
		$(".calGrid:last").before("<h1>" + year + "年" + (month + 1) + "月</h1>");
		weekName();
		month++;
	}

	for (var i = 0; i < 6; i++) {
		$(".low_calendar tbody").append("<tr></tr>");
	};
	for (var j = 0; j < 7; j++) {
		$(".low_calendar tbody tr").nextAll().append("<td></td>");
	}
	month++;
}


//周几show
function weekName() {
	var week_day = ["周日", "周一", "周二", "周三", "周四", "周五", "周六"];
	for (var i = 0; i < 7; i++) {
		if (i == 0 || i == 6) {
			$(".low_calendar:last tr:first").append("<th class='week_day'>" + week_day[i] + "</th>");
		} else {
			$(".low_calendar:last tr:first").append("<th>" + week_day[i] + "</th>");
		}
	};
}

//日期show
function dayShow() {
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth();
	var daysInMonth = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31); //每月天数；
	var day = date.getDate();
	//日期show

	for (var j = 0; j < monthAmount; j++) {

		//判断一年12个月
		if (month > 11) {
			month = 0;
			year++;
		}
		var firstDay = set(year, month);
		//每个月的日期做出数组
		var day_tds = $($(".low_calendar")[j]).find("tbody tr td").toArray();

		for (var i = 1; i < daysInMonth[month] + 1; i++) {
			var date_td = $(day_tds[firstDay + i - 1]); //当月1号位置
			date_td.html(i);
			var _month = month+1;
			if (_month < 10) {
				_month = "0" + _month;
			}
			var _i = i;
			if (_i < 10) {
				_i = "0" + _i;
			}
			date_td.attr("date",year+"-"+_month+"-"+_i);

			//过期变灰
			if (j == 0) {
				if (i < day) {
					date_td.addClass("desibled");
					$(".desibled").css({
						"background": "#d7d7d7",
						"color": "#fff"
					});
				}
			}

			//日期点击事件，过期不能点击
			date_td.click(function() {
				if ($(this).hasClass("desibled")) {
					return false;
				}
			});
			
			//价格日历
			//获取每个月的价格,循环加入span内

			//date_td.append("<span class='sb'>11</span>");

		}
		month++;

		//判断是否为闰年
		if (month == 1) {
			if (((0 == year % 4) && (0 != (year % 100))) || (0 == year % 400)) {
				daysInMonth[1] = 29;
			} else {
				daysInMonth[1] = 28;
			}
		}
	}
}

//获取月第一天
function set(year, month) {
	var d = new Date();
	d.setFullYear(year);
	d.setMonth(month);
	d.setDate(1);
	return d.getDay();
}

//删减多余行
function  del(){
	//多余行删减
	for(var i=0;i<$(".low_calendar").length;i++){
		var monthLast=$($(".low_calendar")[i]).find("tbody tr:last");
		var last=monthLast.find("td:first");
		if(last.html()==""){
			monthLast.remove();
		}
	}
}

function unclick(){
	for(var i=0;i<$(".low_calendar").length;i++){
		var month=$($(".low_calendar")[i]).find("tbody td");
		month.each(function(){
			$(this).click(function(){
				if($(this).html()==""){
					return false;
				}
			});
		});
	}
}