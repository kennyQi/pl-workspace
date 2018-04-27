$(function(){
	var test=new VcitySmall.CitySelector({input:'citySelect_from'});
    var test1=new VcitySmall.CitySelector({input:'citySelect_to'});
    var test2=new VcitySmall.CitySelector({input:'citys'});
    var citysTimes=0;
    $("#citys").click(function(){
    	if(citysTimes==0){
	    	$(".citySelector").each(function(i,e){
	    		if($(this).find("#cityBox").hasClass("hide")) return;
	    		$(this).css("left",parseInt($(this).css("left"))-217);
	    	});
	    	citysTimes++;
    	}
    	
    });
    
    var from=$("#citySelect_from")
        ,to=$("#citySelect_to")
        ,time=$("#search_time")
        ,model_time=new Date()
        ,timeValue=ply.GetQueryString("time")||(model_time.getFullYear()+"-"+timeGeshi(model_time.getMonth()+1)+"-"+timeGeshi(model_time.getDate()))
    //,timeValue=ply.GetQueryString("time")||("")
        ,beforeDay=timeValue.split("-")[0]+"-"+timeValue.split("-")[1]+"-"+((parseInt(timeValue.split("-")[2])-1)<10?"0"+(parseInt(timeValue.split("-")[2])-1):(parseInt(timeValue.split("-")[2])-1))
        ,afterDay=timeValue.split("-")[0]+"-"+timeValue.split("-")[1]+"-"+((parseInt(timeValue.split("-")[2])+1)<10?"0"+(parseInt(timeValue.split("-")[2])+1):(parseInt(timeValue.split("-")[2])+1))
        ,clock=ply.GetQueryString("clock")||"起飞时间"
        ,company=ply.GetQueryString("company")||"航空公司"
        ;
    

    //获取url
    from.val(decodeURI(ply.GetQueryString("from")||""));
    to.val(decodeURI(ply.GetQueryString("to"))||"");
    time.attr("value",timeValue);
    $("#day_rel").attr("day",timeValue).attr("loadDay",timeValue);
    $(".m_html_sel").eq(0).attr("value",decodeURI(clock)).find("label").html(decodeURI(clock));
    $(".m_html_sel").eq(1).attr("value",decodeURI(company)).find("label").html(decodeURI(company));

    //搜索
    $("#search").click(function(){
        if(from.val()==""||from.val()=="城市名"){
            from.css("border","1px solid red");
            setTimeout(function(){
                from.css("border","");
            },1500);
            return;
        }
        if(to.val()==""||to.val()=="城市名"){
            to.css("border","1px solid red");
            setTimeout(function(){
                to.css("border","");
            },1500);
            return;
        }
        if(time.val()==""){
            time.css("border","1px solid red");
            setTimeout(function(){
                time.css("border","");
            },1500);
            return;
        }else{
            var seacrTime=new Date().setFullYear(time.val().split("-")[0],time.val().split("-")[1]-1,time.val().split("-")[2]);
            if(seacrTime-new Date().getTime()<0){
                time.css("border","1px solid red");
                setTimeout(function(){
                    time.css("border","");
                },1500);
                return;
            }
        }
        submitSearch();
    });

    //日历部分点击
    $("#chooseDay li").click(function(){
        if($(this).attr("class")!="off"){
        time.val($(this).attr("rel"));
        $("#search").click();
        $("#chooseDay li").find("a").removeClass("on");
        $(this).find("a").addClass("on");
        }
    });
    $("#day_before").click(function(){
        if($("#day_rel").attr("rel")!="0"){
            var time=$("#day_rel").attr("day");
            creatDate("before",time);
        }
    });
    $("#day_after").click(function(){
        var time=$("#day_rel").attr("day");
         creatDate("after",time);

    });
    //初始化*********************
    (function(){
        $("#day_after").click();
        var loadTime=$("#day_rel").attr("loadDay").split("-");
        var nowDate=new Date();
        var newDate=new Date();
        var crossTime=Math.ceil(parseInt((newDate.setFullYear(loadTime[0],loadTime[1]-1,loadTime[2])-nowDate.getTime())/1000/3600/24)/7);
        $("#day_rel").attr("rel",crossTime);
    })();

    //条件下拉框*****************
    $(".m_html_sel").click(function(){
        $(this).find(".op").toggle();
        $(this).find(".icon").toggle();
    });
    $(".m_html_sel").mouseleave(function(){
        var that=$(this);
        setTimeout(function(){
            that.find(".op").hide();
            that.find(".icon").show();
        },200);
    });

    $(".m_html_sel .op span").click(function(){
        $(this).closest(".m_html_sel").attr("value", $(this).attr("value")).find("label").html($(this).html());
    	$("#search").click();
    });


    //机票详情查看*************
    $(document).on("click",".look_detail",function(){
        $(this).closest(".fly_box").find(".ticket").toggle().siblings().find(".xiab").toggle();
    });
    $(document).on("click",".ticket .toggle",function(){
        $(this).closest(".ticket").hide().closest(".fly_box").find(".xiab").hide();
    });

    //页面加载 提交ajax
    sendAjax();

    //底部翻页
    $("#nextDay").attr("value",afterDay).html("后一天航班("+afterDay.split("-")[1]+"-"+afterDay.split("-")[2]+")>>");
    $("#preDay").attr("value",beforeDay).html("<<前一天航班("+beforeDay.split("-")[1]+"-"+beforeDay.split("-")[2]+")");
    $("#nextDay").click(function(){
        time.attr("value",$(this).attr("value"));
        $("#search").click();
    });
    $("#preDay").click(function(){
        time.attr("value",$(this).attr("value"));
        $("#search").click();
    });

    //更换出发城市****************************
    $("#changeCity").click(function(){
        var v1=from.val();
        from.val(to.val());
        to.val(v1);
    });
    //热门城市搜索****************
    $(".search").click(function(){
    	if($("#citys").val()==""){
    		alert("请输入热门城市名称");
    	}else{
    		$("#searchMp").submit();
    	}
    });
    
  //机票时间选择

	 $("#search_time").click(function(){
		 $(".cityBox").addClass("hide");
	 });
});

//提交表单
function submitSearch(){
    var from=$("#citySelect_from").val(),
        to=$("#citySelect_to").val(),
        time=$("#search_time").val(),
        clock=$(".m_html_sel").eq(0).attr("value")||"",
        company=$(".m_html_sel").eq(1).attr("value")||""
        ;
    location.href=encodeURI("?from="+from+"&to="+to+"&time="+time+"&clock="+clock+"&company="+company);
}

//生成日历
function creatDate(type,time){

    var dayHidden=$("#day_rel");
    var dayCh=["周日","周一","周二","周三","周四","周五","周六"],Arr={};
    var nowTime=new Date();
    var newDay=time.split("-");
    nowTime.setFullYear(newDay[0], newDay[1]-1, newDay[2]);
    var year=nowTime.getFullYear(),month=nowTime.getMonth(),day=nowTime.getDate();
    if(type=="after"||type==null){
        for(var i=0;i<7;i++) {
            var b = new Date();
            b.setFullYear(year, month, day + i);
            Arr = {y: b.getFullYear(), m: (b.getMonth() + 1), d: b.getDate(), z: b.getDay()};
            $("#chooseDay li").eq(i).attr("rel",Arr.y+"-"+(Arr.m=Arr.m<10?"0"+Arr.m:Arr.m)+"-"+(Arr.d=Arr.d<10?"0"+Arr.d:Arr.d)).find("a").html(Arr.m+"-"+Arr.d+"&nbsp;"+dayCh[Arr.z]);
        }
        dayHidden.attr("day",Arr.y+"-"+Arr.m+"-"+(parseInt(Arr.d)+1)).attr("rel",parseInt(dayHidden.attr("rel"))+1);
    }else{
        for(var w=0;w<7;w++) {
            var t = new Date();
            var t2=new Date();
            t.setFullYear(year, month, day-w-8);
            Arr = {y: t.getFullYear(), m: (t.getMonth() + 1), d: t.getDate(), z: t.getDay()};
            $("#chooseDay li").eq(6-w).attr("rel",Arr.y+"-"+(Arr.m=Arr.m<10?"0"+Arr.m:Arr.m)+"-"+(Arr.d=Arr.d<10?"0"+Arr.d:Arr.d)).find("a").html(Arr.m+"-"+Arr.d+"&nbsp;"+dayCh[Arr.z]);
            if(t.getTime()<t2.getTime()){
                $("#chooseDay li").eq(6-w).addClass("off");
            }
        }
        dayHidden.attr("day",Arr.y+"-"+Arr.m+"-"+(parseInt(Arr.d)+7)).attr("rel",parseInt(dayHidden.attr("rel"))-1);
    }
    $("#chooseDay a").removeClass("on");
    $("#chooseDay").find("[rel='"+$("#day_rel").attr("loadDay")+"'] a").addClass("on");
}

//提交ajax
function sendAjax(){
	//var contextPath=$("#contextPath").val();
	var from=encodeURI(encodeURI($("#citySelect_from").val())),
	    to=encodeURI(encodeURI($("#citySelect_to").val())),
	    time=$("#search_time").val();
	var  clock=$(".m_html_sel").eq(0).attr("value")||"";
	var company=$(".m_html_sel").eq(1).attr("value")||"";
	if(clock!="全部"){
		clock=encodeURI(encodeURI($(".m_html_sel").eq(0).attr("value")))||"";
	}else{
		clock="";
	}
	if(company!="全部"){
		company=encodeURI(encodeURI($(".m_html_sel").eq(1).attr("value")))||"";
	}else{
		company="";
	}
	var url="../jp/ticketList?from="+from+"&to="+to+"&time="+time+"&clock="+clock+"&company="+company;

	if(from==""||to==""){
		$(".fly_none").show();
		return;
	}
	var temp='<div class="found"><span class="company pl">' +
    '<label class="fly_bg fl icon fly_{carrier}"></label> ' +
    '<label class="i fl">{airCompName}<b>{flightNo}</b></label> ' +
    '<label class="c fl">机型：{aircraftCode}</label> </span> ' +
    '<span class="time"> <label class="i">{startTime}</label> <label class="c">{startCity}{startTerminal}</label></span> ' +
    '<span class="cross"><label class="t fl">{stopNum}</label>' +
    ' <label class="Bg icon fl"></label> ' +
    '<span class="tim pl fl"> <label class="z Bg pa"></label><label class="x">行程时间</label> <label class="ti">{flyTime}</label> </span></span> ' +
    '<span class="time"> <label class="i">{endTime}</label>' +
    '<label class="c">{endCity}{endTerminal}</label> </span> ' +
    '<span class="other">机建+燃油<br/>￥{taxAmount}</span> ' +
    '<span class="price pl"> <label class="i"> <i>¥</i>&nbsp;{lowPrice}&nbsp;&nbsp;<e>起</e></label>' +
    '<label class="look_detail curp">查看详情</label> <i class="xiab Bg pa"></i> </span> ' +
    '</div>';
	 var temp2='<li>' +
     ' <span class="type Bg {flyCode}"></span> ' +
     '<span class="name">{flyCodeName}</span> ' +
     '<span class="off">{discount}折</span>' +
     '<span class="tips">退改签</span> ' +
     '<span class="price"><i>¥</i>{originalPrice}</span> ' +
     '<a href="../jp/price?flightNo={flightNo}&departDate={startDate}&classCode={classCode}" class="sub">预定</a> ' +
     '</li>';
	 $(".fly_load").show();
		$.ajax({
			//发送请求查询
			type:"post",
			url : ""+url+"",
			dataType : "json",
			success : function(data) {
				data=data.list;
				if(data==undefined||data.length<1){
					 $(".fly_none").show();
					 $(".fly_load").hide();
				}else{
				var dataHb,dataTick,resultHtml,resultHtmlTick,allHtml="",html="";
				if(data!=undefined){
					for(var i=0;i<data.length;i++){
						dataHb=data[i];
						html='<div class="fly_box ov">';
						//console.log(i);
						if(dataHb.cheapestFlightClass.originalPrice==undefined){
							html='<div class="fly_box ov" style="display:none;">';
						}
						dataHb.flyTime=dataHb.flyTime<=60?dataHb.flyTime:(Math.floor(dataHb.flyTime/60)+"小时"+dataHb.flyTime%60+"分钟");
						dataHb.lowPrice=dataHb.cheapestFlightClass.originalPrice;
						dataHb.stopNum=dataHb.stopNum==0?"直飞":("中转"+dataHb.stopNum+"次");
						resultHtml=ply.stringReplace_com(dataHb,temp);

						dataTick=data[i].flightClassList;
						for(var q=0;q<dataTick.length;q++){
							dataTick[q].flightNo=dataHb.flightNo;
							dataTick[q].startDate=dataHb.startDate;
							if(dataTick[q].discount<=100){
								dataTick[q].flyCode="jj";
								dataTick[q].flyCodeName="经济舱";
							}else if(dataTick[q].discount<200 && dataTick[q].discount>100){
								dataTick[q].flyCode="sw";
								dataTick[q].flyCodeName="商务舱";
							}else{
								dataTick[q].flyCode="td";
								dataTick[q].flyCodeName="头等舱";
							}
						}
						resultHtmlTick=ply.stringReplace_com(dataTick,temp2);
						html+=resultHtml+'<ul class="ticket">'+resultHtmlTick+'<li> <span class="toggle">收起<i class="Bg"></i></span></li> </ul></div>';
						allHtml+=html;
					}
					
					$("#m_list *").hide();
					$("#m_list .fly_box").remove();
					$(".fly_load").hide();
					$("#m_list").append(allHtml);
				}else{
					 $(".fly_none").show();
					 $(".fly_load").hide();
				}
			}/*,
			error:function(){
				alert("errot");
			}*/
			}
		});
}

function timeGeshi(num){
	return num<10?("0"+num):num;
}


