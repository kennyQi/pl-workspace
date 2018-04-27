package zzpl.api.action.user;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hg.common.util.BeanMapperUtils;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;
import zzpl.api.action.CommonAction;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.dto.user.FrequentFlyerDTO;
import zzpl.api.client.request.user.AddFrequentFlyerCommand;
import zzpl.api.client.response.user.AddFrequentFlyerResponse;
import zzpl.app.service.local.user.FrequentFlyerService;
import zzpl.domain.model.user.FrequentFlyer;
import zzpl.pojo.qo.user.FrequentFlyerQO;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：添加常旅
 * @类修改者：
 * @修改日期：2015年9月21日上午10:43:49
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年9月21日上午10:43:49
 */
@Component("AddFrequentFlyerAction")
public class AddFrequentFlyerAction implements CommonAction{

	@Autowired
	private FrequentFlyerService frequentFlyerService; 
	
	@Override
	public String execute(ApiRequest apiRequest) {
		AddFrequentFlyerResponse addFrequentFlyerResponse = new AddFrequentFlyerResponse();
		try{
			AddFrequentFlyerCommand addFrequentFlyerCommand = JSON.parseObject(apiRequest.getBody().getPayload(), AddFrequentFlyerCommand.class);
			HgLogger.getInstance().info("cs", "【AddFrequentFlyerAction】"+"addFrequentFlyerCommand:"+JSON.toJSONString(addFrequentFlyerCommand));
			FrequentFlyer frequentFlyer = BeanMapperUtils.getMapper().map(addFrequentFlyerCommand.getFrequentFlyer(), FrequentFlyer.class);
			FrequentFlyerQO frequentFlyerQO = new FrequentFlyerQO();
			frequentFlyerQO.setUserID(frequentFlyer.getUserID());
			frequentFlyerQO.setIdNO(frequentFlyer.getIdNO());
			if(frequentFlyerService.queryCount(frequentFlyerQO)==0){
				//该游客当前人员不存在
				frequentFlyer.setId(UUIDGenerator.getUUID());
				HgLogger.getInstance().info("cs", "【AddFrequentFlyerAction】"+"frequentFlyer:"+JSON.toJSONString(frequentFlyer));
				frequentFlyer.setCreateTime(new Date());
				frequentFlyerService.save(frequentFlyer);
				addFrequentFlyerResponse.setMessage("添加成功");
				addFrequentFlyerResponse.setResult(ApiResponse.RESULT_CODE_OK);
				addFrequentFlyerResponse.setFrequentFlyerDTO(BeanMapperUtils.getMapper().map(frequentFlyer, FrequentFlyerDTO.class));
			}else{
				//该游客当前人员已存在
				throw new Exception("aready exists");
			}
		}catch(Exception e){
			HgLogger.getInstance().info("cs", "【AddFrequentFlyerAction】"+"异常，"+HgLogger.getStackTrace(e));
			if(StringUtils.equals(e.getMessage(), "aready exists")){
				addFrequentFlyerResponse.setMessage("该旅客已存在");
				addFrequentFlyerResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
			}else{
				addFrequentFlyerResponse.setMessage("添加失败");
				addFrequentFlyerResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
			}
		}
		HgLogger.getInstance().info("cs", "【AddFrequentFlyerAction】"+"addFrequentFlyerResponse:"+JSON.toJSONString(addFrequentFlyerResponse));
		return JSON.toJSONString(addFrequentFlyerResponse);
	}

}
