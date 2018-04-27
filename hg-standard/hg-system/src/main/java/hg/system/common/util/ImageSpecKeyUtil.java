package hg.system.common.util;

import hg.system.model.image.ImageSpec;

import java.util.Iterator;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类功能说明：图片附件别名_工具类
 * @类修改者：zzb
 * @修改日期：2014年9月4日下午3:11:31
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年9月4日下午3:11:31
 *
 */
public class ImageSpecKeyUtil {
	
	/**
	 * 
	 * @方法功能说明：检查key是否合理
	 * @修改者名字：zzb
	 * @修改时间：2014年9月4日下午3:40:37
	 * @修改内容：
	 * @参数：@param key
	 * @参数：@return
	 * @return:Boolean
	 * @throws
	 */
	public static Boolean vailKey(String vailKey, JSONArray imageSpecKeys) {
		
		if (imageSpecKeys == null) {
			return null;			
		}
		
		if(vailKey.equals(ImageSpec.DEFAULT_KEY))
			return true;

		for (Iterator<Object> iterator = imageSpecKeys.iterator(); iterator.hasNext();) {
			JSONObject object = (JSONObject) iterator.next();
			
			if (object != null && object.getString("en").equals(vailKey))
				return true;
		}

		return false;
	}
	
	
	
	
}
