require.config({
    //通用js模块路径
	  baseUrl:request_path+"/resources/js/",
    //通用框架
    paths: {
        jquery: 'libs/jquery-2.1.4.min'
    },

    waitSeconds: 0
});

//默认调用js
require(["jquery"],
function($) {
    $(function(){
        if(location.href.indexOf("editor=true")!=-1){
            //入口是个人中心
            $(".connectBox").prepend('<span class="del"></span>');
        }else{
            //入口是选择乘机人
            $(".connectBox").prepend('<span class="choose"></span>');
        }
        var connectList=$("#connectList");
        //删除联系人
        connectList.on("click",".del",function(){
        	if($("#buyOthers").text()==0){
        		alertMsg("无权限！");
        		return false;
        	}
            var selReturn=confirm("确定删除'"+$(this).closest(".connectBox").find(".name").html()+"'联系人吗？");
            if(selReturn){
                //删除联系人
                console.log("删除联系人");
                $.post("deleteConnectUser?id="+$(this).closest(".connectBox").find(".myid").html(),null, function(data) {
              
                	
                	if (data.success) {
    					alertMsg(data.message)
    					window.location.href = window.location.href;
    				}else{
    					alertMsg(data.message)
    				}
                	}, "json");
                
     
            }
        });

        //编辑联系人
        connectList.on("click",".editor",function(){
        	if($("#buyOthers").text()==0){
        		alertMsg("无权限！");
        		return false;
        	}
            var data={};
            var parent=$(this).closest(".connectBox");
            data.name=parent.find(".name").html();
          
            data.cardDetail=parent.find(".cardDetail i").html();
            data.phone=parent.find(".phone").html();
            data.myid=parent.find(".myid").html();
            data.group=parent.find(".group").attr("data-value");
            data.costid=parent.find(".costID").html();
//            alert(data.group)
          
            for(var x in data){
                $("#editorConnect").find("[data-name='"+x+"']").val(data[x]);
            }
            //弹出编辑页
            $("#editor").css({"display":"",
                "position":"absolute",
                "-webkit-transform":"translateX(505px)",
                "left":"0px",
                "z-index":505,
                "top": "0px"}).animate({ "z-index":"0"}, {
                duration:400,
                step: function(now,fx) {
                    $("#editor").css('-webkit-transform','translateX('+now+'px)');
                }});
            setTimeout(function(){
                $("#connect").hide();
            },500);

            
            document.getElementById("form1").action="editConnectUser?id="+ document.getElementById("myid").value;
        });

        //编辑返回
        $(".editorBack").click(function(){
            $("#connect").show();
            $("#editor").css({"display":"",
                "position":"absolute",
                "-webkit-transform":"translateX(0px)",
                "left":"0px",
                "z-index":0,
                "top": "0px"}).animate({ "z-index":"505"}, {
                duration:400,
                step: function(now,fx) {
                    $("#editor").css('-webkit-transform','translateX('+now+'px)');
                }});
            setTimeout(function(){
                $("#editor").removeAttr("style").hide();
            },500);
        });

        //新增联系人
        $(".addConnect").click(function(){
        	if($("#buyOthers").text()==0){
        		alertMsg("无权限！");
        		return false;
        	}
            $("#editor").css({"display":"",
                "position":"absolute",
                "-webkit-transform":"translateX(505px)",
                "left":"0px",
                "z-index":505,
                "top": "0px"}).animate({ "z-index":"0"}, {
                duration:400,
                step: function(now,fx) {
                    $("#editor").css('-webkit-transform','translateX('+now+'px)');
                }});
            document.getElementById("form1").action="addConnectUser";
            setTimeout(function(){
                $("#connect").hide();
            },500);
            var data={};
            var parent=$(this).closest(".connectBox");
            data.name=parent.find(".name").html();
            data.cardDetail=parent.find(".cardDetail i").html();
            data.phone=parent.find(".phone").html();
            data.myid=parent.find(".myid").html();
            
          
            for(var x in data){
                $("#editorConnect").find("[data-name='"+x+"']").val('');
            }
            
        });

        //选中联系人
        connectList.on("click",".choose",function(){
            $(this).toggleClass("chooseOn");
        })
    });
});
/*=======
require.config({
    //通用js模块路径
	  baseUrl:request_path+"/resources/js/",
    //通用框架
    paths: {
        jquery: 'libs/jquery-2.1.4.min'
    },

    waitSeconds: 0
});

//默认调用js
require(["jquery"],
function($) {
    $(function(){
        if(location.href.indexOf("editor=true")!=-1){
            //入口是个人中心
            $(".connectBox").prepend('<span class="del"></span>');
        }else{
            //入口是选择乘机人
            $(".connectBox").prepend('<span class="choose"></span>');
        }
        var connectList=$("#connectList");
        //删除联系人
        connectList.on("click",".del",function(){
            var selReturn=confirm("确定删除'"+$(this).closest(".connectBox").find(".name").html()+"'联系人吗？");
            if(selReturn){
                //删除联系人
                console.log("删除联系人");
                $.post("deleteConnectUser?id="+$(this).closest(".connectBox").find(".myid").html(),null, function(data) {
              
                	
                	if (data.success) {
    					alertMsg(data.message)
    					window.location.href = window.location.href;
    				}else{
    					alertMsg(data.message)
    				}
                	}, "json");
                
     
            }
        });

        //编辑联系人
        connectList.on("click",".editor",function(){
            var data={};
            var parent=$(this).closest(".connectBox");
            data.name=parent.find(".name").html();
          
            data.cardDetail=parent.find(".cardDetail i").html();
            data.phone=parent.find(".phone").html();
            data.myid=parent.find(".myid").html();
            data.group=parent.find(".group").attr("data-value");
            data.costid=parent.find(".costID").html();
//            alert(data.group)
          
            for(var x in data){
                $("#editorConnect").find("[data-name='"+x+"']").val(data[x]);
            }
            //弹出编辑页
            $("#editor").css({"display":"",
                "position":"absolute",
                "-webkit-transform":"translateX(505px)",
                "left":"0px",
                "z-index":505,
                "top": "0px"}).animate({ "z-index":"0"}, {
                duration:400,
                step: function(now,fx) {
                    $("#editor").css('-webkit-transform','translateX('+now+'px)');
                }});
            setTimeout(function(){
                $("#connect").hide();
            },500);
         
            var theval=$("#thecostid").val();
            //设置action
            $("#option option[value=theval]").attr("selected", true); 

            
            document.getElementById("form1").action="editConnectUser?id="+ document.getElementById("myid").value;
        });

        //编辑返回
        $(".editorBack").click(function(){
            $("#connect").show();
            $("#editor").css({"display":"",
                "position":"absolute",
                "-webkit-transform":"translateX(0px)",
                "left":"0px",
                "z-index":0,
                "top": "0px"}).animate({ "z-index":"505"}, {
                duration:400,
                step: function(now,fx) {
                    $("#editor").css('-webkit-transform','translateX('+now+'px)');
                }});
            setTimeout(function(){
                $("#editor").removeAttr("style").hide();
            },500);
        });

        //新增联系人
        $(".addConnect").click(function(){
            $("#editor").css({"display":"",
                "position":"absolute",
                "-webkit-transform":"translateX(505px)",
                "left":"0px",
                "z-index":505,
                "top": "0px"}).animate({ "z-index":"0"}, {
                duration:400,
                step: function(now,fx) {
                    $("#editor").css('-webkit-transform','translateX('+now+'px)');
                }});
            document.getElementById("form1").action="addConnectUser";
            setTimeout(function(){
                $("#connect").hide();
            },500);
            var data={};
            var parent=$(this).closest(".connectBox");
            data.name=parent.find(".name").html();
            data.cardDetail=parent.find(".cardDetail i").html();
            data.phone=parent.find(".phone").html();
            data.myid=parent.find(".myid").html();
            
          
            for(var x in data){
                $("#editorConnect").find("[data-name='"+x+"']").val('');
            }
            
        });

        //选中联系人
        connectList.on("click",".choose",function(){
            $(this).toggleClass("chooseOn");
        })
    });
});
>>>>>>> 33e0c90d0449278b938db9bbbdea2aebfd3694c1
*/