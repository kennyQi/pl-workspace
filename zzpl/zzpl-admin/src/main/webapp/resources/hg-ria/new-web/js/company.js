$(function(){
    $(".company_costCenter_list a label").click(function(){
        ply.alertBox("confirm","确定要删除吗",null,function(){
            ply.alertBoxClose("confirm");
            ply.alertBox("warm","删除成功!","suc");
        });

    });

});