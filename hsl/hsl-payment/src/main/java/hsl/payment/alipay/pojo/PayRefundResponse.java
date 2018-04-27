package hsl.payment.alipay.pojo;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import java.util.List;

/**
 * 支付退款响应
 *
 * @author zhurz
 * @since 1.7
 */
public class PayRefundResponse {

	/**
	 * 是否成功
	 */
	public boolean success;

	/**
	 * 错误代码
	 */
	private String error;

	/**
	 * 原始响应XML
	 */
	private transient String responseXml;

	public PayRefundResponse() {
	}

	@SuppressWarnings("unchecked")
	public PayRefundResponse(String resultXml) throws DocumentException {

		setResponseXml(resultXml);

		Document doc = DocumentHelper.parseText(resultXml);

		List<Node> nodeList = doc.selectNodes("//alipay/*");

		for (Node node : nodeList) {
			// 判断是否有成功标示
			if (node.getName().equals("is_success") && node.getText().equals("T")) {
				setSuccess(true);
			}
			// 判断是否有错误信息
			else if (node.getName().equals("error")) {
				setError(node.getText());
			}
		}
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getResponseXml() {
		return responseXml;
	}

	public void setResponseXml(String responseXml) {
		this.responseXml = responseXml;
	}
}
