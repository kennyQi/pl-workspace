//=============================================================================
// 文件名:		common.js
// 版权:		Copyright (C) 2015 ply
// 创建人:		han.zw
// 创建日期:	2015-4-14
// 描述:		此文件修改请通知作者
// *************常用代码示例: ***********


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
	
};

//图像上传控件
ply.control={
	uploadImg:function(option){
        option=option==undefined?{
            dir:$("body"),
            imgDir:$("img"),
            wid:100,
            hei:100,
            uploadUrl:""
        }:option;
        var html='<div id="uploadImg" style="z-index:10000;"><form action="'+option.uploadUrl+'"  method="post"  enctype="multipart/form-data" id="plUploadIform" target="plUploadIframe">'+
                '<input type="file" name="upload"  id="fileName" style="z-index:1000;cursor:pointer;width:'+option.wid+'px;height:'+option.hei+
                'px;opacity:0;filter:alpha(opacity=0);-moz-opacity:0;position:absolute;left:'+option.imgDir.offset().left+'px;top:'+option.imgDir.offset().top+'px;">'+
                ' <input type="submit" value="Upload file" style="display:none;" />'+
                '</form>';
        //html+='<iframe name="plUploadIframe" id="plUploadIframe" src=""></iframe></div>';
       option.dir.append(html);
        var objBody = document.getElementsByTagName("body").item(0);
        var iframe;
        try {
            iframe = document.createElement('<iframe name="plUploadIframe">');
        } catch (ex) {
            iframe = document.createElement('iframe');
        }
        iframe.id = 'plUploadIframe';
        iframe.name = 'plUploadIframe';
        iframe.width = 0;
        iframe.height = 0;
        iframe.style.display="none";
        iframe.marginHeight = 0;
        iframe.marginWidth = 0;
        objBody.insertBefore(iframe, objBody.firstChild);
        $("#fileName").change(function(){
            if(!($(this).val().indexOf(".jpg")!=-1)&&!($(this).val().indexOf(".png")!=-1)&&!($(this).val().indexOf(".gif")!=-1))
            {alert("只支持jpg,png,gif!");return false;}
            $("#plUploadIform").submit();
        });

        $("#plUploadIframe").load(function(){
            var io = document.getElementById("plUploadIframe");
            var xml = {};
            if(io.contentWindow)
            {
                xml.responseText = io.contentWindow.document.body?io.contentWindow.document.body.innerHTML:null;
                xml.responseXML = io.contentWindow.document.XMLDocument?io.contentWindow.document.XMLDocument:io.contentWindow.document;

            }else if(io.contentDocument)
            {
                xml.responseText = io.contentDocument.document.body?io.contentDocument.document.body.innerHTML:null;
                xml.responseXML = io.contentDocument.document.XMLDocument?io.contentDocument.document.XMLDocument:io.contentDocument.document;
            }
            var src=xml.responseText.slice(xml.responseText.indexOf(">")+1,xml.responseText.indexOf("</"));
            option.imgDir.attr("src");
        });
    }
};
	


$(function(){
	$("#ewm").hover(function(){
		$("#ewmBox").fadeIn(502);
	},function(){
		$("#ewmBox").fadeOut(502);
	});
	
	//关闭按钮统一样式
	$(".close").hover(function(){
		var a=$(this).attr("a");
		$(this).css({"border-color":a});
	},function(){
		var b=$(this).attr("b");
		$(this).css({"border-color":b});
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