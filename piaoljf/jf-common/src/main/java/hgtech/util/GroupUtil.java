package hgtech.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;

public class GroupUtil   {
	public static interface FlowDefine<T>{
		public String getGroupKey(T flow);
		public void setGropuKey(T flow,String groupkey);
		/**
		 * 新组中第一个流水
		 * @param flow
		 * @return
		 */
		public T newSumFlow(T flow);
		public void sum(T flow, T sumFlow);
	}
	
	/**
	 * tom,10;<br>
	 * tom,20;<br>
	 * mary,100<br>
	 * 返回<br>
	 * tom:30(合计);<br>
		 * tom,10;<br>
		 * tom,20;<br>
	 * mary,100（合计）<br>
	 * 	mary,100<br>	  
	 * @param <T>
	 * @param flow
	 * @param define
	 * @return
	 */
	public static <T> Map<T,List<T>>  group(List<T> flow,final FlowDefine  define){
		Map<T,List<T>> ret= new LinkedMap ();
		//sort
		Collections.sort(flow, new Comparator<T>() {
			@Override
			public int compare(Object o1, Object o2) {
				String key1 = define.getGroupKey(o1);
				String key2 = define.getGroupKey(o2);
				if(key1==null)
					return key2==null?0:1;
				return key1.compareTo(key2);
			};
		});
		
		String groupKey=System.currentTimeMillis()+"__";
		List<T> groupList = null ;
		T sumFlow = null;
		for(T f:flow){
			String theGr=define.getGroupKey(f);
			boolean newGroup ;
			if(theGr==null ){
				newGroup = groupKey!=null;
			}else
				newGroup =!theGr.equals(groupKey);
			if(newGroup){
				//new group
				groupKey=theGr;
				sumFlow = (T) define.newSumFlow(f);
				define.setGropuKey(sumFlow, groupKey);
				groupList = new LinkedList<T>();
				ret.put( sumFlow, groupList);
				
			}
			groupList.add(f);
			define.sum(f,sumFlow);
		}
		
		return ret;
	}
	
}
