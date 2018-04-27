<%@ page language="java" contentType="text/xml; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<xml>
	<ToUserName><![CDATA[${map.toUser}]]></ToUserName>
	<FromUserName><![CDATA[${map.fromUser}]]></FromUserName>
	<CreateTime>${map.createTime}</CreateTime>
	<MsgType><![CDATA[text]]></MsgType>
	<c:choose>
		<c:when test="${!map.hasRegOrBind}">
			<Content><![CDATA[亲爱哒/害羞,欢迎关注票量旅游官方微信。【<a href="${system}/welcome">点击有惊喜</a>】! 我们将随时随地为您提供贴心的旅游产品服务，快来加入吧【<a href="${system}/user/bind">绑定账号</a>】！温馨提示：如您还没有票量旅游号，请点击【<a href="${system}/user/reg">注册账号</a>】]]></Content>
		</c:when>
		<c:otherwise>
			<Content><![CDATA[亲/害羞！欢迎关注票量旅游官方微信。我们将为您提供全面的旅游产品服务。特惠酒店、特价机票、打折门票……您的旅游问题，票量旅游帮您搞定，更多精彩敬请关注。/偷笑]]></Content>
		</c:otherwise>
	</c:choose>
	<FuncFlag>1</FuncFlag>
</xml>
