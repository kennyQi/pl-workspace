<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>资料编辑</title>
<!-- meta信息，可维护 -->
<meta charset="UTF-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta content="telephone=no" name="format-detection" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!-- 样式 -->
<link rel="stylesheet" href="${ctx}/css/base.2.0.css" />
<link rel="stylesheet" href="${ctx}/css/company.css"/>
<script type="text/javascript" src="${ctx}/js/jquery-1.10.2.js"></script>
 <script type="text/javascript" src="${ctx}/js/jquery.js"></script> 	 <script type="text/javascript" src="${ctx}/js/jquery-1.10.2.js"></script> 	 <script type="text/javascript" src="${ctx}/js/jquery.form.js"></script> 	 <script type="text/javascript" src="${ctx}/js/json2.js"></script> 	 <script type="text/javascript" src="${ctx}/js/jsbox.js"></script> 	 <script type="text/javascript" src="${ctx}/js/pop.ups/style.js"></script><script type="text/javascript" src="${ctx}/js/pop.ups/pop.ups.js"></script>	 <script type="text/javascript" src="${ctx}/js/hsl.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#province").change(function(){
		if($("#province").val()=="0"){
			var layer = "<option value='0'>选择城市</option>";  
			$("#city").empty();
			$("#city").append(layer);
			//$("#location").empty();
		}else{
			$.ajax({
			       type: "POST",
			       url: "${ctx}/user/searchCity?province="+$("#province").val(),
			       contentType: "application/x-www-form-urlencoded; charset=utf-8",
			       dataType:"json",
			       success: function(data) {
			    	   if (data.length > 0) {  
			    		   var layer = "<option value='0'>选择城市</option>";  
				            $.each(data, function (idx, item) {  
				                layer += "<option value="+item.code +">"+ item.name + "</option>";  
				            });
				            
							$("#city").empty();
							$("#city").append(layer);
							/* $("#location").empty();
							$("#location").append($("#province option:selected").text());
							if($("#city").val()!="0"){
								$("#location").append(" - " + $("#city option:selected").text());
							} */
				        }
			       }
			});
		}
	});
	/* $("#city").change(function(){
		if($("#city").val()=="0"){
			var layer = "<option value='0'>选择城市</option>";  
			$("#city").empty();
			$("#city").append(layer);
			$("#location").empty();
			if($("#province").val()!="0"){
				$("#location").append($("#province option:selected").text());
			}
		}else{
			$("#location").html($("#province option:selected").text() + " - " + $("#city option:selected").text());
		}
	}); */
});

function doSave(){
	var nickName = $("#nickName").val();
	var email = $("#email").val();
	if(nickName.length>6){    	
		showTip('昵称的长度不超过6个字符','好的'); 
		$("#nickName").focus();
		return false;
	}
	var reg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/               
    if(!reg.test(email)){   
    	showTip('请填写正确的邮箱','好的'); 
    	$("#email").focus();
        return false; 
    }
	$.pop.lock(true);
	$.pop.load(true, '正在修改中...');
	$("#editInfoForm").ajaxSubmit({
		timeout:60000,
		dataType:'json',
		error:function() {
		    $.pop.load(false, function() {
    			$.pop.lock(false);
		    	setTimeout(function() {
		    		showTip('系统繁忙,请稍候','好的');
		    	}, 666);
		    });
    	},
		success:function(data) {
			$.pop.load(false, function() {
       			$.pop.lock(false);
       			setTimeout(function() {
       				if (data.result == "1") {	//修改成功
       					$.pop.tips('修改成功', 3, function() {
			            	location.href = "${ctx}/user/userDetailInfo";
           				});
		           	} else {
		           		showTip(data.message,"好的");
		           	}
       			}, 666);
       		});
		}
	});
}
</script>
</head>
<body class="personal">

<!-- 页头  -->
<header class="header">
	  <h1>资料编辑</h1>	  
	  <div class="left-head"> 
	    <a id="goBack" href="${ctx}/user/userDetailInfo" class="tc_back head-btn">
	        <span class="inset_shadow">
	            <span class="head-return"></span>
	        </span>
	    </a> 
	  </div>
</header>

	<!-- 页头 -->
	<section class="hd_myprofile">
		 	 <c:if test="${!empty userDTO.baseInfo.image}">
		 		<img src="${userDTO.baseInfo.image}" alt="头像" id="headImage" class="hd_data_p hd_data_pr radius"/>
		 	 </c:if>
		 	 <c:if test="${empty userDTO.baseInfo.image}">
		 		<img src="${ctx}/img/defaultHeadImage.png" alt="头像" id="headImage" class="hd_data_p hd_data_pr radius"/>
		 	 </c:if>
		 	 <h2 class="name_l">头像</h2>
		 	 <em id="upfileimg" class="m_dlog"></em>
	</section>

	
	<!--start 页面内容 -->
		<section class="editplist">	
		<form id="editInfoForm" action="${ctx}/user/updateCompanyUser" method="post"> 
			<input type="hidden" name="userId" value="${userDTO.id}"/>	
		 	<a href="javascript:;"><div><span>昵称</span><p><input type="text" id="nickName" name="nickName" value="${userDTO.baseInfo.nickName}"></p></div></a>
		    <a href="javascript:;"><div><span>邮箱</span><p><input type="text" id="email" name="email" value="${userDTO.contactInfo.email}"></p></div></a>
		    <a href="javascript:;"><div><span>所在地</span><div class="rc_td">
	            <select name="province" id="province" class="sel_txt sw1 ">
				 	<option value="0">选择省</option>
      				<c:forEach items="${provinces}" var="provinceList">
      					<option value="${provinceList.code}" 
      						<c:if test="${userDTO.contactInfo.provinceId==provinceList.code}">selected="selected"</c:if>
      					 >
      					${provinceList.name}</option>
      				</c:forEach>
				</select>
                <select name="city" id="city" class="sel_txt sw1 ">
					<option value="0">选择城市</option>
					<c:forEach items="${cities}" var="cityList">
      					<option value="${cityList.code}" 
      						<c:if test="${userDTO.contactInfo.cityId==cityList.code}">selected="selected"</c:if>
      					 >
      					${cityList.name}</option>
      				</c:forEach>
				</select>                
                </div><em></em></div></a>
		</form>	
	  	</section>

	    <div class="col_div pl25 pr25">
            <a href="javascript:doSave();" id="btn_click" class="btn btn-org radius3 mt35 " title="修改">修改</a>
        </div>	

			
   <!-- <section id="f_tel" style="display:none">
    <div class="f_tel_title">
      <div class="f_tel_hd">
       <a href="tel:0571-854246">相机</a>
       <a href="tel:0571-854246">从相册选择</a>
       <a id="J_hide">取消</a>
       </div>
     </div>
   </section> -->
	<!--end 页面内容 -->
	 <SCRIPT TYPE="text/javascript" src="${ctx}/js/showtip.js"> </SCRIPT>
	 <script type="text/javascript" src="${ctx}/js/ajaxupload.js"></script>
	 <SCRIPT TYPE="text/javascript"> 
	 var maxSize = 2*1024*1024;//不设置表示不限制大小，此为2M
	 new AjaxUpload($("#headImage"), {
	 	action : "${ctx}/file/imgUpload",
	 	name : 'file',
	 	responseType : 'json',
	 	onSubmit : function(file, ext, fileSize) {
	 		if(maxSize && maxSize < fileSize) {
	 			alert("图片大小超过了2M！");
	 			return false;
	 		}
	 		if (!(ext && /^(png|jpg|gif|jpeg|PNG|JPG|GIF|JPEG)$/.test(ext))) {
	 			alert("请上传图片格式文件！");
	 			return false;
	 		}
	 	},
	 	onComplete : function(file, response, fileSize) {
	 		console.info(response);
	 		if ("success" == response.status) {
	   		
	 	  		//$('#headImage').attr("src", response.imageUrl);
	 	  		//$('#' + idRef).val(response.imageId);
	 	  		updateHeadImage(response.imageUrl);
	 	  	} else {
	 	  		alert(response.msg);
	 	  	}
	 	}
	 });

	 function updateHeadImage(imageUrl){
	 	$.ajax({
	 		type:"post",
	 		url:"${ctx}/user/updateHeadImage",
	 		dataType:"json",
	 		data: {
	 			imagePath: imageUrl
	 		},
	 		success:function(msg){
	 			$('#headImage').attr("src", imageUrl);
	 		}
	 	});
	 }
  /*    $(".m_dlog").click(function(){
     	$("#f_tel").show();
     });
     $("#J_hide").click(function(){     
     	$("#f_tel").hide();
     });

     
     $("#p_name").blur(function(){ 
     	var h =$("#p_name").val();      		    	 
    		if(h.length<=6){    			
    		}
    		else{
    			 showTip('昵称的长度不超过6个字符','好的');    			    			
    		}			
     }) 
      $("#p_email").blur(function(){ 
     	var email =$("#p_name").val();  
     	var reg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/    		    	 
    		if(reg.test(email)){    			
    		}    		
    		else{
    			 showTip('请填写正确的邮箱','好的');    			    			
    		}		
     }) 

      $("#btn_click").click(function(){
        var h =$("#p_name").val();                 
        if(h.length>6){         
        }
        else{
           showTip('昵称的长度不超过6个字符','好的');
           return false;                    
        }     
        var email =$("#p_name").val();  
      var reg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/               
        if(reg.test(email)){          
        }       
        else{
           showTip('请填写正确的邮箱','好的'); 
           return false;                   
        } 


      })    
$('#DropProvince').change(function () {
                var $t = $(this);
                $('#DropCity').empty();
                  $('#DropArea').empty();
                 $('#Dcity').val("");    
                  $('#Darea').val("");    
                $('#DropCity').append('<option value="0">选择城市</option>');
                 $('#DropArea').append('<option value="0">选择地区</option>'); 
                    $.ajax({
                        type: "POST",
                        dataType: "json", 
                        url: "ajax/GetCity.ashx",
                        data: {id:$t.val()},
                        success: function (result) {
                             $.each(result, function (index, item) {
                                 $('#DropCity').append('<option value="'+item.Id+'">'+item.Name+'</option>');
                            })
                         }
                    });
               });
               
       $('#DropCity').change(function () {  
                var $t = $(this);  
                 $('#DropArea').empty();
                   $('#Darea').val("");    
                $('#DropArea').append('<option value="0">选择地区</option>'); 
                $('#Dcity').val($t.val());    
                    $.ajax({
                        type: "POST",
                        dataType: "json", 
                        url: "ajax/GetArea.ashx",
                        data: {id:$t.val()},
                        success: function (result) {
                             $.each(result, function (index, item) {
                                 $('#DropArea').append('<option value="'+item.Id+'">'+item.Name+'</option>');
                            })
                         }
                    });
               });
    $('#DropArea').change(function () {  
         $('#Darea').val($(this).val());    
    })
    


	
 */
  
	 </SCRIPT>
</body>
</html>