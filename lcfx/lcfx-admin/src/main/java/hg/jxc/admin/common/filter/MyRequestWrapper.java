package hg.jxc.admin.common.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class MyRequestWrapper extends HttpServletRequestWrapper {

	public MyRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getParameter(String name) {
		String pv = super.getParameter(name);
		return filterDangerString(pv);
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] values = super.getParameterValues(name);
		if (values==null) {
			return null;
		}
		for (int i = 0; i < values.length; i++) {
			values[i] = filterDangerString(values[i]);
		}
		return values;
	}

	public String filterDangerString(String value) {
		if (value != null) {
			value = value.replace("<", "&lt;");
			value = value.replace(">", "&gt;");
		}

		return value;
	}

}
