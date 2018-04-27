<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" />
<title>每日行程</title>
</head>
<style>
    *{padding:0px;margin:0px;list-style:none;font-size:14px;}
     .b1{background-image:url("${ctx}/page/resource/image/line23.png")}
    /*.b2{background-image:url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACIAAAAeCAYAAABJ/8wUAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA4RpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNS1jMDIxIDc5LjE1NTc3MiwgMjAxNC8wMS8xMy0xOTo0NDowMCAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDo1OTM1MzIwMC1iMTE4LWY3NDktOTY4NC01YmQwYWM3MjlhY2UiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6Mjg4RjlCQTVFQ0JCMTFFNDgzNUFCMjYyMjFBNDdFMUQiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6Mjg4RjlCQTRFQ0JCMTFFNDgzNUFCMjYyMjFBNDdFMUQiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTQgKFdpbmRvd3MpIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6ZDNhMTZkZDQtMzJhOC1jNDQ5LWI3ZTktOGFlY2YzNWY3NzE1IiBzdFJlZjpkb2N1bWVudElEPSJhZG9iZTpkb2NpZDpwaG90b3Nob3A6NDZhMjBhYjUtZTg5MS0xMWU0LWFjMGUtYjIxZTc2YTE2N2YwIi8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+eBuDKQAAAuFJREFUeNqsl01IFkEYx3e3Ny0zQgtKyCD7BKFTl+i9FX2A0CkIMokg0CCC6CJEpzoUHSJKkaRDpZUXo+gLrCCCwkoo61ARBWmfRofKyOzdfg88xssyO7O7r3/4Me87Ozv739lnnpnxwzD0JrT+zCsvoi1wBE7AOXjvWXSzaYmXVUFM/VLpF85DLRyGATgGi7wSxQuXQZ3LyBq4Besi9XNhDzyENqjJYGAjdPHzk7wYv0Pok2t+Pp//37Bi52kpNsAlKE/Q9w3YD4/lz+ipHcZG9LuaogOWQc7QZFMQ07mMxnACI2L6EYzAZoOBKSCjeA3qY0yIRuIu3IVVcFFLl2ZDDw8tUO6F4yCRe0XjzSZ5iQeBpcE7WAtnUwa/BPQP6TyBCVErn7QQOBqNQhPsShmXFVCVoN1VTHTapm9U7dDgTa56MdHgyiNG97AQ/pRo4DM0w9biylzKTt7CLJ1RVRlMDMJ2RmIgeiGXobNfsBiewPyE98g60oKBjrQp3qZGeJ7ChOiDzYTJyHRLW/kU13U6z0tpPiTH+E4jNJoGJ3W6fokE0lTNmv2aSbNI1pZKqxEMrKS8XZQr5uiSL1myRbcAPRoXWSUj+NPWIKcPMKVxX1fZcBJyRo1kT+uI0OAC5QHLA/0E2bddAzhOHxn56iTBelDXiLR6qeuRfNZuS7uZrhka6D5CRmMfdCU0UNAVVvYX97WuzZJ1JVC/Jpq++g0bNXBteiObLd2tFes7DMXcMw51afesslV8HdN+SJf2PsO1vzrbTPrtnL4x9c0xwTvsmEW9aiiqGbpOpTZyRwMxqnJH0A1q/JhUncWIvNU2Q329bRtAnI3rOSiqMViQxYin55hnhpxR6Qjmy4ZRKdNgzmRERmV3pO6ovp1nGZX+mEtjWY2I7ul0lQPXcjjk6lDVadjDBKUYmZj/kjdepFh3uotWXRnVWkbqqe0G33EIT6XiQzh9rZAjCXXfktz7T4ABANmNzQIFQIA7AAAAAElFTkSuQmCC");} */
    .box .cont{padding:10px 0px 10px 15px;border-left:1px solid #46a1da;margin-left:20px;}
    .line{border-top:1px solid #d4d4d4;border-left:1px solid #46a1da;}
    .cont h3{color:#4cabe6;}
    .label{line-height:32px;}
    .icon{padding:2px;background-size:100%;background-repeat: no-repeat;background-position:0px 0px;}

    .label2{line-height:22px;}
   .tips,.time{color:#4cabe6;}
    .artiel{border:1px solid #46a1da;padding:5px;margin:10px 20px 10px 0px;position:relative;color:#7b7b7b;line-height:18px;}
    .artiel:before{content:"";width:16px;height:1px;background:#46a1da;position:absolute;left:-16px;}

    .tips{line-height:20px;}
    .day{position:absolute;height:20px;width:20px;background-size:100%;background-repeat: no-repeat;background-position:0px 0px;left:10px;}
</style>
		<body>
			<c:forEach items="${dayRoutes}" var="dayRoute">  
				<div class="box">
				    <div class="cont">
				        <i class="day b1"></i>
				       <h3>第${dayRoute["days"]==null?"&nbsp;":dayRoute["days"]}天</h3>
				        <div class="label">
				        	出发地：
				        	<span class="text">${dayRoute["starting"]==null?"&nbsp;":dayRoute["starting"]}</span>
				        </div>
				        <div class="label">
				        	目的地：
				            <span class="text">${dayRoute["destination"]==null?"&nbsp;":dayRoute["destination"]}</span>
				        </div>
				        <div class="label">
				        	交通工具：
				            <span class="text">
					            <c:set value="${ fn:split(dayRoute.vehicle, ',') }" var="vehicles" />
								<c:forEach items="${vehicles}" var="vehiclecode">
									<c:if test="${vehiclecode==1}">飞机&nbsp;</c:if>
									<c:if test="${vehiclecode==2}">大巴&nbsp;</c:if>
									<c:if test="${vehiclecode==3}">轮船&nbsp;</c:if>
									<c:if test="${vehiclecode==4}">火车&nbsp;</c:if>
								</c:forEach>
							</span>
				        </div>
				        <div class="artiel">
				        	${dayRoute["schedulingDescription"]==null?"&nbsp;":dayRoute["schedulingDescription"]}
				        </div>
				        <div class="tips">早餐：<c:choose><c:when test="${dayRoute.includeBreakfast}">包含</c:when><c:when test="${!dayRoute.includeBreakfast}">敬请自理</c:when><c:otherwise>&nbsp;</c:otherwise></c:choose></div>
				        <div class="tips">午餐：<c:choose><c:when test="${dayRoute.includeLunch}">包含</c:when><c:when test="${!dayRoute.includeLunch}">敬请自理</c:when><c:otherwise>&nbsp;</c:otherwise></c:choose></div>
				        <div class="tips">晚餐：<c:choose><c:when test="${dayRoute.includeDinner}">包含</c:when><c:when test="${!dayRoute.includeDinner}">敬请自理</c:when><c:otherwise>&nbsp;</c:otherwise></c:choose></div> 
					    <div class="tips">
				        	住宿：${dayRoute["hotelInfoJson"]==null?"&nbsp;":dayRoute["hotelInfoJson"]}
				        </div>
				    </div>
				    <div class="line"></div>
				</div>
			</c:forEach>
	</body>
</html>
