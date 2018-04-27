<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.baidu.ueditor.over.ActionEnter"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%

    request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");
	
	String rootPath = application.getRealPath( "/" );
			
			/* .replaceAll("\\\\", "/");
	String replacePath = "_toBeReplace"; */
	
	/* String uri = request.getScheme() + "://" + request.getServerName() 
			+ ":" + request.getServerPort() + request.getContextPath() + "/"; */
	
	String result = new ActionEnter( request, rootPath ).exec();
	
	/* result = result.replaceAll(rootPath, uri);		// 替换图片管理路径
	result = result.replaceAll(replacePath, uri);	// 替换图片上传路径 */
	
	out.write(result);
%>