package hg.dzpw.pojo.api.alipay;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @类功能说明：支付宝退款详细数据集,此为一笔交易，每笔之间用#隔开
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2016-3-14上午9:09:52
 * @版本：
 */
public class RefundDetailData {
	/** 主交易集合，每笔有且只有一个*/
	private MainTrade mainTrade;
	/** 分润交易集合，可空*/
	private List<DivideProfit> divideProfits;
	/** 子交易集合，可空*/
	private ChildTrade childTrade;
	
	public List<DivideProfit> getDivideProfits() {
		return divideProfits;
	}

	public void setDivideProfits(List<DivideProfit> divideProfits) {
		this.divideProfits = divideProfits;
	}

	@Override
	public String toString(){
		if (mainTrade==null) {
			return null;
		}
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append(mainTrade.tradeNo).append("^");
		stringBuffer.append(mainTrade.refundAmount).append("^");
		stringBuffer.append(mainTrade.refundReason).append("|");
		stringBuffer.deleteCharAt(stringBuffer.lastIndexOf("|"));
		
		if (divideProfits!=null && divideProfits.size()>0) {
			for (DivideProfit divideProfit : divideProfits) {
				stringBuffer.append("|").append(divideProfit.tradeOutAccount);
				stringBuffer.append("^").append(divideProfit.tradeOutPartnerId);
				stringBuffer.append("^").append(divideProfit.tradeInAccount);
				stringBuffer.append("^").append(divideProfit.tradeInPartnerId);
				stringBuffer.append("^").append(divideProfit.refundAmount);
				stringBuffer.append("^").append(divideProfit.refundReason);
			}
		}
		if (childTrade!=null) {
			stringBuffer.append("$$").append(childTrade.refundAmount).append(childTrade.refundReason);
		}
		return stringBuffer.toString();
	}

	public MainTrade getMainTrade() {
		return mainTrade;
	}

	public void setMainTrade(MainTrade mainTrade) {
		this.mainTrade = mainTrade;
	}

	public ChildTrade getChildTrade() {
		return childTrade;
	}

	public void setChildTrade(ChildTrade childTrade) {
		this.childTrade = childTrade;
	}

	/**
	 * 
	 * @类功能说明：交易类型，主交易退款数据
	 * @类修改者：
	 * @修改日期：
	 * @修改说明：
	 * @公司名称：浙江汇购科技有限公司
	 * @部门：技术部
	 * @作者：guotx
	 * @创建时间：2016-3-14上午9:12:09
	 * @版本：
	 */
	public static class MainTrade {
		/** 支付宝交易号 */
		private String tradeNo;
		/** 退款金额 */
		private double refundAmount;
		/** 退款理由 */
		private String refundReason;
		/** 处理结果码，只有在处理异步回调接口时才使用，请求参数中无此参数 */
		private String resultCode;

		public String getTradeNo() {
			return tradeNo;
		}

		public void setTradeNo(String tradeNo) {
			this.tradeNo = tradeNo;
		}

		public double getRefundAmount() {
			return refundAmount;
		}

		public void setRefundAmount(double refundAmount) {
			this.refundAmount = refundAmount;
		}

		public String getRefundReason() {
			return refundReason;
		}

		public void setRefundReason(String refundReason) {
			this.refundReason = refundReason;
		}

		public String getResultCode() {
			return resultCode;
		}

		public void setResultCode(String resultCode) {
			this.resultCode = resultCode;
		}

	}

	/**
	 * 
	 * @类功能说明：交易类型，分润退款数据
	 * @类修改者：
	 * @修改日期：
	 * @修改说明：
	 * @公司名称：浙江汇购科技有限公司
	 * @部门：技术部
	 * @作者：guotx
	 * @创建时间：2016-3-14上午9:14:59
	 * @版本：
	 */
	public static class DivideProfit {
		/** 转出支付宝账号，原收到分润金额的账户 */
		private String tradeOutAccount;
		/** 转出人支付宝账号对应用户ID，2088开头16位数字 */
		private String tradeOutPartnerId;

		/** 转入支付宝账号，原付出分润金额账户 */
		private String tradeInAccount;
		/** 转入人支付宝账号对应用户ID，2088开头16位数字 */
		private String tradeInPartnerId;
		/** 退款金额 */
		private double refundAmount;
		/** 退款理由 */
		private String refundReason;
		/** 处理结果码，只有在处理异步回调接口时才使用，请求参数中无此参数 */
		private String resultCode;
		
		public String getTradeOutAccount() {
			return tradeOutAccount;
		}

		public void setTradeOutAccount(String tradeOutAccount) {
			this.tradeOutAccount = tradeOutAccount;
		}

		public String getTradeOutPartnerId() {
			return tradeOutPartnerId;
		}

		public void setTradeOutPartnerId(String tradeOutPartnerId) {
			this.tradeOutPartnerId = tradeOutPartnerId;
		}

		public String getTradeInAccount() {
			return tradeInAccount;
		}

		public void setTradeInAccount(String tradeInAccount) {
			this.tradeInAccount = tradeInAccount;
		}

		public String getTradeInPartnerId() {
			return tradeInPartnerId;
		}

		public void setTradeInPartnerId(String tradeInPartnerId) {
			this.tradeInPartnerId = tradeInPartnerId;
		}

		public double getRefundAmount() {
			return refundAmount;
		}

		public void setRefundAmount(double refundAmount) {
			this.refundAmount = refundAmount;
		}

		public String getRefundReason() {
			return refundReason;
		}

		public void setRefundReason(String refundReason) {
			this.refundReason = refundReason;
		}

		public String getResultCode() {
			return resultCode;
		}

		public void setResultCode(String resultCode) {
			this.resultCode = resultCode;
		}

	}

	/**
	 * 
	 * @类功能说明：交易类型，子交易退款数据
	 * @类修改者：
	 * @修改日期：
	 * @修改说明：
	 * @公司名称：浙江汇购科技有限公司
	 * @部门：技术部
	 * @作者：guotx
	 * @创建时间：2016-3-14上午9:15:48
	 * @版本：
	 */
	public static class ChildTrade {
		/** 退款金额 */
		private double refundAmount;
		/** 退款理由 */
		private String refundReason;
		/** 补款金额 ,只有在处理异步回调接口时才使用，请求参数中无此参数*/
		private double appendAmount;
		
		/** 处理结果码，只有在处理异步回调接口时才使用，请求参数中无此参数 */
		private String resultCode;

		public double getRefundAmount() {
			return refundAmount;
		}

		public void setRefundAmount(double refundAmount) {
			this.refundAmount = refundAmount;
		}

		public String getRefundReason() {
			return refundReason;
		}

		public void setRefundReason(String refundReason) {
			this.refundReason = refundReason;
		}

		public String getResultCode() {
			return resultCode;
		}

		public void setResultCode(String resultCode) {
			this.resultCode = resultCode;
		}

		public double getAppendAmount() {
			return appendAmount;
		}

		public void setAppendAmount(double appendAmount) {
			this.appendAmount = appendAmount;
		}

	}

	/**
	 * @描述： 解析返回的退款详情
	 * @author: guotx 
	 * @version: 2016-3-23 下午1:58:40
	 */
	public static RefundDetailData parseResultDetails(String result_detail){
			RefundDetailData detailData = null;
			detailData = new RefundDetailData();
//			每笔中包含：交易退款$收费退款|分润退款$$退子交易
//			子交易数据集
			String childDetail = null;
//			主交易和分润
			String mainAndSplitDetail = null;
//			主交易数据集
			String mainDetail = null;
			String[] mainAndChildDetails = result_detail.split("$$");
			if (mainAndChildDetails.length==2) {
//				存在子交易
				childDetail=mainAndChildDetails[1];
				if (StringUtils.isNotBlank(childDetail)) {
					String[] child=childDetail.split("\\^");
					ChildTrade childTrade=new ChildTrade();
					childTrade.setRefundAmount(Double.parseDouble(child[0]));
					childTrade.setAppendAmount(Double.parseDouble(child[1]));
					childTrade.setResultCode(child[2]);
					detailData.setChildTrade(childTrade);
				}
			}
			
			mainAndSplitDetail=mainAndChildDetails[0];
			String[] mainFeeSplits=mainAndSplitDetail.split("$");
			if (mainFeeSplits.length>1) {
//				存在收费退款
				mainDetail=mainFeeSplits[0];
				//TODO 收费退款处理
			}else{
//				不存在收费退款却存在分润退款
				String[] mainAndSplit=mainAndSplitDetail.split("\\|");
				mainDetail=mainAndSplit[0];
				if (mainAndSplit.length>1) {
//					存在分润退款
					List<DivideProfit> divideProfits=new ArrayList<RefundDetailData.DivideProfit>();
					for(int n=1;n<mainAndSplit.length;n++){
						String[] divid=mainAndSplit[n].split("\\^");
						DivideProfit divideProfit=new DivideProfit();
						divideProfit.setTradeOutAccount(divid[0]);
						divideProfit.setTradeOutPartnerId(divid[1]);
						divideProfit.setTradeInAccount(divid[2]);
						divideProfit.setTradeInPartnerId(divid[3]);
						divideProfit.setRefundAmount(Double.parseDouble(divid[4]));
						divideProfit.setResultCode(divid[5]);
						divideProfits.add(divideProfit);
					}
					detailData.setDivideProfits(divideProfits);
				}
			}
			
			if (StringUtils.isNotBlank(mainDetail)) {
				MainTrade mainTrade=new MainTrade();
				String[] main=mainDetail.split("\\^");
				mainTrade.setTradeNo(main[0]);
				mainTrade.setRefundAmount(Double.parseDouble(main[1]));
				mainTrade.setResultCode(main[2]);
				detailData.setMainTrade(mainTrade);
			}
		return detailData;
	}
	public static void main(String[] args) {
		String result_detail="2016032500001000620089902969^0.01^SUCCESS";
		parseResultDetails(result_detail);
	}
}
