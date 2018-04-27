/**
 * script插件开发脚本库 - 3.0
 * 调用方式： Js.function(args);
 * 作者：	胡永伟
 * 时间：	2014-04-20
 */
var Js = new function() {
	var c = {
		i : function(i) {
			return (i < 10) ? ('0' + i) : i;
		}
	}, h = {
		s : function(u) {
			var s, c, b, e;
			s = document.scripts;
			if ((e = u.indexOf('?')) > (b = 0)) {
				u = u.substring(b, e);
			}
			for ( var i = 0; s != null && i < s.length; i++) {
				c = s[i].src;
				if ((e = c.indexOf('?')) > (b = 0)) {
					c = c.substring(b, e);
				}
				if (c.indexOf(u) > -1 || u.indexOf(c) > -1) {
					return true;
				}
			}
			return false;
		},
		l : function(u) {
			var l, c, b, e;
			l = document.styleSheets;
			if ((e = u.indexOf('?')) > (b = 0)) {
				u = u.substring(b, e);
			}
			for ( var i = 0; l != null && i < l.length; i++) {
				c = l[i].href;
				if ((e = c.indexOf('?')) > (b = 0)) {
					c = c.substring(b, e);
				}
				if (c.indexOf(u) > -1 || u.indexOf(c) > -1) {
					return true;
				}
			}
			return false;
		}
	};
	return {
		isBlank : function(s) {	// 判断是否为空
			return (s == null || s == '') ? true : false;
		},
		isNotBlank : function(s) {	// 判断是否不为空,传入数组时判断所有都不能为空
			if (s == null) {
				return false;
			}
			for ( var i = 0; i < s.length; i++) {
				if (s[i] == null || s[i] == '') {
					return false;
				}
			}
			return true;
		},
		getNullStr : function(s) {	// 当为空时返回不是null而是空字符串
			return (s == null) ? "" : s;
		},
		fmtTime : function(D, p) {	// 格式化时间
			var f = c['i'], y = f(D.getFullYear()), M = f(D.getMonth() + 1), d = f(D
					.getDate()), h = f(D.getHours()), m = f(D.getMinutes()), s = f(D
					.getSeconds());
			return p.replace(/yyyy/, y).replace(/MM/, M).replace(/dd/, d)
					.replace(/HH/, h).replace(/hh/, f((h > 12) ? (h - 12) : h))
					.replace(/mm/, m).replace(/ss/, s);
		},
		isBigMonth : function(m) {	// 判断是否是大月
			return (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) ? true
					: false;
		},
		isLeapYear : function(y) {	// 判断是否是闰年
			return (y % 400 == 0 || (y % 4 == 0 && y % 100 != 0)) ? true
					: false;
		},
		calendar : function(D, p) {	// 日历
			var y = p.indexOf('yyyy'), M = p.indexOf('MM'), d = p.indexOf('dd'), H = p
					.indexOf('HH'), h = p.indexOf('hh'), m = p.indexOf('mm'), s = p
					.indexOf('ss');
			return {
				'year' : D.substr(y, 4), 'month' : D.substr(M, 2), 'day' : D.substr(d, 2),
				'HOUR' : D.substr(H, 2), 'hour' : D.substr(h, 2), 'min' : D.substr(m, 2),
				'sec' : D.substr(s, 2)
			};
		},
		fmtSize : function(s) {	// 格式化文件大小
			var u = [ 'B', 'KB', 'MB', 'GB' ], i, t, p;
			for (i = 0; i < u.length; i++) {
				t = s / ((i == 0) ? 1 : Math.pow(1024, i));
				if (t < 1024) {
					if (t == parseInt(t)) {
						return t + u[i];
					} else {
						p = (t + '0').split('.');
						return p[0] + '.' + p[1].substr(0, 2) + u[i];
					}
				}
			}
			return null;
		},
		fmtIEcss : function(s, v) {	// IE高宽兼容
			return s + ':' + v + 'px; ' + s + ':' + (v + 2) + 'px\\9;';
		},
		getSelf : function() {	// 获取js文件自身信息
			var s, u, r, q = '', w, f, b, e, t = [], i, n, v;
			s = document.scripts;
			u = s[s.length - 1].src;
			r = { url : '', wrap : '', file : '', query : '', params : {} };
			if ((e = u.indexOf('?')) > (b = 0)) {
				r['url'] = u.substring(b, e);
				b = e + 1;
				e = u.length;
				if (e > b) {
					q = u.substring(b, e);
					if (q.length > 0) {
						r['query'] = '?' + q;
						b = e = 0;
						while ((e = q.indexOf('&', e)) > b) {
							t.push(q.substring(b, e));
							b = (++e);
						}
						e = q.length;
						t.push(q.substring(b, e));
						for (i = 0; t != null && i < t.length; i++) {
							if (t[i].length > 0) {
								if ((e = t[i].indexOf('=')) > (b = 0)) {
									n = t[i].substring(b, e);
									b = e + 1;
									e = t[i].length;
									v = t[i].substring(b, e);
									r.params[n] = v;
								} else if (e == -1) {
									r.params[t[i]] = '';
								}
							}
						}
					}
				}
			} else {
				r['url'] = u;
			}
			u = r.url;
			b = 0;
			e = u.lastIndexOf('/');
			r['wrap'] = u.substring(b, e);
			b = e + 1;
			e = u.length;
			r['file'] = u.substring(b, e);
			return r;
		},
		getUpWrap : function(w) {	// 获取js文件上一层目录
			var b, e;
			if ((e = w.lastIndexOf('/')) > (b = 0)) {
				w = w.substring(b, e);
			}
			return w;
		},
		importJs : function(u) {	// 导入制定js文件
			if (!h.s(u)) {
				document.write('<script type="text/javascript" src="' + u
						+ '"></script>');
			}
		},
		importCss : function(u) {	// 导入制定css文件
			if (!h.l(u)) {
				document.write('<link rel="stylesheet" type="text/css" href="'
						+ u + '"/>');
			}
		},
		uuid : function() {	// 获取UUID
			return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g,
					function(c) {
						var r = Math.random() * 16 | 0, v = c == 'x' ? r
								: (r & 0x3 | 0x8);
						return v.toString(16);
					});
		},
		back : function() {	// 浏览器退回上一页面
			history.back();
		},
		tip : function(o) {	// 浏览器alert提示
			var b = window.confirm(o.msg);
			if (b) {
				if ($.isFunction(o.yes)) {
					o.yes();
				}
			} else {
				if ($.isFunction(o.no)) {
					o.no();
				}
			}
		},	
		forward : function(u) {	// 页面跳转
			location.href = u;
		},
		refresh : function() {	// 页面刷新
			location.reload(true);
		},
		decodeURL : function(s) {	// 解码字符串
			return decodeURI(s.replace(/\+/g, ' ').replace(/%23/gi, '#')
					.replace(/%24/gi, '$').replace(/%26/gi, '&')
					.replace(/%2B/gi, '+').replace(/%2C/gi, ',')
					.replace(/%2F/gi, '/').replace(/%3A/gi, ':')
					.replace(/%3B/gi, ';').replace(/%3D/gi, '=')
					.replace(/%3F/gi, '?').replace(/%40/gi, '@'));
		},
		setCookie : function(k, v, h) {	// 设置Cookie
			if (k != null && k != '') {
				if (h == null || h == '' || h == 0) {
					document.cookie = k + "=" + v + ";";
				} else {
					var d = new Date();
					d.setHours(d.getHours() + h);
					document.cookie = escape(k + "=" + v) + "; expires=" + d.toGMTString();
				}
			}
		},
		getCookie : function(k) {	// 获取Cookie
			var c = unescape(document.cookie);
			if (c != null && c != '') {
				var u = c.split(/\; /);
				if (u != null && u.length > 0) {
					for (var p in u) {
						var n = u[p].split(/\=/)[0];
						var v = u[p].split(/\=/)[1];
						if (n == k) {
							return v;
						}
					}
				}
			}
		},
		getSmtps : function() {	// 获取常用邮箱Smtp信息
			return {
	    		'qq' : { 'name' : 'QQ邮箱', 'protocol' : 'smtp.qq.com', 'port' : 25 },
	    		'sina' : { 'name' : '新浪邮箱', 'protocol' : 'smtp.sina.com.cn', 'port' : 25 },
	    		'sohu' : { 'name' : '搜狐邮箱', 'protocol' : 'smtp.sohu.com', 'port' : 25 },
	    		'yahoo' : { 'name' : '雅虎邮箱', 'protocol' : 'smtp.mail.yahoo.cn', 'port' : 25 },
	    		'126' : { 'name' : '网易126', 'protocol' : 'smtp.126.com', 'port' : 25 },
	    		'yeah' : { 'name' : '网易yeah', 'protocol' : 'smtp.yeah.net', 'port' : 25 },
	    		'netease' : { 'name' : '网易netease', 'protocol' : 'smtp.netease.net', 'port' : 25 }
	    	};
		},
		limitNum : function(n, a, z) {	// 限制数字在min和max自检
			switch (true) {
				case n < a: n = a; break;
				case n > z: n = z; break;
				default: n;
			}
			return n;
		},
		getClient : function() {	// 获取当前浏览器的高和宽
			return {
				'width' : window.top.document.documentElement.clientWidth,
				'height' : window.top.document.documentElement.clientHeight
			};
		}
	};
};