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
		var lspId = $("#personListSubmitForm input[name='lspId']").val();
		var adultNo = $("#personListSubmitForm input[name='adultNo']").val();
		var linkName = $("#personListSubmitForm input[name='linkName']").val();
		var linkMobile = $("#personListSubmitForm input[name='linkMobile']").val();
		var email = $("#personListSubmitForm input[name='email']").val();
		var lastNum = $("#personListSubmitForm input[name='lastNum']").val();
		
		
		var htm ="";
		$(".ui-border-t[choose='true']").each(function(i,e){
			htm +="<input type='text' value='"+$(e).find(".travelerId").val()+"' name='travelerIds'>" ;
		});
		htm += 
		       "<input type='text' value='"+lspId+"' name='lspId'>" +
		       "<input type='text' value='"+lastNum+"' name='lastNum'>" +
		       "<input type='text' value='"+adultNo+"' name='adultNo'>" +
		       "<input type='text' value='"+linkName+"' name='linkName'>" +
		       "<input type='text' value='"+linkMobile+"' name='linkMobile'>" +
		       "<input type='text' value='"+email+"' name='email'>" ;
		       
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
        
       $.ajax({
    		cache: true,
    		type: "POST",
    		url:'./saveAddTraveler',
    		data:$('#addPersonForm').serialize(),
    		async: false,
    	    success: function(data) {
    	    	var msg = eval("("+data+")");
    		    if(msg.status=="success"){
    		    	$("#addPersonForm").submit();
    		    }else{
    		    	showMsg(msg.message);
    		    }
    	    },
           error: function(request) {
               console.error("连接失败");
               console.error(request);
           }
    	});
      
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
        
        $.ajax({
    		type: "POST",
    		url:'./saveEditTraveler',
    		data:$('#editPersonForm').serialize(),
    		/*async: false,*/
    	    success: function(data) {
    	    	var msg = eval("("+data+")");
    	    	
    		    if(msg.status=="success"){
    		    	$("#editPersonForm").submit();
    		    }else{
    		    	showMsg(msg.message);
    		    }
    	    },
    	    error: function(request) {
    	        alert("连接失败");
    	    },
    	});

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
    
    //选中联系人
    $(".choose").click(function(){
    	 if($(this).hasClass("on")){
    		 $(this).removeClass("on").closest(".ui-border-t").attr("choose",false);
         	checkPerson(0);
         }else{
         	
        	 $(this).addClass("on").closest(".ui-border-t").attr("choose",true);
             
             //判断游玩人数量
         	var message = checkPerson(0);
         	//游玩人数量不正确
         	if(message != ""){
         		showMsg(message);
         		$(this).removeClass("on").closest(".ui-border-t").attr("choose",false);
         		return false;
         	}
         }
    });
    
    //编辑联系人
    $(".person").click(function(){
    	
    	var lspId = $("#personListSubmitForm input[name='lspId']").val();
		var adultNo = $("#personListSubmitForm input[name='adultNo']").val();
		var linkName = $("#personListSubmitForm input[name='linkName']").val();
		var linkMobile = $("#personListSubmitForm input[name='linkMobile']").val();
		var email = $("#personListSubmitForm input[name='email']").val();
		var lastNum = $("#personListSubmitForm input[name='lastNum']").val();
		
		
		var htm ="";
		//页面跳转，保存已选中联系人。
		$(".ui-border-t[choose='true']").each(function(i,e){
			htm +="<input type='text' value='"+$(e).find(".travelerId").val()+"' name='travelerIds'>" ;
		});
		htm +="<input type='text' value='"+$(this).find(".travelerId").val()+"' name='travelerId'>" ;
		htm += 
		       "<input type='text' value='"+lspId+"' name='lspId'>" +
		       "<input type='text' value='"+lastNum+"' name='lastNum'>" +
		       "<input type='text' value='"+adultNo+"' name='adultNo'>" +
		       "<input type='text' value='"+linkName+"' name='linkName'>" +
		       "<input type='text' value='"+linkMobile+"' name='linkMobile'>" +
		       "<input type='text' value='"+email+"' name='email'>" ;
		$(this).closest(".ui-border-t").find("form").append(htm).submit();
    });

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
 
});

//获取参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)return r[2];
    return "";
}
//勾选游玩人


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
        htm +=("<input type='text' value='"+$(e).find(".travelerId").val()+"' name='travelerIds'>");
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
	var adultNo = $("#personListSubmitForm input[name='lastNum']").val();
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

        htm +=("<input type='text' value='"+$(e).find(".travelerId").val()+"' name='travelerIds'>");
    });

    $("#personListSubmitForm #userIds").html(htm);    
    if(adultNo < num){
    	
    	message = "最多只能选择"+adultNo+"位游客"; 
    }
 
   /* if(childNo < children){
    	if(children == 1){
    		message = "该订单不包含儿童！！"; 
    	}else{
    		message = "只能选择"+childNo+"位儿童！！"; 
    	}
    }*/
  
   /* if(type == 1){
    	
    	if(adultNo != num || childNo != children){
    		message = "请选择"+adultNo+"位成人"+childNo+"位儿童";
    	}
    }*/
    if(message == ""){
    	 $(".leftBtn").find("i.adult").html(num);
    	// $(".leftBtn").find("i.chil").html(children);
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


