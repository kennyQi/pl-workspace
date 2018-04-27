$(function(){
	$("#btn_click").click(function(e){
		e.preventDefault();
		var mobile=$("#mobile");
		var name=$("#name");
		var cardNo=$("#cardNo");
		
		var reg = /^[\u4E00-\u9FA5]{0,20}$/;
        if (name.val().length < 1 || !reg.test(name.val())) {
        	$.pop.tips("请输入正确乘客姓名");
        	return ;
        }
        if (mobile.val()) {
            var pattern = /^1[3|4|5|8][0-9]\d{8}$/;
            if (!pattern.test(mobile.val())) {
            	$.pop.tips("请输入正确的手机号");
            	return ;
            } else {

            }
        } else {
        	$.pop.tips("请输入手机号");
        	return ;
        }
        var idCardNo = isIdCardNo(cardNo.val());
        if (idCardNo == false) {
        	$.pop.tips("请输入正确的证件号");
        	return ;
        }
        
       // $(this).closest("form").submit();
        
        $.ajax({
    		cache: true,
    		type: "POST",
    		url:'./addorUpdateLinkMan',
    		data:$('#saveForm').serialize(),
    		async: false,
    	    error: function(request) {
    	        alert("连接失败");
    	    },
    	    success: function(data) {
    	    	var msg = eval("("+data+")");
    		    if(msg.status=="success"){
    		    	location.href="./queryMembers";
    		    }else{
    		    	$.pop.tips(msg.message);
    		    }
    	    }
    	});
	});
})


/**********************************************身份证验证规则***************************************************/
function isIdCardNo(num) {
    num = num.toUpperCase();
    //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。
    if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num))) {
        // alert('输入的身份证号长度不对，或者号码不符合规定');
        //obj.show().html('输入的身份证号长度不对，或者号码不符合规定');
        //obj.show().html('证件号有误');
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
            //obj.show().html('证件号有误');
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
            //obj.show().html('证件号有误');
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

                //obj.show().html('证件号有误末位应该为:' + valnum);
                return false;
            }
            return num;
        }
    }
    return false;
}	