package plfx.yxgjclient.pojo.request;

import plfx.yxgjclient.pojo.param.CreateOrderParams;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 生成订单createOrder查询实体
 * 
 * @author guotx 2015-06-30
 */
@XStreamAlias("createOrderRQ")
public class CreateOrderRQ {
	public CreateOrderRQ() {
		createOrderParams = new CreateOrderParams();
	}

	private String sign;
	private CreateOrderParams createOrderParams;

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public CreateOrderParams getCreateOrderParams() {
		return createOrderParams;
	}

	public void setCreateOrderParams(CreateOrderParams createOrderParams) {
		this.createOrderParams = createOrderParams;
	}

}
