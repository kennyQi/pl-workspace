package plfx.mp.tcclient.tc.service;

import plfx.mp.tcclient.tc.dto.Dto;
import plfx.mp.tcclient.tc.pojo.Response;

/**
 * 
 * @author zhangqy
 * 
 */
public interface ConnectService {
	@SuppressWarnings("rawtypes")
	public Response getResponseByRequest(Dto dto);
}
