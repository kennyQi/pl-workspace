<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>搜索结果</title>
<!-- meta信息，可维护 -->
<meta charset="UTF-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta content="telephone=no" name="format-detection" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!-- 样式 -->
<link rel="stylesheet" href="${ctx }/css/base.2.0.css" />
<link rel="stylesheet" href="${ctx }/css/company.css"/>

<!-- javascript -->
<script type="text/javascript" src="${ctx }/js/jquery-1.10.2.js"></script>
</head>
<body class="personal">
<!-- 页头  -->
<header class="header">
  <h1>搜索结果</h1>
  <div class="left-head"> <a id="goBack" href="javascript:history.go(-1);" class="tc_back head-btn"> <span class="inset_shadow"> <span class="head-return"></span> </span> </a> </div>
</header>
<section id="search_list">

  <c:if test="${companySearchResult.result == '2' }">	
  <c:forEach items="${companySearchResult.companySearchList }" var="company">
  <section class="zuzhi_style">
    <h2>${company.companyName }</h2>
  </section>
  <!--start list -->
  <section class="plist pzuzhi"> <a href="${ctx }/company/queryMembers?departmentId=${company.departId}" class="i10">
    <div><i></i>${company.departName }</div>
    </a>
  </section>
  </c:forEach>
  </c:if>
  
  <c:if test="${companySearchResult.result == '1' }">	
  <c:forEach items="${companySearchResult.companySearchList }" var="company">
  <section class="zuzhi_style">
    <h2>${company.companyName }</h2>
  </section>
  <!--start list -->
  <section class="plist pzuzhi nbottom seacrhzuzhi"> <a href="javascript:;" class="i10">
    <div><i></i>${company.departName }</div>
    </a>
    <ul class="p_list_info">
      <li><a href="${ctx }/company/queryMemberDetail?id=${company.memberId}" >
        <div><i></i>${company.memberName }<em><u>${company.job }</u></em></div>
        </a></li>
    </ul>
  </section>
  </c:forEach>
  </c:if>
  
  <c:if test="${companySearchResult.result == '3' }">	
  	<div class="search_info_none"><i></i>无搜索结果</div>
  </c:if>
  <!--end list -->
</section>
</body>
</html>