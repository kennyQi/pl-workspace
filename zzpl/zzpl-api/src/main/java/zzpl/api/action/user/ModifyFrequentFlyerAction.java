package zzpl.api.action.user;

import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zzpl.api.action.CommonAction;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.dto.user.FrequentFlyerDTO;
import zzpl.api.client.request.user.ModifyFrequentFlyerCommand;
import zzpl.api.client.response.user.ModifyFrequentFlyerResponse;
import zzpl.app.service.local.user.FrequentFlyerService;
import zzpl.domain.model.user.FrequentFlyer;
import zzpl.pojo.qo.user.FrequentFlyerQO;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：修改常旅
 * @类修改者：
 * @修改日期：2015年9月21日上午10:44:23
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年9月21日上午10:44:23
 */
@Component("ModifyFrequentFlyerAction")
public class ModifyFrequentFlyerAction implements CommonAction{

	@Autowired
	private FrequentFlyerService frequentFlyerService;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		ModifyFrequentFlyerResponse modifyFrequentFlyerResponse = new ModifyFrequentFlyerResponse();
		try{
			ModifyFrequentFlyerCommand modifyFrequentFlyerCommand = JSON.parseObject(apiRequest.getBody().getPayload(),ModifyFrequentFlyerCommand.class);
			HgLogger.getInstance().info("cs", "【ModifyFrequentFlyerAction】"+"modifyFrequentFlyerCommand:"+JSON.toJSONString(modifyFrequentFlyerCommand));
			FrequentFlyer frequentFlyer = BeanMapperUtils.getMapper().map(modifyFrequentFlyerCommand.getFrequentFlyer(), FrequentFlyer.class);
			if(StringUtils.isNotBlank(modifyFrequentFlyerCommand.getFrequentFlyer().getId())||frequentFlyerService.get(modifyFrequentFlyerCommand.getFrequentFlyer().getId())==null){
				HgLogger.getInstance().info("cs", "【ModifyFrequentFlyerAction】"+"frequentFlyer:"+JSON.toJSONString(frequentFlyer));
				if(frequentFlyer.getIdNO()!=null&&StringUtils.isNotBlank(frequentFlyer.getIdNO())){
					FrequentFlyerQO frequentFlyerQO = new FrequentFlyerQO();
					frequentFlyerQO.setUserID(frequentFlyer.getUserID());
					frequentFlyerQO.setIdNO(frequentFlyer.getIdNO());
					if(frequentFlyerService.queryCount(frequentFlyerQO)==1&&!StringUtils.equals(frequentFlyerService.queryUnique(frequentFlyerQO).getId(),modifyFrequentFlyerCommand.getFrequentFlyer().getId())){
						//该游客当前人员已存在
						throw new Exception("aready exists");
					}
				}
				frequentFlyer.setId(modifyFrequentFlyerCommand.getFrequentFlyer().getId());
				frequentFlyerService.update(frequentFlyer);
				modifyFrequentFlyerResponse.setMessage("更新成功");
				modifyFrequentFlyerResponse.setResult(ApiResponse.RESULT_CODE_OK);
				frequentFlyer = frequentFlyerService.get(modifyFrequentFlyerCommand.getFrequentFlyer().getId());
				modifyFrequentFlyerResponse.setFrequentFlyerDTO(BeanMapperUtils.getMapper().map(frequentFlyer, FrequentFlyerDTO.class));
			}else{
				throw new Exception("wrong ID");
			}
		}catch(Exception e){
			HgLogger.getInstance().info("cs", "【ModifyFrequentFlyerAction】"+"异常，"+HgLogger.getStackTrace(e));
			if(StringUtils.equals(e.getMessage(), "wrong ID")){
				modifyFrequentFlyerResponse.setMessage("常用旅客ID错误");
				modifyFrequentFlyerResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
			}else if(StringUtils.equals(e.getMessage(), "aready exists")){
				modifyFrequentFlyerResponse.setMessage("该旅客已存在");
				modifyFrequentFlyerResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
			}{
				modifyFrequentFlyerResponse.setMessage("修改失败");
				modifyFrequentFlyerResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
			}
		}
		HgLogger.getInstance().info("cs", "【ModifyFrequentFlyerAction】"+"modifyFrequentFlyerResponse:"+JSON.toJSONString(modifyFrequentFlyerResponse));
		return JSON.toJSONString(modifyFrequentFlyerResponse);
	}

}
