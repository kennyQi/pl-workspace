/* ===========================================================
 * pageUtil.js v1.1
 * Core code of pageUtil plugin
 * http://duapp.iceberg.com/plugin/pageUtil
 * ===========================================================
 * Author : xiaoyuzhzh
 *          Twitter : 
 *          Website : 
 */

(function($){
	$.pageBar = {
			classNames:{
				peve:"peve",//上一页按钮class
				next:"next",//下一页按钮class
				jumpTo:"jumpTo",//跳转按钮class
				jumpToVal:"jumpToValue",//跳转数字的class
				pageNum:"pageNum",//页码class
				pageSize:"pageSize",//页面大小class
				pageCount:1,//总页数
				formClass:"pageForm",//分页表单class
				pageBtns:"pageBtns"
			}
	}
	/**
	 * 给jQuery添加初始化page工具方法
	 */
	$.fn.pageBar = function(opts){
		//alert("这个是添加的jQUery的原型方法");
		//传入的是对象(不可以含有原型参数)或者是空参数
		if($.isObject(opts) || opts == null){
            return this.each(function(){
            	if(!$(this).data("pageBar"))
            		$(this).data("pageBar",new PageBar(this,opts));
            });
        }
	}
	
	/**
	 * 分页工具的构造函数
	 */
	var PageBar = function(pageDiv,opts){
		this.$box = $(pageDiv);
		this.Hbox = pageDiv;
		this.o = $.extend(true,{},$.pageBar.classNames,opts);
		this.init();
	}
	
	PageBar.prototype = {
		init: function(){
			/**
			 * 将this的参数记录下来，因为在onclick方法中的this上下文会变化，不好直接引用this中的变量
			 */
			var that = this;
			/**
			 * 添加组件
			 */
			this.createComp();
			/**
			 * 刷出可直接点击的页码
			 */
			this.createPages();
			
			/**
			 * 上一页的按钮
			 */
			$("."+this.o.peve,this.Hbox).on("click",function(){
				var current = $("."+that.o.formClass+" input."+that.o.pageNum).val();
				that.peve(current);
			});
			/**
			 * 下一页的按钮
			 */
			$("."+this.o.next,this.Hbox).on("click",function(){
				var current = $("."+that.o.formClass+" input."+that.o.pageNum).val();
				that.next(current);
			});
			/**
			 * 跳转按钮
			 */
			$("."+this.o.jumpTo,this.Hbox).on("click",function(){
//				alert("准备跳转");
				var jumpVal = $("input."+that.o.jumpToVal,that.Hbox).val();
				if(jumpVal == ""){
					alert("请先输入页码");return false;
				}
				that.jumpTo(jumpVal);
			});
		},
		/**
		 * 上一页
		 */
		peve: function(current){
			var current = parseInt(current);
			if(current<=1){
				alert("已经是第一页了");
				return;
			}
			this.jumpTo(current-1);
		},
		/**
		 * 下一页
		 */
		next: function(current){
			var current = parseInt(current);
			if(current>=this.o.pageCount){
				alert("已经是最后一页了");
				return;
			}
			this.jumpTo(current+1);
		},

		/**
		 * 跳转
		 */
		jumpTo: function(tarPage){
//			alert(tarPage);
			var tar = parseInt(tarPage);
			if(isNaN(tar)){
				alert("页码输入错误");
				$(".jumpToValue").val("");
				return false;
			}
			if(tar<1||tar>this.o.pageCount){
				alert("页码超出了范围");return false;
			}
			$("."+this.o.formClass+" input."+this.o.pageNum).val(tarPage);
			$("."+this.o.formClass).submit();
		},
		
		/**
		 * 创建组件
		 */
		createComp: function(){
			//上一页与下一页的跳转组件
/*<<<<<<< HEAD
			var componentStr = '<span class="peve"><a href="javascript:" >上一页</a></span><span class="pageBtns"></span><span class="next"><a href="javascript:"  >下一页</a></span><div class="page—ji">去第<input type="text" class="jumpToValue" onpaste="return false" oncontextmenu = "return false;">页 <a class="jumpTo" href="javascript:"  >GO</a></div>';
			this.$box.append(componentStr);
=======*/
			//var componentStr = '<span class="peve"><a >上一页</a></span><span class="pageBtns"></span><span class="next"><a >下一页</a></span><div class="page—ji">去第<input type="text" class="jumpToValue" onpaste="return false" oncontextmenu = "return false;">页 <a class="jumpTo" >GO</a></div>';
			var componentStr = '<span class="peve"><a class="up" href="javascript:void(0)"><</a></span><span class="pageBtns"></span><span class="next"><a href="javascript:void(0)">></a></span><div class="page—ji"></div>';
			this.$box.append(componentStr);  
/*>>>>>>> hsl-1.4.3.1*/
		},
		/**
		 * 创建详细页码
		 */
		createPages:function(){
			var that = this;
			//获取当前页码和总页数
			var current = $("."+this.o.formClass+" input."+this.o.pageNum).val();
			current = parseInt(current);
			var totalCount = this.o.pageCount;
			var headOne = 1;//页码起始值
			var endOne = 0;//页码终止值
			if(current>3){
				headOne = current-2;
			}
			if((totalCount-current)>2&&totalCount>4){
				endOne = headOne+4;
			}else{
				endOne = totalCount;
				if(totalCount>4){
					headOne = totalCount-4;
				}else{
					headOne = 1;
				}
			}
			for(var i = headOne ; i <= endOne ; i ++){
/* <<<<<<< HEAD
				$("."+this.o.pageBtns,this.Hbox).append('<a href="javascript:"  class="pageN'+i+'">'+i+'</a>');
=======*/
				$("."+this.o.pageBtns,this.Hbox).append('<a class="pageN'+i+'" href="javascript:void(0)">'+i+'</a>');
/*>>>>>>> hsl-1.4.3.1*/
			}
			//选中当前页按钮
			$("."+this.o.pageBtns+" a.pageN"+current,this.Hbox).addClass("sel");
			//给值点击按钮添加监听
			$("."+this.o.pageBtns+" a",this.Hbox).on("click",function(){
//				alert("准备直接跳转到页面");
				that.jumpTo($(this).text());
			});
		}
	}
	 
	
	/**
	 *  isObject 方法
	 */
    var toString = Object.prototype.toString, hasOwnProp = Object.prototype.hasOwnProperty;
    $.isObject = function(obj) { 
    	if(toString.call(obj) !== "[object Object]") return false; 
    	var key; 
    	for(key in obj){} 
    	return !key || hasOwnProp.call(obj, key); 
    	};
    $.isString = function(str){ return typeof(str) === 'string' };

})(jQuery);
