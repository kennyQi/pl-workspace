//弹出相册
ply.checkName("albumShow");
ply.albumShow=function(){
	//添加元素
	var html='<div class="m_albums" id="m_albums"><div class="albums_bg"></div><div class="albums_box"><span class="clos"></span> <span class="turnL"></span><span class="turnR"></span><div class="imgshow"><div class="imgshow_box"><img src="" class="img" onload="ply.imgCenter()" /></div><div class="title"><div class="title_bg"></div><span class="alt"></span><span class="zan" style="display:none;">20</span></div></div><span class="page"><i>1</i>/<label class="total">10</label></span><div class="sub_nav"><span class="subTurnL"></span><span class="subTurnR"></span><div class="img_list"><ul class="img_list_Img">';
    var htmlImg='';
	 	/*htmlImg='<li class="on"><img src="images/jd_img01.jpg" class="img" /></li>'+
				'<li><img src="images/index_img1.jpg" class="img" /></li>'+
				'<li><img src="images/index_img2.jpg" class="img" /></li>'+
				'<li><img src="images/index_img3.png" class="img" /></li>'+
				'<li><img src="images/index_img4.png" class="img" /></li>'+
				'<li><img src="images/index_banne1.jpg" class="img" /></li>'+
				'<li><img src="images/list_bn.jpg" class="img" /></li>'+
				'<li><img src="images/index_img1.jpg" class="img" /></li>'+
				'<li><img src="images/index_img2.jpg" class="img" /></li>'+
				'<li><img src="images/index_img3.png" class="img" /></li>'+
				'<li><img src="images/index_img4.png" class="img" /></li>'+
				'<li><img src="images/index_banne1.jpg" class="img" /></li>'+
				'<li><img src="images/list_bn.jpg" class="img" /></li>';   */
    htmlImg=$("#tepm").html();
	 html+=htmlImg;           
     html+='</ul></div></div></div></div>'
	$("body").append(html);
	//计算高度
	var bodyHei=$(window).height()
		,$m_albums=$("#m_albums")	
		,sub_navHei=$m_albums.find(".sub_nav").height()
		,pageHei=$m_albums.find(".page").height()
		,imgListWid=$m_albums.find(".img_list_Img li").width()
		,imgListBoxWid=$m_albums.find(".img_list").width()
		,$turnL=$m_albums.find(".turnL")
		,$turnR=$m_albums.find(".turnR")
		,$subturnL=$m_albums.find(".subTurnL")
		,$subturnR=$m_albums.find(".subTurnR")
		,$imgList=$m_albums.find(".img_list_Img img")
		,$imgLi=$m_albums.find(".img_list_Img li")
		,imgListLength=$imgList.length
		,subTime=Math.floor((imgListWid*imgListLength)/imgListBoxWid)
		;
	
	this.imgCenter=function(){
		$m_albums.find(".imgshow_box").css({"margin-top":($m_albums.find(".imgshow").height()-$m_albums.find(".imgshow_box .img").height())/2});
	}
	this.img_change=function(rel){
		if(rel==0){
			$turnL.add($subturnL).hide();
		}else if(rel==(imgListLength-1)){
			$turnR.add($subturnR).hide();
		}else{
			$turnL.add($subturnR).add($turnR).add($subturnL).show();
		}
		$m_albums.find(".imgshow_box .img").css("height","auto");
		var src=$m_albums.find(".img_list_Img li").eq(rel).find(".img").attr("src");
		$m_albums.find(".img_list_Img li").removeClass("on").eq(rel).addClass("on");
		
		$m_albums.find(".img_list_Img").stop().animate({left:-rel*imgListWid},300);
		
		$m_albums.find(".imgshow_box .img").attr("src",src);
		$m_albums.find(".page i").html(rel+1);
		if($m_albums.find(".imgshow_box .img").height()>$m_albums.find(".imgshow").height()){
			$m_albums.find(".imgshow_box .img").height($m_albums.find(".imgshow").height());
		}
		if(parseInt($m_albums.find(".img_list_Img").css("left")) + $m_albums.find(".img_list_Img").width()-$m_albums.find(".img_list").width()<0 ){
			$subturnR.hide();
		}
	};
	
	//初始化
	this.img_change(0);
	$m_albums.find(".page .total").html(imgListLength);
	$m_albums.find(".albums_bg").height(bodyHei);
	$m_albums.css({"padding-top":(bodyHei-$m_albums.find(".albums_box").height()-70)/2<15?15:(bodyHei-$m_albums.find(".albums_box").height()-70)/2});
	
	//让图片居中
	$m_albums.find(".img_list_Img").css({width:imgListLength*imgListWid});
	$m_albums.show();
	
	
	
	//初始化结束
	//点击事件
	$imgLi.click(function(){
		var rel=$(this).index();
		ply.img_change(rel);
	});
	//关闭
	$m_albums.find(".clos").click(function(){
		$m_albums.hide();
	});
	
	//添加事件
	$turnL.click(function(){
		var rel=$m_albums.find(".img_list_Img li.on").index();
		rel=rel==0?0:rel-1;
		ply.img_change(rel);
	});
	$turnR.click(function(){
		var rel=$m_albums.find(".img_list_Img li.on").index();
		rel=rel==(imgListLength-1)?rel:rel+1;
		ply.img_change(rel);
	});
	
	//底部导航事件
	$subturnL.click(function(){
		if(!$m_albums.find(".img_list_Img").is(":animated")){
			if(parseInt($m_albums.find(".img_list_Img").css("left"))<0){
				if((-parseInt($m_albums.find(".img_list_Img").css("left")))<parseInt($m_albums.find(".img_list").width())){
					$m_albums.find(".img_list_Img").animate({"left":0},500);
					$subturnL.hide();
				}else{
					$m_albums.find(".img_list_Img").animate({"left":parseInt($m_albums.find(".img_list_Img").css("left"))+$m_albums.find(".img_list").width()},500);
						$subturnR.add($subturnL).show();
				}
			}
		}
	});
	
	$subturnR.click(function(){
		if(!$m_albums.find(".img_list_Img").is(":animated")){
			if(parseInt($m_albums.find(".img_list_Img").css("left"))  >  (parseInt($m_albums.find(".img_list").width())-$m_albums.find(".img_list_Img").width())  ){
				//if(parseInt($m_albums.find(".img_list_Img").css("left")) + $m_albums.find(".img_list_Img").width()-$m_albums.find(".img_list").width()>0 ){
//					$m_albums.find(".img_list_Img").animate({"left":-(parseInt($m_albums.find(".img_list").width())-$m_albums.find(".img_list_Img").width()) },500);
//					
//				}else{
					$m_albums.find(".img_list_Img").animate({"left":parseInt($m_albums.find(".img_list_Img").css("left"))-$m_albums.find(".img_list").width()+imgListWid}
						,500
						,function(){
							$subturnR.add($subturnL).show();
							if(parseInt($m_albums.find(".img_list_Img").css("left")) + $m_albums.find(".img_list_Img").width()-$m_albums.find(".img_list").width()<0 ){
								$subturnR.hide();
							}
					});
					
				//}
			}
		}
	});
	
}



