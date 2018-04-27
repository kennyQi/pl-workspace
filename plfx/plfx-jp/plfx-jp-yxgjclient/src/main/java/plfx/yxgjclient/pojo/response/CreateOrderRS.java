package plfx.yxgjclient.pojo.response;

import plfx.yxgjclient.pojo.param.CreateOrderResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 生成订单createOrder结果实体
 * @author guotx
 * 2015-06-30
 */
@XStreamAlias("createOrderRS")
public class CreateOrderRS extends BaseResult{
	private CreateOrderResult createOrderResult;

	public CreateOrderResult getCreateOrderResult() {
		return createOrderResult;
	}

	public void setCreateOrderResult(CreateOrderResult createOrderResult) {
		this.createOrderResult = createOrderResult;
	}

}
