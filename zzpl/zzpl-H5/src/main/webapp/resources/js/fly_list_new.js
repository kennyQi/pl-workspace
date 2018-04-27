require.config({
    //通用js模块路径
	baseUrl:request_path+"/resources/js/",
    //通用框架
    paths: {
        jquery: 'libs/jquery-2.1.4.min',
        moment: 'libs/moment.min'
    },

    waitSeconds: 0
});

//默认调用js
require(["jquery","moment"],
function($,moment) {

    $(function(){
        //loading
        $(".mainLoad").remove();
        $("body").append('<div class="mainLoad"></div>');
        //loadingClosed
        setTimeout(function(){
            $(".mainLoad").remove();
        },1500);

        setTimeout(function() {
            if(window.pageYOffset !== 0) return;
            window.scrollTo(0, window.pageYOffset + 1);
        }, 100);
        var width=$(window).width;
        var dayTime=$(".dayTime");
        var nowDay=moment().format("YYYYMMDD");
        var dayCh=["今天","明天","后天"];
        var dayEn=["周日","周一","周二","周三","周四","周五","周六"];

        //展开筛选框
        $("nav .s").click(function(){
            $('.flight-filter-bg').show();
            $(".flight-filter").animate({bottom:'0px'},300);
        })
        //条目切换
        var $flightChooseMenusLi = $('.flight-chooseMenus li');
        var $itemsboxUl = $('.itemsbox ul');
        $flightChooseMenusLi.click(function(){
            var ind = $(this).index();
            $(this).addClass('current').siblings().removeClass('current');
            $itemsboxUl.eq(ind).show().siblings().hide();
        });
        // 选中状态切换
        var $itemsboxLi = $('.itemsbox li');
        $itemsboxLi.click(function(){
            if(!$(this).hasClass('checked')){
                $(this).addClass('checked');
                if($(this).parent().find('.dot').nextAll().hasClass('checked')){
                    $(this).parent().find('.dot').removeClass('checked');
                }else{
                    $(this).parent().find('.dot').addClass('checked');
                }
            }else{
                $(this).removeClass('checked');
                if($(this).parent().find('.dot').nextAll().hasClass('checked')){
                    $(this).parent().find('.dot').removeClass('checked');
                }else{
                    $(this).parent().find('.dot').addClass('checked');
                }
            }
        });
        // 重置按钮
        var $flightResetBtn = $('.flight-reset-btn');
        $flightResetBtn.click(function(){
            $('.itemsbox').find('.checked').removeClass('checked');
            $('.itemsbox').find('.dot').addClass('checked');
        });
        // 取消按钮
        var $flightCancelBtn = $('.flight-cancel-btn');
        $flightCancelBtn.click(function(){
            $('.flight-filter-bg').hide();
            $('.flight-filter').animate({bottom:'-222px'},300);
        });
        //确认筛选条件


        // $("#filter .sure").click(function(){
        //     var searchTime=$(".fliterList").eq(0).find("dt label").html();
        //     var searchCompany=$(".fliterList").eq(1).find("dt label").html();
        //     console.log(searchTime,searchCompany);
        //     if(searchTime!="不限"||searchCompany!="不限"){
        //         $(".double").addClass("on");
        //     }else{
        //         $(".double").removeClass("on");
        //     }
        //     $("#filter").css({
        //         "z-index":"50"
        //     }).animate({ "z-index":"580"}, {
        //         duration:350,
        //         step: function(now,fx) {
        //             $("#filter").css('-webkit-transform','translateX('+now+'px)');
        //         }});
        // });

        //选择筛选
        // $(".fliterList dd").click(function(){
        //     $(this).closest(".fliterList").find("dd").removeClass("on");
        //     $(this).addClass("on").closest(".fliterList").find("dt label").html($(this).html());
        // });

        //下拉和收起筛选框
        // $(".fliterList dt").click(function(){
        //     $(this).nextAll().slideToggle();
        //     $(this).find("label").toggleClass("down");
        // });

        //重置

        // $("#filter .reset").click(function(){
        //     $(".fliterList").each(function(){
        //         $(this).find("dd").eq(0).click();
        //     })
        // });




        //展开机票
        $(".fly_box .found ").click(function(){
            $(this).closest(".fly_box").find(".ticket").slideToggle();
            $(this).find(".moreIcon").toggleClass("moreIconOn");
        });

        //切换时间*****************************************************
        //后一天
        $(".dayNext").click(function(){
            var time=dayTime.attr("data-time");
            var momentTime=moment(time).subtract(-1,"day");
            var momentDay=moment(momentTime).format("YYYYMMDD");
            var showDay="";
            if((momentDay-nowDay)<3&&(momentDay-nowDay)>=0){
                showDay=dayCh[(momentDay-nowDay)];
            }else{
                showDay=dayEn[momentTime.day()];
            }
            //动画
            dayTime.addClass("changeDayFlash").html(momentTime.format("YYYY-MM-DD")+" "+showDay).attr("data-time",momentTime.format("YYYY-MM-DD"));
            setTimeout(function(){
                dayTime.removeClass("changeDayFlash");
            },600);
        });
        //前一天
        $(".dayUp").click(function(){
            var time=dayTime.attr("data-time");
            var momentTime=moment(time).subtract(1,"day");
            var momentDay=moment(momentTime).format("YYYYMMDD");
            var showDay="";
            if((momentDay-nowDay)<3&&(momentDay-nowDay)>=0){
                showDay=dayCh[(momentDay-nowDay)];
            }else{
                showDay=dayEn[momentTime.day()];
            }
            //动画
            dayTime.addClass("changeDayFlash").html(momentTime.format("YYYY-MM-DD")+" "+showDay).attr("data-time",momentTime.format("YYYY-MM-DD"));
            setTimeout(function(){
                dayTime.removeClass("changeDayFlash")
            },600);
        });

        //底部筛选
        $("nav .single").click(function(){
            var that=$(this);
            if(that.hasClass("on")){
                if(that.hasClass("p")){
                    //价格排序
                    if(that.find("label").hasClass("down")){
                        //进行升序
                        console.log("价格升序");
                    }else{
                        console.log("价格降序");
                    }
                }
                if(that.hasClass("time")){
                    //起飞时间排序
                    if(that.find("label").hasClass("down")){
                        //进行升序
                        console.log("起飞时间升序");
                    }else{
                        console.log("起飞时间降序");
                    }
                }
                that.find("label").toggleClass("down");
            }else{
                $("nav .single").removeClass("on");
                that.addClass("on");
                if(that.hasClass("p")){
                    //价格排序
                    if(that.find("label").hasClass("down")){
                        console.log("价格降序");
                    }else{
                        //进行升序
                        console.log("价格升序");

                    }
                }
                if(that.hasClass("time")){
                    //起飞时间排序
                    if(that.find("label").hasClass("down")){
                        console.log("起飞时间降序");

                    }else{
                        //进行升序
                        console.log("起飞时间升序")

                    }
                }
            }

        });
    });
});
