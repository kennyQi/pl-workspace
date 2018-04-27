package slfx.xl.pojo.system;

import java.util.Map;
import java.util.TreeMap;

/**
 * @类功能说明：价格政策常量
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：liusong
 * @创建时间：2014年12月18日上午11:20:32
 * @版本：V1.0
 *
 */
public class SalePolicyConstants {
	
	/**
	 * 请选择适用线路
	 */
	public static final Map<String,String>	salePolicyLineTypeMap = new TreeMap<String,String>();
	
	public final static Integer TYPE_BY_LINENAME = 1;
	public final static Integer TYPE_BY_LINETYPE = 2;
	public final static Integer TYPE_BY_PRICE = 3;
	public final static Integer TYPE_BY_START = 4;
	public final static Integer TYPE_BY_ALL = 5;
	
	static{
		salePolicyLineTypeMap.put(TYPE_BY_LINENAME + "", "按线路名称");
		salePolicyLineTypeMap.put(TYPE_BY_LINETYPE + "", "按线路类别");
		salePolicyLineTypeMap.put(TYPE_BY_PRICE + "", "按成人价格");
		salePolicyLineTypeMap.put(TYPE_BY_START + "", "按出发地");
		salePolicyLineTypeMap.put(TYPE_BY_ALL + "", "全部");
	}
	
	/***
	 * 政策状态
	 */
	public static final Map<String,String> policyStatusMap = new TreeMap<String,String>();
	
	/**
	 * 未发布
	 */
	public static Integer DO_NOT_RELEASE = 1;
	/**
	 * 已取消
	 */
	public static Integer SALE_CANCEL = 2;
	/**
	 * 已发布
	 */
	public static Integer SALE_SUCCESS = 3;
	/**
	 * 已开始
	 */
	public static Integer SALE_START = 4;
	/**
	 * 已下架
	 */
	public static Integer SALE_DOWN = 5;
	
	static{
		policyStatusMap.put(DO_NOT_RELEASE + "", "未发布");
		policyStatusMap.put(SALE_CANCEL + "", "已取消");
		policyStatusMap.put(SALE_SUCCESS + "", "已发布");
		policyStatusMap.put(SALE_START + "", "已开始");
		policyStatusMap.put(SALE_DOWN + "", "已下架");
	}

	
	/**
	 * 请选择价格政策
	 */
	public static final Map<String,String>	salePolicyPriceTypeMap = new TreeMap<String,String>();
	public static Integer  GROW_PRICE= 1;//成人价格
	public static Integer  YOUNG_PRICE = 2;//儿童价格
	public static Integer  ALL_PRICE = 0;//成人儿童都适用
	static{
		salePolicyPriceTypeMap.put(DO_NOT_RELEASE + "", "成人价格");
		salePolicyPriceTypeMap.put(SALE_CANCEL + "", "儿童价格");
		salePolicyPriceTypeMap.put(ALL_PRICE + "", "全部");
	}
	
	/**
	 * 价格政策开始编号
	 */
	public final static int POLICY_NUMBER_START = 1001;
}
