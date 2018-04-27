	/*
	* 汇积分通用js
	* author：wangkq
	*/


var isTesting =false
function testAlert(s){
	if(isTesting){
		alert(s)
	}
}
function testLog(s){
	if(isTesting){
		console.log(s);
	}
}


	var hjfAlert={
		e:null,
		eleTop:0,
		create:function(s,right,top){
			hjfAlert.e=$(s)
			hjfAlert.eleTop = top
			hjfAlert.e.css("position","fixed")
					.css("background-color","#000")
					.css("filter","alpha(opacity=50)")
					.css("-moz-opacity","0.50")
					.css("opacity","0.50")
					.css("right",right+"%")
					.css("top",top+"px")
					.css("font-size","12px")
					.css("padding","4px 10%")
					.css("border-radius","2px")
					.css("color","#fff")
					.css("z-index","101")
			hjfAlert.e.hide()
		},
		setText:function(text){
			hjfAlert.e.html(text)
			hjfAlert.e.hide()
		},
		appendText:function(text){
			hjfAlert.e.html(hjfAlert.e.html() + text)
			hjfAlert.e.hide()
		},
		alert:function(t){
			if(t == null){
				t = 3000;
			}
			hjfAlert.e.show()
			setTimeout(function(){
					hjfAlert.e.animate({ top: (hjfAlert.eleTop-30)+"px", opacity: 0 }, 1000/4, function () {
					hjfAlert.e.hide();
				});
			},t)
		}
	}
	/*
		//选择器 右偏移（百分比） 上偏移（像素）
		hjfAlert.create("#alertBox",50,200)
		//提示内容
		hjfAlert.setText("提示1")
		//追加提示
		hjfAlert.appendText("<br><br>追加提示")
		//显示提示 消失时间不填为3秒
		hjfAlert.alert(1000)
	*/
	
	
	
	
	var hjfAjax={
		formData:null,
		init:function(){
//			loadJsConfigJs()
			hjfAjax.formData = new Object()
		},
		addData:function(k,n){
			hjfAjax.formData[k] = n	
		},
		submit:function(u){
			var retData = null
			u = u
			$.ajax({
				url:u,
				cache:false,
				async:false,
				data:hjfAjax.formData,
				dataType:"json",
				error:function(){
					//alert("exception:hjfAjax\nurl:"+u)
					alert("服务器未响应，网络可能断开")
				},
				success:function(r){
					retData = r
				}
			});
			return retData;
		},
		submitReturnText:function(u){
			var retData = null
			u = u
			$.ajax({
				url:u,
				cache:false,
				async:false,
				data:hjfAjax.formData,
				dataType:"text",
				error:function(){
					//alert("exception:hjfAjax\nurl:"+u)
					alert("服务器未响应，网络可能断开")
				},
				success:function(r){
					retData = r
				}
			});
			return retData;
		},
		submitParseJson:function(u){
			var retData = null
			u = u
			$.ajax({
				url:u,
				cache:false,
				async:false,
				data:hjfAjax.formData,
				dataType:"text",
				error:function(){
					//alert("exception:hjfAjax\nurl:"+u)
					alert("服务器未响应，网络可能断开")
				},
				success:function(r){
					retData = eval("("+r+")")
				}
			});
			return retData;
		}
	}
	
	
	
	
	
	function clickToHref(s){
		window.location.href= s
	}
	
	
	
	 
	
	
	function hjfAlert2(text,t){
		if(t == null){
			t= 2000
		}
		var e=$("<div></div>").attr("name","hjfAlertDiv"+(new Date).getTime())
		$("body").append(e)
		var eleTop = 100
		var top = 100
		var bodyWidth = e.width()
		
		var startTop=100;
		while(true){
			if($("[hjfAlertBoxTop"+startTop+"]").length == 0){
				break;
			}
			
			startTop = startTop +35
		}
		top = startTop

		e.css("position","fixed")
				.css("background-color","#000")
				.css("filter","alpha(opacity=50)")
				.css("-moz-opacity","0.50")
				.css("opacity","0.50")
				.css("top",top+"px")
				.css("font-size","12px")
				.css("padding","4px 10%")
				.css("border-radius","2px")
				.css("color","#fff").html(text).css("right",bodyWidth/2-e.outerWidth()/2).attr("hjfAlertBoxTop"+top,true)
				setTimeout(function(){
						e.animate({ top: (startTop-30)+"px", opacity: 0 }, 1000/4, function () {
						e.remove()
					});
				},t)		
	}
	/*
		hjfAlert2("提示",200)
	*/
	
	
	function hjfAlert3(text,t){
		if(t == null){
			t= 2000
		}
		var e=$("<div></div>").attr("name","hjfAlertDiv"+(new Date).getTime())
		$("body").append(e)
		var top = 100
		var bodyWidth = e.width()
		
		
		var startTop=100;
		if($("[alertData='"+text+"']").length != 0){
			startTop = $("[alertData='"+text+"']").attr("hjfAlertBoxTopVal")
		}else{
			while(true){
				if($("[hjfAlertBoxTop"+startTop+"]").length == 0){
					break;
				}
				startTop = startTop +35
			}
		}
		top = startTop

		e.css("position","fixed")
							.css("z-index",101)

		.css("border-radius","30px")
				.css("background-color","#000")
				.css("filter","alpha(opacity=50)")
				.css("-moz-opacity","1")
				.css("opacity","1")
				.css("top",top+"px")
				.css("font-size","12px")
				.css("padding","4px 10%")
				.css("color","#fff").html(text).css("right",bodyWidth/2-e.outerWidth()/2)
				.attr("hjfAlertBoxTop"+top,true)
				.attr("hjfAlertBoxTopVal",top)
				.attr("alertData",text)
				setTimeout(function(){
						e.animate({ opacity: 0 }, 1000/4, function () {
						e.remove()
					});
				},t)		
	}
	
	/*
	hjfAlert3("提示",200)
*/
	
	
	
 	function getHjfUrl2(){
		hjfAjax.init()
		return hjfAjax.submit(hjfInfo.getCp()+"/hjf/getHjfUrl")
	}
	
	var hjfInfo ={
		thisContextPath:null,
		setCp:function(cp){
			hjfInfo.thisContextPath = cp
		},
		getCp:function(){
			if(hjfInfo.thisContextPath == null){
				alert("exception:\nhjfInfo.thisContextPath is null")
			}
			return hjfInfo.thisContextPath
		}
	}
	function setCp(s){
		hjfInfo.thisContextPath = s
	}
	
	function toHref(h){
		window.location.href = h
	}
	
	
	/*
	var name = 
	var r = userDataService.getValue(name)
	*/
	var userDataService = {}
	userDataService.getValue = function(name){
		hjfAjax.init()
		hjfAjax.addData("name",name)
		return hjfAjax.submitReturnText(hjfInfo.getCp()+"/userData/getValue")
	}
	userDataService.getValueAndDelete = function(name){
		hjfAjax.init()
		hjfAjax.addData("name",name)
		return hjfAjax.submitReturnText(hjfInfo.getCp()+"/userData/getValueAndDelete")
	}
	
	/*
	var name = 
	var value = 
	var r = userDataService.setValue(name,value)
	*/
	userDataService.setValue = function(name,value){
		hjfAjax.init()
		hjfAjax.addData("name",name)
		hjfAjax.addData("value",value)
		return hjfAjax.submitReturnText(hjfInfo.getCp()+"/userData/setValue")
	}
	
    appTool = {
            setReloadFlag:function(s){
            	userDataService.setValue(s+"_reloadFlag","1")
            },
            reloadIfFlag:function(s){
                var flag= userDataService.getValueAndDelete(s+"_reloadFlag")
                if(flag != null && flag == "1"){
                    document.location.reload()
                }
            },
            setFromUrl:function(s){
            	userDataService.setValue(s+"_fromUrl",location.href)
            	testAlert("setFromUrl:\n"+s+"\n"+location.href)
            },
            getFromUrl:function(s){
                return userDataService.getValue(s+"_fromUrl")
            },
            toReturnUrl:function(s){
                var fromUrl = userDataService.getValueAndDelete(s+"_fromUrl")
                if(fromUrl != null && fromUrl != ""){
                	testAlert("toReturnUrl:\n"+s+"\n有returnUrl\n"+fromUrl)
                	
                	
                    location.href = fromUrl
                }else{
                	testAlert("toReturnUrl:\n"+s+"\n没有returnUrl\n调用history.go(-1)")
                    history.go(-1)
                }
            }
        }
    
    function toHrefUserAppTool(href,s){
    	
    	
    	appTool.setFromUrl(s)
    	
    	window.location.href = href
    }
    function toHrefUseAppTool(href,s){
    	appTool.setFromUrl(s)
    	window.location.href = href
    }
    function returnHrefUseAppTool(s){
    	appTool.toReturnUrl(s)
    }
    
    function toHrefWithCp(s){
    	window.location.href = hjfInfo.getCp() + s
    }
    
    
    
    var ScFilter = (function () {
        function ScFilter() {
        }
        ScFilter.create = function ($e, onBlurCheck, onKeyUpCheck) {
            if (onBlurCheck === void 0) { onBlurCheck = true; }
            if (onKeyUpCheck === void 0) { onKeyUpCheck = true; }
            if (onBlurCheck) {
                $e.blur(function () {
                    $e.val($e.val().replace(/[<>]/g, ""));
                });
            }
            if (onKeyUpCheck) {
                $e.keyup(function () {
                    $e.val($e.val().replace(/[<>]/g, ""));
                });
            }
        };
        return ScFilter;
    }());

    /**
     * 参数1 jquery元素
     * 参数2 离开输入框时检查
     * 参数3 按下键时检查
     */
//    ScFilter.create($("[name='url']"))
//    ScFilter.create($("[name='comment']"))
//    ScFilter.create($("[name='email']",false,true))
//    ScFilter.create($("[name='name']",true,false))
    
    var $$ = (function () {
        function $$() {
        }
        $$.bindInput = function (s) {
        	if(s == null){
        		$("input[name]").each(function (i, e) {
        			var jqe = $(e);
        			$$.bindData(jqe.attr('name'), null);
        		});
        		
        	}else{
        		$(s).find("input[name]").each(function (i, e) {
        			var jqe = $(e);
        			$$.bindData(jqe.attr('name'), null);
        		});
        	}
        };
        $$.bindData = function (name, getData) {
            if (getData == null) {
                var e = $("[name='" + name + "']");
                if (e.length == 1) {
                    this.bindJqe[name] = e;
                }
                else {
                	if(isTesting){
                		console.log("bindJqEleError:name is\"" + name + "\"");
                	}
                	
                }
            }
            else {
                this.bindd[name] = getData;
            }
        };
        $$.getData = function (name) {
            if (this.bindd[name] != null) {
                return this.bindd[name]();
            }
            else if(this.bindJqe[name] != null){
                return this.bindJqe[name].val();
            }else{
            	return null
            }
        };
        $$.data = function (name) {
        	return $$.getData(name)
        };
        $$.bindCheck = function (name,check) {
        	if(name == null || check == null){
        		alert("bindCheck 空参数")
        		return 
        	}
        	$$.bindChk[name] = check
        };
        
        $$.outputData = function (name) {
        	var r = {}
        	for(var i in this.bindd){
        		r[i] = this.bindd[i]()
        	}
        	for(var i in this.bindJqe){
        		r[i] = this.bindJqe[i].val()
        	}
        	
        	return r
        };
        $$.doCheck = function (nameArr) {
        	var re = true
        	if(nameArr != null){
	        	for(var i in nameArr){
	        		r = $$.bindChk[nameArr[i]]($$.data(nameArr[i]))
	        		if(!r){
	        			re = false
	        		}
	        	}
	        	return re
        	}else{
	        	for(var i in $$.bindChk){
	        		r = $$.bindChk[i]($$.data(i))
	        		if(!r){
	        			re = false
	        		}
	        	}
	        	return re
        	}

        };
        
        $$.bindd = {};
        $$.bindJqe = {};
        $$.bindChk = {};
        return $$;
    }());

    /*
    // 全部input
    $$.bindInput()
    // input
    $$.bindData("aInputElementName")
    // 自定义
    $$.bindData("price",function(){
    	var price = $("#price")
    	return price.html()
    })
    */
   


$.fn.extend({
	myHide:function(s){
		if(s == null){
			s == 100
		}
		this.animate({"opacity":0},s)
	},
	myShow:function(s){
		if(s == null){
			s == 100
		}
		this.animate({"opacity":1},s)
	}
})
 

if(window["jsConfigLoadOk"] == null){
	var testCp = getContextPath()
	
	$.ajax({
		url:"/adminConfig/genConfigJs",
		cache:false,
		async:false,
		dataType:"text",
		success:function(){
			setCp("/")
			
		}
	});
	$.ajax({
		url:testCp + "/adminConfig/genConfigJs",
		cache:false,
		async:false,
		dataType:"text",
		success:function(){
			setCp(testCp)
		}
	});
}else{
	setCp(jsConfigCp) 
}

function getContextPath() {
    var pathName = document.location.pathname;
    
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0,index+1);
    return result;
}
/*
function loadJsConfigJs(){
	if(window["jsConfigLoadOk"] == null){
		var scriptJsConfig = $("#scriptJsConfig")
		$.getScript(scriptJsConfig.attr("src"))
	}
	console.log(window["jsConfigCp"] != null);
	console.log(jsConfigCp);
	
	
	if(window["jsConfigCp"] != null){
		setCp(jsConfigCp)
	}
}
*/