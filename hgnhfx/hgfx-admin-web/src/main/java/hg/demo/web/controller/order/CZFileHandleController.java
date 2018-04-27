package hg.demo.web.controller.order;

import hg.demo.web.task.TaskProperty;
import hg.framework.common.model.LimitQuery;
import hg.framework.common.model.Pagination;
import hg.fx.domain.CZFile;
import hg.fx.spi.CzFileSPI;
import hg.fx.spi.qo.CZFileSQO;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 南航反馈文件查询和下载
 * @author zqq
 * @date 2016-7-7上午9:50:13
 * @since
 */
@Controller
@RequestMapping("/czfile")
public class CZFileHandleController {

	@Autowired
	public CzFileSPI czFileService;
	
	public static String CZPATH = TaskProperty.getProperties().getProperty("czPath");
	@RequestMapping("/getCzFile")
	public String getCzFile(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute CZFileSQO sqo,
			@RequestParam(value = "pageNum", defaultValue = "1") String currpage,
			@RequestParam(value = "numPerPage", defaultValue = "20") String pagesize) throws Exception {
		Pagination<CZFile> pagination = new Pagination<CZFile>();
		sqo.setLimit(new LimitQuery());
		sqo.getLimit().setPageNo(Integer.parseInt(currpage));
		sqo.getLimit().setPageSize(Integer.parseInt(pagesize));
		pagination= czFileService.getCzFile(sqo);;
		model.addAttribute("pagination", pagination);
		model.addAttribute("dto", sqo);
		return "/order/czFileMemo.html";

	}
	
	@RequestMapping("/download")
	public String dnwnloadCzFile(HttpServletRequest request,
			HttpServletResponse response, Model model
			) throws Exception {
		String fileName=request.getParameter("fileName");
		String downLoadPath =CZPATH + fileName;
		File file = new File(downLoadPath);
		if(file.exists()){
			download(request, response, fileName);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/checkFile")
	public String checkFile(HttpServletRequest request,
			HttpServletResponse response, Model model
			) throws Exception {
		String fileName=request.getParameter("fileName");
		String downLoadPath = CZPATH + fileName;
		File file = new File(downLoadPath);
		if(file.exists()){
		return "1";
		}
		return null;
	}
	
	public void download(HttpServletRequest request,
			HttpServletResponse response, String fileName)
			throws UnsupportedEncodingException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		java.io.BufferedInputStream bis = null;
		java.io.BufferedOutputStream bos = null;
		String downLoadPath = CZPATH + fileName;
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
}
