$(function(){
    var test=new Vcity.CitySelector({input:'citySelect_from'});
    var test1=new Vcity.CitySelector({input:'citySelect_to'});
    var from=$("#citySelect_from")
        ,to=$("#citySelect_to")
        ,time=$("#search_time")
        ,model_time=new Date()
        ,timeValue=ply.GetQueryString("time")||(model_time.getFullYear()+"-"+(model_time.getMonth()+1)+"-"+model_time.getDate())
        ,beforeDay=timeValue.split("-")[0]+"-"+timeValue.split("-")[1]+"-"+(parseInt(timeValue.split("-")[2])-1)
        ,afterDay=timeValue.split("-")[0]+"-"+timeValue.split("-")[1]+"-"+(parseInt(timeValue.split("-")[2])+1)
        ,clock=ply.GetQueryString("clock")||"起飞时间"
        ,company=ply.GetQueryString("company")||"航空公司"
        ;


    //获取url
    from.val(decodeURI(ply.GetQueryString("from")||""));
    to.val(decodeURI(ply.GetQueryString("to"))||"");
    time.attr("value",timeValue);
    $("#day_rel").attr("day",timeValue).attr("loadDay",timeValue);
    $(".m_html_sel").eq(0).find("label").html(decodeURI(clock)).attr("value",decodeURI(company));
    $(".m_html_sel").eq(1).find("label").html(decodeURI(company)).attr("value",decodeURI(company));

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
    var html='<div class="fly_box ov">';
    var temp='<div class="found"><span class="company pl">' +
        '<label class="fly_bg fl icon fly_3U"></label> ' +
        '<label class="i fl">东方航空<b>MU9107</b></label> ' +
        '<label class="c fl">机型：333</label> </span> ' +
        '<span class="time"> <label class="i">21:30</label> <label class="c">虹桥机场T2</label></span> ' +
        '<span class="cross"><label class="t fl">直飞</label>' +
        ' <label class="Bg icon fl"></label> ' +
        '<span class="tim pl fl"> <label class="z Bg pa"></label><label class="x">行程时间</label> <label class="ti">2小时15分钟</label> </span></span> ' +
        '<span class="time"> <label class="i">21:30</label>' +
        '<label class="c">虹桥机场T2</label> </span> ' +
        '<span class="other">机建+燃油<br/>￥50+￥0</span> ' +
        '<span class="price pl"> <label class="i"> <i>¥</i>&nbsp;830&nbsp;&nbsp;<e>起</e></label>' +
        '<label class="look_detail curp">查看详情</label> <i class="xiab Bg pa"></i> </span> ' +
        '</div>';
    html+='<ul class="ticket">';
    var tmp2='<li>' +
        ' <span class="type Bg"></span> ' +
        '<span class="name">经济舱</span> ' +
        '<span class="off">99折</span>' +
        '<span class="tips">退改签</span> ' +
        '<span class="price"><i>¥</i>1220</span> ' +
        '<a href="#" class="sub">预定</a> ' +
        '</li>';

    html+='<li> <span class="toggle">收起<i class="Bg"></i></span></li> </ul></div>';

    $("#m_list .fly_box").remove();
    $("#m_list").append(html);
}

