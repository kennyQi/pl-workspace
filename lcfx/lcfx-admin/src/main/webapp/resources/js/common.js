 _debugMode_1=false;

function trim(str){  
    str = str.replace(/^(\s|\u00A0)+/,'');  
    for(var i=str.length-1; i>=0; i--){  
        if(/\S/.test(str.charAt(i))){  
            str = str.substring(0, i+1);  
            break;  
        }  
    }  
    return str;  
}  

function getHtmlTemplate(templateId) {
	return $("[htmltemplate='" + templateId + "']").html();
}

function checkExcelFile(obj){
	var maxSize = 2000;// 单位kb
	
    photoExt=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();// 获得文件后缀名
    if(photoExt!='.xls' ){
        alertMsg.error("请上传格式为xls的文件!");
    	obj.value='';
        return false;
    }
    var fileSize = 0;
    var isIE = /msie/i.test(navigator.userAgent) && !window.opera;            
    if (isIE && !obj.files) {          
         var filePath = obj.value;            
         var fileSystem = new ActiveXObject("Scripting.FileSystemObject");   
         var file = fileSystem.GetFile (filePath);               
         fileSize = file.Size;         
    }else {  
         fileSize = obj.files[0].size;     
    } 
    fileSize=Math.round(fileSize/1024*100)/100; // 单位为KB
    if(fileSize>=maxSize){
    	alertMsg.error("文件最大为"+maxSize+"KB，请重新上传!");
    	obj.value='';
        return false;
    }
}

function importSubmitValidate(e,cb){
	if($("#importFile").val()==''){
		alertMsg.warn('请选择要导入的excel文件');
		return false;
	}
	
	return iframeCallback(e,cb);
}

function checkPhone(s){
	s=s.replace(/\D/g,'');
	if(!isNaN(s) && s.length<=12){
		return s;
	}
	
	alertMsg.warn("请输入正确的号码");
	return '';
	
}

function checkNumberA(s,a,b){
	if(!isNaN(s) && parseFloat(s)>=a &&  parseFloat(s)<=b){
		 return s;
		}
			alertMsg.warn("请输入一个"+a+"至"+b+"的数值");
		return '';
}

function checkEm(e){
   var re = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/
   if(re.test(e.value)){
       return;
   }else{
       e.value = '';
       alertMsg.warn("邮箱格式不正确");
       return;
   }
}
function checkExtraInput(e,extraInputName){
	var i = $("[name=\'"+extraInputName+"\']")[0];
	if(e.value==''){
		$(i).val('');
		$(i).show();
	}else{
		$(i).val('');
		$(i).hide();
	}
	
	
}



function fiEftReady(name){
    var i=1;
    var jqe=$("[fi='"+name+i+"']");
    while(jqe.length>0){
        jqe.hide();
        i++;
        jqe=$("[fi='"+name+i+"']");
    }
}
function fiEftShow(name,s){
    var i=1;
    var jqe=$("[fi='"+name+i+"']");
    while(jqe.length>0){
        jqe.delay((i-1)*s).fadeIn(s);
        i++;
        jqe=$("[fi='"+name+i+"']");
    }
}

function fiEffect(n){
	if(_debugMode_1==false){
		fiEftReady(n);fiEftShow(n,200);
		
	}
}
function fiEffectEx(n,s){
	if(_debugMode_1==false){
		fiEftReady(n);fiEftShow(n,s);
	}
}

function removeAllEmpty(e){
    var val = e.value;
     val=val.replace(/\ +/g,"");
    e.value=val;
}

function getCurrentDiv(id){
	var e;
	if(id!=null){
		e=$("#"+id);
	}else{
		e=$("div.unitBox.dialogContent");
		if(e.length<1){
			e=$("div.page.unitBox:visible");
		}
	}
	if(e.length<1){
		alert("error@getCurrentDiv:element not exist!!!");
	}
	return e;
}

function projectSubmitValidateCallback(e,cb){
	var ept = $("[name='extraPtName']");
	var eof = $("[name='extraOfName']");
	var epn = $("[name='extraPName']");
	if(ept.is(":visible") && ept.val() == ''){
		alertMsg.warn("请填写项目类型");
		return false;
	}
	if(eof.is(":visible") && eof.val() == ''){
		alertMsg.warn("请填写运营形式");
		return false;
		
	}
	if(epn.is(":visible") && epn.val() == ''){
		alertMsg.warn("请填写项目模式");
		return false;
		
	}
	
	return validateCallback(e,cb);
}





function removeSc(e){
	var s = e.value;
	s=s.replace(/</g,'');
	s=s.replace(/>/g,'');
	e.value=s;
}

function checkFloatOrInt(e){
    var v = e.value;
    if(v == ""){
        return;
    }
    var reg = /^[1-9]\d*\.\d*|0\.\d*[1-9]\d*$|^[1-9]\d*$/;
    var ret = reg.test(v);
    if(ret == false){
        e.value="";
        alertMsg.error("请输入正整数或者正小数")
    }
	
}



function clearForm(){
	var c= getCurrentDiv().find("div.searchBar");
	c.find("input").val('');
	c.find("select").each(function(){
		$(this).find("option").each(function(i,e){
			if(i!=0){
				$(e).removeAttr("selected");
			}else{
				$(e).attr("selected",true);
			}
		})
	})
	
}


