
$(function() {
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
            that.addClass("moreOn");
            setTimeout(function(){
                that.attr("moreOn","true");
            },100);
            that.closest(".listCont").find("img").show();
        }else{
            that.removeClass("moreOn");
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
            that.addClass("moreOn");
            setTimeout(function(){
                that.attr("moreOn","true");
            },100);
        }else{
            that.removeClass("moreOn");
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
            $(".nav").css({"position":"fixed",top:0});
        }else{
            $(".nav").css({"position":"absolute",top:-48});
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


});

