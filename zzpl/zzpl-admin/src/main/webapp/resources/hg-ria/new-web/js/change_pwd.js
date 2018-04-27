$(function(){
	var $old_pwd = $('#old_pwd');
	var $new_pwd_1 = $('#new_pwd_1');
	var $new_pwd_2 = $('#new_pwd_2');
	var flag = true;
	$old_pwd.blur(function(){
      if($old_pwd.val()==""||$old_pwd.val().length<6||$old_pwd.val().length>18){
         $(this).next().css('display','');
         flag = false;
      }else{
      	$(this).next().css('display','none');
      	flag = true;
      }
	})
	$new_pwd_1.blur(function(){
	  if($new_pwd_1.val()==""||$new_pwd_1.val().length<6||$new_pwd_1.val().length>18){
	  	if(flag){	
         $(this).next().css('display','');
         flag = false;
	  	}
      }else{
	  	$(this).next().css('display','none');
	  	flag = true;
	  }
	})
	$new_pwd_2.blur(function(){
	  if($new_pwd_2.val()!= $new_pwd_1.val()){
	  	if(flag){
         $(this).next().css('display','');
	  	}
      }else{
      	$(this).next().css('display','none');
      }
	})
})