$(function(){
    $("#upload").hover(function(){
        $(".leading").addClass("leadingOn");
    },function(){
        $(".leading").removeClass("leadingOn");
    });


    //删除员工
    $("#manage_delete").click(function(){
        if($(".table tbody td input:checked").length>0){
            ply.alertBox("confirm","确定要删除吗",null,function(){
                ply.alertBoxClose("confirm");
                ply.alertBox("warm","删除成功!","suc");
            });
        }else{
            ply.alertBox("warm","请选择一名员工!","fail");
        }
    });


    //编辑员工
    $("#manage_edit").click(function(){
        if($(".table tbody td input:checked").length==1){
            ply.alertBox("form");
        }else if($(".table tbody td input:checked").length>1){
            ply.alertBox("warm","最多选择一名员工!","fail");
        }else{
            ply.alertBox("warm","请选择一名员工!","fail");
        }
    });

    //搜索
    $("#search_btn").mousedown(function(){
        $(this).css("background-color","#D98705");
    }).mouseup(function(){
        $(this).css("background-color","");

    });
});