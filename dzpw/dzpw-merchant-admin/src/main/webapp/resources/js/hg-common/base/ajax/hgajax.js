/**
 * @author zzb
 * @date 2014-09-22
 */


/**
 * 普通ajax表单提交
 * @param {Object} form
 * @param {Object} callback
 * @param {String} confirmMsg 提示确认信息
 */
function validateCallback(form, callback, confirmMsg) {
	var $form = $(form);
	if (!$form.valid()) {
		return false;
	}

	var _submitFn = function() {
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || HG.ajaxDone,
			error: HG.ajaxError
		});
	}
	
	if (confirmMsg) {
		HG.confirm('确认', confirmMsg, function() {
			if(_submitFn)
				_submitFn();
		});
	} else {
		_submitFn();
	}
	
	return false;
}


/**
 * tab回调
 * @param json
 */
function hgTabAjaxDone(json) {

	HG.ajaxDone(json);
	if (json.statusCode == HG.statusCode.ok) {
		
		$.hgtab.openByTabid(json.navTabId);
	}
}

function ajaxTodo(url, callback) {

	var $callback = callback || hgTabAjaxDone;

	if (! $.isFunction($callback)) $callback = eval('(' + callback + ')');
	
	HG.block();
	$.ajax({
		type:'POST',
		url:url,
		dataType:"json",
		cache: false,
		success: function(response) {
			HG.unblock();
			$callback(response);
		},
		error: HG.ajaxError
	});
}

function uploadifyError(file, errorCode, errorMsg) {
	HG.messager(errorCode+": "+errorMsg, "error");
}
