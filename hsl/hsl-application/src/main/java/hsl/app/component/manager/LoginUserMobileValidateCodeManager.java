package hsl.app.component.manager;

import com.alibaba.fastjson.JSON;
import hg.common.util.SMSUtils;
import hg.log.util.HgLogger;
import hsl.app.component.config.SysProperties;
import hsl.app.service.local.user.UserLocalService;
import hsl.domain.model.user.User;
import hsl.pojo.exception.ShowMessageException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 登录用户手机验证码认证管理
 *
 * @author zhurz
 * @since 1.6.3
 */
@Component
public class LoginUserMobileValidateCodeManager {

	public static final String WEB_LOGIN_USER_VALIDATE_INFO = "HSL:WEB_LOGIN_USER_VALIDATE_INFO:";

	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private SMSUtils smsUtils;
	@Autowired
	private UserLocalService userService;

	private String getKey(String sessionId) {
		return WEB_LOGIN_USER_VALIDATE_INFO + sessionId;
	}

	/**
	 * 错误数自增
	 *
	 * @param sessionId
	 * @param userId
	 * @param retryMax  最大重试次数，当其大于或等于时删除验证信息
	 * @return
	 */
	private int wrongCountAdd(String sessionId, int retryMax) {
		Jedis jedis = jedisPool.getResource();
		int wrongCount = 0;
		try {
			String key = getKey(sessionId);
			ValidateInfo validateInfo = ValidateInfo.valueOf(jedis.get(key));
			if (validateInfo != null) {
				validateInfo.wrongCountAdd();
				jedis.set(key, validateInfo.toString());
				jedis.expire(key, (int) TimeUnit.MINUTES.toSeconds(30));
				wrongCount = validateInfo.getWrongCount();
				if (wrongCount >= retryMax) {
					jedis.del(key);
				}
			}
			jedisPool.returnResource(jedis);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
		}
		return wrongCount;
	}

	/**
	 * 生成验证码
	 *
	 * @param sessionId
	 * @param userId
	 * @return
	 */
	private String generateValidateCode(String sessionId, String userId) {
		String validateCode = String.format("%06d", new Random().nextInt(1000000));
		Jedis jedis = jedisPool.getResource();
		try {
			String key = getKey(sessionId);
			ValidateInfo oldValidateInfo = ValidateInfo.valueOf(jedis.get(key));
			if (oldValidateInfo != null && DateUtils.addSeconds(oldValidateInfo.getCreateDate(), 60).compareTo(new Date()) > 0)
				throw new ShowMessageException("发送频繁");
			jedis.set(key, new ValidateInfo(validateCode, userId, sessionId).toString());
			jedis.expire(key, (int) TimeUnit.MINUTES.toSeconds(30));
			jedisPool.returnResource(jedis);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			if (e instanceof ShowMessageException)
				throw (ShowMessageException) e;
		}
		return validateCode;
	}

	/**
	 * 发送用户登录认证验证码
	 *
	 * @param sessionId 会话ID
	 * @param userId    用户ID
	 * @return 验证码
	 */
	public String sendValidateCode(String sessionId, String userId) {

		if (StringUtils.isBlank(userId))
			throw new ShowMessageException("用户不存在");

		User user = userService.get(userId);

		if (user == null)
			throw new ShowMessageException("用户不存在");
		/* 由于这个字段的值在正常操作中，只在注册流程设置，其他环节不会改变，所以先注释掉。
		if (!user.getStatus().getIsTelChecked())
			throw new ShowMessageException("用户手机未认证");
		*/

		String toMobile = user.getContactInfo().getMobile();
		String smsSign = SysProperties.getInstance().get("sms_sign", "票量旅游");
		String validateCode = generateValidateCode(sessionId, userId);
		String message = String.format("【%s】您当前的登录认证验证码为%s。", smsSign, validateCode);

		try {
			HgLogger.getInstance().info("zhurz", String.format("发送短信->%s->%s", toMobile, message));
			smsUtils.sendSms(toMobile, message);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return validateCode;
	}

	/**
	 * 检查验证码是否一致
	 *
	 * @param sessionId 会话ID
	 * @param userId    用户ID
	 * @param code      用户输入的验证码
	 * @return 为true时为验证通过
	 */
	public boolean checkValidateCode(String sessionId, String userId, String code) {
		Jedis jedis = jedisPool.getResource();
		boolean valida = false;
		try {
			String key = getKey(sessionId);
			ValidateInfo validateInfo = ValidateInfo.valueOf(jedis.get(key));
			if (validateInfo != null) {
				if (StringUtils.equals(validateInfo.getValidateCode(), code) && StringUtils.equals(validateInfo.getUserId(), userId)) {
					valida = true;
					jedis.del(key);
				} else {
					wrongCountAdd(sessionId, 3);
				}
			}
			jedisPool.returnResource(jedis);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
		}
		return valida;
	}

	public static void main(String[] args) {
		System.out.println(new ValidateInfo("111", "222", "333"));
	}

	/**
	 * 验证码信息
	 */
	public static class ValidateInfo {
		/**
		 * 验证码
		 */
		private String validateCode;
		/**
		 * 用户ID
		 */
		private String userId;
		/**
		 * 会话ID
		 */
		private String sessionId;
		/**
		 * 错误次数
		 */
		private int wrongCount;
		/**
		 * 创建时间
		 */
		private Date createDate;

		public ValidateInfo() {
			this(null, null, null);
		}

		public ValidateInfo(String validateCode, String userId, String sessionId) {
			this.validateCode = validateCode;
			this.userId = userId;
			this.sessionId = sessionId;
			this.createDate = new Date();
		}

		@Override
		public String toString() {
			return JSON.toJSONString(this);
		}

		public static ValidateInfo valueOf(String json) {
			try {
				return JSON.parseObject(json, ValidateInfo.class);
			} catch (Exception e) {
				return null;
			}
		}

		public void wrongCountAdd() {
			this.wrongCount++;
		}

		public String getValidateCode() {
			return validateCode;
		}

		public void setValidateCode(String validateCode) {
			this.validateCode = validateCode;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getSessionId() {
			return sessionId;
		}

		public void setSessionId(String sessionId) {
			this.sessionId = sessionId;
		}

		public int getWrongCount() {
			return wrongCount;
		}

		public void setWrongCount(int wrongCount) {
			this.wrongCount = wrongCount;
		}

		public Date getCreateDate() {
			return createDate;
		}

		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}
	}
}
