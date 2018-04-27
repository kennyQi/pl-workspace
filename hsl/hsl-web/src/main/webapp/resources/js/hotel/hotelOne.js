$(function(){
	//判断是否显示筛选
	var starRate=$("#starRate").val(),
	lowRates=parseInt($("#lowRates").val()),
	highRate=parseInt($("#highRate").val()),
	sqrido=$("#sqrido").val();
	if(starRate!=""||lowRates!=0||highRate!=0||sqrido!=""){
		$("#condition").css("display","block");
	}
	if(sqrido!=""){
		addBox($("#sqrido").val(),"location");
	}
	if(starRate!=""){
		$("#hotalstar dd").each(function(){
			if($(this).hasClass("on")){
				$(this).removeClass("on");
			}
		})
		var starRates="";
		if(starRate=="2"){
			starRates="二星级及以下/经济";
			$("#starRate2").addClass("on");
		}else if(starRate=="3"){
			starRates="三星级/舒适";	
			$("#starRate3").addClass("on");
		}else if(starRate=="4"){
			starRates="四星级/高档";
			$("#starRate4").addClass("on");
		}else if(starRate=="5"){
			starRates="五星级/豪华";
			$("#starRate5").addClass("on");
		}
		addBox(starRates,"hotalstar");
	}
	var pic="";
	if((lowRates!=0||highRate!=99999)&&highRate!=0){
		$("#price dd").each(function(){
			if($(this).hasClass("on")){
				$(this).removeClass("on");
			}
		})
		if(lowRates==0 && highRate==150){
			pic="150元以下";
			addBox(pic,"price");
			$("#lowRate1").addClass("on");
		}else if(lowRates==150 && highRate==300){
			pic="150元-300元";
			addBox(pic,"price");
			$("#lowRate2").addClass("on");
		}else if(lowRates==300 && highRate==600){
			pic="300元-600元";
			addBox(pic,"price");
			$("#lowRate3").addClass("on");
		}else if(lowRates==600 && highRate==99999){
			pic="600元以上";
			addBox(pic,"price");
			$("#lowRate4").addClass("on");
		}else{
			pic=""+lowRates+"元-"+highRate+"元";
			addBox(pic,"price");
			$("#priceMin").val(lowRates);
			$("#priceMax").val(highRate);
			$("#priceSure").css("display","inline");
		}
	}else{
		$("#lowRate0").addClass("on");
	}
	//分页初始化;
	/**************页面加载查询商圈*************************************************/
	var city=$("#citySelect_from").val();
	//var cooks=getCookie("citys");
	$.ajax({
		url:"../jd/getHotelGeo",
		type:"post",
		data:"city="+city,
		dataType:"json",
		success:function(data){
			//if(decodeURI(cooks)!=city||decodeURI(cooks)==""){
			var sqrido=$("#sqrido").val();//已选商圈名称
			/************************************商圈****************************************************/
			var commericalLocations="";
			$.each(data.hotelGeoDTO.commericalLocations,function(i,commericalLocation){
				var list_on="";
				if(sqrido==commericalLocation.name){
					list_on="list_on";
				}
				commericalLocations+='<span class="list h3 color3 '+list_on+'" id='+commericalLocation.commericalLocationId+'>'+commericalLocation.name+'</span>';
			})
			commericalLocations+=' <span class="pa more h3 color3 curp Bg" name="locationMore">更多</span>'+
			'<span class="pa close h3 curp Bg" name="locationClose" a="#6bc8f1" b="#dbf3fe"></span>';
			$("#commericalLocations").empty();
			$("#commericalLocations").append(commericalLocations);
			/************************************行政区****************************************************/
			var districts="";
			$.each(data.hotelGeoDTO.districts,function(i,district){
				var list_on="";
				if(sqrido==district.name){
					list_on="list_on";
				}
				districts+='<span class="list h3 color3 '+list_on+'" id='+district.districtId+'>'+district.name+'</span>';
			})
			districts+=' <span class="pa more h3 color3 curp Bg" name="locationMore">更多</span>'+
			' <span class="pa close h3 curp Bg" name="locationClose" a="#6bc8f1" b="#dbf3fe"></span>';
			$("#districts").empty();
			$("#districts").append(districts);
			//	document.cookie="citys="+encodeURI(city);
			addevent();
			//}
		}
	})
	//document.cookie="citys=";
	//setVoluation();
	/**************点击价格排序*************************************************/
	$("#priceOB").on("click",function(){
		var prices=$("#prices").val();
		if(prices=="1"){
			$("#priceOB").addClass("cmdon");
			$("#prices").val("0");
		}else{
			$("#priceOB").removeClass("cmdon");
			$("#prices").val("1");
		}
		$("#rankOB").removeClass("cmdon");
		$("#ranks").val("0");
		$("#commentOB").removeClass("cmdon");
		$("#comments").val("0");
		send();
	})
	/**************点星级格排序*************************************************/	
	$("#rankOB").click(function(){
		var ranks=$("#ranks").val();
		$("#rankOB").addClass("cmdon");
		$("#ranks").val("1");
		$("#priceOB").removeClass("cmdon");
		$("#prices").val("2");
		$("#commentOB").removeClass("cmdon");
		$("#comments").val("2");

		send();
	})
	/**************点击点评排序*************************************************/
	/*$("#commentOB").click(function(){
		var comments=$("#comments").val();
		if(comments=="0"){
			$("#commentOB").addClass("cmdon");
			$("#comments").val("1");
			$("#priceOB").removeClass("cmdon");
			$("#prices").val("1");
			$("#rankOB").removeClass("cmdon");
			$("#ranks").val("0");
		}else{
			$("#commentOB").removeClass("cmdon");
			$("#comments").val("0");
		}
	})*/
	var totalCount=$("#totalCount").html();
	var rate=$("#prices").val();
	var ranks=$("#ranks").val();
	if(rate=="1"){
		$("#priceOB").addClass("cmdon");
	}else{
		$("#priceOB").removeClass("cmdon");
	}
	if(ranks=="1"){
		$("#rankOB").addClass("cmdon");
	}
	if((parseInt(totalCount/5))+1>1){
		$("#page").pageBar({
			pageNum:"pageNum",//页码class
			pageSize:"pageSize",//页面大小class
			pageCount:(parseInt(totalCount/5))+1,//总页数
			formClass:"pageForm",//分页表单class
		});
	}


})
/**************查询酒店列表*************************************************/
function send(){
	setVoluation();
	//发送请求之前比较入店日期和离店日期的大小
	if(dateCompare($("#enter").val(),$("#put").val())){
		$(".pageForm").submit();
	}else{
		alert("入住日期和离店日期不能选择今天以前的日期，并且离店日期不能小于今天入住日期");
	}
}
/*function getCookie(name) {  
    var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));  
    if (arr != null) return arr[2]; return null;  
};*/ 
function setVoluation(){
	var starRate="";//得到酒店星级
	$("#hotalstar dd").each(function(){
		if($(this).hasClass("on")){
			starRate= $(this).attr("starRate");
		}

	})
	var lowRates=0,
	highRates=99999;//定义价格区间
	var price="";//得到房价标识
	$("#price dd").each(function(){
		if($(this).hasClass("on")){
			price= $(this).attr("lowRate");
		}

	})
	if(price=="0"){
		lowRates="";
		highRates="";
	}else if(price=="1"){
		lowRates="";
		highRates="150";
	}else if(price=="2"){
		lowRates="150";
		highRates="300";
	}else if(price=="3"){
		lowRates="300";
		highRates="600";
	}else if(price=="4"){
		lowRates="600";
		highRates="";
	}else{
		lowRates=$("#priceMin").val();
		highRates=$("#priceMax").val();
	}
	var city=$("#citySelect_from").val(),
	arrivaltime=$("#enter").val(),
	departuretime=$("#put").val(),
	brand=$("#hotelName").val(),
	ranks=$("#hotelLoaction").val(),
	comments=$("#comments").val(),
	prices=$("#prices").val();
	/**********给隐藏域查询条件赋值********/
	$("#cityName").val(city);
	$("#startDate").val(arrivaltime);
	$("#endDate").val(departuretime);
	$("#jdmc").val(brand);
	$("#sqwz").val(ranks);
	$("#starRate").val(starRate);
	$("#lowRates").val(lowRates);
	$("#highRate").val(highRates);
}
//跳转到详情页
function detail(id){
	var startTime=$("#enter").val();
	var endTime=$("#put").val();
	window.location.href="../jd/detail?hotelIds="+id+"&arrivalTime="+startTime+"&departureTime="+endTime;
}
/**************比较日期大小*************************************************/
function dateCompare(startdate,enddate) {   
	var arr=startdate.split("-");    
	var starttime=new Date(arr[0],arr[1],arr[2]);    
	var starttimes=starttime.getTime();   

	var arrs=enddate.split("-");    
	var lktime=new Date(arrs[0],arrs[1],arrs[2]);    
	var lktimes=lktime.getTime();  
	var today=new Date();
	var todaytime=today.getTime();
	if(starttimes<todaytime||lktimes<todaytime||starttimes>=lktimes)    
	{   
		return false;   
	}   
	else  
		return true;   

}  



