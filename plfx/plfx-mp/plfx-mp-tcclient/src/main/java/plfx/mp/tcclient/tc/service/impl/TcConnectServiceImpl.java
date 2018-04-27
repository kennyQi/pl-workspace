package plfx.mp.tcclient.tc.service.impl;

import hg.log.util.HgLogger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import plfx.mp.tcclient.tc.dto.Dto;
import plfx.mp.tcclient.tc.dto.ExcepDto;
import plfx.mp.tcclient.tc.exception.DtoErrorException;
import plfx.mp.tcclient.tc.exception.KeylessException;
import plfx.mp.tcclient.tc.pojo.Param;
import plfx.mp.tcclient.tc.pojo.Request;
import plfx.mp.tcclient.tc.pojo.RequestHead;
import plfx.mp.tcclient.tc.pojo.Response;
import plfx.mp.tcclient.tc.pojo.ResponseHead;
import plfx.mp.tcclient.tc.pojo.Result;
import plfx.mp.tcclient.tc.service.ConnectService;
import plfx.mp.tcclient.tc.util.TcClientUtil;
import plfx.mp.tcclient.tc.util.XmlUtil;

@Component
public class TcConnectServiceImpl implements ConnectService {
	
//	private static final Logger logger = LoggerFactory.getLogger(TcConnectServiceImpl.class);
	private String devName = "zhangqy";
	private String tag = "同程接口";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Response getResponseByRequest(Dto dto) {
		// 组装请求

		Response response = null;
		RequestHead head = null;
		ResponseHead rhead = null;
		Result result = null;
		String resultStr = dto.getResult();
		try {
			result = (Result) Class.forName(resultStr).newInstance();
		} catch (Exception e) {
			response = new Response();
			// Result result=(ResultSceneryList)
			// ResultSceneryList.getResult("111111", e.getMessage(), null, null,
			// null, null, null, null);
			result = new Result();
			result.setResult(result, new ExcepDto("111111", e.getMessage()));
			response.setResponse(null, result);
			HgLogger.getInstance().error(devName, tag+e.getMessage());	
			e.printStackTrace();

		}
		if (response != null) {
			return response;
		}
		// 创建头部信息
		try {
			head = RequestHead.getRequestHead(dto.getServiceName());
		} catch (KeylessException e) {
			response = new Response();
			result.setResult(result, new ExcepDto(e.getCode(), e.getMessage()));
			response.setResponse(null, result);
			HgLogger.getInstance().error(devName, tag+e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			response = new Response();
			result.setResult(result, new ExcepDto("111111", e.getMessage()));
			response.setResponse(null, result);
			HgLogger.getInstance().error(devName, tag+e.getMessage());
			e.printStackTrace();
		}
		if (response != null) {
			return response;
		}
		// 创建请求体信息
		String paramStr = dto.getParam();
		Param c = null;
		try {
			c = (Param) Class.forName(paramStr).newInstance();
			c.setParams(c, dto);
		} catch (DtoErrorException e) {
			response = new Response();
			result.setResult(result, new ExcepDto(e.getCode(), e.getMessage()));
			response.setResponse(null, result);
			// logger.error(e.getMessage());
			HgLogger.getInstance().error(devName, tag+e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			response = new Response();
			result.setResult(result, new ExcepDto("111111", e.getMessage()));
			response.setResponse(null, result);
			// logger.error(e.getMessage());
			HgLogger.getInstance().error(devName, tag+e.getMessage());
			e.printStackTrace();
		}
		if (response != null) {
			return response;
		}
		Request request = new Request(head, c);
		// 获取对应的请求xml
		String reqxml = XmlUtil.generateXmlString(request);
		//System.out.println(reqxml);
		HgLogger.getInstance().info(devName, tag+reqxml);
		// 调用请求
		String retdata = null;
		try {
			retdata = TcClientUtil.callUrl(dto.getUrl(), reqxml);
			HgLogger.getInstance().info(devName, tag+retdata);
		} catch (Exception e) {
			e.printStackTrace();
			response = new Response();
			result.setResult(result, new ExcepDto("111111", e.getMessage()));
			response.setResponse(null, result);
			// logger.error(e.getMessage());
			HgLogger.getInstance().error(devName, tag+e.getMessage());
		}
		if (response != null) {
			return response;
		}
		// 解析对应的xml
		rhead = new ResponseHead();
		XmlUtil.analyzeXmlString(result, rhead, retdata, dto.isUnlist());
		response = new Response();
		response.setResponse(rhead, result);
		return response;

	}

}
