package plfx.api.controller.base;

import hg.common.component.BaseQo;
import hg.common.model.qo.DwzPaginQo;
import hg.common.page.Pagination;
import hg.log.util.HgLogger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.KeyValue;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 
 * @类功能说明：基础类CONTROLLER
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年8月1日下午1:18:12
 * @版本：V1.0
 *
 */
public class BaseController {

	/**
	 * 
	 * @方法功能说明：将str打印给响应对象
	 * @修改者名字：tandeng
	 * @修改时间：2014年8月1日下午1:24:36
	 * @修改内容：
	 * @参数：@param response
	 * @参数：@param str
	 * @return:void
	 * @throws
	 */
	protected void print(HttpServletResponse response, String str){
		try {
			response.getWriter().print(str);
		} catch (IOException e) {
			HgLogger.getInstance().error("tandeng", "BaseController->print->exception:" + HgLogger.getStackTrace(e));
		}
	}
	
	/*public void ouputExcel(HSSFWorkbook wb, HttpServletResponse response) throws IOException {
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
		
		} finally {
			inStream.close();
		}
	}*/
	
	/**
	 * 
	 * @方法功能说明：自动将yyyy-MM-dd格式的参数转换成Date型参数
	 * @修改者名字：tandeng
	 * @修改时间：2014年8月1日下午1:25:12
	 * @修改内容：
	 * @参数：@param binder
	 * @return:void
	 * @throws
	 */
	@InitBinder 
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false)); 
	}
	
	/**
	 * 
	 * @方法功能说明：根据DwzPaginQo和Qo创建Pagination
	 * @修改者名字：tandeng
	 * @修改时间：2014年8月1日下午1:25:28
	 * @修改内容：
	 * @参数：@param dwzPaginQo
	 * @参数：@param qo
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	public Pagination createPagination(DwzPaginQo dwzPaginQo, BaseQo qo) {
		Pagination pagination = new Pagination();
		pagination.setPageNo(dwzPaginQo.getPageNum());
		pagination.setPageSize(dwzPaginQo.getNumPerPage());
		pagination.setCondition(qo);
		return pagination;
	}

	/**
	 * 
	 * @方法功能说明：根据DwzPaginQo创建Pagination
	 * @修改者名字：tandeng
	 * @修改时间：2014年8月1日下午1:25:46
	 * @修改内容：
	 * @参数：@param dwzPaginQo
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	public Pagination createPagination(DwzPaginQo dwzPaginQo) {
		return createPagination(dwzPaginQo, null);
	}

	/**
	 * 
	 * @方法功能说明：直接获取请求参数
	 * @修改者名字：tandeng
	 * @修改时间：2014年8月1日下午1:25:59
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param name
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String getParam(HttpServletRequest request, String name) {
		return request.getParameter(name);
	}
	
	public final static List<KeyValue> TRADE_TYPE_LIST = new ArrayList<KeyValue>();

}
