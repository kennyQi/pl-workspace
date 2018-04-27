//公用js
function dbp(s){
//    console.debug(s);
}

function setData(data) {
    if (data == "" || data == null) {
        return;
    }
    for (var k in data) {
        var e = $("[dname = '" + k + "']");
        if(e.length <=0){
        	var e = $("[name = '" + k + "']");
        }
        e.val(data[k]);
        if (e.is("textarea")) {
            e.html(data[k]);
        }
    }
}

function AjaxGetDataSimple(url){
    return ut.ajaxGetData(url+"?"+(new Date().getTime()),null);
}


function replaceVal(k, v, string) {
	eval("var re = /@\\{" + k + "\\}/g");
	string = string.replace(re, v);
	return string;
}

function checkDateInput(rname){
    var e =  $("[rname='"+rname+"']");
     if(e.val() && e.val() != ""){
         e.attr("name", e.attr("rname"));
     }else{
         e.removeAttr("name");
     }
 }


function currentDlg(){
    return $.pdialog.getCurrent();
}
function currentNavTab(){
    return navTab.getCurrentPanel();
}

function toSelect(e) {
    ut.assertIsTrue(e!=null);
    var el = e.find("select[tosel]")
    el.each(function () {
        var v = $(this).attr("tosel");
        if (v && v != "") {
            var el2 = $(this).find("option");
            el2.each(function () {
                if (this.value == v) {
                    $(this).attr("selected", true);
                }
            })

        }

    })
}

function submitQueryPreprocess(){
	  var c=  currentNavTab()
	    c.find("[date_input]").each(function(){
	        var e=$(this);
	        if(e.val() != null && e.val() != "" ){
	            e.attr("name", e.attr("rname"));
	        }
	    })
	}



