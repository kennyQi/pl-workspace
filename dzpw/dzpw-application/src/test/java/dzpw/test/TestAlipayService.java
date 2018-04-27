package dzpw.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import hg.common.util.DateUtil;
import hg.common.util.UUIDGenerator;
import hg.dzpw.app.common.AlipayConfig;
import hg.dzpw.app.common.util.alipay.AlipayCore;
import hg.dzpw.app.service.api.alipay.AliPayCaeChargeService;
import hg.dzpw.app.service.api.alipay.AliPayRefundFastService;
import hg.dzpw.pojo.api.alipay.CaeChargeParameter;
import hg.dzpw.pojo.api.alipay.CaeChargeResponse;
import hg.dzpw.pojo.api.alipay.RefundDetailData;
import hg.dzpw.pojo.api.alipay.RefundFastParameter;
import hg.dzpw.pojo.api.alipay.RefundFastResponse;
import hg.dzpw.pojo.api.alipay.RefundDetailData.ChildTrade;
import hg.dzpw.pojo.api.alipay.RefundDetailData.DivideProfit;
import hg.dzpw.pojo.api.alipay.RefundDetailData.MainTrade;

public class TestAlipayService extends TestCase {
	AliPayCaeChargeService chargeService = new AliPayCaeChargeService();
	AliPayRefundFastService refundService = new AliPayRefundFastService();
	CaeChargeParameter caeChargeParameter = new CaeChargeParameter();
	
	RefundFastParameter refundFastParameter = new RefundFastParameter();

	@Override
	protected void setUp() throws Exception {
		caeChargeParameter.set_input_charset(AlipayConfig.input_charset);
		caeChargeParameter.setAmount("1.20");
		caeChargeParameter.setCharge_type("trade");
		caeChargeParameter.setGmt_out_order_create(DateUtil.formatDateTime(new Date()));
//		caeChargeParameter.setNotify_url("http://115.238.43.242:9123/dzpw-dealer-api/caeNotify");
//		caeChargeParameter.setOperator_id("");
//		caeChargeParameter.setOperator_name("");
		caeChargeParameter.setOut_order_no("PLKJ_"+UUIDGenerator.getUUID());
		caeChargeParameter.setPartner(AlipayConfig.partner);
//		caeChargeParameter.setRoyalty_parameters("");
//		caeChargeParameter.setRoyalty_type("");
		caeChargeParameter.setService(AlipayConfig.CAE_CHARGE_SERVICE_NAME);
		caeChargeParameter.setSign_type(AlipayConfig.sign_type);
		caeChargeParameter.setSubject("票量科技测试_"+System.currentTimeMillis());
		caeChargeParameter.setTrans_account_in(AlipayConfig.partner+AlipayConfig.ACCOUNT_SUFFIX);
		caeChargeParameter.setTrans_account_out("payment@ply365.com");
		caeChargeParameter.setType_code(AlipayConfig.partner+AlipayConfig.TYPE_CODE_SUFFIX);
		//退款接口参数
		refundFastParameter.setService(AlipayConfig.REFUND_FAST_SERVICE_NAME);
		refundFastParameter.setPartner(AlipayConfig.partner);
		refundFastParameter.set_input_charset(AlipayConfig.input_charset);
		refundFastParameter.setSign_type(AlipayConfig.sign_type);
		refundFastParameter.setNotify_url("http://115.238.43.242:9123/dzpw-dealer-api/refundNotify");
		refundFastParameter.setBatch_no(AlipayCore.getRandomBatchNo());
		refundFastParameter.setRefund_date(DateUtil.formatDateTime(new Date()));
		refundFastParameter.setBatch_num("1");
		refundFastParameter.setDetail_data("2016030400001000620089403017^1.20^票量退款测试");
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
/**
	public void testHtmlService() {

		String htmlForm = service.buildFormHtml(caeChargeParameter, "post",
				"提交");
		System.out.println(htmlForm);
	}
	*/
	public void testChargeService(){
		CaeChargeResponse result=chargeService.chargeHttpRequest(caeChargeParameter);
		System.out.println(result.isSuccess());
	}
	
	public void testRefundFast(){
		List<RefundDetailData> datas=new ArrayList<RefundDetailData>();
		datas.add(getDetailData());
		RefundFastResponse response=refundService.refundFastRequest(refundFastParameter,datas);
		System.out.println(response.isSuccess());
		System.out.println(response.getMessage());
	}
	public void testRandom(){
		for (int i = 0; i < 10; i++) {
			System.out.println(AlipayCore.getRandomBatchNo());
		}
	}
	
	private RefundDetailData getDetailData(){
		RefundDetailData data=new RefundDetailData();
		MainTrade mainTrade=new MainTrade();
		mainTrade.setRefundAmount(1.20);
		mainTrade.setRefundReason("票量退款测试");
		mainTrade.setTradeNo("2016030400001000620089403666");

		data.setMainTrade((mainTrade));
//		
//		DivideProfit divideProfit1=new DivideProfit();
//		divideProfit1.setRefundAmount(84.43);
//		divideProfit1.setRefundReason("颜色差");
//		divideProfit1.setTradeInAccount("18856254136");
//		divideProfit1.setTradeInPartnerId("20881567687489");
//		divideProfit1.setTradeOutAccount("weilan@ply365.com");
//		divideProfit1.setTradeOutPartnerId("2088152456124");
//		
//		ArrayList<DivideProfit> divideProfits=new ArrayList<RefundDetailData.DivideProfit>();
//		divideProfits.add(divideProfit1);
//		data.setDivideProfits(divideProfits);
//		
//		ChildTrade childTrade=new RefundDetailData.ChildTrade();
//		childTrade.setRefundAmount(233.3);
//		childTrade.setRefundReason("子因素");
		
//		data.setChildTrade(childTrade);
		return data;
	}
}
