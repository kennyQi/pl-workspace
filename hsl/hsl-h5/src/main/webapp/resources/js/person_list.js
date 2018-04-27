var contextPath = $("#contextPathAdd").attr("name");
//游玩人数标识不能做为人数计算
var personRel=0;
$(function(){
	
	var message = $("#message1").val();
	if(message != "1"){
		showMsg(message);
	}
	
	//跳转到添加游玩人页面
	$("#addPerson").click(function(){
		var userId = $("#personListSubmitForm input[name='userId']").val();
		var lineId = $("#personListSubmitForm input[name='lineId']").val();
		var travelDate = $("#personListSubmitForm input[name='travelDate']").val();
		var adultNo = $("#personListSubmitForm input[name='adultNo']").val();
		var childNo = $("#personListSubmitForm input[name='childNo']").val();
		var htm ="";
		$(".ui-border-t[choose='true']").each(function(i,e){
			htm +="<input type='text' value='"+$(e).find("#travelerId").val()+"' name='travelerIds'>" ;
		});
		htm += "<input type='text' value='"+userId+"' name='userId'>" +
		       "<input type='text' value='"+lineId+"' name='lineId'>" +
		       "<input type='text' value='"+travelDate+"' name='travelDate'>" +
		       "<input type='text' value='"+adultNo+"' name='adultNo'>" +
		       "<input type='text' value='"+childNo+"' name='childNo'>";
		$("#personListAddForm #addpersonHiddenValue").html(htm);
		$("#personListAddForm").submit();
	});
	
    var state=GetQueryString("state");
    if(state==1){

    }
    //证件类型
    var personArr=["","身份证","军官证","护照"];

    //保存新增游客表单
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
        
       $("#addPersonForm").submit();

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
    
    //编辑游客 提交表单
    $("#editPersonSure").click(function() {
        var name = $("#editPersonForm #person_name").val().trim()
            , type = $("#editPersonForm #person_select").find("option:checked").val()
            , num = $("#editPersonForm #person_num").val().trim()
            , mobile = $("#editPersonForm #person_mobile").val().trim()
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
        
       $("#editPersonForm").submit();

    });

    //返回
    $("#cancel").click(function(){
        $(".content").hide();
        setTimeout(function(){
            $("#main,.fixedBtn").show();
        },380);
    });

    //下一步，提交表单！
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
        
      //判断游玩人数量
    	var message = checkPerson(1);
    	//游玩人数量不正确
    	if(message != ""){
    		showMsg(message);
    		return false;
    	}
        $("#personListSubmitForm").submit();
    });
    
    //滑动
    $("#personList li").each(function(i,obj){
        var startX= 0,left= 0,endX= 0,nowX=0,endY=0,startY=0;
        //if(obj.target=="")
        obj.addEventListener('touchstart', function(event) {
            //event.preventDefault();
            if (event.targetTouches.length == 1&&!(event.target.className.indexOf("editor")!=-1)) {
                var touch = event.targetTouches[0];
                endX=startX=touch.pageX;
                startY=touch.pageY;
                left=parseInt($(obj).attr("left"));
            }
        }, false);
        obj.addEventListener('touchmove', function(event) {
           // event.preventDefault();
            if (event.targetTouches.length == 1&&!(event.target.className.indexOf("delete")!=-1)) {
                var touch = event.targetTouches[0];
                endX=touch.pageX;
                endY=touch.pageY;
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
           // event.preventDefault();
            if(!(event.target.className.indexOf("delete")!=-1)){
            	 if(Math.abs(endX-startX)<10){
                     //$(obj).click();
             	   objclick(obj);
             	   return;
                 }
                if(event.target.className.indexOf("editor")!=-1){
                	//count();
                	//跳转到编辑游玩人页面
                	var userId = $("#personListSubmitForm input[name='userId']").val();
            		var lineId = $("#personListSubmitForm input[name='lineId']").val();
            		var travelDate = $("#personListSubmitForm input[name='travelDate']").val();
            		var adultNo = $("#personListSubmitForm input[name='adultNo']").val();
            		var childNo = $("#personListSubmitForm input[name='childNo']").val();
            		var htm ="";
            		$(".ui-border-t[choose='true']").each(function(i,e){
            			htm +="<input type='text' value='"+$(e).find("#travelerId").val()+"' name='travelerIds'>" ;
            		});
            		htm += "<input type='text' value='"+userId+"' name='userId'>" +
            		       "<input type='text' value='"+lineId+"' name='lineId'>" +
            		       "<input type='text' value='"+travelDate+"' name='travelDate'>" +
            		       "<input type='text' value='"+$(this).attr('rel')+"' name='travelerId'>" +
            		       "<input type='text' value='"+adultNo+"' name='adultNo'>" +
            		       "<input type='text' value='"+childNo+"' name='childNo'>";
            		$("#personListEditForm #EditpersonHiddenValue").html(htm);
            		$("#personListEditForm").submit();
                    return;
                }
                if(Math.abs(endY-startY)>15){
                	return
                }
                if(endX-startX<0){
                    $(obj).animate({"transform":"translateX(-75px)","-webkit-transform":"translateX(-75px)"},50);
                    $(obj).attr("left",1);
                }else{
                    $(obj).animate({"transform":"translateX(0px)","-webkit-transform":"translateX(0px)"},50);
                    $(obj).attr("left",0);
                }
              
            }
        }, false);
    });
    //var ua=navigator.userAgent;
    function objclick(obj){

        if($(obj).find(".choose").hasClass("on")){
        	$(obj).attr("choose",false).find(".choose").removeClass("on");
        	checkPerson(0);
        }else{
        	
            $(obj).attr("choose",true).find(".choose").addClass("on");
            
            //判断游玩人数量
        	var message = checkPerson(0);
        	//游玩人数量不正确
        	if(message != ""){
        		showMsg(message);
        		$(obj).attr("choose",false).find(".choose").removeClass("on");
        		return false;
        	}
        }
    }
    /*$("#personList .ui-border-t").click(function(){
    	
        if($(this).find(".choose").hasClass("on")){
        	$(this).attr("choose",false).find(".choose").removeClass("on");
        	checkPerson(0);
        }else{
        	
            $(this).attr("choose",true).find(".choose").addClass("on");
            
            //判断游玩人数量
        	var message = checkPerson(0);
        	//游玩人数量不正确
        	if(message != ""){
        		showMsg(message);
        		$(this).attr("choose",false).find(".choose").removeClass("on");
        		return false;
        	}
        }
    });*/
});

//获取参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)return r[2];
    return "";
}
//勾选游玩人
/*$(document).on("click","#personList .ui-border-t",function(){
	
    if($(this).find(".choose").hasClass("on")){
    	$(this).attr("choose",false).find(".choose").removeClass("on");
    	checkPerson(0);
    }else{
    	
        $(this).attr("choose",true).find(".choose").addClass("on");
        
        //判断游玩人数量
    	var message = checkPerson(0);
    	//游玩人数量不正确
    	if(message != ""){
    		showMsg(message);
    		$(this).attr("choose",false).find(".choose").removeClass("on");
    		return false;
    	}
    }
});*/


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
    var htm ="";
    $(".ui-border-t[choose='true']").each(function(i,e){
        if(DateNow.getFullYear()-$(e).find(".cardid").html().substring(6,10)<12 ){
            children++;
            num--;
        }else if($(e).find(".cardid").html().substring(6,10)-DateNow.getFullYear()==12 ){
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
        //拼接选中了的游玩人
        htm +=("<input type='text' value='"+$(e).find("#travelerId").val()+"' name='travelerIds'>");
    });
    $("#personListSubmitForm #userIds").html(htm);    
    $(".leftBtn").find("i").eq(0).html(num);
    $(".leftBtn").find("i").eq(1).html(children);
}

//检查人数
function checkPerson(type){
	var message = "";
    var DateNow=new Date();
    var num=$(".ui-border-t[choose='true']").length;
    var children=0;
	var adultNo = $("#personListSubmitForm input[name='adultNo']").val();
	var childNo = $("#personListSubmitForm input[name='childNo']").val();
	var htm="";
	
    $(".ui-border-t[choose='true']").each(function(i,e){
    	var birth=$(e).find(".cardid").html();
        if(DateNow.getFullYear()-birth.substring(6,10)<12 ){
            children++;
            num--;
        }else if(DateNow.getFullYear()-birth.substring(6,10)==12 ){
            if(birth.substring(10,12)>(DateNow.getMonth()+1)){
                children++;
                num--;
            }else if(birth.substring(10,12)==(DateNow.getMonth()+1)){
                if(birth.substring(12,14)<(DateNow.getDate())){
                    children++;
                    num--;
                }
            }
        }

        htm +=("<input type='text' value='"+$(e).find("#travelerId").val()+"' name='travelerIds'>");
    });

    $("#personListSubmitForm #userIds").html(htm);    
    if(adultNo < num){
    	
    	message = "只能选择"+adultNo+"位成人！！"; 
    }
 
    if(childNo < children){
    	if(children == 1){
    		message = "该订单不包含儿童！！"; 
    	}else{
    		message = "只能选择"+childNo+"位儿童！！"; 
    	}
    }
  
    if(type == 1){
    	
    	if(adultNo != num || childNo != children){
    		message = "请选择"+adultNo+"位成人"+childNo+"位儿童";
    	}
    }
    if(message == ""){
    	 $(".leftBtn").find("i.adult").html(num);
    	 $(".leftBtn").find("i.chil").html(children);
    }
    return message;
}


//页面初始化 加载数据
$(function(){
	choiceId();
})

//页面加载完成 选中提交表单之前已经勾选游玩人
function choiceId(){
	$("#personListSubmitForm #userIds input[name='travelerIds']").each(function(j,m){
		var countent = $("#"+$(m).val()).html();
		if(countent != ""){
			$("#"+$(m).val()).attr("choose",true).find(".choose").addClass("on");
		}
	});
	//计算人数
	count();
}
