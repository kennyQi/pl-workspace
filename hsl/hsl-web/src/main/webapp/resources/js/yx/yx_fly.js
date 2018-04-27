$(function(){


    sessionStorage.setItem("yxJpPayOrderToken","F");
    sessionStorage.setItem("yxOrderToken","F");
    //**********页面加载 提交ajax**********
    sendAjax();

    //**********点击城市,弹出城市列表**********
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

    //**********生成日期和设置出发城市,目的地城市,出发日期**********
    var orgCity=$("#citySelect_from")
        ,dstCity=$("#citySelect_to")
        ,startDate=$("#search_time")
        ,model_time=new Date()
        ,timeValue=ply.GetQueryString("startDate")||(model_time.getFullYear()+"-"+timeGeshi(model_time.getMonth()+1)+"-"+timeGeshi(model_time.getDate()))
        ,airCompany=ply.GetQueryString("airCompany")||"航空公司"
        ,startTime=ply.GetQueryString("flightTime")||"起飞时间"
    orgCity.val(decodeURI(ply.GetQueryString("orgCity")||""));
    dstCity.val(decodeURI(ply.GetQueryString("dstCity"))||"");

    var beforeDay = getBrforeOrNextDay(timeValue,-1);
    var afterDay = getBrforeOrNextDay(timeValue,1);
    startDate.attr("value",timeValue);
    $("#day_rel").attr("day",timeValue).attr("loadDay",timeValue);
    if(decodeURI(ply.GetQueryString("flightTime")) == "null"){
        startTime="起飞时间";
    }else if(decodeURI(ply.GetQueryString("flightTime")) == "上午(6:00-12:00)"){
        startTime="上午(6:00-12:00)";
    }else if(decodeURI(ply.GetQueryString("flightTime")) == "下午(12:00-18:00)"){
        startTime="下午(12:00-18:00)";
    }else if(decodeURI(ply.GetQueryString("flightTime")) == "晚上(18:00-24:00)"){
        startTime="晚上(18:00-24:00)";
    };
    $(".m_html_sel").eq(0).attr("value",decodeURI(startTime)).find("label").html(decodeURI(startTime));
    $(".m_html_sel").eq(1).attr("value",decodeURI(airCompany)).find("label").html(decodeURI(airCompany));

    //**********往前一天**********
    $("#day_before").click(function(){
        if($("#day_rel").attr("rel")!="0"){
            var time=$("#day_rel").attr("day");
            creatDate("before",time);
        }
    });
    //**********往后一天**********
    $("#day_after").click(function(){
        var time=$("#day_rel").attr("day");
        creatDate("after",time);

    });

    //****************热门城市搜索****************
    $(".search").click(function(){
        if($("#citys").val()==""){
            alert("请输入热门城市名称")
        }else{
            $("#searchMp").submit();
        }
    })

    //*********************提交表单*********************
    $("#search").click(function(){
        if(orgCity.val()==""||orgCity.val()=="城市名"){
            orgCity.css("border","1px solid red");
            setTimeout(function(){
                orgCity.css("border","");
            },1500);
            return;
        }
        if(dstCity.val()==""||dstCity.val()=="城市名"){
            dstCity.css("border","1px solid red");
            setTimeout(function(){
                dstCity.css("border","");
            },1500);
            return;
        }
        if(startDate.val()==""){
            startDate.css("border","1px solid red");
            setTimeout(function(){
                startDate.css("border","");
            },1500);
            return;
        }else{
            var seacrTime=new Date().setFullYear(startDate.val().split("-")[0],startDate.val().split("-")[1]-1,startDate.val().split("-")[2]);
            if(seacrTime-new Date().getTime()<0){
                startDate.css("border","1px solid red");
                setTimeout(function(){
                    startDate.css("border","");
                },1500);
                return;
            }
        }
        submitSearch();
    });

    //************判断能否预定1：如果能预定就跳转到填写订单页面,2：如果不能预定就提示错误原因
    $(document).on("click",".ticket .sub",function(e){

        var that = $(this);
        var encryptString = that.attr("encry");
        if(encryptString == "" ||encryptString == null){
            alert("暂时不能预定");
            return false;
        }

        alertWait();
        $.ajax({
            type:"post",
            url : "../yxjp/flight-book",
            data:{"encryptString":encodeURIComponent(encryptString)},
            success:function(data){
                var datas = eval('('+data+')');
                //预定成功,并且航班政策不为空,跳转到预定填写页面
                if((datas.message != null||datas.message != "")&&datas.yXFlightDTO!=null){
                    var form = that.closest("li").find("form");
                    var encryptStringInput = form.find("input[name='encryptString']");
                    var orderEncryptString = encryptStringInput.val();
                    encryptStringInput.val(encodeURIComponent(orderEncryptString));
                    form.submit();
                }else{
                    $(".fly_loadWait").hide();
                    alertShow();
                }
            }
        });
    });

    //*********************初始化*********************
    (function(){
        $("#day_after").click();
        var loadTime=$("#day_rel").attr("loadDay").split("-");
        var nowDate=new Date();
        var newDate=new Date();
        var crossTime=Math.ceil(parseInt((newDate.setFullYear(loadTime[0],loadTime[1]-1,loadTime[2])-nowDate.getTime())/1000/3600/24)/7);
        $("#day_rel").attr("rel",crossTime);
    })();

    //*****************条件下拉框*****************
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


    //*****************底部设置日期*****************
    $("#nextDay").attr("value",afterDay).html("后一天航班("+afterDay.split("-")[1]+"-"+afterDay.split("-")[2]+")>>");
    $("#preDay").attr("value",beforeDay).html("<<前一天航班("+beforeDay.split("-")[1]+"-"+beforeDay.split("-")[2]+")");
    $("#nextDay").click(function(){
        startDate.attr("value",$(this).attr("value"));
        $("#search").click();
    });
    $("#preDay").click(function(){
        startDate.attr("value",$(this).attr("value"));
        $("#search").click();
    });

    //*****************日历部分点击,搜索机票列表*****************
    $("#chooseDay li").click(function(){
        if($(this).attr("class")!="off"){
            startDate.val($(this).attr("rel"));
            $("#search").click();
            $("#chooseDay li").find("a").removeClass("on");
            $(this).find("a").addClass("on");
        }
    });

    //*****************出发城市和目的地城市互换*****************
    $("#changeCity").click(function(){

        //出发城市
        var orgCity=$("#citySelect_from").val();
        //目的地城市
        var dstCity=$("#citySelect_to").val();

        $("#citySelect_from").val(dstCity);
        $("#citySelect_to").val(orgCity);
    });

    //*************机票详情查看*************
    $(document).on("click",".look_detail",function(){
        $(this).closest(".fly_box").find(".ticket").toggle().siblings().find(".xiab").toggle();
    });

    //*************机票详情查看收起*************
    $(document).on("click",".ticket .toggle",function(){
        $(this).closest(".ticket").hide().closest(".fly_box").find(".xiab").hide();
    });

    //*************机票时间选择*************
    $("#search_time").click(function(){
        $(".cityBox").addClass("hide");
    });

})

//提交表单
function submitSearch(){

    var startDate=$("#search_time").val();
    var orgCity=$("#citySelect_from").val();
    var dstCity=$("#citySelect_to").val();
    var startTime=$(".m_html_sel").eq(0).attr("value");
    var airCompany=$(".m_html_sel").eq(1).attr("value");
    if(startTime == "起飞时间"){
        startTime=null;
    }
    location.href=encodeURI("?orgCity="+orgCity+"&airCompany="+airCompany+"&flightTime="+startTime+"&dstCity="+dstCity+"&startDate="+startDate);
}

//生成前一天(time:当前日期,type[-1:前一天,1:后一天])
function getBrforeOrNextDay(time,Type){
    var newDay=time.split("-");
    var nowTime=new Date();
    nowTime.setFullYear(newDay[0], newDay[1]-1, newDay[2]);
    var year=nowTime.getFullYear(),month=nowTime.getMonth(),day=nowTime.getDate();
    var b = new Date();
    b.setFullYear(year, month, day + Type);
    var time =b.getFullYear()+"-"+((b.getMonth() + 1)<10?"0"+(b.getMonth() + 1):(b.getMonth() + 1))+"-"+(b.getDate()<10?"0"+b.getDate():b.getDate());
    return time;

}
//*************生成日历*************
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


//***********页面加载完成,ajax请求查询机票列表***********
function sendAjax(){
    var orgCity=$("#citySelect_from").val();
    var dstCity=$("#citySelect_to").val();
    var startTime=decodeURI(ply.GetQueryString("flightTime"));
    var startDate=ply.GetQueryString("startDate")||"";
    var airCompany=decodeURI(ply.GetQueryString("airCompany"));
    if(airCompany=="全部"){
        airCompany="";
    }
    if(startTime == null ||startTime==""){
        startTime = null;
    }
    var url ="../yxjp/search-jp";

    if(orgCity==""||dstCity==""){
        $(".fly_none").show();
        return;
    }
    var jpurl='';
    var temp='<div class="found"><span class="company pl">' +
        '<label class="fly_bg fl icon fly_{airComp}"></label> ' +
        '<label class="i fl">{airCompanyName}<b>{flightno}</b></label> ' +
        '<label class="c fl">机型：{planeType}</label> </span> ' +
        '<span class="time"> <label class="i">{startTime}</label> <label class="c">{orgCityName}{departTerm}</label></span> ' +
        '<span class="cross"><label class="t fl">{stopNumber}</label>' +
        ' <label class="Bg icon fl"></label> ' +
        '<span class="tim pl fl"> <label class="z Bg pa"></label><label class="x">行程时间</label> <label class="ti">{flyTime}</label> </span></span> ' +
        '<span class="time"> <label class="i">{endTime}</label>' +
        '<label class="c">{dstCityName}{arrivalTerm}</label> </span> ' +
        '<span class="other">机建+燃油<br/>￥{buildFeeOrOilFee}</span> ' +
        '<span class="price pl"> <label class="i"> <i>¥</i>&nbsp;{minPic}&nbsp;&nbsp;<e>起</e></label>' +
        '<label class="look_detail curp">查看详情</label> <i class="xiab Bg pa"></i> </span> ' +
        '</div>';
    var temp2='<li>' +
        ' <span class="type Bg {flyCode}"></span> ' +
        '<span class="name">{cabinName}</span> ' +
        '<span class="off">{cabinDiscount}折</span>' +
        '<span class="tips">退改签</span> ' +
        '<span class="tipsUnder">{cabinCode}</span> ' +
        '<span class="price"><i>¥</i>{ibePrice}</span> ' +
        '<a ref="../jp/price" encry="{orderEncryptString}"'+
        'href="javascript:"'+
        '" class="sub">预定</a> ' +
        '<form action="../yxjp/flight-order" method="post" style="display:none;">'+
        '<input type="hidden" name="encryptString" value="{orderEncryptString}">'+
        '</form>'+
        '</li>';
    $(".fly_load").show();
    $.ajax({
        //发送请求查询
        url : ""+url+"",
        data:{"orgCity":orgCity,"dstCity":dstCity,"airCompany":airCompany,"startDate":startDate,"flightTime":startTime},
        type:"post",
        dataType : "json",
        success : function(data) {

            var buildFeeOrOilFee=data.buildFeeOrOilFee;
            var oilFee=data.oilFee;
            var buildFee=data.buildFee;
            if(buildFeeOrOilFee=="0"){
                buildFeeOrOilFee="50";
            }
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
                        dataHb.arrivalTerm=dataHb.arrivalTerm=="--"?"T1":dataHb.arrivalTerm;
                        dataHb.departTerm=dataHb.departTerm=="--"?"T1":dataHb.departTerm;
                        dataHb.buildFeeOrOilFee=buildFeeOrOilFee;
                        dataHb.flyTime=parseInt((parseInt(dataHb.endTime)-parseInt(dataHb.startTime))/1000/60);
                        dataHb.flyTime=dataHb.flyTime<=60?dataHb.flyTime:(Math.floor(dataHb.flyTime/60)+"小时"+dataHb.flyTime%60+"分钟");
                        dataHb.stopNumbers=dataHb.stopNumber;
                        dataHb.stopNumber=dataHb.stopNumber==0?"直飞":("中转"+dataHb.stopNumber+"次");
                        var cabList = dataHb.cabinList;
                        dataHb.minPic=cabList[cabList.length-1].ibePrice;
                        var startTime1=new Date(),endTime=new Date();
                        startTime1.setTime(dataHb.startTime);
                        endTime.setTime(dataHb.endTime);
                        dataHb.startTime=timeGeshi(startTime1.getHours())+":"+timeGeshi(startTime1.getMinutes())
                        dataHb.endTime=timeGeshi(endTime.getHours())+":"+timeGeshi(endTime.getMinutes());
                        resultHtml=ply.stringReplace_com(dataHb,temp);
                        dataTick=data[i].cabinList;
                        for(var q=0;q<dataTick.length;q++){
                            dataTick[q].flightNo=dataHb.flightNo;
                            dataTick[q].startDate=dataHb.startDate;
                            if(dataTick[q].cabinName=="经济舱"){
                                dataTick[q].flyCode="jj";
                            }else if(dataTick[q].cabinName=="商务舱"){
                                dataTick[q].flyCode="sw";
                            }else{
                                dataTick[q].flyCode="td";
                            }
                            //把航班信息赋值给 机票信息
                            dataTick[q].flightno=dataHb.flightno;
                            dataTick[q].airComp=dataHb.airComp;
                            dataTick[q].airCompName=dataHb.airCompName;
                            dataTick[q].planeType=dataHb.planeType;
                            dataTick[q].startTime=dataHb.startTime;
                            dataTick[q].departTerm=dataHb.departTerm;
                            dataTick[q].endTime=dataHb.endTime;
                            dataTick[q].arrivalTerm=dataHb.arrivalTerm;
                            dataTick[q].buildFeeOrOilFee=parseFloat(dataHb.buildFeeOrOilFee)+"";
                            dataTick[q].buildFee=parseFloat(buildFee)+"";
                            dataTick[q].oilFee=parseFloat(oilFee)+"";
                            dataTick[q].airCompanyName=dataHb.airCompanyName;
                            dataTick[q].totalPrice=parseFloat(buildFeeOrOilFee)+parseFloat(dataTick[q].ibePrice);
                            dataTick[q].cabinCode=dataTick[q].cabinCode;
                            dataTick[q].stopNumbers=dataHb.stopNumbers;
                            //中文转码
                            dataTick[q].cabinNamecode=dataTick[q].cabinName;
                            dataTick[q].orgCityName=dataHb.orgCityName;
                            dataTick[q].stopNumber=dataHb.stopNumber;
                            dataTick[q].flyTime=dataHb.flyTime;
                            dataTick[q].dstCityName=dataHb.dstCityName;
                            dataTick[q].startDate=getTime(new Date(startTime));
                            dataTick[q].endDate=getTime(new Date(endTime));
                            dataTick[q].orderEncryptString=dataTick[q].orderEncryptString;
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
            }
        }
    });
}

function timeGeshi(num){
    return num<10?("0"+num):num;
}

//弹出层
function alertShow(){
    $(".fly_loadError").show();
    $(".fly_loadError .loadbg").height($(window).height());
    $(".fly_loadError .loadIcon").css({"margin-top":($(window).height()-$(".fly_loadError .loadIcon").height())/2});
}
//关闭弹出层
$(".fly_loadError").click(function(){
    $(this).hide();
})


//弹出层
function alertWait(){
    $(".fly_loadWait").show();
    $(".fly_loadWait .loadbg").height($(window).height());
    $(".fly_loadWait .loadIcon").css({"margin-top":($(window).height()-$(".fly_loadError .loadIcon").height())/2});
}

/**********************************************时间格式化***************************************************/
function getTime(date) {
    var now = "";
    now = date.getFullYear() + "-"; //读英文就行了
    var month = (date.getMonth() + 1).toString();
    if (month.length == 1) {
        month = "0" + month;
    }
    now = now + month + "-";//取月的时候取的是当前月-1如果想取当前月+1就可以了
    var d = date.getDate().toString();
    if (d.length == 1) {
        d = "0" + d;
    }
    now = now + d + " ";
    var hour = date.getHours().toString();
    if (hour.length == 1) {
        hour = "0" + hour;
    }
    return now;
}