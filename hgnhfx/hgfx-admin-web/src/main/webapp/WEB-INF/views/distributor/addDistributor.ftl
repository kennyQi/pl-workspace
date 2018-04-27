<#assign contextPath=springMacroRequestContext.getContextPath() />
<script>
function myCallBack(json){
	if(json[DWZ.keys.statusCode] == DWZ.statusCode.ok)
	{
	DWZ.ajaxDone(json)
	if (json.navTabId){
			navTab.reload(json.forwardUrl, {navTabId: json.navTabId});
		} else {
			var $pagerForm = $("#pagerForm", navTab.getCurrentPanel());
			var args = $pagerForm.size()>0 ? $pagerForm.serializeArray() : {}
			navTabPageBreak(args, json.rel);
		}
		if ("closeCurrent" == json.callbackType) {
			$.pdialog.closeCurrent();
		}
	}
	else if(json[DWZ.keys.statusCode]==DWZ.statusCode.error)
	{
	DWZ.ajaxDone(json);
	}
	else
	{
	$(".errsp").text("");
	var error = json[DWZ.keys.message].split("#split#");
	for(var i=0;i<error.length;i++)
	{
	  var warn = error[i].split("|");
	  $("#errsp"+warn[0]).text(warn[1]);
	}
	
	}
	
}

function checkeURL(URL){
var str=URL;
//在JavaScript中，正则表达式只能使用"/"开头和结束，不能使用双引号
//判断URL地址的正则表达式为:http(s)?://([\w-]+\.)+[\w-]+(/[\w- ./?%&=]*)?
//下面的代码中应用了转义字符"\"输出一个字符"/"
var Expression=/http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/;
var objExp=new RegExp(Expression);
if(objExp.test(str)==true){
return true;
}else{
return false;
}
} 

function checkForm()
{
  var webSite = $("#webSite").val();
  if(webSite.replace(/[ ]/g,"")!="")
  if(!checkeURL(webSite))
  {
  $("#errsp4").text("请输入主站地址格式为http://XXX.com")
  return false;
  }
  else
  {
   $("#errsp4").text("")
  }
}

</script>

<div class="pageContent">

    <form method="post" action="${contextPath}/distributorManagement/saveDistributor" class="pageForm required-validate" onsubmit="return validateCallback(this, myCallBack)">
        <div class="pageFormContent" layoutH="58">
            <div class="unit">
                <label>商户账号：</label>
                <input type="text" name="account" size="64" maxlength="20" class="required"/> <span class="errsp" style="color:red" id="errsp0"></span>
            </div>
            <div class="unit">
                <label>手机号：</label>
                <input type="text" name="phone" size="64" class="required digits mobile" minlength="11" maxlength="11"/><span style="color:red"  class="errsp" id="errsp1"></span>
            </div>
            <div class="unit">
                <label>商户姓名：</label>
                <input  name="name" size="64" class="required"  maxlength="32"/><span style="color:red" class="errsp" id="errsp2"></span>
            </div>
             <div class="unit">
                <label>公司名称：</label>
                <input  name="companyName" size="64" class="required"  maxlength="50"/><span style="color:red" class="errsp" id="errsp3"></span>
            </div>
            <div class="unit">
                <label>主站：</label>
                <input  id="webSite" name="webSite" size="55"    maxlength="50"/><span style="color:red" class="errsp" id="errsp4"></span>
            </div>
            <div class="unit">
            	<label>折扣模式：</label>
            	<select id="discountType" name="discountType" class="required">
            		<option value="1">定价模式</option>
            		<option value="2">返利模式</option>
            	</select>
            	<span style="color:red" class="errsp" id="errsp5"></span>
            </div>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit" onclick="return checkForm();">提交</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
            </ul>
        </div>
    </form>
</div>

