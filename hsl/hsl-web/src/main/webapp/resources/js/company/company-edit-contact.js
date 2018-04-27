$(document).ready(function() {
    //编辑联系人提交表单
    $("#editperson").click(function(){

        var reg1 = /^[\u4E00-\u9FA5]{0,20}$/;
        var reg2 = /^0?(13|15|17|18)[0-9]{9}$/;
        var reg3 =/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])(\d{4}$|\d{3}[Xx]{1})$/;

        var name=$("#edit_person input[name='name']").val()
            ,phone=$("#edit_person input[name='mobile']").val()
            ,cardid=$("#edit_person input[name='idNo']").val()
            ;
        if(name == ""){
            $("#edit_person input[name='name']").next(".eorr").html("请输入正确的名字");
            return false;
        }
        if(reg1.test(name)){
            $("#edit_person input[name='name']").next(".eorr").html("");
        }else{
            $("#edit_person input[name='name']").next(".eorr").html("请输入正确的名字");
            return false;
        }
        if(reg2.test(phone)){
            $("#edit_person input[name='mobile']").next(".eorr").html("");
        }else{
            $("#edit_person input[name='mobile']").next(".eorr").html("请输入正确的手机号");
            return false;
        }
        if(reg3.test(cardid)){
            $("#edit_person input[name='idNo']").next(".eorr").html("");
        }else{
            $("#edit_person input[name='idNo']").next(".eorr").html("请输入正确的身份证");
            return false;
        }

        $.ajax({
            type: "POST",
            url:'../sava-edit-contact',
            data:$('#edit_person').serialize(),
            success: function(data) {
                var msg = eval("("+data+")");
                if(msg=="success"){
                    location.reload();
                }else{
                    alert(msg);
                }
            }
        });


    });

    //新增联系人
    $(".add_person_box #addperson").click(function(){

        var reg1 = /^[\u4E00-\u9FA5]{0,20}$/;
        var reg2 = /^0?(13|15|17|18)[0-9]{9}$/;
        var reg3 =/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])(\d{4}$|\d{3}[Xx]{1})$/;

        var name=$("#add_person input[name='name']").val()
            ,phone=$("#add_person input[name='mobile']").val()
            ,cardid=$("#add_person input[name='idNo']").val()
            ;
       if(name == ""){
           $("#add_person input[name='name']").next(".eorr").html("请输入正确的名字");
           return false;
       }
        if(reg1.test(name)){
            $("#add_person input[name='name']").next(".eorr").html("");
        }else{
            $("#add_person input[name='name']").next(".eorr").html("请输入正确的名字");
            return false;
        }
        if(reg2.test(phone)){
            $("#add_person input[name='mobile']").next(".eorr").html("");
        }else{
            $("#add_person input[name='mobile']").next(".eorr").html("请输入正确的手机号");
            return false;
        }
        if(reg3.test(cardid)){
            $("#add_person input[name='idNo']").next(".eorr").html("");
        }else{
            $("#add_person input[name='idNo']").next(".eorr").html("请输入正确的身份证");
            return false;
        }

        $.ajax({
            type: "POST",
            url:'../sava-add-contact',
            data:$('#add_person').serialize(),
            success: function(data) {
                var msg = eval("("+data+")");
                if(msg=="success"){
                    location.reload();
                }else{
                    alert(msg);
                }
            }
        });


    });

    //删除联系人
    $("#jpQueryForm #delPerson").click(function(){
        if (!confirm("确认要删除？")) {
           return ;
        }
        $.ajax({
            type: "POST",
            url:'../deletect-contact',
            data:{"travelerId":$(this).attr("rel")},
            success: function(data) {
                var msg = eval("("+data+")");
                if(msg=="success"){
                    location.reload();
                }else{
                    alert(msg);
                }
            }
        });


    });

    //弹出框
    var documentWidth = $(window).width();    //屏幕可视区宽度
    var documentHeight = $(window).height();   //屏幕可视区高度

    //编辑联系人
    $("a[name ='editContacts']").click(function(){
        var forDialog=$(this).attr("for")
            ,msgbox=$("."+forDialog+" .boxMessage")
            ,boxWidth=msgbox.width()
            ,boxHeight=msgbox.height()
            ;
        var id = $(this).attr("forid");
        var name = $("#"+id+"_name").html();
        var mobile = $("#"+id+"_mobile").html();
        var idNo = $("#"+id+"_idNo").html();

        $("#edit_person input[name='travelerId']").val(id);
        $("#edit_person input[name='name']").val(name);
        $("#edit_person input[name='mobile']").val(mobile);
        $("#edit_person input[name='idNo']").val(idNo);

        $("."+forDialog).show();
        msgbox.css({"margin-left":(documentWidth-boxWidth)/2});
        msgbox.css({"margin-top":(documentHeight-boxHeight)/2});
    });


    $("#add_new_person").click(function(){

        $("#add_person input[name='travelerId']").val("");
        $("#add_person input[name='name']").val("");
        $("#add_person input[name='mobile']").val("");
        $("#add_person input[name='idNo']").val("");

        var forDialog=$(this).attr("for")
            ,msgbox=$("."+forDialog+" .boxMessage")
            ,boxWidth=msgbox.width()
            ,boxHeight=msgbox.height()
            ;
        $("."+forDialog).show();
        msgbox.css({"margin-left":(documentWidth-boxWidth)/2});
        msgbox.css({"margin-top":(documentHeight-boxHeight)/2});
    });

    $(".boxTitle a").click(function(){
        $(this).closest("[opt='box']").hide();
    });
})

