/**
 * @State.java Create on 2015年1月15日上午11:30:14
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.state;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @类功能说明：状态
 * @类修改者：
 * @修改日期：2015年1月13日下午1:41:49
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=xinglj@hgtech365.com>xinglj</a>
 * @创建时间：2015年1月13日下午1:41:49
 * @version：
 */
public abstract class State implements Serializable{
	
	public int state;
	public String stateText;
	public Map<String, Object> otherData=new HashMap<>();
	/**
	 * 状态路由
	 */
	 Map<StatePath,State> routes=new HashMap<>();
	
	public State(){
		StateDao.instance.states.add(this);
	}
	
	public void addRoute(StatePath path){
		routes.put(path, path.toState);
	}
	
	/**
	 * 此状态下操作列表
	 * @方法功能说明：此状态下操作列表
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月15日上午10:37:12
	 * @version：
	 * @修改内容：
	 * @参数：@return
	 * @return:Set<SatatePath>
	 * @throws
	 */
	public Set<StatePath> getAllPathes(){
		return routes.keySet();
	}

	/**
	 * 
	 * @方法功能说明：此状态下根据对象得到的可操作列表
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月15日上午10:30:46
	 * @version：
	 * @修改内容：
	 * @参数：@param o 用以判断路径走向的对象数据
	 * @参数：@return
	 * @return:SatatePath
	 * @throws
	 */
	public LinkedList<StatePath> getAvailablePathes(Object o){
		LinkedList<StatePath> pathes=new LinkedList<>();
		LinkedList<String> pathname =judgePathName(o);
		for(StatePath p:routes.keySet())
			for(String pname:pathname)
			if(p.pathName.equalsIgnoreCase(pname))
				pathes.add(p);
		return pathes;
	}
	
	/**
	 * 按这个路径执行
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月15日下午1:23:48
	 * @version：
	 * @修改内容：
	 * @参数：@param path
	 * @参数：@return
	 * @return:State。如果是自动路径，可能会跨越几个状态
	 * @throws
	 */
	public State goPath(Object o, StatePath path){
		System.out.println("go path: "+path);
		
		//自动路径
		if(path.isAuto)
		{
			//执行路径上的动作
			path.action(o);
			
			//这个路径连接的下一个状态
			State st2=this.routes.get(path);
			if(st2==null)
				throw new RuntimeException("没有对应目的状态的路径："+path);
			
//			根据对象判断 应该走的路径
			LinkedList<String> pathname = st2.judgePathName(o);
			
//			如果只有一个路径，那么没有选择，可以继续走
			if(pathname.size()==1)
			{
				StatePath path2 = StateDao.instance.pathes.get(pathname.getFirst());
				if(path2==null)
					throw new RuntimeException("未知的路径："+pathname.getFirst());
				return st2.goPath(o, path2);
			}else{
				return st2;
			}
		}else
//			非自动路径，状态不变
			return this;
	}
	
	/**
	 * 
	 * @方法功能说明：转移至 下一状态
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月15日上午11:05:05
	 * @version：
	 * @修改内容：
	 * @参数：@param path 执行完毕的路径
	 * @参数：@return
	 * @return:State
	 * @throws
	 */
	public State nextState(StatePath path){
		return routes.get(path);
	}
	
	/**
	 * 
	 * @方法功能说明：直接到某个状态. 
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月15日上午11:05:53
	 * @version：
	 * @修改内容：
	 * @参数：@param state
	 * @return:这个状态是否是后面的状态
	 * @throws
	 */
	public boolean directToState(State state){
		 return  findBackState(state);
	}
	
	/**
	 * @方法功能说明：这个状态是否是后面的状态
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月15日上午11:12:58
	 * @version：
	 * @修改内容：
	 * @参数：@param state2
	 * @参数：@return
	 * @return:Object
	 * @throws
	 */
	private boolean findBackState(State dest) {
		for(State st:routes.values())
		{	
			if(st.state==dest.state)
				return true;
			else {
				//backSt 后面的状态
				for(State backSt:st.routes.values())
					if(backSt.findBackState(dest))
						return true;
			}
		}	
		return false;
	}

	public abstract    LinkedList<String> judgePathName(Object o);
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
			return  stateText;
	}
}