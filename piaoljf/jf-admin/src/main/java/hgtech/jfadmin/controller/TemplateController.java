/**
 * @文件名称：TemplateController.java
 * @类路径：hgtech.jfaddmin.controller
 * @描述：规则模版管理
 * @作者：xinglj
 * @时间：2014年10月13日下午1:25:08
 */
package hgtech.jfadmin.controller;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthPerm;
import hgtech.jf.JfProperty;
import hgtech.jf.entity.StringUK;
import hgtech.jfadmin.dto.TemplateDto;
import hgtech.jfadmin.service.BaseService;
import hgtech.jfadmin.service.JfCalService;
import hgtech.jfadmin.service.RuleService;
import hgtech.jfadmin.service.TemplateService;
import hgtech.jfadmin.util.JarResolve;
import hgtech.jfcal.model.RuleTemplate;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import ucenter.admin.controller.BaseController;
import ucenter.admin.controller.auth.PermController.PermControllerDto;

/**
 * @类功能说明：模版管理
 * @类修改者：
 * @修改日期：2014年10月13日下午1:25:08
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月13日下午1:25:08
 * 
 */
@Controller
@RequestMapping(value = "/template")
public class TemplateController extends BaseController {

	public static final String navTabId = "templateList";
	public static final String rel = "jbsxBoxRuleTemplate";

	@Autowired
	TemplateService templateService;
	@Autowired
	JfCalService calService;
	@Autowired
	RuleService ruleService;
	
	private static final String PCLASS = "\\s*public\\s+class\\s+(\\w+).*";

	/**
	 * 列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, Model model,
			@ModelAttribute TemplateDto dto) {
		Pagination paging = dto.getPagination();

		paging = this.templateService.findPagination(paging);

		model.addAttribute("pagination", paging);
		model.addAttribute("dto", dto);

		return "/template/templateList.html";
	}

	/**
	 * delete
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete")
	public String delete(HttpServletRequest request, Model model,
			@ModelAttribute TemplateDto dto) {
		try {
		    	
			String code = request.getParameter("code");
			if(ruleService.queryByTemplate(code).size()>0){
			    throw new RuntimeException("模版已被使用！");
			}
			templateService.delete(code);
			return DwzJsonResultUtil.createJsonString("200", "删除成功!", null,
					navTabId, null, null, rel);
		} catch (Throwable e) {
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString("300",
					"删除失败!" + e.getMessage(), null, navTabId, null, null, rel);
		}
	}

	/**
	 * delete
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get")
	public String get(HttpServletRequest request, Model model,
			@ModelAttribute TemplateDto dto) {
		return JSONObject.toJSONString(templateService.get(request
				.getParameter("code")));
	}

	/**
	 * 跳转到添加 的页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/to_add")
	public String to_add(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute TemplateDto dto) {

		return "/template/templateAdd.html";
	}

	/**
	 * @throws IOException
	 * @throws IllegalStateException
	 * 
	 * @方法功能说明：添加模版
	 * @修改者名字：xinglj
	 * @修改时间：2014年10月15日下午3:10:32
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@SuppressWarnings("resource")
	@ResponseBody
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, HttpServletResponse response,
			Model model, @ModelAttribute RuleTemplate dto)
			throws IllegalStateException, IOException {

		String srcName = request.getParameter("filename_templateSrc");
		String clazz = (String) request.getSession().getAttribute("template");
		String src = (String) request.getSession().getAttribute("templateSrc");
		String jarFiles=(String) request.getSession().getAttribute("jarFiles");

		InputStream in = new FileInputStream(new File(src));

		String clazzName = request.getParameter("filename_template");
		if (!srcName.equals(clazzName.replace(".class", ".java"))) {
			String msg = "模版和模版源码不一致！";
			return DwzJsonResultUtil.createSimpleJsonString(
					DwzJsonResultUtil.STATUS_CODE_500, msg);
		}
		dto.code = getClassName(in);
		String simleClass = dto.code.substring(dto.code.lastIndexOf('.') + 1);
		/*
		 * MultipartFile clazz = (MultipartFile)
		 * request.getSession().getAttribute("template"); MultipartFile src =
		 * (MultipartFile) request.getSession().getAttribute("templateSrc");
		 * 
		 * if(!clazz.getOriginalFilename().endsWith(simleClass+".class")){
		 * String msg="模版和模版源码不一致！"; return
		 * DwzJsonResultUtil.createSimpleJsonString
		 * (DwzJsonResultUtil.STATUS_CODE_500,msg); }
		 * 
		 * if(templateService.get(new StringUK(dto.code))!=null) { String
		 * msg="模版代码重复："+dto.code; return
		 * DwzJsonResultUtil.createSimpleJsonString
		 * (DwzJsonResultUtil.STATUS_CODE_500,msg); //throw new
		 * RuntimeException("模版代码重复："+dto.code); // return
		 * "<script>alert('"+msg+
		 * "');window.opener=null;window.open('','_self');window.close();</script>"
		 * ; }
		 */

		if (templateService.get(new StringUK(dto.code)) != null) {
			String msg = "模版代码重复：" + dto.code;
			return DwzJsonResultUtil.createSimpleJsonString(
					DwzJsonResultUtil.STATUS_CODE_500, msg);
		}
		in.close();

		templateService.saveTempldate(dto, clazz, src);
		request.getSession().removeAttribute("template");
		request.getSession().removeAttribute("templateSrc");

		// refresh cal model
		try {
			calService.refreshCalModel();
		} catch (Exception e) {
			e.printStackTrace();
		}

		File file=new File(jarFiles);
		fileDelete(file);
		
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "保存成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;

		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, navTabId, null, null, rel);
		// return
		// "<script>alert('模版上传成功');window.opener=null;window.open('','_self');window.close();</script>";
	}

	/**
	 * @throws IOException
	 * @方法功能说明：扫描出类名
	 * @修改者名字：xinglj
	 * @修改时间：2014年10月28日下午3:35:52
	 * @修改内容：
	 * @参数：@param inputStream
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public static String getClassName(InputStream inputStream)
			throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream));
		String s;
		String pak = "";
		while ((s = reader.readLine()) != null) {
			Matcher mt = Pattern.compile(PCLASS).matcher(s);
			if (s.startsWith("package")) {
				pak = s.substring("package".length()).trim();
				pak = pak.substring(0, pak.length() - 1);
			}
			if (mt.matches())
				return pak + "." + mt.group(1);
		}
		throw new RuntimeException("没找到类名！");
	}

	/**
	 * 
	 * @方法功能说明：上传
	 * @修改者名字：xinglj
	 * @修改时间：2014年9月1日上午11:22:13
	 * @修改内容：
	 * @参数：@param file
	 * @参数：@param maxHeight 最大高度(px)
	 * @参数：@param maxWidth 最大宽度(px)
	 * @参数：@param maxSize 最大大小(kb)
	 * @参数：@param metaMap
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/upload")
	public String upload(/* @RequestParam("file") CommonsMultipartFile file, */
	MultipartHttpServletRequest request, HttpServletResponse response,
			String maxHeight, String maxWidth, String maxSize) {

		JSONObject result = new JSONObject();
		result.put("status", "success");
		result.put("msg", "");
		InputStream in = null;
		List<String> list = null;
		for (MultipartFile file : request.getFileMap().values()) {
			try {
				in = file.getInputStream();
				String name = System.currentTimeMillis() + "";
				String rootPath = System.getProperty("catalina.home");
				// System.out.println(rootPath);
				File dir = new File(rootPath + File.separator + "jarFiles");
				String jarFiles=rootPath + File.separator + "jarFiles";
				System.out.println(jarFiles);
	            request.getSession().setAttribute("jarFiles", jarFiles);
				if (!dir.exists()) {
					dir.mkdir();
				}
				byte[] b = file.getBytes();
				File serverFile = new File(dir.getAbsoluteFile()
						+ File.separator + name + ".jar");
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(b);
				stream.close();
				JarResolve ja = new JarResolve();
				list = ja.jarParse(dir.getAbsoluteFile() + File.separator
						+ name + ".jar", name);
			} catch (Throwable e) {
				result.put("status", "fail");
				result.put("msg", e.getMessage());
				e.printStackTrace();
				return result.toJSONString();
			}
		}
		for (String s : list) {
			if (s.endsWith(".java")) {
				request.getSession().setAttribute("templateSrc", s);
				result.put("templateSrc", s.substring(s.lastIndexOf("/") + 1));
			} else if (s.endsWith(".class")) {
				request.getSession().setAttribute("template", s);
				result.put("template", s.substring(s.lastIndexOf("/") + 1));
			} else {
				request.getSession().setAttribute("reademe", s);
				BufferedReader reader = null;
				try {
					reader = new BufferedReader(new InputStreamReader(
							new FileInputStream(s), "UTF-8"));
					String readMe = reader.readLine();
					result.put("name", readMe);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						reader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}

		return result.toJSONString();
	}

	@RequestMapping("download")
	public ModelAndView download(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView m = new ModelAndView();
		String code = request.getParameter("code");
		String type = request.getParameter("type");
		RuleTemplate template = new RuleTemplate();
		template.setCode(code);
		templateService.setClazzandSrcFilename(template);
		String downLoadPath = type.equalsIgnoreCase("java") ? template
				.getSrcFile() : template.getClazzFile();
		String fileName = type.equalsIgnoreCase("java") ? code + ".java" : code
				+ ".class";

		download(request, response, downLoadPath, fileName);
		return null;
	}

	/**
	 * @方法功能说明：
	 * @修改者名字：xinglj
	 * @修改时间：2014年10月27日下午6:06:52
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param downLoadPath
	 * @参数：@param fileName
	 * @参数：@throws UnsupportedEncodingException
	 * @参数：@throws IOException
	 * @return:void
	 * @throws
	 */
	public static void download(HttpServletRequest request,
			HttpServletResponse response, String downLoadPath, String fileName)
			throws UnsupportedEncodingException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		java.io.BufferedInputStream bis = null;
		java.io.BufferedOutputStream bos = null;

		try {
			File file = new File(downLoadPath);
			long fileLength = file.length();
			response.setContentType("application/octet-stream;");
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("utf-8"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new BufferedInputStream(new FileInputStream(downLoadPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getCause());
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
			response.flushBuffer();
		}
	}
	/**
	 * @方法功能说明：清除上传的临时jar包文件
	 * @修改者名字：<a href=pengel@hgtech365.com>pengel</a>
	 * @修改时间：2014年12月19日下午3:45:47
	 * @version：
	 * @修改内容：
	 * @参数：@param file
	 * @return:void
	 * @throws
	 */
	private void fileDelete(File file){
		File[] files=file.listFiles();
		for(int i=0;i<files.length;i++){
			if(!files[i].isDirectory()){
				files[i].delete();
			}else{
				fileDelete(files[i]);
				files[i].delete();
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		System.out
				.println(getClassName(new FileInputStream(
						"D:\\gitwork\\piaoljf\\jf-admin\\src\\main\\java\\hgtech\\jfadmin\\controller\\TemplateController.java")));

		String s = "public class TemplateController extends BaseController{";
		Matcher mt = Pattern.compile(PCLASS).matcher(s);
		if (mt.matches())
			System.out.println(mt.group(1));
		;

		System.out.println("a.b.c.Class1".substring("a.b.c.Class1"
				.lastIndexOf('.') + 1));
	}
}
