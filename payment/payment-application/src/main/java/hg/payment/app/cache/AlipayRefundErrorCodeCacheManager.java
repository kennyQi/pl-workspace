package hg.payment.app.cache;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * 
 * 
 *@类功能说明：支付宝批量退款错误码
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年12月2日上午9:22:54
 *
 */
@Component
public class AlipayRefundErrorCodeCacheManager {

	private Map<String,String> errorCodeMap = new HashMap<String,String>();
	
	//依据错误提示码获取错误信息
	public String getMessageByCode(String code){
		String message = "";
		message = errorCodeMap.get(code);
		return message;
	}
	

	//初始化支付宝错误信息
	public void initMessageCode(){
		errorCodeMap.put("ILLEGAL_SIGN", "签名不正确");
		errorCodeMap.put("ILLEGAL_DYN_MD5_KEY", "动态密钥信息错误");
		errorCodeMap.put("ILLEGAL_ENCRYPT", "加密不正确");
		errorCodeMap.put("ILLEGAL_ARGUMENT", "参数不正确");
		errorCodeMap.put("ILLEGAL_SERVICE", "Service参数不正确");
		errorCodeMap.put("ILLEGAL_USER", "用户ID不正确");
		errorCodeMap.put("ILLEGAL_PARTNER", "合作伙伴ID不正确");
		errorCodeMap.put("ILLEGAL_EXTERFACE", "接口配置不正确");
		errorCodeMap.put("ILLEGAL_PARTNER_EXTERFACE", "合作伙伴接口信息不正确");
		errorCodeMap.put("ILLEGAL_SECURITY_PROFILE", "未找到匹配的密钥配置");		
		errorCodeMap.put("ILLEGAL_AGENT", "代理ID不正确");
		errorCodeMap.put("ILLEGAL_SIGN_TYPE", "签名类型不正确");
		errorCodeMap.put("ILLEGAL_CHARSET", "字符集不合法");
		errorCodeMap.put("ILLEGAL_CLIENT_IP", "客户端IP地址无权访问服务");
		errorCodeMap.put("HAS_NO_PRIVILEGE", "无权访问");
		errorCodeMap.put("ILLEGAL_DIGEST_TYPE", "摘要类型不正确");
		errorCodeMap.put("ILLEGAL_DIGEST", "文件摘要不正确");
		errorCodeMap.put("ILLEGAL_FILE_FORMAT", "文件格式不正确");
		errorCodeMap.put("ILLEGAL_ENCODING", "不支持该编码类型");
		errorCodeMap.put("ILLEGAL_REQUEST_REFERER", "防钓鱼检查不支持该请求来源");		
		errorCodeMap.put("ILLEGAL_ANTI_PHISHING_KEY", "防钓鱼检查非法时间戳参数");
		errorCodeMap.put("ANTI_PHISHING_KEY_TIMEOUT", "防钓鱼检查时间戳超时");
		errorCodeMap.put("ILLEGAL_EXTER_INVOKE_IP", "防钓鱼检查非法调用IP");
		errorCodeMap.put("BATCH_NUM_EXCEED_LIMIT", "总笔数大于1000");
		errorCodeMap.put("REFUND_DATE_ERROR", "错误的退款时间");
		errorCodeMap.put("BATCH_NUM_ERROR", "传入的总笔数格式错误");
		errorCodeMap.put("DUBL_ROYALTY_IN_DETAIL", "同一条明细中存在两条转入转出账户相同的分润信息");
		errorCodeMap.put("BATCH_NUM_NOT_EQUAL_TOTAL", "传入的退款条数不等于数据集解析出的退款条数");
		errorCodeMap.put("SINGLE_DETAIL_DATA_EXCEED_LIMIT", "单笔退款明细超出限制");
		errorCodeMap.put("DUBL_TRADE_NO_IN_SAME_BATCH", "同一批退款中存在两条相同的退款记录");		
		errorCodeMap.put("DUPLICATE_BATCH_NO", "重复的批次号");
		errorCodeMap.put("TRADE_STATUS_ERROR", "交易状态不允许退款");
		errorCodeMap.put("BATCH_NO_FORMAT_ERROR", "批次号格式错误");
		errorCodeMap.put("PARTNER_NOT_SIGN_PROTOCOL", "平台商未签署协议");
		errorCodeMap.put("NOT_THIS_PARTNERS_TRADE", "退款明细非本合作伙伴的交易");
		errorCodeMap.put("DETAIL_DATA_FORMAT_ERROR", "数据集参数格式错误");
		errorCodeMap.put("SELLER_NOT_SIGN_PROTOCOL", "卖家未签署协议");
		errorCodeMap.put("INVALID_CHARACTER_SET", "字符集无效");
		errorCodeMap.put("ACCOUNT_NOT_EXISTS", "账号不存在");
		errorCodeMap.put("EMAIL_USERID_NOT_MATCH", "Email和用户ID不匹配");		
		errorCodeMap.put("REFUND_ROYALTY_FEE_ERROR", "退分润金额不合法");
		errorCodeMap.put("ROYALTYER_NOT_SIGN_PROTOCOL", "分润方未签署三方协议");
		errorCodeMap.put("RESULT_AMOUNT_NOT_VALID", "退收费、退分润或者退款的金额错误");
		errorCodeMap.put("REASON_REFUND_ROYALTY_ERROR", "退分润错误");
		errorCodeMap.put("TRADE_NOT_EXISTS", "交易不存在");
		errorCodeMap.put("WHOLE_DETAIL_FORBID_REFUND", "整条退款明细都禁止退款");
		errorCodeMap.put("TRADE_HAS_CLOSED", "交易已关闭，不允许退款");
		errorCodeMap.put("TRADE_HAS_FINISHED", "交易已结束，不允许退款");
		errorCodeMap.put("NO_REFUND_CHARGE_PRIVILEDGE", "没有退收费的权限");
		errorCodeMap.put("RESULT_BATCH_NO_FORMAT_ERROR", "批次号格式错误");		
		errorCodeMap.put("BATCH_MEMO_LENGTH_EXCEED_LIMIT", "备注长度超过256字节");
		errorCodeMap.put("REFUND_CHARGE_FEE_GREATER_THAN_LIMIT", "退收费金额超过限制");
		errorCodeMap.put("REFUND_TRADE_FEE_ERROR", "退交易金额不合法");
		errorCodeMap.put("SELLER_STATUS_NOT_ALLOW", "卖家状态不正常");
		errorCodeMap.put("SINGLE_DETAIL_DATA_ENCODING_NOT_SUPPORT", "单条数据集编码集不支持");
		errorCodeMap.put("TXN_RESULT_ACCOUNT_STATUS_NOT_VALID", "卖家账户状态无效或被冻结");
		errorCodeMap.put("TXN_RESULT_ACCOUNT_BALANCE_NOT_ENOUGH", "卖家账户余额不足");
		errorCodeMap.put("CA_USER_NOT_USE_CA", "数字证书用户但未使用数字证书登录");
		errorCodeMap.put("BATCH_REFUND_LOCK_ERROR", "同一时间不允许进行多笔并发退款");
		errorCodeMap.put("REFUND_SUBTRADE_FEE_ERROR", "退子交易金额不合法");		
		errorCodeMap.put("NANHANG_REFUND_CHARGE_AMOUNT_ERROR", "退票面价金额不合法");
		errorCodeMap.put("REFUND_AMOUNT_NOT_VALID", "退款金额不合法");
		errorCodeMap.put("TRADE_PRODUCT_TYPE_NOT_ALLOW_REFUND", "交易类型不允许退交易");
		errorCodeMap.put("RESULT_FACE_AMOUNT_NOT_VALID", "退款票面价不能大于支付票面价");
		errorCodeMap.put("REFUND_CHARGE_FEE_ERROR", "退收费金额不合法");
		errorCodeMap.put("REASON_REFUND_CHARGE_ERR", "退收费失败");
		errorCodeMap.put("RESULT_AMOUNT_NOT_VALID", "退收费金额错误");
		errorCodeMap.put("DUP_ROYALTY_REFUND_ITEM", "重复的退分润条目");
		errorCodeMap.put("RESULT_ACCOUNT_NO_NOT_VALID", "账号无效");
		errorCodeMap.put("REASON_REFUND_ROYALTY_ERROR", "退分润失败");		
		errorCodeMap.put("REASON_TRADE_REFUND_FEE_ERR", "退款金额错误");
		errorCodeMap.put("REASON_HAS_REFUND_FEE_NOT_MATCH", "已退款金额错误");
		errorCodeMap.put("TXN_RESULT_ACCOUNT_BALANCE_NOT_ENOUGH", "账户余额不足");
		errorCodeMap.put("REASON_REFUND_AMOUNT_LESS_THAN_COUPON_FEE", "红包无法部分退款");
		errorCodeMap.put("BATCH_REFUND_STATUS_ERROR", "退款记录状态错误");
		errorCodeMap.put("BATCH_REFUND_DATA_ERROR", "批量退款后数据检查错误");
		errorCodeMap.put("REFUND_TRADE_FAILED", "不存在退交易，但是退收费和退分润失败");
		errorCodeMap.put("REFUND_FAIL", "退款失败");
		
		
	}
	
	
}
