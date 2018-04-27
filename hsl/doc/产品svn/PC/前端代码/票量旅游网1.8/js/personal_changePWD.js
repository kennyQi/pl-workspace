// 使用页面：company_changePWD.html
// 日期：2015、4/20
// 作者：lw
$(document).ready(function() {
  $('.changeBox input').focus(function(){    
	  $(this).next('i').html('');
	  });
  $('.changeBox input').blur(function(){    
	  var pwds = $(this).val();
	  if(pwds != '' && pwds.length<6){    //判断密码长度
		  $(this).next('i').html('密码长度不允许小于6位');
		  };
	  var regular = /^([^\`\+\~\!\#\$\%\^\&\*\(\)\|\}\{\=\"\'\！\￥\……\（\）\——]*[\+\~\!\#\$\%\^\&\*\(\)\|\}\{\=\"\'\`\！\?\:\<\>\•\“\”\；\‘\‘\〈\ 〉\￥\……\（\）\——\｛\｝\【\】\\\/\;\：\？\《\》\。\，\、\[\]\,]+.*)$/;
    if(regular.test(pwds)){     //判断是否包含特殊字符
     $(this).next('i').html('请输入6-20位字符密码,不能包含特殊字符'); 
           return false;
      };
	  $('.changeBox input:eq(2)').blur(function(){
		  var loadPWD=$('.changeBox input:eq(0)').val();     //获取原密码
	      var newPWD = $('.changeBox input:eq(1)').val();    //获取新密码
	      var newPWD1 = $('.changeBox input:eq(2)').val();    //获取重复输入的新密码
		  if(newPWD != newPWD1){
		  $('.changeBox input:eq(2)').next('i').html('两次输入的密码不相等');
		  }
		  });
	  });
$('.yes').click(function(){         //点击确定按钮判断密码是否为空
     var loadPWD=$('.changeBox input:eq(0)').val();     //获取原密码
	 var newPWD = $('.changeBox input:eq(1)').val();    //获取新密码
	 var newPWD1 = $('.changeBox input:eq(2)').val();    //获取重复输入的新密码
	for(i=0;i<$('.changeBox input').length;i++){
		var value = $('.changeBox input:eq('+i+')').val();
		if(value == ''){
			$('.changeBox input:eq('+i+')').next('i').html('密码不能为空');
			}
		}
	if(loadPWD != '' && loadPWD.length>=6 && newPWD != '' && newPWD.length>=6 && newPWD1 != '' && newPWD1.length>=6 && newPWD == newPWD1){  //验证条件
		alert ('验证成功');
		}
	});
});