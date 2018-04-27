package hg.system.common.system;

import hg.system.model.dto.Attr;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.KeyValue;

public class SecurityConstants {
	
	/** 登录用户 Session key */
	public final static String SESSION_USER_KEY = "_SESSION_USER_";
	public final static String SESSION_BUSINESS_ACCOUNT_KEY="_BUSINESS_ACCOUNT_";
	/** 企业信息Session key */
	public final static String SESSION_BUSINESS_KEY = "_SESSION_BUSINESS_";
	
	// 权限资源类型的值反映优先级，从大到小排序。
	
	/** 权限资源类型 权限检查 （检查当前访问资源的用户是否拥有该权限） */
	public final static Short PERM_TYPE_AUTH = 0;
	/** 权限资源类型 角色检查 （检查当前访问资源的用户是否拥有该角色）*/
	public final static Short PERM_TYPE_ROLE = 1;
	/** 权限资源类型 登录检查 （当前资源必须登录才能访问）*/
	public final static Short PERM_TYPE_LOGIN = 2;
	/** 不检查权限 */
	public final static Short PERM_TYPE_PASS = 3;
	/** 权限名 */
	public final static Short PERM_TYPE_NAME = 4;
	
	public final static List<KeyValue> PERM_TYPE_LIST = new ArrayList<KeyValue>();
	static {
		PERM_TYPE_LIST.add(new Attr(PERM_TYPE_AUTH, "权限检查"));
		PERM_TYPE_LIST.add(new Attr(PERM_TYPE_NAME, "权限名"));
		PERM_TYPE_LIST.add(new Attr(PERM_TYPE_ROLE, "角色检查"));
		PERM_TYPE_LIST.add(new Attr(PERM_TYPE_LOGIN, "登录检查"));
		PERM_TYPE_LIST.add(new Attr(PERM_TYPE_PASS, "不检查"));
	}

	/**
	 * 登录检查错误：多次登录错误,禁止登录时间未到
	 */
	public final static String LOGIN_CHECK_ERROR_FREEZE_TIME = "0";
	/** 登录检查错误：用户名不存在 */
	public final static String LOGIN_CHECK_ERROR_UNKNOWN_LOGIN_NAME = "1";
	/** 登录检查错误：密码错误 */
	public final static String LOGIN_CHECK_ERROR_PASSWORD = "2";
	/** 登录检查错误：用户已被锁定 */
	public final static String LOGIN_CHECK_ERROR_LOCK = "3";
	/** 登录检查错误：用户异常 */
	public final static String LOGIN_CHECK_ERROR_NULL = "4";
	/** 登录检查错误：验证码不正确 */
	public final static String LOGIN_CHECK_ERROR_YZM="5";
	/** 登录检查错误：用户未激活 */
	public final static String LOGIN_CHECK_ERROR_USER_INACTIVE="6";
	/** 登录检查错误：企业未审核 */
	public final static String LOGIN_CHECK_ERROR_COMPANY_UNAUDITED="7";
	
	public final static List<KeyValue> LOGIN_CHECK_ERROR_LIST = new ArrayList<KeyValue>();
	static {
		LOGIN_CHECK_ERROR_LIST.add(new Attr(LOGIN_CHECK_ERROR_UNKNOWN_LOGIN_NAME, "用户名不存在"));
		LOGIN_CHECK_ERROR_LIST.add(new Attr(LOGIN_CHECK_ERROR_PASSWORD, "密码错误"));
		LOGIN_CHECK_ERROR_LIST.add(new Attr(LOGIN_CHECK_ERROR_LOCK, "用户已被锁定"));
		LOGIN_CHECK_ERROR_LIST.add(new Attr(LOGIN_CHECK_ERROR_NULL, "用户异常"));
		LOGIN_CHECK_ERROR_LIST.add(new Attr(LOGIN_CHECK_ERROR_YZM, "验证码不正确"));
		LOGIN_CHECK_ERROR_LIST.add(new Attr(LOGIN_CHECK_ERROR_USER_INACTIVE, "用户未激活"));
		LOGIN_CHECK_ERROR_LIST.add(new Attr(LOGIN_CHECK_ERROR_COMPANY_UNAUDITED, "企业未审核"));
	}
	
	/** 用户 可用 */
	public final static Short USER_ENABLE = 1;
	/** 用户 不可用 */
	public final static Short USER_DISABLE = 0;
	
	public final static List<KeyValue> USER_ENABLE_LIST = new ArrayList<KeyValue>();
	static {
		USER_ENABLE_LIST.add(new Attr(USER_ENABLE, "已启用"));
		USER_ENABLE_LIST.add(new Attr(USER_DISABLE, "已禁用"));
	}
	
}
