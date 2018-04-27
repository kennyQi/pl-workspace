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
require(["jquery","moment","script/createDate"],
    function($,moment,cDate) {
        /**
         * Created by Administrator on 2015/10/20.
         */
        var allCity = ['阿克苏|Akesu|aks|AKU','阿勒泰|Aletai|alt|AAT','阿尔山|Aershan|aes|YIE','阿里|Ali|al|NGQ','安康|Ankang|ak|AKA','安庆|Anqing|aq|AQG','安顺|Anshun|as|AVA','鞍山|Anshan|as|AOG','百色|Baise|bs|AEB','包头|Baotou|bt|BAV','巴彦淖尔|Bayannaoer|byne|RLK','博乐|Bole|bl|BPL','保山|Baoshan|bs|BSD','北海|Beihai|bh|BHY','北京|Beijing|bj|PEK','北京南苑|Beijingnanyuan|bjny|NAY','毕节|Bijie|bj|BFJ','昌都|Changdu|cd|BPX','常德|Changde|cd|CGD','常州|Changzhou|cz|CZX','朝阳|Chaoyang|cy|CHG','成都|Chengdu|cd|CTU','池州|Chizhou|cz|JUH','九华山|Jiuhuashan|jhs|JUH','赤峰|Chifeng|cf|CIF','达州|Dazhou|dz|DAX','大理|Dali|dl|DLU','大连|Dalian|dl|DLC','大庆|Daqing|dq|DQA','大同|Datong|dt|DAT','丹东|Dandong|dd|DDG','迪庆|Diqing|dq|DIG','东营|Dongying|dy|DOY','稻城|Daocheng|dc|DCY','敦煌|Dunhuang|dh|DNH','鄂尔多斯|Eerduosi|eeds|DSN','恩施|Enshi|es|ENH','额济纳旗桃来|Ejinaqitaolai|ejnqtl|EJN','二连浩特|erlianhaote|elht|ERL','佛山|Foshan|fs|FUO','福州|Fuzhou|fz|FOC','抚远|Fuyuan|fy|FYJ','阜阳|Fuyang|fy|FUG','富蕴|Fuyun|fy|FYN','赣州|Ganzhou|gz|KOW','格尔木|Geermu|gem|GOQ','固原|Guyuan|gy|GYU','广汉|Guanghan|gh|GHN','广元|Guangyuan|gy|GYS','广州|Guangzhou|gz|CAN','贵阳|Guiyang|gy|KWE','桂林|Guilin|gl|KWL','哈尔滨|Haerbin|heb|HRB','哈密|Hami|hm|HMI','海口|Haikou|hk|HAK','海拉尔|Hailaer|hle|HLD','邯郸|Handan|hd|HDG','汉中|Hanzhoung|hz|HZG','杭州|Hangzhou|hz|HGH','合肥|Hefei|hf|HFE','和田|Hetian|ht|HTN','黑河|Heihe|hh|HEK','淮安|Huaian|ha|HIA','呼和浩特|huhehaote|hhht|HET','怀化|Huaihua|hh|HJJ','黄山|Huangshan|hs|TXN','惠州|Huizhou|hz|HUZ','鸡西|Jixi|jx|JXA','吉林|Jilin|jl|JIL','济南|Jinan|jn|TNA','济宁|Jining|jn|JNG','加格达奇|Jiagedaqi|jgde|JGD','佳木斯|Jiamusi|jms|JMU','嘉峪关|Jiayuguan|jyg|JGN','揭阳|Jieyang|jy|SWA','金昌|Jinchang|jc|JIC','锦州|Jinzhou|jz|JNZ','晋江|Jinjiang|jj|JJN','井冈山|Jinggangshan|jgs|JGS','景德镇|Jingdezhen|jdz|JDZ','九江|Jiujiang|jj|JIU','九寨沟|Jiuzhaigou|jzg|JZH','酒泉|Jiuquan|jq|CHW','喀纳斯|Kanasi|nks|KJI','喀什|Kashi|ks|KHG','康定|Kangding|kd|KGT','克拉玛依|Kelamayi|klmy|KRY','库车|Kuche|kc|KCA','库尔勒|Kuerle|kel|KRL','昆明|Kunming|km|KMG','拉萨|Lasa|ls|LXA','兰州|Lanzhou|lz|LHW','黎平|Liping|lp|HZH','丽江|Lijiang|lj|LJG','荔波|Libo|lb|LLB','连云港|Lianyungang|lyg|LYG','林芝|Linzhi|lz|LZY','临沧|Lincang|lc|LNJ','临沂|Linyi|ly|LYI','吕梁|Lvliang|ll|LLV','柳州|Liuzhou|lz|LZH','龙岩|Longyan|ly|LCX','泸州|Luzhou|lz|LZO','洛阳|Luoyang|ly|LYA','满洲里|Manzhouli|mzl|NZH','芒市|Mangshi|ms|LUM','梅县|Meixian|mx|MXZ','绵阳|Mianyang|my|MIG','漠河|Mohe|mh|OHE','牡丹江|Mudanjiang|mdj|MDG','那拉提|NaLaTi|nlt|NLT','南昌|Nanchang|nc|KHN','南充|Nanchong|nc|NAO','南京|Nanjing|nj|NKG','南宁|Nanning|nn|NNG','南通|Nantong|nt|NTG','南阳|Nanyang|nn|NNY','宁波|Ningbo|nb|NGB','攀枝花|Panzhihua|pzh|PZI','普洱|Puer|pe|SYM','齐齐哈尔|Qiqihaer|qqhe|NDG','黔江|Qianjiang|qj|JIQ','且末|Qiemo|qm|IQM','秦皇岛|Qinghuangdao|qhd|SHP','青岛|Qingdao|qd|TAO','庆阳|Qingyang|qy|IQN','日喀则|rikaze|rkz|RKZ','衢州|Quzhou|qz|JUZ','三亚|Sanya|sy|SYX','厦门|Xiamen|xm|XMN','上海|shanghai|sh|SHA','神农架|Shennongjia|snj|HPG','上海浦东|Shanghaipudong|shpd|PVG','深圳|Shenzhen|sz|SZX','沈阳|Shenyang|sy|SHE','唐山|Tangshan|ts|TVS','石家庄|Shijiazhuang|sjz|SJW','苏州|Suzhou|sz|SZV','遂宁|Suining|sn|SUN','塔城|Tacheng|tc|TCG','台州|Taizhou|tz|HYN','太原|Taiyuan|ty|TYN','腾冲|Tengchong|tc|TCZ','天津|Tianjin|tj|TSN','天水|Tianshui|ts|THQ','通化|Tonghua|th|TNH','通辽|Tongliao|tl|TGO','铜仁|Tongren|tr|TEN','吐鲁番|Tulufan|tlf|TLQ','万州|Wanzhou|wz|WXN','威海|Weihai|wh|WEH','潍坊|Weifang|lf|WEF','温州|Wenzhou|wz|WNZ','文山|Wenshan|ws|WNH','乌海|Wuhai|wh|WUA','乌兰浩特|Wulanhaote|wlht|HLH','乌鲁木齐|Wulumuqi|wlmq|URC','无锡|Wuxi|wx|WUX','梧州|Wuzhou|wz|WUZ','武汉|Wuhan|wh|WUH','武夷山|Wuyishan|wys|WUS','西安|Xian|xa|XIY','西昌|Xichang|xc|XIC','西宁|Xining|xn|XNN','西双版纳|Xishuangbanna|xsbn|JHG','锡林浩特|Xilinhaote|xnht|XIL','襄阳|Xiangyang|xy|XFN','兴城|Xingcheng|xc|XEN','兴义|Xingyi|xy|ACX','徐州|Xuzhou|xz|XUZ','烟台|Yantai|yt|YNT','延安|Yanan|ya|ENY','延吉|Yanji|yj|YNJ','盐城|Yancheng|yc|YNZ','扬州|Yangzhou|yz|YTY','伊春|Yichun|yc|LDS','伊宁|Yining|yn|YIN','宜宾|Yibin|yb|YBP','宜昌|Yichang|yc|YIH','宜春|Yichun|yc|YIC','义乌|Yiwu|yw|YIW','银川|Yinchuan|yc|INC','永州|Yongzhou|yz|LLF','榆林|Yulin|yl|UYN','玉树|Yushu|ys|YUS','运城|Yuncheng|yc|YCU','湛江|Zhanjiang|zj|ZHA','张家界|Zhangjiajie|zjj|DYG','张家口|Zhangjiakou|zjk|ZQZ','长白山|Changbaishan|cbs|NBS','长春|Changchun|cc|CGQ','长沙|Changsha|cs|CSX','长治|Changzhi|cz|CIH','昭通|Zhaotong|zt|ZAT','郑州|Zhengzhou|zz|CGO','中卫|Zhongwei|zw|ZHY','重庆|Chongqing|cq|CKG','舟山|Zhoushan|zs|HSN','珠海|Zhuhai|zh|ZUH','张掖|Zhangye|zy|YZY','遵义|Zunyi|zy|ZYI','左旗巴彦浩特|Zuoqibayanhaote|zqbyht|AXF','右旗巴丹吉林|Youqibadanjilin|yqbdjl|RHT'];
        var allCityObj=[];
        for(var i= 0,l=allCity.length;i<l;i++){
            allCityObj[i]={};
            allCityObj[i].city=allCity[i].split("|")[0];
            allCityObj[i].py=allCity[i].split("|")[1];
            allCityObj[i].smpy=allCity[i].split("|")[2];
            allCityObj[i].data=allCity[i].split("|")[3];
        }
        allCityObj.sort(compare);

        function compare(v1,v2){
            return v1.smpy.charCodeAt(0)- v2.smpy.charCodeAt(0)
        }

        var cityHtml="";
        var cityEn="A";
        cityHtml+='<dt><a name="'+cityEn+'">'+cityEn+'</a></dt>';
        for(var i= 0,l=allCityObj.length;i<l;i++){
            if(allCityObj[i].smpy.substring(0,1).toUpperCase()!=cityEn){
                cityEn=allCityObj[i].smpy.substring(0,1).toUpperCase();
                cityHtml+='<dt><a name="'+cityEn+'">'+cityEn+'</a></dt>';
            }
            /*if(i==2){
             cityHtml+='<dd  class="cityTap cityOn">'+allCityObj[i].city+'</dd>';
             }*/
            cityHtml+='<dd  class="cityTap" py="'+allCityObj[i].py+'" smpy="'+allCityObj[i].smpy+'" for="'+cityEn+'" data="'+allCityObj[i].data+'">'+allCityObj[i].city+'</dd>';
        }
        $(function(){
            //默认显示今天时间
            var nowTime=new Date();
            $("#cityDate input").val(moment(nowTime).format("MM-DD")).attr("relTime",moment(nowTime).format("YYYY-MM-DD"));
            $(".startDate").attr("value",moment(nowTime).format("YYYY-MM-DD"));
            $(".flyDay").attr("value","今天");
            $("#cityList").append(cityHtml);

            //城市根据字母定位
            $(".cityFast a").click(function(e){
                e.preventDefault();
                var href=$(this).attr("href");
                $(".pyBox").show().find("label").html(href.slice(1));
                setTimeout(function(){
                    $(".pyBox").hide();
                },300);
                $(window).scrollTop($(".cityAll a[name='"+href.slice(1)+"']").offset().top-96);
            });

            //日历
            cDate({direct:"dateDetail"
                ,showMonth:12
            });
            $(".can").eq(0).find("div").html("今天");
            $(".can").eq(1).find("div").html("明天");
            $(".can").eq(2).find("div").html("后天");

            //选择城市
            $("#cityFrom,#cityTo").click(function(){

                $("dd.cityTap").removeClass("cityOn").siblings(":contains("+$(this).find("input").val()+")").addClass("cityOn");
                //设置城市默认位置

                $("#city").attr("relId",this.id).css({"display":"",
                    "position":"absolute",
                    "-webkit-transform":"translateX(505px)",
                    "left":"0px",
                    "z-index":505,
                    "top": "0px"}).animate({ "z-index":"0"}, {
                    duration:400,
                    step: function(now,fx) {
                        $("#city").css('-webkit-transform','translateX('+now+'px)');
                    }});

                setTimeout(function(){
                    $("#serchIndex").hide();
                    $("#city").removeAttr("style");
                },410);

             /*   $("#serchIndex").hide();
                $("#city").attr("relId",this.id).show(10,function(){
                    $("#city").removeAttr("style");
                });*/

            });

            //搜索
            $(".inSearchTips").click(function(){
                $(this).hide();
                $(".inSearch").show().find("input").focus();
            });

            $(".inSearch input").keyup(function(){
                var val=$(this).val().trim().toLowerCase();
                if(val){
                    $(".hotList span.cityTap,.hotTitle").hide();//隐藏热门城市
                    $("#cityList dt,#cityList dd").hide();//隐藏所有城市
                    $("#cityList dd").each(function(){
                    	console.log($(this).attr("py").indexOf(val));
                    	console.log($(this).attr("smpy").indexOf(val));
                    	console.log($(this).html().indexOf(val));
                        if($(this).attr("py").toLowerCase().indexOf(val)!=-1||$(this).attr("smpy").indexOf(val)!=-1||$(this).html().indexOf(val)!=-1)
                        {
                            $(this).show().show();
                            $("a[name='"+ $(this).attr("for")+"']").closest("dt").show();
                        }
                    });
                }else{
                    $(".hotList span.cityTap,.hotTitle").show();
                    $("#cityList dt,#cityList dd").show();
                }
            });
            //关闭搜索
            $(".inSearch label").click(function(){
                $(".inSearchTips").show();
                $(this).closest(".inSearch").hide().find("input").val("");
                $(".hotList span.cityTap,.hotTitle").show();
                $("#cityList dt,#cityList dd").show();
            });

            //选中城市
            $("dd.cityTap").click(function(){
                var name=$(this).html();
                var code=$(this).attr("data");
                $("dd.cityTap").removeClass("cityOn");
                $(this).addClass("cityOn");
                $("#"+$("#city").attr("relId")).find(".cityName").attr("value",name).val(name);
                $("#"+$("#city").attr("relId")).find(".cityCode").attr("value",code).val(code);
                
                $("span.cityTap").removeClass("on");
                $("span.cityTap:contains("+name+")").addClass("on");
                $(".backIndex").click();
                setTimeout(function(){
                    //清除搜索条件
                    $(".inSearch label").click();
                },400);
            });
            //热门城市
            $("span.cityTap").click(function(){
                var name=$(this).html();
                $("dd.cityTap:contains("+name+")").click();
            });

            //切换城市
            $("#serchIndex .change").click(function(){
                var fromCity =$("#cityFrom .cityName"),toCity=$("#cityTo .cityName");
                var fromCityCode =$("#cityFrom .cityCode"),toCityCode=$("#cityTo .cityCode");
                var from=fromCity.val();
                fromCity.val(toCity.val()).attr("value",toCity.val());
                toCity.val(from).attr("value",from);
                var fromCode = fromCityCode.val();
                fromCityCode.attr("value",toCityCode.val());
                toCityCode.attr("value",fromCode);
                var that=$(this);
                $("#cityTo input,#cityFrom input").addClass("changeCity");
                that.find("label").addClass("changeAction");
                setTimeout(function(){
                    that.find("label").removeClass("changeAction");
                    $("#cityTo input,#cityFrom input").removeClass("changeCity");
                },1000);
            });
            //返回首页
            $(".backIndex").click(function(){
                var that=$(this);
                $("#serchIndex").show();
                $(window).scrollTop(0);
                that.closest(".scene").css({"display":"",
                    "position":"absolute",
                    "-webkit-transform":"translateX(0px)",
                    "left":"0px",
                    "z-index":0,
                    "top": "0px"}).animate({ "z-index":"505"}, {
                        duration:400,
                        step: function(now,fx) {
                        that.closest(".scene").css('-webkit-transform','translateX('+now+'px)');
                    }});
                    setTimeout(function(){
                        that.closest(".scene").removeAttr("style").hide();
                },500);
            });

            //提交搜索
            $(".search-btn").click(function(){
            	var html="<div class='loading_bg'></div>";
            	$("body").append(html);
            	$("body").append('<div class="mainLoad"></div>');
                $(this).closest("form").submit();
            });
        });



//日期选择
        $(function(){
            var dayCh=["今天","明天","后天"];
            var dayEn=["周日","周一","周二","周三","周四","周五","周六"];
            //跳转日历页面
            $("#cityDate").click(function(){
                var time=$(this).find("input").attr("relTime");
                $("#dateDetail td").removeClass("on");
                $("#d_"+time).addClass("on");

                $("#date").css({"display":"",
                    "position":"absolute",
                    "-webkit-transform":"translateX(505px)",
                    "left":"0px",
                    "z-index":505,
                    "top": "0px"}).animate({ "z-index":"0"}, {
                    duration:400,
                    step: function(now,fx) {
                        $("#date").css('-webkit-transform','translateX('+now+'px)');
                    }});
                setTimeout(function(){
                    $("#serchIndex").hide();
                    $("#date").removeAttr("style");
                },500);

            });

            //日历点击
            $("#dateDetail").on("click",".can",function(){
                var time=this.id.slice(2);
                var day="",dayThan=0;
                $(".can").removeClass("on");
                $(this).addClass("on");
                $("#cityDate input").val(moment(time).format("MM-DD")).attr("relTime",moment(time).format("YYYY-MM-DD"));
                $(".startDate").attr("value",moment(time).format("YYYY-MM-DD"));
                $(".backIndex:visible").click();
                dayThan=moment(time).format("YYYYMMDD")-moment().format("YYYYMMDD");
                if(dayThan<3&&dayThan>=0){
                    day=dayCh[dayThan];
                }else{
                    day=dayEn[moment(time).day()];
                }
                $(".flyDay").attr("value",day);
                $("#serchIndex .fly-day").html(day);
            });




        });


    });
