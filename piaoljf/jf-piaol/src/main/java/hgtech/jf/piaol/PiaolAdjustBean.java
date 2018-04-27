/**
 * @文件名称：PiaolAdjustBean.java
 * @类路径：hgtech.jf.piaol
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年11月2日下午3:18:10
 */
package hgtech.jf.piaol;

import hgtech.jfaccount.AbstractAdjustBean;
import hgtech.jfaccount.JfAccountType;
import hgtech.jfaccount.SetupAccountContext;

/**
 * @类功能说明：转换为账户类型对象的adjust bean
 * @类修改者：
 * @修改日期：2014年11月2日下午3:18:10
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年11月2日下午3:18:10
 *
 */
public class PiaolAdjustBean extends AbstractAdjustBean {

	/* (non-Javadoc)
	 * @see hgtech.jfaccount.AbstractAdjustBean#getAcctTypebyId(java.lang.String)
	 */
	@Override
	protected Object getAcctTypebyId(String accountTypeId2) {
		JfAccountType acctType = SetupAccountContext.findSystemType(accountTypeId2);
		if(acctType==null)
			System.out.println("accountType null"+accountTypeId2);
		return acctType;
	}

}
