package hg.payment.app.cache;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * 
 * 
 *@类功能说明：汇金宝响应码
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年11月19日上午9:03:30
 *
 */
@Component
public class HJBMessageCodeCacheManager {
	
	private Map<String,String> messageCodeMap = new HashMap<String,String>();
	
	//依据响应码获取错误信息
	public String getMessageByCode(String code){
		String message = "";
		message = messageCodeMap.get(code);
		return message;
	}
	
	//初始化汇金宝响应码
	public void initMessageCode(){
		
		messageCodeMap.put("EB2C0001", "必填项缺失"); 
		messageCodeMap.put("EB2C0002", "用户状态异常");
		messageCodeMap.put("EB2C0003", "用户不存在");
		messageCodeMap.put("EB2C0004", "通讯异常");
		messageCodeMap.put("EB2C0005", "余额不足");
		messageCodeMap.put("EB2C0006", "数据格式不正确");
		messageCodeMap.put("EB2C0007", "付款账户不存在");
		messageCodeMap.put("EB2C0008", "收款账户不存在");
		messageCodeMap.put("EB2C0009", "付款账户状态异常");
		
		messageCodeMap.put("EB2C0010", "收款账户状态异常");
		messageCodeMap.put("EB2C0011", "数据库插入失败");
		messageCodeMap.put("EB2C0012", "数据库更新失败");
		messageCodeMap.put("EB2C0013", "银行卡不存在");
		messageCodeMap.put("EB2C0014", "通讯失败");
		messageCodeMap.put("EB2C0015", "支付密码错误");
		messageCodeMap.put("EB2C0016", "支付失败");
		messageCodeMap.put("EB2C0017", "支付密码修改失败");
		messageCodeMap.put("EB2C0018", "登陆密码修改失败");
		messageCodeMap.put("EB2C0019", "原订单号不存在");
		
		messageCodeMap.put("EB2C0020", "数据库访问异常");
		messageCodeMap.put("EB2C0021", "未找到记录");
		
		messageCodeMap.put("EB2C0061", "手机号码已存在");
		messageCodeMap.put("EB2C0062", "用户名已存在");
		messageCodeMap.put("EB2C0063", "证件号已存在");
		messageCodeMap.put("EB2C0064", "注册失败");
		messageCodeMap.put("EB2C0065", "已受理");
		messageCodeMap.put("EB2C0067", "用户邮箱已存在");
		messageCodeMap.put("EB2C0068", "银行卡已绑定");
		messageCodeMap.put("EB2C0069", "银行卡绑定失败");
		
		
		
		messageCodeMap.put("EB2C0085", "(收款方)银行卡不存在");
		messageCodeMap.put("EB2C0086", "(付款方)银行卡不存在");
		messageCodeMap.put("EB2C0087", "支付失败");
		messageCodeMap.put("EB2C0081", "手机验证码不正确");
		messageCodeMap.put("EB2C0082", "支付密码设置失败");
		
		messageCodeMap.put("EB2C0121", "用户暂时冻结（当天密码输入错误五次）");
		messageCodeMap.put("EB2C0122", "用户已登录");
		messageCodeMap.put("EB2C0123", "用户密码错误");
		messageCodeMap.put("EB2C0124", "用户未激活");
		
		
		messageCodeMap.put("EB2C0101", "短信发送不成功");
		messageCodeMap.put("EB2C0102", "已验证");
		messageCodeMap.put("EB2C0103", "手机格式不正确");
		messageCodeMap.put("EB2C0104", "原密码输入不正确");
		messageCodeMap.put("EB2C0105", "短信序号不存在");
		messageCodeMap.put("EB2C0106", "验证码输入错误");
		messageCodeMap.put("EB2C0107", "已经打款申请过且成功了");
		messageCodeMap.put("EB2C0108", "打款验证金额错误");
		messageCodeMap.put("EB2C0109", "已验证过了且成功");
		
		messageCodeMap.put("EB2C0110", "该用户未做打款申请");
		messageCodeMap.put("EB2C0111", "用户与订单号不匹配");
		messageCodeMap.put("EB2C0112", "打款申请未成功");
		messageCodeMap.put("EB2C0113", "已超过最大校验次数");
		messageCodeMap.put("EB2C0114", "银行卡未绑定");
		messageCodeMap.put("EB2C0116", "付款方用户不存在");
		messageCodeMap.put("EB2C0117", "收款方用户不存在");
		messageCodeMap.put("EB2C0118", "付款方用户状态异常");
		messageCodeMap.put("EB2C0119", "收款方用户状态异常");
		
		
		messageCodeMap.put("EB2C0120", "虚户转账失败");
		messageCodeMap.put("EB2C0121", "已激活");
		messageCodeMap.put("EB2C0122", "已申请");
		
		
		
	}

}
