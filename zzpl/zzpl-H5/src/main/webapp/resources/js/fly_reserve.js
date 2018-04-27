require.config({
    //通用js模块路径
	baseUrl:request_path+"/resources/js/",
    //通用框架
    paths: {
        jquery: 'libs/jquery-2.1.4.min'
    },

    waitSeconds: 0
});

//默认调用js
require(["jquery"],
    function($) {
        var scrollTop=0;
        var parent = null;
        $("#sumPricePlus").html("￥"+$("#sumPrice").text()*person_no);
       $(function(){
           //编辑联系人
           $(document).on("click",".passengerDetail",function(){
               var data={};
               var scene=$(this).closest(".scene");
               parent=$(this);
               data.myid=parent.find(".myid").html();
               data.name=parent.find(".name").html();
               data.cardDetail=parent.find(".cardDetail i").html();
               data.phone=parent.find(".phone").html();
               data.group=parent.find(".group").html();
               data.groupId=parent.find(".group").attr("data-value");
               for(var x in data){
                   $("#editorConnect").find("[data-name='"+x+"']").val(data[x]);
               }
               scrollTop=$(window).scrollTop();
               //弹出编辑页
               if(scene.attr("id")=="connect"){
            	   //列表中的联系人编辑
            	   $("#editor").css({"z-index":505,"display":""}).animate({ "z-index":"0"}, {
                       duration:400,
                       step: function(now,fx) {
                           $("#editor").css('-webkit-transform','translateX('+(now)+'px)');
                       },
                       complete:function(){
                           $("#connect").hide();
                           $("#editor .editorBack").data("state","editPer");
                       }
                   });
               }else{
            	   //订单中的联系人编辑
            	   $("#editor").css({"z-index":505,"display":""}).animate({ "z-index":"0"}, {
                       duration:400,
                       step: function(now,fx) {
                           $("#editor").css('-webkit-transform','translateX('+(now)+'px)');
                       },
                       complete:function(){
                           $("#reserve").hide();
                           if(buyOther=="1"){
                        	   $("#editor .editorBack").data("state","editPerList");
                           }
                       }
                   });
               }
              
               $(window).scrollTop(0);
           });
           
           
           
           //乘机人
           $("#choosePer").click(function(){
        	   if($("#buyOthers").text()==0){
              		alertMsg("无权限！");
              		return false;
              	}
        	   scrollTop=$(window).scrollTop();
               $(window).scrollTop(0);
               $("#connect").css({"z-index":505,"display":""}).animate({ "z-index":"0"}, {
                   duration:400,
                   step: function(now,fx) {
                       $("#connect").css('-webkit-transform','translateX('+now+'px)');
                   },
                   complete:function(){
                       $("#reserve").hide();
                   }
               });
           });
           
           //选中联系人
           $("#connect").on("click",".choose",function(){
        	   
               $(this).toggleClass("chooseOn");
               var temp=$("#passengerBoxTemp").html();
               var data=[];
               var p=$(this).closest(".connectBox");
               if(!$(this).hasClass("chooseOn")){
            	   person_no--;
            	   $(".del[data='"+p.find(".choose").attr("data")+"']").closest(".passengerBox").remove();
               }else{
            	   var length = $("#passengerList").find(".passengerBox").length;
            	   if (length >= 5) {
            		   $(this).toggleClass("chooseOn");
                       alertMsg("最多添加五个乘客");
                       return;
                   }
            	   person_no++;
	               data[0]={};
	               data[0].myid=p.find(".choose").attr("data");
	               data[0].name=p.find(".name").html();
	               data[0].cardDetail=p.find(".cardDetail i").html();
	               data[0].phone=p.find(".phone").html();
	               data[0].group=p.find(".group").html();
	               data[0].groupId=p.find(".group").attr("data-value");
	              var html=stringReplace_com(data,temp);
	              $("#passengerList").append(html);
               }
        	   $("#sumPricePlus").html("￥"+$("#sumPrice").text()*person_no);
           })
           
           //新增乘机人
           $(".addNew").click(function(){
        	   
        	   $("#editor").css({"z-index":505,"display":""}).animate({ "z-index":"0"}, {
                   duration:400,
                   step: function(now,fx) {
                       $("#editor").css('-webkit-transform','translateX('+(now)+'px)');
                       $("#editor .editorBack").data("state","addPer");
                   },
                   complete:function(){
                       $("#connect").hide();
                   }
               });
           });

           //完成
           $("#fulfil").click(function(){
        	   var data={};
        	   var parent=$("#editorConnect");
        	   data.myid=parent.find(".myid").val();
        	   data.name=parent.find(".name input").val();
               data.cardDetail=parent.find(".cardNo input").val();
               data.phone=parent.find(".phone input").val();
               data.group=parent.find(".center select option:selected").val();
               data.groupText=parent.find(".center select option:selected").text();
               var flag = true;
               var phonePattern=/^1[3|4|5|8][0-9]\d{8}$/;
               
               //验证手机号
               if(data.phone ==""||!phonePattern.test(data.phone)){
                   alertMsg("请填写正确手机号！");
                   parent.find(".phone input").focus();
                   flag = false;
               }
               
               var flag1 = true;
               //验证身份证号
               if (data.cardDetail.length != 0) {
       				reg=   /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
	       			if (!reg.test(data.cardDetail)) {
	       				//如果通过该验证，说明身份证格式正确，但准确性还需计算
	       				  if(data.cardDetail==18){
	       					   var idCardWi=new Array( 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ); //将前17位加权因子保存在数组里
	       					   var idCardY=new Array( 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ); //这是除以11后，可能产生的11位余数、验证码，也保存成数组
	       					   var idCardWiSum=0; //用来保存前17位各自乖以加权因子后的总和
	       					   for(var i=0;i<17;i++){
	       					    idCardWiSum+=idCard.substring(i,i+1)*idCardWi[i];
	       					   }
	
	       					   var idCardMod=idCardWiSum%11;//计算出校验码所在数组的位置
	       					   var idCardLast=idCard.substring(17);//得到最后一位身份证号码
	
	       				  	 //如果等于2，则说明校验码是10，身份证号码最后一位应该是X
	       				   	if(idCardMod==2){
	       				    	if(idCardLast=="X"||idCardLast=="x"){
	       				    		flag1 = true ;
	       				    	}else{
	       				    		alertMsg("身份证号码错误！");
	       				    		parent.find(".cardNo input").focus();
	       				    		flag1 = false;
	       				    	}
	       				  	 }else{
	       				   	 //用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
	       				    	if(idCardLast==idCardY[idCardMod]){
	       				    		flag1 = true ;
	       				    	}else{
	       				    		alertMsg("身份证号码错误！");
	       				    		parent.find(".cardNo input").focus();
	       				    		flag1 = false;
	       				    	}
	       				   	}
	       				  } else{
	       					  alertMsg("身份证号码错误！");
	       					  parent.find(".cardNo input").focus();
	       					flag1 = false;
	       				  }
	       			} else {
	       				flag1 = true ;
	       			}

	       		} else {
	       			alertMsg("身份证不为空！");
	       			parent.find(".cardNo input").focus();
	       			flag1 = false;
	       		}
               
               //验证姓名中文
               var flag2 = true;
               var Expression = /[^\u4E00-\u9FA5]/;
       		   var objExp = new RegExp(Expression);

       		   if(data.name.length != 0){
       			   if (objExp.test(data.name) == true) {
        			   alertMsg("姓名格式有误，请输入汉字");
        			   parent.find(".name input").focus();
        			   flag2 = false;
        		   } else {
        			   flag2 = true;
        		   }
       		   }else{
		       		   alertMsg("姓名格式有误，请输入汉字");
		 			   flag2 = false;
		 			   parent.find(".name input").focus();
       		   }
               
               if(flag&&flag1&&flag2){
            	   var connectUser = {};
                   connectUser.passengerName = data.name;
                   connectUser.idNO = data.cardDetail;
                   connectUser.telephone = data.phone;
                   connectUser.costCenterID = data.group;
                   connectUser.costCenterName = data.groupText;
                   var add = $("#editor .editorBack").data("state");
                   if(add == "editPer"||add == "editPerList"){
                	   $.ajax({ 
       	       			url : "../user/editConnectUser?id="+data.myid,
       	       			data : connectUser,
       	       			type : "post", 
       	       			cache : false, 
       	       			dataType : "json", //返回json数据 
       	       			error: function(){ 
       	       			}, 
       	       			success:function(datas){
       	       				if(datas.success){
       	       				//修改页面内容
       	                        var box=$("[data='"+data.myid+"']").parent("div");
       	                        box.each(function(){
       	                 		   $(this).find("[name='passengerTel']").val(data.phone);
       	                     	   $(this).find(".del").attr("data",data.myid);
       	                 		   $(this).find("[name='passengerName']").val(data.name);
       	                 		   $(this).find("[name='costCenterIDs']").val(data.group);
       	                 		   $(this).find("[name='passengeridCardNO']").val(data.cardDetail);
       	                 		   
       	                 		   
       	                     	   $(this).find(".phone").html(data.phone);
       	                     	   $(this).find(".cardDetail i").html(data.cardDetail);
       	                     	   $(this).find(".name").html(data.name);
       	                     	   $(this).find(".myid").html(data.myid);
       	                     	   $(this).find(".choose").attr("data",data.myid);
       	                     	   $(this).find(".group").html(data.groupText).attr("data-value",data.group);
       	                        })
       	       				}else{
       	       					alert("修改失败");
       	       				}
       	       			}
       	       		});
                   }else if(add == "addPer"){
                	   $.ajax({ 
          	       			url : "../user/addConnectUser",
          	       			data : connectUser,
          	       			type : "post", 
          	       			cache : false, 
          	       			dataType : "json", //返回json数据 
          	       			error: function(){ 
          	       			}, 
          	       			success:function(datas){
          	       				if(datas.success){
          	                        var str = "<div class='connectBox box'>"
    								  +"<span class='choose' data='"+datas.passenger.id+"'></span>"
    								  +"<div class='passengerDetail'>"
    								  +"<span class='phone' style='display: none'>"+datas.passenger.telephone+"</span>"
    								  +"<span class='myid' style='display: none'>"+datas.passenger.id+"</span>"
    								  +"<span class='name'>"+datas.passenger.passengerName+"</span> "
    								  +"<span class='group' data-value='"+datas.passenger.costCenterID+"'>"+datas.passenger.costCenterName+"</span>"
    								  +"<span class='cardDetail'>身份证 <i>"+datas.passenger.idNO+"</i></span> <label class='editor'>"
    								  +"<img src='../resources/images/reserveOrder.png' alt='' /></label>"
    								  +"</div>"
    								  +"</div>";
          	                        $("#connectList").append(str);
          	       				}else{
          	       					alert("新增失败");
          	       				}
          	       			}
          	       		});
                   }else{
                	    var box=$("[data='"+data.myid+"']").parent("div");
                        box.each(function(){
                 		   $(this).find("[name='passengerTel']").val(data.phone);
                     	   $(this).find(".del").attr("data",data.myid);
                 		   $(this).find("[name='passengerName']").val(data.name);
                 		   $(this).find("[name='costCenterIDs']").val(data.group);
                 		   $(this).find("[name='passengeridCardNO']").val(data.cardDetail);
                 		   
                 		   
                     	   $(this).find(".phone").html(data.phone);
                     	   $(this).find(".cardDetail i").html(data.cardDetail);
                     	   $(this).find(".name").html(data.name);
                     	   $(this).find(".myid").html(data.myid);
                     	   $(this).find(".choose").attr("data",data.myid);
                     	   $(this).find(".group").html(data.groupText).attr("data-value",data.group);
                        })
                   }
                   
                   var that=$(this);
                   
                   if(add == "addPer" || add == "editPer"){
                	   //联系人列表新增的时候
                	   $("#connect").show();
                	   that.closest(".scene").css({"z-index":50}).animate({ "z-index":"555"}, {
                           duration:400,
                           step: function(now,fx) {
                               that.closest(".scene").css('-webkit-transform','translateX('+(now-50)+'px)');
                           },
                           complete:function(){
                               that.closest(".scene").removeAttr("style").hide();
                               $("#editor .editorBack").data("state","normal");
                           }
                       });
                	   return;
                   }
                   $("#reserve").show();
                   that.closest(".scene").css({"z-index":50}).animate({ "z-index":"555"}, {
                       duration:400,
                       step: function(now,fx) {
                           that.closest(".scene").css('-webkit-transform','translateX('+(now-50)+'px)');
                       },
                       complete:function(){
                           that.closest(".scene").removeAttr("style").hide();
                       }
                   });
                   $(window).scrollTop(scrollTop);
                   //清空
                   $("#editorConnect").find(".myid").val("");
                   $("#editorConnect").find(".name input").val("");
                   $("#editorConnect").find(".cardNo input").val("");
                   $("#editorConnect").find(".phone input").val("");
               }
               
        	   
           })
           
           //返回
           $(".editorBack").click(function(){
               var that=$(this);
               
               if(that.data("state")=="addPer"){
            	   //联系人列表新增的时候
            	   $("#connect").show();
            	   that.closest(".scene").css({"z-index":50}).animate({ "z-index":"555"}, {
                       duration:400,
                       step: function(now,fx) {
                           that.closest(".scene").css('-webkit-transform','translateX('+(now-50)+'px)');
                       },
                       complete:function(){
                           that.closest(".scene").removeAttr("style").hide();
                           $("#editor .editorBack").data("state","normal");
                       }
                   });
            	   return;
               }
               $("#reserve").show();
               that.closest(".scene").css({"z-index":50}).animate({ "z-index":"555"}, {
                   duration:400,
                   step: function(now,fx) {
                       that.closest(".scene").css('-webkit-transform','translateX('+(now-50)+'px)');
                   },
                   complete:function(){
                       that.closest(".scene").removeAttr("style").hide();
                   }
               });
               $(window).scrollTop(scrollTop);
               //清空
               $("#editorConnect").find(".myid").val("");
               $("#editorConnect").find(".name input").val("");
               $("#editorConnect").find(".cardNo input").val("");
               $("#editorConnect").find(".phone input").val("");
           });

           //弹出审核

           $(".checkFlow").click(function(){
               //弹出审核页
               scrollTop=$(window).scrollTop();
               $(window).scrollTop(0);
               $("#checkFlow").css({"z-index":505,"display":""}).animate({ "z-index":"0"}, {
                   duration:400,
                   step: function(now,fx) {
                       $("#checkFlow").css('-webkit-transform','translateX('+now+'px)');
                   },
                   complete:function(){
                       $("#reserve").hide();
                   }
               });
           });


           //下拉和收起审核框
           $(document).on("click",".checkSelect dt",function(){
               $(this).nextAll().slideToggle();
               $(this).find("label").toggleClass("down");
           })

           
           //选中审核条件
           $("#checkPointer").on("click","dd",function(){
                var dd=$(this);
               dd.closest(".checkSelect").find("dd").removeClass("on");
               dd.addClass("on");
               var data = dd.attr("data");
               dd.closest(".checkSelect").find("dt label").html(dd.html());
               if(dd.closest("dl").attr("for")){
                   $("#"+dd.closest("dl").attr("for")).html(dd.html());
                   if(dd.closest("dl").attr("for") == "checkFlowTips"){
                	   queryStep(data);
                   }else if (dd.closest("dl").attr("for") == "checkStep") {
                	   queryExecutor(data);
                   }else {
                	   $("#nextUserID").attr("value",data);
                   }
               }
           });


           var connectList=$("#passengerList");
           //删除联系人
           connectList.on("click",".del",function(){
        	   if($("#buyOthers").text()==0){
             		alertMsg("无权限！");
             		return false;
             	}
               var selReturn=confirm("确定删除'"+$(this).closest(".passengerBox").find(".name").html()+"'联系人吗？");
               if(selReturn){
                   //删除联系人
            	   $(".choose[data='"+$(this).attr("data")+"']").removeClass("chooseOn")
            	   $(this).closest(".passengerBox").remove();
            	   person_no--;
            	   $("#sumPricePlus").html("￥"+$("#sumPrice").text()*person_no);
               }
           });


           //提交订单
           $(".submitBtn").click(function(){
               var phonePattern=/^1[3|4|5|8][0-9]\d{8}$/;
               var phone=$("#passengerPhone");
               var name=$("#passengerName");
               var checkFlow=$(".checkFlow span label");
               var note=$("#note");
               if($("#passengerList .passengerBox").length<1){
                   location.href=$(".flyPassenger .title").attr("href");
                   alertMsg("请选择乘机人！");
                   return ;
               }
                
                var Expression = /[^\u4E00-\u9FA5]/;
        		var objExp = new RegExp(Expression);

        		if(name.val()!=""){
        			   if (objExp.test(name.val()) == true) {
        				   name.focus();
        				   alertMsg("联系人格式有误，请输入汉字！");
        				   return ;
         		   } 
        		   }else{
        			   name.focus();
        			   alertMsg("联系人格式有误，请输入汉字！");
        			   return ;
        		   }
               if(phone.val()==""||!phonePattern.test(phone.val())){
                   alertMsg("请填写正确手机号！");
                   phone.focus();
                   return ;
               }
               if(note.val().length>30){
            	   alertMsg("出差说明过长！");
            	   note.focus();
            	   return ;
               }
               if(checkFlow.eq(0).html()=="点击选择"||checkFlow.eq(1).html()=="点击选择"){
                   alertMsg("请选择审核！");
                   $(".checkFlow").click();
                   return ;
               }
               var html="<div class='loading_bg'></div>";
    			$("body").append(html);
               $("#order").submit();

           });
       });

        function alertMsg(msg){
            var html=' <div class="alertMsg"> <span>'+msg+'</span> </div>';
            $(".alertMsg").remove();
            $("body").append(html);
            var that=$(".alertMsg");
            setTimeout(function(){
                that.remove();
            },2500);
        }
       
        //查询环节
        function queryStep(id){
        	$("#workflowID").attr("value",id);
        	$.ajax({ 
    			url : "../flight/queryWorkflowStep?workflowID="+id, 
    			type : "post", 
    			cache : false, 
    			dataType : "json", //返回json数据 
    			error: function(){ 
    			alert('error'); 
    			}, 
    			success:onchangecallback
    		});
        }
        
        function onchangecallback(data){
        	var str = "<dt>流程环节<label>请选择</label></dt>";
        	for(var i=0;i<data.length;i++){
    			str += "<dd data = "+data[i].stepNO+">"+data[i].stepName+"</dd>";
        	}
        	$("#steps").html(str);
        }
        
        //查询执行人
        function queryExecutor(id){
        	var workflow =$("#workflowID").val();
        	$("#nextStepNO").attr("value",id);
        	$.ajax({ 
    			url : "../flight/queryExecutor?nextStepNO="+id+"&workflowID="+workflow, 
    			type : "post", 
    			cache : false, 
    			dataType : "json", //返回json数据 
    			error: function(){ 
    			alert('error'); 
    			}, 
    			success:onchangeExecutor
    		});
        }
        
        function onchangeExecutor(data){
        	var str = "<dt>审批人员<label>请选择</label></dt>";
        	for(var i=0;i<data.length;i++){
    			str += "<dd data = "+data[i].userID+">"+data[i].name+"</dd>";
        	}
        	$("#checkExecutor").html(str);
        }
        
        function stringReplace_com(data,template) {
    		var arr=data;
    		var str="";
    		for(var i=0;i<arr.length;i++){
    			
    			str+=template.replace(/\{\w+\}/g, function(word) {
    				word=word.replace("{","");
    				word=word.replace("}","");
    				return arr[i][word];
    			});
    		}
    		return str;
    	}
});
