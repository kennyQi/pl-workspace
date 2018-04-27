//=============================================================================
// 文件名:		common.js
// 版权:		Copyright (C) 2015 ply
// 创建人:		han.zw
// 创建日期:	2015-4-14
// 描述:		此文件修改请通知作者
// *************常用代码示例: ***********

//jquery cookie

(function (factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD. Register as anonymous module.
        define(['jquery'], factory);
    } else {
        // Browser globals.
        factory(jQuery);
    }
}(function ($) {

    var pluses = /\+/g;

    function encode(s) {
        return config.raw ? s : encodeURIComponent(s);
    }

    function decode(s) {
        return config.raw ? s : decodeURIComponent(s);
    }

    function stringifyCookieValue(value) {
        return encode(config.json ? JSON.stringify(value) : String(value));
    }

    function parseCookieValue(s) {
        if (s.indexOf('"') === 0) {
            // This is a quoted cookie as according to RFC2068, unescape...
            s = s.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, '\\');
        }

        try {
            // Replace server-side written pluses with spaces.
            // If we can't decode the cookie, ignore it, it's unusable.
            s = decodeURIComponent(s.replace(pluses, ' '));
        } catch(e) {
            return;
        }

        try {
            // If we can't parse the cookie, ignore it, it's unusable.
            return config.json ? JSON.parse(s) : s;
        } catch(e) {}
    }

    function read(s, converter) {
        var value = config.raw ? s : parseCookieValue(s);
        return $.isFunction(converter) ? converter(value) : value;
    }

    var config = $.cookie = function (key, value, options) {

        // Write
        if (value !== undefined && !$.isFunction(value)) {
            options = $.extend({}, config.defaults, options);

            if (typeof options.expires === 'number') {
                var days = options.expires, t = options.expires = new Date();
                t.setDate(t.getDate() + days);
            }

            return (document.cookie = [
                encode(key), '=', stringifyCookieValue(value),
                options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
                options.path    ? '; path=' + options.path : '',
                options.domain  ? '; domain=' + options.domain : '',
                options.secure  ? '; secure' : ''
            ].join(''));
        }

        // Read

        var result = key ? undefined : {};

        // To prevent the for loop in the first place assign an empty array
        // in case there are no cookies at all. Also prevents odd result when
        // calling $.cookie().
        var cookies = document.cookie ? document.cookie.split('; ') : [];

        for (var i = 0, l = cookies.length; i < l; i++) {
            var parts = cookies[i].split('=');
            var name = decode(parts.shift());
            var cookie = parts.join('=');

            if (key && key === name) {
                // If second argument (value) is a function it's a converter...
                result = read(cookie, value);
                break;
            }

            // Prevent storing a cookie that we couldn't decode.
            if (!key && (cookie = read(cookie)) !== undefined) {
                result[name] = cookie;
            }
        }

        return result;
    };

    config.defaults = {};

    $.removeCookie = function (key, options) {
        if ($.cookie(key) !== undefined) {
            // Must not alter options, thus extending a fresh object...
            $.cookie(key, '', $.extend({}, options, { expires: -1 }));
            return true;
        }
        return false;
    };

}));



var ply={
	version: '1.0',
    author: "han.zw",
	// 获取QueryString的参数
	GetQueryString: function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
		  return r[2];
	   return "";
	}
	//鼠标移上出现大图
	,imgFloat:function(obj){
		//obj浮动对象
		obj.hover(function(){
			var html='<div style="left:0px;top:0px;border:3px solid #fff;z-index:100000;position:absolute;display:none;" float="float"></div>';
			$("body").append(html);
			var $airImgBox=$("[float='float']")
			//获取图像
			$airImgBox.html($(this).clone());
			var wid=$airImgBox.find("img").width()
				,docWid=$(document).width()
				;
				$(document).mousemove(function (e) {  
					var x 
						,y
						;
					x=e.pageX;  
					y=e.pageY; 
					$airImgBox.css("top",y+5);
					if(docWid<(x+wid+30)){
						$airImgBox.css("left",(x-5-wid));
					}else{
						$airImgBox.css("left",x+5);
					}
					$airImgBox.fadeIn(400);
					
				})  
		},function(){
			$("[float='float']").fadeOut(400,function(){$(this).remove()});
		});
	}
	//json替换html模板
	,stringReplace_com:function(data,template) {
		var arr=data;
		var str="";
		for(var i=0;i<arr.length;i++){
			
			str+=template.replace(/\{\w+\}/g, function(word) {
				word=word.replace("{","");
				word=word.replace("}","");
				if(typeof arr[i][word]=="object"&&arr[i][word]!=""){
					var html="";
					for(var q=0;q<arr[i][word].length;q++){
						html+=","+arr[i][word][q].tagName;
					}
					html=html.slice(1);
					return html;
				}else{
					if(!UqUi.isnull(arr[i][word])){
						return arr[i][word];
					}else{
						return "";
					}
				}
			});
		}
		return str;
	}
	//检测属性同名
	,checkName:function(name){
		for(var x in ply){
			if(x==name){
				console.log(name+" is aleady use");
			} 
		}
	}
	//检测手机号
	,mobileReg:/^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/
	
};

ply.history={
    version:1.1,
    editor:"hzw",
    option:{
        url:"",
        title:"",
        price:"",
        img:"",
        top:100,//显示历史记录高度
        hideHeight:800,//隐藏历史记录高度
        bodyWidth:1200,
        cookieName:"jiudian",
        num:5
        ,expires:3
    },
    start:function(option){
        var opt= $.extend(this.option,option);
        this.checkCookie(opt);
    },
    checkCookie:function(opt){
        console.log($.cookie(opt.cookieName));
        if($.cookie(opt.cookieName)==undefined){
            this.setCookie(opt);
        }else{
            this.createHis(opt);
            this.difCookie(opt);
        }
    },
    setCookie:function(opt){
        $.cookie(opt.cookieName,'[{url:"'+opt.url+'",img:"'+opt.img+'",title:"'+opt.title+'",price:"'+opt.price+'"}]',{expires:opt.expires});
    },
    createHis:function(opt){
        var cookieArr;
        eval("cookieArr="+$.cookie(opt.cookieName));
        var html=' <div class="g_hist pa" style="z-index:333">'+
            '<h3 class="tt pl color6 h3 yahei">'+
            ' 您的浏览记录'+
            ' <i class="pa curp close" id="closeHist" a="#808080" b="#ffffff"></i>'+
            ' </h3>'+
            '<ul class="hist_list">';
        for(var i=0;i<cookieArr.length;i++){
            html+='<li>'+
            ' <a href="'+cookieArr[i].url+'">'+
            '<img src="'+cookieArr[i].img+'" class="pic"><!--尺寸是160*116-->'+
            '<span class="detail h4 color6 disb">'+cookieArr[i].title+'</span>'+
            '<span class="price disb h2 yahei">￥'+cookieArr[i].price+' 起</span>'+
            ' </a>'+
            ' </li>';
        }
        html+= '</ul>'+
        '</div>';
        $("body").append(html);
        var histWidth=$(".g_hist").width();
        var windowW=$("body").width();
        if(((windowW-opt.bodyWidth)/2-histWidth)>20){
         $(".g_hist").css({"top":opt.top,right:(windowW-opt.bodyWidth)/2-histWidth-20});
        }else{
            $(".g_hist").css({"top":opt.top,right:(windowW-opt.bodyWidth)/2-histWidth});
        }

        $(window).scroll(function(){
            //回顶部按钮
             if($(window).scrollTop()>opt.hideHeight){
                $(".g_hist").css({"top":opt.top,"position":"absolute"});
            }else if($(window).scrollTop()>opt.top){
                $(".g_hist").css({"top":0,left:"","position":"fixed"});
            }else{
                $(".g_hist").css({"top":opt.top,"position":"absolute"});
            }
        });

        $("#closeHist").click(function(){
            $(this).closest(".g_hist").hide();
        });

    },
    difCookie:function(opt){
        var cookieArr,add=false;
        var newCookie=opt.url;
        eval("cookieArr="+$.cookie(opt.cookieName));
        for(var i=0;i<cookieArr.length;i++){
            if(cookieArr[i].url==newCookie){
                add=true;
            }
        }
        if(add==false){
            this.addCookie(opt);
        }
    },
    addCookie:function(opt){
        var cookieArr,cookieNew="";
        eval("cookieArr="+$.cookie(opt.cookieName));
        var newCookie={
            url:opt.url,
            img:opt.img,
            title:opt.title,
            price:opt.price
        };
        if(cookieArr.length>=opt.num){
            cookieArr.shift();
        }
            cookieArr.push(newCookie);

        for(var i= 0,l=cookieArr.length;i<l;i++){
            cookieNew+=",{"+
            'url:"'+ cookieArr[i].url+
            '",img:"'+cookieArr[i].img+
            '",title:"'+cookieArr[i].title+
            '",price:"'+cookieArr[i].price+
            '"}';
        }
        cookieNew="["+cookieNew.slice(1);
        cookieNew+="]";
        $.cookie(opt.cookieName,cookieNew);
    }

};


$(function(){
	$("#ewm").hover(function(){
		$("#ewmBox").fadeIn(502);
	},function(){
		$("#ewmBox").fadeOut(502);
	});
	

	
	//返回顶部
	$(".g_totop").hover(function(){
		$(this).addClass("g_totop_on").html("返回顶部");	
	},function(){
		$(this).removeClass("g_totop_on").html("");	
	});
	$(".g_totop").click(function(){
		$("html, body").animate({scrollTop:0},400);
	});
	
	$(window).scroll(function(){
		if($(window).scrollTop()>=700){
			$(".g_totop").show();
		}else{
			$(".g_totop").hide();
		}
	});
});

//关闭按钮统一样式
$(document).on("mouseover",".close",function(){
    var a=$(this).attr("a");
    $(this).css({"border-color":a});
});
$(document).on("mouseout",".close",function(){
    var b=$(this).attr("b");
    $(this).css({"border-color":b});
});
