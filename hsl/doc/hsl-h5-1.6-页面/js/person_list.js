
//游玩人数标识不能做为人数计算
var personRel=0;
$(function(){
    var state=GetQueryString("state");
    if(state==1){

    }
    //证件类型
    var personArr=["","身份证","军官证","护照"];

    //添加游玩人
    $("#addPerson").click(function(){
       location.href="order_addperson.html";
    });
    $("#addPersonSure").click(function() {
        var name = $("#person_name").val().trim()
            , type = $("#person_select").find("option:checked").val()
            , num = $("#person_num").val().trim()
            , mobile = $("#person_mobile").val().trim()
            , rel = $(this).attr("rel")
            ,mobileReg=/1[3|4|5|7|8][0-9]/i
        ;
        if (name == "") {
            showMsg("请填写姓名");
            return;
        }
        if (type == 0) {
            showMsg("请选择证件类型");
            return;
        }
        if (num == ""||!IdentityCodeValid(num)) {
            showMsg("请填写正确证件号");
            return;
        }
        if (mobile == ""||mobile.length!=11||!mobileReg.test(mobile)) {
            showMsg("请填写正确手机号!");
            return;
        }
        alert("发送ajax");
        if(Math.round(Math.random())){
            //提交成功
            console.log("成功！跳转页面！");
        }else{
            showMsg("提交失败！!");
        }

       /* if (rel == "" || rel == undefined ||rel==null) {
            //验证身份证是否重复
            var numflag=true;
            for (var q = 0, l = $(".cardid").length; q < l; q++) {
                if ($(".cardid").html().indexOf(num) != -1) {
                    numflag=false;
                }
            }
            if(!numflag){
                showMsg("证件号已使用!");
                return numflag;
            }
            //新增
            var html = '<li class="ui-border-t box" rel="' + personRel + '" choose="true">' +
                '<label class="choose"></label>' +
                '<h4 class="ui-nowrap person">' +
                '<span class="di">' + name + '</span>' +
                '<label class="di">' +
                '<i>手机号</i>：<i class="num mobile">'+mobile+'</i>' +
                '</label>' +
                '<label class="di"><i class="type" rel="' + type + '">' + personArr[type] + '</i>：<i class="num cardid">' + num + '</i></label>' +
                '</h4>' +
                '</li>';
            personRel++;
            $("#personList").append(html);
        }else{
            //编辑
            $("#personList [rel='"+rel+"']").find("span").html(name).siblings()
                .find("i.mobile").html(mobile).closest("li")
                .find("i.type").attr("rel",type).html(personArr[type]).closest("li")
                .find("i.cardid").html(num);
        }

        $("#person_name").val("");
        $("#person_select").find("option").eq(0).prop("selected", "selected");
        $("#person_num").val("");
        $(this).removeAttr("rel");
        $(".content").hide();
        setTimeout(function(){
            $("#main,.fixedBtn").show();
        },380);

        //计算人数
        count();*/
    });

    //返回
    $("#cancel").click(function(){
        $(".content").hide();
        setTimeout(function(){
            $("#main,.fixedBtn").show();
        },380);
    });

    //下一步
    $(".next-btn").click(function(){
        var data=[];
        var list=$(".ui-border-t[choose='true']");
        for(var i= 0,l=list.length;i<l;i++){
            data[i]={};
            data[i].name=list.eq(i).find("span").html();
            data[i].mobile=list.eq(i).find(".mobile").html();
            data[i].type=list.eq(i).find(".type").attr("rel");
            data[i].cardid=list.eq(i).find(".cardid").html();
        }
        if(data.length>0){
            console.log(data);
            alert("结构已输出console");
        }
    });

    //滑动
    $("#personList li").each(function(i,obj){
        var startX= 0,left= 0,endX= 0,nowX=0;
        //if(obj.target=="")
        obj.addEventListener('touchstart', function(event) {
            event.preventDefault();
            if (event.targetTouches.length == 1&&!(event.target.className.indexOf("editor")!=-1)) {
                var touch = event.targetTouches[0];
                endX=startX=touch.pageX;
                left=parseInt($(obj).attr("left"));
            }
        }, false);
        obj.addEventListener('touchmove', function(event) {
            event.preventDefault();
            if (event.targetTouches.length == 1&&!(event.target.className.indexOf("delete")!=-1)) {
                var touch = event.targetTouches[0];
                endX=touch.pageX;
                if(left==0){
                    if(touch.pageX-startX<-75){
                        $(obj).css({
                            "transform":"translateX(-75px)",
                            "-webkit-transform":"translateX(-75px)"
                        });
                    }else if(touch.pageX-startX<0){
                        $(obj).css({
                            "transform":"translateX("+(touch.pageX-startX)+"px)",
                            "-webkit-transform":"translateX("+(touch.pageX-startX)+"px)"
                        });
                    }
                }
            }
        }, false);
        obj.addEventListener('touchend', function(event) {
            event.preventDefault();
            if(!(event.target.className.indexOf("delete")!=-1)){
                if(event.target.className.indexOf("editor")!=-1){
                    location.href=$(event.target).attr("href");
                    return;
                }
                if(endX-startX<0){
                    $(obj).animate({"transform":"translateX(-75px)","-webkit-transform":"translateX(-75px)"},50);
                    $(obj).attr("left",1);
                }else{
                    $(obj).animate({"transform":"translateX(0px)","-webkit-transform":"translateX(0px)"},50);
                    $(obj).attr("left",0);
                }
                if(parseInt(endX-startX)<5){
                    $(obj).click();
                }
            }
        }, false);
    });
    //var ua=navigator.userAgent;
});

//获取参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)return r[2];
    return "";
}
//勾选游玩人
$(document).on("click","#personList .ui-border-t",function(){
    //showConfirm("确定删除游玩人？",deletePerson,$(this));
    if($(this).find(".choose").hasClass("on")){
        $(this).attr("choose",false).find(".choose").removeClass("on");
    }else{
        $(this).attr("choose",true).find(".choose").addClass("on");
    }
    count();
});


function showMsg(msg){
    var h=$(window).height()
        ,w=$(window).width()
        ;
    var html='<div class="g_msg"><span>'+msg+'</span></div>';
    $("body").append(html);
    var msg_w=parseInt($(".g_msg").css("width")),msg_h=parseInt($(".g_msg").css("height"));
    $(".g_msg").css({"top":(h-msg_h)/2,"left":(w-msg_w)/2})
    $(".g_msg").show(150);
    setTimeout(function(){
            $(".g_msg").remove();
    },2000);
}

//弹出确认框
function showConfirm(msg,fn,obj){
    var h=$(window).height()
        ,w=$(window).width()
        ;
    var html='<div class="g_confirm">'+
        '<div class="bg"></div>'+
        '<div class="confirm_box">'+
        '<h3 class="msg">'+msg+'</h3>'+
        '<div class="btn">'+
        '<span class="yes">是</span>'+
        '<span class="no">否</span>'+
        '</div>'+
        '</div>'+
        '</div>';
    $("body").append(html);
    $(".g_confirm .bg").height(h);
    var msg_h=parseInt($(".g_confirm").height());
    $(".g_confirm").css({"margin-top":(h-msg_h)/2});
    $(".g_confirm").show(150);
    $(".g_confirm .no").click(function(){$(".g_confirm").remove();});
    $(".g_confirm .yes").click(function(){
        fn(obj);
        $(".g_confirm").remove();
    });
}

function deletePerson(obj){
    obj.closest("li").remove();
    personNum--;
    $("#addPerson>div>h4>i").html(personNum);
}


//计算人数
function count(){
    var DateNow=new Date();
    var num=$(".ui-border-t[choose='true']").length;
    var children=0;
    $(".ui-border-t[choose='true']").each(function(i,e){
        if(DateNow.getFullYear()-$(e).find(".cardid").html().substring(7,11)<12 ){
            children++;
            num--;
        }else if($(e).find(".cardid").html().substring(7,11)-DateNow.getFullYear()==12 ){
            if($(e).find(".cardid").html().substring(11,13)<(DateNow.getMonth()+1)){
                children++;
                num--;
            }else if($(e).find(".cardid").html().substring(11,13)==(DateNow.getMonth()+1)){
                if($(e).find(".cardid").html().substring(13,15)<(DateNow.getDate())){
                    children++;
                    num--;
                }
            }
        }
    });
    $(".leftBtn").find("i").eq(0).html(num);
    $(".leftBtn").find("i").eq(1).html(children);
}