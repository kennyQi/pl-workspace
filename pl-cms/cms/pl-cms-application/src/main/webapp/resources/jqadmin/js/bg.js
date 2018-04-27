// JavaScript Document
//验证表单项为空或未选择的情况
function validate(){
	var sr=document.getElementById("sr");
    if(sr.value==""){
	  document.getElementById("sryz").innerHTML="密码不能为空!";
	  event.returnValue=false;	}
}
	
//密码
	function checksr(ole){
		if(ole.value.length<6){
			document.getElementById("sryz").innerHTML="密码不能少于6个字符";
			document.getElementById("sryz").className="red";
			ole.focus();
			event.returnValue=false;
			}
			else if(ole.value.length>20){
			document.getElementById("sryz").innerHTML="密码不能多于20个字符";
			document.getElementById("sryz").className="red";
			ole.focus();
			event.returnValue=false;
			}
			else{
				document.getElementById("sryz").innerHTML="正确";
				document.getElementById("sryz").className="green";
				}
		}
////////////////////

function ck(b){
var input = document.getElementsByTagName("input");  
for (var i=0;i<input.length ;i++ )  
{ if(input[i].type=="checkbox")     
	       input[i].checked = b;    }}


function words_deal(){ 
var Length=$("#gname").val().length; 
if(Length>10){  
$("#zit").show(); 
   }else{
	 $("#zit").hide();
	 }
}
function words_dea(){ 
var Length=$("#zxbi").val().length; 
if(Length>30){  
$("#zit").show(); 
   }else{
	 $("#zit").hide();
	 }
}
 
//$(function(){
//		window.onload = function()
//		{
//			var $li = $('.manage li');
//			var $tab = $('.tab');
//			$li.click(function(){
//				var $this = $(this);
//				var $t = $this.index();
//				$li.removeClass();
//				$this.addClass('sel');
//				$tab.css('display','none');
//				$tab.eq($t).css('display','block');
//			})
//		}
//	});
////////////////////////////////////
		
$('#delt').click(function(){
	var num = 0;
	$(':checkbox[name=mm]').each(function(){
		if($(this).attr('checked'))
		{$(this).closest('tr').remove();num++;}
		})

		});	

function checkAll(e, itemName)
{
  var aa = document.getElementsByName(itemName);
  for (var i=0; i<aa.length; i++)
   aa[i].checked = e.checked;
}
function checkItem(e, allName)
{
  var all = document.getElementsByName(allName)[0];
  if(!e.checked) all.checked = false;
  else
  {
    var aa = document.getElementsByName(e.name);
    for (var i=0; i<aa.length; i++)
     if(!aa[i].checked) return;
    all.checked = true;
  }
}	      

			
		

