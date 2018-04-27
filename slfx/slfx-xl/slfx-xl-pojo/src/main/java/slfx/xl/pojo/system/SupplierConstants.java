package slfx.xl.pojo.system;

import java.util.HashMap;
import java.util.Map;

/**
 *@类功能说明：供应商编码常量
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年12月4日下午4:27:58
 *
 */
public class SupplierConstants {

	public final static Map<String,String> STATUS_MAP = new HashMap<String,String>();

	public final static Map<String,String> AUDIT_STATUS_MAP = new HashMap<String,String>();
	
	/*** 未审核 **/
	public static Integer NOT_AUDIT = 1;
	/*** 审核不通过 **/
	public static Integer AUDIT_FAIL = 2;
	/*** 审核通过 **/
	public static Integer AUDIT_SUCCESS = 3;
	
	public static String NOT_AUDIT_STRING = "未审核";
	public static String AUDIT_FAIL_STRING = "审核不通过";
	public static String AUDIT_SUCCESS_STRING = "已审核";
	
	
	static{
		STATUS_MAP.put(NOT_AUDIT + "", NOT_AUDIT_STRING);
		STATUS_MAP.put(AUDIT_FAIL + "", AUDIT_FAIL_STRING);
		STATUS_MAP.put(AUDIT_SUCCESS + "", AUDIT_SUCCESS_STRING);
		
		AUDIT_STATUS_MAP.put(AUDIT_FAIL + "", AUDIT_FAIL_STRING);
		AUDIT_STATUS_MAP.put(AUDIT_SUCCESS + "", "审核通过");
	}
	
	
	
	
	
}
