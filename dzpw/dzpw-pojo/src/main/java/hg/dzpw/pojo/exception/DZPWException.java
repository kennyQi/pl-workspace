package hg.dzpw.pojo.exception;

import hg.dzpw.pojo.common.BaseException;

/**
 * @类功能说明：电子票务异常
 * @类修改者：
 * @修改日期：2014-11-7下午3:58:36
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-7下午3:58:36
 */
@SuppressWarnings("serial")
public class DZPWException extends BaseException {

	public DZPWException() {
		super();
	}

	public DZPWException(int code, String message) {
		super(code, message);
	}
	
	
	
	/**
     * 异常定义 
     * 0.权限异常			1-999
     * 1.系统管理模块异常编号	1000-1999
     * 2.景区管理模块异常编号	2000-2999
     * 3.经销商管理模块异常编号	3000-3999
     * 4.联票管理模块异常编号	4000-4999
     * 5.联票订单管理异常编号	5000-5999
     * 6.财务对账模块异常编号	6000-6999
     * 7.统计报表模块异常编号	7000-7999
     * 8.接口异常编号		8000-8999
     * 9.其他异常编号		9000-9999
     */
	

	
	/**
	 * 只有异常消息
	 */
	public final static int MESSAGE_ONLY = 0;
	
	/**
	 * 没有权限
	 */
	public final static int NO_AUTH = 403;

	// --------------------- 经销商管理模块异常 ---------------------
	/**
	 * 经销商ID不能为空
	 */
	public final static int DEALER_ID_IS_NULL = 3000;
	/**
	 * 经销商不存在
	 */
	public final static int DEALER_NOT_EXISTS = 3001;
	
	/**
	 * 经销商已关联价格政策
	 */
	public final static int DEALER_HAS_SALE_POLICY = 3002;
	
	// --------------------- 景区管理 ---------------------
	/**
	 * 操作景区缺少参数
	 */
	public final static int NEED_SCENICSPOT_WITHOUTPARAM = 2001;
	
	/**
	 * 操作景区门票周期缺少参数
	 */
	public final static int NEED_SCENICSPOT_TICKET_WITHOUTPARAM = 2002;
	
	/**
	 * 景区其它参数空值异常
	 */
	public final static int NEED_SCENICSPOT_NOTEXIST = 2003;
	
	
	/**
	 * 景区其它参数非法异常
	 */
	public final static int NEED_SCENICSPOT_NOILLEGALITY = 2004;
	
	/**
	 * 景区登录名重复
	 */
	public final static int SCENICSPOT_LOGINNAME_REPEAT = 2005;
	
	/**
	 * 景区已被使用过，不能删除
	 */
	public final static int SCENICSPOT_USED_CANNOT_REMOVE = 2006;
	
	/**
	 * 入园设备编号重复
	 */
	public final static int SCENICSPOT_CLIENTDEVICE_NUMBER_REPEAT = 2007;
	
	/**
	 * 景区ID不能为空
	 */
	public final static int SCENICSPOT_ID_IS_NULL = 2008;
	
	/**
	 * 景区不存在
	 */
	public final static int SCENICSPOT_NOT_EXISTS = 2009;
	
	/**
	 * 创建或修改景区的命令字段不符合要求
	 */
	public final static int SCENICSPOT_COMMAND_FIELD_NOT_REQUIRE = 2010;
	
	/**
	 * 景区代码已经存在
	 */
	public final static int SCENICSPOT_CODE_EXISTS = 2011;
	/**
	 * 景区图片不存在
	 */
	public final static int SCENICSPOT_PIC_NOT_EXISTS = 2012;
	/**
	 * 景区图片格式不合要求
	 */
	public final static int SCENICSPOT_PIC_FORMAT_ERROR = 2013;
	/**
	 * 景区图片文件大小不合要求
	 */
	public final static int SCENICSPOT_PIC_SIZE_EERROR = 2014;
	/**
	 * 景区图片尺寸不合要求
	 */
	public final static int SCENICSPOT_PIC_PIX_ERROR = 2015;
	
	// --------------------- 联票管理 ---------------------
	/**
	 * 操作联票缺少参数
	 */
	public final static int NEED_TICKETGROUP_WITHOUTPARAM = 4001;
	
	/**
	 * 联票不存在
	 */
	public final static int TICKETGROUP_NOT_EXISTS = 4002;
	
	/**
	 * 联票参数不符合要求
	 */
	public final static int NEED_TICKETGROUP_NOT_REQUIRE = 4003;
	
	/**
	 * 分销价格政策缺少参数
	 */
	public final static int NEED_TICKETGROUP_PRICE_WITHOUTPARAM = 4004;
	
	/**
	 * 分销价格政策不存在
	 */
	public final static int TICKETGROUP_PRICE_NOT_EXISTS = 4005;
	
	//-----------------------------------联票政策--------------------------------------
	/**
	 * 联票政策ID不能为空
	 */
	public final static int TICKET_POLICY_ID_IS_NULL = 4000;
	/**
	 * 联票政策已被下架
	 */
	public final static int TICKET_POLICY_STATUS_FINISH = 4006;
	/**
	 * 销售政策ID不能为空
	 */
	public final static int SALE_POLICY_ID_IS_NULL = 4007;
	/**
	 * 销售政策已结束
	 */
	public final static Integer SALE_POLICY_STATUS_FINISH = 4008;
	/**
	 * 销售政策已经存在
	 */
	public final static Integer SALE_POLICY_IS_EXIST = 4009;
	/**
	 * 存在相同名称的价格政策
	 */
	public final static Integer SALE_POLICY_SAME_NAME = 4010;
	/**
	 * 门票政策处于发布状态
	 */
	public final static int SINGLE_TICKET_POLICY_ISSUE = 4011;
	/**
	 * 门票政策不存在
	 */
	public final static int TICKET_POLICY_IS_NULL = 4012;

	
	//-------------------单票政策-------------------------
	/**
	 * 操作联票缺少参数
	 */
	public final static int NEED_SINGLE_TICKET_WITHOUTPARAM = 5001;
	
}