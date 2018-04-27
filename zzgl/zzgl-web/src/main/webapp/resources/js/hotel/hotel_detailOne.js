$(function(){
	/******************点击根据日期查询酒店房型***********************/	
	$("#arrivalTime").val(ply.GetQueryString("arrivalTime"))
	$("#departureTime").val(ply.GetQueryString("departureTime"))
	setTimeout(select(), 200);
	
})
function select(){
	var hotelIds=ply.GetQueryString("hotelIds");//房间id
	var arrivalTime=$("#arrivalTime").val();
	var departureTime=$("#departureTime").val();
	//if(arrivalTime!=""&&departureTime!=""){
	$.ajax({
		url:"../jd/getHotelRooms",
		type:"post",
		data:"hotelIds="+hotelIds+"&arrivalTime="+arrivalTime+"&departureTime="+departureTime,
		dataType:"json",
		success:function(data){
			var text=""
				//拼接房型列表
				$.each(data.hotelDTO.rooms,function(i,rooms){
					var name=rooms.name;
					if(name.length>8){
						name=name.substring(0,7);
					}
					text+='<div class="tr ov"> <span class="fl hs pic pl"> <img src='+rooms.imageUrl+' class="img" />'+
					'<label class="de pa Bg color3 h3">'+name+'</label></span>';
					$.each(rooms.ratePlans,function(i,ratePlans){
						text+='<div class="hs_word fr fir">'+
						'<span class="fl dt mtr">'+ratePlans.ratePlanName+'</span>'+
						'<span class="fl bd mtr">'+rooms.bedType+'</span>'+
						'<span class="fl bf mtr">双早</span>'+
						'<span class="fl wf mtr">';
						if(rooms.broadnet==0){
							text+="无";
						}else if(rooms.broadnet==1){
							text+="免费宽带";
						}else if(rooms.broadnet==2){
							text+="收费宽带";
						}else if(rooms.broadnet==3){
							text+="免费wifi";                        		  
						}else if(rooms.broadnet==4){
							text+="收费wifi";  
						}
						text+='</span>'+
						'<span class="fl pr mtr">￥'+ratePlans.totalRate+'</span>'+
						'<span class="fl bt mtr"><a href="javascript:void(0)" class="btn fl" onclick="reserv('+data.hotelDTO.hotelId+','+ratePlans.ratePlanId+',\''+ratePlans.roomTypeId+'\')">预定</a></span></div>';
					})
					text+='<div class="tr_de ov disb wd h4" >'+rooms.description+'</div></div>';
				})
				$("#hotelRooms").empty();
			$("#hotelRooms").append(text);

			$("#arrivalTime").val(getTime(new Date(data.jDHotelDetailQO.arrivalDate)));
			$("#departureTime").val(getTime(new Date(data.jDHotelDetailQO.departureDate)));
		}
	})
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
function reserv(hotelId,ratePlan,roomTypeId){//跳转到详情
	var arrivalTime=$("#arrivalTime").val();
	var departureTime=$("#departureTime").val();
	window.location.href='../jd/detail?hotelIds='+hotelId+'&roomTypeId='+roomTypeId+'&ratePlan='+ratePlan+'&arrivalTime='+arrivalTime+'&departureTime='+departureTime+'&mark=2';

}