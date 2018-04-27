package hg.hjf.app.service.sys;

import org.springframework.stereotype.Service;

/**
 * 为外部单位代发短信的服务。可能产生财务结算
 * @author  
 *
 */
@Service
public class ProxySmsService {
	/**
	 * 操作会被系统统一的日志服务保存到表jf_operation_log
	 */
	public void sendSmsEvent(String domainCode,String phone,String Action){
	}
}
