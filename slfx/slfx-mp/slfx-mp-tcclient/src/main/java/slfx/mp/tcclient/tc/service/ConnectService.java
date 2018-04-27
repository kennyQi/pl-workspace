package slfx.mp.tcclient.tc.service;

import slfx.mp.tcclient.tc.dto.Dto;
import slfx.mp.tcclient.tc.pojo.Response;

/**
 * 
 * @author zhangqy
 * 
 */
public interface ConnectService {
	@SuppressWarnings("rawtypes")
	public Response getResponseByRequest(Dto dto);
}
