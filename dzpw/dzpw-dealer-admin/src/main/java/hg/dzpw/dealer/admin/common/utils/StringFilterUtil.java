package hg.dzpw.dealer.admin.common.utils;

import org.apache.commons.lang.StringUtils;

/**
 * @类功能说明: 非法字符过滤通用用具
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @版本：V1.0
 */
public class StringFilterUtil {
	
	public final static String[] chartIllegals = {
			"html","script","document","/script","/html","alert","</",">","\"","%"
		};
	
	public final static String[] chartIllegals2 = {
		"html","script","document","alert","write"
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
				content = content.replaceAll(chartIllegals[i],"");
		}
		return content;
	}
	
	
	/**
	 * @方法功能说明：是否包含敏感字符串
	 * @修改者名字：yangkang
	 * @修改时间：2014-12-11下午3:55:39
	 * @参数：@return
	 * @return:Boolean
	 */
	public static Boolean hasIllegalString(String content){
		Boolean f = false;
		if(StringUtils.isBlank(content))
			return f;
		else{
			for(String s : chartIllegals2){
				if(content.indexOf(s)!=-1){
					f = true;
					break;
				}
			}
			return f;
		}
	}
}