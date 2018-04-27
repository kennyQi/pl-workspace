package hg.dzpw.dealer.admin.component.filter;

import hg.common.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @类功能说明: Request包装类
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @版本：V1.0
 */
public class MyRequestWrapper extends HttpServletRequestWrapper {

	public MyRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getParameter(String name) {
		return Validator.filterRemove(Validator.filter(super.getParameter(name)));
	}
}