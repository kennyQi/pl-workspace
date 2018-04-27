$(function(){
    //基本资料编辑
    $("#edit_personInfo").click(function(){
        $("#personInfo .btn_edit_wrap").hide();
        $("#personInfo input").removeClass("off").attr("readonly",false);
        $(".g-search").removeClass("g-searchOff");
        $("#personInfo .btn_wrap").show();
    });
    //基本资料保存
    $("#btn_submit_1").click(function(){
        if(true){
            ply.alertBox("warm","保存成功!","suc");
            $("#personInfo .btn_edit_wrap").show();
            $("#personInfo input").addClass("off").attr("readonly",true);
            $("#personInfo .btn_wrap").hide();
            $(".g-search").addClass("g-searchOff");
        }else{
            ply.alertBox("warm","保存失败!","fail");
        }
    });

    //取消
    $(".btn_reset").click(function(){
        var form=$(this).closest("form");
        form.find(".reset_input").click();
        form.find(".btn_edit_wrap").show();
        form.find("input").addClass("off").attr("readonly",true);
        form.find(".btn_wrap").hide();
        form.find(".g-search").addClass("g-searchOff");
    });

    //联系人资料编辑
    $("#edit_linkManInfo").click(function(){
        $("#linkManInfo .btn_edit_wrap").hide();
        $("#linkManInfo input").removeClass("off").attr("readonly",false);
        $("#linkManInfo .btn_wrap").show();
    });
    //联系人资料保存
    $("#btn_submit_2").click(function(){
        if(true){
            ply.alertBox("warm","保存成功!","suc");
            $("#linkManInfo .btn_edit_wrap").show();
            $("#linkManInfo input").addClass("off").attr("readonly",true);
            $("#linkManInfo .btn_wrap").hide();
        }else{
            ply.alertBox("warm","保存失败!","fail");
        }
    });

});