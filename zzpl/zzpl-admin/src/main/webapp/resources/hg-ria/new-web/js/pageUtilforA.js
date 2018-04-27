/* ===========================================================
 * pageUtil.js v2.0 新添加回调函数功能
 * Core code of pageUtil plugin
 * http://duapp.iceberg.com/plugin/pageUtil
 * ===========================================================
 * Author : xiaoyuzhzh
 *          Twitter : 
 *          Website : 
 */

(function($){
	$.pageBar = {
			params:{
				action:{},//应该是个回调函数
				async:false,//是否异步刷新页码
				peve:"peve",//上一页按钮class
				next:"next",//下一页按钮class
				jumpTo:"jumpTo",//跳转按钮class
				jumpToVal:"jumpToValue",//跳转数字的class
				pageNo:"pageNo",//页码class或者页码值
				pageSize:"pageSize",//页面大小class或者页面大小值
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
		this.o = $.extend(true,{},$.pageBar.params,opts);
		this.init();
	}
	
	PageBar.prototype = {
		init: function(){
			/**
			 * 让组件的文字不能被选择
			 */
			var cssObj = {
					"-moz-user-select":"none",/*火狐*/
					"-webkit-user-select":"none",/*webkit浏览器*/
					"-ms-user-select":"none",/*IE10*/
					"-khtml-user-select":"none",/*早期浏览器*/
					"user-select":"none"
			}
			this.$box.css(cssObj);
			
			/**
			 * action存在则启动回调方法模式
			 */
			if($.isFunction(this.o.action)){
				this.callback = true;
				this.action = this.o.action;
				this.async = this.o.async;
			}
			this.pageNo = this.o.pageNo;
			this.pageSize = this.o.pageSize;
			this.pageCount = this.o.pageCount;
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
				var current = that.getCurrent();
				that.peve(current);
			});
			/**
			 * 下一页的按钮
			 */
			$("."+this.o.next,this.Hbox).on("click",function(){
				var current = that.getCurrent();
				that.next(current);
			});
			/**
			 * 跳转按钮
			 */
			$("."+this.o.jumpTo,this.Hbox).on("click",function(){
				/*alert("准备跳转");*/
				var jumpVal = $("input."+that.o.jumpToVal,that.Hbox).val();
				if(jumpVal == ""){
					alert("请先输入页码");return false;
				}
				that.jumpTo(jumpVal);
			});
		},
		/**
		 * 获取当前页
		 */
		getCurrent:function(){
			var current;
			if(this.callback){//回调函数模式
				current = this.pageNo;
			}else{
				current = $("."+this.o.formClass+" input."+this.o.pageNo).val();
			}
			return current;
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
			if(current>=this.pageCount){
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
			if(tar<1||tar>this.pageCount){
				alert("页码超出了范围");return false;
			}
			if(this.callback){
				var result = this.action(this.pageSize,tar);
				if(!this.async){
					//如果action是异步的，那result将不会拿到正确的值，需要用户在action中调用recreatePage方法重新刷新页码
					this.pageSize = result.pageSize;
					this.pageNo = result.pageNo;
					this.pageCount = result.pageCount;
					this.createPages();
				}
			}else{
				$("."+this.o.formClass+" input."+this.o.pageNo).val(tarPage);
				$("."+this.o.formClass).submit();
			}
		},
		
		/**
		 * js刷新组件状态(需要有回调方法支持)
		 */
		reset:function(){
			this.pageCount = 1;
			this.pageSize = 5;
			if(this.callback){
				this.jumpTo(1);
			}else{
				console.log('该分页组件不是回调函数状态');
			}
		},
		/**
		 * 重新刷新组件页码
		 */
		recreatePage:function(pageSize,pageNo,pageCount){
			this.pageSize = pageSize?pageSize:5;
			this.pageNo = pageNo?pageNo:1;
			this.pageCount = pageCount?pageCount:1;
			this.createPages();
		},
		/**
		 * 创建组件
		 */
		createComp: function(){
			//上一页与下一页的跳转组件
			var componentStr = '<span class="peve"><a  href="javascript:">上一页</a></span><span class="pageBtns"></span><span class="next"><a  href="javascript:">下一页</a></span><div class="page—ji">去第<input type="text" onkeyup="this.value=this.value.replace(/\\D/g,\'\')" class="jumpToValue">页 <a class="jumpTo"  href="javascript:">GO</a></div>';
			this.$box.append(componentStr);
		},
		
		/**
		 * 创建详细页码
		 */
		createPages:function(){
			//清空所有页码
			$("."+this.o.pageBtns,this.Hbox).empty();
			var that = this;
			//获取当前页码和总页数
			var current = this.getCurrent();
			current = parseInt(current);
			var totalCount = this.pageCount;
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
				$("."+this.o.pageBtns,this.Hbox).append('<a  href="javascript:" class="pageN'+i+'">'+i+'</a>');
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