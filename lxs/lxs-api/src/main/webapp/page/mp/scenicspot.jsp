<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" />
<title>门票详情</title>

<link href="${ctx}/page/mp/css/global.css" rel="stylesheet" />
<link href="${ctx}/page/mp/css/common.css" rel="stylesheet" />
<link href="${ctx}/page/mp/css/mp_detail.css" rel="stylesheet" />
<script src="${ctx}/page/mp/js/libs/zepto.min.js"></script>
<script src="${ctx}/page/mp/js/libs/frozen.js"></script>
<script src="${ctx}/page/mp/js/script/mp_detail.js"></script>
</head>

<body style="background:#f2f2f2;">
    <!-- 内容 -->
    <div class="cont">
        <!-- Tab -->
        <div class="ui-tab" id="ui-tab" style="margin:0;">
            <ul class="ui-tab-nav ui-border-b" style="position:fixed;top:-1000px;left:0;">
                <li>景区介绍</li>
            </ul>
            <ul class="ui-tab-content" style="width:300%">
                
                <!-- 景区介绍 -->
                <li>
                    <div class="part">
                        <div class="tit kfsj">
                            开放时间
                            <div class="time fr">
                             <c:if test="${!empty scenicSpot.baseInfo.openTime}">
				            ${scenicSpot.baseInfo.openTime}
				            </c:if>
				            </div>
                        </div>
                    </div>
                    <div class="part">
                        <div class="tit jqjs">
                            景区介绍
                            <div class="btn-switch close"></div>
                        </div>
                        <div class="detail ov">
                        	<c:if test="${!empty scenicSpot.baseInfo.intro}">
				            ${scenicSpot.baseInfo.intro}
				            </c:if>
                        </div>
                    </div>
                    <div class="part">
                        <div class="tit jtzn">
                            交通指南
                            <div class="btn-switch close"></div>
                        </div>
                        <div class="detail ov">
                        	<c:if test="${!empty scenicSpot.contactInfo.traffic}">
				            ${scenicSpot.contactInfo.traffic}
				            </c:if>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
        <!-- js -->
        <script>
        (function() {
            var tab = new fz.Scroll('.ui-tab', {
                role: 'tab',
            });
        })();
        </script>
    </div>
</body>