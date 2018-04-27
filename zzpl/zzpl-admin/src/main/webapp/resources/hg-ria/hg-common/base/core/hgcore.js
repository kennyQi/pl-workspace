/**
 * @author zzb
 * @date 2014-10-14
 */
var HG = {
	// 状态码
	statusCode : {
		ok : 200,
		error : 300,
		timeout : 301
	},
	
	// alert message
	_msg: {}, 
	
	// 数据字典
	_word: {}, 
	
	// 基本设置属性
	_set: {
		proUrl: "",		 	// 项目路径
		loginUrl: "",	 	// session timeout
		homeUrl: "",	 	// 主页
		imagePriewUrl: "", 	// 图片预览
		imageUploadUrl: "", // 图片上传
		imageUploadTempUrl: "", // 图片临时上传
		debug: false
	},
	
	/**
	 * 获取配置的msg
	 */
	msg:function(key, args){
		var _format = function(str,args) {
			args = args || [];
			var result = str || "";
			for (var i = 0; i < args.length; i++){
				result = result.replace(new RegExp("\\{" + i + "\\}", "g"), args[i]);
			}
			return result;
		}
		return _format(this._msg[key], args);
	},
	
	/**
	 * 获取配置的word
	 */
	word:function(key, args){
		var _format = function(str,args) {
			args = args || [];
			var result = str || "";
			for (var i = 0; i < args.length; i++){
				result = result.replace(new RegExp("\\{" + i + "\\}", "g"), args[i]);
			}
			return result;
		}
		return _format(this._word[key], args);
	},
	
	/**
	 * debug 模式下输出数据
	 */
	debug: function(msg) {
		if (this._set.debug) {
			if (typeof(console) != "undefined") console.log(msg);
			else alert(msg);
		}
	},
	
	/**
	 * json数据eval
	 */
	jsonEval : function(data) {
		try {
			if ($.type(data) == 'string')
				return eval('(' + data + ')');
			else
				return data;
		} catch (e) {
			return {};
		}
	},
	
	/**
	 * ajax请求异常
	 */
	ajaxError : function(xhr, ajaxOptions, thrownError) {
		HG.messager("<div>Http status: " + xhr.status + " " + xhr.statusText
				+ "</div>" + "<div>ajaxOptions: " + ajaxOptions + "</div>"
				+ "<div>thrownError: " + thrownError + "</div>" + "<div>"
				+ xhr.responseText + "</div>", "error");
		HG.unblock();
	},
	
	/**
	 * ajax请求成功
	 */
	ajaxDone : function(json) {
		if (json.statusCode == HG.statusCode.error) {
			if (json.message)
				HG.messager(json.message, "error");
		} else if (json.statusCode == HG.statusCode.timeout) {
			// 登陆超时
			window.location = json.forwardUrl;
		} else {
			if (json.message)
				HG.messager(json.message, "success");
		}
	},
	
	/**
	 * json to string
	 */
	obj2str : function(o) {
		var r = [];
		if (typeof o == "string")
			return "\""
					+ o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n")
							.replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t")
					+ "\"";
		if (typeof o == "object") {
			if (!o.sort) {
				for ( var i in o)
					r.push(i + ":" + HG.obj2str(o[i]));
				if (!!document.all
						&& !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/
								.test(o.toString)) {
					r.push("toString:" + o.toString.toString());
				}
				r = "{" + r.join() + "}"
			} else {
				for (var i = 0; i < o.length; i++) {
					r.push(HG.obj2str(o[i]));
				}
				r = "[" + r.join() + "]"
			}
			return r;
		}
		return o.toString();
	},
	
	/**
	 * 获取el所属form
	 */
	getForm : function(el) {
		if (!el)
			return null;

		if (el.nodeName === 'FORM')
			return el;

		var parent = el.parentElement;
		if (parent.nodeName === 'FORM')
			return parent;
		return HG.getForm(parent)
	},
	
	/**
	 * 数组去重
	 */
	arrayUnique : function(arr) {
		var res = [ arr[0] ];
		for (var i = 1; i < arr.length; i++) {
			var repeat = false;
			for (var j = 0; j < res.length; j++) {
				if (arr[i] == res[j]) {
					repeat = true;
					break;
				}
			}
			if (!repeat) {
				res.push(arr[i]);
			}
		}
		return res;
	},
	
	/**
	 * 填充内容
	 */
	fullProperty: function(s, data) {			
		var self = this;
		return s.replace(/\{([\w\-\.]+)\}/g, function(m, name) {
			var val = self.property(data, name);
			return (val !== undefined && val !== null) ? val : '';
		});
	},
	
	/**
	 * 获取对象属性
	 */
	property: function(o, p, v) {				
		if(o) {
			var i = p.indexOf('.');
		    if (i > -1) {
		    	var name = p.substring(0, i > -1 ? i : p.length);
		    	return HG.property(o[name], p.substring(i + 1), v);
		    }
		    if (v) o[p] = v;
			return o[p];
		}
	},
	
	/**
	 * 获取随机数
	 */
	getRandom: function(n) {
		n = n || 10;
		var chars = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];

	    var res = "";
	    for (var i = 0; i < n ; i ++) {
	        var id = Math.ceil(Math.random() * 35);
	        res += chars[id];
	    }
	    return res;
	},
	
	getBooleanVal: function(str) {
		if($.trim(str) == "true") {
			return true;
		} else {
			return false;
		}
	},
	
	/**
	 * 锁屏 
	 */
	block: function(msg, $el) {
		
		var op = {
			boxed: true
		};
		if ($el) {
			op = $.extend(op, {
				target: $el[0]
			});
		}

		Metronic.blockUI(op);
		
	},
	
	/**
	 * 解锁
	 */
	unblock: function($el) {
		
		var op = {};
		if ($el) {
			op = {
				target: '#' + $el.attr('id')
			};
			Metronic.unblockUI(op);
		}
		Metronic.unblockUI();
	},
	
	/**
	 * 确认 
	 */
	confirm: function(title, message, deteFun, canFun) {
		
		title = title;
		message = message;
		deteFun = deteFun || function() {};
		canFun = canFun || function() {};
		
		var messDiv = '<h3><i class="fa fa-warning font-red-sunglo" style="font-size: 20px;"></i>' + message + '</h3>';
		
		bootbox.confirm(messDiv, function(result) {
			if(result) {
				deteFun();
			} else {
				canFun();
			}
		}); 
		
	},
	
	/**
	 * 消息
	 */
	messager: function(msg, type) {
		
		toastr[type](msg, HG.word("proInfo"));
	},
	
	/**
	 * 初始化
	 */
	init: function(pageFrag, options) {
		var op = $.extend({
			proUrl: 			"/",
			loginUrl: 			"/login",
			homeUrl: 			"/home",
			imagePriewUrl: 		"/image",
			imageUploadUrl: 	"/hg/album/image/imgUpload",
			imageUploadTempUrl: "/hg/album/image/imgUploadInTemp",
			statusCode:{}
		}, options);
		
		this._set.proUrl = op.proUrl;
		this._set.loginUrl = op.loginUrl;
		this._set.homeUrl = op.homeUrl;
		this._set.imagePriewUrl = op.imagePriewUrl;
		this._set.imageUploadUrl = op.imageUploadUrl;
		this._set.imageUploadTempUrl = op.imageUploadTempUrl;
		this._set.debug = op.debug;
		$.extend(HG.statusCode, op.statusCode);
		
		jQuery.ajax({
			type: 'GET',
			url: pageFrag,
			dataType: 'xml',
			timeout: 50000,
			cache: false,
			error: function(xhr) {
				alert('Error loading XML document: ' + pageFrag + "\nHttp status: " + xhr.status + " " + xhr.statusText);
			},
			success: function(xml) {
				
				$(xml).find("_WORD_").each(function(){
					var id = $(this).attr("id");
					if (id) HG._word[id] = $(this).text();
				});
				
				$(xml).find("_MSG_").each(function(){
					var id = $(this).attr("id");
					if (id) HG._msg[id] = $(this).text();
				});

				if (jQuery.isFunction(op.callback)) op.callback();
			}
		});
		
		Metronic.init(); 			// init metronic core componets
	    Layout.init(); 				// init layout
	    QuickSidebar.init(); 		// init quick sidebar
	    $.hgLayout.setLayout(op);	// 设置布局属性
	    
		initEnv();
		
		// 初始打开主页
		$.hgtab.openHomePage();
		
		
	}

};

(function($) {

	// 对Date的扩展，将 Date 转化为指定格式的String
	// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
	// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
	// 例子：
	// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
	// (new Date()).Format("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
	Date.prototype.format = function(fmt) { // author: meizz
		var o = {
			"M+" : this.getMonth() + 1, // 月份
			"d+" : this.getDate(), // 日
			"h+" : this.getHours(), // 小时
			"m+" : this.getMinutes(), // 分
			"s+" : this.getSeconds(), // 秒
			"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
			"S" : this.getMilliseconds()
		// 毫秒
		};
		if (/(y+)/.test(fmt))
			fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
					.substr(4 - RegExp.$1.length));
		for ( var k in o)
			if (new RegExp("(" + k + ")").test(fmt))
				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
						: (("00" + o[k]).substr(("" + o[k]).length)));
		return fmt;
	};
	
	/**
	 * 扩展String方法
	 */
	$.extend(String.prototype, {
		isPositiveInteger:function(){
			return (new RegExp(/^[1-9]\d*$/).test(this));
		},
		isInteger:function(){
			return (new RegExp(/^\d+$/).test(this));
		},
		isNumber: function(value, element) {
			return (new RegExp(/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/).test(this));
		},
		trim:function(){
			return this.replace(/(^\s*)|(\s*$)|\r|\n/g, "");
		},
		startsWith:function (pattern){
			return this.indexOf(pattern) === 0;
		},
		endsWith:function(pattern) {
			var d = this.length - pattern.length;
			return d >= 0 && this.lastIndexOf(pattern) === d;
		},
		replaceSuffix:function(index){
			return this.replace(/\[[0-9]+\]/,'['+index+']').replace('#index#',index);
		},
		trans:function(){
			return this.replace(/&lt;/g, '<').replace(/&gt;/g,'>').replace(/&quot;/g, '"');
		},
		encodeTXT: function(){
			return (this).replaceAll('&', '&amp;').replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll(" ", "&nbsp;");
		},
		replaceAll:function(os, ns){
			return this.replace(new RegExp(os,"gm"),ns);
		},
		replaceTm:function($data){
			if (!$data) return this;
			return this.replace(RegExp("({[A-Za-z_]+[A-Za-z0-9_]*})","g"), function($1){
				return $data[$1.replace(/[{}]+/g, "")];
			});
		},
		replaceTmById:function(_box){
			var $parent = _box || $(document);
			return this.replace(RegExp("({[A-Za-z_]+[A-Za-z0-9_]*})","g"), function($1){
				var $input = $parent.find("#"+$1.replace(/[{}]+/g, ""));
				return $input.val() ? $input.val() : $1;
			});
		},
		isFinishedTm:function(){
			return !(new RegExp("{[A-Za-z_]+[A-Za-z0-9_]*}").test(this)); 
		},
		skipChar:function(ch) {
			if (!this || this.length===0) {return '';}
			if (this.charAt(0)===ch) {return this.substring(1).skipChar(ch);}
			return this;
		},
		isValidPwd:function() {
			return (new RegExp(/^([_]|[a-zA-Z0-9]){6,32}$/).test(this)); 
		},
		isValidMail:function(){
			return(new RegExp(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/).test(this.trim()));
		},
		isSpaces:function() {
			for(var i=0; i<this.length; i+=1) {
				var ch = this.charAt(i);
				if (ch!=' '&& ch!="\n" && ch!="\t" && ch!="\r") {return false;}
			}
			return true;
		},
		isPhone:function() {
			return (new RegExp(/(^([0-9]{3,4}[-])?\d{3,8}(-\d{1,6})?$)|(^\([0-9]{3,4}\)\d{3,8}(\(\d{1,6}\))?$)|(^\d{3,8}$)/).test(this));
		},
		isUrl:function(){
			return (new RegExp(/^[a-zA-z]+:\/\/([a-zA-Z0-9\-\.]+)([-\w .\/?%&=:]*)$/).test(this));
		},
		isExternalUrl:function(){
			return this.isUrl() && this.indexOf("://"+document.domain) == -1;
		}
	});
	
	toastr.options = {
	  "closeButton": true,
	  "debug": false,
	  "positionClass": "toast-bottom-right",
	  "onclick": null,
	  "showDuration": "1000",
	  "hideDuration": "1000",
	  "timeOut": "5000",
	  "extendedTimeOut": "1000",
	  "showEasing": "swing",
	  "hideEasing": "linear",
	  "showMethod": "fadeIn",
	  "hideMethod": "fadeOut"
	}
})(jQuery);