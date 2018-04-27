require.config({
    //通用js模块路径
    baseUrl:request_path+"/resources/js/",
    //通用框架
    paths: {
        jquery: 'libs/jquery-2.1.4.min'
    },

    waitSeconds: 0
});

//默认调用js
require(["jquery"],
function($) {
    $(function(){
        //loading
        $(".mainLoad").remove();
        $("body").append('<div class="mainLoad"></div>');
        //loadingClosed
        setTimeout(function(){
            $(".mainLoad").remove();
        },1500);

        $(".order_tab li").each(function(i,e){
        	$(this).click(function(){
        		var url = $("#allOrders").attr("href");
        		url = url.substring(0,url.length-1)+i;
        		$("#allOrders").attr("href",url);
                document.getElementById("allOrders").click();
        	});
        });

        //下拉刷新
        var touchObj=$(".listCont");
        touchObj.each(function(i,obj){
            var startY= 0,left= 0,endY= 0,nowX=0;
            obj.addEventListener('touchstart', function(event) {
            	if($(window).scrollTop()>0){
            		return;
            	}
                $(".loadIngMsg").show();
                if (event.targetTouches.length == 1&&!(event.target.className.indexOf("delete")!=-1)) {
                    var touch = event.targetTouches[0];
                    startY=touch.pageY;
                }
            }, false);
            obj.addEventListener('touchmove', function(event) {
            	if($(window).scrollTop()>0){
            		return;
            	}
                if (event.targetTouches.length == 1&&!(event.target.className.indexOf("delete")!=-1)) {
                    var touch = event.targetTouches[0];
                    endY=touch.pageY;
                    if(endY-startY>0){
                        event.preventDefault();
                        $(obj).css({
                            "-webkit-transform":"translateY("+(endY-startY)+"px)"
                        });
                    }
                }
            }, false);
            obj.addEventListener('touchend', function(event) {

                if(endY-startY<5||$(window).scrollTop()>0){
                    return;
                }
                event.preventDefault();
                $(obj).css({"z-index":(endY-startY),"position":"relative"});
                if(endY-startY>75){
                    $(obj).animate({"z-index":40}, {
                        duration:50,
                            step: function(now,fx) {
                            $(obj).css('-webkit-transform','translateY('+now+'px)');
                        },easing:"linear"});
                    //ajax请求
                    if(location.href.indexOf("type=0")!=-1){
                        var url = $("#allOrders").attr("href");
                		url = url.substring(0,url.length-1)+0;
                		$("#allOrders").attr("href",url);
                    }else if(location.href.indexOf("type=1")!=-1){
                        var url = $("#allOrders").attr("href");
                		url = url.substring(0,url.length-1)+1;
                		$("#allOrders").attr("href",url);
                    }else if(location.href.indexOf("type=2")!=-1){
                        var url = $("#allOrders").attr("href");
                		url = url.substring(0,url.length-1)+2;
                		$("#allOrders").attr("href",url);
                    }else if(location.href.indexOf("type=3")!=-1){
                        var url = $("#allOrders").attr("href");
                		url = url.substring(0,url.length-1)+3;
                		$("#allOrders").attr("href",url);
                    }
                    document.getElementById("allOrders").click();
                }else{
                    $(obj).animate({"z-index":0}, {
                        duration:50,
                        step: function(now,fx) {
                            $(obj).css('-webkit-transform','translateY('+now+'px)');
                        },easing:"linear"});

                    $(".loadIngMsg").hide();
                }

            }, false);
        });
    });

});
