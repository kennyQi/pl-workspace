Date.prototype.format = function(fmt) { // author: meizz
		var o = {
			"M+" : this.getMonth() + 1, // 月份
			"d+" : this.getDate(), // 日
			"h+" : this.getHours(), // 小时
			"m+" : this.getMinutes(), // 分
			"s+" : this.getSeconds(), // 秒
			"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
			"S" : this.getMilliseconds()
		// 毫秒
		};
		if (/(y+)/.test(fmt))
			fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
					.substr(4 - RegExp.$1.length));
		for ( var k in o)
			if (new RegExp("(" + k + ")").test(fmt))
				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
						: (("00" + o[k]).substr(("" + o[k]).length)));
		return fmt;
	};

function getCurrentDate(){
	return new Date(); 	
}


//上周
function getLastWeek(){
	 
    //获取当前时间  
    var currentDate=this.getCurrentDate(); 
    //返回date是一周中的某一天  
    var week=currentDate.getDay(); 
    //返回date是一个月中的某一天  
    var month=currentDate.getDate(); 
    //一天的毫秒数  
    var millisecond=1000*60*60*24; 
    //减去的天数  
    var minusDay=week!=0?week-1:6; 
    //获得当前周的第一天  
    var currentWeekDayOne=new Date(currentDate.getTime()-(millisecond*minusDay)); 
    //上周最后一天即本周开始的前一天  
    var priorWeekLastDay=new Date(currentWeekDayOne.getTime()-millisecond); 
    //上周的第一天  
    var priorWeekFirstDay=new Date(priorWeekLastDay.getTime()-(millisecond*6)); 
    
    var priorWeekFirstDayStr = priorWeekFirstDay.format("yyyy-MM-dd");
    var priorWeekLastDayStr = priorWeekLastDay.format("yyyy-MM-dd");
   
    $("#saleBeginTimeStr", navTab.getCurrentPanel()).attr("value", priorWeekFirstDayStr);
    $("#saleEndTimeStr", navTab.getCurrentPanel()).attr("value", priorWeekLastDayStr);
    
    $("#pagerForm", navTab.getCurrentPanel()).submit();
}


//获取本周
function getCurrentWeek(){
	
    //获取当前时间  
    var currentDate=this.getCurrentDate(); 
    //返回date是一周中的某一天  
    var week=currentDate.getDay(); 
    //返回date是一个月中的某一天  
    var month=currentDate.getDate(); 
    //一天的毫秒数  
    var millisecond=1000*60*60*24; 
    //减去的天数  
    var minusDay=week!=0?week-1:6; 
    //alert(minusDay);  
    //本周 周一  
    var monday=new Date(currentDate.getTime()-(minusDay*millisecond)); 
    //本周 周日  
    var sunday=new Date(monday.getTime()+(6*millisecond)); 
    
    var mondayStr = monday.format("yyyy-MM-dd");
    var sundayStr = sunday.format("yyyy-MM-dd");
    
    $("#saleBeginTimeStr", navTab.getCurrentPanel()).attr("value", mondayStr);
    $("#saleEndTimeStr", navTab.getCurrentPanel()).attr("value", sundayStr);
    $("#pagerForm", navTab.getCurrentPanel()).submit();
}


//获取本月
function getCurrentMonth(){
	
    //获取当前时间  
    var currentDate=this.getCurrentDate(); 
    //获得当前月份0-11  
    var currentMonth=currentDate.getMonth(); 
    //获得当前年份4位年  
    var currentYear=currentDate.getFullYear(); 
    //求出本月第一天  
    var firstDay=new Date(currentYear,currentMonth,1); 
    //当为12月的时候年份需要加1  
    //月份需要更新为0 也就是下一年的第一个月  
    if(currentMonth==11){ 
        currentYear++; 
        currentMonth=0;//就为  
    }else{ 
        //否则只是月份增加,以便求的下一月的第一天  
        currentMonth++; 
    } 
    //一天的毫秒数  
    var millisecond=1000*60*60*24; 
    //下月的第一天  
    var nextMonthDayOne=new Date(currentYear,currentMonth,1); 
    //求出上月的最后一天  
    var lastDay=new Date(nextMonthDayOne.getTime()-millisecond); 
    
    
    var firstDayStr = firstDay.format("yyyy-MM-dd");
    var lastDayStr = lastDay.format("yyyy-MM-dd");
    
    $("#saleBeginTimeStr", navTab.getCurrentPanel()).attr("value", firstDayStr);
    $("#saleEndTimeStr", navTab.getCurrentPanel()).attr("value", lastDayStr);
    $("#pagerForm", navTab.getCurrentPanel()).submit();
    
}


function getMonthDays(year,month){
	//本月第一天 1-31  
    var relativeDate=new Date(year,month,1); 
    //获得当前月份0-11  
    var relativeMonth=relativeDate.getMonth(); 
    //获得当前年份4位年  
    var relativeYear=relativeDate.getFullYear(); 
    //当为12月的时候年份需要加1  
    //月份需要更新为0 也就是下一年的第一个月  
    if(relativeMonth==11){ 
        relativeYear++; 
        relativeMonth=0; 
    }else{ 
        //否则只是月份增加,以便求的下一月的第一天  
        relativeMonth++; 
    } 
    //一天的毫秒数  
    var millisecond=1000*60*60*24; 
    //下月的第一天  
    var nextMonthDayOne=new Date(relativeYear,relativeMonth,1); 
    //返回得到上月的最后一天,也就是本月总天数  
    return new Date(nextMonthDayOne.getTime()-millisecond).getDate(); 
}


function getPriorMonthFirstDay(year,month){ 
    //年份为0代表,是本年的第一月,所以不能减  
    if(month==0){ 
        month=11;//月份为上年的最后月份  
        year--;//年份减1  
        return new Date(year,month,1); 
    } 
    //否则,只减去月份  
    month--; 
    return new Date(year,month,1);; 
}


//上月
function getPreviousMonth(){
    //获取当前时间  
    var currentDate=this.getCurrentDate(); 
    //获得当前月份0-11  
    var currentMonth=currentDate.getMonth(); 
    //获得当前年份4位年  
    var currentYear=currentDate.getFullYear(); 
    //获得上一个月的第一天  
    var priorMonthFirstDay=this.getPriorMonthFirstDay(currentYear,currentMonth); 
    //获得上一月的最后一天  
    var priorMonthLastDay=new Date(priorMonthFirstDay.getFullYear(),priorMonthFirstDay.getMonth(),this.getMonthDays(priorMonthFirstDay.getFullYear(), priorMonthFirstDay.getMonth())); 
    
    var priorMonthFirstDayStr = priorMonthFirstDay.format("yyyy-MM-dd");
    var priorMonthLastDayStr = priorMonthLastDay.format("yyyy-MM-dd");
    
    $("#saleBeginTimeStr", navTab.getCurrentPanel()).attr("value", priorMonthFirstDayStr);
    $("#saleEndTimeStr", navTab.getCurrentPanel()).attr("value", priorMonthLastDayStr);
    $("#pagerForm", navTab.getCurrentPanel()).submit();
}


//昨天
function getYesteday(){
	//获取当前时间  
    var currentDate=this.getCurrentDate(); 
    //一天的毫秒数  
    var millisecond=1000*60*60*24; 
    var yesteday = new Date(currentDate.getTime() - millisecond);
    
    var yestedayStr = yesteday.format("yyyy-MM-dd");
    $("#saleBeginTimeStr", navTab.getCurrentPanel()).attr("value", yestedayStr);
    $("#saleEndTimeStr", navTab.getCurrentPanel()).attr("value", yestedayStr);
    $("#pagerForm", navTab.getCurrentPanel()).submit();
}


//今天
function getToday($tab){
	var currentDate=this.getCurrentDate(); 
	var currentDateStr = currentDate.format("yyyy-MM-dd");
	$("#saleBeginTimeStr", navTab.getCurrentPanel()).attr("value", currentDateStr);
	$("#saleEndTimeStr", navTab.getCurrentPanel()).attr("value", currentDateStr);
	$("#pagerForm", navTab.getCurrentPanel()).submit();
}



