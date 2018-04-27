<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<c:set var="nowDate" value="<%=new Date()%>"></c:set>
<!-- 清除缓存 -->
<% 
// 将过期日期设置为一个过去时间 
response.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT"); 
// 设置 HTTP/1.1 no-cache 头 
response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate"); 
// 设置 IE 扩展 HTTP/1.1 no-cache headers， 用户自己添加 
response.addHeader("Cache-Control", "post-check=0, pre-check=0"); 
// 设置标准 HTTP/1.0 no-cache header. 
response.setHeader("Pragma", "no-cache"); 
%> 

<!DOCTYPE html>
<html>
<head>
<title>线路抢购</title>
<!-- meta信息，可维护 -->
<meta charset="UTF-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta content="telephone=no" name="format-detection" />
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<link href="${ctx}/css/common.css" rel="stylesheet" />
<link href="${ctx}/css/ms_style.css?q=32" rel="stylesheet" />
<script src="${ctx}/js/jquery-2.1.4.min.js" language="javascript"></script>
	
</head>	
<body>	

<img src="${ctx}/images/ms_img2.jpg" class="banner" alt="">
<input type="hidden" name="serviceTime" id="serviceTime" value="${serviceTime}"/>

<div class="g_title pl">
    <img src="${ctx}/images/ms_icon1.png" class="g_ms_icon" alt="">
    人气秒杀
</div>
<!-- 秒杀活动列表开始 -->
<div class="msList">
    
    <c:forEach items="${secKillList}" var="lineSalesPlan">
    	
		<c:choose>
			<c:when test="${lineSalesPlan.lineSalesPlanStatus.status==3}"> 
				<div class="msBox">
			</c:when>
			<c:when test="${lineSalesPlan.lineSalesPlanStatus.status==2}"> 
				<div class="msBox msBegin">
			</c:when>
			<c:when test="${lineSalesPlan.lineSalesPlanStatus.status==4}"> 
				<div class="msBox msOver">
			</c:when>
			<c:otherwise>
			<div class="msBox">
			</c:otherwise>
		</c:choose>
	        <div class="imgBox pl">
	           <c:if test="${lineSalesPlan.line.baseInfo.type==2}">
	           <div class="lineType">自助游</div>
	           </c:if> 
	           <c:if test="${lineSalesPlan.line.baseInfo.type==1}">
	           <div class="lineType">跟团游</div>
	           </c:if>
	           
	           <div class="detail">
					<c:choose>
						<c:when test="${fn:length(lineSalesPlan.baseInfo.planName)<=14}"> 
							<div class="t">${lineSalesPlan.baseInfo.planName}</div>
						</c:when>
						<c:otherwise>
							<div class="t">${fn:substring(lineSalesPlan.baseInfo.planName,0,14)}...</div>
						</c:otherwise>
					</c:choose>
						
	                <div class="price">${lineSalesPlan.lineSalesPlanSalesInfo.planPrice}
	                    <div class="price_off">
	                        <span class="oldPrice">￥${lineSalesPlan.lineSalesPlanSalesInfo.originalPrice}</span>
	                    </div>
	                </div>
	                
	                <c:choose>
						<c:when test="${fn:length(lineSalesPlan.line.baseInfo.name)<=14}"> 
							<div class="s">${lineSalesPlan.line.baseInfo.name}</div>
						</c:when>
						<c:otherwise>
							<div class="s">${fn:substring(lineSalesPlan.line.baseInfo.name,0,14)}...</div>
						</c:otherwise>
					</c:choose>
	                
	            </div>
	            <c:if test="${!empty lineSalesPlan.baseInfo.imageUri}">
					<img src="${image_host}${lineSalesPlan.baseInfo.imageUri}">
				</c:if>
	            <c:if test="${empty lineSalesPlan.baseInfo.imageUri}">
					<img src="${ctx}/images/noPicture_default.jpg">
				</c:if>
	        </div>
	        <div class="btnBox">
	            <div class="timework" data-time-begin="${lineSalesPlan.lineSalesPlanSalesInfo.beginDate}" data-time-over="${lineSalesPlan.lineSalesPlanSalesInfo.endDate}" >
	            	<e>距抢购结束</e>
	                <label class="h"></label>:
	                <label class="m"></label>:
	                <label class="s"></label>
	            </div>
	            <a href="${ctx}/lineSalesPlan/buyingActivityDetail?id=${lineSalesPlan.id}" class="product">立即抢购</a>
	        </div>
	    </div>	
   </c:forEach>  
   
    <div class="msFooter box">
        <img src="${ctx}/images/ms_foot.png" alt="">
    </div>
</div>
<!-- 秒杀活动列表结束 -->


<div class="g_title pl">
    <img src="${ctx}/images/ms_icon2.png" class="g_ms_icon" alt="">
    拼团惠
</div>
<div class="tgTips">满足拼团人数即团购成功，人数不足自动退款</div>

<!-- 拼团活动列表开始 -->
<div class="tgList msList">	
    <c:forEach items="${groupList}" var="lineSalesPlan">
    <c:choose>
	    <c:when test="${lineSalesPlan.lineSalesPlanStatus.status==3}"> 
			<div class="tgBox">
		</c:when>
		<c:when test="${lineSalesPlan.lineSalesPlanStatus.status==2}"> 
			<div class="tgBox tgBegin">
		</c:when>
		<c:when test="${lineSalesPlan.lineSalesPlanStatus.status==5||lineSalesPlan.lineSalesPlanStatus.status==4||lineSalesPlan.lineSalesPlanStatus.status==6}"> 
			<div class="tgBox tgOver">
		</c:when>
		<c:otherwise>
		<div class="tgBox">
		</c:otherwise>
	</c:choose>
       <div class="imgBox pl">
           <c:if test="${lineSalesPlan.line.baseInfo.type==2}">
           		<div class="lineType">自助游</div>
           </c:if> 
           <c:if test="${lineSalesPlan.line.baseInfo.type==1}">
           		<div class="lineType">跟团游</div>
           </c:if>
           <c:if test="${!empty lineSalesPlan.baseInfo.imageUri}">
				<img src="${image_host}${lineSalesPlan.baseInfo.imageUri}">
			</c:if>
			<c:if test="${empty lineSalesPlan.baseInfo.imageUri}">
				<img src="${ctx}/images/noPicture_default.jpg">
			</c:if>
		</div>
        <div class="detailBox">
        			<c:choose>
						<c:when test="${fn:length(lineSalesPlan.baseInfo.planName)<=14}"> 
							<div class="t">${lineSalesPlan.baseInfo.planName}</div>
						</c:when>
						<c:otherwise>
							<div class="t">${fn:substring(lineSalesPlan.baseInfo.planName,0,14)}...</div>
						</c:otherwise>
					</c:choose>
           
           	 <c:choose>
				<c:when test="${fn:length(lineSalesPlan.line.baseInfo.name)<=14}"> 
					<div class="de">${lineSalesPlan.line.baseInfo.name}</div>
				</c:when>
				<c:otherwise>
					<div class="de">${fn:substring(lineSalesPlan.line.baseInfo.name,0,14)}...</div>
				</c:otherwise>
			</c:choose>
           	<div class="price pl">${lineSalesPlan.lineSalesPlanSalesInfo.planPrice}
                <div class="price_off">
                    <span class="oldPrice">${lineSalesPlan.lineSalesPlanSalesInfo.originalPrice}</span>
                </div>
                <a href="${ctx}/lineSalesPlan/buyingActivityDetail?id=${lineSalesPlan.id}" class="buyBtn pa">立即<br>参团</a>
          	    </div>
            <div class="num">
            	已售<i class="offerNum">${lineSalesPlan.lineSalesPlanSalesInfo.soldNum}</i>&nbsp;｜&nbsp;<i class="totalNum">${lineSalesPlan.lineSalesPlanSalesInfo.provideNum}</i>人团</div>
            <div class="timework"  data-time-begin="${lineSalesPlan.lineSalesPlanSalesInfo.beginDate}" data-time-over="${lineSalesPlan.lineSalesPlanSalesInfo.endDate}">
                <e>距抢购结束</e>
                <label class="h"></label>:
                <label class="m"></label>:
                <label class="s"></label>
            </div>
       	</div>
	  </div>
  </c:forEach>
</div>
<!-- 拼团活动列表结束 -->
<script > 
var nowTime=parseInt($("#serviceTime").val());
var localTime=new Date().getTime();
var localCross=nowTime-localTime;
/* nowTime=new Date(nowTime);
nowTime=nowTime.getFullYear()+"-"+ten(nowTime.getMonth()+1)+"-"+ten(nowTime.getDate())+" "+ten(nowTime.getHours())+":"+ten(nowTime.getMinutes())+":"+ten(nowTime.getSeconds());
 */
 $(function(){
    		
        $(".timework").each(function(){
        	var that=$(this);
            var beginTime=$(this).attr("data-time-begin");
            beginTime=beginTime.split(".")[0];
            beginTime=beginTime.replace(/-/g,"/");
            beginTime=new Date(beginTime).getTime();
            var overTime=$(this).attr("data-time-over");
            overTime=overTime.split(".")[0];
            overTime=overTime.replace(/-/g,"/");
            overTime=new Date(overTime).getTime();
            var cross=0,dt={};
            //活动状态已结束，直接不计时
            if($(this).closest(".tgBox").hasClass("tgOver")||$(this).closest(".msBox").hasClass("msOver")){
            	that.find(".h").html("00");
            	that.find(".m").html("00");
            	that.find(".s").html("00");
            	return;
            }
            setInterval(function(){
            	nowTime=new Date().getTime()+localCross;
            	cross=parseInt(beginTime-nowTime);
            	if(cross<0){
            		//活动已经开始
            		cross=parseInt(overTime-nowTime);
            		if(cross<0){
            			//活动已经结束
            			cross=0;
            			that.find("e").html("距抢购结束");
                		that.closest(".msBox").removeClass("msOver").removeClass("msBegin").addClass("msOver");
                		that.closest(".tgBox").removeClass("tgOver").removeClass("tgBegin").addClass("tgOver");
            		}else{
            			//活动正在运行
            			that.find("e").html("距抢购结束");
                		that.closest(".msBox").removeClass("msOver").removeClass("msBegin");
                		that.closest(".tgBox").removeClass("tgOver").removeClass("tgBegin");
            		}
            	}else{
            		//活动还未开始
            			that.find("e").html("距抢购开始");
            		that.closest(".msBox").removeClass("msOver").removeClass("msBegin").addClass("msBegin");
            		that.closest(".tgBox").removeClass("tgOver").removeClass("tgBegin").addClass("tgBegin");
            	}
           	 	dt={
                    	h:Math.floor(cross/60/60/1000),
                    	m:Math.floor(cross/60/1000%60),
                    	s:Math.floor(cross/1000%60)
                    };
            	that.find(".h").html(ten(dt.h));
            	that.find(".m").html(ten(dt.m));
            	that.find(".s").html(ten(dt.s));
            },500);
        });
        
        /* //团购开始前和结束后不让点击
        $(".tgBegin,.tgOver").each(function(){
        	
        })； */
        //团购开始前和结束后不让点击
        $(".tgBox").on("click","a",function(e){
        	if($(this).closest(".tgBox").hasClass("tgBegin")||$(this).closest(".tgBox").hasClass("tgOver")){
        		e.preventDefault();
        	}
        })
         $(".msBox").on("click","a",function(e){
        	if($(this).closest(".msBox").hasClass("msBegin")||$(this).closest(".msBox").hasClass("msOver")){
        		e.preventDefault();
        	}
        })
        
        $(".tgBox .detailBox").click(function(e){
        	if(!($(this).closest(".tgBox").hasClass("tgBegin")||$(this).closest(".tgBox").hasClass("tgOver"))){
        		location.href=$(this).find(".price a").attr("href");
        	}
        	
        })
    });
    function ten(num){
    	num=num<10?("0"+num):num;
    	return num;
    }
</script>	
	
</body>
</html>