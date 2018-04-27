
$(function(){
	
	//点击下一步,表单提交
	$(".next-btn").click(function(){
		var dateHtml = $(".on").html();
		if(dateHtml != null){
			$("#lineOrderDateForm").submit();
		}else{
			showMsg("请选择出行日期");
		}
	});

	creatDate("body",5);

	$("#sureDate").click(function(){
		if($("#dateMobile").find(".on span").html() ){
			var time=$("#dateMobile").find(".on").attr("year")+"年"+$("#dateMobile").find(".on").attr("mon")+"月"+$("#dateMobile").find(".on").attr("day")+"日";
			var price=parseFloat($("#dateMobile").find(".on span").html().slice(1));
			$("#chooseDate h4").html("游玩时间：<i>"+time+"</i>");
			$("#price").html('<i class="dw">¥</i>'+price);
			$(".content").hide().eq(0).show();
		}else{
			showMsg("无法选择该日期");
		}
	});

	//给日期添加价格数据
	if($("#dateMoney option").length>0){
		for(var i=0,l=$("#dateMoney option").length;i<l;i++){
			var html='<span class="money" d="'+$("#dateMoney option").eq(i).val()+'" data-number="'+$("#dateMoney option").eq(i).attr("data-number")+'" for="'+$("#dateMoney option").eq(i).attr("for")+'">'+$("#dateMoney option").eq(i).html()+'</span>';
			$("#d_"+$("#dateMoney option").eq(i).val()+" div").append(html);
		}
		var nowTime=new Date();
		//$("#d_"+nowTime.getFullYear()+"-"+ten(nowTime.getMonth()+1)+"-"+ten(nowTime.getDate())).trigger("click");
	}


	//人数添加
	$(".tool").each(function(){
		var that=$(this);
		$(this).find(".right").click(function(){
			var shengyuNum =parseInt($(".dateMobile td.on span").attr("data-number"));//线路剩余人数
			if(shengyuNum<=( parseInt($(".num[for='adult']").html()) + parseInt($(".num[for='children']").html()) ) ){
				showMsg("当天最多买"+shengyuNum+"张票！");
				return;
			}
			that.find(".num").html(parseInt(that.find(".num").html())+1);
			$("#"+that.find(".num").attr("for")).html(parseInt(that.find(".num").html()));
			//成人或者小孩
			var personType = that.find(".num").attr("for");
			var currentNum = parseInt(that.find(".num").html());
			if(personType == "adult"){
				$("#lineOrderDateForm").find("#adultNo").html("<input name='adultNo' value='"+currentNum+"'/>");
			}else if(personType == "children"){
				$("#lineOrderDateForm").find("#childNo").html("<input name='childNo' value='"+currentNum+"'/>");
			}
		});
		//减
		$(this).find(".left").click(function(){
			if(parseInt(that.find(".num").html())>0) {
				//成人或者小孩
				var personType1 = that.find(".num").attr("for");
				//当前游客数量
				var num = parseInt(that.find(".num").html());
				//成人数量不能为0,至少为1
				if(num == 1 && personType1 == "adult"){
					that.find(".num").html(parseInt(that.find(".num").html()));
					$("#"+that.find(".num").attr("for")).html(1);
					$("#lineOrderDateForm input[name='adultNo']").val(1);
				}else{
					that.find(".num").html(parseInt(that.find(".num").html()) - 1);
					//执行减法后的游客数量
					num = parseInt(that.find(".num").html());
					$("#"+that.find(".num").attr("for")).html(num);
					//设置成人和儿童的数量
					if(personType1 == "adult"){
						$("#lineOrderDateForm").find("#adultNo").html("<input name='adultNo' value='"+num+"'/>");
					}else if(personType1 == "children"){
						$("#lineOrderDateForm").find("#childNo").html("<input name='childNo' value='"+num+"'/>");
					}
				}
				
			}
		});
	});

});


function creatDate(direct,showMonth){
	var nowTime=new Date();
	var year=nowTime.getFullYear(),month=nowTime.getMonth(),showMonth=showMonth;
	var dateArr=[],ArrMonth;
	//生成月份和天数;
	for(var m=0;m<showMonth;m++){
		dateArr[m]=[];
		ArrMonth=month+m;
		for(var d=0;d<33;d++){
			var b=new Date();
			b.setFullYear(year,ArrMonth,d);
			if((b.getMonth()-ArrMonth)<=0){
				var Arr={y:b.getFullYear(),m:(b.getMonth()+1),d:b.getDate(),z:b.getDay()};
				dateArr[m].push(Arr);
			}
		}
		//补位
		if(dateArr[m][0].z!=6){
			for(var q=dateArr[m][0].z;q>0;q--){
				var Arr={y:dateArr[m][0].y,m:dateArr[m][0].m,d:dateArr[m][0].d-1,z:q};
				dateArr[m].unshift(Arr);
			}
		}else{
			dateArr[m].shift();
		}
		var len=dateArr[m].length;
		if(dateArr[m][(len-1)].z!=6){
			var i=0;
			for(var q=(dateArr[m][(len-1)].z+1);q<7;q++){
				i++;
				var Arr={y:dateArr[m][(len-1)].y,m:dateArr[m][(len-1)].m+1,d:i,z:q};
				dateArr[m].push(Arr);
			}
		}
	}
	
	//生成html部分
	for(var i=0,l=dateArr.length;i<l;i++){
		var tableHtml="",weekHtml="",html="",reback=0;
		tableHtml="<table rel='"+dateArr[i][15].m+"' width='100%' border='0' cellspacing='0' cellpadding='0'>"+
			"<tr class='fullYear'>"+
			"<th colspan='7'>"+dateArr[i][15].m+"月"+"·"+dateArr[i][15].y+"</th>"+
			"</tr>"+
			"<tr class='tt'>"+
			"<th>日</th>"+
			"<th>一</th>"+
			"<th>二</th>"+
			"<th>三</th>"+
			"<th>四</th>"+
			"<th>五</th>"+
			"<th>六</th>"+
			"</tr>"
		;
		for(var t=0,tl=dateArr[i].length;t<tl;t++){
			html+="<td year='"+dateArr[i][t].y+"' mon='"+dateArr[i][t].m+"' day='"+dateArr[i][t].d+"' id='d_"+dateArr[i][t].y+"-"+ten(dateArr[i][t].m)+"-"+ten(dateArr[i][t].d)+"'><div>"+dateArr[i][t].d+"</div></td>";
			if((t+1)%7==0){
				html="<tr rel='"+dateArr[i][15].m+"'>"+html+"</tr>";
				weekHtml+=html;
				html="";
			}
		}
		tableHtml+=weekHtml;
		tableHtml+="</table>";
		$("#"+direct).append(tableHtml);
	}
	$("#"+direct+" td").each(function(){
		if($(this).attr("mon")>(nowTime.getMonth()+1)||$(this).attr("day")>=nowTime.getDate()||$(this).attr("year")>nowTime.getFullYear()){
			$(this).addClass("can");
		}
		if($(this).attr("mon")!=$(this).closest("tr").attr("rel")){
			$(this).removeClass("can");
		}else{
			$(this).addClass("show");
		}
		$(this).click(function(){
			if($(this).attr("class").indexOf("can")!=-1 && $(this).html().indexOf("span")!=-1 ){
				$(this).closest("#"+direct).find("td").removeClass("on");
				$(this).addClass("on");
				//赋值
				$("#chooseDay").html($(this).find("span").attr("d"));
				$("#lineOrderDateForm").find("#travelDate").html("<input name='travelDate' value='"+$(this).find("span").attr("d")+"'/>");
				
				var adultNum = $(".leftBtn").find("label[id='adult']").html();
				var childrenNum = $(".leftBtn").find("label[id='children']").html();
				$("#lineOrderDateForm").find("#adultNo").html("<input name='adultNo' value='"+adultNum+"'/>");
				$("#lineOrderDateForm").find("#childNo").html("<input name='childNo' value='"+childrenNum+"'/>");
				
				$("#adultPrice").find(".price").html("成人价："+$(this).find("span").text().substr(1));
				$("#childrenPrice").find(".price").html("儿童价："+$(this).find("span").attr("for"));
				
				//重置人数
				$(".num[for='adult']").html(1);
				$(".num[for='children']").html(0);
			}else if($(this).attr("class").indexOf("can")!=-1 ){
				showMsg("无法选择该日期");
			}
		});
	});


	}

function showMsg(msg){
	var h=$(window).height()
		,w=$(window).width()
		;
	var html='<div class="g_msg"><span>'+msg+'</span></div>';
	$("body").append(html);
	var msg_w=parseInt($(".g_msg").css("width")),msg_h=parseInt($(".g_msg").css("height"));
	$(".g_msg").css({"top":(h-msg_h)/2,"left":(w-msg_w)/2})
	$(".g_msg").show(150);
	setTimeout(function(){
		$(".g_msg").hide(150,function(){
			$(".g_msg").remove();
		});
	},2000);
}
function ten(n){
	n=n<10?"0"+n:n;
	return n;
}

