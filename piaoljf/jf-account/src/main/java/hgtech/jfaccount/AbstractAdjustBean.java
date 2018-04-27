package hgtech.jfaccount;

import hg.common.util.UUIDGenerator;
import hgtech.jf.JfChangeApply;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @类功能说明：申请积分调整的bean
 * @类修改者：
 * @修改日期：2014-9-22下午1:50:06
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-22下午1:50:06
 *
 */
public abstract class AbstractAdjustBean implements JfChangeApply{
	
	public AdjustBean bean = new AdjustBean();
	private boolean newUser;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return  bean.userCode + "  "+ bean.jf+" " +bean.accountTypeId;
	}

	/* (non-Javadoc)
	 * @see hgtech.jfcal.model.JfChangeApply#getjf()
	 */
	@Override
	public int getjf() {
		return bean.jf;
	}

	/* (non-Javadoc)
	 * @see hgtech.jfcal.model.JfChangeApply#getuserCode()
	 */
	@Override
	public String getuserCode() {
		return bean.userCode;
	}

	/* (non-Javadoc)
	 * @see hgtech.jfcal.model.JfChangeApply#gettradeFlow()
	 */
	@Override
	public Serializable gettradeFlow() {
		return bean.tradeFlow;
	}

	/* (non-Javadoc)
	 * @see hgtech.jfcal.model.JfChangeApply#gettradeFlowId()
	 */
	@Override
	public String gettradeFlowId() {
		return bean.tradeFlowId;
	}

	/* (non-Javadoc)
	 * @see hgtech.jfcal.model.JfChangeApply#getremark()
	 */
	@Override
	public String getremark() {
		return bean.remark;
	}

	/* (non-Javadoc)
	 * @see hgtech.jfcal.model.JfChangeApply#getbatchNo()
	 */
	@Override
	public String getbatchNo() {
		return bean.batchNo;
	}

	/* (non-Javadoc)
	 * @see hgtech.jfcal.model.JfChangeApply#getaccountType()
	 */
	@Override
	public  Object getaccountType() {
		return getAcctTypebyId(bean.accountTypeId);
	}

	/**
	 * @方法功能说明：得到积分账户类型对象
	 * @修改者名字：xinglj
	 * @修改时间：2014年11月2日下午3:03:27
	 * @修改内容：
	 * @参数：@param accountTypeId2
	 * @参数：@return
	 * @return:Object
	 * @throws
	 */
	protected abstract Object getAcctTypebyId(String accountTypeId2);
 
	/* (non-Javadoc)
	 * @see hgtech.jfcal.model.JfChangeApply#isArriving()
	 */
	@Override
	public boolean isArriving() {
		return false;
	}
	
	/* (non-Javadoc)
	* @see hgtech.jf.JfChangeApply#getValidDay()
	*/
	@Override
	public Date getValidDay() {
	return null;
	}
	
	/* (non-Javadoc)
	 * @see hgtech.jf.JfChangeApply#getMerchandise()
	 */
	@Override
	public String getMerchandise() {
		return null;
	}
	/* (non-Javadoc)
	 * @see hgtech.jf.JfChangeApply#getMerchant()
	 */
	@Override
	public String getMerchant() {
		return null;
	}
	/* (non-Javadoc)
	* @see hgtech.jf.JfChangeApply#getMerchandiseAmount()
	*/
	@Override
	public int getMerchandiseAmount() {
	return 0;
	}
	/* (non-Javadoc)
	* @see hgtech.jf.JfChangeApply#getFee()
	*/
	@Override
	public int getFee() {
	return 0;
	}
	
	/* (non-Javadoc)
	* @see hgtech.jf.JfChangeApply#isMerchandiseArriving()
	*/
	@Override
	public boolean isMerchandiseArriving() {
	    return false;
	}
	/* (non-Javadoc)
	* @see hgtech.jf.JfChangeApply#getNoticeMobile()
	*/
	@Override
	public String getNoticeMobile() {
	    return "";
	}
	
	/* (non-Javadoc)
	* @see hgtech.jf.JfChangeApply#getAccountTypeForJfRate()
	*/
	@Override
	public Object getAccountTypeForJfRate() {
		return getaccountType();
	}
	
	/* (non-Javadoc)
	* @see hgtech.jf.JfChangeApply#getMerchandiseStatus()
	*/
	@Override
	public String getMerchandiseStatus() {
	return JfFlow.NOR;
	}
	
	@Override
	public String getSendStatus() {
		return JfFlow.NOR;
	}
	@Override
	public String getId() {
		return UUIDGenerator.getUUID();
	}
	@Override
	public String getFlowStatus() {
		return JfFlow.NOR;
	}
	@Override
	public void setFlowStatus(String flowStatus) {
		
	}
	@Override
	public String getRule() {
		return null;
	}
	@Override
	public boolean isNewUser() {
		return newUser;
	}

	public void setNewUser(boolean newUser) {
		this.newUser = newUser;
	}
	
	@Override
	public boolean isSavejf0() {
		return false;
	}
	
	@Override
	public Date getInValidDate() {
		return null;
	}
	@Override
	public String getAppId() {
		return bean.appId;
	}
}	
