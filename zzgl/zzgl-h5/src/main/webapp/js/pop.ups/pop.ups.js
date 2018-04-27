/**
 * H5页面半透明弹出层控件 - 1.1.0
 * 调用方式： $.pop.function();
 * 作者：	胡永伟
 * 时间：	2014-08-01
 */
var pop_ups_wrap = Js.getSelf().wrap;
$.pop = {
	lock : function(flag) {
		var $body = $(window.top.document.body);
		if ($body.find("#sp-popup-lock").length == 0) {
			$body.append($("<div id='sp-popup-lock' " + sp_popup_style.lock + "></div>")); }
		if (flag) { $body.find("#sp-popup-lock").show();
		} else { $body.find("#sp-popup-lock").hide(); }
	},
	load : function(flag, msg) {
		var $body = $(window.top.document.body);
		if ($body.find("#sp-popup-load").length == 0) {
			$body.append($("<span id='sp-popup-load' " + 
					sp_popup_style.load + ">" + (msg = flag ? msg : '') + "</span>"));
			$body.find("#sp-popup-load").css('background',
					'#000 url(' + pop_ups_wrap + '/loading.gif) 25px center no-repeat'); }
		if (flag) { $body.find("#sp-popup-load").html(msg).show();
		} else { $body.find("#sp-popup-load").hide(); if ($.isFunction(msg)) { msg(); } }
	},
	drag : function(flag) {
		var $body = $(window.top.document.body);
		if ($body.find("#sp-popup-drag").length == 0) {
			$body.append($("<div id='sp-popup-drag' " + sp_popup_style.drag + "></div>")); }
		if (flag) { $body.find("#sp-popup-drag").show();
		} else { $body.find("#sp-popup-drag").hide(); }
	},
	tips : function(msg, sec, callback) {
		var $body = $(window.top.document.body);
		if ($body.find("#sp-popup-tips").length == 0) {
			$body.append($("<span id='sp-popup-tips' " + 
					sp_popup_style.tips + ">" + msg + "</span>"));
		} else { $body.find("#sp-popup-tips").html(msg); }
		var $tips = $body.find("#sp-popup-tips").show();
		setTimeout(function() {
			$tips.fadeOut('fast', function() {
				if ($.isFunction(callback)) { callback(); } });
		}, ((sec == parseFloat(sec)) ? sec : 1) * 1000);
	},
	confirm : function(msg, ok, cancel) {
		$.pop._confirm(msg, ok, cancel, 'confirm');
	},
	prompt : function(msg, sec, callback) {
		$.pop._confirm(msg, sec, callback, 'prompt');
	},
	_confirm : function(msg, ok, cancel, type) {
		with(sp_popup_style.confirm) {
			var cfPop = "<div id='sp_popup_confirm' " + box + ">" +
							"<div id='sp_popup_confirm_title' " + title + ">" +
								"<div " + text + ">\u786e\u8ba4\u4fe1\u606f</div>" +
								"<div " + button + ">" +
									"<a id='sp_popup_prompt_close' " +
										"href='javascript:;' " + close + ">×</a>" +
								"</div>" +
							"</div>" +
							"<div " + message + ">" + msg + "</div>" +
							"<div id='sp_popup_prompt_operate' " + operate + ">" +
								"<div id='sp_popup_prompt_ok' " + ok + ">\u786e\u8ba4</div>" +
								"<div id='sp_popup_prompt_cancel' " + cancel + ">\u53d6\u6d88</div>" +
							"</div>" +
						"</div>"; }
		var maxWidth = Js.getClient().width, maxHeight = Js.getClient().height;
		$(window.top.document.body).append($(cfPop).css({ 
			'left': (maxWidth - 280) / 2 + 'px', 'top': (maxHeight / 2 - 108) + 'px' }));
		var $confirm = $(window.top.document.body).find("#sp_popup_confirm");
		$confirm.find("div").attr('onselectstart', 'return false;');
		if (type == 'prompt') { $confirm.find("#sp_popup_prompt_cancel").hide();
			$confirm.find("#sp_popup_prompt_ok").css({'width': '281px',
				'border-right-style': 'hidden', 'border-radius': '0 0 8px 8px'}); }
		$confirm.find("#sp_popup_prompt_close").click(function() { 
			$confirm.remove(); $.pop.lock(false); });
		$confirm.find("#sp_popup_prompt_operate div").mouseover(function() {
			$(this).css({'background-color':'#C00'});
		}).mouseout(function() { $(this).css({'background-color':'#000'}); });
		$confirm.find("#sp_popup_prompt_ok").click(function() {
			$confirm.remove(); $.pop.lock(false); if ($.isFunction(ok)) { ok(); } });
		$confirm.find("#sp_popup_prompt_cancel").click(function() {
			$confirm.remove(); $.pop.lock(false); if ($.isFunction(cancel)) { cancel(); } }); 
		var downFunc, posX, posY, $title = $confirm.find("#sp_popup_confirm_title");
		$title.bind('mousedown', downFunc = function(down) {
			var moveFunc, x, y; down = (down || window.event); $.pop.drag(true);
			posX = down.clientX - $confirm.offset().left; posY = down.clientY - $confirm.offset().top;
			$(window.top.document).bind( 'mousemove', moveFunc = function(move) {
				move = (move || window.event); x = move.clientX - posX; y = move.clientY - posY;
				$confirm.css('left', Js.limitNum(x, 3, maxWidth - 284) + 'px');
				$confirm.css('top', Js.limitNum(y, 3, maxHeight - 125) + 'px'); });
			$(window.top.document).mouseup(function() {
				$.pop.drag(false); $(this).unbind('mousemove', moveFunc); }); });
		$confirm.find("#sp_popup_prompt_close").mouseover(function() {
			$title.unbind('mousedown', downFunc);
		}).mouseout(function() { $title.bind('mousedown', downFunc); });
		$.pop.lock(true); $confirm.show();
		if (type == 'prompt') {	//set prompt timeout
			setTimeout(function() { $confirm.fadeOut('fast', function() {
				$confirm.remove(); $.pop.drag(false); $.pop.lock(false); 
				if ($.isFunction(cancel)) { cancel(); } });
			}, ((ok == parseFloat(ok)) ? ok : 1) * 1000);
		}
	}
};