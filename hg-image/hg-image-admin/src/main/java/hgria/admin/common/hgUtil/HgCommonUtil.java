package hgria.admin.common.hgUtil;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类功能说明：前端控件的后台工具类
 * @类修改者：zzb
 * @修改日期：2014年10月15日下午5:23:06
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年10月15日下午5:23:06
 *
 */
public class HgCommonUtil {

	/**
	 * @类功能说明：树组件转化接口 (逐层)
	 * @类修改者：zzb
	 * @修改日期：2014年10月15日下午6:30:02
	 * @修改说明：
	 * @公司名称：浙江汇购科技有限公司
	 * @作者：zzb
	 * @创建时间：2014年10月15日下午6:30:02
	 *
	 */
	public interface TreeTran {
		
		public String  getId(Object ob);
		
		public String  getText(Object ob);
		
		public Boolean getOpened(Object ob);
	}
	
	/**
	 * @类功能说明：树组件转化接口 (全部)
	 * @类修改者：zzb
	 * @修改日期：2014年11月14日下午3:01:04
	 * @修改说明：
	 * @公司名称：浙江汇购科技有限公司
	 * @作者：zzb
	 * @创建时间：2014年11月14日下午3:01:04
	 *
	 */
	public interface TreeAllTran {
		
		public String  getId(Object ob);
		
		public String  getText(Object ob);
		
		public Boolean getOpened(Object ob);
		
		public String  getParentId(Object ob);
	}
	
	
	
	/**
	 * @方法功能说明：list转成树组件内容 (逐层)
	 * @修改者名字：zzb
	 * @修改时间：2014年10月15日下午6:30:23
	 * @修改内容：
	 * @参数：@param objList
	 * @参数：@param treeTran
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public static JSONArray toTreeJson(List<?> objList, TreeTran treeTran) {
		
		JSONArray array = new JSONArray();
		for (Iterator<?> iterator = objList.iterator(); iterator.hasNext();) {
			
			Object object = (Object) iterator.next();
			
			JSONObject obj = new JSONObject();
			obj.put("id", treeTran.getId(object));
			obj.put("text", treeTran.getText(object));
			JSONObject stateObj = new JSONObject();
			stateObj.put("opened", treeTran.getOpened(object));
			obj.put("state", stateObj);
			obj.put("children", true);
			array.add(obj);
		}
		return array;
		
	}
	
	/**
	 * @方法功能说明：list转成树组件内容 (全部)
	 * @修改者名字：zzb
	 * @修改时间：2014年11月14日下午3:02:03
	 * @修改内容：
	 * @参数：@param objList
	 * @参数：@param treeTran
	 * @参数：@return
	 * @return:JSONArray
	 * @throws
	 */
	public static JSONArray toTreeAllJson(List<?> objList, TreeAllTran treeTran) {
		
		JSONArray array = new JSONArray();
		// 1. 插入map
		Map<String, JSONArray> map = new HashMap<String, JSONArray>();
		for (Iterator<?> iterator = objList.iterator(); iterator.hasNext();) {
			
			Object object = (Object) iterator.next();
			
			JSONObject obj = new JSONObject();
			obj.put("id", treeTran.getId(object));
			obj.put("text", treeTran.getText(object));
			JSONObject stateObj = new JSONObject();
			stateObj.put("opened", treeTran.getOpened(object));
			obj.put("state", stateObj);
			
			String parentId = treeTran.getParentId(object);
			if (!map.containsKey(parentId)) {
				map.put(treeTran.getParentId(object), new JSONArray());
			}
			map.get(parentId).add(obj);
			
			array.add(obj);
		}
		
		// 2. 设置children
		for (Object obj : array) {
			JSONObject jsonObj = (JSONObject) obj;
			jsonObj.put("children", map.get(jsonObj.get("id")));
		}
		
		return map.containsKey(null) ? map.get(null) : new JSONArray();
	}

	/**
	 * @方法功能说明：数据空值返回
	 * @修改者名字：zzb
	 * @修改时间：2014年12月10日上午10:12:52
	 * @修改内容：
	 * @参数：@return
	 * @return:JSONArray
	 * @throws
	 */
	public static JSONArray emptyTreeJson() {
		return new JSONArray();
	}

}

