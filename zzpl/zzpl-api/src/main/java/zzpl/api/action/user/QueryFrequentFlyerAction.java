package zzpl.api.action.user;

import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zzpl.api.action.CommonAction;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.dto.user.FrequentFlyerDTO;
import zzpl.api.client.request.user.QueryFrequentFlyerQO;
import zzpl.api.client.response.user.QueryFrequentFlyerResponse;
import zzpl.app.service.local.user.FrequentFlyerService;
import zzpl.domain.model.user.FrequentFlyer;
import zzpl.pojo.qo.user.FrequentFlyerQO;

import com.alibaba.fastjson.JSON;
/**
 * 
 * @类功能说明：查询常旅
 * @类修改者：
 * @修改日期：2015年9月21日上午10:44:00
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年9月21日上午10:44:00
 */
@Component("QueryFrequentFlyerAction")
public class QueryFrequentFlyerAction implements CommonAction{

	@Autowired
	private FrequentFlyerService frequentFlyerService;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		QueryFrequentFlyerResponse queryFrequentFlyerResponse = new QueryFrequentFlyerResponse();
		try{
			QueryFrequentFlyerQO queryFrequentFlyerQO = JSON.parseObject(apiRequest.getBody().getPayload(),QueryFrequentFlyerQO.class);
			HgLogger.getInstance().info("cs", "【QueryFrequentFlyerAction】"+"queryFrequentFlyerQO:"+JSON.toJSONString(queryFrequentFlyerQO));
			FrequentFlyerQO frequentFlyerQO = BeanMapperUtils.getMapper().map(queryFrequentFlyerQO, FrequentFlyerQO.class);
			List<FrequentFlyer> frequentFlyers = frequentFlyerService.queryList(frequentFlyerQO);
			HgLogger.getInstance().info("cs", "【QueryFrequentFlyerAction】"+"frequentFlyers:"+JSON.toJSONString(frequentFlyers));
			List<FrequentFlyerDTO> frequentFlyerDTOs = BeanMapperUtils.getMapper().mapAsList(frequentFlyers, FrequentFlyerDTO.class);
			queryFrequentFlyerResponse.setFrequentFlyerDTOs(frequentFlyerDTOs);
			queryFrequentFlyerResponse.setMessage("查询成功");
			queryFrequentFlyerResponse.setResult(ApiResponse.RESULT_CODE_OK);
		}catch(Exception e){
			HgLogger.getInstance().info("cs", "【QueryFrequentFlyerAction】"+"异常，"+HgLogger.getStackTrace(e));
			queryFrequentFlyerResponse.setMessage("查询错误");
			queryFrequentFlyerResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
		}
		HgLogger.getInstance().info("cs", "【QueryFrequentFlyerAction】"+"queryFrequentFlyerResponse:"+JSON.toJSONString(queryFrequentFlyerResponse));
		return JSON.toJSONString(queryFrequentFlyerResponse);
	}

}
