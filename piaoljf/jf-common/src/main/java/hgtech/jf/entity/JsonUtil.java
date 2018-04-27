/**
 * @文件名称：JsonUtil.java
 * @类路径：hgtech.jfadmin.dao.imp
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月30日下午12:40:36
 */
package hgtech.jf.entity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014年10月30日下午12:40:36
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月30日下午12:40:36
 *
 */
public class JsonUtil {
    	/**
    	 * 
    	 * @方法功能说明：带有类名的json。防止解析时候不知道类型，无法解析出对象
    	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
    	 * @修改时间：2015年1月27日下午7:53:18
    	 * @version：
    	 * @修改内容：
    	 * @参数：@param o
    	 * @参数：@return
    	 * @return:String
    	 * @throws
    	 */
	public static String tojson(Object o){
		return JSONObject.toJSONString(o,SerializerFeature.PrettyFormat, SerializerFeature.WriteClassName);

	}
	public static Object toO(String j){
		return JSONObject.parse(j);
	}
	
	
}
