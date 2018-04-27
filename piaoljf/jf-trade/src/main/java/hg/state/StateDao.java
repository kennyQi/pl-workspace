/**
 * @StatteMachine.java Create on 2015年1月13日上午11:20:51
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.state;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @类功能说明：状态机模型
 * @类修改者：
 * @修改日期：2015年1月13日上午11:20:51
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=xinglj@hgtech365.com>xinglj</a>
 * @创建时间：2015年1月13日上午11:20:51
 * @version：
 */
public class StateDao {
	public static StateDao instance=new StateDao();
	/**
	 * all 状态
	 */
	LinkedList< State> states =new LinkedList<>();
	/**
	 * 所有路径
	 */
	public  Map<String, StatePath> pathes=new HashMap<String, StatePath>();
	/**
	 * 
	 * @方法功能说明：get State 
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月13日上午11:37:13
	 * @version：
	 * @修改内容：
	 * @参数：@param state
	 * @参数：@return null if not found!
	 * @return:State
	 * @throws
	 */
	public State getState(int state){
		for(State st:states)
			if(st.state==state)
				return st;
		return null;
	}
}
