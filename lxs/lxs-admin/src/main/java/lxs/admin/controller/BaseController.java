package lxs.admin.controller;

import hg.common.model.qo.DwzPaginQo;
import hg.common.page.Pagination;
import hg.common.util.ClassPathTool;
import hg.common.util.StringUtil;
import hg.log.util.HgLogger;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthUser;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.KeyValue;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：Administrator
 * @创建时间：2014年7月30日上午9:29:12
 * @版本：V1.0
 *
 */
public class BaseController {
	
	public AuthUser getAuthUser() {
		Subject currentUser = SecurityUtils.getSubject();
		return (AuthUser) currentUser.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
	}
	
	/**
	 * 将str打印给响应对象
	 * @param response
	 * @param str
	 */
	protected void print(HttpServletResponse response, String str){
		try {
			response.getWriter().print(str);
		} catch (IOException e) {
			HgLogger.getInstance().error("zhangka", "BaseController->print->exception:" + HgLogger.getStackTrace(e));
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
			HgLogger.getInstance().error("lxs_dev", "BaseController->ouputExcel->exception:" + HgLogger.getStackTrace(e));
		} finally {
			if(inStream != null){
				inStream.close();				
			}
		}
	}
	
	/**
	 * 自动将yyyy-MM-dd格式的参数转换成Date型参数
	 * @param binder
	 */
	@InitBinder 
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false)); 
	}
	
	/**
	 * 根据DwzPaginQo和Qo创建Pagination
	 * 
	 * @param dwzPaginQo
	 * @param qo
	 * @return
	 */
	public Pagination createPagination(DwzPaginQo dwzPaginQo, Object qo) {
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
	
	public final static List<KeyValue> TRADE_TYPE_LIST = new ArrayList<KeyValue>();
	
	/**
	 * 修饰日志信息标题
	 * @param title
	 * @return
	 */
	protected String newHeader(String title){
		return "<<---------------运营端--"+title+"--------------->>\r\n";
	}
	
	protected String newLog(String title, String head, String msg){
		StringBuffer log = new StringBuffer();
		log.append("<<---------------运营端--"+title+"--------------\r\n\n\n");
		log.append("\t\t\t\t\t\t------" + head + "------\r\n\n");
		log.append(msg+"\r\n\n");
		log.append("\n\n\t\t\t\t\t\t---------------运营端--"+title+"-------------->>\r\n");
		return log.toString();
	}

}
