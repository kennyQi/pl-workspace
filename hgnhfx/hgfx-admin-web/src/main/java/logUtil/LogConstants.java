package logUtil;

import hg.demo.member.common.domain.model.system.Attr;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.KeyValue;

public class LogConstants {
	/**
	 * 登录
	 */
	public static final String  LOG_IN="用户登录";
	/**
	 * 退出
	 */
	public static final String  LOG_OUT="用户退出";
	/**
	 * 新增商品
	 */
	public static final String  PRODUCT_ADD="新增商品";
	/**
	 * 商品上架
	 */
	public static final String  PRODUCT_PUT="商品上架";
	/**
	 * 商品下架
	 */
	public static final String  PRODUCT_DOWN="商品下架";
	/**
	 * 增加权限
	 */
	public static final String  PREM_ADD="增加权限";
	/**
	 * 修改权限
	 */
	public static final String  PREM_EDIT="修改权限";
	/**
	 * 删除权限
	 */
	public static final String  PREM_DEL="删除权限";
	/**
	 * 增加角色
	 */
	public static final String  ROLE_ADD="增加角色";
	/**
	 * 修改角色
	 */
	public static final String  ROLE_EDIT="修改角色";
	/**
	 * 删除角色
	 */
	public static final String  ROLR_DEL="删除角色";
	
	/**
	 * 新增操作员
	 */
	public static final String  OPERATOR_ADD="新增操作员";
	
	/**
	 * 修改操作员
	 */
	public static final String  OPERATOR_EDIT="修改操作员";
	/**
	 * 删除操作员
	 */
	public static final String  OPERATOR_DEL = "删除操作员";
	/**
	 * 重置密码
	 */
	public static final String  RESET_PASS = "重置密码";
	/**
	 * 修改密码
	 */
	public static final String  MODIFY_PASS = "修改密码";
	/**
	 * 启用操作员
	 */
	public static final String  STAFF_START = "启用操作员";
	/**
	 * 禁用操作员
	 */
	public static final String  STAFF_STOP = "禁用操作员";
	
	public final static List<KeyValue> LOG_LIST = new ArrayList<KeyValue>();
	static {
		LOG_LIST.add(new Attr(LOG_IN, "用户登录"));
		LOG_LIST.add(new Attr(LOG_OUT, "用户退出"));
		LOG_LIST.add(new Attr(PRODUCT_ADD,"新增商品"));
		LOG_LIST.add(new Attr(PRODUCT_PUT,"商品上架"));
		LOG_LIST.add(new Attr(PRODUCT_DOWN,"商品下架"));
		LOG_LIST.add(new Attr(PREM_ADD,"增加权限"));
		LOG_LIST.add(new Attr(PREM_EDIT,"修改权限"));
		LOG_LIST.add(new Attr(PREM_DEL,"删除权限"));
		LOG_LIST.add(new Attr(ROLE_ADD,"增加角色"));
		LOG_LIST.add(new Attr(ROLE_EDIT,"修改角色"));
		LOG_LIST.add(new Attr(ROLR_DEL,"删除角色"));
		LOG_LIST.add(new Attr(OPERATOR_ADD,"新增操作员"));
		LOG_LIST.add(new Attr(OPERATOR_EDIT,"修改操作员"));
		LOG_LIST.add(new Attr(OPERATOR_DEL ,"删除操作员"));
		LOG_LIST.add(new Attr(RESET_PASS ,"重置密码"));
		LOG_LIST.add(new Attr(MODIFY_PASS , "修改密码"));
		LOG_LIST.add(new Attr(STAFF_START , "启用操作员"));
		LOG_LIST.add(new Attr(STAFF_STOP ,"禁用操作员"));
		
	}
	
}

