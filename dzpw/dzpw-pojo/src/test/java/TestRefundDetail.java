import java.util.ArrayList;

import hg.dzpw.pojo.api.alipay.RefundDetailData;
import hg.dzpw.pojo.api.alipay.RefundDetailData.ChildTrade;
import hg.dzpw.pojo.api.alipay.RefundDetailData.DivideProfit;
import hg.dzpw.pojo.api.alipay.RefundDetailData.MainTrade;


public class TestRefundDetail {

	/** 
	 * @描述： 
	 * @author: guotx 
	 * @version: 2016-3-14 上午10:17:14 
	 */

	public static void main(String[] args) {
		RefundDetailData data=new RefundDetailData();
		MainTrade mainTrade=new MainTrade();
		mainTrade.setRefundAmount(123.34);
		mainTrade.setRefundReason("不想买了");
		mainTrade.setTradeNo("5748937254378258478");

		data.setMainTrade((mainTrade));
		
		DivideProfit divideProfit1=new DivideProfit();
		divideProfit1.setRefundAmount(84.43);
		divideProfit1.setRefundReason("颜色差");
		divideProfit1.setTradeInAccount("18856254136");
		divideProfit1.setTradeInPartnerId("20881567687489");
		divideProfit1.setTradeOutAccount("weilan@ply365.com");
		divideProfit1.setTradeOutPartnerId("2088152456124");
		
		ArrayList<DivideProfit> divideProfits=new ArrayList<RefundDetailData.DivideProfit>();
		divideProfits.add(divideProfit1);
		data.setDivideProfits(divideProfits);
		
		ChildTrade childTrade=new ChildTrade();
		childTrade.setRefundAmount(233.3);
		childTrade.setRefundReason("子因素");
		
		data.setChildTrade(childTrade);
		
		System.out.println(data);
	}

}
