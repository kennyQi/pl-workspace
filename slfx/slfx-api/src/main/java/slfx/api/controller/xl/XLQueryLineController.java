package slfx.api.controller.xl;

import hg.log.util.HgLogger;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import slfx.api.base.ApiRequest;
import slfx.api.base.ApiResponse;
import slfx.api.controller.base.SLFXAction;
import slfx.api.v1.request.qo.xl.XLLineApiQO;
import slfx.api.v1.response.xl.XLQueryLineResponse;
import slfx.xl.pojo.dto.line.LineDTO;
import slfx.xl.pojo.qo.LineQO;
import slfx.xl.spi.inter.LineImageService;
import slfx.xl.spi.inter.LineService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类功能说明：api接口查询线路信息Controller
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月23日上午10:41:06
 * @版本：V1.0
 *
 */
@Component("xlQueryLineController")
public class XLQueryLineController implements SLFXAction {
	
	@Autowired
	private LineService lineService;
	@Autowired
	private LineImageService lineImageService;

	@Override
	public String execute(ApiRequest apiRequest) {
		String fromClientKey = apiRequest.getHead().getFromClientKey();
		String clientUserKey = apiRequest.getHead().getClientUserKey();
		JSONObject jsonObject = JSON.parseObject(apiRequest.getBody().getPayload());
		jsonObject.put("fromClientKey", fromClientKey);
		jsonObject.put("clientUserKey", clientUserKey);
		XLLineApiQO qo = JSON.toJavaObject(jsonObject, XLLineApiQO.class);
		
		return xlQueryLine(qo);
	}
	
	/**
	 * 
	 * @方法功能说明：查询线路
	 * @修改者名字：tandeng
	 * @修改时间：2014年12月23日上午10:44:08
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	private String xlQueryLine(XLLineApiQO apiQO){
		HgLogger.getInstance().info("tandeng","XLQueryLineController->[线路查询开始]:"+ JSON.toJSONString(apiQO));
		
		List<LineDTO> lineList = null;
		Integer count = 0;
		try{
			//"apiQO"转换"分销QO"
			LineQO lineQO = new LineQO();
			BeanUtils.copyProperties(apiQO,lineQO);
			lineList = lineService.shopQueryLineList(lineQO);
			count = lineService.shopQueryLineTotalCount(lineQO);
		}catch(Exception e){
			HgLogger.getInstance().error("tandeng","XLQueryLineController->[线路查询异常]:"+HgLogger.getStackTrace(e));
		}
		
		XLQueryLineResponse xlQueryLineResponse = new XLQueryLineResponse();
		
		if(lineList != null && lineList.size() > 0){
			xlQueryLineResponse.setMessage("成功");
			xlQueryLineResponse.setResult(ApiResponse.RESULT_CODE_OK);
			xlQueryLineResponse.setLineList(lineList);
			xlQueryLineResponse.setTotalCount(count);
		}else{
			xlQueryLineResponse.setMessage("失败");
			xlQueryLineResponse.setResult(ApiResponse.RESULT_CODE_FAILE);
			xlQueryLineResponse.setLineList(null);
			xlQueryLineResponse.setTotalCount(null);
		}
		
		HgLogger.getInstance().info("tandeng","XLQueryLineController->[线路查询结束]:"+ JSON.toJSONString(xlQueryLineResponse));
		return JSON.toJSONString(xlQueryLineResponse);
	}

}
