(function($){
	$.fn.foreverSlide=function(option){
		var opt={
			speed:300,//动效速度
			spe:1000,//轮播间隔
			pointerAlign:"center",//点的方向
			titleHtml:"rel",//标题属性
			hasHandle:false,//是否显示左右操作键
			hasTitle:false,//是否显示标题
			hasPointer:false//是否显示轮播点
		};
		option=$.extend(opt,option);
		var $this=$(this)
			,box=$this.find(".foreverSlide_box")
			,length=box.find("li").width($(window).width()).length
			,first=box.find("li").eq(0)
			,last=box.find("li:last")
			,lWidth=first.width()
			,indexI=0
			,hover=false
			;
		var pointHtml=""
			,toolHtml=""
			,titleHtml=""
			,handleHtml=""
			;
		function isPc(){
			var userAgentInfo = navigator.userAgent;
			var Agents = new Array("Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod");
			var flag = true;
			for (var v = 0; v < Agents.length; v++) {
				if (userAgentInfo.indexOf(Agents[v]) > 0) { flag = false; break; }
			}
			return flag;
		}
		option.isPc=isPc();
		//生成元素
		pointHtml="<ul class='forever-pointer' style='text-align:"+option.pointerAlign+"' >";
		for(var p=0;p<length;p++){
			if(p==0) {
				pointHtml += '<li class="current"></li>';
			}else{
				pointHtml += '<li></li>';
			}
		}
		pointHtml+='</ul>';
		if(!option.hasPointer)pointHtml="";
		if(option.hasTitle){
			titleHtml=  '<div class="forever-titleBg"></div>'+
				'<div class="forever-title"></div>' ;
		}else{
			titleHtml="";
		}
		toolHtml='<div class="forever-titleBox">' +
			pointHtml+
			titleHtml+
			'</div>';

		if(option.hasHandle){
			handleHtml='<div class="forever-left"></div>' +
				'<div class="forever-right" ></div>';
		}else{
			handleHtml='';
		}
		$this.append(toolHtml+handleHtml);

		box.width(lWidth*length);

		$this.find(".forever-left").click(function() {
			if(box.is(":animated")) return;
			turnR();
		});
		$this.find(".forever-right").click(function() {
			if(box.is(":animated")) return;
			turnL();
		});
		$this.find(".forever-pointer li").each(function(i,e){
			$(e).click(function(){
				indexI=i-1;
				turnL();
			});
		});
		$this.hover(function(){
			hover=true;
		},function(){
			hover=false;
		});

		setInterval(function(){
			if(hover)return;
			turnL();
		},option.spe);
		function turnL(){
			indexI++;
			if(option.isPc) {
				if (indexI == length) {
					first.css({left: lWidth * length, position: "relative"});
					box.stop().animate({left: -indexI * lWidth}, option.speed, function () {
						box.css({left: 0});
						first.removeAttr("style");
						indexI = 0;
					});
					$this.find(".forever-title").html(box.find("li").eq(0).attr(option.titleHtml));
					$this.find(".forever-pointer li").removeClass("current").eq(0).addClass("current");
				} else {
					box.stop().animate({left: -indexI * lWidth}, option.speed);
					$this.find(".forever-title").html(box.find("li").eq(indexI).attr(option.titleHtml));
					$this.find(".forever-pointer li").removeClass("current").eq(indexI).addClass("current");
				}
			}else{
				//手机端滑动
				if (indexI == length) {
					first.css({left: lWidth * length, position: "relative"});
					box.stop().animate({"-webkit-transform":"translate("+(-indexI * lWidth)+"px, 0px) translateZ(0px)"}, option.speed, function () {
						box.css({"-webkit-transform": "translate(0px, 0px) translateZ(0px)"});
						first.removeAttr("style");
						indexI = 0;
					});
					$this.find(".forever-title").html(box.find("li").eq(0).attr(option.titleHtml));
					$this.find(".forever-pointer li").removeClass("current").eq(0).addClass("current");
				} else {
					box.stop().animate({"-webkit-transform":"translate("+(-indexI * lWidth)+"px, 0px) translateZ(0px)"}, option.speed);
					$this.find(".forever-title").html(box.find("li").eq(indexI).attr(option.titleHtml));
					$this.find(".forever-pointer li").removeClass("current").eq(indexI).addClass("current");
				}
			}
		}
		function turnR(){
			indexI--;
			if(option.isPc) {
				if (indexI == -1) {
					last.css({left: -lWidth*length, position: "relative"});
					box.stop().animate({left: -indexI * lWidth}, option.speed, function () {
						last.removeAttr("style");
						indexI = length-1;
						box.css({left: -lWidth*(length-1)});
					});
					$this.find(".forever-title").html(box.find("li").eq((length-1)).attr(option.titleHtml));
					$this.find(".forever-pointer li").removeClass("current").eq(length-1).addClass("current");
				} else {
					box.stop().animate({left: -indexI * lWidth}, option.speed);
					$this.find(".forever-title").html(box.find("li").eq((indexI)).attr(option.titleHtml));
					$this.find(".forever-pointer li").removeClass("current").eq(indexI).addClass("current");
				}
			}else {
				if (indexI == -1) {
					last.css({left: -lWidth*length, position: "relative"});
					box.stop().animate({"-webkit-transform":"translate("+(-indexI * lWidth)+"px, 0px) translateZ(0px)"}, option.speed, function () {
						last.removeAttr("style");
						indexI = length-1;
						box.css({left: -lWidth*(length-1)});
					});
					$this.find(".forever-title").html(box.find("li").eq((length-1)).attr(option.titleHtml));
					$this.find(".forever-pointer li").removeClass("current").eq(length-1).addClass("current");
				} else {
					box.stop().animate({"-webkit-transform":"translate("+(-indexI * lWidth)+"px, 0px) translateZ(0px)"}, option.speed);
					$this.find(".forever-title").html(box.find("li").eq((indexI)).attr(option.titleHtml));
					$this.find(".forever-pointer li").removeClass("current").eq(indexI).addClass("current");
				}
			}
		}


	};
})(jQuery);
