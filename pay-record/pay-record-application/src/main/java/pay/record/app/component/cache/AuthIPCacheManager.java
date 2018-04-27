package pay.record.app.component.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.redisson.Redisson;
import org.redisson.core.RMap;
import org.springframework.stereotype.Component;

import pay.record.domain.model.authip.AuthIP;
import pay.record.pojo.system.AuthIPConstants;

/**
 * @类功能说明：经销商缓存管理
 * @类修改者：
 * @修改日期：2015-7-14下午3:53:10
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-14下午3:53:10
 */
@Component
public class AuthIPCacheManager {

	@Resource
	private Redisson redisson;

	public static final String PAY_RECORD_AUTHIP_MAP = "PAY_RECORD:AUTHIP_MAP";

	/**
	 * 
	 * @方法功能说明：刷新授权IP缓存
	 * @修改者名字：yuqz
	 * @修改时间：2015年11月27日下午4:13:31
	 * @修改内容：
	 * @参数：@param dealers
	 * @return:void
	 * @throws
	 */
	public void reflushAuthIPMap(List<AuthIP> AuthIPs) {
		RMap<String, AuthIP> map = redisson.getMap(PAY_RECORD_AUTHIP_MAP);
		Map<String, AuthIP> authIPMap = new HashMap<String, AuthIP>();
		for (AuthIP authIP : AuthIPs)
			if (StringUtils.equals(AuthIPConstants.AUTH_SUCC, authIP.getStatus()))
				authIPMap.put(authIP.getFromProjectIP(), authIP);
		map.clear();
		map.putAll(authIPMap);
	}
	
	/**
	 * 
	 * @方法功能说明：获取授权IP
	 * @修改者名字：yuqz
	 * @修改时间：2015年11月27日下午4:56:05
	 * @修改内容：
	 * @参数：@param code
	 * @参数：@return
	 * @return:Dealer
	 * @throws
	 */
	public AuthIP getAuthIP(String fromProjectIP) {
		if (fromProjectIP == null)
			return null;
		RMap<String, AuthIP> map = redisson.getMap(PAY_RECORD_AUTHIP_MAP);
		return map.get(fromProjectIP);
	}
	
}
