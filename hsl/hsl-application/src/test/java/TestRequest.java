import com.alibaba.fastjson.JSONObject;
import hg.common.model.HttpResponse;
import hg.common.util.HttpRequester;
import hg.common.util.HttpUtil;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author zhurz
 */
public class TestRequest {

	String notifyUrl = "http://127.0.0.1:8081/hsl-admin/gate/yxjp/notify";

	/**
	 * 测试请求出票失败
	 */
	@Test
	public void testRequestOutTicketFailure() throws UnsupportedEncodingException {

//		参数	中文含义	类型	长度	备注
//		dealerOrderCode	经销商订单号	String	64
//		passengerName	乘客姓名	String	500
//		refuseMemo	拒绝理由	String	100
//		type	通知类型	Integer	1	6：拒绝出票通知

//		BC18111951100004
//		呵呵 李大鹏

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("dealerOrderCode", "BC21140528100003");
		jsonObject.put("passengerName", "哈利波特");
		jsonObject.put("refuseMemo", "模拟拒绝出票");
		jsonObject.put("type", 6);


		HttpResponse res = HttpUtil.reqForPost(notifyUrl, "msg=" + URLEncoder.encode(jsonObject.toJSONString(), "utf-8"), 6000);

		System.out.println(res.getResult());
	}

	/**
	 * 测试请求出票成功
	 */
	@Test
	public void testRequestOutTicketSuccess() throws UnsupportedEncodingException {

//		notifyUrl会接收到的返回参数如下:
//		参数	中文含义	类型	长度	备注
//		dealerOrderCode	经销商订单号	String	64
//		passengerName	乘客姓名	String	500	多个乘客之间用 ^ 分隔
//		airId	票号	String	500	票号之间用 ^分隔，并与姓名相对应
//		type	通知类型	String	1	1：出票成功通知
//		newPnr	新pnr	String	40	换编码出票后的pnr，如没有，则为空
//		BC18111346100003
//		呵呵 李大鹏

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("dealerOrderCode", "BC21141756100004");
		jsonObject.put("passengerName", "李逍遥");
		jsonObject.put("airId", "TEST0001^TEST0002");
		jsonObject.put("newPnr", "666666");
		jsonObject.put("type", 1);

		HttpResponse res = HttpUtil.reqForPost(notifyUrl, "msg=" + URLEncoder.encode(jsonObject.toJSONString(), "utf-8"), 6000);

		System.out.println(res.getResult());
	}

	/**
	 * 测试退废票失败
	 *
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testRequestRefundFailure() throws UnsupportedEncodingException {
//		1.2.4退废票成功通知
//				退票成功同时会直接退款
//		notifyUrl会接收到的返回参数如下:
//		参数	中文含义	类型	长度	备注
//		dealerOrderCode	经销商订单号	String	64
//		airId	机票票号	String	500	票号之间用 ^分隔
//		refundPrice	实退金额	Double		支付方实际退款金额
//		refundStatus	退票状态	Integer	1	1—成功 2—拒绝退废票
//		refuseMemo	拒绝退票理由	String	100
//		procedures	退款手续费	Double		退款时的手续费
//		type	通知类型	String	1	4：退废票通知

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("dealerOrderCode", "BC21141756100004");
		jsonObject.put("airId", "TEST0001");
		jsonObject.put("refundStatus", 2);
		jsonObject.put("refuseMemo", "不行不行");
		jsonObject.put("type", 4);

		HttpResponse res = HttpUtil.reqForPost(notifyUrl, "msg=" + URLEncoder.encode(jsonObject.toJSONString(), "utf-8"), 6000);

		System.out.println(res.getResult());
	}

	/**
	 * 测试退废票成功
	 *
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testRequestRefundSuccess() throws UnsupportedEncodingException {
//		1.2.4退废票成功通知
//				退票成功同时会直接退款
//		notifyUrl会接收到的返回参数如下:
//		参数	中文含义	类型	长度	备注
//		dealerOrderCode	经销商订单号	String	64
//		airId	机票票号	String	500	票号之间用 ^分隔
//		refundPrice	实退金额	Double		支付方实际退款金额
//		refundStatus	退票状态	Integer	1	1—成功 2—拒绝退废票
//		refuseMemo	拒绝退票理由	String	100
//		procedures	退款手续费	Double		退款时的手续费
//		type	通知类型	String	1	4：退废票通知

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("dealerOrderCode", "BC21141756100004");
		jsonObject.put("airId", "TEST0001");
		jsonObject.put("refundStatus", 1);
		jsonObject.put("procedures", 0d);
		jsonObject.put("refundPrice", 1001d);
		jsonObject.put("type", 4);

		HttpResponse res = HttpUtil.reqForPost(notifyUrl, "msg=" + URLEncoder.encode(jsonObject.toJSONString(), "utf-8"), 6000);

		System.out.println(res.getResult());
	}

	/**
	 * 测试分销通知退废票申请
	 *
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testRequestRefundApply() throws UnsupportedEncodingException {
//		参数	中文含义	类型	长度	备注
//		dealerOrderCode	经销商订单号	String	64
//		passengerName	乘客姓名	String	500	多个乘客之间用 ^ 分隔
//		airId	票号	String	500	票号之间用 ^分隔，并与姓名相对应
//		refundType	申请种类	String	20	1.当日作废
//		2.自愿退票
//		3.非自愿退票
//		4.差错退款
//		5.其他
//		refundMemo	申请理由	text		注：c#用户该项如有字符尽量使用中文字符(如！；（），不用!;())
//		type	通知类型	String	1	5：分销申请退废票通知

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("dealerOrderCode", "BC18111346100003");
		jsonObject.put("passengerName", "李大鹏");
		jsonObject.put("airId", "TEST0002");
		jsonObject.put("refundType", 2);
		jsonObject.put("refundMemo", "分销退票");
		jsonObject.put("type", "5");

		HttpResponse res = HttpUtil.reqForPost(notifyUrl, "msg=" + URLEncoder.encode(jsonObject.toJSONString(), "utf-8"), 6000);

		System.out.println(res.getResult());
	}
}
