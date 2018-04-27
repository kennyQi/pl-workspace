package hg.dzpw.api.controller;

import hg.common.util.JsonUtil;
import hg.common.util.Md5Util;
import hg.dzpw.pojo.api.appv1.base.ApiRequest;
import hg.dzpw.pojo.api.appv1.base.ApiResponse;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @类功能说明: api基础Controller
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-26 上午9:44:49 
 * @版本：V1.0
 */
public class BaseController {
	/**
	 * @方法功能说明: 将value打印给响应对象
	 * @param response
	 * @param str
	 */
	@Deprecated
	protected void print(HttpServletResponse response,Object value){
		PrintWriter out = null;
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			out.print(value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	/**
	 * @方法功能说明: 获取请求参数
	 * @param request
	 * @param name
	 * @return
	 */
	protected String getParam(HttpServletRequest request,String name) {
		return request.getParameter(name);
	}
	
	/**
	 * @方法功能说明: 签名匹配 
	 * @param apiReq
	 * @return
	 */
	@SuppressWarnings({"rawtypes" })
	protected boolean checkSign(ApiRequest apiReq){
		String oldSign = apiReq.getHeader().getSign();
		//去除API请求中的sign(签名)值
		apiReq.getHeader().setSign(null);
		
		String secretKey = "";//数据库中的密匙key
		String msg = JsonUtil.parseObject(apiReq, false);
		String newSign = Md5Util.MD5(secretKey+msg);
		return oldSign.equals(newSign) ? true : false;
	}
	
	/**
	 * @方法功能说明: 初始化Response 
	 * @param result
	 * @param message
	 * @param list
	 * @return
	 */
	protected ApiResponse initResponse(ApiResponse response,String result,String message){
		//默认是失败的
		if(StringUtils.isBlank(result))
			result = ApiResponse.RESULT_ERROR;
		response.setResult(result);
		response.setMessage(message);
		return response;
	}
}