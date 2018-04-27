var ut={log:function(s){
console.log(s)},tip:function(n,msg){
var $e=$("[tip_for='"+n+"']");
ut.assertIsTrue(($e.length>0));
ut.assertExist(msg);
ut.setVal($e,msg);},setVal:function(e,s){
e.val(s).html(s);},produceCounter:function(mx,$te,str,str2,str3){
ut.assertPmtSize(arguments,5);
var prt={intervalId:null,count:0,start:function(){
if(prt.count==0&&prt.intervalId==null){
prt.count=mx;
prt.intervalId=setInterval(function(){
if(prt.count==0){
ut.setVal($te,str);
clearInterval(prt.intervalId);
prt.intervalId=null;
return;};
prt.count--;
ut.setVal($te,(""+str2+prt.count+str3));},1005);};},isStop:function(){
if(prt.count==0&&prt.intervalId==null){
return true;}else{
return false;}}};
return prt;},setAllTip:function(s){
if(s=null){
s="&nbsp;";}
$("[tip_for]").each(function(){
ut.setVal($(this),s)})},s2obj:function(s){
return eval(ut.code2json(s));},validateAll:function(e){
var el;
var ret=true;
if(e!=null){
el=e.find("[tip_for]");}else{
el=$("[tip_for]")};
el.each(function(){
var te=$("#"+$(this).attr("tip_for"));
var vltsText=$(this).attr("vld");
ut.assertExist(vltsText);
var vlts=ut.s2obj(vltsText);
for(var vlt in vlts){
var r2=eval(vlt+"(te)");
var vltv=vlts[vlt];
if(!(r2==true)){
ret=false;
if(vltv==null||vltv==""){
ut.setVal($(this),r2);}else{
ut.setVal($(this),vltv);}
break;}
ut.setVal($(this),"&nbsp;");}});
return ret;},code2json:function(s){
var valArr=(s.split("||"));
var ret="({";
for(var i in valArr){
if((/:'.*'/.test(valArr[i]))){
ret=ret+valArr[i];}else{
ret=ret+valArr[i]+":''";}
if(i!=(valArr.length-1)){
ret=ret+",";}}
return(ret+"})");},ielog:function(a){
if(a==null){
alert("@log");
return;}
if(a instanceof Array){
var r="Array:[\n";
for(i in a){
r=r+i+":    "+a[i]+"  ,\n";}
r=r+"]";
alert(r);
return;}
if(a instanceof Object){
var r="Object:{\n";
for(var i in a){
if(a[i]!=null){
var v=(a[i].toString()).substring(0,10);
r=r+i+":\"" + v + "\",\n";}}
r=r+"}";
alert(r);
return;}
alert(a);},assertExist:function(v){
if(v==null||v==""){
throw new Error("!error@assertExist()");}},assertPmtSize:function(pmtArr,size){
if(pmtArr.length!=size){
throw new Error("!error@assertPmtAllExist()");}},assertIsTrue:function(result){
if(!result){
throw new Error("!error@assertIsTrue()");}},json2obj:function(j){
return eval("("+j+")");},ajaxGetData:function(url,data){
var ret=null;
$.ajax({
'url':url,
cache:false,
type:"post",
data:data,
async:false,
dataType:"html",
success:function(data){
ut.assertIsTrue((data!=null));
ret=data;},
error:function(){
alert("error!@ut.ajaxGetData")}});
return ret;},jqElementExist:function(e){
if(e.length>0){
return true;}
return false;},setFormData:function(data,form){
ut.assertIsTrue(form!=null);
if(data==null||data==""){
return;}
for(var k in data){
if(data[k]==null||data[k]==""){
continue;}
var e=form.find("[name = '"+k+"']");
if(e.is("input")){
e.val(data[k]);}else if(e.is("select")){
var v=data[k];
var el2=$(e).find("option");
el2.each(function(){
if(this.value==v){
$(this).attr("selected",true);}})}}},simpleAjax:function(url,d,dt){
dbp(dt);
if(dt==null){
dt="text";}
dbp(dt);
var ret=null;
$.ajax({
url:url,
cache:false,
type:"post",
async:false,
dataType:dt,
data:d,
success:function(r){
ret=r;},
error:function(){}});
return ret;}};
$.extend({log:function(s){
ut.log(s);}});
function tipByJsonResult(data){
var reg=/^tip>>/;
for(var i in data){
if(reg.test(i)){
ut.tip(i.replace(reg,""),data[i]);}}}
function hrefRedirect(s){
window.location.href=s;}
function simpleAjaxValidateForm(form,extraData){
form.attr("onsubmit","return false");
var path=form.attr("action");
var r;
$.ajax({
url:path+"?"+form.serialize()+"&"+new Date().getTime(),
cache:false,
type:"post",
async:false,
dataType:"json",
data:extraData,
success:function(data){
r=data;},
error:function(){
alert("exception@simpleAjax()");}});
return r;}
function simpleAjaxValidateOneValue(path,e){
var r;
$.ajax({
url:path+"?"+new Date().getTime()+"&"+e.attr('name')+"="+e.val(),
cache:false,
type:"post",
async:false,
dataType:"json",
success:function(data){
r=data;},
error:function(){
alert("exception@simpleAjax()");}});
return r;}

