package hg.dzpw.pojo.common.util;

import java.util.regex.Matcher;
import org.apache.commons.lang.StringUtils;

/**
 * @类功能说明: 非法字符过滤通用用具
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @版本：V1.0
 */
public class StringFilterUtil {
	
	public final static String[] chartIllegals = {
			"html","script","document","alert","</",">","\"","%","$"
		};
	
	/**
	 * 特殊字符转文本
	 * @param content 要转换的内容
	 * @return
	 */
	public static String reverseString(String content) {
		if(StringUtils.isBlank(content))
			return null;
		content = content.trim();
		
		for(int i= 0,len = chartIllegals.length;i < len;i++){
			if(content.indexOf(chartIllegals[i]) != -1)
				content = content.replaceAll(Matcher.quoteReplacement(chartIllegals[i]),"");//取消特殊字符含义
		}
		return content;
	}
}