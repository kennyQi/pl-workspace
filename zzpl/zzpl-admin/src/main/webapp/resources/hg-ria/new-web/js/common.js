//=============================================================================
// 文件名:		common.js
// 版权:		Copyright (C) 2015 zzply
// 创建人:		han.zw
// 创建日期:	2015-8-07
// 描述:		此文件修改请通知作者
// *************常用代码示例: ***********


var ply={
	version: '1.0',
    author: "han.zw",
	// 获取QueryString的参数
	GetQueryString: function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)return r[2];
	    return "";
	}
	//json替换html模板
	,stringReplace_com:function(data,template) {
		var arr=data;
		var str="";
		for(var i=0;i<arr.length;i++){
			/*str+=template.replace(/\{\w+\}/g, function(word) {
				word=word.replace("{","");
				word=word.replace("}","");
                return arr[i][word] || '';
			});*/
            str+=template.replace(/\\?\{([^{}]+)\}/g, function (match, name) {
                return (arr[i][name] === undefined) ? '' : arr[i][name];
            });
		}
		return str;
	},
    //左侧导航收起
    navLeft:function(){
        $(".nav_title").click(function(){
            $(this).toggleClass("nav_title_on").closest(".nav_model").find(".nav_op").toggle();
            $(this).closest(".nav_model").toggleClass("nav_model_on");
        });
    },
    //分页
    page:function(){
        var $page=$("#page");
        if($page.length>0){
            $page.pageBar({
                pageNum : "pageNum",//页码class
                pageCount : $page.attr("total"),//总页数
                formClass : $page.attr("formClass")//分页表单class
            });
        }
    },
   /* //弹出框
    alertMsg:function(){
        var wid=$(window).width(),hei=$(window).height();
        var $alert=$("#alert");
        var alertW=$alert.width(),alertH=$alert.height();
        $("#alertBg").height(hei).show();
        $alert.css({left:(wid-alertW)/2,top:(hei-alertH)/2}).show();
    },
    alertMsgClose:function(){
        $("#alert").add("#alertBg").hide();
    },
    //弹出框
    alertWarm:function(msg,type){
        var wid=$(window).width(),hei=$(window).height();
        var $alert=$("#alertWarm");
        $alert.html($alert.html().replace("{msg}",msg).replace("{type}",type));
        var alertW=$alert.width(),alertH=$alert.height();
        $("#alertWarmBg").height(hei).show();
        $alert.css({left:(wid-alertW)/2,top:(hei-alertH)/2}).show();
    },
    alertWarmClose:function(){
        $("#alertWarm").add("#alertWarmBg").hide();
    },*/
    /*模拟弹窗
        type:{form 弹出表单,
              warm 提示框,
              confirm 确认框
            }
        msg:提示内容
        status:{suc 成功，fail 失败}
     */
    alertBox:function(type,msg,status,call){
        var $alert=$("#alert_"+type);
        var wid=$(window).width(),hei=$(window).height();
        var alertW=$alert.width(),alertH=$alert.height();
        $("#alertBg").height(hei).show();
        if(msg!=null){
            $alert.html($alert.html().replace("{msg}",msg))
        }
        if(status!=null){
            $alert.html($alert.html().replace("{status}",status))
        }
        $alert.css({left:(wid-alertW)/2,top:(hei-alertH)/2}).show();
        if(typeof call=="function"){
            $alert.find(".a-btn-sure").bind("click",call);
        }
    },
    alertBoxClose:function(type){
        $("#alert_"+type).add("#alertBg").hide();
        if(type=="confirm"){
            $("#alert_"+type).find(".a-btn-sure").unbind("click");
        }
    },
    loading:function(){
      var html='<div class="loading pa">'+
            '<div class="loading_bg pa"></div>'+
            '<label class="loading_icon pa"></label>'+
           ' </div>';
      if($(".loading").length<1){
    	  $("body").append(html);
      }
        
    },
    loadingClose:function(){
        $(".loading").remove();
    },

    //各种input控件
    controlInput:function(){
        //日期
        $(".input-data").click(function(){
            $(".current .input-select").click();
            laydate();
        });

        //模拟下拉
        $(document).on("click",".input-select",function(){
        	//queryCostCent($(this).attr("id"));
            if($(this).closest(".input-selectBox").hasClass("current")){
                $(this).closest(".input-selectBox").removeClass("current");
            }else{
                $(".input-selectBox").removeClass("current");
                $(this).closest(".input-selectBox").addClass("current");
                setTimeout(function(){
                    $("body").bind("click",function(e){
                        if(!(e.target.className.indexOf("input-select")!=-1)){
                            $(".input-selectBox").removeClass("current");
                            $("body").unbind("click");
                        }
                    });
                    }, 100);
            }
        });


        //模拟下拉的选择
        $(document).on("click",".input-selectBox li",function(){
        	var data=$(this).closest(".input-selectBox").find("input").val();
            $(this).closest(".input-selectBox").removeClass("current").find(".input-select").html($(this).html());
            $(this).closest(".input-selectBox").find("input").val($(this).attr("data"));
            if($(this).attr("rel")=="companyID"){
            	
            	if($(this).closest(".input-selectBox").attr("first")=="true"&&
            			data!=$(this).attr("data")&&
            			$(".order_member .member_box").length>0){
                	ply.alertBox("confirm","切换公司将会删除乘机人，确认切换？","",companyChange);
                	//绑定原公司id
                	$(".a-btn-sure").attr("data",data);
            	}else{
            		selectUser();
                	selectCost();
                	$(this).closest(".input-selectBox").attr("first","true");
            	}
            	
            }
        });
        $(".input-selectBox").each(function(){
            $(this).find("li[data='"+ $(this).find("input").val()+"']").click();
        });


        //全选
        $(".table th [type='checkbox']").change(function(){
            var checkd=$(this).prop("checked");
            $(this).closest(".table").find("td [type='checkbox']").prop("checked",checkd);
        });

        //全选对应
        $(".table td [type='checkbox']").change(function(){
            var checkd=$(this).prop("checked");
            if(checkd){
                $(this).closest(".table").find("th [type='checkbox']").prop("checked",checkd);
                $(this).closest(".table").find("td [type='checkbox']").each(function(){
                    if($(this).prop("checked")!=checkd){
                        $(this).closest(".table").find("th [type='checkbox']").prop("checked",false);
                    }
                });
            }else{
                $(this).closest(".table").find("th [type='checkbox']").prop("checked",false);
            }

        });


        //表单验证
        $("form").submit(function(e){
            var $this=$(this);
            var msg="";
            var flag=true;
            $this.find("[validate='true']").each(function(){
                if(flag){
                    if($(this).val()==""){
                        msg=$(this).attr("for")+"不能为空！";
                        $(".errorMsg").html(msg);
                        flag=false;
                    }
                }
            });
            $this.find("[datatype='mobile']").each(function(){
                if(flag){
                    if($(this).val().length<11){
                        msg=$(this).attr("for")+"格式不对！";
                        $(".errorMsg").html(msg);
                        flag=false;
                    }
                }
            });
            $this.find("[datatype='icCard']").each(function(){
                if(flag){
                    if($(this).val().length<15){
                        msg=$(this).attr("for")+"格式不对！";
                        $(".errorMsg").html(msg);
                        flag=false;
                    }
                }
            });
            $this.find("[datatype='again']").each(function(){
                if(flag){
                    if($this.find("[datatype='again']").eq(0).val()!=$this.find("[datatype='again']").eq(1).val()){
                        msg="两次输入不一致！";
                        $(".errorMsg").html(msg);
                        flag=false;
                    }
                }
            });
            if(!flag){
            	e.preventDefault();
            }else{
            	ply.alertBoxClose("form");
                ply.loading();
            }

           /* return flag;*/

        }).find("input").focus(function(){
            $(".errorMsg").html("");
        });

        //表单提交
        $(".btn-submit").click(function(){
            $(this).closest("form").submit();
        });
    }
};

$(function(){
    ply.navLeft();
    ply.page();
    ply.controlInput();
    //左侧栏目选中暂用js
    $(".g-nav li").each(function(){
        if(location.href.replace("#","").indexOf($(this).find("a").attr("href"))!=-1){
            $(this).addClass("current");
        }
    });
});

