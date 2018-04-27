package hg.demo.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

import validatorUtil.Validator;

/**
 * @类功能说明: Request包装类
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @版本：V1.0
 */
public class MyRequestWrapper extends HttpServletRequestWrapper {

	/** 非过滤字段	 */
	public final static String[] KEY_WORDS = {"originalPass","newPass","againPass","password"};
	
	public MyRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] values = super.getParameterValues(name);
		for(int j=0;j<KEY_WORDS.length;j++){
			if(StringUtils.equals(KEY_WORDS[j],name)){
//				System.out.println(JSON.toJSONString(values));
				return values;
			}
		}
		if(values!=null){
			for(int i=0;i<values.length;i++){
				values[i]=Validator.filterRemove(Validator.filter(values[i]));
			}
		}
		return values;
	};
	
	@Override
	public String getParameter(String name) {
		return Validator.filterRemove(Validator.filter(super.getParameter(name)));
	}
	
}