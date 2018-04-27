<#-- @ftlvariable name="useCache" type="String" -->
<#-- @ftlvariable name="time" type="long" -->
<#-- @ftlvariable name="sqo" type="hg.member.common.spi.qo.DepartmentSQO" -->
<#-- @ftlvariable name="pagination" type="hg.common.model.Pagination" -->
<#assign contextPath = springMacroRequestContext.getContextPath() />
<!doctype html>
<html lang="zhCn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<script src="${contextPath}/resources/js/ajaxfileupload.js" type="text/javascript"></script>
<script type="text/javascript">
var path="${contextPath}";
function upload(){
	if($("#file").val() == ""){
		alert("没有选择图片");
		return false;
	}
	
	$.ajaxFileUpload({
		url : path+"/set/img",
       	secureuri : false,
       	fileElementId : 'file',
       	dataType : 'json',
       	data: {
       		//maxHeight: 175,
       		//maxWidth: 230,
       		maxSize: 20480
       	},
		success: function (data) {
			if(data.status == "false"){
				alert(data.msg);
			}else{
				$("#imgView").attr("src", "http://"+data.sourceIpAddr+data.uri);
				$("#imgUrl").attr("value","http://"+data.sourceIpAddr+data.uri);
			}
			
		},
        error: function (data, status, e) {
        	alert("上传失败");
        }
    });
}

function check(){
	var imgUrl = $("#imgUrl").val();
	if(imgUrl==""){
		alert("请上传logo图片");
		return false;
	}
	$("#form").submit();
}
</script>
<img id="imgView" src="${logoUrl!}"width="120" height="120" style="padding-left: 65px;"/><br/>
<span style="padding-left: 65px;">建议<strong>160px*48px</strong>的图片</span><br/>
<span style="padding-left: 65px;">上传文件</span><input type="file" value="" name="file" id="file">
<input type="button" value="上传" onclick="upload();">

<form action="${contextPath}/set/saveLogo" method="post" id="form" onsubmit="return validateCallback(this, navTabAjaxDone);">
	<input type="hidden" hidden="hidden" value="${logoUrl!}"  class="required"  name="logoUrl" id="imgUrl">
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit" onclick="check();">保存</button></div></div></li>
			<li>
				<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
			</li>
		</ul>
	</div>
</form>
</body>
</html>