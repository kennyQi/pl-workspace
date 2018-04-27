//输入框获取和失去焦点 textarea可用、
function inputEdit(obj,old,cla){//obj=jquery对象，old=默认值，class=修改的样式名称
	obj.focus(function(){
		var rel=$(this).attr(old);
		$(this).addClass(cla);
		if(rel==$(this).val()||rel==$(this).html()){
				$(this).val("").html("");
		}
	});
	
	obj.blur(function(){
		var rel=$(this).attr(old);
			if($(this).val()==""){
				$(this).val(rel).removeClass(cla);
			}
			
	});
}