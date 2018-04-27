package hsl.web.controller.safe;

import hsl.web.util.NetworkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 安全相关
 *
 * @author zhurz
 * @since 1.7.2
 */
@Controller
public class SafeController {

	@Autowired
	private JedisPool jedisPool;

	public static final String SEND_SMS_CODE_IP = "PLZX:SEND_SMS_IP";

	/**
	 * 用来记录恶意调用发送短信接口的来源IP
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/comRegister/sendCode")
	public void sendCode(HttpServletRequest request, HttpServletResponse response) {
		int n = 0;
		String ip = NetworkUtil.getIpAddr(request);
		Jedis jedis = jedisPool.getResource();
		while (true) {
			try {
				jedis.hincrBy(SEND_SMS_CODE_IP, ip, 1);
				jedisPool.returnResource(jedis);
				break;
			} catch (Exception e) {
				jedisPool.returnBrokenResource(jedis);
				if (++n > 3)
					break;
				jedis = jedisPool.getResource();
			}
		}
	}
}
