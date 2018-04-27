var personFor = {};
var personNum = 1;
var isCover = {b: true, rel: ""};

//liwei
$(document).ready(function (e) {

    //验证Token
    var nowOrderToken = sessionStorage.getItem("yxOrderToken");
    var contextPath=$("#contextPath").val();
    if(nowOrderToken !="F"){
       alert("重复下单");
       var url = contextPath+"/company/orderManage?sel=5";
       window.location.href=url;
       return;
    }

    var personHtml = $(".opportunityModel").html();

    $(document).on("click", ".radio", function () {   //乘客类型选择
        //$(this).toggleClass('radio_click');
    });
    //下拉框通用
    $(document).on("click", ".companySelect", function () {
        $('.selectNav').hide();
        $(this).next(".selectNav").show();
    });

    $("#cardType").val("NI");
    $(document).on("click", ".selectNav li", function () {//下拉列表选值
        var optionsValue = $(this).html();
        var value = $(this).attr("value");
        var rel = $(this).attr("rel");
        //赋值
        $(this).closest("ul").parent().find("input").val(value);
        var forDom = $(this).parent().attr("for");
        $("#" + forDom).html(optionsValue).attr("rel", rel);
        $(this).parents(".selectNav").css({"display": "none"});
        //给需要提交的隐藏域复制证件类型
        //$("#cardType").val($(this).attr("name"));
    });
    $("body").bind("click", function (evt) {     //点击空白处隐藏
        if ($(evt.target).parents("ul > li").length == 0) {
            $('.selectNav').hide();
        }
    });


    $(document).on("click", ".close", function () {
        $(".companyEmployee").hide();
    });

    //通讯录选择
    $(document).on("click", ".addCotanct", function () {
        var top = $(this).offset().top;
        $(".companyEmployee").css("top", top - 150).fadeIn(300);
        personFor = $(this).closest(".opportunity");
    });

    //选择通讯录成员
    $(document).on("click", ".com_list li i", function () {
        var that = $(this);
        var rel = that.closest("li").find("span").attr("rel");
        $(".person_fly .opportunity").find("span.ts").html("");
        if (that.hasClass("on")) {
            //删除
            if(personNum>1){
                personNum--;
                $(".personDel[rel='" + rel + "']").closest(".opportunity").remove();
                $("#h-totalPrice b").html("￥" + personNum * parseInt($("#totalPrice").val()));
                $("[name='flightPriceInfoDTO.payAmount']").val(personNum * parseInt($("#totalPrice").val()));
                $(".person_fly .opportunity").eq(0).find(".del").hide();
            }else{
                $(".person_fly .opportunity").eq(0).find("input").val("").attr("disabled", false);
                //把证件类型添加默认的值
                $(".person_fly .opportunity").eq(0).find("[name='idType']").val("1");
                $(".person_fly .opportunity").eq(0).find("[name='type']").val("1");
            }
            that.removeClass("on");
        } else {
            //添加
            var length = $(".person_fly .opportunity").length;
            var personList = {};

            if (length >= 5) {
                alert("最多可同时为五位乘客购买!");
                return;
            }
            personList.name = $(this).closest("li").find("span").attr("name");
            personList.idNo = $(this).closest("li").find("span").attr("idNo");
            personList.mobile = $(this).closest("li").find("span").attr("mobile");
            personList.rel = $(this).closest("li").find("span").attr("rel");
            //第一行无数据
            if (($(".person_fly .opportunity").eq(0).find("[rell='name']").attr("relvalue") == undefined
                &&($(".person_fly .opportunity").eq(0).find("[rell='name']").val() == "")
                && $(".person_fly .opportunity").eq(0).find("[rell='idNo']").attr("relvalue") == undefined
                && $(".person_fly .opportunity").eq(0).find("[rell='idNo']").val() == ""
                && $(".person_fly .opportunity").eq(0).find("[rell='mobile']").attr("relvalue") == undefined
                && $(".person_fly .opportunity").eq(0).find("[rell='mobile']").val() == "")||
                ($(".person_fly .opportunity").eq(0).find("[rell='name']").attr("relvalue") !=""
                &&$(".person_fly .opportunity").eq(0).find("[rell='name']").val() ==""
                && $(".person_fly .opportunity").eq(0).find("[rell='idNo']").attr("relvalue") !=""
                && $(".person_fly .opportunity").eq(0).find("[rell='idNo']").val() ==""
                && $(".person_fly .opportunity").eq(0).find("[rell='mobile']").attr("relvalue") !=""
                && $(".person_fly .opportunity").eq(0).find("[rell='mobile']").val() =="")) {
                for (var x in personList) {
                    $(".person_fly .opportunity").eq(0).find("[rell='" + x + "']").val(personList[x]).attr("disabled", true);
                }
                $(".person_fly .opportunity").eq(0).find(".del").attr("rel", personList.rel);
                isCover.b = false;
                isCover.rel = personList.rel;
            } else {
                $(".person_fly").append(personHtml);
                for (var x in personList) {
                    $(".person_fly .opportunity").eq(length).find("input[name='" + x + "']").val(personList[x]).attr("disabled", true);
                }
                $(".person_fly .opportunity").eq(length).find(".del").attr("rel", personList.rel);
                personNum++;
                $("[name='flightPriceInfoDTO.payAmount']").val(personNum * parseInt($("#totalPrice").val()));
                $("#h-totalPrice b").html("￥" + personNum * parseInt($("#totalPrice").val()));
            }
            that.addClass("on");

        }
    });

    var contextPath = $("#contextPath").val();
    /*****************************************员工信息选择**********************************************/
    var company = {                              //ajax获取数据
        method: "post",
        url: "" + contextPath + "/yxjp/companys",
        async: false,
        dataType: "script",
        success: function (data) {
            var list = eval(data);
            $.each(list, function (i, bt) {
                var html = "<li class='l' rel=" + bt.id + ">" + bt.companyName + "</li>";
                $(".com1").append(html);
            })
        }
    };
    $.ajax(company);
    $(document).on("click", ".com1 li", function () {    //获取公司
        $(".employeeMessBox").html("");
        $("#optionValue3").html("请选择部门");
        $("#optionValue4").html("请选择人员");
        $(".com2 li").remove();
        $(".com3 li").remove();
        coms = $(this).attr('rel');
        $.ajax({
            url: "" + contextPath + "/yxjp/depMents?companyId=" + coms + "",
            dataType: "script",
            success: function (data) {
                var list = eval(data);
                if (list.length <= 0) {
                    $("#optionValue3").html("无更多部门");
                    return;
                }
                $.each(list, function (i, bt) {
                    var html = "<li class='l' rel=" + bt.id + ">" + bt.deptName + "</li>";
                    $(".com2").append(html);
                })
            }
        });
    });

    //**************获取部门**************
    $(document).on("click", ".com2 li", function () {
        $(".employeeMessBox").html("");
        $("#optionValue4").html("请选择人员");
        $(".com3 li").remove();
        pers = $(this).attr('rel');
        $.ajax({
            url: "" + contextPath + "/yxjp/members?depmentId=" + pers + "",
            dataType: "script",
            success: function (data) {
                var list = eval(data);
                if (list.length <= 0) {
                    $("#optionValue4").html("无更多人员");
                    return;
                }
                $.each(list, function (i, bt) {
                    var html = "<li class='l' rel=" + bt.id + ">" + bt.name + "</li>";
                    $(".com3").append(html);
                })

            }
        });
    });
    var me1 = ""
        , me2 = ""
        , me3 = ""
        , me4 = ""
        , me5 = ""
        , me6 = ""
        , me7 = ""
        , me8 = "";

    //***********获取个人信息***********
    $(document).on("click", ".com3 li", function () {
        depa = $(this).attr('rel');
        $(".employeeMessBox li").remove();
        $.ajax({
            url: "" + contextPath + "/jp/getMembersInfomation?memberId=" + depa + "",
            dataType: "script",
            success: function (data) {
                var list = eval(data);
                me1 = $("#optionValue2").html();   //公司名称
                me1_id = $("#optionValue2").attr("rel")//公司id
                me2 = $("#optionValue3").html();    //部门名称
                me2_id = $("#optionValue3").attr("rel");//部门id
                me3 = $("#optionValue4").html();     //员工姓名
                me4 = "身份证";    //证件类型
                me5 = list[0].certificateID;    //身份证号码
                me6 = list[0].mobilePhone;    //手机号码
                me8 = list[0].id;
                me3_id = $("#optionValue4").attr("rel");
                messHtml = '<li><i>企业信息：</i><span>' + me1 + '</span><span>' + me2 + '</span></li><li><i>姓名：</i><span>' + me3 + '</span></li><li><i>证件信息：</i><span>' + me4 + '</span><span>' + me5 + '</span></li><li><i>手机号码：</i><span>' + me6 + '</span></li>';

                $(".employeeMessBox").append(messHtml);
            }
        });
    });

    //************添加企业员工,点击确认提交************
    $(".com_tj").click(function(){

        var name = $(".name").val();
        if(name != ""){
            var addPersonHtm = "<ul class='opportunity fl h-opportunity-more'>" +
                "<li class='type'><span type='text' class='texta select yahei h3 color6 companySelect' id='optionValue2'  value='身份证'>成人</span>" +
                "<ul class='selectNav cardIdSelect' for='optionValue2'><li class='l NI'  value='0'>成人</li></ul></li>" +
                "<li><span class='cla yahei h3 color6 fl'>姓名:</span><input type='text' class='texta yahei h3 color6 name yanz' name='name' value='"+me3+"' disabled='disabled' rell='name'>" +
                "<span class='fr h4 ts' style='color:#FF0004; line-height:30px; margin-left:10px; display:none' id='name'></span></li>" +
                "<li><span type='text' class='texta select yahei h3 color6 companySelect' id='optionValue1'  value='身份证'>身份证</span>" +
                "<ul class='selectNav cardIdSelect' for='optionValue1'><li class='l NI'  value='1'>身份证</li></ul>" +
                "<input type='hidden' name='idType' value='0' /><input type='hidden' name='companyMemberId' rell='companyMemberId' value='"+me8+"'/></li>" +
                "<li class='cardNum'><input type='text' class='texta yahei h3 color6 idNum  yanz' name='idNo' placeholder='证件号' value='"+me5+"' disabled='disabled' rell='idNo'>" +
                "<input type='hidden' name='idType' value='1' rell='idType'><input type='hidden' name='type' value='1' rell='type'>"+
                "<span class='fr h4 ts' style='color:#FF0004; line-height:30px; margin-left:10px; display:none' id='idcardv'></span></li>" +
                "<li class='fly_mobile'><span class='cla yahei h3 color6 fl'>手机:</span>" +
                "<input type='text' class='texta yahei h3 color6 mobileNum mobile  yanz' maxlength='11'name='mobile' value='"+me6+"' disabled='disabled' rell='mobile'>" +
                "<span class='fr h4 ts' style='color:#FF0004; line-height:30px; margin-left:10px; display:none'></span></li>" +
                "<li class='deleteBtn'><a href='javascript:' class='personDel fl tc color6 h3 del'>删除</a></li></ul>";

            $("#tickerform  .opportunityModel").append(addPersonHtm);
            $(".companyEmployee").hide();
        }else{
            $(".name").attr("disabled", true);
            $(".name").val(me3);    //姓名
            $("#optionValue1").html(me4);   //证件类型
            $(".idNum").val(me5);   //证件号码
            $(".idNum").attr("disabled", true);
            $(".mobileNum").val(me6);    //手机号码
            $(".mobileNum").attr("disabled", true);
            $("#linkMobile").val(me6);//给手机号码赋值
            $(".companyEmployee").hide();
            $("input[name='companyMemberId']").val(me8);
        }
    });

    $(".personDel").eq(0).hide();

    /*****************************************员工信息选择**********************************************/
    /**********************************************表单验证***************************************************/
//	$('input').val(null);   //清空所有表单内容    暂时 注释
    var pattern = null;

    $(document).on("blur", ".mobile", function () {    //验证手机号码
        if (this.value) {
            pattern = /^1[3|4|5|8][0-9]\d{8}$/;
            if (!pattern.test(this.value)) {
                $(this).siblings(".ts").show().html('请填写正确的手机号码');
                $(this).addClass("ban");
            } else {
                $(this).siblings(".ts").hide();
                $(this).removeClass("ban");
            }
        } else {
            $(this).siblings(".ts").show().html('请填写手机号码');
            $(this).addClass("ban");
        }

    });

    /**********************************************验证身份证号码***************************************************/

    $(document).on("blur", ".idNum", function () {    //验证身份证号码
        var idCardNo = isIdCardNo(this.value, $(this).siblings(".ts"));
        if (idCardNo == false) {
            $(this).addClass("ban");
        } else {
            $(this).removeClass("ban");
            $(this).siblings(".ts").hide();
        }
    });
    /**********************************************验证姓名***************************************************/
    $(document).on("blur", ".name", function () {    //验证姓名
        var name = this.value;
        //姓名验证
        var reg = /^[\u4E00-\u9FA5]{0,20}$/;
        if (name.length < 1 || !reg.test(name)) {
            $(this).siblings(".ts").show().html('请输入正确乘客姓名');
            $(this).addClass("ban");
        } else {
            $(this).siblings(".ts").hide();
            $(this).removeClass("ban");
        }
    });
    /**********************************************判断是企业用户还是个人***************************************************/
    var usertype = $("#usertype").val();//1个人  2企业
    if (usertype == "1") {
        $(".companyLink").hide();
    }
    /**********************************************提交表单***************************************************/
    $("#createOrder").click(function () {

        var beforToken = $("#yxOrderToken").val();
        var afterToken = sessionStorage.getItem("yxOrderToken");
        if(afterToken != "F"){
            alert("请勿重复提交订单");
            var url = contextPath+"/company/orderManage?sel=5";
            window.location.href=url;
            return;
        }

        //判断同一次下单,身份证，姓名是否重复
        var count = checkNameRepaect();
        if(count >1){
            return;
        }

        $(".idNum").blur();
        $(".mobile").blur();
        $(".name").blur();
        //调用所有的验证方法
        var msg = true;
        var idResult = true;
        $(".yanz").each(function () {
            var ban = $(this).hasClass("ban");
            //ban存在返回true
            if (ban == true) {
                msg = false;
            }
        })
        if (msg == true) {
            $(".opportunityModel .opportunity").each(function (i, e) {
                $(this).find("input").each(function () {
                    if ($(this).attr("name") != "") {
                        $(this).attr("name", "passengers[" + i + "]." + $(this).attr("rell"));
                        $(this).attr("relvalue",$(this).val());
                        $(this).removeAttr("disabled");
                    }
                });
            });
            $.ajax({
                url: "" + contextPath + "/yxjp/create-jporder",
                type: "POST",
                data: $("#tickerform").serialize(),
                success: function (data) {
                    var dats = eval(data);
                    if(dats == "F1"){
                        window.location.href="" + contextPath + "/yxjp/main";
                    }else if(dats == "F2"){
                        alert("创建订单失败");
                    }else if(dats.indexOf("订单ID")>-1){
                        var ids = dats.split(",");
                        var orderId = ids[1];
                        window.location.href="" + contextPath + "/yxjp/pay-order-pag?orderId="+orderId;
                        $(".fly_load").show();
                        $(".fly_load .loadbg").height($(window).height());
                        $(".fly_load .loadIcon").css({"margin-top": ($(window).height() - $(this).height()) / 2});
                    }else{
                        alert(dats);
                        //window.location.reload();
                    }
                }
            })
        }

    });

    //判断同一次下单,身份证，姓名是否重复
    function checkNameRepaect(){
        var checkValue= 1;
        var currentName = "";
        var nameStr = "";
        var repeat = 0;
        $("#tickerform input[name='name']").each(function(i,k){
            var nameCount = 1;
            currentName = $(this).val();
            if(repeat !=1 ){
                $("#tickerform input[name='name']").each(function(j,m){
                    if(currentName == $(m).val()){
                        nameCount++;
                        if(nameCount >2){
                            checkValue = nameCount;
                            alert("姓名:【"+currentName+"】重复!!");
                            repeat =1;
                            return;
                        }
                    }
                })
            }
        })

        var currentIdNo = "";
        var idRepeat = 0;
        //判断同一次下单,身份证，姓名是否重复
        $("#tickerform input[name='idNo']").each(function(i,k){
            var idNoCount = 1;
            currentIdNo = $(this).val();
            if(idRepeat !=1 ){
                $("#tickerform input[name='idNo']").each(function(j,n){
                    if(currentIdNo == $(n).val()){
                        idNoCount++;
                        if(idNoCount >2){
                            alert("身份证:【"+currentIdNo+"】重复!!");
                            checkValue = idNoCount;
                            idRepeat =1;
                            return;
                        }
                    }
                })
            }
        })

        return checkValue;
    }
    //添加乘客
    $(".addFlyMenber").click(function () {
        if ($(".opportunity").length > 5) {
            alert("最多可同时为五位乘客购买!");
            return;
        }
        var resultHtml = "";
        personNum++;
        resultHtml = personHtml.replace(/\[0\]/g, "[" + (personNum - 1) + "]");
        $(".opportunityModel").append(resultHtml);
        $("#h-totalPrice b").html("￥" + personNum * parseInt($("#totalPrice").val()));
        $("[name='flightPriceInfoDTO.payAmount']").val(personNum * parseInt($("#totalPrice").val()));
    });

    //删除乘客
    $(document).on("click", ".personDel", function () {
        if ($(".opportunity").length > 1) {
            $(this).closest(".opportunity").remove();
            personNum--;
            $("#h-totalPrice b").html("￥" + personNum * parseInt($("#totalPrice").val()));
            $("[name='flightPriceInfoDTO.payAmount']").val(personNum * parseInt($("#totalPrice").val()));
            var rel = $(this).attr("rel");
            $(".com_list span[rel='" + rel + "']").closest("li").find("i").removeClass("on");
        }

    });

});

/**********************************************身份证验证规则***************************************************/
function isIdCardNo(num, obj) {

    num = num.toUpperCase();
    //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。
    if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num))) {
        // alert('输入的身份证号长度不对，或者号码不符合规定');
        //obj.show().html('输入的身份证号长度不对，或者号码不符合规定');
        obj.show().html('证件号有误');
        return false;
    }
    //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
    //下面分别分析出生日期和校验位
    var len, re;
    len = num.length; //身份证号的长度
    if (len == 15) {
        re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
        var arrSplit = num.match(re);

        //检查生日日期是否正确
        var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/' + arrSplit[4]);
        var bGoodDay;
        bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
        if (!bGoodDay) {
            //obj.show().html('输入的身份证号里出生日期不对');
            obj.show().html('证件号有误');
            return false;
        } else {
            //将15位身份证转成18位
            //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
            var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
            var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
            var nTemp = 0, i;
            num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);
            for (i = 0; i < 17; i++) {
                nTemp += num.substr(i, 1) * arrInt[i];
            }
            num += arrCh[nTemp % 11];
            return num;
        }
    }

    if (len == 18) {
        re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
        var arrSplit = num.match(re);

        //检查生日日期是否正确
        var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/" + arrSplit[4]);
        var bGoodDay;
        bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
        if (!bGoodDay) {
            //obj.show().html('输入的身份证号里出生日期不对');
            obj.show().html('证件号有误');
            return false;
        } else {
            //检验18位身份证的校验码是否正确。
            //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
            var valnum;
            var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
            var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
            var nTemp = 0, i;
            for (i = 0; i < 17; i++) {
                nTemp += num.substr(i, 1) * arrInt[i];
            }
            valnum = arrCh[nTemp % 11];
            if (valnum != num.substr(17, 1)) {
                //obj.show().html('身份证号的末位应该为：' + valnum);

                obj.show().html('证件号有误末位应该为:' + valnum);
                return false;
            }
            return num;
        }
    }
    return false;
}

