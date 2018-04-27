package hsl.spi.action;
import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;
import hsl.api.base.ApiRequest;
import hsl.api.v1.response.jp.JPCreateOrderResponse;
import hsl.app.common.util.ClientKeyUtil;
import hsl.pojo.command.JPOrderCreateCommand;
import hsl.pojo.dto.jp.JPOrderCreateDTO;
import hsl.spi.inter.api.jp.JPService;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
@Component("jpOrderCreateAction")
public class JPOrderCreateAction implements HSLAction {
	@Resource
	private JPService jpService;
	
	@Resource
	private HgLogger hgLogger;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		JPOrderCreateCommand jpOrderCreateCommand = JSON.parseObject(apiRequest.getBody().getPayload(), JPOrderCreateCommand.class);
		//设置那个平台 add by qiuxianxiang
		jpOrderCreateCommand.setFromClientKey(ClientKeyUtil.FROM_CLIENT_KEY);
		HgLogger.getInstance().info("zhangka", "JPOrderCreateAction->execute->jpOrderCreateCommand[创建订单]:" + JSON.toJSONString(jpOrderCreateCommand));
		
		JPOrderCreateDTO jpOrderCreateDTO = jpService.orderCreate(jpOrderCreateCommand);
		
		JPCreateOrderResponse response = null;
		
		if (jpOrderCreateDTO == null) {
			response = new JPCreateOrderResponse();
			response.setMessage("失败！");
			response.setResult(JPCreateOrderResponse.RESULT_CODE_FAIL);
		} else {
			response = BeanMapperUtils.getMapper().map(jpOrderCreateDTO, JPCreateOrderResponse.class);
			response.setMessage("成功");
			response.setResult(JPCreateOrderResponse.RESULT_CODE_OK);
		}
		
		HgLogger.getInstance().info("zhangka", "JPOrderCreateAction->execute->response[创建订单]:" +JSON.toJSONString(response.getResult()));
		
		return JSON.toJSONString(response);
	}

}
