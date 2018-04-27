package plfx.mp.tcclient.tc.pojo;

import plfx.mp.tcclient.tc.dto.Dto;
import plfx.mp.tcclient.tc.util.ReadProperties;

/**
 * 参数抽象父类，定义基本调用信息
 * @author zhangqy
 *
 */
public class  Param {
	/**
	 * 客户端IP
	 */
	private String clientIp;
	
	
	public Param() {
		super();
		this.setClientIp(ReadProperties.getClient_id());
	}
	
	public void setParams(Param param,Dto dto) throws Exception{
	}
	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
}
