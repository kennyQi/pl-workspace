$(function(){
   //滑动
    $(".pList li").each(function(i,obj){
        var startX= 0,left= 0,endX= 0,nowX=0;
        //if(obj.target=="")
        obj.addEventListener('touchstart', function(event) {
            event.preventDefault();
            if (event.targetTouches.length == 1&&!(event.target.className.indexOf("delete")!=-1)) {
                var touch = event.targetTouches[0];
                startX=touch.pageX;
                left=parseInt($(obj).attr("left"));
            }
        }, false);
        obj.addEventListener('touchmove', function(event) {
            event.preventDefault();
            if (event.targetTouches.length == 1&&!(event.target.className.indexOf("delete")!=-1)) {
                var touch = event.targetTouches[0];
                endX=touch.pageX;
                if(left==0){
                    if(touch.pageX-startX<-75){
                        $(obj).css({
                            "transform":"translateX(-75px)",
                            "-webkit-transform":"translateX(-75px)"
                        });
                    }else if(touch.pageX-startX<0){
                        $(obj).css({
                            "transform":"translateX("+(touch.pageX-startX)+"px)",
                            "-webkit-transform":"translateX("+(touch.pageX-startX)+"px)"
                        });
                    }
                }
            }
        }, false);
        obj.addEventListener('touchend', function(event) {
            event.preventDefault();
            if(!(event.target.className.indexOf("delete")!=-1)){
                if(endX-startX<0){
                    $(obj).animate({"transform":"translateX(-75px)","-webkit-transform":"translateX(-75px)"},50);
                    $(obj).attr("left",1);
                }else{
                    $(obj).animate({"transform":"translateX(0px)","-webkit-transform":"translateX(0px)"},50);
                    $(obj).attr("left",0);
                }
            }
        }, false);
    });

    //删除
    $(".delete").tap(function(){
        var that=$(this);
        setTimeout(function(){
            that.closest("li").remove();
        },50);
    });

    $("#reset").tap(function(){
        document.getElementById("order").reset();
    });

    //是否已阅读
    $(".law").tap(function(){
       if($(this).find(".choose").hasClass("on")){
           $(this).find(".choose").removeClass("on");
       }else{
           $(this).find(".choose").addClass("on");
       }
    });

    //详情
    $("#order_detail").click(function(){
        $(".order_modal").show();
    });

    $(".order_modal #modal_close").click(function(){
        setTimeout(function(){
            $(".order_modal").hide();
        },368);
    });
});