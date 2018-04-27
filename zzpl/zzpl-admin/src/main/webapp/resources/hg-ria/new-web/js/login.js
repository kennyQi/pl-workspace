$(function(){
    /*轮播*/
    ///设置初始状态
    var len=$("#carousel .turn").length;
    var wid=parseInt($("#carousel").eq(0).width());
    var hei=$("#carousel .turn").eq(0).height();
    var html="";
    $("#carousel .turn").width(wid);
    $("#carousel .turn").hide().eq(0).show();
    //$("#carousel .img_list").css("width",len*wid);
    for(var i=0;i<len;i++){
        html+="<li>"+(i+1)+"</li>";
        $("#carousel .turn").eq(i).css("z-index",0);
    }
    $("#carousel .icon").html(html);

    var icon_wid=$("#carousel .icon").width();
    //$("#carousel .icon").css("left",(wid-icon_wid)/2).find("li:eq(0)").addClass("on");
    $("#carousel .icon").find("li:eq(0)").addClass("on");
    $("#carousel .turn").eq(0).css("z-index",3);

    //按钮点击事件
    $("#carousel .icon li").each(function(i,e){
        $(this).click(function(){
            if($(this).attr("class")!="on"){
                $("#carousel .icon li").removeClass("on").eq(i).addClass("on");
                $("#carousel .turn").fadeOut(800).eq(i).show(0);
                setTimeout(function(){
                    $("#carousel .turn").css("z-index",0).eq(i).css("z-index",len);
                },500);
            }
        });
    });

    setInterval(function(){
        var rel=$("#carousel .icon li.on").index();
        if(rel==(len-1)){
            rel=0;
        }else{
            rel++;
        }
        $("#carousel .icon li").removeClass("on").eq(rel).addClass("on");
        $("#carousel .turn").fadeOut(800).eq(rel).show(0);
        setTimeout(function(){
            $("#carousel .turn").css("z-index",0).eq(rel).css("z-index",len);
        },500);
    },3000);

    /*轮播*/


    //提交验证
    /*$(".login-submit").click(function(){
        $(this).closest("form").submit();
    });*/

    $("#login").submit(function(){
        var flag=true;
        $(this).find("input").each(function(){
            if(flag) {
                if ($(this).val() == "") {
                    $(this).next("span").find(" label").html("&nbsp;"+$(this).attr("placeholder") + "不能为空！&nbsp;");
                    flag=false;
                }
            }
        });
        return flag;
    }).find("input").focus(function(){
        $("#login").find(".tips label").html("");
    })
});