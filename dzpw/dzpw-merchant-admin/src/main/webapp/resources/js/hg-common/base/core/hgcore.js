/**
 * @author zzb
 * @date 2014-09-22
 */

var HG = {
	/**
	 * 状态码
	 */
	statusCode : {
		ok : 200,
		error : 300,
		timeout : 301
	},
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
	 * 
	 * @param json
	 */
	ajaxDone : function(json) {
		if (json.statusCode == HG.statusCode.error) {
			if (json.message)
				HG.messager(json.message, "error");
		} else if (json.statusCode == HG.statusCode.timeout) {
			// 登陆超时
			HG.confirm('确认', '登陆超时！请重新登陆！', function() {
				window.location = HG.getRootPath() + "/jq/login";
			});
		} else {
			if (json.message)
				HG.messager(json.message, "info");
		}
	},
	/*
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
	/*
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
	
	// 数组去重
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
	
	// 填充内容
	fullProperty: function(s, data) {			
		var self = this;
		return s.replace(/\{([\w\-\.]+)\}/g, function(m, name) {
			var val = self.property(data, name);
			return (val !== undefined && val !== null) ? val : '';
		});
	},
	
	// 获取对象属性
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
	
	/*
	 * 获取项目根路径
	 */
	getRootPath: function() {
		
	    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
	    var curWwwPath = window.document.location.href;
	    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	    var pathName = window.document.location.pathname;
	    var pos = curWwwPath.indexOf(pathName);
	    //获取主机地址，如： http://localhost:8083
	    var localhostPaht = curWwwPath.substring(0, pos);
	    //获取带"/"的项目名，如：/uimcardprj
	    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	    return(localhostPaht + projectName);
	},
	
	triggerMinClickEvent: function (fireOnThis) {
	   /* if( document.createEvent ) {
	        var evObj = document.createEvent('MouseEvents');
	        evObj.initEvent('click', true, false );
	        fireOnThis.dispatchEvent(evObj);
	    } else if( document.createEventObject ) {
	    	fireOnThis.fireEvent('onclick');
	    }*/
	},
	
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
	
	/*
	 * 锁屏 
	 */
	block: function(msg, $el) {
		
		var $el = $($el || 'body');
		msg = msg || '<div class="loading"></div>';
		
		$el.block({
			message: msg,
			css: {
				width: '164px'
			}
		});
	},
	
	/*
	 * 解锁
	 */
	unblock: function($el) {
		
		var $el = $($el || 'body');
		$el.unblock();
	},
	
	/*
	 * 确认 
	 */
	confirm: function(title, message, deteFun, canFun) {
		
		title = title || '确认';
		message = message || '请确认';
		deteFun = deteFun || function() {};
		canFun = canFun || function() {};
		
		$.confirm({
			'title'		: title,
			'message'	: message,
			'buttons'	: {
				'确定'	: {
					'class'	: 'blue',
					'action': deteFun
				},
				'取消'	: {
					'class'	: 'gray',
					'action': canFun	// Nothing to do in this case. You can as well omit the action property.
				}
			}
		});
	},
	
	/*
	 * 消息
	 */
	messager: function(msg, type) {
		
		msg = msg || '操作成功！';
		type = type || 'success';
		$.globalMessenger().post({
			message: msg,
			showCloseButton: true,
			type: type
		});
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
	
	$._messengerDefaults = {
	  extraClasses: 'messenger-fixed messenger-theme-air messenger-on-bottom messenger-on-right',
	  maxMessages: 3
	}
})(jQuery);