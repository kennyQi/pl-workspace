/**
 * @JsonUtil.java Create on 2016-5-24上午10:33:07
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.demo.member.common.domain.model.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2016-5-24上午10:33:07
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=gaoce@hgtech365.com>gaoce</a>
 * @创建时间：2016-5-24上午10:33:07
 * @version：
 */
public class JsonUtil {
	/**
	 * 将对象转换为JSON字符串
	 * @param obj
	 * @param isArray 是否为数组
	 * @return
	 */
	public static String parseObject(Object obj, boolean isArray){
		if(obj!=null){
			if(isArray && !(obj instanceof Collection)){
				List<Object> list=new ArrayList<Object>();
				list.add(obj);
				return JSON.toJSONString(list);
			}else if(!isArray && obj instanceof Collection){
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("list", obj);
				return JSON.toJSONString(map);
			}else{
				return JSON.toJSONString(obj);
			}
		}else{
			if(isArray){
				return "[]";
			}else{
				return "{}";
			}
		}
	}
	public class obj{
		String id;
		String name;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}
	public static void main(String[] args) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("1", "2");
		map.put("2", 2);
		List<Object> list=new ArrayList<Object>();
		List<Object> list1=new ArrayList<Object>();
		list1.add("222");
		list1.add("你好");
		list.add(list1);
		list.add(new Date());
		System.out.println(parseObject(list1, true));
	}
}
