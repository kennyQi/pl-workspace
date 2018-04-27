/**
 * @author chenys
 * @date 2014年12月3日 上午9:11:49
 * ----------------------------------------------------------
 * 只是简单的自定义函数，放在这里统一管理，方便维护和修改
 * ----------------------------------------------------------
 */

/**
 * 重置按钮单击事件
 */
function MyReset(form){
	//清空所有输入框的值
	$(form).find(":input").val("");
	//提交一次
	$(form).submit();
};

/**
 * 关闭按钮单击事件
 * @param formId
 */
function MyClose(msg,formId){
	var tempFun = function(){
		/*var dialog = $("body").data(formId);
		$.pdialog.close(dialog);*/
		$.pdialog.closeCurrent();
	};
	alertMsg.confirm(msg,{okCall: tempFun});
}