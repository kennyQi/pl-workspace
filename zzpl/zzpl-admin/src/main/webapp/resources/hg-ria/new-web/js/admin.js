$(function(){
    $("#addPath").click(function(){
        var path_length=$(".table tbody tr").length;
        $("#tableBox").append($("#temp").html().replace(/\{eq\}/g,path_length+1));
    });
    //删除
    $(".path_delete").click(function(){

        ply.alertBox("confirm","确认删除吗?",null,function(){
            ply.alertBoxClose("confirm");
            ply.alertBox("warm","删除成功!","suc");
        });
    });

    //修改流程
    $(".path_change").click(function(){
        var eq= $(this).closest("tr").find("td").eq(0).html();
        var name=$(this).closest("tr").find("td").eq(1).html();
        var nextEq=$(this).closest("tr").find("td").eq(4).html();
        $(this).closest("tr").html($("#temp tr").html().replace(/\{eq\}/g,eq));
        $("tr[rel='"+eq+"']").find("td").eq(1).find("input").val(name);
        $("tr[rel='"+eq+"']").find("td").eq(4).find("input").val(nextEq);
    });

    //删除流程
    $(".path_list_delete").click(function(){
        ply.alertBox("confirm","确认删除吗?",null,function(){
            ply.alertBoxClose("confirm");
            ply.alertBox("warm","删除成功!","suc");
        });
    });


    //移动角色
    $("#changeRole li").click(function(){
        if($(".admin_role_right td input:checked").length>0){

        }else{
            ply.alertBox("warm","请选择职员!","fail");
        }
    });

    $(document).on("click","#tableBox .input-btn",function(){
        ply.alertBox("warm","请选择职员!","suc");
    });


    //删除公司
    $("#company_delete").click(function(){
        if($(".table tbody td input:checked").length>0){
            ply.alertBox("confirm","确认删除公司吗",null,function(){
                ply.alertBoxClose("confirm");
                ply.alertBox("warm","删除成功!","suc");
            });
        }else{
            ply.alertBox("warm","请选择一个公司!","fail");
        }
    });


    //编辑公司
    $("#company_edit").click(function(){
        if($(".table tbody td input:checked").length==1){
            ply.alertBox("form");
        }else if($(".table tbody td input:checked").length>1){
            ply.alertBox("warm","最多选择一个公司!","fail");
        }else{
            ply.alertBox("warm","请选择一个公司!","fail");
        }
    });

});