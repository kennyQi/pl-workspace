$(function () {
	/*预订须知*/
	var $btnYdxz = $('.btn_ydxz');
	var $winBg = $('.win_bg');
	var $ydinfoBox = $('.ydinfo_box');
	var $btnClose = $('.btn_close');
	$btnYdxz.click(function () {
		$winBg.show();
		$ydinfoBox.show();
	});
	$btnClose.click(function () {
		$winBg.show().hide();
		$(this).parent().hide();
	});

	// 刷新价格
	function refreshPrice() {
		if (window.dzpOrderEdit != undefined)
			dzpOrderEdit.refreshPrice();
	}

	/*添加游玩人*/
	var playerItem = '<li class="fl wd playerItem"><i class="icon_delete"></i><span class="tick_tis playerName fl">姓名：</span><input type="text" name="name" class="playerName" rell="name"><span class="tick_tis playerId fl">身份证：</span><input type="text" name="idNo" class="playerId" rell="idNo"><span class="color8 h4 yahei tick_ts">请输入正确的姓名格式和身份证</span></li>';
	$(".btn_addPlayer").click(function () {
		if ($(".playerItem").length >= 5) {
			alert("最多可同时为五位乘客购买!");
			return;
		} else if ($(".playerItem:last-child").find("[rell='name']").val() == ""
			&& $(".playerItem:last-child").find("[rell='idNo']").val() == "") {
			alert("请在空余处添加游玩人!");
			return;
		} else if ($(".playerItem:last-child").find("[rell='name']").val() == ""
			|| $(".playerItem:last-child").find("[rell='idNo']").val() == "") {
			alert("请将游玩人姓名和身份证号填写完整！");
			return;
		} else {
			$('.playerList').append(playerItem);
			refreshPrice();
		}
	});

	/*删除已添加的游玩人*/
	$(document).on("click", ".icon_delete", function () {
		$(this).closest(".playerItem").remove();
		if ($(".playerItem").size() == 0) {
			$('.person_play_form ul').html('<li class="fl wd playerItem"> <i class="icon_delete"></i> <span class="tick_tis playerName fl">姓名：</span> <input type="text" name="name" class="playerName" rell="name"> <span class="tick_tis playerId fl">身份证：</span> <input type="text" name="idNo" class="playerId" rell="idNo"> <span class="color8 h4 yahei tick_ts">请输入正确的姓名格式和身份证</span> </li> ');
		}
		var idno = $(this).siblings('input.playerId').val();
		if (idno) $('.linksList span[idno=' + idno + ']').siblings('i').removeClass('on');
		refreshPrice();
	});

	/*从联系人中选择游玩人*/
	var $btnChoosePlayer = $('.btn_choosePlayer');
	var $winChoosePlayer = $('.win_choosePlayer');
	$btnChoosePlayer.click(function () {
		$('.win_choosePlayer').show();
	});

	//选择通讯录成员
	var isCover = {b: true, rel: ""};
	var personHtml = $(".playerList").html();
	$(document).on("click", ".linksList li i", function () {
		var that = $(this);
		var rel = that.closest("li").find("span").attr("rel");
		var len = $(".playerItem").length;
		var personList = {};

		personList.name = $(this).closest("li").find("span").attr("name");
		personList.idNo = $(this).closest("li").find("span").attr("idNo");
		personList.rel = $(this).closest("li").find("span").attr("rel");

		if (that.hasClass("on")) {

			var idNo = $(this).closest("li").find("span").attr("idNo");
			$(".playerItem").each(function (index) {
				if ($(this).find("[rell='idNo']").val() == idNo) {
					$(this).remove();
				}
			})

			if ($(".playerItem").size() == 0) {
				$('.person_play_form ul').html('<li class="fl wd playerItem"> <i class="icon_delete"></i> <span class="tick_tis playerName fl">姓名：</span> <input type="text" name="name" class="playerName" rell="name"> <span class="tick_tis playerId fl">身份证：</span> <input type="text" name="idNo" class="playerId" rell="idNo"> <span class="color8 h4 yahei tick_ts">请输入正确的姓名格式和身份证</span> </li> ');
			}

			that.removeClass("on");
		} else {
			if (len >= 5) {
				alert("最多可同时为五位乘客购买!");
				return;
			}
			//第一行无数据
			if (($(".playerItem:last-child").find("[rell='name']").attr("relvalue") == undefined
				&& ($(".playerItem:last-child").find("[rell='name']").val() == "")
				&& $(".playerItem:last-child").find("[rell='idNo']").attr("relvalue") == undefined
				&& $(".playerItem:last-child").find("[rell='idNo']").val() == "") ||
				($(".playerItem:last-child").find("[rell='name']").attr("relvalue") != ""
				&& $(".playerItem:last-child").find("[rell='name']").val() == ""
				&& $(".playerItem:last-child").find("[rell='idNo']").attr("relvalue") != ""
				&& $(".playerItem:last-child").find("[rell='idNo']").val() == "")) {
				for (var x in personList) {
					$(".playerItem:last-child").find("[rell='" + x + "']").val(personList[x]).attr("disabled", true);
				}
				$(".playerItem:last-child").find(".icon_delete").attr("rel", personList.rel);
				isCover.b = false;
				isCover.rel = personList.rel;
			} else {
				$(".playerList").append(personHtml);
				for (var x in personList) {
					$(".playerItem").eq(len).find("input[name='" + x + "']").val(personList[x]).attr("disabled", true);
				}
				$(".playerItem").eq(len).find(".icon_delete").attr("rel", personList.rel);
				refreshPrice();
			}
			that.addClass("on");

		}
	});

	// 判断游玩人姓名和身份证号格式
	$(document).on("blur", ".playerName", function () {
		var name = this.value;
		//姓名验证
		var reg = /^[\u4E00-\u9FA5]{0,20}$/;
		if (name.length < 1 || !reg.test(name)) {
			$(this).parent().find('.tick_ts').css('color', '#f00');
		} else {
			$(this).parent().find('.tick_ts').css('color', '#888');
		}
	});

	$(document).on("blur", ".playerId", function () {    //验证身份证号码
		var idCardNo = isIdCardNo(this.value, $(this).parent().find('.tick_ts'));
		if (idCardNo == false) {
			console.log('111');
		} else {
			$(this).parent().find('.tick_ts').css('color', '#888');
		}
	});

	// 提交订单
	$("#tick_btn_tj").click(function () {
		//隐藏域
		var beforToken = $("#yxOrderToken").val();
		var afterToken = sessionStorage.getItem("yxOrderToken");

		if (afterToken != "F") {
			alert("请勿重复提交订单");
			var url = contextPath + "/company/orderManage?sel=5";
			window.location.href = url;
			return;
		}

		//判断同一次下单,身份证，姓名是否重复
		var count = checkNameRepaect();
		if (count > 1) {
			return;
		}

		// 发ajax请求
		// msg----
		if (msg == true) {
			$(".opportunityModel .opportunity").each(function (i, e) {
				$(this).find("input").each(function () {
					if ($(this).attr("name") != "") {
						$(this).attr("name", "passengers[" + i + "]." + $(this).attr("rell"));
						$(this).attr("relvalue", $(this).val());
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
					if (dats == "F1") {
						window.location.href = "" + contextPath + "/yxjp/main";
					} else if (dats == "F2") {
						alert("创建订单失败");
					} else if (dats.indexOf("订单ID") > -1) {
						var ids = dats.split(",");
						var orderId = ids[1];
						window.location.href = "" + contextPath + "/yxjp/pay-order-pag?orderId=" + orderId;
						$(".fly_load").show();
						$(".fly_load .loadbg").height($(window).height());
						$(".fly_load .loadIcon").css({"margin-top": ($(window).height() - $(this).height()) / 2});
					} else {
						alert(dats);
						//window.location.reload();
					}
				}
			})
		}
	});

})

/**********************************************身份证验证规则***************************************************/
function isIdCardNo(num, obj) {

	num = num.toUpperCase();
	//身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。
	if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num))) {
		obj.css('color', '#f00');
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
			obj.css('color', '#f00');
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
			obj.css('color', '#f00');
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
				obj.css('color', '#f00');
				return false;
			}
			return num;
		}
	}
	return false;
}

//判断同一次下单,身份证，姓名是否重复
function checkNameRepaect() {
	var checkValue = 1;
	var currentName = "";
	var nameStr = "";
	var repeat = 0;
	$("#person_play_form input[name='name']").each(function (i, k) {
		var nameCount = 1;
		currentName = $(this).val();
		if (repeat != 1) {
			$("#person_play_form input[name='name']").each(function (j, m) {
				if (currentName == $(m).val()) {
					nameCount++;
					if (nameCount > 2) {
						checkValue = nameCount;
						alert("姓名:【" + currentName + "】重复!!");
						repeat = 1;
						return;
					}
				}
			})
		}
	})

	var currentIdNo = "";
	var idRepeat = 0;
	//判断同一次下单,身份证，姓名是否重复
	$("#person_play_form input[name='idNo']").each(function (i, k) {
		var idNoCount = 1;
		currentIdNo = $(this).val();
		if (idRepeat != 1) {
			$("#person_play_form input[name='idNo']").each(function (j, n) {
				if (currentIdNo == $(n).val()) {
					idNoCount++;
					if (idNoCount > 2) {
						alert("身份证:【" + currentIdNo + "】重复!!");
						checkValue = idNoCount;
						idRepeat = 1;
						return;
					}
				}
			})
		}
	})

	return checkValue;
}

