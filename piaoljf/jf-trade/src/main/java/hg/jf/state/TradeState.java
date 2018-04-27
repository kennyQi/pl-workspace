/**
 * @JfState.java Create on 2015年1月15日下午2:13:43
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.jf.state;

import java.io.Serializable;
import java.util.LinkedList;

import hg.state.State;
import hg.state.StatePath;

/**
 * @类功能说明：积分互转的状态机
 * @类修改者：
 * @修改日期：2015年1月15日下午2:13:43
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=xinglj@hgtech365.com>xinglj</a>
 * @创建时间：2015年1月15日下午2:13:43
 * @version：
 */
public class TradeState implements Serializable{
	
	
	
	/**
	 * @FieldsTO_OK:tranfer ok
	 */
	private static final String TO_END = "to_end";
	/**
	 * @FieldsTO_TRANSFEROUTFAIL:tranfer out fail
	 */
	private static final String TO_TRANSFEROUTFAIL = "to_outfail";
	/**
	 * @FieldsTO_TRANSFEROUTOK: tranfer out ok
	 */
	private static final String TO_TRANSFEROUTOK = "to_outok";
	/**
	 * @FieldsTO_TRANSFEROUT:to tranfer out
	 */
	private static final String TO_TRANSFEROUT = "to_transferout";
	protected static final String TO_TRANSFERINOK = "to_transferinok";
	protected static final String TO_TRANSFERINFAIL = "to_transferinfail";
	protected static final String TO_TRANSFERIN = "to_transferin";
	private static TradeState instance ;
	/**
	 * 初始化
	 */
	public  State stInited;
	/**
	 * 转出动作结束
	 */
	public  State stOuted;
	public  State stOutedOk;
	public  State stOutedFail;
	/**
	 * 转入动作结束
	 */
	public  State stIn;
	public  State stInOk;
	public  State stInFail;
	/**
	 * 完毕
	 */
	public  State stOk;
	
	public static TradeState getInstance(){
		if(instance==null)
		{
			instance=new TradeState();
			instance.init();


		}
		return instance;
	}

	/**
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月18日下午4:18:23
	 * @version：
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	private void init() {
		//inited
		 stInited = new State() {
			@Override
			public LinkedList<String> judgePathName(Object o) {
				LinkedList p=new LinkedList<>();
				p.add(TO_TRANSFEROUT);
				return p;
			}
		};
		stInited.state=100;
		stInited.stateText="初始化";

//		转出结束状态 
		  stOuted=new State() {
			
			@Override
			public LinkedList<String> judgePathName(Object o) {
				LinkedList r=new LinkedList<>();
				if(((TradeStateAction)o).isOutOk()){
					r.add(TO_TRANSFEROUTOK);
				}else{
					r.add(TO_TRANSFEROUTFAIL);
				}
				return r;
			}
		};
		stOuted.state=200;
		stOuted.stateText="转出完毕";
		
		StatePath pathTransferOut =new StatePath(TO_TRANSFEROUT,stOuted) {
			
			@Override
			public void action(Object o) {
				((TradeStateAction)o).tranfserOut();
			}
		};
		pathTransferOut.pathText="转出积分";
		pathTransferOut.isAuto=true;
		
		stInited.addRoute(pathTransferOut);
		
		//			转出成功状态
		  stOutedOk=new State() {
			
			@Override
			public LinkedList<String> judgePathName(Object o) {
				LinkedList<String> linkedList = new LinkedList<>();
				linkedList.add(TO_TRANSFERIN);
				return linkedList;
			}
		};
		stOutedOk.state=301;
		stOutedOk.stateText="转出成功";			
//		转出失败状态
		 stOutedFail=new State() {
			
			@Override
			public LinkedList<String> judgePathName(Object o) {
				return new LinkedList<>();
			}
		};
		stOutedFail.state=301;
		stOutedFail.stateText="转出失败";

		
		StatePath pathReresultOk=new StatePath(TO_TRANSFEROUTOK, stOutedOk) {
			
			@Override
			public void action(Object o) {}
		};
		pathReresultOk.isAuto=true;
		pathReresultOk.pathText="转出结果ok";

		StatePath pathReresultFail=new StatePath(TO_TRANSFEROUTFAIL, stOutedFail) {
			
			@Override
			public void action(Object o) {}
		};
		pathReresultFail.isAuto=true;
		pathReresultFail.pathText="转出结果fail";
		
		stOuted.addRoute(pathReresultOk);
		stOuted.addRoute(pathReresultFail);
		
//		转入结束状态 
		  stIn = new State(){

			@Override
			public LinkedList<String> judgePathName(Object o) {
				LinkedList<String> pathes = new LinkedList<>();
				if(((TradeStateAction)o).isInOk())
					pathes.add(TO_TRANSFERINOK);
				else
					pathes.add(TO_TRANSFERINFAIL);
				return pathes;
			}
			
		}; 
		
//		path:转入
		StatePath pathIn=new StatePath(TO_TRANSFERIN, stIn) {
			
			@Override
			public void action(Object o) {
				((TradeStateAction)o).transferIn();
			}
		};
		pathIn.isAuto=true;
		pathIn.pathText="转入";
		
		stOutedOk.addRoute(pathIn);
		
//		转入成功状态
		  stInOk=new State() {
			@Override
			public LinkedList<String> judgePathName(Object o) {
				LinkedList<String> path =new LinkedList<>();
				path.add(TO_END);
				return path;
			}
		};
		stInOk.state=400;
		stInOk.stateText="积分转入成功/未结算，在途";
		
//		转入失败状态
		  stInFail=new State() {
			
			@Override
			public LinkedList<String> judgePathName(Object o) {
				return new LinkedList<>();
			}
		};
		stInFail.state=401;
		stInFail.stateText="转入失败";
		
		StatePath pathInok=new StatePath(TO_TRANSFERINOK,stInOk) {
			@Override
			public void action(Object o) {}
		};
		pathInok.isAuto=true;
		pathInok.pathText="转入判断成功";
		StatePath pathInfail=new StatePath(TO_TRANSFERINFAIL, stInFail) {
			
			@Override
			public void action(Object o) {}
		};
		pathInfail.isAuto=true;
		pathInfail.pathText="转入判断失败";
		
		stIn.addRoute(pathInfail);
		stIn.addRoute(pathInok);
		
//		结算完毕状态
		  stOk=new State() {
			
			@Override
			public LinkedList<String> judgePathName(Object o) {
				return new LinkedList<>();
			}
		};
		stOk.state=1000;
		stOk.stateText="入账";
		
		StatePath pathEnd=new StatePath(TO_END, stOk) {
			
			@Override
			public void action(Object o) {
			}
		};
		pathEnd.isAuto=false;
		pathEnd.pathText="结算";

		stInOk.addRoute(pathEnd);		
	}
	
}
