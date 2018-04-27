/*
var productCodeStrArr = 
var trickCode = 
var r = trickService.checkTrick(productCodeStrArr,trickCode)
 */
var trickService = {}
trickService.checkTrick = function(productCodeStrArr,trickCode){
	hjfAjax.init()
	hjfAjax.addData("productCodeStrArr",productCodeStrArr)
	hjfAjax.addData("trickCode",trickCode)
	return hjfAjax.submit(hjfInfo.getCp()+"/member/userCart/checkTrick")
}

/*
 * var r = userService.isLogin()
 */
var userService = userService||{}
userService.isLogin = function(){
	hjfAjax.init()
	return hjfAjax.submit(hjfInfo.getCp()+"/userInfo/isLogin")
}

var userInfoService = userInfoService||{}
userInfoService.mobileIsLogin = function(){
	hjfAjax.init()
	return hjfAjax.submit(hjfInfo.getCp()+"/hjf/userInfo/mobileIsLogin")
}



/*
 * var r = rechargeService.haveReturn()
 */
var rechargeService = rechargeService||{}
rechargeService.haveReturn = function(){
	hjfAjax.init()
	return hjfAjax.submit(hjfInfo.getCp()+"/wearIncomeAndPay/recharge/haveReturn")
}

rechargeService.haveReturnH5 = function(){
	hjfAjax.init()
	return hjfAjax.submit(hjfInfo.getCp()+"/recharge/haveReturn")
}



/*
 * var phone = var phoneBillNum = var r =
 * phoneBillService.genOrder(phone,phoneBillNum)
 */
var phoneBillService = phoneBillService||{}
phoneBillService.genOrder = function(phone,phoneBillNum){
	hjfAjax.init()
	hjfAjax.addData("phone",phone)
	hjfAjax.addData("phoneBillNum",phoneBillNum)
	return hjfAjax.submit(hjfInfo.getCp()+"/phoneBill/genOrder")
}

/*
 * var orderId = var payPwd = var r = phoneBillService.payOrder(orderId,payPwd)
 */
phoneBillService.payOrder = function(orderId,payPwd){
	hjfAjax.init()
	hjfAjax.addData("orderId",orderId)
	hjfAjax.addData("payPwd",payPwd)
	return hjfAjax.submit(hjfInfo.getCp()+"/phoneBill/payOrder")
}

/*
 * var phone = var phoneBillRmb = var r =
 * phoneBillService.phoneInfo(phone,phoneBillRmb)
 */
phoneBillService.phoneInfo = function(phone,phoneBillRmb){
	hjfAjax.init()
	hjfAjax.addData("phone",phone)
	hjfAjax.addData("phoneBillRmb",phoneBillRmb)
	return hjfAjax.submit(hjfInfo.getCp()+"/phoneBill/phoneInfo")
}







/*
 * var phone = var r = jserviceService.test(phone)
 */
var jserviceService = jserviceService||{}
jserviceService.bind = function(s,sucDo,errDo,chkDo){if(jserviceService[s] == null){
console.log("noExistJsServiceMethod:"+s);
}
if(sucDo != null){
jserviceService[s + "_succdo"] = sucDo
}
if(errDo != null){
jserviceService[s + "_errdo"] = errDo
}
if(chkDo != null){
	jserviceService[s + "_checkDo"] = chkDo
}
}

jserviceService.test = function(phone){
	if(jserviceService["test_checkDo"] != null){
		if(!((jserviceService["test_checkDo"])())){
			return 
		}
	}
	hjfAjax.init()
if(phone== null){ phone = $$.getData("phone") }
	console.log(phone);
	hjfAjax.addData("phone",phone)
	var r = hjfAjax.submit(hjfInfo.getCp()+"/jservice/test")
if(r != null){
if(r.result == true && jserviceService["test_succdo"] != null){
jserviceService["test_succdo"](r)
}else if(r.result == false && jserviceService["test_errdo"] != null){
jserviceService["test_errdo"](r)
}
}
return r}



/*
var phone = 
var r = phonebillService.testjs(phone)
*/

var phonebillService = phonebillService||{}
phonebillService.bind = function(s,sucDo,errDo,chkDo){if(phonebillService[s] == null){
console.log("noExistJsServiceMethod:"+s);	}
if(sucDo != null){
phonebillService[s + "_succdo"] = sucDo	}
if(errDo != null){
phonebillService[s + "_errdo"] = errDo
	}
if(chkDo != null){
phonebillService[s + "_checkDo"] = chkDo	}	}

///////////////////////////////////////
phonebillService.testjs = function(phone){
if(phonebillService["testjs_checkDo"] != null){
if(!((phonebillService["testjs_checkDo"])())){
return 	}	}

	hjfAjax.init()
if(phone== null){ phone = $$.getData("phone") }
	hjfAjax.addData("phone",phone)
	var r = hjfAjax.submit(hjfInfo.getCp()+"/phonebill/testjs")
if(r != null){
if(r.result == true && phonebillService["testjs_succdo"] != null){
phonebillService["testjs_succdo"](r)	}else if(r.result == false && phonebillService["testjs_errdo"] != null){
phonebillService["testjs_errdo"](r)	}	}
return r}


/*
var aaa = 
var bbb = 
var r = xxxService.dododo(aaa,bbb)
*/

var xxxService = xxxService||{}
xxxService.bind = function(s,sucDo,errDo,chkDo){if(xxxService[s] == null){
console.log("noExistJsServiceMethod:"+s);	}
if(sucDo != null){
xxxService[s + "_succdo"] = sucDo	}
if(errDo != null){
xxxService[s + "_errdo"] = errDo
	}
if(chkDo != null){
xxxService[s + "_checkDo"] = chkDo	}	}

///////////////////////////////////////
xxxService.dododo = function(aaa,bbb){
if(xxxService["dododo_checkDo"] != null){
if(!((xxxService["dododo_checkDo"])())){
return 	}	}

	hjfAjax.init()
if(aaa== null){ aaa = $$.getData("aaa") }
	hjfAjax.addData("aaa",aaa)
if(bbb== null){ bbb = $$.getData("bbb") }
	hjfAjax.addData("bbb",bbb)
	var r = hjfAjax.submit(hjfInfo.getCp()+"/xxx/dododo")
if(r != null){
if(r.result == true && xxxService["dododo_succdo"] != null){
xxxService["dododo_succdo"](r)	}else if(r.result == false && xxxService["dododo_errdo"] != null){
xxxService["dododo_errdo"](r)	}	}
return r}



/*
var id = 
var name = 
var r = testdoService.dodo(id,name)
*/

var testdoService = testdoService||{}
testdoService.bind = function(s,sucDo,errDo,chkDo){if(testdoService[s] == null){
console.log("noExistJsServiceMethod:"+s);	}
if(sucDo != null){
testdoService[s + "_succdo"] = sucDo	}
if(errDo != null){
testdoService[s + "_errdo"] = errDo
	}
if(chkDo != null){
testdoService[s + "_checkDo"] = chkDo	}	}

///////////////////////////////////////
testdoService.dodo = function(id,name){
if(testdoService["dodo_checkDo"] != null){
if(!((testdoService["dodo_checkDo"])())){
return 	}	}

	hjfAjax.init()
if(id== null){ id = $$.getData("id") }
	hjfAjax.addData("id",id)
if(name== null){ name = $$.getData("name") }
	hjfAjax.addData("name",name)
	var r = hjfAjax.submit(hjfInfo.getCp()+"/testdo/dodo")
if(r != null){
if(r.result == true && testdoService["dodo_succdo"] != null){
testdoService["dodo_succdo"](r)	}else if(r.result == false && testdoService["dodo_errdo"] != null){
testdoService["dodo_errdo"](r)	}	}
return r}



/*
var loginName = 
var pwd = 
var email = 
var realName = 
var r = adminConfigService.createAdminAccount(loginName,pwd,email,realName)
*/

var adminConfigService = adminConfigService||{}
adminConfigService.bind = function(s,sucDo,errDo,chkDo){if(adminConfigService[s] == null){
console.log("noExistJsServiceMethod:"+s);	}
if(sucDo != null){
adminConfigService[s + "_succdo"] = sucDo	}
if(errDo != null){
adminConfigService[s + "_errdo"] = errDo
	}
if(chkDo != null){
adminConfigService[s + "_checkDo"] = chkDo	}	}

///////////////////////////////////////
adminConfigService.createAdminAccount = function(loginName,pwd,email,realName){
if(adminConfigService["createAdminAccount_checkDo"] != null){
if(!((adminConfigService["createAdminAccount_checkDo"])())){
return 	}	}

	hjfAjax.init()
if(loginName== null){ loginName = $$.getData("loginName") }
	hjfAjax.addData("loginName",loginName)
if(pwd== null){ pwd = $$.getData("pwd") }
	hjfAjax.addData("pwd",pwd)
if(email== null){ email = $$.getData("email") }
	hjfAjax.addData("email",email)
if(realName== null){ realName = $$.getData("realName") }
	hjfAjax.addData("realName",realName)
	var r = hjfAjax.submit(hjfInfo.getCp()+"/adminConfig/createAdminAccount")
if(r != null){
if(r.result == true && adminConfigService["createAdminAccount_succdo"] != null){
adminConfigService["createAdminAccount_succdo"](r)	}else if(r.result == false && adminConfigService["createAdminAccount_errdo"] != null){
adminConfigService["createAdminAccount_errdo"](r)	}	}
return r}


///////////////////////////////////////
adminConfigService.adminIsCreated = function(){
if(adminConfigService["adminIsCreated_checkDo"] != null){
if(!((adminConfigService["adminIsCreated_checkDo"])())){
return 	}	}

	hjfAjax.init()
	var r = hjfAjax.submit(hjfInfo.getCp()+"/adminConfig/adminIsCreated")
if(r != null){
if(r.result == true && adminConfigService["adminIsCreated_succdo"] != null){
adminConfigService["adminIsCreated_succdo"](r)	}else if(r.result == false && adminConfigService["adminIsCreated_errdo"] != null){
adminConfigService["adminIsCreated_errdo"](r)	}	}
return r}