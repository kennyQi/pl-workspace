package zzpl.api.action.user;

import hg.log.util.HgLogger;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import zzpl.api.action.CommonAction;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.request.user.DeleteFrequentFlyerCommand;
import zzpl.api.client.response.user.DeleteFrequentFlyerResponse;
import zzpl.app.service.local.user.FrequentFlyerService;

/**
 * 
 * @类功能说明：常旅删除
 * @类修改者：
 * @修改日期：2015年9月21日上午10:43:25
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年9月21日上午10:43:25
 */
@Component("DeleteFrequentFlyerAction")
public class DeleteFrequentFlyerAction implements CommonAction{

	@Autowired
	private FrequentFlyerService frequentFlyerService;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		DeleteFrequentFlyerResponse deleteFrequentFlyerResponse = new DeleteFrequentFlyerResponse();
		try{
			DeleteFrequentFlyerCommand deleteFrequentFlyerCommand = JSON.parseObject(apiRequest.getBody().getPayload(),DeleteFrequentFlyerCommand.class);
			HgLogger.getInstance().info("cs", "【DeleteFrequentFlyerAction】"+"deleteFrequentFlyerCommand:"+JSON.toJSONString(deleteFrequentFlyerCommand));
			if(deleteFrequentFlyerCommand.getId()!=null&&StringUtils.isNotBlank(deleteFrequentFlyerCommand.getId())){
				frequentFlyerService.deleteById(deleteFrequentFlyerCommand.getId());
				deleteFrequentFlyerResponse.setMessage("删除成功");
				deleteFrequentFlyerResponse.setResult(ApiResponse.RESULT_CODE_OK);
				deleteFrequentFlyerResponse.setId(deleteFrequentFlyerCommand.getId());
			}else{
				throw new Exception("no ID");
			}
		}catch(Exception e){
			HgLogger.getInstance().info("cs", "【DeleteFrequentFlyerAction】"+"异常，"+HgLogger.getStackTrace(e));
			if(StringUtils.equals(e.getMessage(), "no ID")){
				deleteFrequentFlyerResponse.setMessage("常用旅客不存在");
				deleteFrequentFlyerResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
			}else{
				deleteFrequentFlyerResponse.setMessage("删除失败");
				deleteFrequentFlyerResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
			}
		}
		HgLogger.getInstance().info("cs", "【DeleteFrequentFlyerAction】"+"deleteFrequentFlyerResponse:"+JSON.toJSONString(deleteFrequentFlyerResponse));
		return JSON.toJSONString(deleteFrequentFlyerResponse);
	}

}
