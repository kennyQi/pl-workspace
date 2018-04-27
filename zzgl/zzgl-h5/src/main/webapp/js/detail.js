
$(function() {
	var message = $("#message").val();
	if(message != "1"){
		showMsg(message);
	}
    var $slider = $('#demo-slider-0');
    var counter = 0;
    var getSlide = function() {
        counter++;
        return '<li><img src="http://s.amazeui.org/media/i/demos/bing-' +
            (Math.floor(Math.random() * 4) + 1) + '.jpg" />' +
            '<div class="am-slider-desc">动态插入的 slide ' + counter + '</div></li>';
    };

    $('.js-demo-slider-btn').on('click', function() {
        var action = this.getAttribute('data-action');
        if (action === 'add') {
            $slider.flexslider({
                'addSlide': getSlide(),
                slideshow:true,
                slideshowSpeed: 1000
            });
        } else {
            var count = $slider.flexslider('count');
            count > 1 && $slider.flexslider({'removeSlide': $slider.flexslider('count') - 1,
                slideshow:true,
                slideshowSpeed: 1000
            });
        }
    });

    //隐藏图像
    $(".detailTab:eq(1) .listCont").each(function(){
        var that=$(this);
        if($(this).find("img").length>3){
            $(this).find("img").each(function(i){
                if(i>2){
                    that.find("img").eq(i).hide();
                }
            });
            $(this).find(".more").show();
        }
    });
    //隐藏文字

    $(".detailTab:eq(0) .listCont").each(function(){
        var that=$(this);
        if($(this).find("p").height()>60){
            $(this).find("p").css({"height":"60px","overflow":"hidden"});
            $(this).find(".more").show();
        }
    });


    //详情更多
    $(".detailTab:eq(1) .more").click(function(){
        var that=$(this);
        if(that.attr("moreOn")!="true"){
            that.find("i").html("&#xe6a2;");
            setTimeout(function(){
                that.attr("moreOn","true");
            },100);
            that.closest(".listCont").find("img").show();
        }else{
            that.find("i").html("&#xe616;");

            setTimeout(function(){
                that.attr("moreOn","false");
            },100);
            that.closest(".listCont").find("img").each(function(i){
                if(i>2){
                    $(this).hide();
                }
            });
        }
    });

    $(".detailTab:eq(0) .more").click(function(){
        var that=$(this);
        if(that.attr("moreOn")!="true"){
            that.closest(".listCont").find("p").css("height","auto");
            that.find("i").html("&#xe6a2;");
            setTimeout(function(){
                that.attr("moreOn","true");
            },100);
        }else{
            that.find("i").html("&#xe616;");
            setTimeout(function(){
                that.attr("moreOn","false");
            },100);
            that.closest(".listCont").find("p").css("height","60px");
        }
    });

    //导航浮动
    var oldtop=1000;
    setTimeout(function(){
        oldtop=$(".nav").offset().top;
    },300);
    $(window).scroll(function(){
        if($(window).scrollTop()>=oldtop){
            if(!($(".nav").css("position").indexOf("fixed")!=-1)){
                    $(".nav").hide().css({"position":"fixed",top:0}).fadeIn(300);
            }
        }else{
            if(!($(".nav").css("position").indexOf("absolute")!=-1)) {
                $(".nav").hide().css({"position": "absolute", top: -48}).fadeIn(300);
            }
        }
    });

    //内容切换

    $(".nav span").each(function(i,e){
        $(this).click(function(){
            $(".nav span").removeClass("on");
            $(this).addClass("on");
            $(".detailTab").hide().eq(i).show();
        });
    });

    //抢购跳转
   $(".next-btn").click(function(){
	   $("#bookOrderForm").submit();
   });
    
});

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
            $(".g_msg").remove();
    },2000);
}

