/**
 * @文件名称：Session.java
 * @类路径：hgtech.jfcal.model
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年9月30日下午3:12:23
 */
package hgtech.jfcal.model;

import hgtech.jf.entity.Entity;
import hgtech.jf.entity.StringUK;

/**
 * @类功能说明：一个用户的积分中间数据
 * @类修改者：
 * @修改日期：2014年9月30日下午3:12:23
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年9月30日下午3:12:23
 *
 */
public class Session extends DataRow implements Entity<StringUK>{
	public StringUK userCode;
	/* (non-Javadoc)
	 * @see hgtech.jf.entity.Entity#getUK()
	 */
	@Override
	public StringUK readUK() {
		return userCode;
	}

	/* (non-Javadoc)
	 * @see hgtech.jf.entity.Entity#setUK(java.lang.Object)
	 */
	@Override
	public void putUK(StringUK uk) {
		this.userCode=uk;
	}

	/* (non-Javadoc)
	 * @see hgtech.jf.entity.Entity#syncTransentPersistence()
	 */
	@Override
	public void syncUK() {
		// TODO Auto-generated method stub
		
	}

}
