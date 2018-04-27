// 使用页面：company_list.html
// 日期：2015、4/20
// 作者：lw
var ajaxDate=[{'firstName':'韩卫娜','position':'行政人事部','mobile':'15801409856','id':'330152198608091125'},
    {'firstName':'韩卫娜','position':'行政人事部','mobile':'15801409856','id':'330152198608091125'},
    {'firstName':'韩卫娜','position':'行政人事部','mobile':'15801409856','id':'330152198608091125'},
    {'firstName':'韩卫娜','position':'景区商务部','mobile':'15801409856','id':'330152198608091125'},
    {'firstName':'韩卫娜','position':'景区商务部','mobile':'15801409856','id':'330152198608091125'},
    {'firstName':'韩卫娜','position':'平台运营部','mobile':'15801409856','id':'330152198608091125'},
    {'firstName':'韩卫娜','position':'平台运营部','mobile':'15801409856','id':'330152198608091125'},
    {'firstName':'韩卫娜','position':'平台运营部','mobile':'15801409856','id':'330152198608091125'},
    {'firstName':'韩卫娜','position':'平台运营部','mobile':'15801409856','id':'330152198608091125'},
    {'firstName':'韩卫娜','position':'视觉设计部','mobile':'15801409856','id':'330152198608091125'},
    {'firstName':'韩卫娜','position':'视觉设计部','mobile':'15801409856','id':'330152198608091125'},
    {'firstName':'韩卫娜','position':'视觉设计部','mobile':'15801409856','id':'330152198608091125'},
    {'firstName':'韩卫娜','position':'视觉设计部','mobile':'15801409856','id':'330152198608091125'},
    {'firstName':'韩卫娜','position':'视觉设计部','mobile':'15801409856','id':'330152198608091125'},
    {'firstName':'韩卫娜','position':'视觉设计部','mobile':'15801409856','id':'330152198608091125'},
    {'firstName':'韩卫娜','position':'视觉设计部','mobile':'15801409856','id':'330152198608091125'},
    {'firstName':'韩卫娜','position':'产品部','mobile':'15801409856','id':'330152198608091125'},
    {'firstName':'韩卫娜','position':'产品部','mobile':'15801409856','id':'330152198608091125'},
    {'firstName':'韩卫娜','position':'产品部','mobile':'15801409856','id':'330152198608091125'},
    {'firstName':'韩卫娜','position':'开发部','mobile':'15801409856','id':'330152198608091125'},
    {'firstName':'韩卫娜','position':'开发部','mobile':'15801409856','id':'330152198608091125'},
    {'firstName':'韩卫娜','position':'开发部','mobile':'15801409856','id':'330152198608091125'},
    {'firstName':'韩卫娜','position':'开发部','mobile':'15801409856','id':'330152198608091125'},
    {'firstName':'韩卫娜','position':'开发部','mobile':'15801409856','id':'330152198608091125'},
    {'firstName':'韩卫娜','position':'开发部','mobile':'15801409856','id':'330152198608091125'},
    {'firstName':'韩卫娜','position':'开发部','mobile':'15801409856','id':'330152198608091125'},
    {'firstName':'韩卫娜','position':'开发部','mobile':'15801409856','id':'330152198608091125'}
];

$(document).ready(function() {	

var strHtml="";
    for( var i=0;i<ajaxDate.length;i++){
        strHtml+='<li><span class="all"><i></i></span><span class="name">'+ajaxDate[i].firstName+'</span><span class="position">'+ajaxDate[i].position+'</span><span class="mobile">'+ajaxDate[i].mobile+'</span><span class="id">'+ajaxDate[i].id+'</span></li>';
    }
    $(".rightTable").append(strHtml);


   //组织选择  
/*$(".rinav li").click(function(){
	$(".rinav li").removeClass("selected");
	$(this).addClass("selected");
	}); */
   //部门选择
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
});

