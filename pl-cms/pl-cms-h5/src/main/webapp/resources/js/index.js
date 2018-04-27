// JavaScript Document

//轮播效果
	//var index={
//		Carousel:function(){
//			var len=$("#bn_carousel .bn_list").length
//				,iconHtml=""
//				,titleHtml=""
//				,hei=parseInt($("#bn_carousel img").eq(0).height())
//				,wid=parseInt($("#bn_carousel img").eq(0).width())
//				;
//			for(var i=0;i<len;i++){
//				iconHtml+="<i></i>";
//			}
//			$("#bn_icon").html(iconHtml).find("i").eq(len-1).addClass("on");
//			$(".bn").add(".bn_list").css({"height":hei,"width":wid});
//			$(".bn_list_box").css({"height":hei,"width":len*wid}).after($(".bn_list_box").clone());
//			$(".bn_list_box").eq(1).css({"left":len*wid});
//			
//			//初始化结束
//			if(len>1){
//				var rel=1;
//				var intervalFn;
//				var t=setInterval(function(){
//					
//							$("#bn_icon").removeClass("on").find("i").eq(len-rel).addClass("on");
//							$(".bn_list_box").eq(0).animate({left:-rel*wid},500);
//							$(".bn_list_box").eq(1).animate({left:(len-rel)*wid},500,function(){
//								if(rel==len){
//									rel=1;
//									var moList=$(".bn_list_box").eq(0).detach();
//									$(".bn_list_box").after(moList);
//									$(".bn_list_box").eq(1).css({"left":(len-1)*wid});
//								}else{
//									rel++;
//								}
//							});
//				},5000);
//			}
//			
//			
//			var img_box=document.getElementById("bn_carousel");
//			 var startX, startY, endX, endY;
//             img_box.addEventListener("touchstart", touchStart, false);
//            img_box.addEventListener("touchmove", touchMove, false);
//            img_box.addEventListener("touchend", touchEnd, false);
//            function touchStart(event) {
//                 var touch = event.touches[0];
//					startY = touch.pageY;
//					startX = touch.pageX;
//             }
//            function touchMove(event) {
//                var touch = event.touches[0];
//                 endX = touch.pageX;
//				 document.write(endX);
//             }
//             function touchEnd(event) {
//				 
//                 if ((startX - endX) > 60) {
//					 	if(!$(".bn_list_box").is(":animated")){
//                    		$("#bn_icon").removeClass("on").find("i").eq(len-rel).addClass("on");
//							$(".bn_list_box").eq(0).animate({left:-rel*wid},500);
//							$(".bn_list_box").eq(1).animate({left:(len-rel)*wid},500,function(){
//								if(rel==len){
//									rel=1;
//									var moList=$(".bn_list_box").eq(0).detach();
//									$(".bn_list_box").after(moList);
//									$(".bn_list_box").eq(1).css({"left":(len-1)*wid});
//								}else{
//									rel++;
//								}
//							});
//						}
//                 }
//             }
//		}
//	};

var index={
		Carousel:function(){
			var len=$("#thelist .bn_list").length
				,iconHtml=""
				,titleHtml=""
				,hei=parseInt($("#thelist img").eq(0).height())
				,wid=parseInt($("#wrapper").width())
				;
				$("#wrapper").add("#thelist .bn_list").css("height",wid*38/64);
				$("#thelist .bn_list img").css({"height":wid*38/64,"width":wid});
				$("#scroller").css("width",len*wid);
				
				for(var i=0;i<len;i++){
					iconHtml+='<li>'+(i+1)+'</li>';
				}
				$("#indicator").html(iconHtml);
				$("#indicator li").eq(0).addClass("active");
				//初始化
				setInterval(function(){
					if($("#indicator > li").eq(len-1).attr("class")!=undefined&&$("#indicator > li").eq(len-1).attr("class")!=""){
						myScroll.currPageX=0;
						myScroll.x=0;
						myScroll.scrollToPage(0, 0,wid,len);
					}else{
						myScroll.scrollToPage('next', 0,wid,len);
					}
				},5000);
				
		}
	};
	index.Carousel();
	
//轮播效果