	// 使用页面：company_list.html
	// 日期：2015、4/20
	// 作者：lw
	
	$(document).ready(function() {	
	
	$(document).on("click",".rightNav li",function(){
		$(".rightTable li i").removeClass("selected3");	  //取消全选
		$(".rightNav li").removeClass("selected1");
		$(this).addClass("selected1");    //更改部门列表样式
		$(".rightTable li:not(:eq(0))").remove();
		var val= $(this).find('a').text(); //获取当前点击a的值
		$.ajax(oBject);
	    $(".position:not(:contains("+val+"))").parents("li:not(:eq(0))").remove();
		});
	   //内容选择
	$(document).on("click",".rightTable li",function(){
		$(".rightTable li").removeClass("selected2");
		$(this).addClass("selected2");
		});
	   //复选
	$(document).on("click",".rightTable li i",function(){
		$(this).toggleClass("selected3");
		});
	   //全选
	$(document).on("click",".rightTable .title i",function(){
		if($(".rightTable .title i").hasClass("selected3")){
			$(".rightTable li i").addClass("selected3");
		}else{
			$(".rightTable li i").removeClass("selected3");	
		}
	});
	
	    //弹出框
	    var documentWidth = $(window).width();    //屏幕可视区宽度
	    var documentHeight = $(window).height();   //屏幕可视区高度
	
	    $("[rel='dialog']").click(function(){
	        var forDialog=$(this).attr("for")
	            ,msgbox=$("."+forDialog+" .boxMessage")
	            ,boxWidth=msgbox.width()
	            ,boxHeight=msgbox.height()
	            ;
	            $("."+forDialog).show();
	            msgbox.css({"margin-left":(documentWidth-boxWidth)/2});
	            msgbox.css({"margin-top":(documentHeight-boxHeight)/2});

	    });
	    $(".boxTitle a").click(function(){
	        $(this).closest("[opt='box']").hide();
	    });
	
	
	
		//点击导入中的选择文件
		$(".choice").click(function(){
			$(".file").click();
		});
	    $("#uploadfileName").change(function(){
	       console.log($(this).val());
	        $("#uploadFile").val($(this).val());
	    });
	    //上传文件
	    $("#uploadYes").click(function(){
	        $("#uploadFileForm").submit();
	    });
	    
	  //给导入导出添加事件
		$("#download").on("click",function(){
			var deptId=$('#department_list .selected1').attr('id');
			location.href = "../company/organizeManage/excel?id="+deptId;
		});
	    
	    
	    //新增组织
	    $("#addgroup").click(function(){
	    	var name=$("#group").val();
	    	var reg1 = /^[\u4E00-\u9FA5]{0,20}$/;
	    	  if(reg1.test(name)){
	    		  var divArr = 	$(this).next().html();
	    		  $("#group").next(".eorr").html("");
	    	 }else{
	    		$("#group").next(".eorr").html("<div class='wrong error'><i></i>请输入正确的组织名称</div>");
	    		return false;
	    	 }
	    	if(name!=''){
	    		$.ajax({
			 		cache: true,
			 		type: "POST",
			 		url:'../company/organizeManage/addCompany?companyName='+encodeURI(name),
			 		async: false,
			 		//contentType: 'application/x-www-form-urlencoded; charset=utf-8',
			 	    error: function(request) {
			 	        alert("连接失败");
			 	    },
			 	    success: function(data) {
			 	    	location.reload();
			 	    }
			 	});
	    	}
	    	
	    });
	    
	    //添加部门
	    $("#adddepart").click(function(){
	    	var name=$("#depart").val()
	    	,id=$("#company_list .selected").attr("id");
	    	var reg1 = /^[\u4E00-\u9FA5]{0,20}$/;
	    	if(reg1.test(name)){
	    		$("#depart").next(".eorr").html("");
		    	}else{
		    		$("#depart").next(".eorr").html("请输入正确的名字");
		    	  return false;
		    	}
	    	if(name!=''){
	    		$.ajax({
			 		cache: true,
			 		type: "POST",
			 		url:'../company/organizeManage/addDepartMent?deptName='+encodeURI(name)+'&companyId='+id,
			 		async: false,
			 	    error: function(request) {
			 	        alert("连接失败");
			 	    },
			 	    success: function(data) {
			 	    	location.reload();
			 	    }
			 	});
	    	}
	    });
	    
	  //验证
    	$("#person_name").blur(function(){
    		var g =$(this).val(); 
    		var reg1 = /^[\u4E00-\u9FA5]{0,20}$/;
    	  if(reg1.test(g)){
    		  var divArr = 	$(this).next().html();
    	 	  $(this).next(".eorr").html("");
	    	}else{
	    	  $(this).next(".eorr").html("<div class='wrong error'><i></i>请输入正确的名字</div>");
	    	  return false;
	    	}
    	});
    	//职务
    	$("#person_job").blur(function(){
    		var tel =$(this).val(); 
    	 	var reg2 = /^[\u4E00-\u9FA5]{0,20}$/;
    	  if(reg2.test(tel)){
    		  $(this).next(".eorr").html("");
    	  }else{
			 $(this).next(".eorr").html("<div class='wrong error'><i></i>请输入正确的职务名称</div>")
			 return false;
    	  }
     });
    	 //电话号码
    	$("#person_mobile").blur(function(){
    		var g =$(this).val(); 
    		var reg3 = /^0?(13|15|18)[0-9]{9}$/;
    	  if(reg3.test(g)){
    		  $(this).next(".eorr").html("");
    	  }else{
			 $(this).next(".eorr").html("<div class='wrong error'><i></i>手机格式错误</div>")
			 return false;
    	   }
    	});
    	
    	//身份证号
    	$("#person_cardid").blur(function(){
    		var tel =$(this).val(); 
    	 	var reg4 =/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])(\d{4}$|\d{3}[Xx]{1})$/;
    	  if(reg4.test(tel)){
    		  $(this).next(".eorr").html("");
    	   }else{
  			 $(this).next(".eorr").html("<div class='wrong error'><i></i>身份证格式错误</div>")
			 return false;
	       }
    	   
    	});
	       
	    
	    //添加成员
	    $("#addperson").click(function(){
	    	
	    	var reg1 = /^[\u4E00-\u9FA5]{0,20}$/;
	    	var reg2 = /^0?(13|15|17|18)[0-9]{9}$/;
	    	var reg3 =/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])(\d{4}$|\d{3}[Xx]{1})$/;
	    	
	    	var name=$("#person_name").val()
	    		,phone=$("#person_mobile").val()
	    		,cardid=$("#person_cardid").val()
	    		;
	    	if(reg1.test(name)){
	    		$("#person_name").next(".eorr").html("");
		    	}else{
		    		$("#person_name").next(".eorr").html("请输入正确的名字");
		    	  return false;
		    	}
	    	if(reg2.test(phone)){
	    		$("#person_mobile").next(".eorr").html("");
		    	}else{
		    		$("#person_mobile").next(".eorr").html("请输入正确的手机号");
		    	  return false;
		    	}
	    	if(reg3.test(cardid)){
	    		$("#person_cardid").next(".eorr").html("");
		    	}else{
		    		$("#person_cardid").next(".eorr").html("请输入正确的身份证");
		    	  return false;
		    	}
	    	
	    	
    		$.ajax({
         		cache: true,
         		type: "POST",
         		url:'../company/organizeManage/addMember',
         		data:$('#add_person').serialize(),
         		async: false,
         	    error: function(request) {
         	        alert("连接失败");
         	    },
         	    success: function(data) {
         	    	var msg = eval("("+data+")");
         		    if(msg.status=="success"){
         		    	location.reload();
         		    }else{
         		    	alert("添加失败");
         		    }
         	    }
         	});
	    	
	    	
	    });
	    
	    
	  //给删除成员按钮添加事件
		$("#del").on("click",function(){
			var checkedBox = $("#memberList li:not('.title') i.selected3");
			if(checkedBox.length>0){
				if(!confirm("删除之后数据不可恢复，确定删除吗？")){
					return;
				}
				var id ="";
				checkedBox.each(function(){
					id+=","+$(this).attr("id");
				});
				id=id.slice(1);
				//location.href = '${(contextPath)!}/company/organizeManage/deleteMember?id='+id;
				$.ajax({
			 		cache: true,
			 		type: "get",
			 		url:'../company/organizeManage/deleteMember?id='+id,
			 		async: false,
			 	    error: function(request) {
			 	        alert("连接失败");
			 	    },
			 	    success: function(data) {
						var data = eval("("+data+")");
			 		    if(data.status=="success"){
							location.reload();
			 		    }else{
			 		    	alert("删除失败");
			 		    }
			 	    }
			 	});
			}else{
				alert("请先选择一个成员");
			}
		});
		
		//当没有部门时，右侧员工信息头部不会出现
	var bmNum = $(".bmNum").val();
	if(bmNum < 1){
		$(".depart").remove();
	}
	    
	});

	

