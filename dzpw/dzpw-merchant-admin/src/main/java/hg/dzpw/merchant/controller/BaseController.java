package hg.dzpw.merchant.controller;

import hg.common.component.BaseQo;
import hg.common.component.DateParamEditor;
import hg.common.model.qo.DwzPaginQo;
import hg.common.page.Pagination;
import hg.common.util.ClassPathTool;
import hg.common.util.StringUtil;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @author yuxx
 * 
 */
public class BaseController {
	
	/**
	 * 将str打印给响应对象
	 * @param response
	 * @param str
	 */
	protected void print(HttpServletResponse response, String str){
		try {
			response.getWriter().print(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void ouputExcel(HSSFWorkbook wb, HttpServletResponse response) throws IOException {
		InputStream inStream = null;
		try {
			// 将文件存到指定位置
			ClassPathTool.getInstance();
			String fileName = ClassPathTool.getWebRootPath() + "/excel/"
					+ StringUtil.getRandomName() + ".xls";
			FileOutputStream fout = new FileOutputStream(fileName);

			wb.write(fout);
			fout.close();
			// 将文件输入并下载 读到流中
			inStream = new FileInputStream(fileName); // 文件的存放路径
			// 设置输出的格式
			response.reset();
			response.setContentType("application/vnd.ms-excel MSEXCEL");
			response.setHeader("Content-Disposition", "attachment;filename=\""
					+ StringUtil.getRandomName() + ".xls" + "\"");
			// 循环取出流中的数据
			byte[] b = new byte[100];
			int len;
			while ((len = inStream.read(b)) > 0) {
				response.getOutputStream().write(b, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			inStream.close();
		}
	}
	
	/**
	 * 自动将yyyy-MM-dd格式的参数转换成Date型参数
	 * @param binder
	 */
	@InitBinder 
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateParamEditor()); 
	}
	
	/**
	 * 根据DwzPaginQo和Qo创建Pagination
	 * @param dwzPaginQo
	 * @return
	 */
	public Pagination createPagination(DwzPaginQo dwzPaginQo, BaseQo qo) {
		Pagination pagination = new Pagination();
		pagination.setPageNo(dwzPaginQo.getPageNum());
		pagination.setPageSize(dwzPaginQo.getNumPerPage());
		pagination.setCondition(qo);
		return pagination;
	}

	/**
	 * 根据DwzPaginQo创建Pagination
	 * 
	 * @param dwzPaginQo
	 * @return
	 */
	public Pagination createPagination(DwzPaginQo dwzPaginQo) {
		return createPagination(dwzPaginQo, null);
	}
	
	/**
	 * 直接获取请求参数
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public String getParam(HttpServletRequest request, String name) {
		return request.getParameter(name);
	}
}