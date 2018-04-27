/**
 * @CZFileHandleController.java Create on 2015年6月8日上午10:54:46
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.jxc.admin.controller.cz;

import java.io.IOException;

import hg.hjf.cz.CzFileService;
import hg.hjf.util.WebProxyUtil;
import hg.jxc.admin.common.TaskProperty;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @类功能说明：南航文件处理（生成和处理反馈文件
 * @类修改者：
 * @修改日期：2015年6月8日上午10:54:46
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=xinglj@hgtech365.com>xinglj</a>
 * @创建时间：2015年6月8日上午10:54:46
 * @version：
 */
@Controller
@RequestMapping("/czfile")
public class CZFileHandleController {
 

	@RequestMapping("/download")
	public String dnwnloadCzFile(HttpServletRequest request,
			HttpServletResponse response, Model model
			) throws Exception {
		
		final String site = getServerSite();
		String contextPath = getServerContextPath();
		WebProxyUtil.proxy(site+contextPath+"/czfile/downloadSumfile", request,response);
		return null;
	}
	@ResponseBody
	@RequestMapping("/checkFile")
	public String checkFile(HttpServletRequest request,
			HttpServletResponse response, Model model
			) throws Exception {
		
		return proxyCzServer(request, response, "/czfile/checkFile");
	} 

	@RequestMapping("/getCzFile")
	public String getCzFile(HttpServletRequest request,
			HttpServletResponse response, Model model
			 ) throws Exception {
		final String serverUri = "/czfile/getCzFile";
		return proxyCzServer(request, response, serverUri);
	}
	
	private String proxyCzServer(HttpServletRequest request,
			HttpServletResponse response, final String serverUri)
			throws IOException, HttpException {
		final String site = getServerSite();
		String contextPath = getServerContextPath();
		String s=WebProxyUtil.getContent(site+contextPath+serverUri, request);
		s = s.replace(contextPath+"/czfile", request.getContextPath()+"/czfile" );
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(s);
		return null;
	}
	private String getServerSite(){
		return TaskProperty.getProperties().getProperty("serverSite").toString();
	}
	private String getServerContextPath(){
		return TaskProperty.getProperties().getProperty("serverContextPath").toString();
	}
}
