package hg.payment.app.common.filter;


import hg.common.util.Validator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class MyRequestWrapper extends HttpServletRequestWrapper {

	public MyRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getParameter(String name) {
		return Validator.filterRemove(Validator.filter(super.getParameter(name)));
	}

}

