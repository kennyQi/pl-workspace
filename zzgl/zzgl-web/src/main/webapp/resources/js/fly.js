$(function(){
	var test=new VcitySmall.CitySelector({input:'citySelect_from'});
	var test1=new VcitySmall.CitySelector({input:'citySelect_to'});
	var test2=new VcitySmall.CitySelector({input:'citys'});
	var citysTimes=0;
	$("#citys").click(function(){
		if(citysTimes==0){
			$(".citySelector").each(function(i,e){
				if($(this).find("#cityBox").hasClass("hide")) return;
				$(this).css("left",parseInt($(this).css("left"))-217);
			});
			citysTimes++;
		}

	});

	var from=$("#citySelect_from")
	,to=$("#citySelect_to")
	,time=$("#search_time")
	,model_time=new Date()
	,timeValue=ply.GetQueryString("startDate")||(model_time.getFullYear()+"-"+timeGeshi(model_time.getMonth()+1)+"-"+timeGeshi(model_time.getDate()))
	,beforeTime=new Date(timeValue)
	,afterTime=new Date(timeValue)
	,clock=ply.GetQueryString("clock")||"起飞时间"
	,company=ply.GetQueryString("airCompany")||"航空公司"
	;

	beforeTime.setDate(beforeTime.getDate()-1);
	afterTime.setDate(afterTime.getDate()+1);

	var beforeDay=beforeTime.getFullYear()+"-"+timeGeshi(beforeTime.getMonth()+1)+"-"+timeGeshi(beforeTime.getDate())
		,afterDay=afterTime.getFullYear()+"-"+timeGeshi(afterTime.getMonth()+1)+"-"+timeGeshi(afterTime.getDate())

	//获取url
	from.val(decodeURI(ply.GetQueryString("orgCity")||""));
	to.val(decodeURI(ply.GetQueryString("dstCity"))||"");
	time.attr("value",timeValue);
	$("#day_rel").attr("day",timeValue).attr("loadDay",timeValue);
	$(".m_html_sel").eq(0).attr("value",decodeURI(clock)).find("label").html(decodeURI(clock));
	$(".m_html_sel").eq(1).attr("value",decodeURI(company)).find("label").html(decodeURI(company));

	//搜索
	$("#search").click(function(){
		if(from.val()==""||from.val()=="城市名"){
			from.css("border","1px solid red");
			setTimeout(function(){
				from.css("border","");
			},1500);
			return;
		}
		if(to.val()==""||to.val()=="城市名"){
			to.css("border","1px solid red");
			setTimeout(function(){
				to.css("border","");
			},1500);
			return;
		}
		if(time.val()==""){
			time.css("border","1px solid red");
			setTimeout(function(){
				time.css("border","");
			},1500);
			return;
		}else{
			var seacrTime=new Date().setFullYear(time.val().split("-")[0],time.val().split("-")[1]-1,time.val().split("-")[2]);
			if(seacrTime-new Date().getTime()<0){
				time.css("border","1px solid red");
				setTimeout(function(){
					time.css("border","");
				},1500);
				return;
			}
		}
		submitSearch();
	});

	//日历部分点击
	$("#chooseDay li").click(function(){
		if($(this).attr("class")!="off"){
			time.val($(this).attr("rel"));
			$("#search").click();
			$("#chooseDay li").find("a").removeClass("on");
			$(this).find("a").addClass("on");
		}
	});
	$("#day_before").click(function(){
		if($("#day_rel").attr("rel")!="0"){
			var time=$("#day_rel").attr("day");
			creatDate("before",time);
		}
	});
	$("#day_after").click(function(){
		var time=$("#day_rel").attr("day");
		creatDate("after",time);

	});
	//初始化*********************
	(function(){
		$("#day_after").click();
		var loadTime=$("#day_rel").attr("loadDay").split("-");
		var nowDate=new Date();
		var newDate=new Date();
		var crossTime=Math.ceil(parseInt((newDate.setFullYear(loadTime[0],loadTime[1]-1,loadTime[2])-nowDate.getTime())/1000/3600/24)/7);
		$("#day_rel").attr("rel",crossTime);
	})();

	//条件下拉框*****************
	$(".m_html_sel").click(function(){
		$(this).find(".op").toggle();
		$(this).find(".icon").toggle();
	});
	$(".m_html_sel").mouseleave(function(){
		var that=$(this);
		setTimeout(function(){
			that.find(".op").hide();
			that.find(".icon").show();
		},200);
	});

	$(".m_html_sel .op span").click(function(){
		$(this).closest(".m_html_sel").attr("value", $(this).attr("value")).find("label").html($(this).html());
		$("#search").click();
	});


	//机票详情查看*************
	$(document).on("click",".look_detail",function(){
		$(this).closest(".fly_box").find(".ticket").toggle().siblings().find(".xiab").toggle();
	});
	$(document).on("click",".ticket .toggle",function(){
		$(this).closest(".ticket").hide().closest(".fly_box").find(".xiab").hide();
	});

	//页面加载 提交ajax
	sendAjax();

	//底部翻页
	$("#nextDay").attr("value",afterDay).html("后一天航班("+afterDay.split("-")[1]+"-"+afterDay.split("-")[2]+")>>");
	$("#preDay").attr("value",beforeDay).html("<<前一天航班("+beforeDay.split("-")[1]+"-"+beforeDay.split("-")[2]+")");
	$("#nextDay").click(function(){
		time.attr("value",$(this).attr("value"));
		$("#search").click();
	});
	$("#preDay").click(function(){
		time.attr("value",$(this).attr("value"));
		$("#search").click();
	});

	//更换出发城市****************************
	$("#changeCity").click(function(){
		var v1=from.val();
		from.val(to.val());
		to.val(v1);
	});
	//热门城市搜索****************
	$(".search").click(function(){
		if($("#citys").val()==""){
			alert("请输入热门城市名称")
		}else{
			$("#searchMp").submit();
		}
	})

	//弹出层
	function alertShow(){
		$(".fly_loadError").show();
		$(".fly_loadError .loadbg").height($(window).height());
		$(".fly_loadError .loadIcon").css({"margin-top":($(window).height()-$(".fly_loadError .loadIcon").height())/2});
	}
	//关闭弹出层
	$(".fly_loadError").click(function(){
		$(this).hide();
	})
	

	//弹出层
	function alertWait(){
		$(".fly_loadWait").show();
		$(".fly_loadWait .loadbg").height($(window).height());
		$(".fly_loadWait .loadIcon").css({"margin-top":($(window).height()-$(".fly_loadError .loadIcon").height())/2});
	}

	//机票时间选择

	$("#search_time").click(function(){
		$(".cityBox").addClass("hide");
	});
	
	$(document).on("click",".ticket .sub",function(e){
		var that=$(this);
		alertWait();
		$.ajax({
			type:"get",
			url:that.attr("ref"),
			data:{
				encryptString:that.attr("encry")
			},
			dataType:"json"
		}).done(function(d){
			if(!d.error&&d.flightPolicy.pricesGNDTO!=null){
				//可以预定
				that.closest("li").find("[name='encryptString']").val(d.flightPolicy.pricesGNDTO.encryptString);
				that.closest("li").find("[name='buildFee']").val(d.flightPolicy.buildFee);
				that.closest("li").find("[name='oilFee']").val(d.flightPolicy.oilFee);
				that.closest("li").find("[name='singlePlatTotalPrice']").val(d.flightPolicy.pricesGNDTO.singlePlatTotalPrice);
				that.closest("li").find("[name='ibePrice']").val(d.flightPolicy.pricesGNDTO.ibePrice);
				that.closest("li").find("form").submit();
				
			}else{
				$(".fly_loadWait").hide();
				alertShow();
			}
		});
	})
});

//提交表单
function submitSearch(){
	var from=$("#citySelect_from").val(),
	to=$("#citySelect_to").val(),
	time=$("#search_time").val(),
	clock=$(".m_html_sel").eq(0).attr("value")||"",
	company=$(".m_html_sel").eq(1).attr("value")||""
	;
	location.href=encodeURI("?orgCity="+from+"&dstCity="+to+"&startDate="+time+"&clock="+clock+"&airCompany="+company);
}

//生成日历
function creatDate(type,time){

	var dayHidden=$("#day_rel");
	var dayCh=["周日","周一","周二","周三","周四","周五","周六"],Arr={};
	var nowTime=new Date();
	var newDay=time.split("-");
	nowTime.setFullYear(newDay[0], newDay[1]-1, newDay[2]);
	var year=nowTime.getFullYear(),month=nowTime.getMonth(),day=nowTime.getDate();
	if(type=="after"||type==null){
		for(var i=0;i<7;i++) {
			var b = new Date();
			b.setFullYear(year, month, day + i);
			Arr = {y: b.getFullYear(), m: (b.getMonth() + 1), d: b.getDate(), z: b.getDay()};
			$("#chooseDay li").eq(i).attr("rel",Arr.y+"-"+(Arr.m=Arr.m<10?"0"+Arr.m:Arr.m)+"-"+(Arr.d=Arr.d<10?"0"+Arr.d:Arr.d)).find("a").html(Arr.m+"-"+Arr.d+"&nbsp;"+dayCh[Arr.z]);
		}
		dayHidden.attr("day",Arr.y+"-"+Arr.m+"-"+(parseInt(Arr.d)+1)).attr("rel",parseInt(dayHidden.attr("rel"))+1);
	}else{
		for(var w=0;w<7;w++) {
			var t = new Date();
			var t2=new Date();
			t.setFullYear(year, month, day-w-8);
			Arr = {y: t.getFullYear(), m: (t.getMonth() + 1), d: t.getDate(), z: t.getDay()};
			$("#chooseDay li").eq(6-w).attr("rel",Arr.y+"-"+(Arr.m=Arr.m<10?"0"+Arr.m:Arr.m)+"-"+(Arr.d=Arr.d<10?"0"+Arr.d:Arr.d)).find("a").html(Arr.m+"-"+Arr.d+"&nbsp;"+dayCh[Arr.z]);
			if(t.getTime()<t2.getTime()){
				$("#chooseDay li").eq(6-w).addClass("off");
			}
		}
		dayHidden.attr("day",Arr.y+"-"+Arr.m+"-"+(parseInt(Arr.d)+7)).attr("rel",parseInt(dayHidden.attr("rel"))-1);
	}
	$("#chooseDay a").removeClass("on");
	$("#chooseDay").find("[rel='"+$("#day_rel").attr("loadDay")+"'] a").addClass("on");
}

//提交ajax
function sendAjax(){
	var contextPath=$("#contextPath").val();
	var from=encodeURI(encodeURI($("#citySelect_from").val())),
	to=encodeURI(encodeURI($("#citySelect_to").val())),
	time=$("#search_time").val();
	var  clock=$(".m_html_sel").eq(0).attr("value")||"";
	var company=$(".m_html_sel").eq(1).attr("value")||"";
	if(clock!="全部"){
		clock=encodeURI(encodeURI($(".m_html_sel").eq(0).attr("value")))||"";
	}else{
		clock="";
	}
	if(company!="全部"&&company!="航空公司"){
		company=encodeURI(encodeURI($(".m_html_sel").eq(1).attr("value")))||"";
	}else{
		company="";
	}
	var url="../jp/ticketList?orgCity="+from+"&dstCity="+to+"&startDate="+time+"&clock="+clock+"&airCompany="+company;

	if(from==""||to==""){
		$(".fly_none").show();
		return;
	}
	var jpurl='';
	var temp='<div class="found"><span class="company pl">' +
	'<label class="fly_bg fl icon fly_{airComp}"></label> ' +
	'<label class="i fl">{airCompanyName}<b>{flightno}</b></label> ' +
	'<label class="c fl">机型：{planeType}</label> </span> ' +
	'<span class="time"> <label class="i">{startTime}</label> <label class="c">{orgCityName}{departTerm}</label></span> ' +
	'<span class="cross"><label class="t fl">{stopNumber}</label>' +
	' <label class="Bg icon fl"></label> ' +
	'<span class="tim pl fl"> <label class="z Bg pa"></label><label class="x">行程时间</label> <label class="ti">{flyTime}</label> </span></span> ' +
	'<span class="time"> <label class="i">{endTime}</label>' +
	'<label class="c">{dstCityName}{arrivalTerm}</label> </span> ' +
	'<span class="other">机建+燃油<br/>￥{buildFeeOrOilFee}</span> ' +
	'<span class="price pl"> <label class="i"> <i>¥</i>&nbsp;{minPic}&nbsp;&nbsp;<e>起</e></label>' +
	'<label class="look_detail curp"></label> <i class="xiab Bg pa"></i> </span> ' +
	'</div>';
	//form=encodeURI(from);
	//to=encodeURI(to);
	var temp2='<li>' +
	' <span class="type Bg {flyCode}"></span> ' +
	'<span class="name">{cabinName}</span> ' +
	'<span class="off">{cabinDiscount}折</span>' +
	'<span class="tips">退改签</span> ' +
	'<span class="tipsUnder">{cabinCode}</span> ' +
	'<span class="price"><i>¥</i>{ibePrice}</span> ' +
	'<a ref="../jp/price" encry="{encryptString}"'+
	'href="javascript:"'+
	'" class="sub"></a> ' +
	'<form action="../jp/scart" method="post" style="display:none;">'+
	'<input type="hidden" name="orgCity" value="'+decodeURI(decodeURI(from))+'">'+
	'<input type="hidden" name="dstCity" value="'+decodeURI(decodeURI(to))+'">'+
	'<input type="hidden" name="airComp" value="{airComp}">'+
	'<input type="hidden" name="airCompanyName" value="{airCompanyName}">'+
	'<input type="hidden" name="flightno" value="{flightno}">'+
	'<input type="hidden" name="planeType" value="{planeType}">'+
	'<input type="hidden" name="startTime" value="{startTime}">'+
	'<input type="hidden" name="orgCityName" value="{orgCityName}">'+
	'<input type="hidden" name="departTerm" value="{departTerm}">'+
	'<input type="hidden" name="stopNumber" value="{stopNumber}">'+
	'<input type="hidden" name="flyTime" value="{flyTime}">'+
	'<input type="hidden" name="endTime" value="{endTime}">'+
	'<input type="hidden" name="dstCityName" value="{dstCityName}">'+
	'<input type="hidden" name="arrivalTerm" value="{arrivalTerm}">'+
	'<input type="hidden" name="buildFeeOrOilFee" value="{buildFeeOrOilFee}">'+
	'<input type="hidden" name="cabinNamecode" value="{cabinNamecode}">'+
	'<input type="hidden" name="totalPrice" value="{totalPrice}">'+
	'<input type="hidden" name="cabinDiscount" value="{cabinDiscount}">'+
	'<input type="hidden" name="startDate" value="{startDate}">'+
	'<input type="hidden" name="endDate" value="{endDate}">'+
	'<input type="hidden" name="cabinCode" value="{cabinCode}">'+
	'<input type="hidden" name="stopNumbers" value="{stopNumbers}">'+
	'<input type="hidden" name="flightencryptString" value="{encryptString}">'+
	'<input type="hidden" name="encryptString">'+
	'<input type="hidden" name="buildFee" >'+
	'<input type="hidden" name="oilFee">'+
	'<input type="hidden" name="singlePlatTotalPrice">'+
	'<input type="hidden" name="ibePrice">'+
	'</form>'+
	'</li>';
	$(".fly_load").show();
	$("#ticket_list").show();
	$.ajax({
		//发送请求查询
		type:"post",
		url : url,
		dataType : "json",
		success : function(data) {

			var buildFeeOrOilFee=data.buildFeeOrOilFee;
			var oilFee=data.oilFee;
			var buildFee=data.buildFee;
			if(buildFeeOrOilFee=="0"){
				buildFeeOrOilFee="50";
			}
			data=data.list;
			if(data==undefined||data.length<1){
				$(".fly_none").show();
				$(".fly_load").hide();
			}else{
				var dataHb,dataTick,resultHtml,resultHtmlTick,allHtml="",html="";
				if(data!=undefined){
					for(var i=0;i<data.length;i++){
						dataHb=data[i];
						html='<div class="fly_box ov">';
						dataHb.arrivalTerm=dataHb.arrivalTerm=="--"?"T1":dataHb.arrivalTerm;
						dataHb.departTerm=dataHb.departTerm=="--"?"T1":dataHb.departTerm;
						dataHb.buildFeeOrOilFee=buildFeeOrOilFee;
						dataHb.flyTime=parseInt((parseInt(dataHb.endTime)-parseInt(dataHb.startTime))/1000/60);
						dataHb.flyTime=dataHb.flyTime<=60?dataHb.flyTime:(Math.floor(dataHb.flyTime/60)+"小时"+dataHb.flyTime%60+"分钟");
						dataHb.stopNumbers=dataHb.stopNumber;
						dataHb.stopNumber=dataHb.stopNumber==0?"直飞":("中转"+dataHb.stopNumber+"次");
						var startTime=new Date(),endTime=new Date();
						startTime.setTime(dataHb.startTime);
						endTime.setTime(dataHb.endTime);
						dataHb.startTime=timeGeshi(startTime.getHours())+":"+timeGeshi(startTime.getMinutes())
						dataHb.endTime=timeGeshi(endTime.getHours())+":"+timeGeshi(endTime.getMinutes());
						resultHtml=ply.stringReplace_com(dataHb,temp);
						dataTick=data[i].cabinList;
						for(var q=0;q<dataTick.length;q++){
							dataTick[q].flightNo=dataHb.flightNo;
							dataTick[q].startDate=dataHb.startDate;
							if(dataTick[q].cabinName=="经济舱"){
								dataTick[q].flyCode="jj";
							}else if(dataTick[q].cabinName=="商务舱"){
								dataTick[q].flyCode="sw";
							}else{
								dataTick[q].flyCode="td";
							}
							//把航班信息赋值给 机票信息
							dataTick[q].flightno=dataHb.flightno;
							dataTick[q].airComp=dataHb.airComp;
							dataTick[q].airCompName=dataHb.airCompName;
							dataTick[q].planeType=dataHb.planeType;
							dataTick[q].startTime=dataHb.startTime;
							dataTick[q].departTerm=dataHb.departTerm;
							dataTick[q].endTime=dataHb.endTime;
							dataTick[q].arrivalTerm=dataHb.arrivalTerm;
							dataTick[q].buildFeeOrOilFee=parseFloat(dataHb.buildFeeOrOilFee)+"";
							dataTick[q].buildFee=parseFloat(buildFee)+"";
							dataTick[q].oilFee=parseFloat(oilFee)+"";
							dataTick[q].airCompanyName=dataHb.airCompanyName;
							dataTick[q].totalPrice=parseFloat(buildFeeOrOilFee)+parseFloat(dataTick[q].ibePrice);
							dataTick[q].cabinCode=dataTick[q].cabinCode;
							dataTick[q].stopNumbers=dataHb.stopNumbers;
							//中文转码
							dataTick[q].cabinNamecode=dataTick[q].cabinName;
							dataTick[q].orgCityName=dataHb.orgCityName;
							dataTick[q].stopNumber=dataHb.stopNumber;
							dataTick[q].flyTime=dataHb.flyTime;
							dataTick[q].dstCityName=dataHb.dstCityName;
							dataTick[q].startDate=getTime(new Date(startTime));
							dataTick[q].endDate=getTime(new Date(endTime));
						}
						resultHtmlTick=ply.stringReplace_com(dataTick,temp2);
						html+=resultHtml+'<ul class="ticket">'+resultHtmlTick+'<li> <span class="toggle">收起<i class="Bg"></i></span></li> </ul></div>';
						allHtml+=html;
					}

					$("#m_list *").hide();
					$("#m_list .fly_box").remove();
					$(".fly_load").hide();
					$("#m_list").append(allHtml);
				}else{
					$(".fly_none").show();
					$(".fly_load").hide();
				}
			}
		}
	});
}

function timeGeshi(num){
	return num<10?("0"+num):num;
}
/**********************************************时间格式化***************************************************/
function getTime(date) {
	var now = "";
	now = date.getFullYear() + "-"; //读英文就行了
	var month = (date.getMonth() + 1).toString(); 
	if (month.length == 1) {
		month = "0" + month;
	}
	now = now + month + "-";//取月的时候取的是当前月-1如果想取当前月+1就可以了
	var d = date.getDate().toString();
	if (d.length == 1) {
		d = "0" + d;
	}
	now = now + d + " ";
	var hour = date.getHours().toString();
	if (hour.length == 1) {
		hour = "0" + hour;
	}
	return now;
}

