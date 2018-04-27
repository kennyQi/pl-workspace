$(function(){


    //删除公司
    $("#order_over,#order_being").click(function(){
        if($(".table tbody td input:checked").length>0){
            var confirmT=confirm("确定要标记为已结算吗？");
            if(confirmT){
                ply.alertWarm("提交成功","suc");
                $(this).closest("tr").remove();
            }
        }else{
            alert("请选择一个订单");
        }
    });



});